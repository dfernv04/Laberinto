
public class Casilla {
	
	private char caracter;
	private int fila;
	private int col;
	
	public Casilla (char c, int i, int j) {
		this.caracter=c;
		this.fila=i;
		this.col=j;
	}
	
	public char getCaracter() {
		return caracter;
	}

	public void setCaracter(char caracter) {
		this.caracter = caracter;
	}

	public int getFila() {
		return fila;
	}

	public void setFila(int fila) {
		this.fila = fila;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Casilla other = (Casilla) obj;
		if (caracter != other.caracter)
			return false;
		if (col != other.col)
			return false;
		if (fila != other.fila)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		StringBuilder cadena = new StringBuilder();
		if(this.caracter=='*') {
			cadena.append("("+(this.fila+1)+"/"+(this.col+1)+")*");
		}else {
			cadena.append("("+(this.fila+1)+"/"+(this.col+1)+")");
		}
		return cadena.toString();
	}
}
	