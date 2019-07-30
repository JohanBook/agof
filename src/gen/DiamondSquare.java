// http://stackoverflow.com/questions/2755750/diamond-square-algorithm
package gen;

import java.util.Random;

import util.Util;
import gof.Settings;
import gof.Terrain;

public class DiamondSquare {
	public static Terrain[][] gen(Terrain[][] terrain) {
		double[][] data = genh(250);
		for (int y = 0; y < Settings.grid_size; y++)
			for (int x = 0; x < Settings.grid_size; x++)
				terrain[x][y].height = (int) data[x][y];
		return terrain;
	}

	public static double[][] genh(double h) {
		final int DATA_SIZE = Settings.grid_size;
		final double SEED = 0;

		double[][] data = new double[DATA_SIZE][DATA_SIZE];
		data[0][0] = data[0][DATA_SIZE - 1] = data[DATA_SIZE - 1][0] = data[DATA_SIZE - 1][DATA_SIZE - 1] = SEED;
		Random r = Util.random;

		for (int sideLength = DATA_SIZE - 1; sideLength >= 2; sideLength /= 2, h /= 2.0) {
			int halfSide = sideLength / 2;

			for (int x = 0; x < DATA_SIZE - 1; x += sideLength) {
				for (int y = 0; y < DATA_SIZE - 1; y += sideLength) {
					double avg = data[x][y] + data[x + sideLength][y]
							+ data[x][y + sideLength]
							+ data[x + sideLength][y + sideLength];
					avg /= 4.0;

					data[x + halfSide][y + halfSide] = avg
							+ (r.nextDouble() * 2 * h) - h;
				}
			}

			for (int x = 0; x < DATA_SIZE - 1; x += halfSide) {
				for (int y = (x + halfSide) % sideLength; y < DATA_SIZE - 1; y += sideLength) {
					double avg = data[(x - halfSide + DATA_SIZE - 1)
							% (DATA_SIZE - 1)][y]
							+ data[(x + halfSide) % (DATA_SIZE - 1)][y]
							+ data[x][(y + halfSide) % (DATA_SIZE - 1)]
							+ data[x][(y - halfSide + DATA_SIZE - 1)
									% (DATA_SIZE - 1)];

					avg /= 4.0;
					avg = avg + (r.nextDouble() * 2 * h) - h;
					data[x][y] = avg;
					if (x == 0)
						data[DATA_SIZE - 1][y] = avg;
					if (y == 0)
						data[x][DATA_SIZE - 1] = avg;
				}
			}
		}
		return data;
	}
}
