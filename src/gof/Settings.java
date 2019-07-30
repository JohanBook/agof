package gof;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class Settings extends JFrame {

	// General parameters
	public static int grid_size = 129;// 100;
	public static final int spacing = 6; // interval between pixels
	public static final int animal_max_size = 8;
	public static final int update_time = 100;
	public static final int animal_update_time = 5000;
	public static final int terrain_update_time = 15000;

	// World generation
	public static int pathing_angle = 5;
	public static int sea_level = 5;
	public static int max_water_splashes = 150;
	public static int rock_level = 125;
	public static int swamp_spawn = 25; // chance of spawning swamp is 1/x
	public static int water_humidity = 50;
	public static int max_temp = 50; // the max temperature in Celsius
	public static int min_temp = -25; // the min temperature in Celsius

	// Building
	public static int construction_range = 50;

	// Create a GUI
	public Settings() {
		JPanel pnl = new JPanel();
		pnl.setLayout(new GridLayout(8, 2));

		// grid_size
		pnl.add(new JLabel("Grid size:"));
		JTextField txtGrid = new JTextField(5);
		txtGrid.setText("" + grid_size);
		txtGrid.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				grid_size = getInt(e.getSource());
			}
		});
		pnl.add(txtGrid);

		// sea_level
		pnl.add(new JLabel("Sea level:"));
		JTextField txtSea = new JTextField(5);
		txtSea.setText("" + sea_level);
		txtSea.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sea_level = getInt(e.getSource());
			}
		});
		pnl.add(txtSea);

		// rock_level
		pnl.add(new JLabel("Rock level:"));
		JTextField txtRock = new JTextField(5);
		txtRock.setText("" + rock_level);
		txtRock.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				rock_level = getInt(e.getSource());
			}
		});
		pnl.add(txtRock);

		// water splashes
		pnl.add(new JLabel("Water splashes:"));
		JTextField txtSplashes = new JTextField(5);
		txtSplashes.setText("" + max_water_splashes);
		txtSplashes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				max_water_splashes = getInt(e.getSource());
			}
		});
		pnl.add(txtSplashes);

		// max temperature
		pnl.add(new JLabel("Max temperature:"));
		JTextField txtMax = new JTextField(5);
		txtMax.setText("" + max_temp);
		txtMax.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				max_temp = getInt(e.getSource());
			}
		});
		pnl.add(txtMax);

		// min temperature
		pnl.add(new JLabel("Min temperature:"));
		JTextField txtMin = new JTextField(5);
		txtMin.setText("" + min_temp);
		txtMin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				min_temp = getInt(e.getSource());
			}
		});
		pnl.add(txtMin);

		add(pnl, BorderLayout.CENTER);

		setTitle("Settings");
		pack();
		setVisible(true);
	}

	private int getInt(Object obj) {
		String txt = ((JTextField) obj).getText();
		try {
			return Integer.parseInt(txt);
		} catch (Exception e) {
			System.out.println("Error: unable to parse int");
			return 0;
		}

	}
}
