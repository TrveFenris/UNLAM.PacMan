package server;

import game.Mapa;
import game.Partida;
import gameobject.Bolita;
import gameobject.Fantasma;
import gameobject.Jugador;
import gameobject.Pacman;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.SocketException;
import java.util.Iterator;
import java.util.Random;

import paquetes.Paquete;
import paquetes.PaqueteAbandonarPartida;
import paquetes.PaqueteBolitaEliminada;
import paquetes.PaqueteBuscarPartida;
import paquetes.PaqueteCoordenadas;
import paquetes.PaqueteID;
import paquetes.PaqueteLanzarPartida;
import paquetes.PaqueteLogin;
import paquetes.PaqueteLogout;
import paquetes.PaquetePartida;
import paquetes.PaqueteRegistro;
import paquetes.PaqueteSkins;
import paquetes.PaqueteUnirsePartida;
import paquetes.PaquetejugadorListo;
import punto.Punto;

public class ThreadServer extends Thread {

    private Server servidor;
    private DataBase database;
    private Usuario user;
    private String partida; //Utilizado para cachear la partida en la que se encuentra el usuario
    private boolean running;
    private boolean partidaCorriendo;
    
    public ThreadServer(Server servidor, Usuario usuario) {
        this.servidor=servidor;
        this.database=servidor.getDatabase(); 
        usuario.setNombre("");
        user = usuario;
        running = true;
        partidaCorriendo=false;
    }
    
    public void setPartidaCorriendo(boolean valor){
    	partidaCorriendo=valor;
    }

