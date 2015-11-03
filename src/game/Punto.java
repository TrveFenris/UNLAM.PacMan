package game;

public class Punto {
	private int x;
	private int y;
	
	public Punto(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public Punto sumar(Punto vec){
		return new Punto(this.x + vec.x, this.y + vec.y);
	}
	public Punto restar(Punto vec){
		return new Punto(this.x - vec.x, this.y - vec.y);
	}
	public int producto(Punto vec){
		return (this.x * vec.x) + (this.y * vec.y);
	}
	
	public boolean equals(Punto vec){
		return (this.x == vec.x) && (this.y == vec.y);
	}
	
	public double distanciaCon(Punto vec){
		return Math.sqrt( Math.pow( (this.x - vec.x), 2) + Math.pow( (this.y - vec.y), 2) );
	}
	
	public double modulo(){
		return Math.sqrt((x*x)+(y*y));
	}
	//GETTERS
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public void setX(int n){
		x = n;
	}
	public void setY(int n){
		y = n;
	}
	public void setXY(int x, int y){
		this.x = x;
		this.y = y;
	}
	//Metodos Estaticos
	public static Punto sumar(Punto a, Punto b){
		return new Punto(a.x + b.x, a.y + b.y);
	}
	public static Punto restar(Punto a, Punto b){
		return new Punto(a.x - b.x, a.y - b.y);
	}
	public static double producto(Punto a, Punto b){
		return (a.x * b.x) + (a.y * b.y);
	}
	public static double producto(Punto a, double b){
		return (a.x * b) + (a.y * b);
	}
	public static boolean equals(Punto a, Punto b){
		return (a.x == b.x) && (a.y == b.y);
	}
	//FIN Metodos Estaticos
}
