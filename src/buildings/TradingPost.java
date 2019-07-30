package buildings;

import java.io.IOException;

import javax.imageio.ImageIO;

import gof.Data;
import gof.Type;

public class TradingPost extends Building {

	public TradingPost() {
		super(Type.DESSERT);

		try {
			image = ImageIO.read(getClass().getClassLoader()
					.getResourceAsStream("resources/trading_post.png"));
		} catch (IOException e) {
			System.out.println("Error: unable to load image: " + toString());
		}
	}

	@Override
	public void generateIncome() {
		if (Data.player.food >= 1) {
			Data.player.food -= 1;
			Data.player.gold++;
		}
	}

	@Override
	public boolean affordable() {
		if (Data.player.lumber >= 6) {
			Data.player.lumber -= 6;
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "trading post";
	}

}
