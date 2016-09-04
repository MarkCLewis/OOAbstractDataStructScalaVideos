package polymorphism

object StreetLightColor extends Enumeration {
  val Red, Green, Yellow = Value
}

class StreetLight(private var _color: StreetLightColor.Value) {
  def color = _color
  
  import StreetLightColor._
  
  def cycle: Unit = _color = _color match {
    case Red => Green
    case Green => Yellow
    case Yellow => Red
  }
}