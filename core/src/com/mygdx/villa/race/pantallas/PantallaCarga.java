package com.mygdx.villa.race.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.mygdx.villa.race.elementos.Imagen;
import com.mygdx.villa.race.utiles.Config;
import com.mygdx.villa.race.utiles.Recursos;
import com.mygdx.villa.race.utiles.Render;


public class PantallaCarga implements Screen {
	private Imagen fondo;
	private boolean FadeInTerminado = false, termina = false;
	private float a = 0;
	private float contTiempo= 0, tiempoEspera = 10;
	private float contTiempoT=0, tiempoTermina= 10;
	private Music fondoMusicaCarga;
	
	@Override
	public void show() {
		fondo = new Imagen(Recursos.CARGA);
		fondo.setSize(Config.ANCHO, Config.ALTO);
		fondo.setTransparencia(a);
		
		fondoMusicaCarga = Gdx.audio.newMusic(Gdx.files.internal("audio/PantallaCarga.mp3"));
		fondoMusicaCarga.play();
	}

	@Override
	public void render(float delta) {
		Render.limpiarPantalla(0, 0, 0);
		contFade();
		Render.batch.begin();
		fondo.dibujar();
		Render.batch.end();
	}

	private void contFade() {
		fondo.setTransparencia(a);
		if (!FadeInTerminado) {
			a += 0.01f;
			if (a > 1) {
				a = 1;
				FadeInTerminado = true;
			}
		} else {
			contTiempo += 0.03f;
			if (contTiempo > tiempoEspera) {
				a -= 0.01f;
				if (a < 0) {
					a = 0;
					termina=true;
				}
			}
		}
		if(termina) {
			contTiempoT+=0.1f;
			if(contTiempoT>tiempoTermina) {
				Render.app.setScreen(new PantallaMenu());
			}
		}
	}

	@Override 
	public void resize(int width, int height) {
		
		
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
		fondoMusicaCarga.dispose();
	}

}