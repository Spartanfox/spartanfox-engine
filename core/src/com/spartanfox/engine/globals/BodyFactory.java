/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spartanfox.engine.globals;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.joints.*;
import com.spartanfox.engine.engineComponents.Engine;
import com.spartanfox.engine.utils.BodyEditorLoader;
 
public class BodyFactory {
	
	private static World world;
        public static Engine engine;
        private static BodyFactory bodyFactory;
        
        public static final int STEEL = 0;
        public static final int WOOD = 1;
        public static final int RUBBER = 2;
        public static final int STONE = 3;
        public static BodyEditorLoader loader;
	private BodyFactory(World world){
		this.world = world;
	}
        public static BodyFactory getFactory(World world){
            
            if(bodyFactory == null){
                bodyFactory = new BodyFactory(world);
                loader = new BodyEditorLoader(Gdx.files.internal("gun.json"));
                
            }
            return bodyFactory;
        }
    static public FixtureDef makeFixture(int material, Shape shape){
	FixtureDef fixtureDef = new FixtureDef();
	fixtureDef.shape = shape;
		
	switch(material){
	case 0:
                //steel
		fixtureDef.density = 1f;
		fixtureDef.friction = 0.3f;
		fixtureDef.restitution = 0.1f;
		break;
	case 1:
            //wood
		fixtureDef.density = .5f;
		fixtureDef.friction = 0.7f;
		fixtureDef.restitution = 0.3f;
		break;
	case 2:
            //rubber
		fixtureDef.density = .5f;
		fixtureDef.friction = 0.1f;
		fixtureDef.restitution = 0.8f;
		break;
	case 3:
            //stone
		fixtureDef.density = 1f;
		fixtureDef.friction = 0.9f;
		fixtureDef.restitution = 0.01f;
	default:
		fixtureDef.density = 7f;
		fixtureDef.friction = 0.5f;
		fixtureDef.restitution = 0.3f;
	}
	return fixtureDef;
    }
    static public FixtureDef makeFixture(int material){
	FixtureDef fixtureDef = new FixtureDef();
	switch(material){
	case 0:
                //steel
		fixtureDef.density = 1f;
		fixtureDef.friction = 0.3f;
		fixtureDef.restitution = 0.1f;
		break;
	case 1:
            //wood
		fixtureDef.density = .5f;
		fixtureDef.friction = 0.7f;
		fixtureDef.restitution = 0.3f;
		break;
	case 2:
            //rubber
		fixtureDef.density = .5f;
		fixtureDef.friction = 0.1f;
		fixtureDef.restitution = 0.9f;
		break;
	case 3:
            //stone
		fixtureDef.density = 1f;
		fixtureDef.friction = 0.9f;
		fixtureDef.restitution = 0.01f;
	default:
		fixtureDef.density = 7f;
		fixtureDef.friction = 0.5f;
		fixtureDef.restitution = 0.3f;
	}
	return fixtureDef;
    }
    public Body makeCirclePolyBody(Object data,float posx, float posy, float radius, int material, BodyType bodyType){
	return makeCirclePolyBody(data, posx,  posy,  radius,  material,  bodyType,  false);
    }
    public Body makeCirclePolyBody(Object data,float posx, float posy, float radius, int material, BodyType bodyType, boolean fixedRotation){
	// create a definition
	BodyDef boxBodyDef = new BodyDef();
	boxBodyDef.type = bodyType;
	boxBodyDef.position.x = posx;
	boxBodyDef.position.y = posy;
	boxBodyDef.fixedRotation = fixedRotation;
		
	//create the body to attach said definition
	Body boxBody = world.createBody(boxBodyDef);
        boxBody.setUserData(data);
	CircleShape circleShape = new CircleShape();
	circleShape.setRadius(radius /2);
	boxBody.createFixture(makeFixture(material,circleShape));
	circleShape.dispose();
	return boxBody;
    }
    public Body makeBoxPolyBody(Object data,float posx, float posy, float width, float height,int material, BodyType bodyType){
	return makeBoxPolyBody(data,posx, posy, width, height, material, bodyType, false);
    }
	
    public Body makeBoxPolyBody(Object data,float posx, float posy, float width, float height,int material, BodyType bodyType, boolean fixedRotation){
	// create a definition
	BodyDef boxBodyDef = new BodyDef();
	boxBodyDef.type = bodyType;
	boxBodyDef.position.x = posx;
	boxBodyDef.position.y = posy;
	boxBodyDef.fixedRotation = fixedRotation;
		
	//create the body to attach said definition
	Body boxBody = world.createBody(boxBodyDef);
        boxBody.setUserData(data);
	PolygonShape poly = new PolygonShape();
	poly.setAsBox(width/2, height/2);
        
	boxBody.createFixture(makeFixture(material,poly));
	poly.dispose();

	return boxBody;
    }
    public Body makeModelBody(Object data,float posx, float posy, float scale,int material, BodyType bodyType, boolean fixedRotation){
        
	// create a definition
	BodyDef boxBodyDef = new BodyDef();
	boxBodyDef.type = bodyType;
	boxBodyDef.position.x = posx;
	boxBodyDef.position.y = posy;
	boxBodyDef.fixedRotation = fixedRotation;
		
	//create the body to attach said definition
	Body boxBody = world.createBody(boxBodyDef);
        boxBody.setUserData(data);
	loader.attachFixture(boxBody,"gun",makeFixture(material),scale);
        System.out.println(boxBody.getMass());
	return boxBody;
    }
    public Body makePolygonShapeBody(Object data,Vector2[] vertices, float posx, float posy, int material, BodyType bodyType){
	BodyDef boxBodyDef = new BodyDef();
	boxBodyDef.type = bodyType;
	boxBodyDef.position.x = posx;
	boxBodyDef.position.y = posy;
	Body boxBody = world.createBody(boxBodyDef);
	boxBody.setUserData(data);
	PolygonShape polygon = new PolygonShape();
	polygon.set(vertices);
	boxBody.createFixture(makeFixture(material,polygon));
	polygon.dispose();
	return boxBody;
    }
    
    public void makeAllFixturesSensors(Body bod){
	for(Fixture fix :bod.getFixtureList()){
		fix.setSensor(true);
	}
    }
    public void makeConeSensor(Body body, float size){
		
	FixtureDef fixtureDef = new FixtureDef();
	//fixtureDef.isSensor = true; // will add in future
		
	PolygonShape polygon = new PolygonShape();
		
	float radius = size;
	Vector2[] vertices = new Vector2[5];
	vertices[0] = new Vector2(0,0);
	for (int i = 2; i < 6; i++) {
	    float angle = (float) (i  / 6.0 * 145 * com.badlogic.gdx.math.MathUtils.degreesToRadians); // convert degrees to radians
	    vertices[i-1] = new Vector2( radius * ((float)Math.cos(angle)), radius * ((float)Math.sin(angle)));
	}
	polygon.set(vertices);
	fixtureDef.shape = polygon;
	body.createFixture(fixtureDef);
	polygon.dispose();
    }
    
    public static void stickBodies(Body bodyA, Body bodyB){
        JointDef jointDef = new DistanceJointDef();
        jointDef.collideConnected = true;
        jointDef.bodyA = bodyB;
        jointDef.bodyB = bodyA;
        engine.addJoint(jointDef);
    }
}
