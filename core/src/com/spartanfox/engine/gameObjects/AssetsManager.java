/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spartanfox.engine.gameObjects;

import com.badlogic.gdx.utils.Array;

/**
 *
 * @author Ben
 */
public class AssetsManager {
    private AssetsManager assetsManager;
   // private Array<TextureManager> textures;
    private AssetsManager(){
        //textures= new Array();
    }
    public AssetsManager getAssets(){
        if(assetsManager==null)assetsManager=new AssetsManager();
        return assetsManager;
    }

}
