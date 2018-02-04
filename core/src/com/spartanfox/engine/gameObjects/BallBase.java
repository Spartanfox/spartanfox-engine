/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spartanfox.engine.gameObjects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.spartanfox.engine.engineComponents.Engine;
import com.spartanfox.engine.globals.BodyFactory;
import com.spartanfox.engine.globals.Sounds;
import java.util.Random;

/**
 *
 * @author Ben
 */
public class BallBase extends PhysicsObject{
    public BallBase(String texture, float x, float y, float die) {
        super(texture, x, y, die, die);
        name+=".ball";
    }
    public BallBase(String texture, float x, float y, float die, Engine parent) {
        super(texture, x, y, die, die);
        name+=".ball";
        this.parent = parent;
        addToWorld(parent.world);
    }
    @Override
    void addToWorld(World world){
        BodyFactory fac = BodyFactory.getFactory(world);
        body = fac.makeCirclePolyBody(this,sprite.getX(),sprite.getY(),((sprite.getWidth()+sprite.getHeight())/2)/parent.WORLD_SCALE,BodyFactory.STONE, BodyDef.BodyType.DynamicBody,false);
    }
    @Override
    void addToWorld(World world,int material, BodyType type){
        BodyFactory fac = BodyFactory.getFactory(world);
        body = fac.makeCirclePolyBody(this,sprite.getX(),sprite.getY(),((sprite.getWidth()+sprite.getHeight())/2)/parent.WORLD_SCALE,material, type,false);
    }
        @Override
    public void collideBegin(Fixture b){
        float v = Math.abs((body.getLinearVelocity().x+body.getLinearVelocity().y)/2);
          Sounds.play("blop",(v/5),(v/5)+0.5f);
    }
}
