import java.util.ArrayList;

import java.util.Scanner;



public class Laberinto {
	private Casilla[][] matriz;
	private int tamanyo;
	Scanner input;
	private ArrayList<Casilla> solucion;
	private ArrayList<Casilla> solucionAux;
	private ArrayList<Casilla> solucion2;
	
	public Laberinto(int tam, Scanner sc) {
		this.matriz = new Casilla[tam][tam]; 
		this.tamanyo=tam;
		this.input=sc;
	}
	
	public void leerMatriz() {
		for(int i=0;i<this.tamanyo;i++) {
			String linea = this.input.nextLine();
			char[] vecaux = linea.toCharArray();
			
			for(int j=0;j<this.tamanyo;j++) {
				Casilla cas = new Casilla(vecaux[j], i, j);
				this.matriz[i][j]=cas;
			}
		}
	}
	
	public void resolverLaberinto() {
		int asterisco=0;
		if((this.matriz[0][0].getCaracter()=='1') || (this.matriz[this.tamanyo-1][this.tamanyo-1].getCaracter()=='1')) {
			System.out.println("NO.");
			System.exit(-1);
		}else {
			if(this.tamanyo==1) {
				if(this.matriz[0][0].getCaracter()=='0') {
					System.out.println("SI, SIN PREMIO.");
					System.out.println("(1,1)");
					System.exit(-1);
				}else {
					System.out.println("SI, CON PREMIO.");
					System.out.println("(1,1)*");
					System.exit(-1);
				}
			}else {
				for(int i=0;i<this.tamanyo;i++) {
					for(int j=0;j<this.tamanyo;j++) {
						if(this.matriz[i][j].getCaracter()=='*') {
							asterisco=1;
						}
					}
				}
				if(asterisco==1) {
					solucion = new ArrayList<Casilla>();
					solucionAux = new ArrayList<Casilla>();
					solucion2 = new ArrayList<Casilla>();
					solucionAux.add(this.matriz[0][0]);
					int filaAst=0, colAst=0;
					for(int i=0;i<this.tamanyo;i++) {
						for(int j=0;j<this.tamanyo;j++) {
							if(this.matriz[i][j].getCaracter()=='*') {
								this.matriz[i][j].setCaracter('0');
								filaAst=i;
								colAst=j;
							}
						}
					}
					if(rodeadoUnos(this.matriz[filaAst][colAst])) {
						resolverSinAsterico(this.matriz[0][0], this.matriz[this.tamanyo-1][this.tamanyo-1], solucion, solucionAux);
						System.out.println("SI, SIN PREMIO");
						String errorDisplay = solucion.toString();
						errorDisplay  = errorDisplay.substring(1, errorDisplay.length() - 1);
						System.out.println(errorDisplay.replace(",", "").replace("/", ","));
					}else {
						resolverSinAsterico(this.matriz[0][0], this.matriz[filaAst][colAst], solucion, solucionAux);
						if(solucion.size()==0) {
							System.out.println("NO.");
							System.exit(-1);
						}
						solucion2.addAll(solucion);
						solucion.removeAll(solucion);
						solucionAux.removeAll(solucionAux);
						resetearMatriz();
						resolverSinAsterico(this.matriz[filaAst][colAst], this.matriz[this.tamanyo-1][this.tamanyo-1], solucion, solucionAux);
						solucion2.addAll(solucion);
						if(solucion.size()==0) {
							System.out.println("NO.");
						}else {
							System.out.println("SI, CON PREMIO.");
							this.matriz[filaAst][colAst].setCaracter('*');
							String errorDisplay = solucion2.toString();
							errorDisplay  = errorDisplay.substring(1, errorDisplay.length() - 1);
							System.out.println(errorDisplay.replace(",", "").replace("/", ","));
						}
					}
				}else{
					
					solucion = new ArrayList<Casilla>();
					solucionAux = new ArrayList<Casilla>();
					
					solucionAux.add(this.matriz[0][0]);
					
					resolverSinAsterico(this.matriz[0][0], this.matriz[this.tamanyo-1][this.tamanyo-1],solucion,solucionAux);
					
					if(solucion.size()==0) {
						System.out.println("NO.");
					}else {
						System.out.println("SI, SIN PREMIO.");
						String errorDisplay = solucion.toString();
						errorDisplay = errorDisplay.substring(1,errorDisplay.length() - 1);
						System.out.println(errorDisplay.replaceAll(",", "").replaceAll("/", ","));
					}
				}
			}
		}
	}
	
