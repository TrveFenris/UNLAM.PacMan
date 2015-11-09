package server;

import java.awt.EventQueue;
import java.awt.Robot;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.AWTException;
import java.awt.Color;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;

import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.event.MouseEvent;

import rectas.Recta;
import rectas.Recta.RectaInvalidaException;
import punto.Punto;

import java.awt.event.MouseAdapter;

import javax.swing.JTextArea;
import javax.swing.SwingConstants;

public class MapEditor extends JFrame {

	private static final long serialVersionUID = -3653077430168537493L;
	private JPanel contentPane;
	private JTextField textFieldNombre;
	private JTextField txtCoordenadas;
	private JTextArea textAreaLisaRectas;
	private JPanel areaDibujo;
	private ArrayList<Recta>rectas;
	private Punto pInicial;
	private Punto pFinal;
	private Punto pReal;
	

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MapEditor frame = new MapEditor();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MapEditor() {
		pInicial=new Punto(0,0);
		pFinal=new Punto(0,0);
		pReal=new Punto(0,0);
		rectas=new ArrayList<Recta>();
		
		setResizable(false);
		setTitle("Editor de mapas de Pacman");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 932, 650);
		contentPane = new JPanel();
		contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		//contentPane.setOpaque(false);
		
		textFieldNombre = new JTextField();
		textFieldNombre.setToolTipText("Ingrese aqui el nombre del mapa");
		textFieldNombre.setBounds(231, 0, 103, 20);
		contentPane.add(textFieldNombre);
		textFieldNombre.setColumns(10);
		
		areaDibujo = new JPanel();
		areaDibujo.setBackground(Color.DARK_GRAY);
		areaDibujo.setBounds(0, 20, 800, 600);
		areaDibujo.setLayout(null);
		//areaDibujo.setOpaque(false);
		areaDibujo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(pInicial.isOrigen()){
					pInicial.setXY(e.getX(), e.getY());
					pReal.setXY((int)e.getLocationOnScreen().getX(), (int)e.getLocationOnScreen().getY());
					System.out.println("pInicial: "+pInicial);
				}
				else
				{
					pFinal.setXY(e.getX(), e.getY());
					System.out.println("pFinal: "+pFinal);
					//Revisar esto contemplando la nueva excepción lanzada por Recta()
					try {
						Recta r=new Recta(pInicial,pFinal);
						rectas.add(r);
						r.dibujar(areaDibujo);
						actualizarListaRectas();
						pInicial.setXY(0, 0);
						pFinal.setXY(0,0);
					}
					catch(RectaInvalidaException ex) {
						ex.printStackTrace();
					}
					
				}
			}
		});
		areaDibujo.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				txtCoordenadas.setText("X: "+e.getX()+"  "+"Y: "+e.getY());
				int x=(int)e.getLocationOnScreen().getX();
				int y=(int)e.getLocationOnScreen().getY();
				if(!pInicial.isOrigen()){
					try {
						if(e.getX()>pInicial.getX()+5||e.getX()<pInicial.getX()-5){
								Robot r = new Robot();
								r.mouseMove(x,pReal.getY());			
						}
						else{
							//if(e.getY()>pInicial.getY()+5||e.getY()<pInicial.getY()-5){
								Robot r=new Robot();
								r.mouseMove(pReal.getX(),y);
							//}
						}
					} catch (AWTException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		contentPane.add(areaDibujo);
		
		JLabel lblNombre = new JLabel("Nombre del mapa");
		lblNombre.setBounds(127, 0, 103, 20);
		contentPane.add(lblNombre);
		
		JLabel lblCoordenadas = new JLabel("Coordenadas");
		lblCoordenadas.setBounds(384, 0, 86, 20);
		contentPane.add(lblCoordenadas);
		
		txtCoordenadas = new JTextField();
		txtCoordenadas.setEditable(false);
		txtCoordenadas.setBounds(473, 0, 103, 20);
		contentPane.add(txtCoordenadas);
		txtCoordenadas.setColumns(10);
		
		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.setBounds(711, 0, 89, 20);
		contentPane.add(btnGuardar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(622, 0, 89, 20);
		contentPane.add(btnCancelar);
		
		JButton btnAbrir = new JButton("Abrir");
		btnAbrir.setBounds(0, 0, 89, 20);
		contentPane.add(btnAbrir);
		
		JLabel lblRectas = new JLabel("Rectas");
		lblRectas.setHorizontalAlignment(SwingConstants.CENTER);
		lblRectas.setBounds(800, 20, 128, 20);
		contentPane.add(lblRectas);
		
		textAreaLisaRectas = new JTextArea();
		textAreaLisaRectas.setEditable(false);
		textAreaLisaRectas.setBounds(800, 40, 128, 610);
		contentPane.add(textAreaLisaRectas);
	}
	
	private void actualizarListaRectas(){
		textAreaLisaRectas.setText("");
		for(Iterator<Recta>recta=rectas.iterator();recta.hasNext();){
			Recta rec=recta.next();
			//rec.dibujar(areaDibujo);
			System.out.println(rec);
			textAreaLisaRectas.setText(textAreaLisaRectas.getText()+" "+rec.getPuntoInicial()+" "+rec.getPuntoFinal()+"\n");
		}
	}
}
