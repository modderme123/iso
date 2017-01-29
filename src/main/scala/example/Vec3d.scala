package example

case class Vec3d(x: Double, y: Double, z: Double) {
  def +(other: Vec3d): Vec3d = Vec3d(x + other.x, y + other.y, z + other.z)

  def -(other: Vec3d): Vec3d = Vec3d(x - other.x, y - other.y, z - other.z)

  def *(d: Double): Vec3d = Vec3d(x * d, y * d, z * d)

  def *(m: Mat3): Vec3d = m * this

  def /(d: Double): Vec3d = Vec3d(x / d, y / d, z / d)

  def scale(o: Vec3d, v: Vec3d): Vec3d = {
    val ov      = this - o
    val stretch = Vec3d(ov.x * v.x, ov.y * v.y, ov.z * v.z)
    stretch + o
  }

  def rotateX(o: Vec3d, angle: Double): Vec3d = {
    val p    = this - o
    val newZ = p.z * math.cos(angle) - p.y * math.sin(angle)
    val newY = p.z * math.sin(angle) + p.y * math.cos(angle)

    Vec3d(p.x, newY, newZ) + o
  }

  def rotateY(o: Vec3d, angle: Double): Vec3d = {
    val p    = this - o
    val newX = p.x * math.cos(angle) - p.z * math.sin(angle)
    val newZ = p.x * math.sin(angle) + p.z * math.cos(angle)

    Vec3d(newX, p.y, newZ) + o
  }

  def rotateZ(o: Vec3d, angle: Double): Vec3d = {
    val p    = this - o
    val newX = p.x * math.cos(angle) - p.y * math.sin(angle)
    val newY = p.x * math.sin(angle) + p.y * math.cos(angle)

    Vec3d(newX, newY, p.z) + o
  }

  def length: Double = math.sqrt(x * x + y * y + z * z)

  def unit: Vec3d = this / length

  def dot(other: Vec3d): Double = x * other.x + y * other.y + z * other.z

  def cross(other: Vec3d): Vec3d = {
    val i = y * other.z - other.y * z
    val j = x * other.z - other.x * z
    val k = x * other.y - other.x * y
    Vec3d(i, j, k)
  }
}