	public void resolverSinAsterico(Casilla casActual, Casilla fin, ArrayList<Casilla> solucion,ArrayList<Casilla> solucionAux) {
		
		if(casActual.equals(fin)) {
			compararSoluciones(solucion, solucionAux);
		}else {
			
			int[][] movimientos = { {-1,0}, {-1,1}, {0,1}, {1,1}, {1,0}, {1,-1}, {0,-1}, {1,-1} };
			
			int filanew, colnew;
			Casilla aux;
			
			for(int i=0;i<movimientos.length;i++) { 

				filanew = casActual.getFila() + movimientos[i][0];
				colnew = casActual.getCol() + movimientos[i][1];
				
				if(filanew>=0 && colnew>=0 && filanew<this.tamanyo && colnew<this.tamanyo) {
					
					aux = this.matriz[filanew][colnew];
						switch (i) {
							case 0:
								if(arribaDisponible(aux)) {
									solucionAux.add(aux);
									casActual.setCaracter('/');
									resolverSinAsterico(aux,fin, solucion, solucionAux);
									casActual.setCaracter('0');
									solucionAux.remove(aux);
								}
								break;
							case 1:
								if(arribaDerechaDisponible(aux)) {
									solucionAux.add(aux);
									casActual.setCaracter('/');
									resolverSinAsterico(aux,fin, solucion, solucionAux);
									casActual.setCaracter('0');
									solucionAux.remove(aux);
								}
								break;
							case 2:
								if(derechaDisponible(aux)) {
									solucionAux.add(aux);
									casActual.setCaracter('/');
									resolverSinAsterico(aux,fin, solucion, solucionAux);
									casActual.setCaracter('0');
									solucionAux.remove(aux);
								}
								break;
							case 3:
								if(abajoDerechaDisponible(aux)) {
									solucionAux.add(aux);
									casActual.setCaracter('/');
									resolverSinAsterico(aux,fin, solucion, solucionAux);
									casActual.setCaracter('0');
									solucionAux.remove(aux);
								}
								break;
							case 4:
								if(abajoDisponible(aux)) {
									solucionAux.add(aux);
									casActual.setCaracter('/');
									resolverSinAsterico(aux,fin, solucion, solucionAux);
									casActual.setCaracter('0');
									solucionAux.remove(aux);
								}
								break;
							case 5:
								if(abajoIzquierdaDisponible(aux)) {
									solucionAux.add(aux);
									casActual.setCaracter('/');
									resolverSinAsterico(aux,fin, solucion, solucionAux);
									casActual.setCaracter('0');
									solucionAux.remove(aux);
								}
								break;
							case 6:
								if(izquierdaDisponible(aux)) {
									solucionAux.add(aux);
									casActual.setCaracter('/');
									resolverSinAsterico(aux,fin, solucion, solucionAux);
									casActual.setCaracter('0');
									solucionAux.remove(aux);
								}
								break;
							case 7:
								if(arribaIzquierdaDisponible(aux)) {
									solucionAux.add(aux);
									casActual.setCaracter('/');
									resolverSinAsterico(aux,fin, solucion, solucionAux);
									casActual.setCaracter('0');
									solucionAux.remove(aux);
								}
								break;
						}
					}
				}
			}
		}

