import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class RayTracer {

	private static final int IMAGE_WIDTH = 800;
	private static final double ASPECT_RATIO = 16.0 / 9.0;
	private static final int IMAGE_HEIGHT = (int) (IMAGE_WIDTH / ASPECT_RATIO);

	// Rays per pixel
	private static final int NUM_SAMPLES = 10;

	// Number of bounes
	private static final int MAX_DEPTH = 10;

	public static void main(String[] args) {
		DrawingPanel drawingPanel = new DrawingPanel(IMAGE_WIDTH, IMAGE_HEIGHT);
		Graphics graphics = drawingPanel.getGraphics();
		BufferedImage image = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_ARGB);

		Camera camera = new Camera();

		Hittable[] list = new Hittable[2];
		list[0] = new Sphere(new Vec3(0, 0, -1), 0.5);
		list[1] = new Sphere(new Vec3(0, -100.5, -1), 100);
		HittableList world = new HittableList(list, 2);

		fillImage(graphics, image, world, camera);
		System.out.println("Done");
	}

	private static void fillImage(Graphics graphics, BufferedImage image, Hittable world, Camera camera) {
		for (int row = 0; row < IMAGE_HEIGHT; row++) {
			System.out.println("Processing row: " + row);
			for (int col = 0; col < IMAGE_WIDTH; col++) {
				setPixelColour(image, row, col, world, camera);
			}
			graphics.drawImage(image, 0, 0, null);
		}
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
		Vec3 rayColour = antiAliasedColour.div(NUM_SAMPLES);
		Color color = new Color((float) rayColour.r(), (float) rayColour.g(), (float) rayColour.b());
		int y = IMAGE_HEIGHT - row - 1;
		image.setRGB(col, y, color.getRGB());
	}

	private static Vec3 rayColour(Ray ray, Hittable world, int depth) {
		HitRecord hitRecord = new HitRecord();

		if (depth <= 0)
			return new Vec3(0, 0, 0);

		// Does the ray hit anything in the world? If so, colour the pixel the colour of
		// the surface normal of the first hit
		if (world.hit(ray, 0, Double.POSITIVE_INFINITY, hitRecord)) {
			Vec3 target = hitRecord.p.add(hitRecord.normal).add(randomInUnitSphere());
			return rayColour(new Ray(hitRecord.p, target.sub(hitRecord.p)), world, depth - 1).mul(0.5);
		}
		Vec3 unit_dir = Vec3.unit_vector(ray.direction());
		var t = 0.5 * (unit_dir.y() + 1.0);
		return new Vec3(1.0, 1.0, 1.0).mul(1.0 - t).add(new Vec3(0.5, 0.7, 1.0).mul(t));
	}

	private static Vec3 randomInUnitSphere() {
		while (true) {
			Vec3 p = Vec3.random(-1, 1);
			if (p.squared_length() >= 1)
				continue;
			return p;
		}
	}

	public static double hitSphere(final Vec3 center, float radius, Ray r) {
		Vec3 oc = r.origin().sub(center);
		float a = (float) r.direction().squared_length();
		float halfB = (float) Vec3.dot(oc, r.direction());
		float c = (float) (oc.squared_length() - radius * radius);
		float discriminant = halfB * halfB - a * c;

		if (discriminant < 0) {
			return -1.0;
		} else {
			return (-halfB - Math.sqrt(discriminant)) / a;
		}
	}
}
