package game;

import gameobject.Bolita;

import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.*;


public class Mapa {
	
	private ArrayList<Recta> rectas;
	private ArrayList<Bolita> bolitas;
	private JPanel area;
	
	public Mapa(JPanel a) {
		area=a;
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
	
	public void dibujar(){
		for(Iterator<Recta>r=rectas.iterator();r.hasNext();){
			Recta rec=r.next();
			rec.dibujar(area);
			generarBolitas();
		}
	}
	
	public void generarBolitas(){
		for(Iterator<Recta>r=rectas.iterator();r.hasNext();){
			Recta rec=r.next();
			int cantBolitas=(rec.getLongitud()-10)/20;
			for(int i=0;i<cantBolitas;i++){
				ImageIcon icon = new ImageIcon("img/bolitaNormal.gif");
				JLabel l= new JLabel(icon);
				if(rec.tipo==Rectas.HORIZONTAL){
					l.setBounds(rec.puntoInicial.getX()+ i*20+10, rec.puntoInicial.getY()-9, 20, 20);
				}
				else if(rec.tipo==Rectas.VERTICAL){
					l.setBounds(rec.puntoInicial.getX()-9,rec.puntoInicial.getY() +i*20+10, 20, 20);
				}
				l.setIcon(icon);
				bolitas.add(new Bolita(l,false));
				area.add(l);
			}
		}
	}
}
