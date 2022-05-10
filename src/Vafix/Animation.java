package Vafix;

import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

import java.util.Arrays;

public class Animation{
	private int x;
	private int y;
	private boolean playing;
	private float counter;
	private int frame;
	private int totalFrames;
	private int speed = 1;
	private Texture[] content;
	
	public Animation(Texture[] content) {
		this.content = content;
		this.totalFrames = this.content.length;
		this.frame = 0;
		this.counter = 0;
		this.x = 0;
		this.y = 0;
	}
	
	public void play() {
		if(!this.playing)this.frame = 0;
		this.playing = true;
		
	}
	
	public void stop() {
		this.playing = false;
	}
	
	public void speed(int speed) {
		this.speed = speed;
	}
	
	public void Continue() {
		this.playing = true;
	}
	
	public void coords(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void run() {
		if(this.playing) {
			this.counter++;
			if(this.counter%this.speed/10==0) {
				this.frame++;
				if(this.frame>=totalFrames)this.frame=0;
			}
		}
		gmt.drawImage(this.x, this.y, this.content[frame].getWidth()*10, this.content[frame].getHeight()*10, this.content[frame]);
	}
}
