package com.mygdx.villa.race.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.mygdx.villa.race.VillaRace;
import com.mygdx.villa.race.elementos.Imagen;
import com.mygdx.villa.race.elementos.Texto;
import com.mygdx.villa.race.inout.Entradas;
import com.mygdx.villa.race.utiles.Config;
import com.mygdx.villa.race.utiles.ControladorMusica;
import com.mygdx.villa.race.utiles.Recursos;
import com.mygdx.villa.race.utiles.Render;


public class PantallaMenuOnline implements Screen{
	//cargo la musica	
	//variables para el fadein de las pantallas
	float a;
	float contTiempo = 0, tiempoEspera = 5;
	boolean FadeInTerminado = false;
	
	//variables para las opciones
	Texto opciones[] = new Texto[3];
	String textos[] = {"MODO HISTORIA","MODO ONLINE","AJUSTES","SALIR"};
	Imagen fondo;
	Texto p;
	
	private ControladorMusica controladorMusica;
	
	int opc=1;
	
	public boolean abierto=true;

	Entradas entradas= new Entradas();
	public float tiempo=0;
	
	@Override
	public void show() {
		//cargo las entradas
		Gdx.input.setInputProcessor(entradas);

		//inicio la musica para los menu
        controladorMusica = ControladorMusica.getInstancia();
        controladorMusica.iniciarMusica();;
		
		//cargo las imagenes de fondo
		fondo = new Imagen(Recursos.MENU);
		fondo.setSize(Config.ANCHO, Config.ALTO);
		
		//int avance = 90;
		for(int i=0;i<opciones.length;i++) {
			opciones[i]= new Texto(Recursos.FUENTEMENU, 35,Color.GRAY, true, Color.BLACK);
			opciones[i].setTexto(textos[i]);
			//opciones[i].setPosition((Config.ANCHO/2)-(opciones[i].getAncho()/2),(Config.ALTO/2)+(opciones[0].getAlto()/2+100)-(opciones[i].getAlto()+avance*i));
		}
		opciones[0].setPosition((Config.ANCHO/2)-(opciones[0].getAncho()/2),438);
		opciones[1].setPosition((Config.ANCHO/2)-(opciones[1].getAncho()/2),338);
		opciones[2].setPosition((Config.ANCHO/2)-(opciones[2].getAncho()/2),238);
	}

	@Override
	public void render(float delta) {
		//limpia
		Render.limpiarPantalla(0, 0, 0);
		
		//suma para el fade
		contFade();
		
		//dibuja
		Render.batch.begin();
		fondo.dibujar();
		for(int i = 0; i<opciones.length; i++) {
			opciones[i].dibujar();
		}
		Render.batch.end();
		
		tiempo+=delta;
		
		//movimiento de la seleccion hacia arriba y abajo
		if(entradas.isAbajo()) {
			if(tiempo>0.2f) {
				tiempo=0;
				opc++;
				if(opc>3) {
					opc=1;
				}
			}
		}
		if(entradas.isArriba()) {
			if(tiempo>0.2f) {
				tiempo=0;
				opc--;
				if(opc<1) {
					opc=3;
				}
			}
		}
		
		//cambio de color de las palabras para saber que se esta seleccionando
		for(int i=0; i<opciones.length;i++) {
			if(i==(opc-1)) {
				opciones[i].setColor(Color.YELLOW);
			}else {
				opciones[i].setColor(Color.GRAY);
			}
		}
		
		//detecta que se eligio y manda a la pantalla de lo elegido
		if(entradas.isEnter()) {
			if(opc==1) {
				Render.app.setScreen(new PantallaNivelVilla());
				controladorMusica.detenerMusica();
			}
			if(opc==2) {
//				Render.app.setScreen(null);
			}
			if(opc==3) {
//			Render.app.setScreen(null);
		}
			if(opc==4) {
			Render.app.dispose();
		}
		}
		
	}

	private void contFade() {
		fondo.setTransparencia(a);
		if (!FadeInTerminado) {
			a += 0.01f;
			if (a > 1) {
				a = 1;
			}
		} else {
			contTiempo += 0.03f;
			}
		}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
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
		Render.batch.dispose();
	}
}