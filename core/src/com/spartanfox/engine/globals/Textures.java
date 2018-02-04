/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spartanfox.engine.globals;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;

/**
 *
 * @author Ben
 */
public class Textures {
    private static Textures tex;
    private static Array<TextureHandler> textures = new Array();
    private static Texture error;
    Textures(){
        load();
    }
    public static Textures getTextures(){
        if(tex==null)tex=new Textures();
        return tex;
    }
    
    private void load(){
        //dispose all the unused textures during a theme change to avoid memory leaks
        for(TextureHandler t : textures){
            if(t.texture!=null)t.texture.dispose();
        }
        textures.clear();
        //if the textures are corrupt or just arent there then the error TextureHandler will
        //be a placeholder which is generated programatically so it is always there
        int size = 64;
        Pixmap pixmap = new Pixmap(size,size, Pixmap.Format.RGBA8888 );
        pixmap.setColor(1f,0f,0f,1f);
        pixmap.fillCircle(size/2,size/2,size/2);
        pixmap.setColor(0f,0f,0f,1f);
        pixmap.fillCircle(size/2,size/2,(size/2)-2);
        pixmap.setColor(1f,0f,0f,1f);
        int offset = size/6;
        int length = (size+size)/3;
        pixmap.drawLine(offset,offset,offset+length,offset+length);
        pixmap.drawLine(offset+length,offset,offset,offset+length);
        error = new Texture(pixmap);
        pixmap.dispose();
        //things that get rendered the most get put at the top so theyre the first in the list
        textures.add(new TextureHandler("metalBlock" ,"metalBlock.png"));
        textures.add(new TextureHandler("pixel","Pixel.png"));
        textures.add(new TextureHandler("woodenBlock","woodBlock.png"));
        textures.add(new TextureHandler("ball","ball.png"));
        textures.add(new TextureHandler("background","background.png"));
        textures.add(new TextureHandler("gun","Gun.png"));
        textures.add(new TextureHandler("cat","cat.png"));
    }
    
    
    //only use in non gameplay areas if the game requires textures use Texture.get()
    //to retreive the TextureHandler and put it into a reference to avoid needless performance drops
    public  Texture get(String name){
        for(TextureHandler t : textures){
            if(t.getName().toLowerCase().equals(name.toLowerCase())){
                return t.getTexture();
            }
        }
        return error;
    }
    public  TextureRegionDrawable getDrawable(String name){
        for(TextureHandler t : textures){
            if(t.getName().toLowerCase().equals(name.toLowerCase())){
                return new TextureRegionDrawable(new TextureRegion(t.getTexture()));
            }
        }
        System.out.println(name+ " failed to load");
        return new TextureRegionDrawable(new TextureRegion(error));
    }
    public  boolean unload(String name){
         for(TextureHandler t : textures){
            if(t.getName().toLowerCase().equals(name.toLowerCase())){
                t.texture.dispose();
                t.texture = null;
                return true;
            }
        }
        return false;
    }
    public  boolean unloadAll(){
         for(TextureHandler t : textures){
                if(t.texture!=null){
                   t.texture.dispose();
                   t.texture = null;
                }
                return true;
        }
        return false;
    }
    public void reloadAll(){
        unloadAll();
        load();
    }
    private  class TextureHandler{
        private Texture texture;
        private String name;
        private String location;
        TextureHandler(String name, String location){
            this.location = location;
            this.name = name;
        }
        public Texture getTexture(){
            if(texture == null){
                try{
                    this.texture = new Texture(Gdx.files.internal(location));
                }catch(Exception e){
                    texture = error;
                }
            }
            return texture;
        }
        public String getName(){
            return name;
        }
    }
}