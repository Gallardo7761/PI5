package adda.ej3.common.manual;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import adda.ej3.common.DatosProductosTransportes;
import adda.ej3.common.DatosProductosTransportes.Destino;
import adda.ej3.common.DatosProductosTransportes.Producto;
import us.lsi.common.List2;
import us.lsi.common.Pair;

public record ProductosTransportesProblem (
		Integer z,
		List<Integer> unidadesRestantes,
		List<Integer> demandasRestantes) {
	
	public static ProductosTransportesProblem of(
			Integer z,
			List<Integer> unidadesRestantes,
			List<Integer> demandasRestantes) {
		return new ProductosTransportesProblem(z,unidadesRestantes,demandasRestantes);
	}
	
	public static ProductosTransportesProblem initial() {
		return of(0, initialValues().first(), initialValues().second());
	}
	
	private static Pair<List<Integer>,List<Integer>> initialValues() {
		List<Integer> ur = DatosProductosTransportes.getProductos().stream()
				.map(Producto::cantidad).toList();
		List<Integer> dr = DatosProductosTransportes.getDestinos().stream()
				.map(Destino::demanda).toList();
		return Pair.of(ur, dr);
	}
	
	public List<Integer> actions() {
		List<Integer> actions = new ArrayList<>();
		Integer totVars = DatosProductosTransportes.getN() *
				DatosProductosTransportes.getM();

		if(z < totVars) {
			Integer uds = this.unidadesRestantes
					.get(z/DatosProductosTransportes.getM());
			Integer dem = this.demandasRestantes
					.get(z%DatosProductosTransportes.getM());
			if (dem == 0 || uds == 0) {
				actions = List.of(0);
			} else if (uds < 0) {
				return List2.empty();
			} else if (uds < dem) {
				actions = List.of(0, uds);
			} else {
				actions = List.of(0, dem);
			}
			return actions;
		} else {
			return actions;
		}
	}

	public ProductosTransportesProblem neighbor(Integer a) {
		Integer i = this.z / DatosProductosTransportes.getM();
		Integer j = this.z % DatosProductosTransportes.getM();
		List<Integer> nuevasUnidadesRestantes = new ArrayList<>(unidadesRestantes());
		List<Integer> nuevasDemandasRestantes = new ArrayList<>(demandasRestantes());

		nuevasUnidadesRestantes.set(i, nuevasUnidadesRestantes.get(i) - a);
		nuevasDemandasRestantes.set(j, nuevasDemandasRestantes.get(j) - a);
		
		return of(this.z + 1, nuevasUnidadesRestantes, nuevasDemandasRestantes);
	}
	
	public Double heuristic() {
		// COSTE MINIMO DE PRODS AUN DEMANDADOS EN [z, n*m)
		if (this.demandasRestantes().stream().allMatch(x -> x <= 0))
			return 0.;
		Integer ultimoIndice = DatosProductosTransportes.getM() * DatosProductosTransportes.getN();
		return IntStream.range(this.z(), ultimoIndice)
				.filter(i -> this.demandasRestantes().get(i % DatosProductosTransportes.getM()) > 0)
				.mapToDouble(i -> DatosProductosTransportes.getCoste(i / DatosProductosTransportes.getM(),
						i % DatosProductosTransportes.getM()))
				.min().orElse(Double.MAX_VALUE);
	}
	
}
