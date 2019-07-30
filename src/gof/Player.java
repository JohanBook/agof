package gof;

import java.awt.event.KeyEvent;

import buildings.Castle;
import buildings.Farm;
import buildings.Harbor;
import buildings.Lumbermill;
import buildings.Mine;
import buildings.TradingPost;

public class Player {
	private int xmax;
	private int ymax;

	public int x;
	public int y;

	// Resources
	public int gold = 2;
	public int lumber = 20;
	public int metal = 0;
	public int food = 3;

	// Forces
	public int men = 0;

	// Other
	private boolean canUseBoat = false; // if the player can walk on water

	// For initialization
	public void setBounds(int xmax, int ymax) {
		this.xmax = xmax;
		this.ymax = ymax;

		x = xmax / 2;
		y = ymax / 2;

		if (Data.grid.getTerrain(x, y).type == Type.WATER)
			canUseBoat = true;
	}

	// ================ Controls ================
	public void move(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.VK_UP || event.getKeyChar() == 'w') {
			if (y > 0)
				tryToMove(0, -1);// y--;
			return;
		}

		if (event.getKeyCode() == KeyEvent.VK_DOWN || event.getKeyChar() == 's') {
			if (y < ymax)
				tryToMove(0, 1);// y++;
			return;
		}

		if (event.getKeyCode() == KeyEvent.VK_LEFT || event.getKeyChar() == 'a') {
			if (x > 0)
				tryToMove(-1, 0);// x--;
			return;
		}

		if (event.getKeyCode() == KeyEvent.VK_RIGHT
				|| event.getKeyChar() == 'd') {
			if (x < xmax)
				tryToMove(1, 0);// x++;
			return;
		}

		// Building shortcuts
		if (event.getKeyChar() == 'c')
			Data.grid.build(new Castle());
		if (event.getKeyChar() == 'f')
			Data.grid.build(new Farm());
		if (event.getKeyChar() == 'h')
			Data.grid.build(new Harbor());
		if (event.getKeyChar() == 'l')
			Data.grid.build(new Lumbermill());
		if (event.getKeyChar() == 'm')
			Data.grid.build(new Mine());
		if (event.getKeyChar() == 't')
			Data.grid.build(new TradingPost());

		if (event.getKeyChar() == 'n')
			Core.restart();
		if(event.getKeyCode() == KeyEvent.VK_ENTER)
			Core.pause();

	}

	private void tryToMove(int dx, int dy) {
		if (canUseBoat
				|| (Data.grid.getTerrain(x + dx, y + dy).type != Type.WATER)) {
			x += dx;
			y += dy;
		}
	}
}
