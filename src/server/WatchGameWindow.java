package server;

import game.Partida;
import gameobject.Bolita;
import gameobject.Jugador;

import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import rectas.Recta;
import javax.swing.JLabel;

public class WatchGameWindow extends JFrame {

	private static final long serialVersionUID = 5081634637084287852L;
	private JPanel contentPane;
	private ServerWindow ventanaServidor;
	private Partida partidaEnCurso;
	private WatchGameWindow thisWindow;
	
	/* GameWindow constructor */
	public WatchGameWindow(Partida partida, ServerWindow ventana) {
		setResizable(false);
		thisWindow = this;
		partidaEnCurso = partida;
		ventanaServidor = ventana;
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
					mensajeSalida();
			}
		});
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 806, 625);
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
		partidaEnCurso.getMapa().dibujar(contentPane);
		
		JLabel lblFondo = new JLabel("");
		lblFondo.setBounds(0, 0, 800, 600);
		lblFondo.setIcon(new ImageIcon("img/mapa-rectas.gif"));
		contentPane.add(lblFondo);
		setVisible(true);
	}
	
	private void mensajeSalida(){
		int option = JOptionPane.showConfirmDialog(this,
			    "Esta seguro que quiere salir?",
			    "Saliendo del modo espectador",
			    JOptionPane.YES_NO_OPTION);
		if(option == JOptionPane.YES_OPTION){
			ventanaServidor.setEnabled(true);
			this.dispose();
		}
	}
}
