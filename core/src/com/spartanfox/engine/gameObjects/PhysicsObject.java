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
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.spartanfox.engine.engineComponents.Engine;
import com.spartanfox.engine.globals.BodyFactory;
import com.spartanfox.engine.levels.PhysicsLevel;

/**
 *
 * @author Ben
 */
public class PhysicsObject extends BaseObject{
    public Body body;
    public Engine parent;
    public float constantXV=0,constanctYV=0;
    public boolean grabbed;
    public PhysicsObject(String texture, float x, float y, float width, float height) {
        super(texture, x, y, width, height);
        name += ".physics";
    }
    public PhysicsObject(String texture, float x, float y, float width, float height, Engine parent) {
        super(texture, x, y, width, height);
        name += ".physics";
        this.parent = parent;
        addToWorld(parent.world);
    }
    
    void addToWorld(World world){  
        BodyFactory bodyFactory = BodyFactory.getFactory(world);
        body = bodyFactory.makeBoxPolyBody(this,sprite.getX(),sprite.getY(),sprite.getWidth()/parent.WORLD_SCALE,sprite.getHeight()/parent.WORLD_SCALE,BodyFactory.STEEL, BodyDef.BodyType.DynamicBody);
    }
    
    void addToWorld(World world,int material,BodyType type){  
        BodyFactory bodyFactory = BodyFactory.getFactory(world);
        body = bodyFactory.makeBoxPolyBody(this,sprite.getX(),sprite.getY(),sprite.getWidth()/parent.WORLD_SCALE,sprite.getHeight()/parent.WORLD_SCALE,material,type);
    }
    
    @Override
    public void update(){
        body.applyForceToCenter(constantXV, constanctYV,true);
        Vector2 pos = body.getPosition();
        pos.set(pos.x*parent.WORLD_SCALE,pos.y*parent.WORLD_SCALE);
        float width  = sprite.getWidth ();
        float height = sprite.getHeight();
        if(pos.y<-1000){
            markForDelete=true;
        }
        float a = body.getAngle() * MathUtils.radiansToDegrees;
        float x = pos.x - width /2f;
        float y = pos.y - height/2f;
        sprite.setPosition(x, y);
        sprite.setOrigin(width/2, height/2);
        sprite.setRotation(a);
    }
    public void destroyed(){
    }
    public void collideBegin(Fixture b){
        //System.out.println(getName()+" has collided with "+((PhysicsObject)b.getBody().getUserData()).getName());
    }
    public void colliding(Fixture b){
        
    }
    public void collideEnd(Fixture b){
        
    }
    public Vector2 getPosition(){
        return body.getPosition();
    }
    public String getName(){
        return name+":"+id;
    }
    public Body getBody(){
        return body;
    }
    public void setPosition(Vector2 newPosition){
        body.setTransform(newPosition,body.getAngle());
    }
    public void setAngle(float angle){
        body.setTransform(body.getPosition(),angle);
    }
    
    
}
