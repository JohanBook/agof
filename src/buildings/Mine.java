package buildings;

import java.io.IOException;

import javax.imageio.ImageIO;

import gof.Data;
import gof.Type;

public class Mine extends Building {

	public Mine() {
		super(Type.ROCK);

		try {
			image = ImageIO.read(getClass().getClassLoader()
					.getResourceAsStream("resources/mine.png"));
		} catch (IOException e) {
			System.out.println("Error: unable to load image: " + toString());
		}
	}

	@Override
	public void generateIncome() {
		if (Data.player.food >= 2) {
			Data.player.food -= 2;
			Data.player.metal++;
		}
	}

	@Override
	public boolean affordable() {
		if (Data.player.gold >= 1 && Data.player.lumber >= 5) {
			Data.player.gold -= 1;
			Data.player.lumber -= 5;
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "mine";
	}

}
