package com.mygdx.villa.race.objetosgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.mygdx.villa.race.utiles.Recursos;
import com.mygdx.villa.race.utiles.Render;

public class Auto {
    // Enumeración para los estados del auto
    public enum EstadoAuto {
        PARADO,
        CORRIENDO
    }

    private Sprite spr;
    private float x, y, ancho, alto;
    private int turbo;
    private int marca;
    private float tiempo;
    int velocidadActual;
    int velocidadMaxima = 100;
    private Animation<TextureRegion> corriendoAnimation;
    private TextureRegion[] regionsMovimiento_corriendo;
    private Animation<TextureRegion> paradoAnimation;
    private TextureRegion[] regionsMovimiento_parado;
    private TextureRegion frameActual;

    private EstadoAuto estadoActual;

    public Auto(float x, float y, float ancho, float alto) {
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.alto = alto;

        // Carga texturas para el auto parado y corriendo
        Texture paradotxt = new Texture(Recursos.PARADO);
        Texture corriendotxt = new Texture(Recursos.CORRIENDO);

        // Divide texturas por regiones para la animacion
        TextureRegion[][] paradoFrames = TextureRegion.split(paradotxt, paradotxt.getWidth() / 4, paradotxt.getHeight());
        regionsMovimiento_parado = new TextureRegion[4];
        TextureRegion[][] corriendoFrames = TextureRegion.split(corriendotxt, corriendotxt.getWidth() / 4, corriendotxt.getHeight());
        regionsMovimiento_corriendo = new TextureRegion[4];

        // Llena el array con las regiones de la animación del auto parado
        for (int i = 0; i < 4; i++) {
            regionsMovimiento_parado[i] = paradoFrames[0][i];
        }
        // Crea la animacion del auto parado con un tiempo de cambio de frame
        paradoAnimation = new Animation<>(1 / 6f, regionsMovimiento_parado);
        tiempo = 0f;

        // Llena el array con las regiones de la animacion del auto corriendo
        for (int i = 0; i < 4; i++) {
            regionsMovimiento_corriendo[i] = corriendoFrames[0][i];
        }
        // Crea la animacion del auto corriendo con un tiempo de cambio de frame
        corriendoAnimation = new Animation<>(1 / 6f, regionsMovimiento_corriendo);
        tiempo = 0f;

        estadoActual = EstadoAuto.PARADO; // Estado inicial del auto: parado

        spr = new Sprite(paradoAnimation.getKeyFrame(0, true));
        spr.setPosition(x, y);
        spr.setSize(ancho, alto);

        velocidadMaxima = 100;}

    public float getY() {
		return y;
	}

	// Metodo para dibujar el auto en la pantalla
    public void render() {
        tiempo += Gdx.graphics.getDeltaTime();
        frameActual = paradoAnimation.getKeyFrame(tiempo, true);
        spr.draw(Render.batch);
    }

    // Metodo para actualizar la animación del auto
    public void updateAnimation(float delta, float velocidad) {
        switch (estadoActual) {
            case PARADO:
                spr.setRegion(paradoAnimation.getKeyFrame(tiempo, true));
                break;
            case CORRIENDO:
                spr.setRegion(corriendoAnimation.getKeyFrame(tiempo, true));
                break;
        }
        spr.setPosition(x, y);

        // Ajustar la velocidad de la animacion segun la velocidad actual del auto
        float velocidadAnimacion = 1f + (velocidadActual / velocidadMaxima);
        corriendoAnimation.setFrameDuration(1f / (6f * velocidadAnimacion));
    }

    // Metodo para obtener la animación actual del auto
    public Animation<TextureRegion> getAnimationEstadoActual() {
        switch (estadoActual) {
            case PARADO:
                return paradoAnimation;
            case CORRIENDO:
                return corriendoAnimation;
            default:
                return paradoAnimation;
        }
    }

    // Metodo para cambiar el estado del auto
    public void cambiarEstado(EstadoAuto nuevoEstado) {
        estadoActual = nuevoEstado;
        spr.setRegion(getAnimationEstadoActual().getKeyFrame(0));
    }

    public void setPosition(float x) {
        this.x = x;
        spr.setPosition(x, y);
    }

    public float getX() {
        return x;
    }

    public float getAncho() {
        return ancho;
    }
}