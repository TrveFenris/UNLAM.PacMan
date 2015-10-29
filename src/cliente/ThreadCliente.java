package cliente;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ThreadCliente extends Thread {
    private Socket socket;

    public ThreadCliente(Socket socket) {
        super("ThreadCliente");
        this.socket = socket;
    }

    public void run() {
        DataInputStream datos;
        String temp = null;
        try {
            do {
                if (temp != null) {
                    System.out.println(temp);
                }
                datos = new DataInputStream(socket.getInputStream());
            } while ((temp = datos.readUTF()) != null);
        }
        catch (IOException e) {
            try {
                socket.close();
            }
            catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}
