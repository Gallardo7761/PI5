package adda.ej3.common;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import adda.ej3.common.DatosProductosTransportes.Destino;
import adda.ej3.common.DatosProductosTransportes.Producto;
import us.lsi.common.List2;
import us.lsi.common.Pair;
import us.lsi.graphs.virtual.VirtualVertex;

public record ProductosTransportesVertex(
		Integer z,
		List<Integer> unidadesRestantes,
		List<Integer> demandasRestantes) 
implements VirtualVertex<ProductosTransportesVertex, ProductosTransportesEdge, Integer>{
	
	public static ProductosTransportesVertex of(
			Integer z,
			List<Integer> unidadesRestantes,
			List<Integer> demandasRestantes) {
		return new ProductosTransportesVertex(z,unidadesRestantes,demandasRestantes);
	}
	
	public static ProductosTransportesVertex initial() {
		return of(0, initialValues().first(), initialValues().second());
	}
	
	private static Pair<List<Integer>,List<Integer>> initialValues() {
		List<Integer> ur = DatosProductosTransportes.getProductos().stream()
				.map(Producto::cantidad).toList();
		List<Integer> dr = DatosProductosTransportes.getDestinos().stream()
				.map(Destino::demanda).toList();
		return Pair.of(ur, dr);
	}

	// GOAL OK
	public static Predicate<ProductosTransportesVertex> goal() {
		return v -> v.z() == DatosProductosTransportes.getN() * DatosProductosTransportes.getM();
	}
	
	// GOALHASSOLUTION OK
	public static Predicate<ProductosTransportesVertex> goalHasSolution() {
		return v -> v.demandasRestantes.stream().allMatch(d -> d == 0);
	}
	
	// ACTIONS OK
	public List<Integer> actions() {
		// TODO Auto-generated method stub
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

	// NEIGHBOR OK
	@Override
	public ProductosTransportesVertex neighbor(Integer a) {
		Integer i = this.z / DatosProductosTransportes.getM();
		Integer j = this.z % DatosProductosTransportes.getM();
		List<Integer> nuevasUnidadesRestantes = new ArrayList<>(unidadesRestantes());
		List<Integer> nuevasDemandasRestantes = new ArrayList<>(demandasRestantes());

		nuevasUnidadesRestantes.set(i, nuevasUnidadesRestantes.get(i) - a);
		nuevasDemandasRestantes.set(j, nuevasDemandasRestantes.get(j) - a);
		
		return of(this.z + 1, nuevasUnidadesRestantes, nuevasDemandasRestantes);
	}

	// EDGE OK
	@Override
	public ProductosTransportesEdge edge(Integer a) {
		return ProductosTransportesEdge.of(this, neighbor(a), a);
	}

	public String toString() {
		return "("+this.z+","+this.unidadesRestantes+","+this.demandasRestantes+")";
	}
	
}
