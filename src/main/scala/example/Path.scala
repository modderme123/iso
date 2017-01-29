package example

case class Path(vertices: Seq[Vec3d], color: Color) {
  def translate(amount: Vec3d): Path = {
    Path(vertices.map(_ + amount), color)
  }

  def depth: Double = {
    vertices.foldLeft(0.0) {
      case (o: Double, me: Vec3d) => math.max(o, me.z)
    } / vertices.length
  }

  def scale(origin: Vec3d, amount: Vec3d): Path = {
    Path(vertices.map(_.scale(origin, amount)), color)
  }

  def rotateX(origin: Vec3d, angle: Double): Path = {
    Path(vertices.map(_.rotateX(origin, angle)), color)
  }

  def rotateY(origin: Vec3d, angle: Double): Path = {
    Path(vertices.map(_.rotateY(origin, angle)), color)
  }

  def rotateZ(origin: Vec3d, angle: Double): Path = {
    Path(vertices.map(_.rotateZ(origin, angle)), color)
  }
}
