package com.mygdx.villa.race;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.villa.race.pantallas.PantallaCarga;
import com.mygdx.villa.race.pantallas.PantallaMenu;
import com.mygdx.villa.race.utiles.Render;

public class VillaRace extends Game {
	@Override
	//crea la ventana del principio osea el juego //
	public void create () {
		Render.app = this;
		Render.batch  = new SpriteBatch();
		//this.setScreen(new PantallaCarga());
		this.setScreen(new PantallaCarga());
	}

	@Override
	//renderiza a cada rato (los FPS)
	public void render () {
		super.render();

	}
	
	@Override
	//limpia memoria, elimina de la memoria cosas que ya no se usan para no sobrecargar la memoria
	public void dispose () {
		Render.batch.dispose();
	}
}
