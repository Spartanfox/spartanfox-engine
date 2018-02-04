/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spartanfox.engine.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.spartanfox.engine.gameObjects.BaseObject;
import java.util.ArrayList;

/**
 *
 * @author Ben
 */
public class Level {
    int x,y,width,height;
    boolean paused;
    ArrayList<BaseObject> objects;
    public Level(int x,int y,int width,int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        
    }
    
    public void render(SpriteBatch batch,float delta){
        if(!paused)update(delta);
    }
    public void  update(float delta){
    
    }
    public void resize(int x, int y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;    
    }
    public void pause(){
        paused = true;
    }
    public void unpause(){
        paused = false;
    }
}
