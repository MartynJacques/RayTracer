import java.util.Arrays;
import java.util.Random;

//vector class

public class Vec3 {
	double[] e = new double[3];

	public Vec3() {
	}

	public Vec3(double e0, double e1, double e2) {
		e[0] = e0;
		e[1] = e1;
		e[2] = e2;
	}

	// coordinate
	public double x() {
		return e[0];
	}

	public double y() {
		return e[1];
	}

	public double z() {
		return e[2];
	}

	// color
	public double r() {
		return e[0];
	}

	public double g() {
		return e[1];
	}

	public double b() {
		return e[2];
	}

	public double length() {
		return Math.sqrt(e[0] * e[0] + e[1] * e[1] + e[2] * e[2]);
	}

	public double squared_length() {
		return e[0] * e[0] + e[1] * e[1] + e[2] * e[2];
	}

	// add
	public Vec3 add(Vec3 v2) {
		return new Vec3(e[0] + v2.e[0], e[1] + v2.e[1], e[2] + v2.e[2]);
	}

	// subtract
	public Vec3 sub(Vec3 v2) {
		return new Vec3(e[0] - v2.e[0], e[1] - v2.e[1], e[2] - v2.e[2]);
	}

	// divide
	public Vec3 div(Vec3 v2) {
		return new Vec3(e[0] / v2.e[0], e[1] / v2.e[1], e[2] / v2.e[2]);
	}

	public Vec3 div(double t) {
		return new Vec3(e[0] / t, e[1] / t, e[2] / t);
	}

	// multiply
	public Vec3 mul(Vec3 v2) {
		return new Vec3(e[0] * v2.e[0], e[1] * v2.e[1], e[2] * v2.e[2]);
	}

	public Vec3 mul(double t) {
		return new Vec3(e[0] * t, e[1] * t, e[2] * t);
	}

	// dot product
	public static double dot(Vec3 v1, Vec3 v2) {
		return v1.e[0] * v2.e[0] + v1.e[1] * v2.e[1] + v1.e[2] * v2.e[2];
	}

	// cross product
	public static Vec3 cross(Vec3 v1, Vec3 v2) {
		return new Vec3((v1.e[1] * v2.e[2] - v1.e[2] * v2.e[1]), -(v1.e[0] * v2.e[2] - v1.e[2] * v2.e[0]),
				(v1.e[0] * v2.e[1] - v1.e[1] * v2.e[0]));
	}

	public void unit() {
		double k = 1.0 / length();
		e[0] *= k;
		e[1] *= k;
		e[2] *= k;
	}

	public static Vec3 unit_vector(Vec3 v) {
		return v.div(v.length());
	}

	public void set(Vec3 v) {
		e[0] = v.e[0];
		e[1] = v.e[1];
		e[2] = v.e[2];
	}

	public static Vec3 random(int i, int j) {
		return new Vec3(randomDouble(i, j), randomDouble(i, j), randomDouble(i, j));
	}

	public static Vec3 randomInUnitSphereUnitVector() {
		return Vec3.unit_vector(randomInUnitSphere());
	}

	private static double randomDouble(int i, int j) {
		Random random = new Random();
		double randomValue = i + (j - i) * random.nextDouble();
		return randomValue;
	}

	public static Vec3 randomInUnitSphere() {
		while (true) {
			Vec3 p = Vec3.random(-1, 1);
			if (p.squared_length() >= 1)
				continue;
			return p;
		}
	}

	@Override
	public String toString() {
		return "Vec3 [e=" + Arrays.toString(e) + "]";
	}

}