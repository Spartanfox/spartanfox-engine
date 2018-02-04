/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spartanfox.engine.levels;

import com.spartanfox.engine.gameObjects.PhysicsObject;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.spartanfox.engine.gameObjects.*;
import com.spartanfox.engine.globals.BodyFactory;
import com.spartanfox.engine.globals.Values;
import java.time.LocalDate;

/**
 *
 * @author Ben
 */
public class PhysicsLevel extends Level implements ContactListener{
    public World world;
    Array<PhysicsObject> physicsObjects;
    //ArrayList<Intercecting> colliding;
    Array<PhysicsObject> markedForDelete;
    public PhysicsLevel(int x,int y,int width,int height){
        
        super(x,y,width,height);
        this.world = new World(new Vector2(0,-9.8f),true);

        this.world.setContactListener(this);
        this.world.setWarmStarting(true);
        createFloor();
        physicsObjects  = new Array();
        markedForDelete = new Array();
        for (int i = 0; i < 20; i++) {
//            physicsObjects.add(new MetalBlock(-100,100+i*150,50,50,this));
        }
        for (int i = 0; i < 40; i++) {
      //      physicsObjects.add(new BouncyBall(i,i*200,50,this));
        }
      //  physicsObjects.add(new WaterBlock(0,-Values.HEIGHT/2,Values.WIDTH,200,this));
    }
    private void createFloor() {
        BodyFactory fac = BodyFactory.getFactory(world);
        fac.makeBoxPolyBody(null,0,-Values.HEIGHT/2,Values.WIDTH,1,BodyFactory.STEEL, BodyDef.BodyType.StaticBody);
        fac.makeBoxPolyBody(null,-Values.WIDTH/2,0,1,Values.HEIGHT,BodyFactory.STEEL, BodyDef.BodyType.StaticBody);
        fac.makeBoxPolyBody(null, Values.WIDTH/2,0,1,Values.HEIGHT,BodyFactory.STEEL, BodyDef.BodyType.StaticBody);
    }
    @Override
    public void render(SpriteBatch batch,float delta){
        update(delta);
      //  System.out.println((60*delta)*60);
        
        for(PhysicsObject p : physicsObjects){
            p.render(delta, batch);
            if( p.markForDelete){
                //p.markForDelete = false;
                //markedForDelete.add(p);
                world.destroyBody(p.body);
                //p.destroy();
                physicsObjects.removeValue(p,true);
                
            }
        }
       // clean();
        
    }
    public void update(float delta){
        world.step(delta,3,3);world.step(delta,3,3);world.step(delta,3,3);
        Array<Contact> contacts = world.getContactList();
    }
    public void newObject(PhysicsObject newObj){
        physicsObjects.add(newObj);                                                                                          
    }
    public void clean(){
        
        for(PhysicsObject p : markedForDelete){
            //p.destroy();
            world.destroyBody(p.body);
           //physicsObjects.remove(p);
            physicsObjects.removeValue(p,true);
        }
        markedForDelete.clear();
    }
    @Override
    public void beginContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();
        if(a.getBody().getUserData()!=null&&b.getBody().getUserData()!=null){
            ((PhysicsObject)a.getBody().getUserData()).collideBegin(b);
            ((PhysicsObject)b.getBody().getUserData()).collideBegin(a);
            
        }
        //System.out.println(a.getBody().getType()+" has hit "+b.getBody().getType());
    }

    @Override
    public void endContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();
        
        if(a.getBody().getUserData()!=null&&b.getBody().getUserData()!=null){
            ((PhysicsObject)a.getBody().getUserData()).collideEnd(b);
            ((PhysicsObject)b.getBody().getUserData()).collideEnd(a);
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold mnfld) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse ci) {

    }
}
