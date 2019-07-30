package gof;

import java.util.ArrayList;

import buildings.Building;
import util.Util;

public class Terrain {
	public Type type = Type.PLAIN;
	public int height = Util.gauss(10);
	public int temperature;
	public int volcanic_activity;
	public int downfall;

	public int energy = type.getEnergy();

	public Terrain[] neighboring_terrain;

	public Animal animal;
	public Building building;

	// Checks if a tile is empty
	public boolean isEmpty() {
		return animal == null && building == null;
	}

	public boolean hasAnimal() {
		return animal != null;
	}

	public boolean hasBuilding() {
		return building != null;
	}

	public boolean hasNeighborType(Type type) {
		boolean ret = false;
		for (Terrain tile : neighboring_terrain)
			if (tile.type == type) {
				ret = true;
				break;
			}
		return ret;
	}

	public static int nNeighborsOfType(Terrain terr, Type type, int n) {
		int count = 0;
		for (Terrain tile : terr.neighboring_terrain)
			if (n != 1)
				count = nNeighborsOfType(tile, type, n - 1);
			else if (tile.type == type)
				count++;
		return count;
	}

	public static Terrain getNeighbor(Terrain terr, Type type) {
		for (Terrain tile : terr.neighboring_terrain)
			if (tile.type == type)
				return tile;
		return null;
	}

	public static Terrain getRandomNeighbor(Terrain terr, Type type) {
		ArrayList<Terrain> matches = new ArrayList<>();
		for (Terrain tile : terr.neighboring_terrain)
			if (tile.type == type)
				matches.add(tile);
		if (matches.size() < 1)
			return null;
		return matches.get(Util.random.nextInt(matches.size()));
	}
}