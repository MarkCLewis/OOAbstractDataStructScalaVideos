package oobasics.bank

import io.StdIn._

object BankMain {
  def main(args: Array[String]): Unit = {
    val bank = new Bank
    var option = 0
    var customer: Option[Customer] = None
    var account: Option[Account] = None

    while (option != 10) {
      println(menu)
      option = readInt()
      option match {
        case 1 => customer = Some(createCustomer(bank))
        case 2 => customer = selectCustomer(bank)
        case 3 => account = customer.map(c => createAccount(bank, c))
        case 4 =>
          account.foreach(a => closeAccount(bank, a))
          account = None
        case 5 => account = selectAccount(bank)
        case 6 => account.foreach(deposit)
        case 7 => account.foreach(withdraw)
        case 8 => account.foreach(a => println("The balance is " + a.balance))
        case 9 => customer.foreach(changeAddress)
        case 10 => println("Goodbye.")
        case _ => println("That is not a valid option. Please select again.")
      }
    }
  }

  private def createCustomer(bank: Bank): Customer = {
    println("What is the customer's first name?")
    var firstName = readLine()
    println("What is the customer's last name?")
    var lastName = readLine()
    println("What is the customer's address? End input with a blank line.")
    var address = readAddress()
    bank.addCustomer(firstName, lastName, address)
  }

  private def readAddress(): Address = {
    var lines = List[String]()
    do {
      lines ::= readLine()
    } while (lines.head.nonEmpty)
    new Address(lines.tail.reverse)
  }

  private def selectCustomer(bank: Bank): Option[Customer] = {
    println("Do you want to find the customer by name or id? (name/id)")
    val option = readLine()
    if (option == "name") {
      println("What is the customer's first name?")
      var firstName = readLine()
      println("What is the customer's last name?")
      var lastName = readLine()
      bank.findCustomer(firstName, lastName)
    } else {
      println("What is the customer's id")
      val id = readLine()
      bank.findCustomer(id)
    }
  }

  private def createAccount(bank: Bank, customer: Customer): Account = {
    bank.openAccount(customer)
  }

  private def closeAccount(bank: Bank, a: Account): Unit = {
    bank.closeAccount(a)
  }

  private def selectAccount(bank: Bank): Option[Account] = {
    println("What is the account id?")
    val id = readLine()
    bank.findAccount(id)
  }

  private def deposit(a: Account): Unit = {
    println("How much would you like to deposit?")
    val amount = readInt()
    if (!a.deposit(amount)) {
      println("That was an invalid amount.")
    }
  }

  private def withdraw(a: Account): Unit = {
    println("How much would you like to withdraw?")
    val amount = readInt()
    if (!a.withdraw(amount)) {
      println("That was an invalid amount.")
    }
  }

  private def changeAddress(c: Customer): Unit = {
    println("Enter the new address. Finish with a blank line.")
    c.changeAddress(readAddress())
  }

  private val menu = """Select one of the following options:
1. Create Customer
2. Select Customer
3. Create Account
4. Close Account
5. Select Account
6. Deposit to Account
7. Withdraw from Account
8. Check Account Balance
9. Change Address
10. Quit"""
}