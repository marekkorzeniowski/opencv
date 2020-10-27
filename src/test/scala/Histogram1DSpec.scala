import Utils.loadImage
import org.scalatest.flatspec.AnyFlatSpec

class Histogram1DSpec extends AnyFlatSpec{

  behavior of "Histogram1d"

  it should "calculatePerceivedBrightness - return 0" in {
    val img = loadImage("./sample_photos/white_background.jpg")
    val h = new Histogram1D
    val coef = h.calculatePerceivedBrightness(img)
    assertResult(0) {
      coef
    }
  }

}
