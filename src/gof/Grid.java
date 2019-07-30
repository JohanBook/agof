package gof;

import gen.DiamondSquare;
import gen.FractalPath;
import gen.Terr;

import java.util.LinkedList;

import util.Util;
import buildings.Building;

public class Grid {
	private Terrain[][] terrain; // convention Terrain[x][y]
	public LinkedList<Animal> animals = new LinkedList<Animal>();
	public LinkedList<Animal> kill_list = new LinkedList<Animal>();
	public LinkedList<Animal> breed_list = new LinkedList<Animal>();
	public LinkedList<Building> buildings = new LinkedList<Building>();

	public Grid() {
		Settings.grid_size = (int) Math
				.pow(2, Util.min2pow(Settings.grid_size)) + 1;
		System.out.println("Grid: " + Settings.grid_size * Settings.grid_size
				+ " tiles");

		// Initialize the grid
		terrain = new Terrain[Settings.grid_size][Settings.grid_size];
		for (int y = 0; y < Settings.grid_size; y++)
			for (int x = 0; x < Settings.grid_size; x++)
				terrain[x][y] = new Terrain();

		setup_Neighbors();

		generate_Volcanic_Activity();
		generate_Heights();
		generate_Temperatures();
		Terr.generate_Terrain(terrain);
		if (Core.animals)
			generate_Animals();

		System.out
				.println("=========================================================================");
	}

	// ================ Links each tile to its neighbors ================
	private void setup_Neighbors() {
		// 8 neighboring terrains
		for (int y = 1; y < Settings.grid_size - 1; y++)
			for (int x = 1; x < Settings.grid_size - 1; x++) {
				Terrain[] neighboring_terrain = new Terrain[8];
				neighboring_terrain[0] = terrain[x + 1][y];
				neighboring_terrain[1] = terrain[x + 1][y + 1];
				neighboring_terrain[2] = terrain[x + 1][y - 1];
				neighboring_terrain[3] = terrain[x - 1][y];
				neighboring_terrain[4] = terrain[x - 1][y + 1];
				neighboring_terrain[5] = terrain[x - 1][y - 1];
				neighboring_terrain[6] = terrain[x][y + 1];
				neighboring_terrain[7] = terrain[x][y - 1];
				terrain[x][y].neighboring_terrain = neighboring_terrain;
			}

		// 5 neighboring terrains
		for (int y = 1; y < Settings.grid_size - 1; y++) {

			// x = 0
			Terrain[] neighboring_terrain = new Terrain[5];
			neighboring_terrain[0] = terrain[0][y + 1];
			neighboring_terrain[1] = terrain[0][y - 1];
			neighboring_terrain[2] = terrain[1][y];
			neighboring_terrain[3] = terrain[1][y + 1];
			neighboring_terrain[4] = terrain[1][y - 1];
			terrain[0][y].neighboring_terrain = neighboring_terrain;

			// y = 0
			neighboring_terrain = new Terrain[5];
			neighboring_terrain[0] = terrain[y + 1][0];
			neighboring_terrain[1] = terrain[y - 1][0];
			neighboring_terrain[2] = terrain[y][1];
			neighboring_terrain[3] = terrain[y + 1][1];
			neighboring_terrain[4] = terrain[y - 1][1];
			terrain[y][0].neighboring_terrain = neighboring_terrain;

			// x = Settings.grid_size
			neighboring_terrain = new Terrain[5];
			neighboring_terrain[0] = terrain[Settings.grid_size - 1][y + 1]; // error
			neighboring_terrain[1] = terrain[Settings.grid_size - 1][y - 1];
			neighboring_terrain[2] = terrain[Settings.grid_size - 2][y];
			neighboring_terrain[3] = terrain[Settings.grid_size - 2][y + 1];
			neighboring_terrain[4] = terrain[Settings.grid_size - 2][y - 1];
			terrain[Settings.grid_size - 1][y].neighboring_terrain = neighboring_terrain;

			// y = Settings.grid_size
			neighboring_terrain = new Terrain[5];
			neighboring_terrain[0] = terrain[y + 1][Settings.grid_size - 1];
			neighboring_terrain[1] = terrain[y - 1][Settings.grid_size - 1];
			neighboring_terrain[2] = terrain[y][Settings.grid_size - 2];
			neighboring_terrain[3] = terrain[y + 1][Settings.grid_size - 2];
			neighboring_terrain[4] = terrain[y - 1][Settings.grid_size - 2];
			terrain[y][Settings.grid_size - 1].neighboring_terrain = neighboring_terrain;
		}

		// 3 neighboring terrains
		{
			// x = y = 0
			Terrain[] neighboring_terrain = new Terrain[3];
			neighboring_terrain[0] = terrain[0][1];
			neighboring_terrain[1] = terrain[1][0];
			neighboring_terrain[2] = terrain[1][1];
			terrain[0][0].neighboring_terrain = neighboring_terrain;

			// x = 0, y = Settings.grid_size
			neighboring_terrain = new Terrain[3];
			neighboring_terrain[0] = terrain[0][Settings.grid_size - 2];
			neighboring_terrain[1] = terrain[1][Settings.grid_size - 1];
			neighboring_terrain[2] = terrain[1][Settings.grid_size - 2];
			terrain[0][Settings.grid_size - 1].neighboring_terrain = neighboring_terrain;

			// x = Settings.grid_size, y = 0
			neighboring_terrain = new Terrain[3];
			neighboring_terrain[0] = terrain[Settings.grid_size - 2][0];
			neighboring_terrain[1] = terrain[Settings.grid_size - 1][1];
			neighboring_terrain[2] = terrain[Settings.grid_size - 2][1];
			terrain[Settings.grid_size - 1][0].neighboring_terrain = neighboring_terrain;

			// x = y = Settings.grid_size
			neighboring_terrain = new Terrain[3];
			neighboring_terrain[0] = terrain[Settings.grid_size - 2][Settings.grid_size - 1];
			neighboring_terrain[1] = terrain[Settings.grid_size - 1][Settings.grid_size - 2];
			neighboring_terrain[2] = terrain[Settings.grid_size - 2][Settings.grid_size - 2];
			terrain[Settings.grid_size - 1][Settings.grid_size - 1].neighboring_terrain = neighboring_terrain;
		}
	}

