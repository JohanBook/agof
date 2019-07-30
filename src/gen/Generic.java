package gen;

import gof.Settings;
import gof.Terrain;
import gof.Type;

public class Generic {

	public static void gen(Terrain[][] terrain, Type type) {
		double[][] heightmap = DiamondSquare.genh(10);
		for (int y = 0; y < Settings.grid_size; y++)
			for (int x = 0; x < Settings.grid_size; x++)
				if (heightmap[x][y] > 10)
					terrain[x][y].type = type;

	}

}
