package buildings;

import java.io.IOException;

import javax.imageio.ImageIO;

import gof.Data;
import gof.Type;

public class Farm extends Building {

	public Farm() {
		super(Type.PLAIN);
		
		try {
			image = ImageIO.read(getClass().getClassLoader()
					.getResourceAsStream("resources/farm.png"));
		} catch (IOException e) {
			System.out.println("Error: unable to load image: " + toString());
		}
	}

	@Override
	public void generateIncome() {
		Data.player.food++;
	}

	@Override
	public boolean affordable() {
		if (Data.player.lumber >= 5) {
			Data.player.lumber -= 5;
			return true;
		}
		return false;
	}
	
	@Override
	public String toString() {
		return "farm";
	}

}
