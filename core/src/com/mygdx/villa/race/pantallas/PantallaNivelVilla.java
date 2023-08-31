package com.mygdx.villa.race.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
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

public class PantallaNivelVilla implements Screen {
	//para el resize porque sino no me toma el Config.ANCHO y ALTO
	int width= Config.ANCHO;
	int height=Config.ALTO;
	
	//creo el auto y sus variables
	Auto auto;
	private float velocidad=0f;
	private float aceleracion=25f;
	private float frenado=30f;
	private float velocidadMaxima=180f;
	boolean acelerando = false;
	boolean frenando = false;
	private Music audioAcelerando;
	
	//cargo para que me tome las entradas
	Entradas entradas= new Entradas();
	
	Imagen autoTextura;
	Imagen fondo;
	
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
		
		//carga audio del auto acelerando
		audioAcelerando = Gdx.audio.newMusic(Gdx.files.internal("audio/acelerando.mp3"));
		//lo pone el loop
		audioAcelerando.setLooping(true);
		
		//cargo el archivo del mapa
		mapa = new TmxMapLoader().load("mapas/villa/PRUEBA.tmx");
		
		//creo el renderer del mapa
		mapaRenderer = new OrthogonalTiledMapRenderer(mapa);
		
		
		//creo el auto
		auto = new Auto(0,15,256,120);
		
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
	        acelerando = true;
	        auto.cambiarEstado(Auto.EstadoAuto.CORRIENDO);
			audioAcelerando.play();	
	    } else {
	        auto.cambiarEstado(Auto.EstadoAuto.PARADO);
	        acelerando = false;
			audioAcelerando.stop();	
	    }

	    if (entradas.isS()) {
	        frenando = true;
	    } else {
	        frenando = false;
	    }
	    //calculo para crear una aceleracion lo mas parecido a la realidad, Hay que realizar cambios
        if (acelerando) {
            velocidad += aceleracion * delta;
        } else if (frenando) {
            velocidad -= frenado * delta;
        }

        if (velocidad > velocidadMaxima) {
            velocidad = velocidadMaxima;
        } else if (velocidad < 0) {
            velocidad = 0;
        }
        //setea la posicion del auto siguiendo los calculos de la velocidad
        auto.setPosition(auto.getX() + velocidad * delta);
		
        //dibuja
        Render.batch.begin();


		auto.updateAnimation(delta,velocidad);
		auto.render();
		Render.batch.end();
		
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

	}
	
}
