package server;

import game.Partida;
import gameobject.Jugador;

import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import punto.Punto;
import rectas.Rectas;

public class WatchGameWindow extends JFrame {

	private static final long serialVersionUID = 5081634637084287852L;
	private JPanel contentPane;
	private boolean gameRunning;
	private WatchGameThread threadEspectador;
	private ServerWindow ventanaServidor;
	private Partida partidaEnCurso;
	
	/* GameWindow constructor */

	public WatchGameWindow(Partida partida, ServerWindow ventana) {
		setResizable(false);
		partidaEnCurso = partida;
		ventanaServidor = ventana;
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
					mensajeSalida();
			}
		});
			
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setBackground(Color.BLACK);
		//contentPane.setOpaque(true);
		gameRunning = true;
		threadEspectador = new WatchGameThread();
		//threadEscucha = new ListenThread();
		//threadEscucha.start();
		this.setTitle("PAC-MAN | Partida: "+partida.getNombre());
	}
	
	private void mensajeSalida(){
		int option = JOptionPane.showConfirmDialog(this,
			    "¿Esta seguro que quiere salir?",
			    "Saliendo del juego",
			    JOptionPane.YES_NO_OPTION);
		if(option == JOptionPane.YES_OPTION){
			gameRunning = false;
			threadEspectador.timer.cancel();
			ventanaServidor.setEnabled(true);
			this.dispose();
		}
	}
	
	public void runGameLoop() {
		//gameLoopThread.start();
	}
	/*
	private void update(){
		for(Iterator<Jugador>j=jugadores.iterator();j.hasNext();) {
			Jugador jug=j.next();
			jug.actualizarUbicacion(mapa.getArrayRectas());
			ultimaDireccion=jug.getTipoUbicacion();
			switch(ultimaDireccion){
				case HORIZONTAL:
					jug.setLeftBound(jug.getRectaActual(0).getPuntoInicialX());
					jug.setRightBound(jug.getRectaActual(0).getPuntoFinalX());
					jug.setUpperBound(jug.getRectaActual(0).getPuntoInicialY());
					jug.setLowerBound(jug.getRectaActual(0).getPuntoInicialY());
					break;
				case VERTICAL:
					jug.setUpperBound(jug.getRectaActual(0).getPuntoInicialY());
					jug.setLowerBound(jug.getRectaActual(0).getPuntoFinalY());
					jug.setLeftBound(jug.getRectaActual(0).getPuntoInicialX());
					jug.setRightBound(jug.getRectaActual(0).getPuntoInicialX());
					
					break;
				case AMBAS:
					//System.out.println("Interseccion");
					for(int i=0;i<2;i++){
						if(jug.getRectaActual(i).getTipo()==Rectas.HORIZONTAL){
							jug.setLeftBound(jug.getRectaActual(i).getPuntoInicialX());
							jug.setRightBound(jug.getRectaActual(i).getPuntoFinalX());
						}
						else
							if(jug.getRectaActual(i).getTipo()==Rectas.VERTICAL){
								jug.setUpperBound(jug.getRectaActual(i).getPuntoInicialY());
								jug.setLowerBound(jug.getRectaActual(i).getPuntoFinalY());
							}
					}
					jug.cambiarSentido(ultimaAccion);
					break;
			}
			jug.mover();
			userWindow.getCliente().enviarPosicion(jug.getLocation());
			semaforo.lock();	     
			try {
				pacmanBot.setLocation(paux.getX(), paux.getY());
			} 
			finally {
				semaforo.unlock();
			}
			restrictBoundaries(jug);
			calcularColisiones (jug);
		}
	}
	*/
	/**
	 * Thread que maneja el Game Loop 
	 * */
	private class WatchGameThread extends Thread {
		private Timer timer;
		
		public void run() {
			System.out.println("Comienza el juego");
			timer = new Timer();
			timer.schedule( new TimerTask() {
			    public void run() {
			       if(gameRunning){
			    	   //update();
			       }
			    }
			 }, 0, 16);
		}
	}
	
	/**
	 * Thread que recibe actualizaciones de los demas jugadores
	 */
/*
	private class ListenThread extends Thread {
		
		private boolean running;
		
		public void run() {
			running = true;
			while(running){
				Punto p = userWindow.getCliente().recibirPosicion();
				if(p!=null){
					semaforo.lock();
					try {
						paux = p;
					} 
					finally {
						semaforo.unlock();
					}
				}
			}
		}
		
		public void pararThread(){
			running = false;
		}
	}
*/
}
