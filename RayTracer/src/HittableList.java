import java.util.List;

public class HittableList extends Hittable {

	List<Hittable> hittableList;

	HittableList() {
	}

	HittableList(List<Hittable> hittableList) {
		this.hittableList = hittableList;
	}

	/**
	 * Does the ray hit anything in the list?
	 */
	boolean hit(Ray ray, double tMin, double tMax, HitRecord hitRecord) {
		HitRecord tempHitRecord = new HitRecord();
		boolean hitAnyting = false;
		double closestSoFar = tMax;
		for (Hittable hittable : hittableList)
			if (hittable.hit(ray, tMin, closestSoFar, tempHitRecord)) {
				hitAnyting = true;
				closestSoFar = tempHitRecord.t;
				hitRecord.set(tempHitRecord);
			}
		return hitAnyting;
	}
}