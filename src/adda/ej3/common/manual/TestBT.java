package adda.ej3.common.manual;

import java.util.stream.IntStream;

import adda.ej3.common.DatosProductosTransportes;
import adda.util.ConsoleColors;
import adda.util.Titles;
import us.lsi.common.String2;

public class TestBT {
	public static void main(String[] args) {
		IntStream.range(1, 4).forEach(i -> {
			System.out.println(ConsoleColors.BLUE + Titles.getTitle(i) 
				+ ConsoleColors.RESET);
			DatosProductosTransportes.iniDatos("ficheros/ejercicios/Ejercicio3DatosEntrada"+i+".txt");
			
			System.out.println(ConsoleColors.RED + "\nBT MANUAL" 
				+ String2.linea() + ConsoleColors.RESET);
			
			ProductosTransportesBT.search();
			
			System.out.println(ProductosTransportesBT.sol());
		});
	}
}
