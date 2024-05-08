package adda.ej4.common;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import us.lsi.common.Pair;

public class SolucionPersonas {
	private static List<Pair<Integer,Integer>> solucion;
	private static Integer sumaAf;
	
	public static SolucionPersonas of_list(List<Integer> ls) {
		return new SolucionPersonas(ls);
	}
	
	public SolucionPersonas() {
		sumaAf = 0;
		solucion = new ArrayList<>();
	}
	
	public SolucionPersonas(List<Integer> ls) {
		sumaAf = 0;
		solucion = new ArrayList<>();
		for(int i = 0; i < ls.size() - 1; i+=2) {
			solucion.add(Pair.of(ls.get(i), ls.get(i+1)));
			sumaAf += DatosPersonas.getAfinidad(ls.get(i), ls.get(i+1));
		}
	}
	
	public String toString() {
		String personas = solucion.stream()
				.map(p -> "(" + p.first() + ", " + p.second() + ")")
				.collect(Collectors.joining(", ", "Relación de parejas:\n[", "]\n"));
		return String.format("%sSuma de afinidades: %d", personas, sumaAf);
	}
}
