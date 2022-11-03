package com.primordialstudios.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.primordialstudios.main.Game;
import com.primordialstudios.world.Camera;
import com.primordialstudios.world.World;

public class Minions extends Entity{

    private double speed = 0.5;
    private int life = 10;

    private int maskx = 2,masky = 0,maskw = 14, maskh = 16;
    private int frames = 0, maxFrames = 20, index = 0, maxIndex = 1;
    private int damageFrames = 10, damageCurrent = 0;

    private BufferedImage[] sprites;
    private boolean isDamaged = false;
    
    public Minions(int x, int y, int width, int height, BufferedImage[] sprite) {
		super(x, y, width, height, null);
		sprites = new BufferedImage[2];
		this.sprites[0] = sprite[0];
		this.sprites[1] = sprite[1];
    }
    
    public void tick(){
        if(isCollidingWithPlayer() == false){
            if((int) x < Game.player.getX() && World.isMinionFree((int)(x + speed), this.getY())
            && !isColliding((int)(x + speed), this.getY())){
                x += speed;
            }else if((int) x > Game.player.getX() && World.isMinionFree((int)(x - speed), this.getY())
            && !isColliding((int)(x - speed), this.getY())){
                x -= speed;
            }
            if((int) y < Game.player.getY() && World.isMinionFree(this.getX(),(int)(y + speed))
            && !isColliding(this.getX(),(int)(y + speed))){
                y += speed;
            }else if((int) y > Game.player.getY() && World.isMinionFree(this.getX(), (int)(y - speed))
            && !isColliding(this.getX(), (int)(y - speed))){
                y -= speed;
            }
        }else{
            if(Game.rand.nextInt(100) < 20){
                Game.player.life -= 15;
                Game.player.isDamaged = true;
            }
        }

        frames ++;
        if(frames == maxFrames){
            frames = 0;
            index ++;
            if(index > maxIndex){
                index = 0;
            }
        }
    }

    public boolean isCollidingWithPlayer(){
        Rectangle minionCurrent = new Rectangle(this.getX()+ maskx, this.getY() + masky,maskw,maskh);
        Rectangle player = new Rectangle((Game.player.getX() + Game.player.maskx) + 6,Game.player.getY() + Game.player.masky ,22,32);

        return minionCurrent.intersects(player);
    }

    public boolean isColliding(int xnext, int ynext){
        Rectangle minionCurrent = new Rectangle(xnext + maskx,ynext + masky,maskw,maskh);
        for(int i = 0; i < Game.minions.size(); i++){
            Minions e = Game.minions.get(i);
            if(e == this){
                continue;
            }
            Rectangle targetMinion = new Rectangle(e.getX() + maskx, e.getY() + masky,maskw,maskh);
            if(minionCurrent.intersects(targetMinion)){
                return true;
            }
        }
        return false;
    }

    public void render(Graphics g){
        g.drawImage(sprites[index], this.getX() - Camera.x,this.getY() - Camera.y,null);
    }


}