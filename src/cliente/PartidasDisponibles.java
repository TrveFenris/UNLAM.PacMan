package cliente;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import server.PaqueteListaPartidas;

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
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PartidasDisponibles extends JFrame {

	private JPanel contentPane;
	private UserWindow mainWindow;
	private PartidasDisponibles thisWindow;

	public PartidasDisponibles(ArrayList<String> datosPartidas, UserWindow main) {
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				if(mainWindow != null) {
					setVisible(false);
					mainWindow.setVisible(true);
				}
			}
		});
		
		mainWindow = main;
		thisWindow = this;
		
		setTitle("Selecci\u00F3n de partida");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 182, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JLabel lblSeleccioneUnaPartida = new JLabel("Seleccione una partida:");
		GridBagConstraints gbc_lblSeleccioneUnaPartida = new GridBagConstraints();
		gbc_lblSeleccioneUnaPartida.gridwidth = 2;
		gbc_lblSeleccioneUnaPartida.insets = new Insets(0, 0, 5, 5);
		gbc_lblSeleccioneUnaPartida.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblSeleccioneUnaPartida.gridx = 1;
		gbc_lblSeleccioneUnaPartida.gridy = 1;
		contentPane.add(lblSeleccioneUnaPartida, gbc_lblSeleccioneUnaPartida);
		
		JComboBox<String> comboBoxPartidas = new JComboBox<String>();
		GridBagConstraints gbc_comboBoxPartidas = new GridBagConstraints();
		gbc_comboBoxPartidas.gridwidth = 2;
		gbc_comboBoxPartidas.insets = new Insets(0, 0, 5, 5);
		gbc_comboBoxPartidas.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxPartidas.gridx = 1;
		gbc_comboBoxPartidas.gridy = 2;
		contentPane.add(comboBoxPartidas, gbc_comboBoxPartidas);
		
		JLabel lblNombrePartida = new JLabel("Partida");
		GridBagConstraints gbc_lblNombrePartida = new GridBagConstraints();
		gbc_lblNombrePartida.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombrePartida.gridx = 1;
		gbc_lblNombrePartida.gridy = 3;
		contentPane.add(lblNombrePartida, gbc_lblNombrePartida);
		
		JLabel lblCantidadDeJugadores = new JLabel("Cantidad de jugadores");
		GridBagConstraints gbc_lblCantidadDeJugadores = new GridBagConstraints();
		gbc_lblCantidadDeJugadores.insets = new Insets(0, 0, 5, 5);
		gbc_lblCantidadDeJugadores.gridx = 2;
		gbc_lblCantidadDeJugadores.gridy = 3;
		contentPane.add(lblCantidadDeJugadores, gbc_lblCantidadDeJugadores);
		
		JTextArea textAreaNombres = new JTextArea();
		textAreaNombres.setBackground(SystemColor.control);
		GridBagConstraints gbc_textAreaNombres = new GridBagConstraints();
		gbc_textAreaNombres.insets = new Insets(0, 0, 5, 5);
		gbc_textAreaNombres.fill = GridBagConstraints.BOTH;
		gbc_textAreaNombres.gridx = 1;
		gbc_textAreaNombres.gridy = 4;
		contentPane.add(textAreaNombres, gbc_textAreaNombres);
		
		JTextArea textAreaCantJugadores = new JTextArea();
		textAreaCantJugadores.setBackground(SystemColor.control);
		GridBagConstraints gbc_textAreaCantJugadores = new GridBagConstraints();
		gbc_textAreaCantJugadores.insets = new Insets(0, 0, 5, 5);
		gbc_textAreaCantJugadores.fill = GridBagConstraints.BOTH;
		gbc_textAreaCantJugadores.gridx = 2;
		gbc_textAreaCantJugadores.gridy = 4;
		contentPane.add(textAreaCantJugadores, gbc_textAreaCantJugadores);
		
		JButton btnUnirse = new JButton("Unirse");
		GridBagConstraints gbc_btnUnirse = new GridBagConstraints();
		gbc_btnUnirse.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnUnirse.insets = new Insets(0, 0, 5, 5);
		gbc_btnUnirse.gridx = 1;
		gbc_btnUnirse.gridy = 6;
		contentPane.add(btnUnirse, gbc_btnUnirse);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				mainWindow.setVisible(true);
			}
		});
		GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
		gbc_btnCancelar.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnCancelar.insets = new Insets(0, 0, 5, 5);
		gbc_btnCancelar.gridx = 2;
		gbc_btnCancelar.gridy = 6;
		contentPane.add(btnCancelar, gbc_btnCancelar);
		
		textAreaNombres.setText("");
		for(String s : datosPartidas){
			String[] aux = s.split(" ");
			textAreaNombres.setText(textAreaNombres.getText()+aux[0]);
			textAreaCantJugadores.setText(textAreaCantJugadores.getText()+aux[1]);
			comboBoxPartidas.addItem(aux[0]);
		}
		
	}

}
