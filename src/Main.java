import java.util.Scanner;

public class Main {
	
	public static void main(String[] args) {
		Scanner input = new Scanner (System.in);
		int tamanyo = input.nextInt();
		input.nextLine();
		
		Laberinto lab = new Laberinto(tamanyo, input);
		lab.leerMatriz();
		lab.resolverLaberinto();
		input.close();
	}
	
	
	
}
