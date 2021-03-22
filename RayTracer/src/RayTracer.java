import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class RayTracer {

	private static final int IMAGE_WIDTH = 800;
	private static final double ASPECT_RATIO = 16.0 / 9.0;
	private static final int IMAGE_HEIGHT = (int) (IMAGE_WIDTH / ASPECT_RATIO);

	private static final double VIEWPORT_HEIGHT = 2.0;
	private static final double VIEWPORT_WIDTH = ASPECT_RATIO * VIEWPORT_HEIGHT;
	private static final double FOCAL_LENGTH = 1.0;

	private static final Vec3 ORIGIN = new Vec3(0, 0, 0);
	private static final Vec3 HORIZONTAL = new Vec3(VIEWPORT_WIDTH, 0, 0);
	private static final Vec3 VERTICAL = new Vec3(0, VIEWPORT_HEIGHT, 0);
	private static final Vec3 LOWER_LEFT_CORNER = ORIGIN
			.sub(HORIZONTAL.div(2).sub(VERTICAL.div(2).sub(new Vec3(0, 0, FOCAL_LENGTH))));

	public static void main(String[] args) {
		DrawingPanel drawingPanel = new DrawingPanel(IMAGE_WIDTH, IMAGE_HEIGHT);
		Graphics graphics = drawingPanel.getGraphics();
		BufferedImage image = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_ARGB);

		fillImage(graphics, image);

		System.out.println("Done");
	}

	private static void fillImage(Graphics graphics, BufferedImage image) {
		for (int row = 0; row < IMAGE_HEIGHT; row++) {
			System.out.println("Processing row: " + row);
			for (int col = 0; col < IMAGE_WIDTH; col++) {
				setPixelColour(image, row, col);
			}
			graphics.drawImage(image, 0, 0, null);
		}
	}

	private static void setPixelColour(BufferedImage image, int row, int col) {
		float r = (float) col / (IMAGE_WIDTH - 1);
		float g = (float) row / (IMAGE_HEIGHT - 1);

		Vec3 direction = LOWER_LEFT_CORNER.add(HORIZONTAL.mul(r).add(VERTICAL.mul(g).sub(ORIGIN)));
		Ray ray = new Ray(ORIGIN, direction);
		Vec3 rayColour = rayColour(ray);

		Color color = new Color((float) rayColour.r(), (float) rayColour.g(), (float) rayColour.b());
		int y = IMAGE_HEIGHT - row - 1;
		image.setRGB(col, y, color.getRGB());
	}

	private static Vec3 rayColour(Ray ray) {
		Vec3 direction = ray.direction();
		double t = 0.5 * (direction.y() + 1.0);
		return new Vec3(1.0, 1.0, 1.0).mul(1.0 - t).add(new Vec3(0.5, 0.7, 1.0).mul(t));
	}

}
