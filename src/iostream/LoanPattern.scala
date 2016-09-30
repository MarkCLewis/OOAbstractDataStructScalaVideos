package iostream

import java.io.FileInputStream
import java.io.BufferedInputStream
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.FileOutputStream
import java.io.BufferedOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

object LoanPattern {
  def useFileInputStream[A](filename: String)(body: FileInputStream => A): A = {
    val fis = new FileInputStream(filename)
    try {
      body(fis)
    } finally {
      fis.close()
    }
  }
  
  def withDOS[A](filename: String)(body: DataOutputStream => A): A = {
    val dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(filename)))
    try {
      body(dos)
    } finally {
      dos.close()
    }
  }
  
  def withDIS[A](filename: String)(body: DataInputStream => A): A = {
    val dis = new DataInputStream(new BufferedInputStream(new FileInputStream(filename)))
    try {
      body(dis)
    } finally {
      dis.close()
    }
  }

  def withOOS[A](filename: String)(body: ObjectOutputStream => A): A = {
    val dos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(filename)))
    try {
      body(dos)
    } finally {
      dos.close()
    }
  }
  
  def withOIS[A](filename: String)(body: ObjectInputStream => A): A = {
    val dis = new ObjectInputStream(new BufferedInputStream(new FileInputStream(filename)))
    try {
      body(dis)
    } finally {
      dis.close()
    }
  }
}