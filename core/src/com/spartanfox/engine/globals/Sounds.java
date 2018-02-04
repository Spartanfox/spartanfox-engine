/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spartanfox.engine.globals;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.*;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Ben
 */
public abstract class Sounds {
    private static HashMap<String,Sound> sounds;
    public static void create(){
        sounds = new HashMap();
        sounds.put("blop",Gdx.audio.newSound(Gdx.files.internal("sounds/blop.mp3")));
        sounds.put("fart",Gdx.audio.newSound(Gdx.files.internal("sounds/lose.wav")));
        sounds.put("metal",Gdx.audio.newSound(Gdx.files.internal("sounds/metal.mp3")));
        sounds.put("win",Gdx.audio.newSound(Gdx.files.internal("sounds/win.wav")));
    }
    public static void play(String sound){
        sounds.get(sound).play(0.5f);
    }
    public static void play(String sound,float pitch){
       // long id = sounds.get(sound).play(0.5f);
       // sounds.get(sound).setPitch(id,pitch);
    }    
    public static void play(String sound,float vol,float pitch){
       // long id = sounds.get(sound).play(vol);
       // sounds.get(sound).setPitch(id,pitch);
    }
}
