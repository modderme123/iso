package example

import org.scalajs.dom
import org.scalajs.dom.{html, MouseEvent}

import scala.math._
import scala.scalajs.js

object Iso extends js.JSApp {
  def randomColor: Color = Color(80, 42, 42)
  // @formatter:off
  val vertices = Seq(
    Vec3d(-0.5, 0.5, -0.5),  // Front-Top-Left     0
    Vec3d(0.5, 0.5, -0.5),   // Front-Top-Right    1
    Vec3d(0.5, -0.5, -0.5),  // Front-Bottom-Right 2
    Vec3d(-0.5, -0.5, -0.5), // Front-Bottom-Left  3
    Vec3d(-0.5, 0.5, 0.5),   // Rear-Top-Left      4
    Vec3d(0.5, 0.5, 0.5),    // Rear-Top-Right     5
    Vec3d(0.5, -0.5, 0.5),   // Rear-Bottom-Right  6
    Vec3d(-0.5, -0.5, 0.5)   // Rear-Bottom-Left   7
  )

  // When inputing faces be sure to do the points clockwise
  // if you are facing the front of that face
  val cube = Shape(
    Seq(
      Path(Seq(vertices(0), vertices(1), vertices(2), vertices(3)), randomColor), // Front-Right
      Path(Seq(vertices(1), vertices(5), vertices(6), vertices(2)), randomColor), // Rear-Right
      Path(Seq(vertices(5), vertices(4), vertices(7), vertices(6)), randomColor), // Rear-Left
      Path(Seq(vertices(4), vertices(0), vertices(3), vertices(7)), randomColor), // Front-Left
      Path(Seq(vertices(0), vertices(1), vertices(5), vertices(4)), randomColor), // Top
      Path(Seq(vertices(7), vertices(6), vertices(2), vertices(3)), randomColor) // Bottom
    )
  ).scale(Vec3d(0, 0, 0), Vec3d(1.5, 6.25, 1.5)).rotateZ(Vec3d(0, 0, 0), Pi * 0.5)

  val iso = Mat3(
    Vec3d(sqrt(3),  1, sqrt(2)),
    Vec3d(0,        2, -sqrt(2)),
    Vec3d(-sqrt(3), 1, sqrt(2))
  )
    // @formatter:on

  val light = Vec3d(-1, 1, 0.5) * iso

  def main(): Unit = {
    val (w, h) = (dom.window.innerWidth, dom.window.innerHeight)
    val canvas = dom.document.createElement("canvas").asInstanceOf[html.Canvas]
    dom.document.body.appendChild(canvas)
    canvas.width = w.toInt
    canvas.height = h.toInt
    val ctx = canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]

    ctx.translate(w / 2, h / 2)

    var rotAngle: Double = 0
    dom.window.setInterval(
      () => {
        // @formatter:off
        // Mat3 has the columns sideways, so input them vertically then rotate
        val rot = Mat3(
          Vec3d(cos(rotAngle),  0, sin(rotAngle)),
          Vec3d(0,              1, 0),
          Vec3d(-sin(rotAngle), 0, cos(rotAngle))
        ).rotate(false)
        // @formatter:on

        // TODO multiply faces by iso
        val cameraFaces = cube.faces.map { face: Path =>
          Path(face.vertices.map(_ * (rot * iso)), face.color)
        }
        val depthSorted = cameraFaces.sortWith(_.depth > _.depth)

        val litFaces = lightFaces(depthSorted)

        ctx.clearRect(-w / 2, -h / 2, w, h)
        for (i <- litFaces) {
          ctx.fillStyle = i.color.toString
          ctx.beginPath()

          val v1 = i.vertices(0)
          ctx.moveTo(20 * v1.x, -20 * v1.y)

          for (z <- i.vertices.drop(1)) {
            val vi = z
            ctx.lineTo(20 * vi.x, -20 * vi.y)
          }

          ctx.closePath()
          ctx.fill()
        }
      },
      10
    )

    dom.window.addEventListener("mousemove", { e: MouseEvent =>
      rotAngle = -atan2(e.pageX - w / 2, e.pageY - h / 2)
    })
  }
  def lightFace(face: Path): Color = {
    // Take two edges of the face in screen space
    val edge1 = face.vertices(0) - face.vertices(1)
    val edge2 = face.vertices(2) - face.vertices(1)

    // Create a flat plane from two vectors
    val plane = edge1 cross edge2

    // The angle at which the light decides how to shade the faces
    val angleFromLight = light dot plane

    face.color + (-angleFromLight / 10)
  }

  def lightFaces(faces: Iterable[Path]): Iterable[Path] = {
    for {
      face <- faces
      color = lightFace(face)
    } yield {
      face.copy(color = color)
    }
  }

}
