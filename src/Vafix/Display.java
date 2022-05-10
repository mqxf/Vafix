package Vafix;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.constant.Constable;
import java.lang.module.ModuleDescriptor.Version;
import java.util.Arrays;
import java.util.Vector;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.w3c.dom.html.HTMLOListElement;

import FactoryIsland.Main;

public class Display{
	private String title;
	private int width, height;
	private boolean rez;
	private float red = 0.0f;
	
	public static long winAddr;
	
	private int programID;
	private int vertexShader;
	private int fragmentShader;
	private vafix main = new Main();
	
	private float[] vertices = {
			1.0f, 1.0f, 0.0f,
			-1.0f, 1.0f, 0.0f,
			1.0f, -1.0f, 0.0f,
			-1.0f, -1.0f, 0.0f,
	};
	
	private int[] indices = {
			0, 1, 2, 1, 3, 2
	};

	public Display(String title, int width, int height, boolean rez) {
		this.title = title;
		this.width = width;
		this.height = height;
		this.rez = rez;
		
		init();
		gmt.init(this);
		loop();
		
		glfwFreeCallbacks(winAddr);
		glfwDestroyWindow(winAddr);
		
		glfwTerminate();
		glfwSetErrorCallback(null).free();
	}
	
	private void init() {
		GLFWErrorCallback.createPrint(System.err).set();
		
		if (!glfwInit()) {
			throw new IllegalStateException("Err: Cannot init glfw!");
		}
		
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, GL_FALSE);
		if (this.rez) glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);
		else glfwWindowHint(GLFW_RESIZABLE, GL_FALSE);
		
		winAddr = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);
		
		if (winAddr == NULL) {
			throw new IllegalStateException("Err: Window cannot be created!");
		}
		
		glfwMakeContextCurrent(winAddr);
		GL.createCapabilities();
		glfwSwapInterval(1);
		
		glfwShowWindow(winAddr);	
	}
	
	private void loop() {
		glClearColor(0.2f, 0.2f, 0.2f, 1.0f);
		shader("shader");
		bind();
		
		Model model = new Model(null, null, winAddr, "rect");
		model.coords(new float[] {0.3f, 0.3f, 0.2f, 0.2f});
		Model model2 = new Model(null, null, winAddr, "rect");
		model2.coords(new float[] {0.2f, -0.5f, 0.2f, 0.2f});
		
		main.init();
		
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA,  GL_ONE_MINUS_SRC_ALPHA);
		
		long lastime = System.nanoTime();
		double AmountOfTicks = 60;
		double ns = 1000000000 / AmountOfTicks;
		double delta = 0;
		int frames = 0;
		double time = System.currentTimeMillis();
		
		while (!glfwWindowShouldClose(winAddr)) {
			
			glfwPollEvents();
			glClear(GL_COLOR_BUFFER_BIT);
			
			long now = System.nanoTime();
			delta += (now - lastime) / ns;
			lastime = now;
			
			if(delta >= 1) {
				glEnable(GL_TEXTURE_2D);
				main.running();
				frames++;
				delta--;
				if(System.currentTimeMillis() - time >= 1000) {
					System.out.println("fps:" + frames);
					time += 1000;
					frames = 0;
				}
			}
			
			glDisable(GL_TEXTURE_2D);
			
			glfwSwapBuffers(winAddr);
		}
	}
	
	
	
	private void shader(String filename) {
		programID = glCreateProgram();
		
		vertexShader = glCreateShader(GL_VERTEX_SHADER);
		glShaderSource(vertexShader, readFile("./shaders/", filename+".vs"));
		glCompileShader(vertexShader);
		if(glGetShaderi(vertexShader, GL_COMPILE_STATUS) != 1) {
			System.err.println("vertex shader error: "+glGetShaderInfoLog(vertexShader));
			System.exit(1);
		}
		
		fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
		glShaderSource(fragmentShader, readFile("./shaders/", filename+".fs"));
		glCompileShader(fragmentShader);
		if(glGetShaderi(fragmentShader, GL_COMPILE_STATUS) != 1) {
			System.err.println("fragment shader error: "+glGetShaderInfoLog(fragmentShader));
			System.exit(1);
		}
		
		glAttachShader(programID, vertexShader);
		glAttachShader(programID, fragmentShader);
		
		glBindAttribLocation(programID,0 , "vertices");
		glLinkProgram(programID);
		if((glGetProgrami(programID, GL_LINK_STATUS)) != 1) {
			System.err.println("link program error: "+glGetProgramInfoLog(programID));
			System.exit(1);
		}
		glValidateProgram(programID);
		if((glGetProgrami(programID, GL_VALIDATE_STATUS)) != 1) {
			System.err.println("validate program error: "+glGetProgramInfoLog(programID));
			System.exit(1);
		}
	}
	
	private void bind() {
		glUseProgram(programID);
	}
	
	public static String readFile(String path, String filename) {
		StringBuilder string = new StringBuilder();
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(new File(path + filename)));
			String line;
			while((line = reader.readLine()) != null) {
				string.append(line);
				string.append("\n");
			}
			reader.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		return string.toString();
	}
	
	public void setUniform1f(String name, float value) {
		int location = glGetUniformLocation(programID, name);
		if(location != -1) {
			glUniform1f(location, value);
		}
	}
	
	public void setUniform1i(String name, int value) {
		int location = glGetUniformLocation(programID, name);
		if(location != -1) {
			glUniform1f(location, value);
		}
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}
	
	public long getAddr() {
		return this.winAddr;
	}
}
