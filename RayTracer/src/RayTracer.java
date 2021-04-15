import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.stream.IntStream;

public class RayTracer {

	private static final int IMAGE_WIDTH = 900;
	private static final double ASPECT_RATIO = 16.0 / 9.0;
	private static final int IMAGE_HEIGHT = (int) (IMAGE_WIDTH / ASPECT_RATIO);
	private static final int RAYS_PER_PIXEL = 100;
	private static final int MAX_RAY_DEPTH = 50;

	public static void main(String[] args) {
		DrawingPanel drawingPanel = new DrawingPanel(IMAGE_WIDTH, IMAGE_HEIGHT);
		Graphics graphics = drawingPanel.getGraphics();
		BufferedImage image = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_ARGB);

		HittableList world = WorldBuilder.build();
		Camera camera = CameraBuilder.build(IMAGE_WIDTH, IMAGE_HEIGHT);

		boolean parallel = (args.length != 0 && args[0].equals("--parallel"));

		long start = System.currentTimeMillis();
		fillImage(graphics, image, world, camera, parallel);
		long elapsedTimeMillis = System.currentTimeMillis() - start;
		System.out.printf("Done, took %.2fs%n", elapsedTimeMillis / 1000.0);
	}

	private static void fillImage(Graphics graphics, BufferedImage image, Hittable world, Camera camera,
			boolean parallel) {
		IntStream rowNumbers = IntStream.range(0, IMAGE_HEIGHT);
		if (parallel)
			rowNumbers = rowNumbers.parallel();

		rowNumbers.forEach(row -> {
			fillRow(graphics, image, world, camera, row);
		});
	}

	private static void fillRow(Graphics graphics, BufferedImage image, Hittable world, Camera camera, int row) {
		for (int col = 0; col < IMAGE_WIDTH; col++)
			setPixelColour(image, row, col, world, camera);
		graphics.drawImage(image, 0, 0, null);
	}

	private static void setPixelColour(BufferedImage image, int row, int col, Hittable world, Camera camera) {
		Vec3 antiAliasedColour = antiAliasedPixelColour(row, col, world, camera);
		Vec3 gammaCorrectedColour = gammaCorrectColour(antiAliasedColour);
		setRgbPixelValue(image, row, col, gammaCorrectedColour);
	}

	private static void setRgbPixelValue(BufferedImage image, int row, int col, Vec3 gammaCorrectedColour) {
		Color colour = vectorToColor(gammaCorrectedColour);
		int y = IMAGE_HEIGHT - row - 1;
		image.setRGB(col, y, colour.getRGB());
	}

	private static Color vectorToColor(Vec3 gammaCorrectedColour) {
		Color colour = new Color((int) (255 * gammaCorrectedColour.r()), (int) (255 * gammaCorrectedColour.g()),
				(int) (255 * gammaCorrectedColour.b()));
		return colour;
	}

	private static Vec3 gammaCorrectColour(Vec3 antiAliasedColour) {
		Vec3 gammaCorrectedColour = new Vec3(Math.sqrt(antiAliasedColour.r()), Math.sqrt(antiAliasedColour.g()),
				Math.sqrt(antiAliasedColour.b()));
		return gammaCorrectedColour;
	}

	private static Vec3 antiAliasedPixelColour(int row, int col, Hittable world, Camera camera) {
		Vec3 antiAliasedColour = new Vec3(0, 0, 0);
		for (int i = 0; i < RAYS_PER_PIXEL; i++) {
			float x = (float) ((col + Math.random()) / (IMAGE_WIDTH - 1));
			float y = (float) ((row + Math.random()) / (IMAGE_HEIGHT - 1));
			Ray ray = camera.getRay(x, y);
			antiAliasedColour = antiAliasedColour.add(rayColour(ray, world, MAX_RAY_DEPTH));
		}
		antiAliasedColour = antiAliasedColour.div(RAYS_PER_PIXEL);
		return antiAliasedColour;
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
		Vec3 unitDir = Vec3.unitVector(ray.direction());
		var t = 0.5 * (unitDir.y() + 1.0);
		return new Vec3(1.0, 1.0, 1.0).mul(1.0 - t).add(new Vec3(0.5, 0.7, 1.0).mul(t));
	}

}
