package server;

import game.Partida;
import gameobject.Bolita;
import gameobject.Jugador;

import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import rectas.Recta;

public class WatchGameWindow extends JFrame {

	private static final long serialVersionUID = 5081634637084287852L;
	private JPanel contentPane;
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
		this.setTitle("PAC-MAN | Partida: " + partida.getNombre());
		/* Carga todos los jugadores al JPanel de la ventana */
		for(Jugador jug : partidaEnCurso.getJugadores()) {
			jug.dibujar(contentPane);
		}
		/* Carga todas las bolitas al JPanel de la ventana */
		for(Bolita b : partidaEnCurso.getMapa().getArrayBolitas()) {
			b.dibujar(contentPane);
		}
		/* Carga todas las rectas al JPanel de la ventana */
		for(Recta r : partidaEnCurso.getMapa().getArrayRectas()) {
			r.dibujar(contentPane);
		}
	}
	
	private void mensajeSalida(){
		int option = JOptionPane.showConfirmDialog(this,
			    "Esta seguro que quiere salir?",
			    "Saliendo del modo espectador",
			    JOptionPane.YES_NO_OPTION);
		if(option == JOptionPane.YES_OPTION){
			//gameRunning = false;
			//threadEspectador.timer.cancel();
			ventanaServidor.setEnabled(true);
			this.dispose();
		}
	}
	/*
	public void runGameLoop() {
		//gameLoopThread.start();
	}

	private void update(){
	}
	 */
	/**
	 * Thread que maneja el Game Loop 
	 * */
	/*
	private class WatchGameThread extends Thread {
		private Timer timer;
		
		public void run() {
			timer = new Timer();
			timer.schedule( new TimerTask() {
			    public void run() {
			       if(gameRunning){
			    	   update();
			       }
			    }
			 }, 0, 16);
		}
	}
	*/
}
