package paquetes;

import gameobject.Direcciones;
import punto.Punto;

public class PaqueteCoordenadas extends Paquete {

	private static final long serialVersionUID = 6990615650451885236L;
	private Punto coordenadas;
	private int IDJugador;
	private Direcciones direccion;
	
	public PaqueteCoordenadas(Punto punto, int id, Direcciones direccion) {
		coordenadas = punto;
		IDJugador = id;
		this.direccion = direccion;
	}
	
	public Punto getCoordenadas() {
		return coordenadas;
	}
	
	public int getIDJugador() {
		return IDJugador;
	}
	
	public Direcciones getDireccion(){
		return direccion;
	}

	@Override
	public TipoPaquete getTipo() {
		return TipoPaquete.COORDENADAS;
	}
}
