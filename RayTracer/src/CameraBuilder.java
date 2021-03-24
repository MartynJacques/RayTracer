
public class CameraBuilder {

	public static Camera build(int imageWidth, int imageHeight) {
		Vec3 lookfrom = new Vec3(0, 5, -15);
		Vec3 lookat = new Vec3(0, 0, 0);
		double dist_to_focus = lookfrom.sub(lookat).length();
		double aperture = 100;
		return new Camera(lookfrom, lookat, new Vec3(0, 1, 0), 40, (double) (imageWidth) / imageHeight, aperture,
				dist_to_focus);
	}

}
