package util;

import gof.Settings;
import gof.Terrain;

import java.util.Random;

public class Util {
	public static Random random = new Random();

	// Returns an integer
	public static int gauss(int n) {
		return (int) (random.nextGaussian() * n);
	}

	public static int min2pow(double x) {
		int n = 0;
		while (x >= 2) {
			x /= 2;
			n++;
		}
		return n;
	}

	public static Terrain findLowestTile(Terrain[] neighboring_terrain,
			int level) {
		Terrain lowest_tile = null;
		int min = level;
		for (Terrain tile : neighboring_terrain)
			if (tile.height <= min) {
				lowest_tile = tile;
				min = tile.height;
			}
		return lowest_tile;
	}

	public static int randInRange() {
		return random.nextInt(Settings.grid_size);
	}

	public static boolean isInRange(int x) {
		return 0 <= x && x < Settings.grid_size;
	}

	public static Terrain randomTile(Terrain[] neighboring_terrain) {
		return neighboring_terrain[random.nextInt(neighboring_terrain.length)];
	}

	public static int distance(Point a, Point b) {
		// return (a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y);
		return Math.abs(a.x - b.x) + Math.abs(a.y - b.y); // taxicab metric
	}
}
