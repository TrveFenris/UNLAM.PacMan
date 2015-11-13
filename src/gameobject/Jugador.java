package gameobject;

import game.Configuracion;
import game.ConfiguracionSprites;

import java.util.ArrayList;
import java.util.Iterator;

import punto.Punto;
import rectas.Recta;
import rectas.Rectas;

public abstract class Jugador extends GameObject{
	protected int velocidad;
	protected int velX;
	protected int velY;
	protected String nombre;
	protected int puntaje;
	protected ArrayList<Recta> rectasActuales;
	protected ConfiguracionSprites skin;
	//Variables delimitadoras
	protected int upperBound;
	protected int lowerBound;
	protected int leftBound;
	protected int rightBound;
	
	public Jugador(String nombre, int velocidad, ConfiguracionSprites skin){
		super();
		this.velocidad=velocidad;
		this.nombre=nombre;
		this.skin=skin;
		puntaje=0;
		velX = 0;
		velY = 0;
		rectasActuales=new ArrayList<Recta>();
	}
	
	public void input(){
		
	}
	
	public void cambiarSentido(Direcciones a){
		switch(a){
			case ARRIBA:
				velX=0;
				velY=-1*velocidad;
				setImagen(skin.getValor(Direcciones.ARRIBA));
				break;
			case ABAJO:
				velX=0;
				velY=velocidad;
				setImagen(skin.getValor(Direcciones.ABAJO));
				break;
			case IZQUIERDA:
				velX=-1*velocidad;
				setImagen(skin.getValor(Direcciones.IZQUIERDA));
				velY=0;
				break;
			case DERECHA:
				velX=velocidad;
				velY=0;
				setImagen(skin.getValor(Direcciones.DERECHA));
				break;
			case NINGUNA:
				break;
		}
	}
	
	public void mover(){
		setLocation(getLocation().getX() + velX, getLocation().getY() + velY);
	}
	
	public void actualizarPuntaje(int cant){
		puntaje=cant;
	}
	/*
	public boolean colisionaCon(Bolita obj) {
		if( this.getCentroCoordenadas().equals(obj.getCentroCoordenadas()) ) {
			//System.out.println("Colisiono con una bolita");
			return true;
		}
		else return false;
	}
	*/
	public boolean colisionaCon(Bolita obj) {
		if( this.getCentroCoordenadas().distanciaCon(obj.getCentroCoordenadas()) <= Configuracion.PACMAN_DISTANCIA_PARA_COMER_BOLITA.getValor() ) {
			return true;
		}
		else return false;
	}
	/**
	 * Devuelve la recta sobre la que se encuentra el jugador. 
	 * Ya que la lista de rectasActuales puede tener 1 o 2 rectas en un determinado momento, es necesario indicar el indice.
	 * @param indice
	 * @return null :si se especifica un indice mayor a la cantidad de rectas actuales.
	 * @see #getCantRectasActuales()
	 */
	public Recta getRectaActual(int indice){
		if(indice>=rectasActuales.size())
			return null;
		return rectasActuales.get(indice);
	}
	
	/**
	 * Devuelve la cantidad de rectas sobre las que se encuentra el jugador en un determinado momento.
	 */
	public int getCantRectasActuales(){
		return rectasActuales.size();
	}
	
	/**
	 * Actualiza el campo "rectaActual", que indica sobre que recta se encuentra el jugador en cada momento.
	 * @param rectas -Lista de rectas del mapa
	 */
	public void actualizarUbicacion(ArrayList<Recta> rectas){
		//Remover las rectas anteriores
		rectasActuales.clear();
		//Agregar las rectas actuales
		for(Iterator<Recta>k=rectas.iterator();k.hasNext();) {
			Recta rec = k.next();
				if(estaEn(rec)) {
					rectasActuales.add(rec);
			}
		}
	}
	
	/**
	 * Devuelve si el jugador se encuentra en una recta HORIZONTAL, VERTICAL, o AMBAS
	 */
	public Rectas getTipoUbicacion(){
		if(rectasActuales.size()>1)
			return Rectas.AMBAS;
		else
			if(rectasActuales.size()>0)
				return rectasActuales.get(0).getTipo();
		System.out.println("ERROR EN TIPO DE UBICACION");
		return Rectas.HORIZONTAL;
	}
	
	public int getUpperBound() {
		return upperBound;
	}
	public int getLowerBound() {
		return lowerBound;
	}
	public int getRightBound() {
		return rightBound;
	}
	public int getLeftBound() {
		return leftBound;
	}
	public void setUpperBound(int bound) {
		upperBound = bound;
	}
	public void setLowerBound(int bound) {
		lowerBound = bound;
	}
	public void setRightBound(int bound) {
		rightBound = bound;
	}
	public void setLeftBound(int bound) {
		leftBound = bound;
	}
	/**
	 * @param Recta
	 * @return Si el jugador esta o no sobre dicha recta.
	 */
	public boolean estaEn(Recta r) {
		boolean result = false;
		Punto p = getCentroCoordenadas();
		if(r.getTipo() == Rectas.VERTICAL){
			if(p.getX() == r.getPuntoInicialX() && ( p.getY() >= r.getPuntoInicialY() && p.getY() <= r.getPuntoFinalY() ) ) {
				result = true;
			}
		}
		else if(r.getTipo() == Rectas.HORIZONTAL) {
			if(p.getY() == r.getPuntoInicialY() && ( p.getX() >= r.getPuntoInicialX() && p.getX() <= r.getPuntoFinalX() ) ) {
				result = true;
			}
		}
		return result;
	}
	
}
