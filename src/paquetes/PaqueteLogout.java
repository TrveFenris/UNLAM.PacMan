package paquetes;

public class PaqueteLogout extends Paquete {
	private static final long serialVersionUID = 5464379611467846040L;
	private boolean resultado;
	
	public PaqueteLogout() {
		resultado = false;
	}
	public boolean getResultado() {
		return resultado;
	}
	public void setResultado(boolean result) {
		resultado = result;
	}
	@Override
	public TipoPaquete getTipo() {
		return TipoPaquete.LOGOUT;
	}

}
