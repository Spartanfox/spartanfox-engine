package com.spartanfox.engine;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.spartanfox.engine.globals.Sounds;
import com.spartanfox.engine.screens.GameScreen;

public class Main extends Game {
	SpriteBatch batch;
	Texture img;
	GameScreen gameScreen;
	@Override
	public void create () {
		batch = new SpriteBatch();
		//img = new Texture("badlogic.jpg");
                Sounds.create();
                gameScreen = new GameScreen(this);
                this.setScreen(gameScreen);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(.1f, .1f, .1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		/*batch.begin();
		batch.draw(im, 0, 0);
		batch.end();*/
                super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
                gameScreen.dispose();
                
	}
}
