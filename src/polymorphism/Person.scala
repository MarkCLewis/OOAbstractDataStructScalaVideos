package polymorphism

trait Person {}
trait Parent extends Person {}
trait Female extends Person {}
class Mother extends Parent with Female {}