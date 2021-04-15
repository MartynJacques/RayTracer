
public class CameraBuilder {

	public static Camera build(int imageWidth, int imageHeight) {
		Vec3 lookFrom = new Vec3(0, 5, -15);
		Vec3 lookAt = new Vec3(0, 0, 0);
		double distanceToFocus = lookFrom.sub(lookAt).length();
		double aperture = 100;
		return new Camera(lookFrom, lookAt, new Vec3(0, 1, 0), 40, (double) (imageWidth) / imageHeight, aperture,
				distanceToFocus);
	}

}
