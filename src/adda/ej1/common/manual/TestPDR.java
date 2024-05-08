package adda.ej1.common.manual;

import java.util.stream.IntStream;

import adda.ej1.common.DatosHuertos;
import adda.util.ConsoleColors;
import adda.util.Titles;
import us.lsi.common.String2;

public class TestPDR {
	public static void main(String[] args) {
		IntStream.range(1, 4).forEach(i -> {
			System.out.println(ConsoleColors.BLUE + Titles.getTitle(i) 
				+ ConsoleColors.RESET);
			DatosHuertos.iniDatos("ficheros/ejercicios/Ejercicio1DatosEntrada"+i+".txt");
			
			System.out.println(ConsoleColors.RED + "\nPDR" 
				+ String2.linea() + ConsoleColors.RESET);
			System.out.println(HuertosPDR.search());
		});
	}
}
