package adda.ej1.common;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import adda.ej1.common.DatosHuertos.Huerto;
import adda.ej1.common.DatosHuertos.Verdura;
import us.lsi.common.Pair;

public class SolucionHuertos {
	private static int index;
	private static Integer distintas;
	private static List<Pair<Verdura, Huerto>> solucion;
	
	public static SolucionHuertos of_range(List<Integer> ls) {
		return new SolucionHuertos(ls);
	}
	
	public SolucionHuertos() {
		distintas = 0;
		solucion = new ArrayList<>();
	}
	
	public SolucionHuertos(List<Integer> ls) {
		index = 0;
		solucion = new ArrayList<>();
		ls.stream().forEach(hue -> {
			if(hue > 0) {
				Verdura v = DatosHuertos.getVerdura(index);
				Huerto h = DatosHuertos.getHuerto(hue - 1);
				solucion.add(Pair.of(v, h));
			}
			index++;
		});
		distintas = (int) solucion.stream()
				.map(p -> p.first())
				.distinct().count();
	}
	
	public String toString() {
		String verduras = solucion.stream()
				.map(p -> p.first().nombre() + ": Huerto " + p.second().nombre().replace("H", ""))
				.collect(Collectors.joining("\n    ", "Reparto de verduras y huerto en el que es plantada " + 
						"cada una de ellas (si procede):\n    ", "\n"));
		return String.format("%sVariedades cultivadas: %d", verduras, distintas);
	}
	
	
}
