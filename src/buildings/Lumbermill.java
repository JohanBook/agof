package buildings;

import java.io.IOException;

import javax.imageio.ImageIO;

import gof.Data;
import gof.Type;

public class Lumbermill extends Building {

	public Lumbermill() {
		super(Type.FOREST);
		try {
			image = ImageIO.read(getClass().getClassLoader()
					.getResourceAsStream("resources/lumbermill.png"));
		} catch (IOException e) {
			System.out.println("Error: unable to load image: " + toString());
		}
	}

	@Override
	public void generateIncome() {
		if (Data.player.food >= 1) {
			Data.player.food -= 1;
			Data.player.lumber++;
		}
	}

	@Override
	public boolean affordable() {
		if (Data.player.lumber >= 7) {
			Data.player.lumber -= 7;
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "lumbermill";
	}

}
