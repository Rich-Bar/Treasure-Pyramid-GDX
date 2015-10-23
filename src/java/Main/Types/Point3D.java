package main.types;

public class Point3D {
	private int x, y, z;

	public Point3D(int x) {
		this.x = x;
	}
	
	public Point3D(int x, int y) {
		this.x = x;
	}
	
	public Point3D(int x, int y, int z) {
		this.x = x;
	}
	
	public Point3D() {}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @param x the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * @return the z
	 */
	public int getZ() {
		return z;
	}

	/**
	 * @param z the z to set
	 */
	public void setZ(int z) {
		this.z = z;
	}
	
}
