public class HittableList extends Hittable {
	int list_size;
	Hittable[] list;

	HittableList() {
	}

	HittableList(Hittable[] l, int n) {
		list = l;
		list_size = n;
	}

	boolean hit(Ray r, double t_min, double t_max, HitRecord rec) {
		HitRecord temp_rec = new HitRecord();
		boolean hit_anything = false;
		double closest_so_far = t_max;
		for (int i = 0; i < list_size; i++) {
			if (list[i].hit(r, t_min, closest_so_far, temp_rec)) {
				hit_anything = true;
				closest_so_far = temp_rec.t;
				rec.set(temp_rec);
			}
		}
		return hit_anything;
	}
}