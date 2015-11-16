package paquetes;

public class PaqueteUnirsePartida extends Paquete {
	private static final long serialVersionUID = 529617164265803804L;
	private String nombrePartida;
	private boolean resultado;
	//private PaqueteSkins skins;
	
	public PaqueteUnirsePartida(String nombre/*, PaqueteSkins skin*/) {
		nombrePartida = nombre;
		//skins = skin;
	}
	public String getNombrePartida() {
		return nombrePartida;
	}
	/*
	public PaqueteSkins getPaqueteSkins() {
		return skins;
	}
	*/
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
