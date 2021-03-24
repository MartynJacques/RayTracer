import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class RayTracer {

	private static final int IMAGE_WIDTH = 600;
	private static final double ASPECT_RATIO = 16.0 / 9.0;
	private static final int IMAGE_HEIGHT = (int) (IMAGE_WIDTH / ASPECT_RATIO);

	// Rays per pixel, for anti-aliasing
	private static final int NUM_SAMPLES = 100;

	// Maximum amount of time
	private static final int MAX_DEPTH = 50;

	public static void main(String[] args) {
		DrawingPanel drawingPanel = new DrawingPanel(IMAGE_WIDTH, IMAGE_HEIGHT);
		Graphics graphics = drawingPanel.getGraphics();
		BufferedImage image = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_ARGB);

		HittableList world = buildWorld();
		Camera camera = setupCamera();

		long start = System.currentTimeMillis();
		if (args.length != 0 && args[0].equals("--parallel")) {
			fillImageParrallel(graphics, image, world, camera);
		} else {
			fillImage(graphics, image, world, camera);
		}
		long elapsedTimeMillis = System.currentTimeMillis() - start;
		System.out.printf("Done, took %.2fs%n", elapsedTimeMillis / 1000.0);
	}

	private static Camera setupCamera() {
		Vec3 lookfrom = new Vec3(0, 5, -15);
		Vec3 lookat = new Vec3(0, 0, 0);
		double dist_to_focus = lookfrom.sub(lookat).length();
		double aperture = 100;
		return new Camera(lookfrom, lookat, new Vec3(0, 1, 0), 40, (double) (IMAGE_WIDTH) / IMAGE_HEIGHT, aperture,
				dist_to_focus);
	}

	private static HittableList buildWorld() {
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

		Hittable[] list = hittableList.stream().toArray(Hittable[]::new);
		return new HittableList(list, hittableList.size());
	}

	private static void fillImage(Graphics graphics, BufferedImage image, Hittable world, Camera camera) {
		for (int row = 0; row < IMAGE_HEIGHT; row++) {
			for (int col = 0; col < IMAGE_WIDTH; col++) {
				setPixelColour(image, row, col, world, camera);
			}
			graphics.drawImage(image, 0, 0, null);
		}
	}

	private static void fillImageParrallel(Graphics graphics, BufferedImage image, Hittable world, Camera camera) {
		IntStream.range(0, IMAGE_HEIGHT).parallel().forEach(i -> {
			for (int col = 0; col < IMAGE_WIDTH; col++)
				setPixelColour(image, i, col, world, camera);
			graphics.drawImage(image, 0, 0, null);
		});
	}

	private static void setPixelColour(BufferedImage image, int row, int col, Hittable world, Camera camera) {
		Vec3 antiAliasedColour = new Vec3(0, 0, 0);
		Ray ray;
		for (int i = 0; i < NUM_SAMPLES; i++) {
			float r = (float) ((col + Math.random()) / (IMAGE_WIDTH - 1));
			float g = (float) ((row + Math.random()) / (IMAGE_HEIGHT - 1));
			ray = camera.getRay(r, g);
			antiAliasedColour = antiAliasedColour.add(rayColour(ray, world, MAX_DEPTH));
		}
		Vec3 finalColour = antiAliasedColour.div(NUM_SAMPLES);
		finalColour = new Vec3(Math.sqrt(finalColour.e[0]), Math.sqrt(finalColour.e[1]), Math.sqrt(finalColour.e[2])); // gamma
		Color colour = new Color((int) (255 * finalColour.r()), (int) (255 * finalColour.g()),
				(int) (255 * finalColour.b()));

		int y = IMAGE_HEIGHT - row - 1;
		image.setRGB(col, y, colour.getRGB());
	}

	private static Vec3 rayColour(Ray ray, Hittable world, int depth) {
		HitRecord hitRecord = new HitRecord();

		if (depth <= 0)
			return new Vec3(0, 0, 0);

		if (world.hit(ray, 0.001, Double.POSITIVE_INFINITY, hitRecord)) {
			Ray scattered = new Ray();
			Vec3 attenuation = new Vec3();
			if (hitRecord.mat.scatter(ray, hitRecord, attenuation, scattered)) {
				return rayColour(scattered, world, depth - 1).mul(attenuation);
			}
			return new Vec3(0, 0, 0);
		}
		Vec3 unit_dir = Vec3.unitVector(ray.direction());
		var t = 0.5 * (unit_dir.y() + 1.0);
		return new Vec3(1.0, 1.0, 1.0).mul(1.0 - t).add(new Vec3(0.5, 0.7, 1.0).mul(t));
	}

}
