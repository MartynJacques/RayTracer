public class Sphere extends Hittable {
	Vec3 center;
	double radius;
	Material mat;

	public Sphere() {
	}

	public Sphere(Vec3 cen, double r, Material m) {
		center = cen;
		radius = r;
		mat = m;
	}

	boolean hit(Ray r, double t_min, double t_max, HitRecord rec) {
		Vec3 oc = r.origin().sub(center);
		double a = Vec3.dot(r.direction(), r.direction());
		double b = Vec3.dot(oc, r.direction());
		double c = Vec3.dot(oc, oc) - radius * radius;
		double discriminant = b * b - a * c;
		if (discriminant > 0) {
			rec.mat = mat;
			rec.h = this;
			double temp = (-b - Math.sqrt(discriminant)) / a;
			if (temp < t_max && temp > t_min) {
				rec.t = temp;
				rec.p = r.point_at_parameter(rec.t);
				rec.normal = rec.p.sub(center).div(radius);
				return true;
			}
			temp = (-b + Math.sqrt(discriminant)) / a;
			if (temp < t_max && temp > t_min) {
				rec.t = temp;
				rec.p = r.point_at_parameter(rec.t);
				rec.normal = rec.p.sub(center).div(radius);
				return true;
			}
		}
		return false;
	}
}