package dotty.features

object UnionTypes {

  trait Clickable[T] {
    def click(): T
    def mix(): Unit
    def children: List[String]
  }

  trait Cancelable {
    def cancel(): Unit
    def mix(): Unit
    def children: List[Int]
  }


  trait Movable {
    def move(x: Int): Unit
    def mix(): Unit
  }

  // Intersection types can be used as types for arguments to methods
  // it contains all methods from both traits
  // You don't need to limit yourself to only 2 traits
  def action(element: Clickable[String] & Cancelable & Movable): String = {
    element.click()
    element.cancel()
    // What will happen if you call mix() which is defined in 2 traits?
    element.mix()
    // What is a type of 'children'? Nicely merged type:
    // List[String] & List[Int] => List[String & Int]
    val c: List[String & Int] = element.children
    element.click()
  }

  // Intersection type can also be used as a field/method type
  val ls: List[Cancelable & Movable] = List.empty

  // can also express given alias
  given (Cancelable & Movable) = ???

  // but can also be aliased with a type name
  type CancelMove = Cancelable & Movable

  def fx(): CancelMove = ???

  trait Parent { def work: Unit }
  trait UserName extends Parent { def help: String = "UN" }
  trait Password extends Parent { def help: String = "PW" }

  // Union type as method parameter type
  def help(id: UserName | Password) = {
    val idswap: Password | UserName = id  // A | B =:= B | A
    // println(id.help) // this won't work because:
    // a type is lub(least upper bound) of Password and UserName which is Parent
    // That's why this will work:
    idswap.work
    val user = id match {
      case _: UserName => s"UserName"
      case _: Password => s"Password"
    }
  }
}
