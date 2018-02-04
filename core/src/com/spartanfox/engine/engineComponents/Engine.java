/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spartanfox.engine.engineComponents;

import com.spartanfox.engine.gameObjects.PhysicsObject;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.JointDef;
import static com.badlogic.gdx.physics.box2d.JointDef.JointType.MouseJoint;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.QueryCallback;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;
import com.badlogic.gdx.physics.box2d.joints.MouseJointDef;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.Array;
import com.spartanfox.engine.gameObjects.*;
import com.spartanfox.engine.globals.BodyFactory;
import com.spartanfox.engine.globals.Values;
import java.util.Random;

/**
 *
 * @author Ben
 */
public class Engine implements ContactListener,InputProcessor,GestureListener {
    public boolean paused;
    public World world;
    Array<PhysicsObject> physicsObjects;
    Array<Joint> joints;
    Array<JointDef> jointsToBeAdded;
    public final float WORLD_SCALE = 100;
    
    SpriteBatch batch;    
    Camera debugcam;
    Camera cam;
    BaseBackground background;
    Box2DDebugRenderer debugRenderer;
    Gun gun;
    private Vector3 tmp = new Vector3();
    private Vector2 tmp2 = new Vector2();
    private MouseJointDef jointDef;
    private MouseJoint joint;
    //ArrayList<Intercecting> colliding;
    //Array<PhysicsObject> markedForDelete;
    public int x,y,width,height;
    public Random ran;
    public Engine(int x,int y,int width,int height){
        paused = false;
        batch = new SpriteBatch();
        BodyFactory.engine = this;
        debugcam = new OrthographicCamera(Values.WIDTH/WORLD_SCALE,Values.HEIGHT/WORLD_SCALE);
             cam = new OrthographicCamera(Values.WIDTH,Values.HEIGHT);
        //debugRenderer = new Box2DDebugRenderer(false,true,false,false,false,false);
        debugRenderer = new Box2DDebugRenderer(true,true,true,true,true,true);
        background = new BaseBackground(new Texture(Gdx.files.internal("background.png")));
        InputMultiplexer multi = new InputMultiplexer();
        
        multi.addProcessor(new GestureDetector(this));
        multi.addProcessor(this);
        Gdx.input.setInputProcessor(multi);  
        
        //batch.setProjectionMatrix(cam.combined);
        this.world = new World(new Vector2(0,
        /*
            0
        /*/-9.8f/**/),true);

        this.world.setContactListener(this);
        this.world.setWarmStarting(true);
        // mouse joint
		
        
        
        joints          = new Array();
        physicsObjects  = new Array();
        jointsToBeAdded = new Array();
        createFloor();
        ran = new Random();
        for (int i = 0; i < 5; i++) {
           //physicsObjects.add(new WoodenBlock(((ran.nextFloat()*Values.WIDTH)-Values.WIDTH/2)/WORLD_SCALE,i*10,(int)(1*WORLD_SCALE),(int)(1*WORLD_SCALE),this));
        }
        for (int i = 0; i < 5; i++) {
            //physicsObjects.add(new BouncyBall(((ran.nextFloat()*Values.WIDTH)-Values.WIDTH/2)/WORLD_SCALE,i*10,(int)(1*WORLD_SCALE),this));
            
        }
        for (int i = 0; i < 5; i++) {
            //physicsObjects.add(new MetalBlock(((ran.nextFloat()*Values.WIDTH)-Values.WIDTH/2)/WORLD_SCALE,i*10,(int)(1*WORLD_SCALE),(int)(1*WORLD_SCALE),this));
            
        }
        for (int i = 0; i < 5; i++) {
            //physicsObjects.add(new Gun(((ran.nextFloat()*Values.WIDTH)-Values.WIDTH/2)/WORLD_SCALE,i*10,(int)(1*WORLD_SCALE),this));
            
        }
        this.addObject(new BouncyBall(0,0,1*WORLD_SCALE,this));
        this.addObject(new WoodenBlock(0,0,1*WORLD_SCALE,1*WORLD_SCALE,this));
        
        jointDef = new MouseJointDef();
        jointDef.bodyA = physicsObjects.get(0).body;
        jointDef.collideConnected = true;
        jointDef.maxForce = 100;
    }
    private void createFloor() {
        this.addObject(new Wall(0,(-Values.HEIGHT/2)/WORLD_SCALE,Values.WIDTH,1*WORLD_SCALE,this));
        this.addObject(new Wall((-Values.WIDTH/2)/WORLD_SCALE,0,1*WORLD_SCALE-1,Values.HEIGHT-WORLD_SCALE*3,this));
        this.addObject(new Wall((Values.WIDTH/2)/WORLD_SCALE,0,1*WORLD_SCALE-1,Values.HEIGHT-WORLD_SCALE*3,this));
    }
    public void render(float delta){
        batch.begin();
        batch.setProjectionMatrix(cam.combined);
        background.render(batch, delta);
        if(!paused)update(delta);
        solveJoints();
        for(PhysicsObject p : physicsObjects){
            p.render(delta, batch);
            if(p.markForDelete){
                if(joint!=null){
                    if(joint.getBodyB()==p.getBody()){
                        world.destroyJoint(joint);
                        joint = null;
                    }
                }
                p.destroyed();
                world.getJoints(joints);
                for(Joint j : joints){
                    if(j.getBodyA()==p.body||j.getBodyB()==p.body){
                        world.destroyJoint(j);
                    }
                }
                world.destroyBody(p.body);
                physicsObjects.removeValue(p,false);
                
            }
        }
        //*
        batch.setProjectionMatrix(cam.combined);
        solveJoints();
        world.getJoints(joints);
        for(Joint j : joints){
            if(j.getBodyA()==null||j.getBodyB()==null){
                world.destroyJoint(j);
                System.out.println("EVIL JOINT HISSSSS");
            }
        }
        //*
        debugRenderer.render(world,debugcam.combined);
        /**/
        batch.end();
    }
    public void update(float delta){
        System.out.println("Steppin world");
        System.out.println(world.getBodyCount()+" "+this.physicsObjects.size+"    "+world.getJointCount()+" "+this.jointsToBeAdded.size+"");
        world.step(delta,1,1);
        //solveJoints();
    }
    public void addObject(PhysicsObject newObj){
        physicsObjects.add(newObj);                                                                                          
    }
    public void addJoint(JointDef joint){
        jointsToBeAdded.add(joint);
    }
    public void newRandomObject(float x, float y){
        if(world.isLocked())return;
        switch(ran.nextInt(2)){
            case(0):
                System.out.println("Spawning bounc ball");
                addObject(new BouncyBall(x,y,(int)(1*WORLD_SCALE),this));
                break;
            case(1):
                System.out.println("Spawnin metal block");
                addObject(new MetalBlock(x,y,(int)(1*WORLD_SCALE),(int)(1*WORLD_SCALE),this));
                break;         
            case(2):
                System.out.println("Spawning gun");
                addObject(new Gun(x,y,(int)(1*WORLD_SCALE),this));
                break;            
            case(3):
                System.out.println("Spawning woodenblock");
                addObject(new WoodenBlock(x,y,(int)(1*WORLD_SCALE),(int)(1*WORLD_SCALE),this));
                break;
        }
    }
    public Vector3 getMousePosInGameWorld(){
        return debugcam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
    }
    public Vector3 getMousePosInGameWorld(int button){
        return debugcam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(),button));
    }
    
    public void solveJoints(){
        world.getJoints(joints);
        for(Joint j : joints){
            if(j.getBodyA() == null || j.getBodyB() == null){
                joints.removeValue(j,true);
                world.destroyJoint(j);
            }
        } 
        for(JointDef joint : jointsToBeAdded){
            Body a = joint.bodyA;
            Body b = joint.bodyB;
            boolean connected = false;
            if(a==null||b==null){
                connected = true;
            }
            for(Joint j : joints){
                if((j.getBodyA()==a&&j.getBodyB()==b)||(j.getBodyA()==b&&j.getBodyB()==a)||(j.getBodyA()==null&&j.getBodyB()==null)){
                    connected = true;
                }
            }
            if(!connected){
                world.createJoint(joint);            
            }
            
            jointsToBeAdded.removeValue(joint,true); 
        }
    }
    public boolean alreadyConnected(Body a, Body b){
        for(JointDef j : jointsToBeAdded){
            if((j.bodyA==a&&j.bodyB==b)||
               (j.bodyA==b&&j.bodyB==a)){
                return true;
            }
        }
        return false;
    }
    @Override
    public void beginContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();
        float force = contact.getTangentSpeed();
        if(force>0)System.out.println(force);
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
       // mnfld.g
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse ci) {

    }

        
    @Override
    public boolean touchDown(int i, int i1, int i2, int i3) {
        if(joint==null){
            System.out.println("touch down");
            tmp = this.getMousePosInGameWorld();
            world.QueryAABB(dragBody, tmp.x, tmp.y, tmp.x, tmp.y);
        }else{
            joint = (MouseJoint) world.createJoint(jointDef);
        }
	return true;
    }


    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
            if(joint == null)
                    return false;
            tmp = this.getMousePosInGameWorld();
            joint.setTarget(tmp2.set(tmp.x, tmp.y-1));
            return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            if(joint == null){
                return false;
            }            
            ((PhysicsObject)joint.getBodyB().getUserData()).grabbed = false;
            world.destroyJoint(joint);
            joint = null;
            return true;
    }

    @Override
    public boolean mouseMoved(int i, int i1) {
        return false;
    }

    @Override
    public boolean scrolled(int i) {
        return false;
    }

    @Override
    public boolean keyDown(int i) {
        return false;
    }

    @Override
    public boolean keyUp(int i) {
        return false;
    }

    @Override
    public boolean keyTyped(char c) {
        return false;
    }

    @Override
    public boolean touchDown(float f, float f1, int i, int i1) {
        return false;
    }

    @Override
    public boolean tap(float f, float f1, int i, int i1) {
        System.out.println(i1);
        if(i1==0){
            System.out.println("tappy");
            if(joint == null){
                System.out.println("spawnin");
                this.newRandomObject(tmp.x,tmp.y);
                return true;
            }else{
                tmp = this.getMousePosInGameWorld();
                world.QueryAABB(deleteBody, tmp.x, tmp.y, tmp.x, tmp.y);
            }
        }else{
            //doesjt do it
            //tmp = this.getMousePosInGameWorld();
            //world.QueryAABB(dragBody, tmp.x, tmp.y, tmp.x, tmp.y);
            //return true;
        }
        return false;    
    }

    @Override
    public boolean longPress(float f, float f1) {
        return false;
    }

    @Override
    public boolean fling(float f, float f1, int i) {
        return false;
    }

    @Override
    public boolean pan(float f, float f1, float f2, float f3) {
        return false;    }

    @Override
    public boolean panStop(float f, float f1, int i, int i1) {
        return false;    }

    @Override
    public boolean zoom(float f, float f1) {
        return false;    }

    @Override
    public boolean pinch(Vector2 vctr, Vector2 vctr1, Vector2 vctr2, Vector2 vctr3) {
        return false;    }

    @Override
    public void pinchStop() {
    }
    private QueryCallback dragBody = new QueryCallback() {

		@Override
		public boolean reportFixture(Fixture fixture) {
			if(!fixture.testPoint(tmp.x, tmp.y))
                            return true;
                        if(jointDef.bodyA!=fixture.getBody()){
                            jointDef.bodyB = fixture.getBody();
                            ((PhysicsObject)fixture.getBody().getUserData()).grabbed = true;
                            jointDef.target.set(tmp.x, tmp.y);
                            joint = (MouseJoint) world.createJoint(jointDef);
                        }
			return false;
		}
    };
    private QueryCallback stickBody = new QueryCallback() {

		@Override
		public boolean reportFixture(Fixture fixture) {
			if(!fixture.testPoint(tmp.x, tmp.y))
                            return true;
                        if(jointDef.bodyA!=fixture.getBody()){
                            jointDef.bodyB = fixture.getBody();
                            ((PhysicsObject)fixture.getBody().getUserData()).grabbed = true;
                            jointDef.target.set(tmp.x, tmp.y);
                            joint = (MouseJoint) world.createJoint(jointDef);
                        }
			return false;
		}
    };
    private QueryCallback deleteBody = new QueryCallback() {

		@Override
		public boolean reportFixture(Fixture fixture) {
			if(!fixture.testPoint(tmp.x, tmp.y))
                            return true;
                        ((PhysicsObject)fixture.getBody().getUserData()).markForDelete = true;
			return false;
		}
    };

}
