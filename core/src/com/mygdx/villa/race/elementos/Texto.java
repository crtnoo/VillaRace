package com.mygdx.villa.race.elementos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.*;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.mygdx.villa.race.utiles.Render;

public class Texto {
	BitmapFont fuente;
	private float x=0,y=0;
	private String texto = "";
	GlyphLayout layout;

	public Texto(String rutaFuente, int tamaño, Color color, boolean sombra, Color colorSombra) {
		FreeTypeFontGenerator generador = new FreeTypeFontGenerator(Gdx.files.internal(rutaFuente));
		FreeTypeFontParameter parametros = new FreeTypeFontParameter();
		parametros.size = tamaño;
		parametros.color = color;
		if (sombra) {
			parametros.shadowColor = colorSombra;
			parametros.shadowOffsetX = 1;
			parametros.shadowOffsetY = 1;
		}
		fuente = generador.generateFont(parametros);
		layout = new GlyphLayout();
	}
	
	public void setColor(Color color) {
		fuente.setColor(color);
	}
	public void setPosition(float x, float y) {
		this.x=x;
		this.y=y;
	}
	public void dibujar() {
		fuente.draw(Render.batch,texto,x,y);
	}
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
		layout.setText(fuente, texto);
	}
	public float getAncho() {
		return layout.width;
	}
	
	public float getAlto() {
		return layout.height;
	}
}
