package paquetes;

public class PaqueteServidorLleno extends Paquete {
	private static final long serialVersionUID = 6748369189700332492L;

	@Override
	public TipoPaquete getTipo() {
		return TipoPaquete.SERVIDOR_LLENO;
	}

}
