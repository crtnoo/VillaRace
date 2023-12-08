package com.mygdx.villa.race.objetosgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.mygdx.villa.race.utiles.Recursos;
import com.mygdx.villa.race.utiles.Render;

public class Auto {
    // Enumeración para los estados del auto
    public enum EstadoAuto {
        PARADO,
        CORRIENDO
    }
    
    //datos para la logica 
    private int marcha;
    private final  int numMarcha = 5;
    private final int[] velocidadesMaximas = {0,60,100,150,180,220};
	private float velocidad=0f;
	private float km;
	private float aceleracion=50f;
	private float frenado=40f;
	private float frenadoneutro=20f;
	private float velocidadMaxima=220f;
	boolean acelerando = false;
	boolean frenando = false;
    private float tiempo;
    private float velocidadActual;
    private float rpm;
    private float rpmMaximo;
	
    //datos para la imagen del auto
    private Sprite spr;
    private float x, y, ancho, alto;
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
        }

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

    public float getVelocidad() {
    	return velocidad;
    }
    
    public void acelerar(float delta) {
        if (marcha != 0) { // Solo se puede acelerar si no está en neutral
            velocidad += aceleracion * delta;
            // Limitar la velocidad según la marcha actual
            if (velocidad > velocidadesMaximas[marcha]) {
                velocidad = velocidadesMaximas[marcha];
            }
            actualizarKM();
        }
    }

    public void frenar(float delta) {
        velocidad -= frenado * delta;
        if (velocidad < 0) {
            velocidad = 0;
        }
        actualizarKM();
    }

    public void parar( float delta) {
        velocidad -= frenadoneutro * delta;
        if (velocidad < 0) {
            velocidad = 0;
        }
        actualizarKM();
    }
    
    public void subirMarcha() {
        if (marcha < numMarcha && velocidad >= velocidadesMaximas[marcha]) { 
            marcha++;
        }
        if(marcha==5) {
        	System.out.println("Ya no puede cambiar de marcha");
        }
    }

    public void bajarMarcha() {
        if (marcha > 1 && velocidad < velocidadesMaximas[marcha - 1]) {
            marcha--;
        }
    }

    public int getMarcha() {
        return marcha;
    }

    public void actualizarKM() {
    	km = velocidad /2f;
	}

	public float getKM() {
	return km;
	}
	
	//creo esta funcion para tomar la box del auto para la meta y obstaculos
	public Rectangle getBoundingRectangle() {
		return new Rectangle(x, y, ancho, alto);
	}

	public void setVelocidad(int velocidad) {
		this.velocidad = velocidad;
	}
}