/**
 * Proxy for Vec3 Coordinates
 */
public class Coordinate extends Vec3 {

	private Vec3 vec;

	public Coordinate() {
		vec = new Vec3();
	}

	public Coordinate(double e0, double e1, double e2) {
		vec = new Vec3(e0, e1, e2);
	}

	public Coordinate(Vec3 vec) {
		this.vec = vec;
	}

	public double length() {
		return vec.length();
	}

	public double squaredLength() {
		return vec.squaredLength();
	}

	public Coordinate add(Vec3 v2) {
		return new Coordinate(vec.add(v2));
	}

	public Coordinate sub(Vec3 v2) {
		return new Coordinate(vec.sub(v2));
	}

	public Coordinate div(Vec3 v2) {
		return new Coordinate(vec.div(v2));
	}

	public Coordinate div(double t) {
		return new Coordinate(vec.div(t));
	}

	public Coordinate mul(Vec3 v2) {
		return new Coordinate(vec.mul(v2));
	}

	public Coordinate mul(double t) {
		return new Coordinate(vec.mul(t));
	}

}
