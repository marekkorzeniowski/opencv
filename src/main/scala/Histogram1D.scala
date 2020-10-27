import org.bytedeco.javacpp.indexer.FloatRawIndexer
import org.bytedeco.javacpp.{FloatPointer, IntPointer}
import org.bytedeco.opencv.global.opencv_imgproc.calcHist
import org.bytedeco.opencv.opencv_core.{Mat, MatVector}
import scala.math.{abs}

class Histogram1D {

  val numberOfBins = 256
  val channels: IntPointer = new IntPointer(1L).put(0)
  val minRange = 0.0f
  val maxRange = 256.0f

  def getHistogram(image: Mat, mask: Mat = new Mat()): Mat = {
    val histSize = new IntPointer(1L).put(numberOfBins)
    val ranges = new FloatPointer(minRange, maxRange)
    val matToVector = new MatVector(Array(image): _*)

    val hist = new Mat()
    calcHist(matToVector, channels, mask, hist, histSize, ranges)

    hist
  }

  def getHistogramAsArray(image: Mat): Array[Float] = {
    // Create and calculate histogram object
    val hist = getHistogram(image)

    // Extract values to an array
    val dest = new Array[Float](numberOfBins)
    val histI = hist.createIndexer().asInstanceOf[FloatRawIndexer]
    for (bin <- 0 until numberOfBins) {
      dest(bin) = histI.get(bin)
    }

    dest
  }

  def calculatePerceivedBrightness(image: Mat) = {
    val numberOfPixels = image.rows() * image.cols()
    val histArray = getHistogramAsArray(image)

    val average = histArray.zipWithIndex.map {case (count, bin) => count * bin}.sum / numberOfPixels
    val normalizedAvg = abs((average * 100) / numberOfBins - 100).toInt
    normalizedAvg
  }



}
