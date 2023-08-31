package com.mygdx.villa.race.elementos;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.villa.race.utiles.Render;

public class Imagen {

	private Texture t;
	private Sprite s;
	int ancho;
	int alto;
	public int getAncho() {
		return ancho;
	}
	public void setAncho(int ancho) {
		this.ancho = ancho;
	}
	public int getAlto() {
		return alto;
	}
	public void setAlto(int alto) {
		this.alto = alto;
	}
	public Imagen(String ruta) {
		t = new Texture(ruta);
		s = new Sprite(t);
	}
	public void dibujar() {
		s.draw(Render.batch);
	}
	
	public void setTransparencia(float a) {
		s.setAlpha(a);
	}
	
	public void setSize(float ancho, float alto) {
		s.setSize(ancho,alto);
	}
	
	public void setX(float ancho) {
		s.setX(ancho);
	}
	

}