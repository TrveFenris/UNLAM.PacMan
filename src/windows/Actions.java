package windows;

public enum Actions {
	ARRIBA(0),
	ABAJO(1),
	IZQUIERDA(2),
	DERECHA(3);
	
	private final int actionCode;

    private Actions(int actionCode) {
        this.actionCode = actionCode;
    }
}
