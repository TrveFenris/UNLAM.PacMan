package editor;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import gameobject.Bolita;
import punto.Punto;
import rectas.Recta;
import rectas.Recta.RectaInvalidaException;

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
	private MapEditor thisFrame;

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
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				mensajeSalida();
			}
		});
		thisFrame=this;
		pInicial=new Punto(0,0);
		pFinal=new Punto(0,0);
		pReal=new Punto(0,0);
		rectas=new ArrayList<Recta>();	
		setResizable(false);
		setTitle("Editor de mapas de Pacman");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 932, 650);
		contentPane = new JPanel();
		contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		areaDibujo = new JPanel();
		areaDibujo.setFocusTraversalKeysEnabled(false);
		areaDibujo.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
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
					try {
						//Se crean nuevos puntos porque si no NO FUNCIONA
						Recta r=new Recta(new Punto(pInicial.getX(),pInicial.getY()), new Punto(pFinal.getX(),pFinal.getY()));
						rectas.add(r);
						r.dibujar(areaDibujo);
						actualizarListaRectas();
						contentPane.repaint();
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
		//contentPane.setOpaque(false);
		
		textFieldNombre = new JTextField();
		textFieldNombre.setToolTipText("Ingrese aqui el nombre del mapa");
		textFieldNombre.setBounds(231, 0, 103, 20);
		contentPane.add(textFieldNombre);
		textFieldNombre.setColumns(10);
		
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
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int option = JOptionPane.showConfirmDialog(thisFrame,
					    "¿Desea guardar el mapa?\nSi existe un mapa con el mismo nombre, se sobreescribirá.",
					    "¿Guardar mapa?",
					    JOptionPane.YES_NO_OPTION);
				if(option == JOptionPane.YES_OPTION) {
					if(textFieldNombre.getText().equals(null) || textFieldNombre.getText().equals("")) {
						JOptionPane.showMessageDialog(thisFrame,
								"Ingrese un nombre para el mapa.",
								 "Error",
								 JOptionPane.ERROR_MESSAGE);
						return;
					}
					else if(rectas.size()==0) {
						JOptionPane.showMessageDialog(thisFrame,
								"No se puede guardar un mapa en blanco.",
								 "Error",
								 JOptionPane.ERROR_MESSAGE);
						return;
					}
					guardar();
				}
			}
		});
		btnGuardar.setBounds(711, 0, 89, 20);
		contentPane.add(btnGuardar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(622, 0, 89, 20);
		contentPane.add(btnCancelar);
		
		JButton btnAbrir = new JButton("Abrir");
		btnAbrir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//rectas = new ArrayList<Recta>();
				File archivo = null;
			    FileReader fr = null;
			    BufferedReader br = null;
			    try {
			    	archivo = new File ("maps/"+"mapaoriginal"+".pacmap");
			    	fr = new FileReader (archivo);
			    	br = new BufferedReader(fr);
			    	String linea = br.readLine();
			    	String[] datos;
			    	int cantRectas = Integer.parseInt(linea);
			    	for(int i=0;i<cantRectas;i++){
			    		linea = br.readLine();
			    		datos = linea.split(" ");
			    		Recta r =new Recta(new Punto(Integer.parseInt(datos[0]),Integer.parseInt(datos[1])), new Punto(Integer.parseInt(datos[2]),Integer.parseInt(datos[3])));
			    		rectas.add(r);
			    		r.dibujar(areaDibujo);
						actualizarListaRectas();
			    		contentPane.repaint();
			    	}
			    }
			    catch(Exception e){
			    	e.printStackTrace();
			    }
			    finally{
			    	try{                    
			    		if( null != fr ){   
			    			fr.close();     
			    		}                  
			    	}
			    	catch (Exception e2){ 
			    		e2.printStackTrace();
			    	}
			    }
			}
		});
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
		
		JButton btnAgregarRecta = new JButton("+");
		btnAgregarRecta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String s = (String)JOptionPane.showInputDialog(
		                thisFrame,
		                "Ingrese recta:",
		                "Nueva Recta",
		                JOptionPane.PLAIN_MESSAGE,
		                null,
		                null,
		                null);

				if ((s != null) && (s.length() > 0)) {
					String [] datos = s.split(" ");
					try {
						Recta r=new Recta(new Punto(Integer.parseInt(datos[0]),Integer.parseInt(datos[1])), new Punto(Integer.parseInt(datos[2]),Integer.parseInt(datos[3])));
						rectas.add(r);
						r.dibujar(areaDibujo);
						actualizarListaRectas();
						contentPane.repaint();
					}
					catch(RectaInvalidaException e) {
						System.out.println("Recta invalida!");
					}
					
					return;
				}
				else {
					if (s != null)
						JOptionPane.showMessageDialog(thisFrame,
							"Ingrese datos para la recta.",
							 "Error",
							 JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
		});
		btnAgregarRecta.setBounds(580, 0, 41, 20);
		contentPane.add(btnAgregarRecta);
	}
	
	private void actualizarListaRectas(){
		textAreaLisaRectas.setText("");
		for(Iterator<Recta>recta=rectas.iterator();recta.hasNext();){
			Recta rec=recta.next();
			textAreaLisaRectas.setText(textAreaLisaRectas.getText()+" "+rec.getPuntoInicial()+" "+rec.getPuntoFinal()+"\n");
		}
	}
	
	private void mensajeSalida() {
		int option = JOptionPane.showConfirmDialog(thisFrame,
			    "¿Esta seguro que quiere salir?",
			    "Saliendo del juego",
			    JOptionPane.YES_NO_OPTION);
		if(option == JOptionPane.YES_OPTION) {
			thisFrame.dispose();
		}
	}
	
	private void guardar(){
		FileWriter fichero = null;
        PrintWriter pw = null;
        try{
            fichero = new FileWriter("maps/"+textFieldNombre.getText()+".pacmap");
            pw = new PrintWriter(fichero);
            pw.println(rectas.size());
            for(Recta r : rectas){
            	pw.println(r.getPuntoInicialX()+" "+r.getPuntoInicialY()+" "+r.getPuntoFinalX()+" "+r.getPuntoFinalY());
            }
			JOptionPane.showMessageDialog(thisFrame,
						"Mapa guardado correctamente.",
						 "Guardado exitoso",
						 JOptionPane.INFORMATION_MESSAGE);
        } 
        catch (Exception e) {
            e.printStackTrace();
        } 
        finally {
           try {
	           if (null != fichero)
	              fichero.close();
           } 
           catch (Exception e2){
              e2.printStackTrace();
           }
        }
	}
}
