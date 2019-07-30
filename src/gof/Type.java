package gof;

public enum Type {
	WATER(4), //
	ROCK(3), //
	PLAIN(4), //
	FOREST(7), //
	DESSERT(2), //
	ICE(2), //
	SWAMP(3); //

	private final int energy;

	Type(int energy) {
		this.energy = energy;
	}

	public int getEnergy() {
		return energy;
	}
}
