package adda.ej3.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SolucionProductosTransportes {
	private static Map<Integer, List<Integer>> solucion;
	private static Integer costeTotal;
	
	public static SolucionProductosTransportes of_range(List<Integer> ls) {
		return new SolucionProductosTransportes(ls);
	}
	
	public SolucionProductosTransportes() {
		solucion = new HashMap<>();
		costeTotal = 0;
	}
	
	public SolucionProductosTransportes(List<Integer> ls) {
		solucion = new HashMap<>();
		costeTotal = 0;
		
		for(int i = 0; i < DatosProductosTransportes.getN(); i++) {
			solucion.put(i, new ArrayList<>());
			for(int j = 0; j < DatosProductosTransportes.getM(); j++) {
				solucion.get(i).add(ls.get(i * DatosProductosTransportes.getM() + j));
				costeTotal += ls.get(i * DatosProductosTransportes.getM() + j) *
						DatosProductosTransportes.getCoste(i, j);
			}
		}
	}
	
	public String toString() {
		String productos = solucion.entrySet().stream()
				.map(e -> e.getKey() + ": " + e.getValue())
				.collect(Collectors.joining("\n",
						"Reparto obtenido (cantidad de producto repartido a cada destino):\n" +
				"//Destino m:[cantidad de producto n, cantidad de producto n+1, ...]\n",
				"\n"));
		return String.format("%sCoste total de almacenamiento: %d", productos, costeTotal);
	}
}
