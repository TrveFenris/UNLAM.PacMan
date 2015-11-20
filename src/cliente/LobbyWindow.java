package cliente;

import game.ConfiguracionSprites;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import paquetes.PaqueteAbandonarLobby;
import paquetes.PaqueteLanzarPartida;
import paquetes.PaquetejugadorListo;

public class LobbyWindow extends JFrame {
	private static final long serialVersionUID = 2633638473228159143L;
	private JPanel contentPane;
	private UserWindow mainWindow;
	private LobbyWindow thisWindow;
	private JLabel lblReady;
	private boolean ready;
	private ListenThread thread;
	private ImageIcon iconoReady;
	private ImageIcon iconoNotReady;

	public LobbyWindow(String nombrePartida, UserWindow main) {
		setResizable(false);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				if(mainWindow != null) {
					confirmarSalirDelLobby();
				}
			}
		});
		mainWindow = main;
		thisWindow = this;
		setTitle("Lobby de la partida \""+nombrePartida+"\"");
		iconoReady = new ImageIcon(ConfiguracionSprites.ICONO_READY.getValor());
		iconoNotReady = new ImageIcon(ConfiguracionSprites.ICONO_NOTREADY.getValor());
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(257, 237, 177, 23);
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				confirmarSalirDelLobby();
			}
		});
		contentPane.add(btnCancelar);
		
		JButton btnJugadorListo = new JButton("Listo");
		btnJugadorListo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!ready) {
					mainWindow.getCliente().enviarDatosPartida(new PaquetejugadorListo(true));
					lblReady.setIcon(iconoReady);
					ready = true;
				}
				else {
					mainWindow.getCliente().enviarDatosPartida(new PaquetejugadorListo(false));
					lblReady.setIcon(iconoNotReady);
					ready = false;
				}
				System.out.println(ready);
			}
		});
		btnJugadorListo.setBounds(10, 204, 89, 23);
		contentPane.add(btnJugadorListo);
		
		JButton btnLanzarPartida = new JButton("Lanzar Partida");
		btnLanzarPartida.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainWindow.getCliente().enviarDatosPartida(new PaqueteLanzarPartida());
			}
		});
		btnLanzarPartida.setBounds(148, 204, 109, 23);
		contentPane.add(btnLanzarPartida);
		
		lblReady = new JLabel();
		lblReady.setBounds(108, 199, 34, 28);
		lblReady.setIcon(iconoNotReady);
		contentPane.add(lblReady);
		thread = new ListenThread();
		thread.start();
	}
	
	private void confirmarSalirDelLobby(){
		int res= JOptionPane.showConfirmDialog(this,
			    "¿Esta seguro?",
			    "Cerrando sesion",
			    JOptionPane.YES_NO_OPTION);
		if(res == JOptionPane.YES_OPTION) {
			thread.pararThread();
			//Aviso al servidor para que me elimine de la partida
			mainWindow.getCliente().enviarDatosPartida(new PaqueteAbandonarLobby());
			setVisible(false);
			mainWindow.setVisible(true);
			dispose();
			System.out.println("Saliendo de la partida");
		}
	}
	
	/**
	 * Thread que espera confirmación de inicio de partida
	 */
	private class ListenThread extends Thread {
		
		private boolean running;
		public void run() {
			running = true;
			while(running){
				PaqueteLanzarPartida p = mainWindow.getCliente().recibirConfirmacionInicioDePartida();
				if(p!=null){
					if(p.isReady()) {
						System.out.println("LANZANDO JUEGO");
						mainWindow.lanzarJuego();
						pararThread();
						dispose();
					}
					else {
						System.out.println("No se pudo lanzar la partida.");
					}
				}
			}
			System.out.println("THREAD \"LanzarJuego\" detenido");
		}

		public void pararThread(){
			running = false;
		}
	}
}