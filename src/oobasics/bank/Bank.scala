package oobasics.bank

class Bank {
  private var _customers: List[Customer] = Nil
  private var _accounts: List[Account] = Nil
  private var nextCustomerNumber = 0

  def addCustomer(fname: String, lname: String, address: Address): Customer = {
    nextCustomerNumber += 7
    val customer = new Customer(fname, lname, nextCustomerNumber.toString, address)
    _customers ::= customer
    customer
  }
  
  def openAccount(c: Customer): Account = {
    val account = Account(c)
    _accounts ::= account
    account
  }
  
  def closeAccount(a: Account): Boolean = {
    if(_accounts.contains(a)) {
      _accounts = _accounts.filter(_ != a)
      if(!a.customer.removeAccount(a.id)) {
        println("Account wasn't part of customer!")
      }
      true
    } else false
  }
  
  def findCustomer(fname: String, lname: String): Option[Customer] = {
    _customers.find(c => c.firstName == fname && c.lastName == lname)
  }
  
  def findCustomer(id: String): Option[Customer] = {
    _customers.find(_.id == id)
  }
  
  def findAccount(id: String): Option[Account] = {
    _accounts.find(_.id == id)
  }
}