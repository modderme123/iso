package example

case class Shape(faces: Seq[Path]) {
  def translate(amount: Vec3d): Shape = {
    Shape(faces.map(_.translate(amount)))
  }

  def scale(origin: Vec3d, amount: Vec3d): Shape = {
    Shape(faces.map(_.scale(origin, amount)))
  }

  def rotateX(origin: Vec3d, angle: Double): Shape = {
    Shape(faces.map(_.rotateX(origin, angle)))
  }

  def rotateY(origin: Vec3d, angle: Double): Shape = {
    Shape(faces.map(_.rotateY(origin, angle)))
  }

  def rotateZ(origin: Vec3d, angle: Double): Shape = {
    Shape(faces.map(_.rotateZ(origin, angle)))
  }
}
