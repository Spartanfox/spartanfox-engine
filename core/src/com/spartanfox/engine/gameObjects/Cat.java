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
import java.util.Random;

/**
 *
 * @author Ben
 */
public class Cat extends BallBase{
    
    public Cat(float x, float y, float die,Engine parent) {
        super("cat", x, y, die);
        this.parent=parent;
        name += ".cat";
        Random r = new Random();
        Color c = new Color(r.nextFloat(),r.nextFloat(),r.nextFloat(),1f);
        sprite.setColor(c);
        addToWorld(parent.world,BodyFactory.RUBBER, BodyDef.BodyType.DynamicBody);
    }
}
