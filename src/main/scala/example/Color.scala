package example

case class Color(r: Int, g: Int, b: Int) {
  override def toString = s"rgb($r, $g, $b)"

  def *(p: Double) = Color((r * p).toInt, (g * p).toInt, (b * p).toInt)

  def +(percent: Double): Color = {
    val amt = (2.55 * percent).toInt
    Color(r + amt, g + amt, b + amt)
  }
}
