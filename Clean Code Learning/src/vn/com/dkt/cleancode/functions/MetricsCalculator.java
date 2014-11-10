package vn.com.dkt.cleancode.functions;

import java.awt.Point;

public class MetricsCalculator {
	public double xProjection(Point p1, Point p2) {
		assert p1 != null : "p1 should not be null";
		assert p2 != null : "p2 should not be null";
		return (p2.x - p1.x) * 1.5;
	}

	public void a() {
		xProjection(null, null);
	}

}