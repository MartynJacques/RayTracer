/**
 * Proxy for Vec3 Colours
 */
public class Colour extends Vec3 {

	private Vec3 vec;

	public Colour() {
		vec = new Vec3();
	}

	public Colour(double e0, double e1, double e2) {
		vec = new Vec3(e0, e1, e2);
	}

	public Colour(Vec3 vec) {
		this.vec = vec;
	}

	public double length() {
		return vec.length();
	}

	public double squaredLength() {
		return vec.squaredLength();
	}

	public Colour add(Vec3 v2) {
		return new Colour(vec.add(v2));
	}

	public Colour sub(Vec3 v2) {
		return new Colour(vec.sub(v2));
	}

	public Colour div(Vec3 v2) {
		return new Colour(vec.div(v2));
	}

	public Colour div(double t) {
		return new Colour(vec.div(t));
	}

	public Colour mul(Vec3 v2) {
		return new Colour(vec.mul(v2));
	}

	public Colour mul(double t) {
		return new Colour(vec.mul(t));
	}

}
