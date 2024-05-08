package adda.ej4.common.manual;

import java.util.stream.IntStream;

import adda.ej4.common.DatosPersonas;
import adda.util.ConsoleColors;
import adda.util.Titles;
import us.lsi.common.String2;

public class TestBT {
	public static void main(String[] args) {
		IntStream.range(1, 4).forEach(i -> {
			System.out.println(ConsoleColors.BLUE + Titles.getTitle(i) 
				+ ConsoleColors.RESET);
			DatosPersonas.iniDatos("ficheros/ejercicios/Ejercicio4DatosEntrada"+i+".txt");
			
			System.out.println(ConsoleColors.RED + "\nBT MANUAL" 
				+ String2.linea() + ConsoleColors.RESET);
			
			PersonasBT.search();
			
			System.out.println(PersonasBT.sol());
		});
	}
}
