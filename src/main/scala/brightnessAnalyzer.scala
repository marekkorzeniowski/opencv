import Utils._

object brightnessAnalyzer extends App{

  if(args.length < 2){
    println("\n")
    println("Required parameters:")
    println("\t1   -  input path with photos")
    println("\t2*  -  [OPTIONAL] output path - unless explicitly given - ./processed_photos is created by default")
    println("\t3*  -  [OPTIONAL] cut-off limit of darkness, range from 1 to 99 - by default 75 (reasonable and well-tested value)")
  }

  else {
    val input = args(1)
    val output = if(args.length > 2) args(2) else {createDirIfNotExists("./processed_photos"); "./processed_photos"}
    val cutOff = if(args.length > 3) args(3).toInt else 75

    if(!ifDirExists(output) || !ifDirExists(input)){
      println("Invalid input or output directory")
      sys.exit(0)
    }

    if(!checkRange(cutOff)){
      println("Incorrect cut-off limit - permitted range 1 - 99")
      sys.exit(0)
    }


    getListOfFiles(input).foreach { file =>
      val src = loadImage(file.getAbsolutePath)
      val h = new Histogram1D
      val coefficient = h.calculatePerceivedBrightness(src)

      saveFileToNewDest(file, output, coefficient, cutOff)
    }
    println("Processing complete!")
    sys.exit(0)
  }


}
