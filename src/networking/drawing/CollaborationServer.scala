package networking.drawing

import java.rmi.server.UnicastRemoteObject
import collection.mutable

@remote trait RemoteCollaborationServer {
  def joinCollaboration(col: RemoteCollaborator): Seq[RemoteCollaborator]
}

class CollaborationServer extends UnicastRemoteObject with RemoteCollaborationServer {
  private val collaborators = mutable.Buffer[RemoteCollaborator]()
  
  def joinCollaboration(col: RemoteCollaborator): Seq[RemoteCollaborator] = {
    collaborators += col
    collaborators
  }
}