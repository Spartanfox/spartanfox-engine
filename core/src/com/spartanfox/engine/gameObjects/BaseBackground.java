/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spartanfox.engine.gameObjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.spartanfox.engine.globals.Values;

/**
 *
 * @author Ben
 */
public class BaseBackground {
    Texture background;
    public BaseBackground(Texture background){
        this.background = background;
    }
    public void render(SpriteBatch batch, float delta){
        if(batch.isDrawing())batch.draw(background,-Values.WIDTH/2,-Values.HEIGHT/2,Values.WIDTH,Values.HEIGHT);
    }
}
