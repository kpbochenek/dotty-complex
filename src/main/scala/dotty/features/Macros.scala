package dotty.features

object Logger {
  private var indent = 0

  inline def log[T](msg: String, indentMargin: =>Int)(op: => T): T =
    if (true) {
      println(s"${" " * indent}start $msg")
      indent += indentMargin
      val result = op
      indent -= indentMargin
      println(s"${" " * indent}end $msg")
      result
    } else {
      op
    }
}


@main
def main(): Unit = {
  println("Real main")
  new Macros().main(Array())
}

class Macros {

  def main(args: Array[String]): Unit = {
    Logger.log("transaction", 2) {
      val received = Logger.log("borrow money", 2) {
        // borrowing money
        100
      }

      val lost = Logger.log("pay taxes", 2) {
        // paying taxes
        80
      }

      received - lost
    }
  }

}
