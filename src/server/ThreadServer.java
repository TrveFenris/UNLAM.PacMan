package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import paquetes.Paquete;
import paquetes.PaqueteBolitaEliminada;
import paquetes.PaqueteBuscarPartida;
import paquetes.PaqueteCoordenadas;
import paquetes.PaqueteLogin;
import paquetes.PaqueteLogout;
import paquetes.PaqueteRegistro;
import paquetes.PaqueteSesion;
import paquetes.PaqueteSkins;
import paquetes.PaqueteUnirsePartida;

public class ThreadServer extends Thread {

    private Socket clientSocket;
    private Server servidor;
    private DataBase database;
    private Usuario user;
    private String partida; //Utilizado para cachear la partida en la que se encuentra el usuario
    
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
							break;
						case BUSCAR_PARTIDA:
							//PaqueteBuscarPartida paqBuscar = (PaqueteBuscarPartida) paquete;
							//paqBuscar = enviarListaDePartidas();
							o.writeObject(enviarListaDePartidas());
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
							break;
						
						case ID: break; //el server no deberia recibir este paquete
						
						case LOGIN:
							PaqueteLogin paqLogin = (PaqueteLogin) paquete;
							user.setNombre(paqLogin.getNombreUsuario());
							System.out.println(user.getNombre()+" se ha conectado al servidor");
							paqLogin.setResultado(true);
							//paq.setResultado(database.verificarDatos(paq.getNombre(), paq.getPassword()));
							//paquete = paqSesion;
							o.writeObject(paqLogin);
							break;
						case LOGOUT:
							PaqueteLogout paqLogout = (PaqueteLogout) paquete;
							paqLogout.setResultado(true);
							run=false;
							o.writeObject(paqLogout);
							break;
							
						case PARTIDA: break; //el server no deberia recibir este paquete
						
						case REGISTRO:
							PaqueteRegistro paqReg = (PaqueteRegistro) paquete;
							paqReg.setResultado(false);
							//paquete.setResultado(database.registrarUsuario(paqReg.getNombreUsuario(), paqReg.getPassword()));
							run=false;
							//paquete = paqSesion;
							o.writeObject(paqReg);
							break;
						//Case SESION: OBSOLETO
						/*
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
						*/
						case SKINS:
							PaqueteSkins paqSkins = (PaqueteSkins)paquete;
							//int idJugador = user.getId();
							//String partidaJugador = user.getPartida();
							//for(Jugadores j : servidor.get)
								
							//paquete = paqSkins;
							break;
							
						case UNIRSE_PARTIDA:
							PaqueteUnirsePartida paqUnir = (PaqueteUnirsePartida)paquete;
							boolean res = servidor.agregarAPartida(user, paqUnir.getNombrePartida());
							paqUnir.setResultado(res);
							o.writeObject(paqUnir);
							//paquete = paqUnir;
							break;

						default:
							System.out.println("Paquete desconocido.");
							break;
    	            }
    	            /*
    	            if(responder!=false)
    	            	o.writeObject(paquete);
    	            */
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
