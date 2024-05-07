package adda.ej3.common;

import java.util.Comparator;
import java.util.function.Predicate;

public class ProductosTransportesHeuristic {
	public static Double heuristic(
			ProductosTransportesVertex v1, 
            Predicate<ProductosTransportesVertex> goal,
            ProductosTransportesVertex v2) {
		// COSTE MINIMO EN SITIOS QUE AUN SE DEMANDA
		int m = DatosProductosTransportes.getM();
		return (double) ProductosTransportesVertex.initial().demandasRestantes().stream()
					.filter(i -> i != 0)
					.map(d -> ProductosTransportesVertex.initial().demandasRestantes().indexOf(d))
					.map(s -> DatosProductosTransportes.getCoste(v1.z()/m, s))
					.min(Comparator.naturalOrder())
					.get();
		/*List<Integer> ls = ProductosTransportesVertex.initial().demandasRestantes();
		List<Integer> ls2 = new ArrayList<>();
		List<Integer> ls3 = new ArrayList<>();
		for(Integer i : ls) {
			if(i != 0) {
				ls2.add(ls.indexOf(i));
			}
		}
		for(Integer i : ls2) {
           ls3.add(DatosProductosTransportes.getCoste(v1.z()/m, i));
		}
		Double res = (double) ls3.stream().min(Comparator.naturalOrder()).get();
		return res;*/
    } 
}
