package buildings;

import java.awt.Image;

import util.Point;
import gof.Data;
import gof.Type;

// Abstract class describing a building
public abstract class Building {
	
	// Coordinates
	protected int x;
	protected int y;

	protected static Type requiredTile;
	protected static Image image;

	Building(Type requiredTile) {
		x = Data.player.x;
		y = Data.player.y;
		Building.requiredTile = requiredTile;
	}

	public void init() {
		Data.grid.getTerrain(x, y).building = this;
	}

	public Point getPoint() {
		return new Point(x, y);
	}

	public boolean tile() {
		if (getTile() == requiredTile)
			return true;
		System.out
				.println("Unable to build " + toString() + " on " + getTile());
		return false;

	}

	public abstract void generateIncome();

	public Type getTile() {
		return Data.grid.getTerrain(x, y).type;
	}

	public abstract boolean affordable();

	@Override
	public abstract String toString();

	public Image getImage() {
		return image;
	}

}
