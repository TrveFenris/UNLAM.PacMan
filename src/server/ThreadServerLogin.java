package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

public class ThreadServerLogin extends Thread {

    private Socket clientSocket;
    private ArrayList<Socket> socketList;
    private Server servidor;
    private PaqueteLogin paquete;
    
    public ThreadServerLogin(Socket socket, ArrayList<Socket> sockets,Server serv) {
       	//super("ThreadServer");
        clientSocket = socket;
        socketList = sockets;
        servidor=serv;
    }

    public void run() {
        DataInputStream data;
        try {
        	data= new DataInputStream(clientSocket.getInputStream());
        	ObjectInputStream is = new ObjectInputStream(data);
        	paquete=(PaqueteLogin)is.readObject();
            try {
                 if (!clientSocket.isClosed()) {
                	 DataOutputStream d = new DataOutputStream(clientSocket.getOutputStream());
                     ObjectOutputStream o = new ObjectOutputStream(d);
                     //VERIFICAR USUARIO EN LA DATABASE
                     paquete.setAck(true);
                     //
                     o.writeObject(paquete);
                 }
            }
            catch (IOException e) {
            	e.printStackTrace();
            }
            //NUNCA SE LLEGA A ESTE PUNTO
        }
        catch(EOFException e){
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
        } catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
    }
}