	public boolean rodeadoUnos(Casilla cas) {
		int count = 0;
		
		int[][] movimientos = { {-1,0}, {-1,1}, {0,1}, {1,1}, {1,0}, {1,-1}, {0,-1}, {1,-1} };
		
		int filanew, colnew;
		Casilla aux;
		
		for(int i=0;i<movimientos.length;i++) { 

			filanew = cas.getFila() + movimientos[i][0];
			colnew = cas.getCol() + movimientos[i][1];
			
			if(filanew<0 || colnew<0 || filanew>=this.tamanyo || colnew>=this.tamanyo) {
				count ++;
			}else {
				
				aux = this.matriz[filanew][colnew];
					switch (i) {
						case 0:
							if(!arribaDisponible(aux)) {
								if(aux.getCaracter()!='/') {
									count++;
								}
							}
							break;
						case 1:
							if(!arribaDerechaDisponible(aux)) {
								if(aux.getCaracter()!='/') {
									count++;
								}
							}
							break;
						case 2:
							if(!derechaDisponible(aux)) {
								if(aux.getCaracter()!='/') {
									count++;
								}
							}
							break;
						case 3:
							if(!abajoDerechaDisponible(aux)) {
								if(aux.getCaracter()!='/') {
									count++;
								}
							}
							break;
						case 4:
							if(!abajoDisponible(aux)) {
								if(aux.getCaracter()!='/') {
									count++;
								}
							}
							break;
						case 5:
							if(!abajoIzquierdaDisponible(aux)) {
								if(aux.getCaracter()!='/') {
									count++;
								}
							}
							break;
						case 6:
							if(!izquierdaDisponible(aux)) {
								if(aux.getCaracter()!='/') {
									count++;
								}
							}
							break;
						case 7:
							if(!arribaIzquierdaDisponible(aux)) {
								if(aux.getCaracter()!='/') {
									count++;
								}
							}
							break;
					}
			}
		}
		if(count == 8) {
			return true;
		}else {
			return false;
		}
		
	}
	
	public void resetearMatriz() {
		
		for(int i=0;i<this.tamanyo;i++) {
			for(int j=0;j<this.tamanyo;j++) {
				if(this.matriz[i][j].getCaracter()=='/') {
					this.matriz[i][j].setCaracter('0');
				}
			}
		}
	}
	
	public void compararSoluciones(ArrayList<Casilla> solucion, ArrayList<Casilla> solucionAux) {
		if(solucion.size()==0 || solucion.size()>solucionAux.size()) {
			solucion.removeAll(solucion);
			solucion.addAll(solucionAux);
		}
	}
	
	public boolean arribaDisponible(Casilla casDesti) {
		if(casDesti.getCaracter()=='/' || casDesti.getCaracter()=='1') {
			return false;
		}
		return true;
	}
	
	public boolean arribaDerechaDisponible(Casilla casDesti) {
		if(casDesti.getCaracter()=='/' || casDesti.getCaracter()=='1') {
			return false;
		}
		return true;
	}
	
	public boolean derechaDisponible(Casilla casDesti) {
		if(casDesti.getCaracter()=='/' || casDesti.getCaracter()=='1') {
			return false;
		}
		return true;
	}
	
	public boolean abajoDerechaDisponible(Casilla casDesti) {
		if(casDesti.getCaracter()=='/' || casDesti.getCaracter()=='1') {
			return false;
		}
		return true;
	}
	
	public boolean abajoDisponible(Casilla casDesti) {
		if(casDesti.getCaracter()=='/' || casDesti.getCaracter()=='1') {
			return false;
		}
		return true;
	}
	
	public boolean abajoIzquierdaDisponible(Casilla casDesti) {
		if(casDesti.getCaracter()=='/' || casDesti.getCaracter()=='1') {
			return false;
		}
		return true;
	}
	
	public boolean izquierdaDisponible(Casilla casDesti) {
		if(casDesti.getCaracter()=='/' || casDesti.getCaracter()=='1') {
			return false;
		}
		return true;
	}
	
	public boolean arribaIzquierdaDisponible(Casilla casDesti) {
		if(casDesti.getCaracter()=='/' || casDesti.getCaracter()=='1') {
			return false;
		}
		return true;	
	}
	
	@Override
	public String toString() {
		StringBuilder cadena = new StringBuilder();
		Casilla cas;
		for(int i=0;i<solucion.size();i++) {
			cas=solucion.get(i);
			cadena.append(cas.toString());
		}
		return cadena.toString();
	}
}