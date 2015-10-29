package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

public class ThreadServer extends Thread {

    private Socket clientSocket;
    private ArrayList<Socket> socketList;
    private Server servidor;
    
    public ThreadServer(Socket socket, ArrayList<Socket> sockets,Server serv) {
       	//super("ThreadServer");
        clientSocket = socket;
        socketList = sockets;
        servidor=serv;
    }

    public void run() {
        DataInputStream data;
        Iterator<Socket> i;
        String aux = null;
        try {
        	data= new DataInputStream(clientSocket.getInputStream());
            do {
                if (aux != null) {
                    System.out.println(aux);
                    i = socketList.iterator();
                    while (i.hasNext()) {
                        Socket cliente = i.next();
                        try {
                            /* Si el socket extraido es distinto al socket del thread
                             se enviara el msg a todos los usuarios del array list 
                             menos al que envio dicho msg.*/
                            if (!cliente.equals(clientSocket)&&!cliente.isClosed()) {
                                DataOutputStream d = new DataOutputStream(cliente.getOutputStream());
                                d.writeUTF(aux);// envia el mensaje al socket correspondiente.
                            }
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } while ((aux = data.readUTF()) != null);
			//System.out.println("Cerrando cliente");
			//socketList.remove(clientSocket);
			//clientSocket.close();
			//servidor.eliminarCliente();
			//System.out.println("Un cliente se ha desconectado.");
        }
        catch(IOException e) {
        	e.printStackTrace();
            try {
            	System.out.println("Cerrando cliente");
                socketList.remove(clientSocket);
                clientSocket.close();
                servidor.eliminarCliente();
                System.out.println("Un cliente se ha desconectado.");
            }
            catch (IOException e1) {
                e1.printStackTrace();
            }
            finally {
            	System.out.println("La conexion ha finalizado.");
            }
        }
    }
}
