
import java.io.{File}

import Utils._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers


class UtlisSpec extends AnyFlatSpec with Matchers{

  behavior of "Utilis object"

  it should "checkRange - return false if value beyond range" in {
    assert(!checkRange(101))
  }

  it should "ifDirExists - return false if directory is invalid" in {
    assert(!ifDirExists("./fake/dir//"))
  }

  it should "loadImage - assert not null" in {
    val img = loadImage("./sample_photos/bright_a.jpg")
    img should not be null
  }

  it should "getListOfFiles - shouldn't be empty and consist only of files" in {
    val path = "./sample_photos"
    val fileList = getListOfFiles(path)
    assert(fileList.nonEmpty && fileList.forall(_.isFile))
  }

  it should "createLabel - check if contains bright" in {
    val lab = createLabel(new File("./sample_photos/white_background.jpg"), 0, 75)
    assertResult(true) {
      lab.contains("bright")
    }
  }

  it should "saveFileToNewDest - check if final directory is correct" in {
    val newFile = new File("./sample_photos/white_background.jpg")
    val newDest = saveFileToNewDest(newFile, "./processed_photos", 0, 75)
      assert(newDest.toString === "./processed_photos/white_background_bright_0.jpg")
  }

}
