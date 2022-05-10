package FactoryIsland;

import java.util.Arrays;

import Vafix.Animation;
import Vafix.Display;
import Vafix.Model;
import Vafix.Renderer;
import Vafix.Sprite;
import Vafix.Texture;
import Vafix.gmt;
import Vafix.vafix;
import Vector.Vector4f;

public class Main implements vafix{
	
	static final int WIDTH = 1000;
	static final int HEIGHT = 800;
	Renderer renderer = new Renderer();
	
	Animation anim;
	int[][] terrain = Terrain.generateTerrain();
	
	public static void main(String[] args) {
		Display win = new Display("Game", WIDTH, HEIGHT, false);
		
	}

	@Override
	public void init() {
		anim = new Animation(new Texture[] {new Texture("./res/1.png"), new Texture("./res/2.png"), new Texture("./res/3.png")});
		anim.speed(1);
		anim.play();
		anim.coords(200, 200);
		Sprite sprite = new Sprite(new Vector4f(1.0f, 1.0f, 1.0f, 1.0f));
		renderer.add(sprite);
	}
	
	@Override
	public void running() {
		renderer.render();
	}
}
