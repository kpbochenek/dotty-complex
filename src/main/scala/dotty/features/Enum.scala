package dotty.features


object SimpleEnum {
  // Simple enum where you have 3 stable predefined choices
  enum Color {
    case Red, Green, Blue
  }

  // Simply 'Color' is a Type that can have 3 values.
  def red: Color = Color.Green

  // You can easily iterate over all types
  def all: Array[Color] = Color.values

  // Or convert String to one of values (be careful it can throw exception!)
  def isOrange: Color = Color.valueOf("Orange")
}


object ParametrizedEnum {
  // enums can also be parametrized
  enum Color(val rgb: Int) {
    case Red extends Color(1)
    case Green extends Color(2)
    case Blue extends Color(3)
  }

  // We still have access to values and valueOf
  def all: Array[Color] = Color.values

  def X_test_refs: Unit = {
    Color.Red
    println("??")
    Color.Red
    val c = 1 + 2
    Color.Red
  }
}


object GenericEnum {
  class X(color: String) {}
  def makeColor(s: String): String = s"C-${s}"

  // But enums can also be parametrized by generics
  enum Color[T <: X](val rgb: T) {

    case Red extends Color(new X(makeColor("Red")))
    case Green extends Color[X](new X(makeColor("Green")))
    case Blue extends Color(new X(makeColor("Blue")))

    // you can also define other utility fields/methods here
    // they are defined for each value not as static members of Color.
    val wp = "*"
    def wrap(): String = wp + rgb + wp
  }
}


object EnumConstructor {
  given String = "HEX"

  // Enums can take multiple parameters and can also need implicit ones
  enum Color(val rgb: Int)(trnsp: Boolean)(using String) {
    case Red extends Color(1)(true)(using "HEX")
    case Green extends Color(2)(false)
    case Blue extends Color(3)(false)
  }

  // We still have access to values and valueOf
  def all: Array[Color] = Color.values

  // As with normal classes 'val' fields are fields
  // other like 'transparent' act as parameters and cannot be accessed from outside
  Color.Red.rgb
  // Color.Red.transparent // won't compile
}


object EnumClass {
  // We can also have enum values that can have infinite values,
  // parametrized by values
  enum Color(val rgb: Int) {
    case Hex(gb: Int) extends Color(gb)
    case Cmyk(val gb: Int) extends Color(gb)
  }

  // Because we don't have limited set of values, '.values' will no longer compile
  // Color.values // won't compile

  val redHex = Color.Hex(123)
  val blueCmyk = Color.Cmyk(3356)
}


object EnumExtends {
  trait X { def display: String = " I am X!" }
  trait Y { def fun: Boolean = true }

  // You can provide self-name for your enum 'slf' and constrain it with another type
  // You can also mixin other traits not only 'Color'
  // Oonly access modifiers are allowed for enums
  protected enum Color extends X { slf: Y =>
    case Red extends Color, Y
    case Green extends Color, Y
  }

  println(Color.Red.display)
  println(Color.Red.fun)
}

object EnumMixAll {
  trait X
  trait Y extends X
  trait Ord[T] { def cmp(x: T): Boolean = true }

  given Y = ???
  object In {
    trait Y
  }

  // Complex example :)
  private enum Color[+A, -B, C <: X](rgb: A, tip: B)(using C) extends X { slf: C =>
    case Red[W : Ord](p: List[W]) extends Color[String, W, Y]("A", p.head), Y
    case Green(a: Int)(using Y) extends Color[Int, String, Y](a, "a"), Y
  }

  given Ord[Int] = new Ord[Int] { }
  // Use of case values
  Color.Red(List(3, 2, 411)).toString + Color.Green(2).toString

  // '.values' and '.valueOf' not allowed here
  // Color.values // won't compile
}
