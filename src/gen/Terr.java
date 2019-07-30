// Code for terrain generation

package gen;

import gof.Settings;
import gof.Terrain;
import gof.Type;
import util.Util;

public class Terr {

	// ================ Generate terrain ================
	public static void generate_Terrain(Terrain[][] terrain) {
		
		// Generate deserts
		Generic.gen(terrain, Type.DESSERT);
		Generic.gen(terrain, Type.FOREST);

		// Generate water
		terrain = WaterSplash.generate(terrain);

		// Calculate downfall TODO
		for (int y = 0; y < Settings.grid_size; y++)
			for (int x = 0; x < Settings.grid_size; x++) {

				// water
				if (terrain[x][y].type == Type.WATER)
					terrain[x][y].downfall = Settings.water_humidity;
				if (Terrain.nNeighborsOfType(terrain[x][y], Type.WATER, 3) >= 1)
					terrain[x][y].downfall = Settings.water_humidity * 3 / 4;
			}

		// Generate rock
		for (int y = 0; y < Settings.grid_size; y++)
			for (int x = 0; x < Settings.grid_size; x++) {
				if (terrain[x][y].type == Type.WATER)
					continue;
				int rnd = terrain[x][y].height + Util.random.nextInt(25);
				if (rnd > Settings.rock_level)
					terrain[x][y].type = Type.ROCK;
			}


		// Remaining
		/*
		 * for (int y = 0; y < Settings.grid_size; y++) for (int x = 0; x <
		 * Settings.grid_size; x++) { int tmp = terrain[x][y].temperature; if
		 * (terrain[x][y].type != Type.WATER) { int rnd = terrain[x][y].height +
		 * Util.random.nextInt(25);
		 * 
		 * Type type; if (tmp + Util.gauss(10) > 30) type = Type.DESSERT; else
		 * type = Type.PLAIN;
		 * 
		 * if (rnd > 4 * Settings.sea_level) type = Type.FOREST;
		 * 
		 * if (Util.random.nextInt(Settings.swamp_spawn) == 0) type =
		 * Type.SWAMP;
		 * 
		 * if (rnd > Settings.rock_level) type = Type.ROCK;
		 * 
		 * // Avoid placing forest next to dessert if (type == Type.FOREST &&
		 * terrain[x][y].hasNeighborType(Type.DESSERT)) type = Type.PLAIN;
		 * 
		 * terrain[x][y].type = type; } }
		 */

		// Generate rivers
		int rivers = Util.random.nextInt(100) + 1;
		for (int river = 0; river < rivers; river++) {
			int x = Util.random.nextInt(Settings.grid_size);
			int y = Util.random.nextInt(Settings.grid_size);
			River.generate(terrain[x][y], Settings.sea_level);
		}
		System.out.println("Rivers: " + rivers);

		// Calculate energy
		for (int y = 0; y < Settings.grid_size; y++)
			for (int x = 0; x < Settings.grid_size; x++) {
				terrain[x][y].energy = terrain[x][y].type.getEnergy()
						+ Util.random.nextInt(terrain[x][y].type.getEnergy());
				terrain[x][y].energy /= 2;
			}

	}
}
