package punto;

import java.io.Serializable;

public class Punto implements Serializable{
	private static final long serialVersionUID = 18L;
	private int x;
	private int y;
	
	public Punto(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public Punto sumar(Punto p){
		return new Punto(this.x + p.x, this.y + p.y);
	}
	public Punto restar(Punto p){
		return new Punto(this.x - p.x, this.y - p.y);
	}
	public int producto(Punto p){
		return (this.x * p.x) + (this.y * p.y);
	}
	
	public boolean equals(Punto p){
		return (this.x == p.x) && (this.y == p.y);
	}
	
	public double distanciaCon(Punto p){
		return Math.sqrt( Math.pow(this.x - p.x, 2) + Math.pow(this.y - p.y, 2) );
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
	public boolean isOrigen(){
		return(x==0&&y==0);
	}
	
	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
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
