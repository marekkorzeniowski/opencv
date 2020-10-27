import java.io.{File, IOException}
import java.nio.file.{Files, Path, Paths, StandardCopyOption}
import org.bytedeco.opencv.global.opencv_imgcodecs.{IMREAD_GRAYSCALE, imread}
import org.bytedeco.opencv.opencv_core.Mat

object Utils {

  //TODO: metoda jesli zwraca boolean powinna zaczynać sie od 'is' lub 'has' boolean methods convention
  def checkRange(cutOff: Int): Boolean = cutOff > 0 && cutOff < 100

  //TODO: same here
  def ifDirExists(path: String): Boolean = Files.exists(Paths.get(path))

  //TODO: to tez jest slabe ze metoda load image zamyka aplikacje
  def loadImage(absPath: String, code: Int = IMREAD_GRAYSCALE): Mat = {
    val image = imread(absPath, code)
    if (image.empty()) {
      // error handling
      // no image has been created...
      // possibly display an error message
      // and quit the application
      println("Error reading image...")
      System.exit(1)
    }
    image
  }

  //TODO: jesli metoda zwraca listy nie powinno sie tego duplikować w nazwie getFiles: List jest dosyć klarowne
  def getListOfFiles(dir: String):List[File] = {
    val d = new File(dir)
    if (d.exists && d.isDirectory) {
      d.listFiles.filter(_.isFile).toList
    } else {
      println("Empty or incorrect folder...")
      List[File]()
    }
  }

  def displayResults(file: File, factor: Int): Unit = {
    val len = file.getName.length
    val numberOfSpaces = " " * (25 - len)

    println(s"File: ${file.getName} $numberOfSpaces- perceived brightness: \t$factor")
  }

  def createLabel(file: File, coefficient: Int, cutOff: Int): String = {
    val tokens = file.getName.split("\\.")
    val label = if (coefficient > cutOff) s"_dark_$coefficient" else s"_bright_$coefficient"
    val newName = s"${tokens(0)}$label.${tokens(1)}"
    newName
  }

  def saveFileToNewDest(file: File, destPath: String, coefficient: Int, cutOff: Int): Path = {
    val fileName = createLabel(file, coefficient, cutOff)
    val srcPath = file.getAbsolutePath

    Files.copy(
      Paths.get(srcPath),
      Paths.get(s"$destPath/$fileName"),
      StandardCopyOption.REPLACE_EXISTING
    )
  }

  //TODO: metoda robi dwie rzeczy
  def createDirIfNotExists(path: String): Unit = {
    if(ifDirExists(path)) {
      println("Directory already exists")
    }
    else {
      try {
        Files.createDirectory(Paths.get(path))
        println("Directory created successfully!")
      }
      catch {
        case e: IOException => println("IOException trying to create directory")
      }
    }
  }

}
