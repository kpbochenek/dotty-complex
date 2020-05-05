package com.kpbochenek.example.dotty

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

  // Union types can be used as types for arguments to methods
  // it contains all methods from both traits
  // You don't need to limit yourself to only 2 traits
  def action(element: Clickable[String] & Cancelable & Movable): String = {
    element.cancel()
    // What will happen if you call mix() which is defined in 2 traits?
    element.mix()
    // What is a type of 'children' ???
    element.children
    element.click()
  }

  // Union type can also be used as a field/method type
  val ls: List[Cancelable & Movable] = List.empty

  // can also express given alias
  given (Cancelable & Movable) = ???

  // but can also be aliased with a type name
  type CancelMove = Cancelable & Movable

  def fx(): CancelMove = ???

}
