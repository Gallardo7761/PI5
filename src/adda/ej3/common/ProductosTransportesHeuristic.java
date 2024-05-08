package adda.ej3.common;

import java.util.function.Predicate;
import java.util.stream.IntStream;

public class ProductosTransportesHeuristic {
	public static Double heuristic(ProductosTransportesVertex v1, Predicate<ProductosTransportesVertex> goal,
			ProductosTransportesVertex v2) {
		// COSTE MINIMO DE PRODS AUN DEMANDADOS EN [z, n*m)
		if (v1.demandasRestantes().stream().allMatch(x -> x <= 0))
			return 0.;
		Integer ultimoIndice = DatosProductosTransportes.getM() * DatosProductosTransportes.getN();
		return IntStream.range(v1.z(), ultimoIndice)
				.filter(i -> v1.demandasRestantes().get(i % DatosProductosTransportes.getM()) > 0)
				.mapToDouble(i -> DatosProductosTransportes.getCoste(i / DatosProductosTransportes.getM(),
						i % DatosProductosTransportes.getM()))
				.min().orElse(Double.MAX_VALUE);
	}
}
