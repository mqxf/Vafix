package FactoryIsland;

import Vafix.Display;
import Vafix.Texture;
import Vafix.gmt;

public class DrawTiles {
	
	static Texture tile_grass = new Texture("./res/tiles/grass.png");
	static Texture tile_stone = new Texture("./res/tiles/stone.png");
	
	public static void draw(int[][] terrainData) {
		for(int i=0;i<terrainData.length;i++) {
			if(terrainData[i][2] == 1) {
				gmt.drawImage(terrainData[i][0], terrainData[i][1], 20, 20, tile_grass);
			}
			if(terrainData[i][2] == 2) {
				gmt.drawImage(terrainData[i][0], terrainData[i][1], 20, 20, tile_stone);
			}
		}
	}
}
