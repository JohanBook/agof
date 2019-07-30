// Used to calculate tectonic activity

package gen;

import util.Point;
import util.Util;
import gof.Terrain;

public class FractalPath {

	public static Terrain[][] generate(Terrain[][] terrain) {
		int numPoints = Util.random.nextInt(15) + 1;
		Point[] points = new Point[numPoints];
		points[0] = new Point(Util.randInRange(), Util.randInRange());
		for (int n = 1; n < numPoints; n++) {
			boolean range = true;
			Point point;
			int count = 0;
			do {
				point = new Point(Util.randInRange(), Util.randInRange());

				// Check distance
				for (int k = 0; k < n; k++) {
					if (distance(point, points[k]) < 15)
						range = false;
					count++;
				}
			} while (!range && count < 1000);
			points[n] = point;
		}
		System.out.println("Tectonic points: " + numPoints);

		// For each point draw connection to all other points
		for (int n = 0; n < numPoints; n++)
			for (int k = n + 1; k < numPoints; k++) {
				terrain = drawLine(terrain, points[n], points[k]);
			}

		return terrain;
	}

	private static Terrain[][] drawLine(Terrain[][] terrain, Point p1, Point p2) {
		int threshold = 2;
		int count = 0; // to avoid infinite loops

		while (distance(p1.x, p1.y, p2.x, p2.y) > threshold && count < 1000) {
			if (Util.isInRange(p1.x) && Util.isInRange(p1.y))
				terrain[p1.x][p1.y].volcanic_activity += 100;

			int xs = (p2.x - p1.x);
			int ys = (p2.y - p1.y);

			double d = Math.sqrt(xs * xs + ys * ys);
			xs = (int) Math.ceil(0.5 * Util.random.nextGaussian() + xs / d);
			ys = (int) Math.ceil(0.5 * Util.random.nextGaussian() + ys / d);

			p1.x += xs;
			p1.y += ys;

			// System.out.println("x: " + p1.x + ", y: " + p1.y);

			count++;
		}

		return terrain;
	}

	private static double distance(Point p1, Point p2) {
		return Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2);
	}

	private static double distance(int x1, int y1, int x2, int y2) {
		return Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2);
	}
}
