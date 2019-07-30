package buildings;

import java.io.IOException;

import javax.imageio.ImageIO;

import gof.Data;
import gof.Type;

public class Hunter extends Building {

	public Hunter() {
		super(Type.FOREST);

		try {
			image = ImageIO.read(getClass().getClassLoader()
					.getResourceAsStream("resources/hunters_lodge.png"));
		} catch (IOException e) {
			System.out.println("Error: unable to load image: " + toString());
		}
	}

	@Override
	public void generateIncome() {
		if (Data.player.food >= 10) {
			Data.player.food += 3;
		}
	}

	@Override
	public boolean affordable() {
		if (Data.player.gold >= 1 && Data.player.metal >= 1 && Data.player.lumber >= 5) {
			Data.player.gold -= 1;
			Data.player.metal -= 1;
			Data.player.lumber -= 5;
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "hunter's lodge";
	}

}
