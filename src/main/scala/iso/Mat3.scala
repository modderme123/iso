package iso

case class Mat3(column1: Vec3d, column2: Vec3d, column3: Vec3d) {
  def *(other: Vec3d): Vec3d = {
    (column1 * other.x) + (column2 * other.y) + (column3 * other.z)
  }

  def *(other: Mat3): Mat3 = {
    def multCols(o: Vec3d): Vec3d = {
      val c1 = other.rotate(false).column1 dot o
      val c2 = other.rotate(false).column2 dot o
      val c3 = other.rotate(false).column3 dot o
      Vec3d(c1, c2, c3)
    }
    val c1 = multCols(this.column1)
    val c2 = multCols(this.column2)
    val c3 = multCols(this.column3)
    Mat3(c1, c2, c3)
  }

  def rotate(clockwise: Boolean): Mat3 = {
    if (clockwise) {
      val c1 = Vec3d(column3.x, column2.x, column1.x)
      val c2 = Vec3d(column3.y, column2.y, column1.y)
      val c3 = Vec3d(column3.z, column2.z, column1.z)
      Mat3(c1, c2, c3)
    } else {
      val c1 = Vec3d(column1.x, column2.x, column3.x)
      val c2 = Vec3d(column1.y, column2.y, column3.y)
      val c3 = Vec3d(column1.z, column2.z, column3.z)
      Mat3(c1, c2, c3)
    }
  }
}
