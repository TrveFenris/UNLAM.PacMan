package tallerjava.windows;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JLabel;

import java.awt.GridBagConstraints;
import java.awt.Font;
import java.awt.Insets;
import java.awt.TextField;
import java.awt.Button;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import javax.swing.JButton;

public class ConfigWindow extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private UserWindow userWindow;
	private TextField textFieldDer;
	private TextField textFieldIzq;
	private TextField textFieldArriba;
	private TextField textFieldAbajo;

	/* ConfigWindow Constructor */
	public ConfigWindow(UserWindow window) {
		userWindow=window;
		setTitle("Configuraci\u00F3n");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				dispose();
				userWindow.setVisible(true);
			}
		});
		setBounds(100, 100, 518, 337);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0, 0, 91, 57, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		/* Controles */
		JLabel lblTeclas = new JLabel("TECLAS");
		lblTeclas.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		GridBagConstraints gbc_lblTeclas = new GridBagConstraints();
		gbc_lblTeclas.gridwidth = 2;
		gbc_lblTeclas.insets = new Insets(0, 0, 5, 5);
		gbc_lblTeclas.gridx = 1;
		gbc_lblTeclas.gridy = 1;
		contentPane.add(lblTeclas, gbc_lblTeclas);
		
		Button btnArriba = new Button("Arriba");
		btnArriba.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				leerControl(0);
			}
		});
		
		JLabel lblPacman = new JLabel("PACMAN");
		lblPacman.setToolTipText("Selecciona el modelo para el personaje Pacman");
		lblPacman.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		GridBagConstraints gbc_lblPacman = new GridBagConstraints();
		gbc_lblPacman.gridwidth = 2;
		gbc_lblPacman.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblPacman.insets = new Insets(0, 0, 5, 5);
		gbc_lblPacman.gridx = 4;
		gbc_lblPacman.gridy = 1;
		contentPane.add(lblPacman, gbc_lblPacman);
		GridBagConstraints gbc_btnArriba = new GridBagConstraints();
		gbc_btnArriba.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnArriba.anchor = GridBagConstraints.SOUTH;
		gbc_btnArriba.insets = new Insets(0, 0, 5, 5);
		gbc_btnArriba.gridx = 1;
		gbc_btnArriba.gridy = 2;
		contentPane.add(btnArriba, gbc_btnArriba);
		
		textFieldArriba = new TextField();
		textFieldArriba.setEditable(false);
		GridBagConstraints gbc_textFieldArriba = new GridBagConstraints();
		gbc_textFieldArriba.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldArriba.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldArriba.gridx = 2;
		gbc_textFieldArriba.gridy = 2;
		contentPane.add(textFieldArriba, gbc_textFieldArriba);
		
		Button btnAbajo = new Button("Aabajo");
		btnAbajo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				leerControl(1);
			}
		});
		GridBagConstraints gbc_btnAbajo = new GridBagConstraints();
		gbc_btnAbajo.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnAbajo.insets = new Insets(0, 0, 5, 5);
		gbc_btnAbajo.gridx = 1;
		gbc_btnAbajo.gridy = 3;
		contentPane.add(btnAbajo, gbc_btnAbajo);
		
		textFieldAbajo = new TextField();
		textFieldAbajo.setEditable(false);
		GridBagConstraints gbc_textFieldAbajo = new GridBagConstraints();
		gbc_textFieldAbajo.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldAbajo.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldAbajo.gridx = 2;
		gbc_textFieldAbajo.gridy = 3;
		contentPane.add(textFieldAbajo, gbc_textFieldAbajo);
		
		Button btnIzquierda = new Button("Izquierda");
		btnIzquierda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				leerControl(2);
			}
		});
		GridBagConstraints gbc_btnIzquierda = new GridBagConstraints();
		gbc_btnIzquierda.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnIzquierda.insets = new Insets(0, 0, 5, 5);
		gbc_btnIzquierda.gridx = 1;
		gbc_btnIzquierda.gridy = 4;
		contentPane.add(btnIzquierda, gbc_btnIzquierda);
		
		textFieldIzq = new TextField();
		textFieldIzq.setEditable(false);
		GridBagConstraints gbc_textFieldIzq = new GridBagConstraints();
		gbc_textFieldIzq.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldIzq.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldIzq.gridx = 2;
		gbc_textFieldIzq.gridy = 4;
		contentPane.add(textFieldIzq, gbc_textFieldIzq);
		
		Button btnDerecha = new Button("Derecha");
		btnDerecha.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				leerControl(3);
			}
		});
		GridBagConstraints gbc_btnDerecha = new GridBagConstraints();
		gbc_btnDerecha.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnDerecha.insets = new Insets(0, 0, 5, 5);
		gbc_btnDerecha.gridx = 1;
		gbc_btnDerecha.gridy = 5;
		contentPane.add(btnDerecha, gbc_btnDerecha);
		
		textFieldDer = new TextField();
		textFieldDer.setEditable(false);
		GridBagConstraints gbc_textFieldDer = new GridBagConstraints();
		gbc_textFieldDer.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldDer.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldDer.gridx = 2;
		gbc_textFieldDer.gridy = 5;
		contentPane.add(textFieldDer, gbc_textFieldDer);
		
		JLabel lblFantasma = new JLabel("FANTASMA");
		lblFantasma.setToolTipText("Selecciona el modelo para el personaje Fantasma");
		lblFantasma.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		GridBagConstraints gbc_lblFantasma = new GridBagConstraints();
		gbc_lblFantasma.gridwidth = 2;
		gbc_lblFantasma.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblFantasma.insets = new Insets(0, 0, 5, 5);
		gbc_lblFantasma.gridx = 4;
		gbc_lblFantasma.gridy = 5;
		contentPane.add(lblFantasma, gbc_lblFantasma);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				userWindow.setVisible(true);
			}
		});
		GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
		gbc_btnCancelar.insets = new Insets(0, 0, 0, 5);
		gbc_btnCancelar.gridx = 3;
		gbc_btnCancelar.gridy = 9;
		contentPane.add(btnCancelar, gbc_btnCancelar);
		
		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String arriba=textFieldArriba.getText();
				String abajo=textFieldAbajo.getText();
				String izquierda=textFieldIzq.getText();
				String derecha=textFieldDer.getText();
				userWindow.setControles(arriba, abajo, derecha, izquierda);
				dispose();
				userWindow.setVisible(true);
			}
		});
		GridBagConstraints gbc_btnGuardar = new GridBagConstraints();
		gbc_btnGuardar.insets = new Insets(0, 0, 0, 5);
		gbc_btnGuardar.gridx = 4;
		gbc_btnGuardar.gridy = 9;
		contentPane.add(btnGuardar, gbc_btnGuardar);
		
		cargarValoresActuales();
	}
	
	/* Métodos */
	private void cargarValoresActuales(){
		String[]controles=userWindow.getControles();
		textFieldArriba.setText(controles[0]);
		textFieldAbajo.setText(controles[1]);
		textFieldIzq.setText(controles[2]);
		textFieldDer.setText(controles[3]);
	}
	
	private void leerControl(int control){
		KeyBindingsWindow cw = new KeyBindingsWindow(this,control);
		cw.setVisible(true);
	}
	
	public void setControlArriba(String s){
		textFieldArriba.setText(s);
	}
	
	public void setControlAbajo(String s){
		textFieldAbajo.setText(s);
	}
	
	public void setControlIzq(String s){
		textFieldIzq.setText(s);
	}
	
	public void setControlDer(String s){
		textFieldDer.setText(s);
	}
}
