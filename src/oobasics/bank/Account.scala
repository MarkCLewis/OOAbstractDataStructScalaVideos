package oobasics.bank

class Account private(val customer: Customer, val id: String) {
  private[this] var _balance = 0

  customer.addAccount(this)
  
  def balance = _balance

  def balance_=(newBalance: Int) = {
    if (newBalance < _balance) withdraw(_balance - newBalance)
    else deposit(newBalance - _balance)
  }

  def deposit(amount: Int): Boolean = {
    if (amount < 0) false
    else {
      _balance += amount
      true
    }
  }

  def withdraw(amount: Int): Boolean = {
    if (amount < 0 || amount > _balance) false
    else {
      _balance -= amount
      true
    }
  }
}

object Account {
  private var nextAccountNumber = 0

  def main(args: Array[String]): Unit = {
    val a = new Account(new Customer("Mark", "Lewis", "id", new Address(Nil)), "id")
    a.balance = 700
    a.balance += 40
  }

  def apply(c: Customer): Account = {
    nextAccountNumber += 13
    new Account(c, nextAccountNumber.toString)
  }
  
  def apply(lines: Iterator[String]): Account = {
    ???
  }
}