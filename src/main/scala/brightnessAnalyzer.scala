import Utils._

//TODO: Duża litera pierdoła ale wygląda amatorsko strasznie
object brightnessAnalyzer extends App{

  /*
  Ja bym to widział tak:
  readConsoleArguments()
  validateArguments()
  getFiles()
  analizePhotos()
  closeApplication()
   */

  if(args.length < 2){
    //TODO: wydaje mi sie ze jest sposob na wyswietlanie multiline string w scali zamiast 7x metoda println
    println("\n")
    println("Required parameters:")
    println("\t1   -  input path with photos")
    println("\t2*  -  [OPTIONAL] output path - unless explicitly given - ./processed_photos is created by default")
    println("\t3*  -  [OPTIONAL] cut-off limit of darkness, range from 1 to 99 - by default 75 (reasonable and well-tested value)")
  }

  else {
    //TODO: generalnie całość można by zrobic w metodzie readConsoleArguments()
    val input = args(1)
    val output = if(args.length > 2) args(2) else {
      //TODO:
      createDirIfNotExists("./processed_photos"); "./processed_photos"
    }
    //TODO: magic number 75 nie mam pojecia co oznacza, trzeba wyrzuć to do stałej i nazwac jakoś
    val cutOff = if(args.length > 3) args(3).toInt else 75

    //TODO: rename params, i tez wyrzucilbym te checki do metody i nazwał w stylu validateParameters()
    if(!ifDirExists(output) || !ifDirExists(input)){
      println("Invalid input or output directory")

      //TODO: sys exit niby jasne ale uzyte w 3 miejscach wyrzucilbym do metody i nazwal closeApplication()
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

      //TODO:destination jest ok zamiast dest im czytelniej tym lepiej
      saveFileToNewDest(file, output, coefficient, cutOff)
    }
    println("Processing complete!")
    sys.exit(0)
  }


}
