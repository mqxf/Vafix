package Vafix;

import Vector.Vector2f;
import Vector.Vector4f;

public class Sprite {
	
	private Vector4f color;
	private Vector2f position = new Vector2f(0.5f, 0.5f);
	private Vector2f scale = new Vector2f(1.0f, 1.0f);
	private Vector2f[] texCoords;
	
	private Texture tex;
	
	public Sprite(Vector4f color) {
		this.color = color;
		this.tex = null;
	}
	
	public Sprite(Texture tex) {
		this.tex = tex;
		this.color = new Vector4f(1.0f, 1.0f, 1.0f, 1.0f);
	}
	
	public Vector4f getColor() {
		return this.color;
	}
	
	public Vector2f getPosition() {
		return this.position;
	}
	
	public Vector2f getScale() {
		return this.scale;
	}
	
	public Texture getTexture() {
		return this.tex;
	}
	
	public Vector2f[] getTexCoords() {
		Vector2f[] texCoordsGet = {
				new Vector2f(1, 1),
				new Vector2f(1, 0),
				new Vector2f(0, 1),
				new Vector2f(0, 0),
		};
		
		return texCoordsGet;
	}
}
