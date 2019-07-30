package buildings;

import java.io.IOException;

import javax.imageio.ImageIO;

import gof.Data;
import gof.Type;

// Cost 2 gold, 3 metal, 2 food
// Generates 1 warrior
public class Castle extends Building {

	public Castle() {
		super(Type.PLAIN);

		try {
			image = ImageIO.read(getClass().getClassLoader()
					.getResourceAsStream("resources/castle.png"));
		} catch (IOException e) {
			System.out.println("Error: unable to load image: " + toString());
		}
	}

	@Override
	public void generateIncome() {
		if (Data.player.gold >= 2 && Data.player.metal >= 3
				&& Data.player.food >= 2) {
			Data.player.gold -= 2;
			Data.player.metal -= 3;
			Data.player.food -= 2;
			Data.player.men++;
		}
	}

	@Override
	public boolean affordable() {
		if (Data.player.gold >= 10 && Data.player.lumber >= 15
				&& Data.player.metal >= 5) {
			Data.player.gold -= 10;
			Data.player.lumber -= 15;
			Data.player.metal -= 5;
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "castle";
	}

}
