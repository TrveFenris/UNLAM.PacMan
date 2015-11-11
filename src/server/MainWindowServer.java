package server;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainWindowServer extends JFrame {
	//Thread de escucha del servidor
	private class ListenThread extends Thread {
		public void run() {
			while (bandera) {
				cliente = servidor.aceptarConexion();
	            if (cliente != null){
	            	new ThreadServerSesion(cliente,servidor,servidor.getDatabase()).start();
	            	clientes.add(cliente);
	            	System.out.println(clientes.size()+"\t"+servidor.getListaSockets().size());
	            }
	        }
			System.out.println("FIN DEL THREAD");
			detenerPartidas();
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
	public static MainWindowServer frame;
	
	private Server servidor = null;
	private int puerto = 5070;
	private boolean bandera;
	private int maxClientes=6;
	private Socket cliente = null;
	private ListenThread threadEscucha;
	private JLabel lblClientesConectados;
	private ArrayList<String> nombres;
	private ArrayList<Socket> clientes;
	private JLabel lblPartidas;
	private JTextArea textAreaNombres;
	private JTextArea textAreaPartidas;
	private JButton btnCrearPartida;
	private JButton btnCerrarServidor;
	private String auxNombrePartida;
	private JLabel lblCantJugadores;
	private JTextArea textAreaCantJugadores;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new MainWindowServer();
					frame.setVisible(true);			
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainWindowServer() {
		setResizable(false);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				mensajeSalida();
			}
		});
		//INICIALIZACION DE ARRAYLISTS
		nombres = new ArrayList<String>();
		clientes = new ArrayList<Socket>();
		
		setTitle("Server PacMan");
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 448, 333);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{30, 40, 0, 50, 0, 133, 83, 30, 0};
		gbl_contentPane.rowHeights = new int[]{30, 30, 30, 30, 30, 100, 30, 30, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0, 0.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JLabel lblServer = new JLabel("SERVER");
		lblServer.setForeground(SystemColor.textHighlight);
		lblServer.setFont(new Font("Comic Sans MS", Font.BOLD | Font.ITALIC, 18));
		GridBagConstraints gbc_lblServer = new GridBagConstraints();
		gbc_lblServer.gridwidth = 5;
		gbc_lblServer.insets = new Insets(0, 0, 5, 5);
		gbc_lblServer.gridx = 1;
		gbc_lblServer.gridy = 0;
		contentPane.add(lblServer, gbc_lblServer);
		
		JLabel lblNombre = new JLabel("Nombre");
		GridBagConstraints gbc_lblNombre = new GridBagConstraints();
		gbc_lblNombre.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombre.gridx = 1;
		gbc_lblNombre.gridy = 1;
		contentPane.add(lblNombre, gbc_lblNombre);
		
		textFieldNombre = new JTextField();
		textFieldNombre.setEditable(false);
		GridBagConstraints gbc_textFieldNombre = new GridBagConstraints();
		gbc_textFieldNombre.gridwidth = 4;
		gbc_textFieldNombre.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldNombre.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldNombre.gridx = 3;
		gbc_textFieldNombre.gridy = 1;
		contentPane.add(textFieldNombre, gbc_textFieldNombre);
		textFieldNombre.setColumns(10);
		
		JLabel lblIp = new JLabel("IP");
		GridBagConstraints gbc_lblIp = new GridBagConstraints();
		gbc_lblIp.insets = new Insets(0, 0, 5, 5);
		gbc_lblIp.gridx = 1;
		gbc_lblIp.gridy = 2;
		contentPane.add(lblIp, gbc_lblIp);
		
		textFieldIP = new JTextField();
		textFieldIP.setEditable(false);
		GridBagConstraints gbc_textFieldIP = new GridBagConstraints();
		gbc_textFieldIP.gridwidth = 4;
		gbc_textFieldIP.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldIP.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldIP.gridx = 3;
		gbc_textFieldIP.gridy = 2;
		contentPane.add(textFieldIP, gbc_textFieldIP);
		textFieldIP.setColumns(10);
		
		JLabel lblPuerto = new JLabel("Puerto");
		GridBagConstraints gbc_lblPuerto = new GridBagConstraints();
		gbc_lblPuerto.insets = new Insets(0, 0, 5, 5);
		gbc_lblPuerto.gridx = 1;
		gbc_lblPuerto.gridy = 3;
		contentPane.add(lblPuerto, gbc_lblPuerto);
		
		textFieldPuerto = new JTextField();
		textFieldPuerto.setEditable(false);
		GridBagConstraints gbc_textFieldPuerto = new GridBagConstraints();
		gbc_textFieldPuerto.gridwidth = 4;
		gbc_textFieldPuerto.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldPuerto.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldPuerto.gridx = 3;
		gbc_textFieldPuerto.gridy = 3;
		contentPane.add(textFieldPuerto, gbc_textFieldPuerto);
		textFieldPuerto.setColumns(10);
		
		//SERVIDOR
		crearServidor();
		
		textFieldNombre.setText(servidor.getNombreHost());
		textFieldPuerto.setText(Integer.toString(servidor.getPuerto()));
		textFieldIP.setText(servidor.getIPHost());
		
		lblClientesConectados = new JLabel("Clientes conectados");
		GridBagConstraints gbc_lblClientesConectados = new GridBagConstraints();
		gbc_lblClientesConectados.gridwidth = 3;
		gbc_lblClientesConectados.insets = new Insets(0, 0, 5, 5);
		gbc_lblClientesConectados.gridx = 1;
		gbc_lblClientesConectados.gridy = 4;
		contentPane.add(lblClientesConectados, gbc_lblClientesConectados);
		
		lblPartidas = new JLabel("Partidas");
		GridBagConstraints gbc_lblPartidas = new GridBagConstraints();
		gbc_lblPartidas.insets = new Insets(0, 0, 5, 5);
		gbc_lblPartidas.gridx = 5;
		gbc_lblPartidas.gridy = 4;
		contentPane.add(lblPartidas, gbc_lblPartidas);
		
		lblCantJugadores = new JLabel("Cant. jugadores");
		GridBagConstraints gbc_lblCantJugadores = new GridBagConstraints();
		gbc_lblCantJugadores.insets = new Insets(0, 0, 5, 5);
		gbc_lblCantJugadores.gridx = 6;
		gbc_lblCantJugadores.gridy = 4;
		contentPane.add(lblCantJugadores, gbc_lblCantJugadores);
		
		textAreaNombres = new JTextArea();
		textAreaNombres.setEditable(false);
		textAreaNombres.setBackground(SystemColor.control);
		GridBagConstraints gbc_textAreaNombres = new GridBagConstraints();
		gbc_textAreaNombres.gridwidth = 3;
		gbc_textAreaNombres.insets = new Insets(0, 0, 5, 5);
		gbc_textAreaNombres.fill = GridBagConstraints.BOTH;
		gbc_textAreaNombres.gridx = 1;
		gbc_textAreaNombres.gridy = 5;
		contentPane.add(textAreaNombres, gbc_textAreaNombres);
		
		textAreaPartidas = new JTextArea();
		textAreaPartidas.setBackground(SystemColor.control);
		textAreaPartidas.setEditable(false);
		GridBagConstraints gbc_textAreaPartidas = new GridBagConstraints();
		gbc_textAreaPartidas.insets = new Insets(0, 0, 5, 5);
		gbc_textAreaPartidas.fill = GridBagConstraints.BOTH;
		gbc_textAreaPartidas.gridx = 5;
		gbc_textAreaPartidas.gridy = 5;
		contentPane.add(textAreaPartidas, gbc_textAreaPartidas);
		
		btnCerrarServidor = new JButton("Cerrar servidor");
		btnCerrarServidor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mensajeSalida();
			}
		});
		
		textAreaCantJugadores = new JTextArea();
		textAreaCantJugadores.setEditable(false);
		textAreaCantJugadores.setBackground(SystemColor.control);
		GridBagConstraints gbc_textAreaCantJugadores = new GridBagConstraints();
		gbc_textAreaCantJugadores.insets = new Insets(0, 0, 5, 5);
		gbc_textAreaCantJugadores.fill = GridBagConstraints.BOTH;
		gbc_textAreaCantJugadores.gridx = 6;
		gbc_textAreaCantJugadores.gridy = 5;
		contentPane.add(textAreaCantJugadores, gbc_textAreaCantJugadores);
		GridBagConstraints gbc_btnCerrarServidor = new GridBagConstraints();
		gbc_btnCerrarServidor.gridwidth = 3;
		gbc_btnCerrarServidor.insets = new Insets(0, 0, 5, 5);
		gbc_btnCerrarServidor.gridx = 1;
		gbc_btnCerrarServidor.gridy = 6;
		contentPane.add(btnCerrarServidor, gbc_btnCerrarServidor);
		
		btnCrearPartida = new JButton("Crear partida");
		btnCrearPartida.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ingresarNombrePartida();
			}
		});
		GridBagConstraints gbc_btnCrearPartida = new GridBagConstraints();
		gbc_btnCrearPartida.gridwidth = 2;
		gbc_btnCrearPartida.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnCrearPartida.insets = new Insets(0, 0, 5, 5);
		gbc_btnCrearPartida.gridx = 5;
		gbc_btnCrearPartida.gridy = 6;
		contentPane.add(btnCrearPartida, gbc_btnCrearPartida);
		bandera=true;
		threadEscucha=new ListenThread();
		threadEscucha.start();
		System.out.println("SERVIDOR CREADO");
	}
	
	private void mensajeSalida() {
		int option = JOptionPane.showConfirmDialog(frame,
			    "¿Esta seguro que quiere salir?",
			    "Saliendo del juego",
			    JOptionPane.YES_NO_OPTION);
		if(option == JOptionPane.YES_OPTION) {
			threadEscucha.pararThread();
			frame.dispose();
		}
	}
	
	public void agregarNombre(String nombre){
		nombres.add(nombre);
		actualizarListaDeNombres();
	}
	
	public void removerNombre(String nombre){
		nombres.remove(nombre);
		actualizarListaDeNombres();
	}
	
	public void actualizarListaDeNombres(){
		textAreaNombres.setText("");
		for(Iterator<String>s=nombres.iterator();s.hasNext();){
			String cadena=s.next();
			textAreaNombres.setText(textAreaNombres.getText()+cadena+"\n");
		}
	}
	
	public void actualizarListaDePartidas(){
		textAreaPartidas.setText("");
		textAreaCantJugadores.setText("");
		for(Iterator<ThreadServerPartida>s=servidor.getPartidas().iterator();s.hasNext();){
			ThreadServerPartida thread=s.next();
			textAreaPartidas.setText(textAreaPartidas.getText()+thread.getNombre()+"\n");
			textAreaCantJugadores.setText(textAreaCantJugadores.getText()+thread.getCantJugadores()+" \n");
		}
	}
	
	/**
	 * Crea una nueva partida, lanzando un nuevo ThreadServerPartida.
	 * @param nombre -El nombre de la partida.
	 */
	public void crearPartida(String nombre){
		auxNombrePartida = nombre;
		if(auxNombrePartida!=null&&auxNombrePartida!=""){
			ThreadServerPartida t = new ThreadServerPartida(servidor, auxNombrePartida);
			servidor.agregarPartida(t);
			t.start();
			actualizarListaDePartidas();
		}
	}
	
	/**
	 * Detiene todos los threads de partidas que esten en ejecucion.
	 */
	public void detenerPartidas(){
		for(Iterator<ThreadServerPartida>s=servidor.getPartidas().iterator();s.hasNext();){
			ThreadServerPartida thread=s.next();
			thread.detener();
		}
	}
	
	/**
	 * Lanza una ventana para el ingreso del nombre de la partida.
	 */
	public void ingresarNombrePartida(){
		IngresoNombrePartida ventana = new IngresoNombrePartida(frame);
		ventana.setVisible(true);
	}
	
	private void crearServidor(){
		boolean serverCreado=false;
		int maxPuertosABuscar=50;;
		while(serverCreado!=true && maxPuertosABuscar>0){
			try {
				servidor = new Server(puerto, maxClientes,this);
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