	// ////////////// Sets up a pattern of volcanic activity ////////////////
	private void generate_Volcanic_Activity() {
		terrain = FractalPath.generate(terrain);

		// Smoothing
		for (int y = 0; y < Settings.grid_size; y++)
			for (int x = 0; x < Settings.grid_size; x++) {
				double sum = 0;
				for (Terrain terr : terrain[x][y].neighboring_terrain)
					sum += terr.volcanic_activity;
				terrain[x][y].volcanic_activity = (int) (sum / terrain[x][y].neighboring_terrain.length);
			}
	}

	// ////////////// Sets up height differences ////////////////
	private void generate_Heights() {
		terrain = DiamondSquare.gen(terrain);

		// Smooth out
		for (int y = 0; y < Settings.grid_size; y++)
			for (int x = 0; x < Settings.grid_size; x++) {
				double sum = 0;
				for (Terrain terr : terrain[x][y].neighboring_terrain)
					sum += terr.height;
				terrain[x][y].height = (int) (sum / terrain[x][y].neighboring_terrain.length);
			}

		// Add some minor peaks
		int peaks = Util.random
				.nextInt(Settings.grid_size * Settings.grid_size);
		for (int peak = 0; peak < peaks; peak++)
			terrain[Util.randInRange()][Util.randInRange()].height += Util.random
					.nextInt(10);
		System.out.println("Peaks generated: " + peaks);
	}

	// /////////////// Generate temperatures ////////////////
	private void generate_Temperatures() {

		// Generate a position-based temperature
		double slope = 2 * (Settings.max_temp - Settings.min_temp)
				/ Settings.grid_size;
		for (int y = 0; y < Settings.grid_size; y++)
			for (int x = 0; x < Settings.grid_size; x++) {
				double temperature = Settings.max_temp - slope
						* Math.abs(y - 0.5 * Settings.grid_size);
				terrain[x][y].temperature = (int) temperature
						+ Util.random.nextInt(2);

			}

		// TODO: Include volcanic activity
		for (int y = 0; y < Settings.grid_size; y++)
			for (int x = 0; x < Settings.grid_size; x++) {
				terrain[x][y].temperature += (int) (0.1 * terrain[x][y].volcanic_activity);
			}

		// TODO: Simulate streams and weather

		// Smoothing
		for (int y = 0; y < Settings.grid_size; y++)
			for (int x = 0; x < Settings.grid_size; x++) {
				double sum = 0;
				for (Terrain terr : terrain[x][y].neighboring_terrain)
					sum += terr.temperature;
				terrain[x][y].temperature = (int) (sum / terrain[x][y].neighboring_terrain.length);
			}

	}

