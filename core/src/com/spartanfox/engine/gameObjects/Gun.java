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
import com.spartanfox.engine.globals.Sounds;

/**
 *
 * @author Ben
 */
public final class Gun extends PhysicsObject{
    int cooldown;
    boolean shoot;
    public Gun(float x, float y,float scale, Engine parent) {
        super("gun", x, y, scale, scale);
        this.parent = parent;
        addToWorld(parent.world);
    }
    
    @Override
    public void addToWorld(World world){
        BodyFactory fac = BodyFactory.getFactory(world);
        body = fac.makeModelBody(this,sprite.getX(), sprite.getY(), sprite.getHeight()/parent.WORLD_SCALE,BodyFactory.STEEL, BodyDef.BodyType.DynamicBody,false);
    }
    //*
    @Override
    public void collideBegin(Fixture b){
        Vector2 vol = body.getLinearVelocity();
        vol.sub(b.getBody().getLinearVelocity());
        
        if(Math.abs(vol.x)+Math.abs(vol.y)>=5){
            if(cooldown<=0&&!((PhysicsObject)b.getBody().getUserData()).name.contains("bullet")){
                shoot = true;

            }
        }
    }
    /**/
    //*
    @Override
    public void update(){
        super.update();
        cooldown--;
        
        if(grabbed||shoot){
            shoot = false;
            if(cooldown<0){
                Vector2 vol = body.getLinearVelocity();
                Vector2 pos = body.getPosition();
                float rot = body.getAngle();
                PhysicsObject bullet = new Bullet(pos.x+(float)(Math.cos(rot)*sprite.getWidth()/1.8/parent.WORLD_SCALE),pos.y+(float)(Math.sin(rot)*sprite.getHeight()/1.8/parent.WORLD_SCALE),sprite.getHeight()/8,this.parent);
                bullet.body.setLinearVelocity(vol);
                bullet.body.applyLinearImpulse((float)Math.cos(rot)*1,(float)Math.sin(rot)*1, 0, 0, true);
                parent.addObject(bullet);
                //Sounds.play("fart");
               // grabbed = false;
                cooldown=60;
            }
        }
    }
    /*/
    //@Override
    //public void update(){
    //        super.update();
    //}
    /**/
}





