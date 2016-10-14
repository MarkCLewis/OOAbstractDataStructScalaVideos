package networking

import java.rmi.server.UnicastRemoteObject
import java.rmi.Naming
import java.rmi.registry.LocateRegistry
import scala.collection.mutable
import java.rmi.RemoteException

@remote trait RemoteServer {
  def connect(client: RemoteClient): Unit
  def disconnect(client: RemoteClient): Unit
  def getClients: Seq[RemoteClient]
}

object RMIChatServer extends UnicastRemoteObject with App with RemoteServer {
  LocateRegistry.createRegistry(1099)
  Naming.rebind("ChatServer", this)
  
  private val clients = mutable.Buffer[RemoteClient]()

  def connect(client: RemoteClient): Unit = {
    clients += client
    sendUpdate
  }
  
  def disconnect(client: RemoteClient): Unit = {
    clients -= client
    sendUpdate
  }
  
  def getClients: Seq[RemoteClient] = {
    clients
  }
  
  private def sendUpdate: Unit = {
    val deadClients = clients.filter { c => 
      try {
        c.clientUpdate(clients)
        false
      } catch {
        case ex: RemoteException => true
      }
    }
    clients --= deadClients
  }
}