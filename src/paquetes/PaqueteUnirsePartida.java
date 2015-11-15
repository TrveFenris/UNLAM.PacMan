package paquetes;

public class PaqueteUnirsePartida extends Paquete {
	private static final long serialVersionUID = 529617164265803804L;
	private String nombrePartida;
	private boolean resultado;
	
	public PaqueteUnirsePartida(String nombre) {
		nombrePartida = nombre;
	}
	public String getNombre() {
		return nombrePartida;
	}
	@Override
	public TipoPaquete getTipo() {
		return TipoPaquete.UNIRSE_PARTIDA;
	}
	public boolean getResultado() {
		return resultado;
	}
	public void setResultado(boolean result) {
		resultado = result;
	}

}
