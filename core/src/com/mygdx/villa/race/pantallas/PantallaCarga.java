package com.mygdx.villa.race.pantallas;

import com.badlogic.gdx.Screen;
import com.mygdx.villa.race.elementos.Imagen;
import com.mygdx.villa.race.utiles.Config;
import com.mygdx.villa.race.utiles.Recursos;
import com.mygdx.villa.race.utiles.Render;


public class PantallaCarga implements Screen {
	Imagen fondo;
	boolean FadeInTerminado = false, termina = false;
	float a = 0;
	float contTiempo= 0, tiempoEspera = 5;
	float contTiempoT=0, tiempoTermina= 5;
	
	@Override
	public void show() {
		fondo = new Imagen(Recursos.CARGA);
		fondo.setSize(Config.ANCHO, Config.ALTO);
		fondo.setTransparencia(a);
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
		
	}

}