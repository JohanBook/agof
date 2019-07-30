// Code for plotting that shit
package gui;

import gof.Data;
import gof.Settings;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import util.Point;
import buildings.Building;

@SuppressWarnings("serial")
public class PlotPanel extends JPanel {
	private BufferedImage terrain;

	public void drawTerrain() {
		terrain = new BufferedImage(Settings.grid_size * Settings.spacing,
				Settings.grid_size * Settings.spacing,
				BufferedImage.TYPE_INT_ARGB);
		Graphics og = terrain.createGraphics();
		for (int y = 0; y < Settings.grid_size; y++)
			for (int x = 0; x < Settings.grid_size; x++) {
				int r = 0;
				int g = 0;
				int b = 0;
				int h = (int) (50 * Math
						.ceil(Data.grid.getTerrain(x, y).height / 50));

				// Determine color from terrain
				switch (Data.grid.getTerrain(x, y).type) {
				case DESSERT:
					r += 255;
					g += 255;
					b += Data.grid.getTerrain(x, y).height;
					break;
				case FOREST:
					r += 0;
					g += 150 + h / 6;
					b += 0;
					break;
				case PLAIN:
					r += 150 + h / 6;
					g += 200;
					b += 150 + h / 6;
					break;
				case ROCK:
					r += 75 + h / 4;
					g += 75 + h / 4;
					b += 75 + h / 4;
					break;
				case WATER:
					r += 75 + Data.grid.getTerrain(x, y).height;
					g += 75 + Data.grid.getTerrain(x, y).height;
					b += 255;
					break;
				case ICE:
					r += 255;
					g += 255;
					b += 255;
					break;
				case SWAMP:
					r += 100;
					g += 150;
					b += 0;
					break;
				default:
					break;
				}

				if (Data.grid.getTerrain(x, y).hasAnimal()) {
					r = 250;
					g = 175;
					b = 0;
				}

				if (r >= 255)
					r = 255;
				if (g >= 255)
					g = 255;
				if (b >= 255)
					b = 255;

				if (r < 0)
					r = 0;
				if (g < 0)
					g = 0;
				if (b < 0)
					b = 0;

				og.setColor(new Color(r, g, b));
				og.fillRect(Settings.spacing * x, Settings.spacing * y,
						Settings.spacing, Settings.spacing);
			}
	}

	@Override
	protected void paintComponent(Graphics og) {
		super.paintComponent(og);

		//drawTerrain();

		// Draw background
		og.drawImage(terrain, 0, 0, null);

		// Paint each building
		for (Building building : Data.grid.buildings) {
			Point point = building.getPoint();
			try {
				og.drawImage(building.getImage(), Settings.spacing * point.x,
						Settings.spacing * point.y, Settings.spacing,
						Settings.spacing, null);

				og.drawImage(building.getImage(), Settings.spacing * point.x,
						Settings.spacing * point.y, null);

			} catch (Exception e) {
				System.out.println("Error: Failed to load static image");
			}
		}

		// Draw player position
		if (Data.player == null) {
			System.out.println("ERROR: Player reference can not be found");
			return;
		}
		og.setColor(new Color(255, 0, 0));
		og.fillRect(Settings.spacing * Data.player.x, Settings.spacing
				* Data.player.y, Settings.spacing, Settings.spacing);
	}
}