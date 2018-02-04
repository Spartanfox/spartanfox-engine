/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spartanfox.engine.gameObjects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.spartanfox.engine.engineComponents.Engine;
import com.spartanfox.engine.globals.BodyFactory;
import com.spartanfox.engine.levels.PhysicsLevel;
import java.util.Random;

/**
 *
 * @author Ben
 */
public final class BouncyBall extends BallBase{
    
    public BouncyBall(float x, float y, float die,Engine parent) {
        super("ball", x, y, die);
        this.parent=parent;
        name += ".bouncy";
        this.addToWorld(parent.world,BodyFactory.RUBBER, BodyDef.BodyType.DynamicBody);
        Random r = new Random();
        Color c = new Color(r.nextFloat(),r.nextFloat(),r.nextFloat(),1f);
        sprite.setColor(c);
    }

    public void collideBegin(Fixture b){
        super.collideBegin(b);
        //*
        if( b.getBody().getType() != BodyDef.BodyType.StaticBody&&
                !parent.alreadyConnected(body, b.getBody()))
            BodyFactory.stickBodies(body, b.getBody());
        /**/
    }
   /*
    @Override
    public void collideBegin(Fixture b){
        
        Vector2 vol = body.getLinearVelocity();
        if(Math.abs(vol.x)+Math.abs(vol.y)>=70&&!((PhysicsObject)b.getBody().getUserData()).name.contains("ball")&&!((PhysicsObject)b.getBody().getUserData()).name.contains("water")
            
        ){
            //b.getBody().setTransform(-1000,1,1);
            b.getBody().setLinearVelocity(
                    b.getBody().getLinearVelocity().mulAdd(new Vector2(1000,1000),new Vector2(1,1))
            );
            b.getBody().setAngularVelocity(5);
            
            
            //((PhysicsObject)b.getBody().getUserData()).markForDelete = true;
            markForDelete = true;
            
        }
       
        
    }/**/
}
