package gui;

import gof.Data;
import gof.Settings;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import buildings.Castle;
import buildings.Farm;
import buildings.Harbor;
import buildings.Hunter;
import buildings.Lumbermill;
import buildings.Mine;
import buildings.TradingPost;

@SuppressWarnings("serial")
public class GUI extends JFrame {
	private PlotPanel pnlPlot;

	private JLabel lblInfo = new JLabel();

	public GUI() {
		pnlPlot = new PlotPanel();

		final JPanel pnlButtons = new JPanel();
		final JPanel pnlConstruct = new JPanel();
		final JPanel pnlMenu = new JPanel();
		final JButton btnConstruct = new JButton("Construct");
		final JButton btnResearch = new JButton("Research");
		// final JButton btnRemove = new JButton("Remove");
		btnConstruct.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pnlConstruct.setVisible(true);
				pnlMenu.setVisible(false);
			}

		});
		pnlMenu.add(btnConstruct);
		pnlMenu.add(btnResearch);
		
		pnlButtons.add(pnlMenu);

		// Construct buttons
		JButton btnFarm = new JButton("Farm");
		JButton btnMine = new JButton("Mine");
		JButton btnLumb = new JButton("Lumbermill");
		JButton btnHarb = new JButton("Harbor");
		JButton btnTrad = new JButton("Trading Post");
		JButton btnHunt = new JButton("Hunter's lodge");
		JButton btnCast = new JButton("Castle");
		JButton btnCanc = new JButton("Cancel");
		btnFarm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Data.grid.build(new Farm());
				pnlConstruct.setVisible(false);
				pnlMenu.setVisible(true);
			}
		});
		btnMine.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Data.grid.build(new Mine());
				pnlConstruct.setVisible(false);
				pnlMenu.setVisible(true);
			}
		});
		btnLumb.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Data.grid.build(new Lumbermill());
				pnlConstruct.setVisible(false);
				pnlMenu.setVisible(true);
			}
		});
		btnHarb.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Data.grid.build(new Harbor());
				pnlConstruct.setVisible(false);
				pnlMenu.setVisible(true);
			}
		});
		btnTrad.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Data.grid.build(new TradingPost());
				pnlConstruct.setVisible(false);
				pnlMenu.setVisible(true);
			}
		});
		btnHunt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Data.grid.build(new Hunter());
				pnlConstruct.setVisible(false);
				pnlMenu.setVisible(true);
			}
		});
		btnCast.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Data.grid.build(new Castle());
				pnlConstruct.setVisible(false);
				pnlMenu.setVisible(true);
			}
		});
		btnCanc.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pnlConstruct.setVisible(false);
				pnlMenu.setVisible(true);
			}
		});

		// Tooltip
		btnFarm.setToolTipText("Costs: 5 lumber, Gives: 1 food");
		btnLumb.setToolTipText("Costs: 7 lumber, Gives: 1 lumber");
		btnHarb.setToolTipText("Costs: 8 lumber, Gives: 1 food");
		btnTrad.setToolTipText("Costs: 7 lumber, Gives: 1 gold");
		btnMine.setToolTipText("Costs: 5 lumber and 1 gold, Gives: 1 metal");
		btnHunt.setToolTipText("Costs: 5 lumber, 1 gold and 1 metal, Gives: 3 food if food > 10");
		btnCast.setToolTipText("Costs: 15 lumber, 10 gold and 5 metal, Gives: 1 soldier");

		pnlConstruct.add(btnFarm);
		pnlConstruct.add(btnLumb);
		pnlConstruct.add(btnHarb);
		pnlConstruct.add(btnTrad);
		pnlConstruct.add(btnMine);
		pnlConstruct.add(btnHunt);
		pnlConstruct.add(btnCast);
		pnlConstruct.add(btnCanc);

		pnlConstruct.setVisible(false);
		pnlButtons.add(pnlConstruct);

		// Labels
		JPanel pnlLabels = new JPanel();
		pnlLabels.add(lblInfo);
		add(pnlButtons, BorderLayout.SOUTH);
		add(pnlLabels, BorderLayout.NORTH);

		// Key bindings
		pnlPlot.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent event) {
				Data.player.move(event);
			}
		});

		// Stuff
		pnlPlot.setPreferredSize(new Dimension(Settings.spacing
				* Settings.grid_size, Settings.spacing * Settings.grid_size));
		add(pnlPlot, BorderLayout.CENTER);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Empire builder");
		pack();
		setVisible(true);
		pnlPlot.requestFocus();
	}

	public void update() {
		pnlPlot.repaint();
		lblInfo.setText("Gold: " + Data.player.gold + "  Lumber: "
				+ Data.player.lumber + "  Metal: " + Data.player.metal
				+ "  Food: " + Data.player.food + "  Soldiers: "
				+ Data.player.men);
		pnlPlot.requestFocus();
	}
	
	public void updateBackground(){
		pnlPlot.drawTerrain();
	}

}
