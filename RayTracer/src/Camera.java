public class Camera {

	private static final double ASPECT_RATIO = 16.0 / 9.0;
	private static final double VIEWPORT_HEIGHT = 2.0;
	private static final double VIEWPORT_WIDTH = ASPECT_RATIO * VIEWPORT_HEIGHT;
	private static final double FOCAL_LENGTH = 1.0;

	private static final Vec3 ORIGIN = new Vec3(0, 0, 0);
	private static final Vec3 HORIZONTAL = new Vec3(VIEWPORT_WIDTH, 0, 0);
	private static final Vec3 VERTICAL = new Vec3(0, VIEWPORT_HEIGHT, 0);

	private static final Vec3 a = ORIGIN.sub(HORIZONTAL.div(2));
	private static final Vec3 b = VERTICAL.div(2);
	private static final Vec3 c = new Vec3(0, 0, FOCAL_LENGTH);
	private static final Vec3 LOWER_LEFT_CORNER = a.sub(b).sub(c);

	public Ray getRay(double x, double y) {
		Vec3 direction = LOWER_LEFT_CORNER.add(HORIZONTAL.mul(x).add(VERTICAL.mul(y).sub(ORIGIN)));
		return new Ray(ORIGIN, direction);
	}

}
