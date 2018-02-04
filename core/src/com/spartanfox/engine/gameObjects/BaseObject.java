/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spartanfox.engine.gameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import static com.spartanfox.engine.gameObjects.PhysicsObject.count;
import com.spartanfox.engine.globals.Textures;

/**
 *
 * @author Ben
 */
public class BaseObject {
    static int count;
    public int id;
    public String name;
    public float health = 100;
    public Sprite sprite;
    public boolean markForDelete;
    public BaseObject(String texture,float x,float y,float width,float height){
        count++;
        id = count;
        this.name = "base";
        sprite = new Sprite(Textures.getTextures().get(texture));
        sprite.setX(x);
        sprite.setY(y);
        sprite.setBounds(x,y,width,height);
    }
    public void render(float delta,SpriteBatch batch){
        update();
        if(batch.isDrawing())sprite.draw(batch);
    }
    public void update(){
        
    }
    public Sprite getSprite(){
        return sprite;
    }
}
