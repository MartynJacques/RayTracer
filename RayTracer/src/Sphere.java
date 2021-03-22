public class Sphere extends Hittable {
	Vec3 center;
	double radius;

	public Sphere() {
	}

	public Sphere(Vec3 cen, double r) {
		center = cen;
		radius = r;
	}

	boolean hit(Ray r, double t_min, double t_max, HitRecord rec) {
		Vec3 oc = r.origin().sub(center);
		var a = r.direction().squared_length();
		var halfB = Vec3.dot(oc, r.direction());
		var c = oc.squared_length() - (radius * radius);

		var discriminant = halfB * halfB - a * c;
		if (discriminant < 0)
			return false;
		var sqrtd = Math.sqrt(discriminant);

		var root = (-halfB - sqrtd) / a;
		if (root < t_min || t_max < root) {
			root = (-halfB + sqrtd) / a;
			if (root < t_min || t_max < root) {
				return false;
			}
		}

		rec.t = root;
		rec.p = r.point_at_parameter(rec.t);
		Vec3 outwardNormal = (rec.p.sub(center)).div(radius);
		rec.setFaceNormal(r, outwardNormal);

		return true;
	}
}