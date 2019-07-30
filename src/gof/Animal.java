// Contains code for the behavior of the animal
package gof;

import util.Util;

public class Animal {
	Grid grid; // a connection to the grid for killing/breeding purposes

	private int size;
	public boolean isPredator;
	private boolean gregarious_behavior;
	private boolean rival_behavior;
	private int breeding_rate;
	private boolean isLandAnimal;
	private boolean isAmphibie;
	private int speed; // tiles per round

	public int life;
	public int required_energy;
	public int preferred_temperature;
	public Terrain terrain;

	public Animal(Terrain terrain, Grid grid) {

		// Initialization
		changeTile(terrain); // set terrain-animal link
		this.grid = grid;

		size = Util.random.nextInt(Settings.animal_max_size) + 1;
		isPredator = Util.random.nextBoolean();
		gregarious_behavior = Util.random.nextBoolean();
		rival_behavior = Util.random.nextBoolean();
		isAmphibie = Util.random.nextBoolean();
		breeding_rate = Util.random.nextInt(4) + 1;

		// If land animal or not
		if (terrain.type == Type.WATER)
			isLandAnimal = false;
		else
			isLandAnimal = true;

		speed = Util.random.nextInt(3) + 1;

		// Calculations
		required_energy = size * breeding_rate;
		life = size;
		preferred_temperature = terrain.temperature + Util.gauss(5);
	}

	// ================ Behavior ================
	// This code parts contains the behavior of the animal.
	public void update() {
		if (terrain == null) {
			kill();
			return;
		}

		// ==== Carnivore ====
		if (isPredator) {

			// Look for animal in neighboring tiles
			Terrain[] neighboring_terrain = terrain.neighboring_terrain;
			Terrain foody_tile = null;
			for (Terrain tile : neighboring_terrain)
				if (tile.hasAnimal())
					foody_tile = tile;

			if (foody_tile == null) {
				life--;
				move();
			} else {
				// Check if possible to kill prey TODO: include flock behavior
				if (foody_tile.animal.size < size + 1) {
					foody_tile.animal.kill();
					changeTile(foody_tile);
					life++;
				} else
					life--;
			}

		}

		// ==== Herbivore ====
		else {

			// Check if enough plants to eat
			if (terrain.energy >= required_energy) {
				terrain.energy -= required_energy;
				life++; // more seldom?
			} else {
				terrain.energy = 0;
				life--;
				move(speed);
			}
		}

		// ==== Reproduce ====
		if (life >= size)
			breed();

		// Punish animals in wrong terrain
		// TODO: include swimming_capability
		if (terrain == null) {
			kill();
			return;
		}

		if (terrain.type == Type.WATER && isLandAnimal && !isAmphibie)
			life--;
		if (terrain.type != Type.WATER && !isLandAnimal && !isAmphibie)
			life--;

		// Punish animals in wrong temperal zone
		// if (Math.abs(terrain.temperature - preferred_temperature) > 10)
		// life--;

		// Kill animals if they have no life
		if (life <= 0)
			kill();

	}

	// Force the animal to move n times
	public void move(int n) {
		for (int i = 0; i < n; i++)
			move();
	}

	// Force the animal to move
	public void move() {
		if (terrain == null) {
			kill();
			return;
		}

		Terrain[] neighboring_terrain = terrain.neighboring_terrain;
		Terrain foody_tile = neighboring_terrain[0];
		for (Terrain tile : neighboring_terrain)
			if (tile.energy >= foody_tile.energy)
				foody_tile = tile;

		// Investigate foody tile
		if (foody_tile.isEmpty()) {
			changeTile(foody_tile);
		} else {
			if (rival_behavior)
				fight(this, foody_tile.animal).terrain = foody_tile;
		}

		// kill neighboring animals
		if (rival_behavior) {

		}

		// move towards other animals
		if (gregarious_behavior || isPredator) {

		}
	}

	// Kill the animal
	public void kill() {
		grid.kill_list.add(this);
		changeTile(null);
	}

	// Force the animal to breed
	// TODO: complete this code part
	public void breed() {
		if (terrain == null) {
			kill();
			return;
		}

		Animal offspring = new Animal(terrain.neighboring_terrain[0], grid); // TODO:
																				// randomize
		offspring.breeding_rate = breeding_rate;
		offspring.gregarious_behavior = gregarious_behavior;
		offspring.isAmphibie = isAmphibie;
		offspring.isLandAnimal = isLandAnimal;
		offspring.isPredator = isPredator;
		offspring.preferred_temperature = preferred_temperature;
		offspring.rival_behavior = rival_behavior;
		grid.breed_list.add(offspring);
	}

	// Fight yo
	public static Animal fight(Animal a, Animal b) {
		if (a.size > b.size + 1) {
			b.kill();
			return a;
		}
		if (b.size > a.size + 1) {
			a.kill();
			return b;
		}
		if (a.life * a.size > b.life * b.size) {
			if (b.size * b.life != 0)
				a.life -= (int) (a.size / b.size * b.life);
			b.kill();
			return a;
		}
		if (b.life * b.size > a.life * a.size) {
			if (a.size * a.life != 0)
				b.life -= (int) (b.size / a.size * a.life);
			a.kill();
			return a;
		}

		// Randomize winner?
		a.life -= b.life;
		b.kill();

		return a;
	}

	// A method to change the terrain-animal link
	public void changeTile(Terrain tile) {
		if (terrain != null)
			terrain.animal = null;
		if (tile != null)
			tile.animal = this;
		terrain = tile;
	}

}