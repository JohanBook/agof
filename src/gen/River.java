package gen;

import util.Util;
import gof.Terrain;
import gof.Type;

public class River {

	public static void generate(Terrain terr, int sea_level) {
		Terrain lowest_tile = terr;
		while (lowest_tile.type != Type.WATER) {
			terr = lowest_tile;
			terr.type = Type.WATER;
			lowest_tile = Util.findLowestTile(lowest_tile.neighboring_terrain,
					sea_level);
			

			// If find no lower tile call back recursively
			if (lowest_tile == null) {
				River.generate(terr, sea_level + 50);
				break;
			}
		}
	}
}
