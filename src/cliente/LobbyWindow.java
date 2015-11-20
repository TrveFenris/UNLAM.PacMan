package cliente;

import game.ConfiguracionSprites;

import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import paquetes.PaqueteAbandonarPartida;
import paquetes.PaqueteLanzarPartida;
import paquetes.PaquetejugadorListo;

public class LobbyWindow extends JFrame {
	private static final long serialVersionUID = 2633638473228159143L;
	private JPanel contentPane;
	private UserWindow mainWindow;
	private LobbyWindow thisWindow;
	private JComboBox<String> comboBoxPartidas;
	private JLabel lblReady;
	private boolean ready;
	private int IDJugadorLocal;
	private ListenThread thread;
	private ImageIcon iconoReady;
	private ImageIcon iconoNotReady;

	//public LobbyWindow(ArrayList<AbstractMap.SimpleImmutableEntry<String, Integer>> datosPartidas, UserWindow main) {
	public LobbyWindow(String nombrePartida, UserWindow main, int idJugador) {
		setResizable(false);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				if(mainWindow != null) {
					//Si cierro la ventana deberia quitarme de la partida
					confirmarSalirDelLobby();
				}
			}
		});
		mainWindow = main;
		thisWindow = this;
		IDJugadorLocal = idJugador;
		//setTitle("Seleccion de partida");
		setTitle("Lobby de la partida \""+nombrePartida+"\"");
		iconoReady = new ImageIcon(ConfiguracionSprites.ICONO_READY.getValor());
		iconoNotReady = new ImageIcon(ConfiguracionSprites.ICONO_NOTREADY.getValor());
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblSeleccioneUnaPartida = new JLabel("Seleccione una partida:");
		lblSeleccioneUnaPartida.setBounds(35, 35, 359, 14);
		contentPane.add(lblSeleccioneUnaPartida);
		
		comboBoxPartidas = new JComboBox<String>();
		comboBoxPartidas.setBounds(35, 54, 359, 20);
		contentPane.add(comboBoxPartidas);
		
		JLabel lblNombrePartida = new JLabel("Partida");
		lblNombrePartida.setBounds(106, 79, 34, 14);
		contentPane.add(lblNombrePartida);
		
		JLabel lblCantidadDeJugadores = new JLabel("Cantidad de jugadores");
		lblCantidadDeJugadores.setBounds(251, 79, 109, 14);
		contentPane.add(lblCantidadDeJugadores);
		
		JTextArea textAreaNombres = new JTextArea();
		textAreaNombres.setBounds(35, 98, 177, 65);
		textAreaNombres.setBackground(SystemColor.control);
		contentPane.add(textAreaNombres);
		
		JTextArea textAreaCantJugadores = new JTextArea();
		textAreaCantJugadores.setBounds(217, 98, 177, 65);
		textAreaCantJugadores.setBackground(SystemColor.control);
		contentPane.add(textAreaCantJugadores);
		
		JButton btnUnirse = new JButton("Unirse");
		btnUnirse.setBounds(35, 198, 177, 23);
		btnUnirse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//lanzarJuego();
			}
		});
		contentPane.add(btnUnirse);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(217, 198, 177, 23);
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				confirmarSalirDelLobby();
			}
		});
		contentPane.add(btnCancelar);
		
		textAreaNombres.setText("");
		
		JButton btnJugadorListo = new JButton("Listo");
		btnJugadorListo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!ready) {
					mainWindow.getCliente().enviarDatosPartida(new PaquetejugadorListo(true));
					lblReady.setIcon(iconoReady);
					ready = true;
					//Cambiar el label ready a una tilde
				}
				else {
					mainWindow.getCliente().enviarDatosPartida(new PaquetejugadorListo(false));
					lblReady.setIcon(iconoNotReady);
					ready = false;
					//cambiar el label ready a una cruz
				}
				System.out.println(ready);
			}
		});
		btnJugadorListo.setBounds(35, 237, 89, 23);
		contentPane.add(btnJugadorListo);
		
		JButton btnLanzarPartida = new JButton("Lanzar Partida");
		btnLanzarPartida.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainWindow.getCliente().enviarDatosPartida(new PaqueteLanzarPartida());
			}
		});
		btnLanzarPartida.setBounds(227, 237, 109, 23);
		contentPane.add(btnLanzarPartida);
		
		lblReady = new JLabel();
		lblReady.setBounds(134, 232, 34, 28);
		lblReady.setIcon(iconoNotReady);
		contentPane.add(lblReady);
		thread = new ListenThread();
		thread.start();
		/*
		for(SimpleImmutableEntry<String, Integer> s : datosPartidas){
			textAreaNombres.setText(textAreaNombres.getText()+s.getKey()+"\n");
			textAreaCantJugadores.setText(textAreaCantJugadores.getText()+s.getValue()+"\n");
			comboBoxPartidas.addItem(s.getKey());
		}
		*/
	}
/*
	private void lanzarJuego() {
		if(mainWindow.getCliente().unirseAPartida(comboBoxPartidas.getSelectedItem().toString()) ) {
			System.out.println("Entrando a la partida");
			//Recibir paquete partida.
		}
		else {
			System.out.println("Error al unirse a la partida");
			return;
		}
		this.setVisible(false);
		//mainWindow.lanzarJuego();
		dispose();
	}
	*/
	
	private void confirmarSalirDelLobby(){
		int res= JOptionPane.showConfirmDialog(this,
			    "¿Esta seguro?",
			    "Cerrando sesion",
			    JOptionPane.YES_NO_OPTION);
		if(res == JOptionPane.YES_OPTION) {
			thread.pararThread();
			mainWindow.getCliente().enviarDatosPartida(new PaqueteAbandonarPartida());
			setVisible(false);
			mainWindow.setVisible(true);
			dispose();
			System.out.println("Saliendo de la partida");
			//Aviso al servidor para que me elimine de la partida
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
						mainWindow.lanzarJuego(IDJugadorLocal);
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