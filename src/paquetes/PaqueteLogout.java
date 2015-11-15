package paquetes;

public class PaqueteLogout extends Paquete {
	private static final long serialVersionUID = 5464379611467846040L;

	@Override
	public TipoPaquete getTipo() {
		return TipoPaquete.LOGOUT;
	}

}
