/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spartanfox.engine.gameObjects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.spartanfox.engine.engineComponents.Engine;
import com.spartanfox.engine.globals.BodyFactory;

/**
 *
 * @author Ben
 */
public class Wall extends PhysicsObject{
    
    public Wall(float x, float y, float width, float height, Engine parent) {
        super("pixel", x, y, width, height);
        this.parent = parent;
        sprite.setColor(Color.GRAY);
        addToWorld(parent.world,BodyFactory.STEEL, BodyDef.BodyType.StaticBody);
    }
    
}
