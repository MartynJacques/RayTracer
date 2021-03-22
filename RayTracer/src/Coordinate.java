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

	public double x() {
		return vec.get(0);
	}

	public double y() {
		return vec.get(1);
	}

	public double z() {
		return vec.get(2);
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