    public synchronized  void run() {
        try {
        	ObjectInputStream is = new ObjectInputStream(new DataInputStream(user.getSocket().getInputStream()));
        	//ObjectInputStream is = new ObjectInputStream(new BufferedInputStream(user.getSocket().getInputStream()));
        	while(running){
        		Paquete paquete=(Paquete)is.readObject();
                if (!user.getSocket().isClosed()) {
                	ObjectOutputStream o = user.getOutputStream();
    	            //ACCIONES A REALIZAR SEGUN EL TIPO DE PAQUETE RECIBIDO
    	            switch(paquete.getTipo()) {
    	            	case ABANDONAR_LOBBY:
    	            		servidor.eliminarDePartida(user, user.getPartida());
    	            		partida="";
    	            		user.setPartida("");
    	            		break;
    	            	case ABANDONAR_PARTIDA:
    	            		PaqueteAbandonarPartida paq = (PaqueteAbandonarPartida)paquete;
    	            		//servidor.eliminarDePartida(user, user.getPartida());
    	            		//if(paq.isPacman()){
    	            			for(Usuario u : servidor.getUsuariosEnPartida(user.getPartida())){
    		            			if(/*u.getSocket()!=user.getSocket() && */!u.getSocket().isClosed()){
    		            				ObjectOutputStream os = u.getOutputStream();
    		            				os.writeObject(paq);
    		            				os.flush();
    		            			}
    		            			u.setPartida("");
    							}
    	            			servidor.eliminarTodosLosJugadoresDePartida(partida);
    	            		//}
    	            		//else{
    	            		//	servidor.eliminarDePartida(user, partida);
    	            		//}
    	            		partida="";
    	            		user.setPartida("");
    	            		break;
						case BOLITA_ELIMINADA:
							if(partidaCorriendo){
								PaqueteBolitaEliminada paqBolita = (PaqueteBolitaEliminada)paquete;
								Partida pa = servidor.getNombresDePartida().get(partida);
								servidor.getSemaforoBolita(partida).lock();
								pa.getMapa().removerBolita(paqBolita.getIndice());
								servidor.getSemaforoBolita(partida).unlock();
								for(Usuario u : servidor.getUsuariosEnPartida(user.getPartida())){
			            			if(u.getSocket()!=user.getSocket() && !u.getSocket().isClosed()){
			            				ObjectOutputStream os = u.getOutputStream();
			            				u.getSemaforo().lock();
			            				os.writeObject(paqBolita);
			            				os.flush();
			            				u.getSemaforo().unlock();
			            			}
								}
							}
							break;
							
						case BUSCAR_PARTIDA:
							o.writeObject(enviarListaDePartidas());
							o.flush();
							break;
							
						case COORDENADAS:
							if(partidaCorriendo){
								PaqueteCoordenadas paqCoord = (PaqueteCoordenadas)paquete;
								Partida p = servidor.getNombresDePartida().get(partida);
								for(Jugador j : p.getJugadores()){
									if(j.getID()==paqCoord.getIDJugador()){
										calcularColisiones(j);
										servidor.getSemaforoJugador(partida).lock();
										j.setLocation(paqCoord.getCoordenadas().getX(), paqCoord.getCoordenadas().getY());
										j.cambiarSentido(paqCoord.getDireccion());
										servidor.getSemaforoJugador(partida).unlock();
										break;
									}
								}
								for(Usuario u : servidor.getUsuariosEnPartida(partida)){
			            			if(u.getSocket()!=user.getSocket() && !u.getSocket().isClosed()&&u.getId()!=paqCoord.getIDJugador()){
			            				ObjectOutputStream os = u.getOutputStream();
			            				u.getSemaforo().lock();
			            				os.writeObject((Paquete)paqCoord);
			            				os.flush();
			            				u.getSemaforo().unlock();
			            			}
			            		}
							}
							break;
						case ID: break; //el server no deberia recibir este paquete
						
						case JUGADOR_ELIMINADO:
							break;
						case JUGADOR_LISTO:
							PaquetejugadorListo paqListo = (PaquetejugadorListo)paquete;
							System.out.println("El usuario "+user.getNombre()+" esta listo: "+paqListo.isReady());
							user.setReady(paqListo.isReady());
							break;
						
						case LANZAR_PARTIDA:
							PaqueteLanzarPartida paqLaunch = (PaqueteLanzarPartida) paquete;
							System.out.println("Lanzando partida -> Usuarios en la partida: "+servidor.getUsuariosEnPartida(partida).size());
							if(servidor.getUsuariosEnPartida(partida)!=null && servidor.getUsuariosEnPartida(partida).size()>=2) {
								System.out.println("Cantidad de usuarios alcanzada.");
								int usuariosListos = 0;
								for(Usuario u : servidor.getUsuariosEnPartida(partida)) {
									if(u.isReady()) {
										usuariosListos++;
									}
								}
								System.out.println("Usuarios listos: "+usuariosListos);
								if(usuariosListos>=2) { //TODO Cambiar por mayor a 2.
									System.out.println("Cantidad de usuarios listos alcanzada.");
									//TODO Unificar (si es posible) estos asquerosos for each, en un unico for each.
									paqLaunch.setReady(true);
									for(Usuario u : servidor.getUsuariosEnPartida(user.getPartida())){
				            			if(!u.getSocket().isClosed()){
				            				ObjectOutputStream os = u.getOutputStream();
				            				os.writeObject(paqLaunch);
				            				os.flush();
				            			}
				            		}
									System.out.println("paqLaunch enviado");
									Partida part = servidor.getNombresDePartida().get(partida);
									Random r = new Random();
									int i=0;
									boolean hayPacman = false;
									System.out.println();
									for(Usuario us : servidor.getUsuariosEnPartida(user.getPartida())){
										part.sortearIDs();
										int id = part.getIdsDisponibles().get(0);
										part.tomarID(id);
										user.setId(id);
										if(r.nextBoolean()&&!hayPacman){
											hayPacman=true;
											//El punto original es (15,35)
											//En el mapa nuevo es (25,55)
											Pacman pac = new Pacman(new Punto(25,55), us.getNombre(), us.getSkinPacman(), id);
											servidor.agregarJugadorAPartida(pac, partida);
											user.setJugador(pac);
											
										}
										else {
											if(!hayPacman&&i==servidor.getUsuariosEnPartida(user.getPartida()).size()-1){
												Pacman pac = new Pacman(new Punto(25,55), us.getNombre(), us.getSkinPacman(), id);
												servidor.agregarJugadorAPartida(pac, partida);
												user.setJugador(pac);
											}
											else {
												Fantasma fan = new Fantasma(new Punto(25,55), us.getNombre(), us.getSkinFantasma(), id);
												servidor.agregarJugadorAPartida(fan, partida);
												user.setJugador(fan);
											}
										}
										ObjectOutputStream os = us.getOutputStream();
										os.writeObject(new PaqueteID(id));
										os.flush();
										i++;
									}
									System.out.println("IDs enviados");
									Partida y = servidor.getNombresDePartida().get(partida);
									y.setActiva(true);
									for(Usuario u : servidor.getUsuariosEnPartida(user.getPartida())){
				            			if(!u.getSocket().isClosed()){
				            				ObjectOutputStream os = u.getOutputStream();
											os.writeObject(new PaquetePartida(servidor.getNombresDePartida().get(partida)));
											os.flush();
				            			}
				            		}
									System.out.println("partidas enviadas");
									for(Usuario u: servidor.getUsuariosEnPartida(user.getPartida())){
										u.getSesion().setPartidaCorriendo(true);
									}
								}
								else {
									System.out.println("false 1");
									paqLaunch.setReady(false);
									o.writeObject(paqLaunch);
									o.flush();
								}
							}
							else {
								System.out.println("false 2");
								paqLaunch.setReady(false);
								o.writeObject(paqLaunch);
								o.flush();
							}
							break;
						
						case LOGIN:
							PaqueteLogin paqLogin = (PaqueteLogin) paquete;
							user.setNombre(paqLogin.getNombreUsuario());
							System.out.println(user.getNombre()+" se ha conectado al servidor");
							paqLogin.setResultado(true);
							//paqLogin.setResultado(database.verificarDatos(paqLogin.getNombreUsuario(), paqLogin.getPassword()));
							o.writeObject(paqLogin);
							o.flush();
							break;
							
						case LOGOUT:
							PaqueteLogout paqLogout = (PaqueteLogout) paquete;
							paqLogout.setResultado(true);
							running=false;
							o.writeObject(paqLogout);
							o.flush();
							break;	
						
						case PARTIDA: break; //el server no deberia recibir este paquete
						
						case REGISTRO:
							PaqueteRegistro paqReg = (PaqueteRegistro) paquete;
							paqReg.setResultado(false);
							//paqReg.setResultado(database.registrarUsuario(paqReg.getNombreUsuario(), paqReg.getPassword()));
							running=false;
							o.writeObject(paqReg);
							o.flush();
							break;
						
						case SCORE:
							break;
							
						case SERVIDOR_LLENO: break; //No deberia recibirlo
						/*
						case SKINS:
							PaqueteSkins paqSkins = (PaqueteSkins)paquete;
							//int idJugador = user.getId();
							//String partidaJugador = user.getPartida();
							//for(Jugadores j : servidor.get)
							break;
						*/
						case UNIRSE_PARTIDA:
							PaqueteUnirsePartida paqUnir = (PaqueteUnirsePartida)paquete;
							boolean res = servidor.agregarUsuarioAPartida(user, paqUnir.getNombrePartida());
							paqUnir.setResultado(res);
							partida = paqUnir.getNombrePartida();
							o.writeObject(paqUnir);
							o.flush();
							if(res){
								System.out.println("Esperando skins de: "+user.getNombre());
								PaqueteSkins paqSkinsUser = (PaqueteSkins) is.readObject();
								partida = paqUnir.getNombrePartida();
								user.setSkinPacman(paqSkinsUser.getSkinPacman());
								user.setSkinFantasma(paqSkinsUser.getSkinFantasma());
								System.out.println("Skins recibidas.");
							}
							break;

						default:
							System.out.println("Paquete desconocido.");
							break;
    	            }
                }
        	}
        	servidor.eliminarCliente(user);
        	System.out.println(user.getNombre()+" se ha desconectado del servidor");
        }
        catch(EOFException e){
        		running=false;
                servidor.eliminarCliente(user);
                System.out.println(user.getNombre()+" se ha desconectado del servidor");
        }
        catch(SocketException e){
        	running=false;
        	System.out.println("SocketException "+ user.getNombre()+user.getId());
        	servidor.eliminarCliente(user);
        	System.out.println(user.getNombre()+" se ha desconectado del servidor");
	    }
        catch(IOException e) {
        	running=false;
        	servidor.eliminarCliente(user);
        	System.out.println(user.getNombre()+" se ha desconectado del servidor");
        } 
        catch (ClassNotFoundException e1) {
			e1.printStackTrace();
        }
    }
    
    private void calcularColisiones(Jugador j) {
    	Mapa map = servidor.getNombresDePartida().get(partida).getMapa();
		if(j.isPacman()) {
			Iterator<Bolita> it = map.getArrayBolitas().iterator();
			while(it.hasNext()) {
				Bolita b = it.next();
				if(b.isAlive() && j.colisionaCon(b)) {
					for(Usuario u : servidor.getUsuariosEnPartida(user.getPartida())){
            			if(!u.getSocket().isClosed()){
            				try {
            					ObjectOutputStream os = u.getOutputStream();
            					os.writeObject(new PaqueteBolitaEliminada(map.getArrayBolitas().indexOf(b), b));
            					os.flush();
            				}
            				catch(IOException e) {
            					System.out.println("Problema al enviar bolita eliminada!");
            					return;
            				}
            			}
            		}
					map.removerBolita(b);
					it = map.getArrayBolitas().iterator();
				}
			}
		}
		else { //Entonces es un fantasma
			
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
    
    public void pararThread(){
    	running = false;
    }
}
