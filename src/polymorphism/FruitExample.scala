package polymorphism

class Skin

class Fruit {
  def canEatSkin: Boolean = true
  def fractionalLiquidContent: Double = 0.5
  def peel(): Skin = ???
  def slice(): Unit = ???
}

object FruitExample {
//  makeBreakfastShake(new Banana())
//  makeBreakfastShake(new Apple())
//  
//  
//  def makeBreakfastShake(fruit: Fruit): Unit = {
//    if(!fruit.canEatSkin) fruit.peel()
//    blender += fruit
//    blender += juice
//    if(fruit.fractionalLiquidContent < 0.3) blender += juice
//    blender += ice
//    blender.blend
//  }
}