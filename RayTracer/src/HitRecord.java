public class HitRecord {
	double t;
	Vec3 p;
	Vec3 normal;
	boolean frontFace;

	public void set(HitRecord hr) {
		t = hr.t;
		p = hr.p;
		normal = hr.normal;
	}

	void setFaceNormal(Ray ray, Vec3 outwardNormal) {
		frontFace = Vec3.dot(ray.direction(), outwardNormal) < 0;
		normal = frontFace ? outwardNormal : new Vec3(-outwardNormal.x(), -outwardNormal.y(), -outwardNormal.z());
	}
}