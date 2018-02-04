/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spartanfox.engine.gameObjects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.spartanfox.engine.engineComponents.Engine;
import com.spartanfox.engine.globals.BodyFactory;
import com.spartanfox.engine.levels.PhysicsLevel;
import com.spartanfox.engine.utils.IntersectionUtils;

/**
 *
 * @author Ben
 */
public class WaterBlock extends PhysicsObject{
    
    public WaterBlock(float x, float y, float width, float height, Engine parent) {
        super("pixel", x, y, width, height);
        name+=".water";
        this.parent = parent;
        sprite.setScale(1.2f);
        addToWorld(parent.world,BodyFactory.STEEL,BodyType.StaticBody);
        BodyFactory fac = BodyFactory.getFactory(parent.world);
        fac.makeAllFixturesSensors(body);
        sprite.setColor(new Color(0,0.3f,0.8f,0.7f));
    }

    @Override
    public void collideBegin(Fixture b){
        Vector2 velDir = b.getBody().getLinearVelocityFromWorldPoint(Vector2.Zero);
        velDir.sub(body.getLinearVelocityFromWorldPoint(Vector2.Zero));
        //IntersectionUtils.findIntersectionOfFixtures(null,null,null);
        
        ((PhysicsObject)b.getBody().getUserData()).constanctYV+=5;
        
    }
    @Override
    public void collideEnd(Fixture b){
        ((PhysicsObject)b.getBody().getUserData()).constanctYV-=5;
    }
}
