/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spartanfox.engine.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.spartanfox.engine.Main;
import com.spartanfox.engine.engineComponents.Engine;
import com.spartanfox.engine.gameObjects.BaseBackground;
import com.spartanfox.engine.globals.Values;
import com.spartanfox.engine.levels.Level;
import com.spartanfox.engine.levels.PhysicsLevel;

/**
 *
 * @author Ben
 */
public class GameScreen implements Screen{
    Stage stage;
    Main main;

    Engine engine;

    public GameScreen(Main main){
        this.main = main;
        
        engine = new Engine(0,0,Values.WIDTH,Values.HEIGHT);
       
    }    

    @Override
    public void show() {
        LoadUI();
        //Run once code goes here
    }
    private void LoadUI(){
        stage = new Stage();
        //Gdx.input.setInputProcessor(stage);
    }
    @Override
    public void render(float delta) {

        engine.render(delta);
        
    }

    @Override
    public void resize(int width, int height) {
        LoadUI();

    }

    @Override
    public void pause() {
        
    }

    @Override
    public void resume() {
       
    }

    @Override
    public void hide() {
        stage.dispose();
        stage = null;
    }

    @Override
    public void dispose() {
        //stage.dispose();
        //stage = null;
    }
}
