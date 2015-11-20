package paquetes;

public class PaqueteAbandonarLobby extends Paquete{

	private static final long serialVersionUID = 2236084928910201115L;
	
	public PaqueteAbandonarLobby() {
	}
	
	@Override
	public TipoPaquete getTipo() {
		return TipoPaquete.ABANDONAR_LOBBY;
	}
}
