package paquetes;

public class PaqueteRegistro extends Paquete {
	private static final long serialVersionUID = -8912490712122305221L;

	@Override
	public TipoPaquete getTipo() {
		return TipoPaquete.REGISTRO;
	}

}
