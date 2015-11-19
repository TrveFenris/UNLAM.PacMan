package paquetes;

public class PaqueteLanzarPartida extends Paquete {
	private static final long serialVersionUID = 9176381048245973322L;
	private boolean ready;
	public PaqueteLanzarPartida() {
		ready = false;
	}
	public void setReady(boolean estado) {
		ready = estado;
	}
	public boolean isReady() {
		return ready;
	}
	@Override
	public TipoPaquete getTipo() {
		return TipoPaquete.LANZAR_PARTIDA;
	}

}
