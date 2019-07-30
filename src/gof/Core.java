// The program core
package gof;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gui.GUI;

import javax.swing.Timer;

public class Core {
	public static boolean animals;

	private static Timer updateTimer;
	private static Timer resourceTimer;
	private static Timer terrainTimer;

	// Start simulation
	public static void start() {
		updateTimer.start();
		resourceTimer.start();
		terrainTimer.start();
	}

	// Stop simulation
	public static void pause() {
		updateTimer.stop();
		resourceTimer.stop();
		terrainTimer.stop();
		
		System.out.println("Game paused");
	}

	// New simulation
	public static void restart() {
		if (Data.gui != null) {
			Data.gui.setVisible(false);
			Data.gui.dispose();
		}

		Data.grid = new Grid();
		Data.gui = new GUI();
		Data.gui.updateBackground();
		Data.player = new Player();
		Data.player.setBounds(Settings.grid_size, Settings.grid_size);

		updateTimer = new Timer(Settings.update_time, new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				Data.gui.update();
			}
		});
		resourceTimer = new Timer(Settings.animal_update_time,
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						Data.grid.updateResources();
						if (animals) {
							try {
								Data.grid.updateAnimals();
							} catch (Exception ex) {
								System.out
										.println("ERROR: error in animal update");
							}
						}
					}
				});
		terrainTimer = new Timer(Settings.terrain_update_time,
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						Data.grid.updateTerrain();
						Data.gui.updateBackground();
						try {
							Data.grid.updateAnimals();
						} catch (Exception ex) {
							System.out
									.println("ERROR: error in terrain update");
						}
					}
				});
	}

	public static void main(String[] args) {
		if (args.length > 0) {
			animals = true;
			System.out.println(args[0]);
		}
		restart();
		start();
	}
}
