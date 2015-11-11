package game;

import gameobject.Bolita;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import punto.Punto;
import rectas.Recta;
import rectas.Rectas;
import rectas.Recta.RectaInvalidaException;


public class Mapa {
	
	private ArrayList<Recta> rectas;
	private ArrayList<Bolita> bolitas;
	
	/**
	 * Genera un mapa por defecto.
	 */
	public Mapa() {
		rectas = new ArrayList<Recta>();
		bolitas = new ArrayList<Bolita>();
		agregarRecta(new Punto(30,50), new Punto(30,530));
		agregarRecta(new Punto(30,530), new Punto(730,530));
		agregarRecta(new Punto(730,50), new Punto(730,530));
		agregarRecta(new Punto(370,50), new Punto(370,530));
		agregarRecta(new Punto(30,290), new Punto(730,290));
		agregarRecta(new Punto(30,50), new Punto(730,50));
		agregarRecta(new Punto(250,30), new Punto(250,500));
		agregarRecta(new Punto(100,150), new Punto(600,150));
		agregarRecta(new Punto(550,34), new Punto(550,550));
		agregarRecta(new Punto(200,470), new Punto(500,470));
		agregarRecta(new Punto(200,450), new Punto(500,470)); //Recta de prueba, no se agrega porque es oblicua
	}
	
	/**
	 * Carga un mapa creado previamente.
	 * Los mapas deben estar guardados en la carpeta "maps/", el nombre debe ser ingresado sin extension.
	 * @param nombre -El nombre del mapa
	 */
	public Mapa(String nombre){
		rectas = new ArrayList<Recta>();
		bolitas = new ArrayList<Bolita>();
		File archivo = null;
	    FileReader fr = null;
	    BufferedReader br = null;
	    try {
	    	archivo = new File ("maps/"+nombre+".pacmap");
	    	fr = new FileReader (archivo);
	    	br = new BufferedReader(fr);
	    	String linea = br.readLine();
	    	String[] datos;
	    	int cantRectas = Integer.parseInt(linea);
	    	for(int i=0;i<cantRectas;i++){
	    		linea = br.readLine();
	    		datos = linea.split(" ");
	    		agregarRecta(new Punto(Integer.parseInt(datos[0]),Integer.parseInt(datos[1])), new Punto(Integer.parseInt(datos[2]),Integer.parseInt(datos[3])));
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
	
	public ArrayList<Bolita> getArrayBolitas() {
		return bolitas;
	}
	
	public ArrayList<Recta> getArrayRectas() {
		return rectas;
	}
	
	public int getCantidadBolitasRestantes() {
		return bolitas.size();
	}
	
	private void agregarRecta(Punto p1, Punto p2) {
		try {
			rectas.add(new Recta(p1,p2));
		}
		catch(RectaInvalidaException ex) {
			System.out.println("No se pudo crear la recta: Parametros invalidos.");
		}
		
	}
	
	public void dibujar(JPanel area){
		for(Recta rec : rectas) {
			rec.dibujar(area);
		}
		generarBolitas(area);
		System.out.println(bolitas.size());
	}
	
	public void generarBolitas(JPanel area){
		ImageIcon bolitaNormalIcon = new ImageIcon("img/bolitaNormal.gif");
		for(Recta rec : rectas) {
			int cantBolitas = (rec.getLongitud()-10)/20;
			for(int i=0;i<cantBolitas;i++){
				JLabel l = new JLabel(bolitaNormalIcon);
				if(rec.getTipo()==Rectas.HORIZONTAL){
					l.setBounds(rec.getPuntoInicialX() + i*20+10, rec.getPuntoInicialY()-10, 21, 21);
				}
				else if(rec.getTipo()==Rectas.VERTICAL){
					l.setBounds(rec.getPuntoInicialX() - 10,rec.getPuntoInicialY() + i*20+10, 21, 21);
				}
				l.setIcon(bolitaNormalIcon);
				Bolita b = new Bolita(l,false);
				//System.out.println(b.getCentroCoordenadas().toString());
				boolean colision = false;
				if( !bolitas.isEmpty() ) {
					for(Bolita bol : bolitas) {
						if( b.getCentroCoordenadas().distanciaCon(bol.getCentroCoordenadas()) <= ( (b.getWidth()/2) + (bol.getWidth()/2) ) ) {
							colision = true;
							break;
						}
					}
				}
				if(!colision) {
					bolitas.add(b);
					area.add(l);
				}
			}
		}
	}
	
	public void removerBolita(Bolita b) {
		b.setAliveState(false);
		b.borrarImagen();
		bolitas.remove(b);
	}
}
