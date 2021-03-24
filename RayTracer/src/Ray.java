public class Ray {
	Vec3 origin;
	Vec3 direction;

	public Ray() {
	}

	public Ray(Vec3 a, Vec3 b) {
		origin = a;
		direction = b;
	}

	public Vec3 origin() {
		return origin;
	}

	public Vec3 direction() {
		return direction;
	}

	public Vec3 point_at_parameter(double t) {
		return origin.add(direction.mul(t));
	}

	public void set(Ray r) {
		origin = r.origin;
		direction = r.direction;
	}
}