package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import game.Partida;
import paquetes.Paquete;
import paquetes.PaqueteBolitaEliminada;
import paquetes.PaqueteBuscarPartida;
import paquetes.PaqueteCoordenadas;
import paquetes.PaqueteID;
import paquetes.PaqueteSesion;
import paquetes.PaqueteSkins;

public class ThreadServer extends Thread {

    private Socket clientSocket;
    private Server servidor;
    private DataBase database;
    private Usuario user;
    
    public ThreadServer(Server servidor, Usuario usuario) {
        clientSocket = usuario.getSocket();
        this.servidor=servidor;
        this.database=servidor.getDatabase(); 
        usuario.setNombre("");
        user = usuario;
    }

    public synchronized  void run() {
        try {
        	boolean responder;
        	boolean run = true;
        	while(run){
        		responder = true;
        		DataInputStream data= new DataInputStream(clientSocket.getInputStream());
            	ObjectInputStream is = new ObjectInputStream(data);
        		//paquete=(PaqueteSesion)is.readObject();
        		Paquete paquete=(Paquete)is.readObject();
                if (!clientSocket.isClosed()) {
    	            DataOutputStream d = new DataOutputStream(clientSocket.getOutputStream());
    	            ObjectOutputStream o = new ObjectOutputStream(d);
    	            //ACCIONES A REALIZAR SEGUN EL TIPO DE PAQUETE RECIBIDO
    	            switch(paquete.getTipo()) {
						case BOLITA_ELIMINADA:
							PaqueteBolitaEliminada paqBolita = (PaqueteBolitaEliminada)paquete;
							for(Usuario u : servidor.getUsuariosEnPartida(user.getPartida())){
		            			if(u.getSocket()!=clientSocket){
		            				DataOutputStream ds = new DataOutputStream(u.getSocket().getOutputStream());
		            				ObjectOutputStream os = new ObjectOutputStream(ds); 
		            				os.writeObject(paqBolita);
		            			}
							}
							//paquete = paqBolita;
							//responder=false;
							break;
						case BUSCAR_PARTIDA:
							break;
						case COORDENADAS:
							PaqueteCoordenadas paqCoord = (PaqueteCoordenadas)paquete;
							for(Usuario u : servidor.getUsuariosEnPartida(user.getPartida())){
		            			if(u.getSocket()!=clientSocket){
		            				DataOutputStream ds = new DataOutputStream(u.getSocket().getOutputStream());
		            				ObjectOutputStream os = new ObjectOutputStream(ds); 
		            				os.writeObject(paqCoord);
		            			}
		            		}
							//paquete = paqCoord;
							//responder=false;
							break;
						
						case ID: break; //el server no deberia recibir este paquete
						
						case LOGIN:
							break;
						case LOGOUT:
							break;
							
						case PARTIDA: break; //el server no deberia recibir este paquete
						
						case REGISTRO:
							break;
						
						case SESION:
							PaqueteSesion paqSesion = (PaqueteSesion)paquete;
							switch(paqSesion.getSolicitud()){
								case LOGIN:
									user.setNombre(paqSesion.getNombre());
									System.out.println(user.getNombre()+" se ha conectado al servidor");
									paqSesion.setResultado(true);
									//paq.setResultado(database.verificarDatos(paq.getNombre(), paq.getPassword()));
									paquete = paqSesion;
									break;
								case LOGOUT:
									paqSesion.setResultado(true);
									run=false;
									paquete = paqSesion;
									break;
								case REGISTRO:
									paqSesion.setResultado(false);
									//paquete.setResultado(database.registrarUsuario(paquete.getNombre(), paquete.getPassword()));
									run=false;
									paquete = paqSesion;
									break;
								case BUSCAR_PARTIDA:
									paquete = enviarListaDePartidas();
									break;
								case UNIRSE_PARTIDA:
									System.out.println(paqSesion.getMensaje());
									boolean res=servidor.agregarAPartida(user, paqSesion.getMensaje());
									paqSesion.setResultado(res);
									paquete = paqSesion;
									break;
							}
							break;
						
						case SKINS:
							PaqueteSkins paqSkins = (PaqueteSkins)paquete;
							//paquete = paqSkins;
							break;
							
						case UNIRSE_PARTIDA:
							break;
						
						default:
							System.out.println("Paquete desconocido.");
							break;
    	            }
    	            if(responder!=false)
    	            	o.writeObject(paquete);
                }
        	}
        	 clientSocket.close();
        	 servidor.eliminarCliente(user);
        	 System.out.println(user.getNombre()+" se ha desconectado del servidor");
        }
        catch(EOFException e){
            try {
                clientSocket.close();
                servidor.eliminarCliente(user);
                System.out.println(user.getNombre()+" se ha desconectado del servidor");
            }
            catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        catch(IOException e) {
        	e.printStackTrace();
            try {
                clientSocket.close();
                servidor.eliminarCliente(user);
                System.out.println(user.getNombre()+" se ha desconectado del servidor");
            }
            catch (IOException e1) {
                e1.printStackTrace();
            }
        } catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
    }
    
    private PaqueteBuscarPartida enviarListaDePartidas(){
    	PaqueteBuscarPartida paquete = new PaqueteBuscarPartida();
    	System.out.println("PREPARANDO LISTA");
    	for(String partida : servidor.getPartidas()){
    		System.out.println("S: "+partida);
    		paquete.agregarPartida(partida, servidor.getCantJugadores(partida));
    	}
        return paquete;
    }
}
