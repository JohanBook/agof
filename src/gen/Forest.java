package gen;

import util.Util;
import gof.Terrain;
import gof.Type;

public class Forest {

	public static void generate(Terrain[][] terrain) {
		int forests = Util.random.nextInt(500) + 1;
		for (int n = 0; n < forests; n++) {
			int x = Util.randInRange();
			int y = Util.randInRange();

			if (terrain[x][y].type == Type.PLAIN)
				gen2(terrain[x][y]);
		}
	}

	private static void gen2(Terrain tile) {
		while (tile != null) {
			tile.type = Type.FOREST;

			int count = 0;
			do {
				tile = Terrain.getRandomNeighbor(tile, Type.PLAIN);
				count++;
				if (count > 10) {
					tile = null;
					break;
				}
			} while (tile != null && tile.hasNeighborType(Type.DESSERT));
		}
	}
}
