package oobasics.bank

class Account(val customer: Customer, val id: String) {
  private var _balance = 0
  
  customer.addAccount(this)
  
  def balance = _balance
  
  def balance_=(newBalance: Int) = {
    if(newBalance < _balance) withdraw(_balance - newBalance)
    else deposit(newBalance - _balance)
  }
  
  def deposit(amount: Int): Boolean = {
    if(amount < 0) false
    else {
      _balance += amount
      true
    }
  }
  
  def withdraw(amount: Int): Boolean = {
    if(amount < 0 || amount > _balance) false
    else {
      _balance -= amount
      true
    }
  }
}

object Account {
  def main(args:Array[String]):Unit = {
    val a = new Account(new Customer("Mark","Lewis","id",new Address(Nil)),"id")
    a.balance = 700
    a.balance += 40
  }
}