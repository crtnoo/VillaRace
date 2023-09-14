package com.mygdx.villa.race.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.mygdx.villa.race.elementos.Imagen;
import com.mygdx.villa.race.inout.Entradas;
import com.mygdx.villa.race.objetosgame.Auto;
import com.mygdx.villa.race.utiles.Config;
import com.mygdx.villa.race.utiles.Recursos;
import com.mygdx.villa.race.utiles.Render;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class PantallaNivelVilla implements Screen {
	//para el resize porque sino no me toma el Config.ANCHO y ALTO
	int width= Config.ANCHO;
	int height=Config.ALTO;
	
	//esto tendria que ir en la clase auto
	//creo el auto y sus variables
	Auto auto;
	private Music audioAcelerando;
	
	private Stage stage;
	BitmapFont font = new BitmapFont();
	Label velocidadLabel;
	Label marchaLabel;
	Label rpmLabel;
	
	//cargo para que me tome las entradas
	Entradas entradas= new Entradas();

	
	//para el mapa
	private TiledMap mapa; //info del mapa
	private TiledMapRenderer mapaRenderer;
	//private OrthogonalTiledMapRenderer rendererMapa; //renderiza/dibuja el mapa
	
	//creo la camara
	private OrthographicCamera cam;
	//private float cameraSpeed = 200f;
	@Override
	
	
	public void show() {
		//procesa las entradas 
		Gdx.input.setInputProcessor(entradas);
		
		//creo el auto
		auto = new Auto(0,15,256,120);
		//carga audio del auto acelerando
		audioAcelerando = Gdx.audio.newMusic(Gdx.files.internal("audio/acelerando.mp3"));
		//lo pone el loop
		audioAcelerando.setLooping(true);
		
		stage = new Stage(new ScreenViewport());
        // Agrega los elementos del HUD al Stage
        BitmapFont hudFont = new BitmapFont();
        velocidadLabel = new Label("Velocidad: 0 km/h", new Label.LabelStyle(hudFont, Color.WHITE));
        velocidadLabel.setPosition(10, Config.ALTO - 60);
        stage.addActor(velocidadLabel);

        marchaLabel = new Label("Marcha: 0", new Label.LabelStyle(hudFont, Color.WHITE));
        marchaLabel.setPosition(10, Config.ALTO - 90);
        stage.addActor(marchaLabel);

        rpmLabel = new Label("RPM: 0", new Label.LabelStyle(hudFont, Color.WHITE));
        rpmLabel.setPosition(10, Config.ALTO - 120);
        stage.addActor(rpmLabel);
	    
		//cargo el archivo del mapa
		mapa = new TmxMapLoader().load(Recursos.NIVELVILLA);
		//creo el renderer del mapa
		mapaRenderer = new OrthogonalTiledMapRenderer(mapa);
		//creo la camara
		cam = new OrthographicCamera(Config.ANCHO,Config.ALTO);	
	}

	@Override
	public void render(float delta) {
		//limpia la pantalla para renderizar las imagenes
		Render.limpiarPantalla(1, 1, 1);
		//updatea la posicion de la camara
		cam.update();
		Render.batch.setProjectionMatrix(cam.combined);
		
		//setea vista del mapa
		mapaRenderer.setView(cam);
		mapaRenderer.render();
		
		//ESTO ES PARA QUE LA CAMARA SIGA DE FORMA UNIFORME AL AUTO, HAY QUE HACER ALGUNOS CAMBIOS
		// Ajustar la posición de la cámara para que el auto esté en la esquina izquierda
        float targetX = auto.getX() + auto.getAncho() / 2;
        float moveX = targetX - (cam.position.x - cam.viewportWidth / 4);

        cam.translate(moveX, 0);

        // Limitar la posición de la cámara para que el auto no se salga del mundo del juego
        float cameraHalfWidth = cam.viewportWidth / 2;
        float minX = cameraHalfWidth;
        float maxX = mapa.getProperties().get("width", Integer.class) * mapa.getProperties().get("tilewidth", Integer.class) - cameraHalfWidth;

        cam.position.x = MathUtils.clamp(cam.position.x, minX, maxX);
        
        if (entradas.isW()) {
            auto.cambiarEstado(Auto.EstadoAuto.CORRIENDO);
            audioAcelerando.play();
            auto.acelerar(delta);
            auto.actualizarRPM();
        } else {
            auto.cambiarEstado(Auto.EstadoAuto.PARADO);
            audioAcelerando.stop();
            auto.actualizarRPM();
        }

        if (entradas.isS()) {
            auto.frenar(delta);
            auto.actualizarRPM();
        }
        if (!entradas.isW() && auto.getVelocidad() > 0) {
            auto.parar(delta);
        }
        if(entradas.isE()) {
        	auto.subirMarcha();
        }
        if(entradas.isQ()) {
        	auto.bajarMarcha();
        }
	    
        //setea la posicion del auto siguiendo los calculos de la velocidad
        auto.setPosition(auto.getX() + auto.getVelocidad() * delta);
	    
        //dibuja
        Render.batch.begin();
		auto.updateAnimation(delta, auto.getVelocidad());
		auto.render();
		Render.batch.end();
		
	    // Dibuja el HUD
	    stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
	    stage.draw();
	    
	    // Actualiza los datos en las etiquetas del HUD
	    velocidadLabel.setText("Velocidad: " + auto.getKM() + " km/h");
	    marchaLabel.setText("Marcha: " + auto.getMarcha());
	    rpmLabel.setText("RPM: " + auto.getRPM());
	}

	@Override
	public void resize(int width, int height) {
		//el resize de la cam con la ventana
		cam.setToOrtho(false, width, height);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		mapa.dispose();
		Render.batch.dispose();
	}
	
}
