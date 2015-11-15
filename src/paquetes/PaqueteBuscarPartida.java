package paquetes;

public class PaqueteBuscarPartida extends Paquete {
	private static final long serialVersionUID = -656348248287909020L;

	@Override
	public TipoPaquete getTipo() {
		return TipoPaquete.BUSCAR_PARTIDA;
	}

}
