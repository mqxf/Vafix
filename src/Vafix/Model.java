package Vafix;

import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.file.StandardWatchEventKinds;
import java.util.Arrays;

import javax.management.loading.PrivateClassLoader;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL;

public class Model {
	private int drawCount;
	private int v_id;
	private int i_id;
	
	private float[] vertices;
	private int[] indices;
	private String type;
	
	private float[] coords = new float[4];
	
	public Model(float[] vertices, int[] indices, long winAddr, String type) {
		glfwMakeContextCurrent(winAddr);
		
		this.type = type;
		
		if(vertices!=null && indices!=null && type==null) {
			this.vertices = vertices;
			this.indices = indices;
		}
	}
	
	public void render() {
		glEnableVertexAttribArray(0);
		
		glBindBuffer(GL_ARRAY_BUFFER, this.v_id);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, this.i_id);
		
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		
		glDrawElements(GL_TRIANGLES, this.drawCount, GL_UNSIGNED_INT, 0);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		
		glDisableVertexAttribArray(0);
	}
	
	private void genVertices(String type) {
		if(type=="rect") {
			float[] verts = {
					this.coords[0], this.coords[1], 0.0f,
					this.coords[0]+this.coords[2], this.coords[1], 0.0f,
					this.coords[0], this.coords[1]+this.coords[3], 0.0f,
					this.coords[0]+this.coords[2], this.coords[1]+this.coords[3], 0.0f,
			};
			int[] inds = {0, 1, 2, 1, 3, 2};
			this.vertices = verts;
			this.indices = inds;			
		}
		else if(type=="triangle") {
			float[] verts = {
				this.coords[0], this.coords[1], 0.0f,
				this.coords[2], this.coords[3], 0.0f,
				this.coords[4], this.coords[5], 0.0f,
			};
			int[] inds = {0, 1, 2};
			this.vertices = verts;
			this.indices = inds;
		}
		else if(type=="polygon") {
			int vertCount = 0;
			float[] verts = new float[this.coords.length+this.coords.length/2];
			for(int i=0;i<this.coords.length/2;i++) {
				for(int j=0;j<3;j++) {
					if(j!=2) {
						verts[i+j+vertCount*2] = this.coords[i+j+vertCount];
					}else {
						verts[i+j+vertCount*2] = 0.0f;
						vertCount++;
					}
				}
			}
			System.out.println(Arrays.toString(verts));
			int indCount = 0;
			int indArrCount = 0;
			int[] inds1 = new int[this.coords.length]; //0, 1, 2, 1, 2, 3
			for(int i=0;i<this.coords.length;i++) {
				if(i%3==0 && i!=0) {
					indCount+=2;
				}
				inds1[i] = i-indCount;
			}
			for(int i=0;i<inds1.length;i++) {
				if(inds1[i]==this.coords.length/2-1) {
					indArrCount++;
					break;
				}
				indArrCount++;
			}
			//int[] inds = new int[indArrCount];
			//for(int i=0;i<indArrCount;i++) {
				//inds[i] = inds1[i];
			//}
			int[] inds = {
					0, 3, 1
			};
			System.out.println(Arrays.toString(inds1));
			System.out.println(Arrays.toString(inds));
			this.vertices = verts;
			this.indices = inds;
		}
	}
	
	public void coords(float[] coords) {
		this.coords = coords;
		
		genVertices(this.type);
		init();
	}
	
	private void init() {
		GL.createCapabilities();
		
		this.drawCount = this.indices.length;
		
		FloatBuffer vecBuffer = BufferUtils.createFloatBuffer(this.vertices.length);
		vecBuffer.put(this.vertices);
		vecBuffer.flip();
		IntBuffer indBuffer = BufferUtils.createIntBuffer(this.indices.length);
		indBuffer.put(this.indices);
		indBuffer.flip();
		
		this.v_id = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, this.v_id);
		glBufferData(GL_ARRAY_BUFFER, vecBuffer, GL_STATIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		
		this.i_id = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, this.i_id);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, indBuffer, GL_STATIC_DRAW);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
	}
}

/* 
 * rect: x1, y1, width, height
 * triangle: x1, y1, x2, y2, x3, y3
 * polygon: x1, y1, x2, y2, x3, y3...
 * */
