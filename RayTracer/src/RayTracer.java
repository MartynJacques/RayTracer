import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class RayTracer {

	private static final int IMAGE_WIDTH = 256;
	private static final int IMAGE_HEIGHT = 256;

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
		float b = 0.25f;

		Color color = new Color(r, g, b);
		int y = IMAGE_HEIGHT - row - 1;
		image.setRGB(col, y, color.getRGB());
	}

}
