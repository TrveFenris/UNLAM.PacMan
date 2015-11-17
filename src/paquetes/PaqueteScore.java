package paquetes;

public class PaqueteScore extends Paquete {

	private static final long serialVersionUID = -8567996112361198613L;
	private int score;
	
	public PaqueteScore(int score) {
		this.score = score;
	}
	public int getScore() {
		return score;
	}
	@Override
	public TipoPaquete getTipo() {
		// TODO Auto-generated method stub
		return null;
	}

}
