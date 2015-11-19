package cliente;

import game.ConfiguracionSprites;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.AbstractMap;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JList;

import paquetes.PaqueteBuscarPartida;
import paquetes.PaqueteSkins;
import paquetes.PaqueteUnirsePartida;

import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

public class UserWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private UserWindow thisWindow;
	private GameWindow gameWindow;
	private ClientWindow mainWindow;
	private ConfigWindow configWindow;
	private JPanel contentPane;
	private JButton btnCerrarSesion;
	private JButton btnConfig;
	private JButton btnBuscarPartida;
	private JLabel lblBienvenida;
	private JLabel lblCantJugadores;
	private Cliente cliente;
	//CONFIGURACION
	private String userName;
	private int arriba;
	private int abajo;
	private int izquierda;
	private int derecha;
	private ConfiguracionSprites skinPacman;
	private ConfiguracionSprites skinFantasma;
	private JButton btnUnirsePartida;
	private JButton btnActualizar;
	private JList<String> listPartidas;
	private DefaultListModel<String> listModelPartidas;
	
	private ArrayList<AbstractMap.SimpleImmutableEntry<String, Integer>> datos; //Usado para cachear las partidas disponibles en el server
	
	/* UserWindow Constructor */
	//public UserWindow(MainWindow window,String nombre) {
	public UserWindow(ClientWindow window,String nombre, Cliente cliente) {
		setResizable(false);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				if(mainWindow != null) {
					confirmarCerrarSesion();
				}
			}
		});
		this.cliente=cliente;
		mainWindow = window;
		thisWindow = this;
		datos = new ArrayList<AbstractMap.SimpleImmutableEntry<String, Integer>>();
		userName = nombre;
		skinPacman = ConfiguracionSprites.PACMAN_DEFAULT;
		skinFantasma = ConfiguracionSprites.FANTASMA_DEFAULT;
		setTitle("Menu principal");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 441, 263);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblBienvenida = new JLabel("");
		lblBienvenida.setBounds(110, 5, 210, 26);
		lblBienvenida.setForeground(new Color(51, 153, 204));
		lblBienvenida.setFont(new Font("Comic Sans MS", Font.BOLD | Font.ITALIC, 18));
		lblBienvenida.setHorizontalAlignment(SwingConstants.CENTER);
		lblBienvenida.setText("Bienvenid@ "+userName+"! ");
		contentPane.add(lblBienvenida);
		
		btnBuscarPartida = new JButton("Buscar partida (Deprecated)");
		btnBuscarPartida.setBounds(10, 40, 150, 25);
		btnBuscarPartida.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//lanzarJuego();
				buscarPartida();
			}
		});
		contentPane.add(btnBuscarPartida);
		
		btnConfig = new JButton("Configuracion");
		btnConfig.setBounds(10, 120, 150, 25);
		btnConfig.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				lanzarVentanaConfiguracion();
			}
		});
		contentPane.add(btnConfig);
		
		btnCerrarSesion = new JButton("Cerrar sesion");
		btnCerrarSesion.setBounds(10, 160, 150, 25);
		btnCerrarSesion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				confirmarCerrarSesion();
			}
		});
		contentPane.add(btnCerrarSesion);
		
		btnUnirsePartida = new JButton("Unirse a Partida");
		btnUnirsePartida.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cliente.enviarDatosPartida(new PaqueteUnirsePartida(listPartidas.getSelectedValue()));
				PaqueteUnirsePartida paquete = (PaqueteUnirsePartida) cliente.recibirDatosPartida();
				cliente.enviarDatosPartida(new PaqueteSkins(skinPacman, skinFantasma)); //Esto deberia levantarlo de la config window
				if(paquete!=null && paquete.getResultado() == true) {
					System.out.println("Entrando a la partida "+listPartidas.getSelectedValue());
					lanzarJuego();
					//Recibir paquete partida.
				}
				else {
					System.out.println("Error al unirse a la partida");
					return;
				}
			}
		});
		btnUnirsePartida.setBounds(10, 80, 150, 25);
		contentPane.add(btnUnirsePartida);
		
		listModelPartidas = new DefaultListModel<String>();
		listPartidas = new JList<String>(listModelPartidas);
		listPartidas.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				String s = listPartidas.getSelectedValue();
				System.out.println("Seleccionaste la partida "+listPartidas.getSelectedValue());
				Integer cant = 0;
				for(AbstractMap.SimpleImmutableEntry<String, Integer> partida : datos) {
					if(partida.getKey().equals(s)) {
						cant = partida.getValue();
					}
				}
				lblCantJugadores.setText(cant.toString());
			}
		});
		listPartidas.setBounds(180, 40, 175, 145);
		listPartidas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		contentPane.add(listPartidas);
		
		btnActualizar = new JButton("");
		btnActualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cliente.enviarDatosPartida(new PaqueteBuscarPartida());
				PaqueteBuscarPartida paquete = (PaqueteBuscarPartida) cliente.recibirDatosPartida();
				datos = paquete.getPartidas();
				//listPartidas.removeAll();
				listModelPartidas.clear();
				for(AbstractMap.SimpleImmutableEntry<String, Integer> partida : datos) {
					listModelPartidas.addElement(partida.getKey());
				}
			}
		});
		btnActualizar.setBounds(375, 40, 48, 48);
		btnActualizar.setIcon(new ImageIcon("img/icon-reload.gif"));
		contentPane.add(btnActualizar);
		
		JLabel lblJugadores = new JLabel("Jugadores:");
		lblJugadores.setBounds(180, 195, 60, 15);
		contentPane.add(lblJugadores);
		
		lblCantJugadores = new JLabel("");
		lblCantJugadores.setBounds(250, 195, 50, 15);
		contentPane.add(lblCantJugadores);
		
		cargarControles();
	}

	/* Metodos */
	private void confirmarCerrarSesion(){
		int res= JOptionPane.showConfirmDialog(this,
			    "¿Esta seguro?",
			    "Cerrando sesion",
			    JOptionPane.YES_NO_OPTION);
		if(res == JOptionPane.YES_OPTION) {
			mainWindow.resetUserAndPassword();
			mainWindow.setVisible(true);
			cliente.cerrarCliente();
			this.dispose();
		}
	}

	public void lanzarJuego(){
		this.setVisible(false);
		gameWindow = new GameWindow(this);
		gameWindow.setLocationRelativeTo(null);
		gameWindow.setNameLabel(userName);
		gameWindow.setSkinPacman(skinPacman);
		gameWindow.setSkinFantasma(skinFantasma);
		gameWindow.setVisible(true);
		gameWindow.runGameLoop();
		gameWindow.setControles(this.getControles());
	}
	
	private void buscarPartida(){
		ArrayList<AbstractMap.SimpleImmutableEntry<String, Integer>> datos = cliente.buscarPartidas();
		if(datos==null){
			System.out.println("Error al recibir la lista de partidas.");
			return;
		}
		LobbyWindow winPartidas = new LobbyWindow(datos, thisWindow);
		winPartidas.setVisible(true);
		this.setVisible(false);
	}
	
	/**
	 * Crga los controles por defecto.
	 */
	private void cargarControles() {
			this.arriba=KeyEvent.VK_W;
			this.abajo=KeyEvent.VK_S;
			this.derecha=KeyEvent.VK_D;
			this.izquierda=KeyEvent.VK_A;
	}
	
	public void setControles(int arriba, int abajo, int izquierda, int derecha) {
		this.arriba=arriba;
		this.abajo=abajo;
		this.derecha=derecha;
		this.izquierda=izquierda;
	}
	
	/**
	 * Devuelve un vector de enteros con los keyCode de los controles asignados. El orden es: arriba (0), abajo(1), izquierda(2), derecha(3).
	 * @return
	 */
	public int[] getControles() {
		int[] controles=new int[4];
		controles[0]=arriba;
		controles[1]=abajo;
		controles[2]=izquierda;
		controles[3]=derecha;
		return controles;
	}
	
	private void lanzarVentanaConfiguracion() {
		this.setVisible(false);
		configWindow = new ConfigWindow(this);
		configWindow.setLocationRelativeTo(null);
		configWindow.setVisible(true);
	}
	
	public Cliente getCliente(){
		return this.cliente;
	}
	
	public void setSkinPacman(ConfiguracionSprites pacman) {
		skinPacman = pacman;
	}
	public void setSkinFantasma(ConfiguracionSprites fantasma) {
		skinFantasma = fantasma;
	}
}
