package com.primordialstudios.entities;

import java.awt.image.BufferedImage;

public class Enemy extends Entity{

    private BufferedImage[] rightEnemy;
    private BufferedImage[] leftEnemy;

    public Enemy(int x, int y, int width, int height, BufferedImage sprite){
        super(x, y, width, height, sprite);
    }    
}