	// ================ Generate a set of animals ================
	private void generate_Animals() {
		int numAnimals = 2 * Util.random.nextInt(Settings.grid_size);
		for (int n = 0; n < numAnimals; n++) {
			int x = Util.random.nextInt(Settings.grid_size);
			int y = Util.random.nextInt(Settings.grid_size);
			Animal creature = new Animal(terrain[x][y], this);
			terrain[x][y].animal = creature;
			animals.add(creature);
		}
		System.out.println("Animals: " + numAnimals);
	}

	// Update each animal
	public void updateAnimals() {
		if (animals == null)
			return;
		for (Animal creature : animals)
			creature.update();
		for (Animal creature : kill_list)
			animals.remove(creature);
		for (Animal creature : breed_list)
			animals.add(creature);

		// Clear the lists
		kill_list = new LinkedList<Animal>();
		breed_list = new LinkedList<Animal>();
	}

	// ================ Update the terrain ================
	public void updateTerrain() {

		// Update energy
		for (int y = 0; y < Settings.grid_size; y++)
			for (int x = 0; x < Settings.grid_size; x++)
				if (terrain[x][y].energy < terrain[x][y].type.getEnergy())
					terrain[x][y].energy++;

		// Terrain growth
		for (int y = 0; y < Settings.grid_size; y++)
			for (int x = 0; x < Settings.grid_size; x++) {
				Type type = terrain[x][y].type;
				int neighbor = 0;
				Terrain change_tile = null;
				for (Terrain tile : terrain[x][y].neighboring_terrain)
					if (tile.type == type)
						neighbor++;
					else
						change_tile = tile;
				if (neighbor > 2 && change_tile != null
						&& Util.random.nextInt(5) < 1) {

					// Special case if any of them is water
					if (change_tile.type == Type.WATER || type == Type.WATER) {
						if (Util.random.nextInt(10) < 1) {
							change_tile.type = type;
						}
					} else
						change_tile.type = type;
				}
			}

		// Add some randomness
		int points = Util.random.nextInt(Settings.grid_size) + 1;
		for (int point = 0; point < points; point++) {
			int x = Util.random.nextInt(Settings.grid_size);
			int y = Util.random.nextInt(Settings.grid_size);
			if (terrain[x][y].type == Type.WATER)
				continue;
			int c = Util.random.nextInt(3);
			Type type = Type.WATER;
			switch (c) {
			case 0:
				type = Type.FOREST;
				break;
			case 1:
				type = Type.PLAIN;
				break;
			case 2:
				type = Type.DESSERT;
				break;
			}
			terrain[x][y].type = type;
			x += Util.gauss(1);
			y += Util.gauss(1);
			if (x < 0 || y < 0 || x >= Settings.grid_size
					|| y >= Settings.grid_size)
				continue;
			terrain[x][y].type = type;
		}
	}

	public void updateResources() {
		for (Building building : Data.grid.buildings) {
			building.generateIncome();
		}
	}

	public Terrain getTerrain(int x, int y) {
		return terrain[x][y];
	}

	// ================ Buildings ================
	public void build(Building building) {
		if (building.tile())
			if (buildingInRange(building, Settings.construction_range))
				if (building.affordable()) {
					buildings.add(building);
					building.init();
				} else
					System.out.println("Insufficient resources to build a "
							+ building.toString());
			else
				System.out
						.println("You cannot build this far away from other buildings");
	}

	public boolean buildingInRange(Building building, int range) {
		if (buildings.isEmpty())
			return true;
		for (Building bd : buildings)
			if (bd != building
					&& Util.distance(building.getPoint(), bd.getPoint()) <= range)
				return true;
		return false;
	}
}
