package com.primordialstudios.entities;

import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Graphics;

import com.primordialstudios.main.Game;
import com.primordialstudios.world.Camera;
import com.primordialstudios.world.World;

public class Player extends Entity{

    public boolean right, up, left, down;
    public int right_dir = 0 , left_dir = 1;
	public int dir = right_dir;
    public double speed = 1.7;

    private int frames = 0, maxFrames = 15, index = 0, maxIndex = 3;
	private boolean moved = false;
	
	public boolean isDamaged = false;
	public double life = 100, maxLife = 100;

	public static  int maskX = 6, maskY = 0, maskW = 22,maskH = 32;

    private BufferedImage[] rightPlayer;
    private BufferedImage[] leftPlayer;

    public Player(int x, int y, int width, int height, BufferedImage sprite){
		super(x, y, width, height, sprite);
		
        rightPlayer = new BufferedImage[4];
		leftPlayer = new BufferedImage[4];
		
		for(int i = 0; i < 4; i++){
			rightPlayer[i] = Game.spritesheet.getSprite(112 + (i * 32), 0, 32, 32);
		}

		for(int i = 0; i < 4; i++){
			leftPlayer[i] = Game.spritesheet.getSprite(112 + (i * 32), 32, 32, 32);
		}

    }

    public void tick(){
        moved = false;
		if(right && World.isFree((int)(x + speed), this.getY())) {
			moved = true;
			dir = right_dir;
			x += speed;
		}else if(left && World.isFree((int)(x - speed), this.getY())) {
			moved = true;
			dir = left_dir;
			x -= speed;
		}
		if(up && World.isFree(this.getX(), (int)(y - speed))) {
			moved = true;
			y -= speed;
		}else if(down && World.isFree(this.getX(), (int)(y + speed))) {
			moved = true;
			y += speed;
		}
		
		if(moved) {
			frames++;
			if(frames == maxFrames) {
				frames = 0;
				index++;
				if(index > maxIndex) {
					index = 0;
				}
			}
		}

        updateCamera();
    }

    public void updateCamera(){
        Camera.x = Camera.clamp(this.getX() -(Game.WIDTH/4), 0, World.WIDTH*16 - Game.WIDTH);
        Camera.y = Camera.clamp(this.getY() -(Game.HEIGHT/4), 0, World.HEIGHT*16 - Game.HEIGHT);
    }

    public void render(Graphics g){
		if(dir == right_dir){
			g.drawImage(rightPlayer[index] , this.getX() - Camera.x, this.getY() - Camera.y,null);
		}else if(dir == left_dir){
			g.drawImage(leftPlayer[index] , this.getX() - Camera.x, this.getY() - Camera.y,null);
		}
    }
}