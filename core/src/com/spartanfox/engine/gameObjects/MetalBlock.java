/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spartanfox.engine.gameObjects;

import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.spartanfox.engine.engineComponents.Engine;
import com.spartanfox.engine.globals.BodyFactory;
import com.spartanfox.engine.globals.Sounds;

/**
 *
 * @author Ben
 */
public class MetalBlock extends PhysicsObject{
    public MetalBlock(float x, float y, float width, float height,Engine parent) {
        super("metalBlock", x, y, width, height);
        this.name += ".metal";
        this.parent = parent;
        super.addToWorld(parent.world,BodyFactory.STEEL,BodyType.DynamicBody);
    }

    @Override
    public void collideBegin(Fixture b){
        float v = Math.abs((body.getLinearVelocity().x+body.getLinearVelocity().y)/2);
          Sounds.play("metal",v/5,(v/10));
    }
}
