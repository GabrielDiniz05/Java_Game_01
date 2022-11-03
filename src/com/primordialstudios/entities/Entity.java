package com.primordialstudios.entities;

import java.awt.image.BufferedImage;

import com.primordialstudios.main.Game;
import com.primordialstudios.world.Camera;

import java.awt.Rectangle;
import java.awt.Color;
import java.awt.Graphics;

public class Entity {

    private BufferedImage sprite;

    public static BufferedImage MINION_EN = Game.spritesheet.getSprite(112, 80, 16, 16);
    public static BufferedImage MINION_EN2 = Game.spritesheet.getSprite(128, 80, 16, 16);

    protected double x;
    protected double y;
    protected int width;
    protected int height;

    public int maskx,masky,mwidth,mheight;

    public Entity(int x, int y, int width, int height, BufferedImage sprite){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.sprite = sprite;

        this.maskx = 0;
        this.masky = 0;
        this.mwidth = width;
        this.mheight = height;
    }

    public void setMask(int maskx, int masky, int mwidth, int mheight){
        this.maskx = maskx;
        this.masky = masky;
        this.mwidth = mwidth;
        this.mheight = mheight;
    }



    public void setX(int newX){
        this.x = newX;
    }

    public void setY(int newY){
        this.y = newY;
    }

    public int getX(){
        return (int)this.x;
    }

    public int getY(){
        return (int)this.y;
    }

    public int getWidth(){
        return this.width;
    }

    public int getHeight(){
        return this.height;
    }

    public void tick(){

    }

    public static boolean isColidding(Entity e1,Entity e2){
        Rectangle e1Mask = new Rectangle(e1.getX() + e1.maskx,e1.getY() + e1.masky,e1.mwidth,e1.mheight);
        Rectangle e2Mask = new Rectangle(e2.getX() + e2.maskx,e2.getY() + e2.masky,e2.mwidth,e2.mheight);

        return e1Mask.intersects(e2Mask);
    }

    public void render(Graphics g){
        g.drawImage(sprite,this.getX() - Camera.x,this.getY() - Camera.y,null);
    }
    
}