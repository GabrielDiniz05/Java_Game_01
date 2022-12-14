package com.primordialstudios.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

import com.primordialstudios.entities.Player;
import com.primordialstudios.graficos.Spritesheet;
import com.primordialstudios.graficos.UI;
import com.primordialstudios.entities.Enemy;
import com.primordialstudios.entities.Entity;
import com.primordialstudios.entities.Minions;
import com.primordialstudios.world.World;

public class Game extends Canvas implements Runnable,KeyListener{
    private static final long serialVersionUID = 1L;
    public static JFrame frame;
    private Thread thread;
    private BufferedImage image;
    public static Random rand;

    public static List<Entity> entities;
    public static List<Minions> minions;

    private boolean isRunning = true;
    public static final int WIDTH = 360;
    public static final int HEIGHT = 200;
    public static final int SCALE = 3;

    public static Spritesheet spritesheet;
    public static World world;
    public static Player player;
    public static Enemy enemy;
    public UI ui;

    public Game(){
        rand = new Random();

        addKeyListener(this);
        setPreferredSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE));
        iniciarFrame();
        image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);

        entities = new ArrayList<Entity>();
        minions = new ArrayList<Minions>();

        spritesheet = new Spritesheet("/spritesheet.png");
        player = new Player(0, 0, 32, 32, spritesheet.getSprite(112, 0, 32, 32));
        entities.add(player);
        world = new World("/level1.png");
    }

    public void iniciarFrame(){
        frame = new JFrame("MyFirstGame");
        frame.add(this);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public void start(){
        thread = new Thread(this);
        isRunning = true;
        thread.start();
    }

    public void stop(){
        isRunning = false;
        try{
            thread.join();
        } catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        Game game = new Game();
        game.start();
    }

    public void tick(){
        for(int i = 0; i < entities.size(); i++){
            Entity e = entities.get(i);
            e.tick();
        }
    }

    public void render(){
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null){
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = image.getGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0,0,WIDTH,HEIGHT);

        world.render(g);
		for(int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			e.render(g);
        }
        

        g.dispose();
        g = bs.getDrawGraphics();
        g.drawImage(image, 0, 0, WIDTH*SCALE, HEIGHT*SCALE, null);
        bs.show();
    }

    public void run(){
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        int frames = 0;
        double timer = System.currentTimeMillis();
        requestFocus();
        while(isRunning){
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            if(delta >= 1){
                tick();
                render();
                frames++;
                delta--;
            }
            if(System.currentTimeMillis() - timer >= 1000){
                System.out.println("FPS: " + frames);
                frames = 0;
                timer += 1000;
            }
        }
        stop();
    }

    public void keyPressed(KeyEvent e){
        if(e.getKeyCode() == KeyEvent.VK_RIGHT ||
				e.getKeyCode() == KeyEvent.VK_D){
			player.right = true;
		}else if(e.getKeyCode() == KeyEvent.VK_LEFT ||
				e.getKeyCode() == KeyEvent.VK_A){
			player.left = true;
        }
        
        if(e.getKeyCode() == KeyEvent.VK_UP ||
				e.getKeyCode() == KeyEvent.VK_W){
			player.up = true;
		}else if(e.getKeyCode() == KeyEvent.VK_DOWN ||
				e.getKeyCode() == KeyEvent.VK_S) {
			player.down = true;
		}
    }

    public void keyReleased(KeyEvent e){
        if(e.getKeyCode() == KeyEvent.VK_RIGHT ||
				e.getKeyCode() == KeyEvent.VK_D){
			player.right = false;
		}else if(e.getKeyCode() == KeyEvent.VK_LEFT ||
				e.getKeyCode() == KeyEvent.VK_A){
			player.left = false;
        }
        
        if(e.getKeyCode() == KeyEvent.VK_UP ||
				e.getKeyCode() == KeyEvent.VK_W){
			player.up = false;
		}else if(e.getKeyCode() == KeyEvent.VK_DOWN ||
				e.getKeyCode() == KeyEvent.VK_S) {
			player.down = false;
		}
    }

    public void keyTyped(KeyEvent e){

    }


    
}