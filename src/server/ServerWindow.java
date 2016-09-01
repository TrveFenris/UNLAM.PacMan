package server;

import game.Configuracion;
import game.Partida;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.Socket;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

public class ServerWindow extends JFrame {
	//Thread de escucha del servidor
	private class ListenThread extends Thread {
		public void run() {
			while (bandera) {
				cliente = servidor.aceptarConexion();
	            if (cliente != null){
	            	Usuario u = new Usuario(cliente);
	            	u.setSesion(new ThreadServer(servidor, u));
	            	u.getSesion().start();
	            	servidor.agregarUsuario(u);
	            }
	            actualizarListaDePartidas();
	            actualizarListaDeNombres();
	        }
			System.out.println("FIN DEL THREAD");
			//detenerPartidas();
	        servidor.pararServidor();
	        System.out.println("SERVER CERRADO");
		}
		public void pararThread(){
			bandera=false;
		}
	}
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldNombre;
	private JTextField textFieldIP;
	private JTextField textFieldPuerto;
	public static ServerWindow frame;
	
	private Server servidor = null;
	private boolean bandera;
	private Socket cliente = null;
	private ListenThread threadEscucha;
	private JLabel lblClientesConectados;
	private JLabel lblPartidas;
	private JTextArea textAreaNombres;
	private JButton btnCrearPartida;
	private JButton btnCerrarServidor;
	private JLabel lblCantJugadores;
	private JTextArea textAreaCantJugadores;
	private JList<String> listPartidas;
	private DefaultListModel<String> listModelPartidas;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new ServerWindow();
					frame.setVisible(true);			
				} catch (Exception e) {
					System.out.println("Error al lanzar la ventana del servidor");
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ServerWindow() {
		setResizable(false);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				mensajeSalida();
			}
		});
		
		setTitle("Server PacMan");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 448, 333);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblServer = new JLabel("SERVER");
		lblServer.setBounds(141, 5, 70, 26);
		lblServer.setForeground(SystemColor.textHighlight);
		lblServer.setFont(new Font("Comic Sans MS", Font.BOLD | Font.ITALIC, 18));
		contentPane.add(lblServer);
		
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(35, 41, 37, 14);
		contentPane.add(lblNombre);
		
		textFieldNombre = new JTextField();
		textFieldNombre.setBounds(108, 38, 294, 20);
		textFieldNombre.setEditable(false);
		contentPane.add(textFieldNombre);
		textFieldNombre.setColumns(10);
		
		JLabel lblIp = new JLabel("IP");
		lblIp.setBounds(49, 71, 10, 14);
		contentPane.add(lblIp);
		
		textFieldIP = new JTextField();
		textFieldIP.setBounds(108, 68, 294, 20);
		textFieldIP.setEditable(false);
		contentPane.add(textFieldIP);
		textFieldIP.setColumns(10);
		
		JLabel lblPuerto = new JLabel("Puerto");
		lblPuerto.setBounds(38, 101, 32, 14);
		contentPane.add(lblPuerto);
		
		textFieldPuerto = new JTextField();
		textFieldPuerto.setBounds(108, 98, 294, 20);
		textFieldPuerto.setEditable(false);
		contentPane.add(textFieldPuerto);
		textFieldPuerto.setColumns(10);
		
		//SERVIDOR
		crearServidor();
		
		textFieldNombre.setText(servidor.getNombreHost());
		textFieldPuerto.setText(Integer.toString(servidor.getPuerto()));
		textFieldIP.setText(servidor.getIPHost());
		
		lblClientesConectados = new JLabel("Clientes conectados");
		lblClientesConectados.setBounds(46, 131, 96, 14);
		contentPane.add(lblClientesConectados);
		
		lblPartidas = new JLabel("Partidas");
		lblPartidas.setBounds(234, 131, 39, 14);
		contentPane.add(lblPartidas);
		
		lblCantJugadores = new JLabel("Cant. jugadores");
		lblCantJugadores.setBounds(323, 131, 78, 14);
		contentPane.add(lblCantJugadores);
		
		textAreaNombres = new JTextArea();
		textAreaNombres.setBounds(35, 150, 120, 105);
		textAreaNombres.setEditable(false);
		textAreaNombres.setBackground(SystemColor.control);
		contentPane.add(textAreaNombres);
		
		btnCerrarServidor = new JButton("Cerrar servidor");
		btnCerrarServidor.setBounds(320, 265, 110, 18);
		btnCerrarServidor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mensajeSalida();
			}
		});
		
		textAreaCantJugadores = new JTextArea();
		textAreaCantJugadores.setBounds(323, 150, 105, 105);
		textAreaCantJugadores.setEditable(false);
		textAreaCantJugadores.setBackground(SystemColor.control);
		contentPane.add(textAreaCantJugadores);
		contentPane.add(btnCerrarServidor);
		
		btnCrearPartida = new JButton("Crear partida");
		btnCrearPartida.setBounds(35, 265, 120, 18);
		btnCrearPartida.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ingresarNombrePartida();
			}
		});
		contentPane.add(btnCrearPartida);
		
		JButton btnVerPartida = new JButton("Ver Partida");
		btnVerPartida.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Partida part = servidor.getNombresDePartida().get(listPartidas.getSelectedValue());
				System.out.println("SELECCIONADA: "+listPartidas.getSelectedValue());
				if(part != null && part.getActiva()) {
					new WatchGameWindow(part, frame);
				}
				else {
					if(part != null && !part.getActiva()) {
						JOptionPane.showMessageDialog(frame,
								"No se puede ver la partida: no está activa.",
								 "Error",
								 JOptionPane.ERROR_MESSAGE);
						return;
					}
				}
			}
		});
		btnVerPartida.setBounds(165, 265, 145, 18);
		contentPane.add(btnVerPartida);
		
		listModelPartidas = new DefaultListModel<String>();
		listPartidas = new JList<String>(listModelPartidas);
		listPartidas.setBackground(SystemColor.control);
		listPartidas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listPartidas.setBounds(165, 150, 145, 105);
		contentPane.add(listPartidas);
		
		
		bandera=true;
		threadEscucha=new ListenThread();
		threadEscucha.start();
		System.out.println("SERVIDOR CREADO");
	}
	
	private void mensajeSalida() {
		int option = JOptionPane.showConfirmDialog(frame,
			    "Esta seguro que quiere salir?",
			    "Saliendo del juego",
			    JOptionPane.YES_NO_OPTION);
		if(option == JOptionPane.YES_OPTION) {
			threadEscucha.pararThread();
			for(Usuario u : servidor.getListaUsuarios())
			{
				u.getSesion().pararThread();
			}
			frame.dispose();
		}
	}
	
	public void actualizarListaDeNombres(){
		textAreaNombres.setText("");
		for(Usuario u : servidor.getListaUsuarios()) {
			textAreaNombres.append(u.getNombre()+"\n");
		}
	}
	
	public void actualizarListaDePartidas(){
		textAreaCantJugadores.setText("");
		for(String partida : servidor.getPartidas()) {
			textAreaCantJugadores.append(servidor.getCantUsuariosEnPartida(partida)+"\n");
		}
	}
	/**
	 * Crea una nueva partida, lanzando un nuevo ThreadServerPartida.
	 * @param nombre -El nombre de la partida.
	 */
	public void crearPartida(String nombre){
		if(nombre!=null&&nombre!=""){
			if(servidor.crearPartida(nombre)) {
				actualizarListaDePartidas();
				listModelPartidas.addElement(nombre);
			}
			else {
				JOptionPane.showMessageDialog(this,
						"No se pudo crear la partida: ya existe una partida con el mismo nombre.",
						 "Error",
						 JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
	}
	
	/**
	 * Lanza una ventana para el ingreso del nombre de la partida.
	 */
	public void ingresarNombrePartida(){
		String s = (String)JOptionPane.showInputDialog(
                frame,
                "Ingrese el nombre de la partida:",
                "Nueva Partida",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                null);

		if ((s != null) && (s.length() > 0)) {
			if(s.length() > 10) {
				JOptionPane.showMessageDialog(this,
						"El nombre de la partida no puede contener mas de 10 caracteres.",
						 "Error",
						 JOptionPane.ERROR_MESSAGE);
				return;
			}
			this.crearPartida(s.trim());
			return;
		}
		else {
			if (s != null)
				JOptionPane.showMessageDialog(this,
					"Ingrese un nombre para la partida.",
					 "Error",
					 JOptionPane.ERROR_MESSAGE);
			return;
		}
	}
	
	private void crearServidor(){
		boolean serverCreado=false;
		int puerto=Configuracion.PUERTO_INICIAL.getValor();
		int maxPuertosABuscar=Configuracion.RANGO_PUERTOS.getValor();
		while(serverCreado!=true && maxPuertosABuscar>0){
			try {
				servidor = new Server(puerto, Configuracion.MAX_CLIENTES.getValor(),this);
				serverCreado=true;
			} catch (IOException e) {
				System.out.println("No se puede escuchar desde el puerto " +puerto+", buscando nuevo puerto...");
				puerto++;
				maxPuertosABuscar--;
			}
		}
		if(maxPuertosABuscar<=0){
			System.out.println("No se encontro ningun puerto de escucha disponible");
			System.exit(-1);
		}
	}
}
