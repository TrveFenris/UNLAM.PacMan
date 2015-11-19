package cliente;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.SystemColor;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.AbstractMap;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LobbyWindow extends JFrame {
	private static final long serialVersionUID = 2633638473228159143L;
	private JPanel contentPane;
	private UserWindow mainWindow;
	private LobbyWindow thisWindow;
	private JComboBox<String> comboBoxPartidas;

	public LobbyWindow(ArrayList<AbstractMap.SimpleImmutableEntry<String, Integer>> datosPartidas, UserWindow main) {
		setResizable(false);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				if(mainWindow != null) {
					//Si cierro la ventana deberia quitarme de la partida
					setVisible(false);
					mainWindow.setVisible(true);
					dispose();
				}
			}
		});
		
		mainWindow = main;
		thisWindow = this;
		
		setTitle("Seleccion de partida");
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
				lanzarJuego();
			}
		});
		contentPane.add(btnUnirse);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(217, 198, 177, 23);
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				mainWindow.setVisible(true);
				dispose();
			}
		});
		contentPane.add(btnCancelar);
		
		textAreaNombres.setText("");
		
		JButton btnJugadorListo = new JButton("Listo");
		btnJugadorListo.setBounds(35, 237, 89, 23);
		contentPane.add(btnJugadorListo);
		
		JButton btnLanzarPartida = new JButton("Lanzar Partida");
		btnLanzarPartida.setBounds(227, 237, 109, 23);
		contentPane.add(btnLanzarPartida);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setBounds(134, 232, 34, 28);
		contentPane.add(lblNewLabel);
		for(SimpleImmutableEntry<String, Integer> s : datosPartidas){
			textAreaNombres.setText(textAreaNombres.getText()+s.getKey()+"\n");
			textAreaCantJugadores.setText(textAreaCantJugadores.getText()+s.getValue()+"\n");
			comboBoxPartidas.addItem(s.getKey());
		}
		
	}

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
		mainWindow.lanzarJuego();
		dispose();
	}
}
