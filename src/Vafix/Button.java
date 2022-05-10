package Vafix;

public class Button {
	
	private int x;
	private int y;
	private int width;
	private int height;
	private String text;
	private int[] color;
	private Texture tex;
	private Animation anim;
	
	public Button(int x, int y, int width, int height, String text, int[] color) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.color = color;
		this.text = text;
	}
	
	public Button(int x, int y, int width, int height, String text, Texture tex) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.tex = tex;
		this.text = text;
	}
	
	public Button(int x, int y, int width, int height, String text, Animation anim) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.anim = anim;
		this.text = text;
	}
	
	
}
