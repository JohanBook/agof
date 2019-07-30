// A method for generating water in the terrain
// ADVANTAGE: Leaves some "lakes" empty

package gen;

import util.Util;
import gof.Settings;
import gof.Terrain;
import gof.Type;

public class WaterSplash{

	public static Terrain[][] generate(Terrain[][] terrain) {

		// Generate a set of "splash points"
		if (Settings.max_water_splashes <= 0)
			return terrain;
		int points = Util.random.nextInt(Settings.max_water_splashes) + 1;
		for (int point = 0; point < points; point++) {
			int x = Util.random.nextInt(Settings.grid_size);
			int y = Util.random.nextInt(Settings.grid_size);

			if (terrain[x][y].height <= Settings.sea_level)
				terrain[x][y].type = Type.WATER;
		}

		// Fill all connected tiles
		for (int y = 0; y < Settings.grid_size; y++)
			for (int x = 0; x < Settings.grid_size; x++)
				if (terrain[x][y].height <= Settings.sea_level)
					for (Terrain terr : terrain[x][y].neighboring_terrain)
						if (terr.type == Type.WATER) {
							terrain[x][y].type = Type.WATER;
							break;
						}

		// Reverse to ensure filling
		for (int y = Settings.grid_size - 1; y >= 0; y--)
			for (int x = Settings.grid_size - 1; x >= 0; x--)
				if (terrain[x][y].height <= Settings.sea_level)
					for (Terrain terr : terrain[x][y].neighboring_terrain)
						if (terr.type == Type.WATER) {
							terrain[x][y].type = Type.WATER;
							break;
						}

		System.out.println("Water splashes: " + points);
		return terrain;
	}
}
