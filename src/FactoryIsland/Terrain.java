package FactoryIsland;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.Arrays;

import Vafix.gmt;

public class Terrain {
	static int[][] generateTerrain() {
		int[][] terrainResult = new int[(int)(Math.floor(Main.WIDTH/20)*Math.floor(Main.HEIGHT/20))][(int)(Math.floor(Main.WIDTH/100)*Math.floor(Main.HEIGHT/100)*3)];
		int row = 0;
		int col = 0;
		double scale = 0.1;
		for(int i=0;i<(Math.ceil((Main.WIDTH/20)*((Main.HEIGHT/20)))); i++) {
			col++;
			if(i%Math.ceil(Main.WIDTH/20) == 0)row++;
			if(i%Math.ceil(Main.WIDTH/20) == 0)col=0;
			if(Math.abs(Noise.noise(col*scale, (row-1)*scale, 0)) >= 0.2) {
				terrainResult[i] = new int[] {col*20, (row-1)*20, 2};
			}else{
				terrainResult[i] = new int[] {col*20, (row-1)*20, 1};
			}
			
			
		}
		
		return terrainResult;
	}
	
	static int[] loadFID(String path) {
		int[] res = null;
		StringBuilder string = new StringBuilder();
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(new File(path)));
			String line;
			while((line = reader.readLine()) != null) {
				string.append(line);
			}
			String resString = string.toString();
			resString = resString.replace(" ", "");
			res = new int[resString.length()];
			for(int i=0;i<res.length;i++) {
				res[i] = Character.getNumericValue(resString.charAt(i));;
			}
			reader.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		return res;
	}
}
