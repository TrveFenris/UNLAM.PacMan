package paquetes;

public class PaqueteLogin extends Paquete{
	private static final long serialVersionUID = 7517800177765401842L;

	@Override
	public TipoPaquete getTipo() {
		return TipoPaquete.LOGIN;
	}

}
