/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spartanfox.engine.gameObjects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.spartanfox.engine.engineComponents.Engine;
import com.spartanfox.engine.globals.BodyFactory;
import java.util.Random;
/**
 *
 * @author Ben
 */
public final class WoodenBlock extends PhysicsObject{

    public WoodenBlock(float x, float y, float width, float height,Engine parent) {
        super("woodenBlock", x, y, width, height);
        name += ".wood";
        this.parent = parent;
        addToWorld(parent.world,BodyFactory.WOOD, BodyDef.BodyType.DynamicBody);
    }

    @Override
    public void destroyed(){
                if(parent.world.isLocked())return;
        //*
        Vector2 location = body.getPosition();
        if((sprite.getWidth()/parent.WORLD_SCALE)>=0.25){
            for (int i = 0; i < 4; i++) {
                PhysicsObject block = new WoodenBlock(location.x,location.y,sprite.getWidth()/2,sprite.getHeight()/2,this.parent);
                Vector2 vol = body.getLinearVelocity();
                block.body.setLinearVelocity(vol);
                body.setTransform(location.x, location.y,(new Random().nextFloat()*2)-0.5f);
                parent.addObject(block);
            }
        }
        /**/
    }
    @Override
    public void collideBegin(Fixture b){
        Vector2 vol = body.getLinearVelocity();
        vol.sub(b.getBody().getLinearVelocity());
        if(Math.abs(vol.x)+Math.abs(vol.y)>=(sprite.getWidth()/parent.WORLD_SCALE)*20){
            this.markForDelete = true;
        }
    }
}
