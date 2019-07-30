package buildings;

import java.io.IOException;

import javax.imageio.ImageIO;

import gof.Data;
import gof.Type;

public class Harbor extends Building {

	public Harbor() {
		super(Type.WATER);

		try {
			image = ImageIO.read(getClass().getClassLoader()
					.getResourceAsStream("resources/harbor.png"));
		} catch (IOException e) {
			System.out.println("Error: unable to load image: " + toString());
		}
	}

	@Override
	public boolean tile() {
		if (getTile() == requiredTile)
			if (Data.grid.getTerrain(x, y).hasNeighborType(Type.PLAIN)
					|| Data.grid.getTerrain(x, y).hasNeighborType(Type.DESSERT))
				return true;
			else {
				System.out.println(toString() + " must be build next to PLAIN or DESSERT");
				return false;
			}
		System.out
				.println("Unable to build " + toString() + " on " + getTile());
		return false;

	}

	@Override
	public void generateIncome() {
		Data.player.food++;
	}

	@Override
	public boolean affordable() {
		if (Data.player.lumber >= 8) {
			Data.player.lumber -= 8;
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "harbor";
	}

}
