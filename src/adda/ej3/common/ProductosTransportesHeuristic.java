package adda.ej3.common;

import java.util.Comparator;
import java.util.function.Predicate;

public class ProductosTransportesHeuristic {
	public static Double heuristic(
			ProductosTransportesVertex v1, 
            Predicate<ProductosTransportesVertex> goal,
            ProductosTransportesVertex v2) {
		// COSTE MINIMO EN SITIOS QUE AUN SE DEMANDA
		return (double) ProductosTransportesVertex.initial().demandasRestantes().stream()
					.filter(i -> i != 0)
					.map(d -> ProductosTransportesVertex.initial().demandasRestantes().indexOf(d))
					.map(s -> DatosProductosTransportes.getCoste(v1.z(), s))
					.min(Comparator.naturalOrder())
					.get();
    } 
}
