/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spartanfox.engine.gameObjects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.spartanfox.engine.engineComponents.Engine;
import com.spartanfox.engine.globals.BodyFactory;
import com.spartanfox.engine.globals.Sounds;


/**
 *
 * @author Ben
 */
public class Bullet extends BallBase{
    int cooldown;
    public Bullet(float x, float y, float die, Engine parent) {
        super("ball", x, y,die);
        this.parent = parent;
        name+=".bullet";
        cooldown = 20*60*60;
        sprite.setColor(Color.GRAY);
        this.addToWorld(parent.world,BodyFactory.STEEL, BodyDef.BodyType.DynamicBody);
    }

    @Override
    public void update(){
        super.update();
        cooldown--;
        if(cooldown<=0)this.markForDelete=true;
    }

}
