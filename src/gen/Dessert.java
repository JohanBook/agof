package gen;

import gof.Terrain;
import gof.Type;
import util.Util;

public class Dessert {

	public static void generate(Terrain[][] terrain) {
		int forests = Util.random.nextInt(100) + 1;
		for (int n = 0; n < forests; n++) {
			int x = Util.randInRange();
			int y = Util.randInRange();

			if (terrain[x][y].type == Type.PLAIN)
				gen2(terrain[x][y]);
		}
	}

	private static void gen2(Terrain tile) {
		while (tile != null) {
			tile.type = Type.DESSERT;
			tile = Terrain.getRandomNeighbor(tile, Type.PLAIN);
		}
	}

}