package com.mygdx.villa.race.utiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class ControladorMusica {
    private static ControladorMusica instancia = new ControladorMusica();

    private Music fondoMusicaMenu;

    private ControladorMusica() {
        // Inicializar la música
    	fondoMusicaMenu = Gdx.audio.newMusic(Gdx.files.internal("audio/jereklein_donde.mp3"));
    	fondoMusicaMenu.setLooping(true);
        iniciarMusica();
    }

    public static ControladorMusica getInstancia() {
        if (instancia == null) {
            instancia = new ControladorMusica();
        }
        return instancia;
    }

    public void iniciarMusica() {
        if (!fondoMusicaMenu.isPlaying()) {
        	fondoMusicaMenu.play();
        }
    }

    public void detenerMusica() {
        if (fondoMusicaMenu.isPlaying()) {
        	fondoMusicaMenu.stop();
        }
    }

    public void dispose() {
    	fondoMusicaMenu.dispose();
    }
}

