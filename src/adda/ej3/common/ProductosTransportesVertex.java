package adda.ej3.common;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import adda.ej1.common.DatosHuertos;
import adda.ej3.common.DatosProductosTransportes.Destino;
import adda.ej3.common.DatosProductosTransportes.Producto;
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

	public static Predicate<ProductosTransportesVertex> goal() {
		return v -> v.z() == DatosProductosTransportes.getN() * DatosProductosTransportes.getM();
	}
	
	@Override
	public List<Integer> actions() {
		Stream<Integer> actions = IntStream.range(0, DatosProductosTransportes.getM())
				.boxed()
				.filter(j -> quedaProducto(this.z())) // hay unidades del producto
				.filter(j -> !demandaMinimaOk(j)); // demanda minima no cubierta todavia
		
		if(this.z() < DatosProductosTransportes.getN() * DatosProductosTransportes.getM()) {
			List<Integer> res = actions.collect(Collectors.toList());
			res.add(-1);
			return res;
		} else {
			return List.of();
		}
	}
	
	private boolean demandaMinimaOk(Integer j) {
		// TODO Auto-generated method stub
		return this.demandasRestantes.get(j) == 0;
	}

	private boolean quedaProducto(Integer i) {
		// TODO Auto-generated method stub
		return this.unidadesRestantes.get(i) > 0;
	}

	@Override
	public ProductosTransportesVertex neighbor(Integer a) {
		// TODO Auto-generated method stub
		if(a == -1) {
			return of(this.z() + 1, )
		} else {
			
		}
	}

	@Override
	public ProductosTransportesEdge edge(Integer a) {
		// TODO Auto-generated method stub
		return ProductosTransportesEdge.of(this, neighbor(a), a);
	}

}