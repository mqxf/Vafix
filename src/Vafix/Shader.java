package Vafix;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Shader {
	
	private String vertDir;
	private String fragDir;
	private int id;
	private int vertex;
	private int fragment;
	
	public Shader(String fragDir, String vertDir) {
		this.vertDir = vertDir;
		this.fragDir = fragDir;
		this.id = glCreateProgram();
		this.vertex = glCreateShader(GL_VERTEX_SHADER);
		this.fragment = glCreateShader(GL_FRAGMENT_SHADER);
		
		glShaderSource(this.vertex, readFile(vertDir));
		glShaderSource(this.fragment, readFile(fragDir));
	}
	
	public void compile() {
		glCompileShader(this.vertex);
		glCompileShader(this.fragment);
		
		glAttachShader(this.id, this.vertex);
		glAttachShader(this.id, this.fragment);
		
		glBindAttribLocation(this.id,0 , "vertices");
		glLinkProgram(this.id);
		
		glValidateProgram(this.id);
	}
	
	public void use() {
		glUseProgram(this.id);
	}
	
	private String readFile(String path) {
		StringBuilder string = new StringBuilder();
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(new File(path)));
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
	
	public void uploadIntArray(String name, int[] value) {
		int location = glGetUniformLocation(this.id, name);
		if(location != -1) {
			glUniform1iv(location, value);
		}
	}
}
