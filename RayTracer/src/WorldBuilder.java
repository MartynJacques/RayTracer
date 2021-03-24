import java.util.ArrayList;
import java.util.List;

public class WorldBuilder {

	public static HittableList build() {
		Material materialGround = new Lambertian(new Vec3(0.5, 0.5, 0.5));
		Material diffuserRed = new Lambertian(new Vec3(0.9, 0.0, 0.0));
		Material diffuserBlue = new Lambertian(new Vec3(0.1, 0.2, 0.5));
		Material diffuserGreen = new Lambertian(new Vec3(0.1, 0.9, 0.1));
		Material glassBall = new Dielectric(new Vec3(1, 1, 1), 1.5);
		Material matteMetal = new Metal(new Vec3(0.8, 0.6, 0.2), 1);
		Material materialMetalShinyGold = new Metal(new Vec3(0.8, 0.6, 0.2), 0);
		Material materialMetalShinySilver = new Metal(new Vec3(0.75, 0.75, 0.75), 0.05);

		List<Hittable> hittableList = new ArrayList<>();

		// Ground
		hittableList.add(new Sphere(new Vec3(0, -100.5, -1), 100, materialGround));

		// Big
		hittableList.add(new Sphere(new Vec3(-1, 0.7, 5), 1.5, glassBall));
		hittableList.add(new Sphere(new Vec3(-3, 1, 2), 1.5, materialMetalShinyGold));
		hittableList.add(new Sphere(new Vec3(4, 1.5, 1), 2, materialMetalShinySilver));

		// Small
		hittableList.add(new Sphere(new Vec3(-2, 0, -1), 0.5, diffuserBlue));
		hittableList.add(new Sphere(new Vec3(0, 0, -1), 0.5, diffuserRed));
		hittableList.add(new Sphere(new Vec3(-1, 0, -1), 0.5, glassBall));
		hittableList.add(new Sphere(new Vec3(3, 0, -1), 0.5, matteMetal));
		hittableList.add(new Sphere(new Vec3(4, 0, -3), 0.5, diffuserBlue));
		hittableList.add(new Sphere(new Vec3(2.5, 0, -5), 0.5, diffuserBlue));
		hittableList.add(new Sphere(new Vec3(1, 0, -4), 0.5, glassBall));
		hittableList.add(new Sphere(new Vec3(1, -0.1, 4), 0.5, diffuserRed));
		hittableList.add(new Sphere(new Vec3(1.5, 0, 1), 0.5, diffuserGreen));
		hittableList.add(new Sphere(new Vec3(2, -0.85, 12), 0.5, diffuserGreen));
		hittableList.add(new Sphere(new Vec3(-5, -0.1, 1), 0.5, glassBall));
		hittableList.add(new Sphere(new Vec3(-4, 0, -1.5), 0.5, diffuserGreen));
		hittableList.add(new Sphere(new Vec3(-1.5, 0, -4.5), 0.5, diffuserGreen));
		hittableList.add(new Sphere(new Vec3(-3, 0, -5), 0.5, diffuserRed));

		return new HittableList(hittableList);
	}

}
