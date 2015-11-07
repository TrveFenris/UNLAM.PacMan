package game;

import gameobject.Bolita;

import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.*;

import punto.Punto;
import rectas.Recta;
import rectas.Rectas;


public class Mapa {
	
	private ArrayList<Recta> rectas;
	private ArrayList<Bolita> bolitas;
	
	public Mapa() {
		rectas = new ArrayList<Recta>();
		bolitas = new ArrayList<Bolita>();
		agregarRecta(new Punto(30,50), new Punto(30,530));
		agregarRecta(new Punto(30,530), new Punto(730,530));
		agregarRecta(new Punto(730,50), new Punto(730,530));
		agregarRecta(new Punto(380,50), new Punto(380,530));
		agregarRecta(new Punto(30,290), new Punto(730,290));
		agregarRecta(new Punto(30,50), new Punto(730,50));
	}
	
	public ArrayList<Bolita> getArrayBolitas() {
		return bolitas;
	}
	
	public ArrayList<Recta> getArrayRectas() {
		return rectas;
	}
	
	private void agregarRecta(Punto p1, Punto p2) {
		rectas.add(new Recta(p1,p2));
	}
	
	public void dibujar(JPanel area){
		for(Iterator<Recta>r=rectas.iterator();r.hasNext();){
			Recta rec=r.next();
			rec.dibujar(area);
			generarBolitas(area);
		}
	}
	
	public void generarBolitas(JPanel area){
		for(Iterator<Recta>r=rectas.iterator();r.hasNext();){
			Recta rec=r.next();
			int cantBolitas=(rec.getLongitud()-10)/20;
			for(int i=0;i<cantBolitas;i++){
				ImageIcon icon = new ImageIcon("img/bolitaNormal.gif");
				JLabel l= new JLabel(icon);
				if(rec.getTipo()==Rectas.HORIZONTAL){
					l.setBounds(rec.getPuntoInicialX() + i*20+10, rec.getPuntoInicialY()-9, 20, 20);
				}
				else if(rec.getTipo()==Rectas.VERTICAL){
					l.setBounds(rec.getPuntoInicialX() - 9,rec.getPuntoInicialY() + i*20+10, 20, 20);
				}
				l.setIcon(icon);
				bolitas.add(new Bolita(l,false));
				area.add(l);
			}
		}
	}
}
