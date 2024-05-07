package adda.ej3.common;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.IntStream;

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

	private static int i;
	private static int j;
	private static int m = DatosProductosTransportes.getM();
	
	public static ProductosTransportesVertex of(
			Integer z,
			List<Integer> unidadesRestantes,
			List<Integer> demandasRestantes) {
		i = z/m;
		j = z%m;
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
		return IntStream.rangeClosed(0, 
				Math.min(
					this.unidadesRestantes().get(i),
					this.demandasRestantes().get(j))
				)
				.boxed()
				.toList();
	}

	@Override
	public ProductosTransportesVertex neighbor(Integer a) {
		Integer nuevoZ = this.z + 1;
		List<Integer> nuevasUnidadesRestantes = List2.copy(this.unidadesRestantes);
		List<Integer> nuevasDemandasRestantes = List2.copy(this.demandasRestantes);
		
		if(a == -1) {
			return of(nuevoZ, nuevasUnidadesRestantes, nuevasDemandasRestantes);
		} else {
			int uRestantes = this.unidadesRestantes.get(i);
			
			nuevasUnidadesRestantes.set(i, nuevasUnidadesRestantes.get(i) - uRestantes);
			nuevasDemandasRestantes.set(j, nuevasDemandasRestantes.get(j) - uRestantes);
			
			return of(nuevoZ,nuevasUnidadesRestantes,nuevasDemandasRestantes);			
		}
	}

	@Override
	public ProductosTransportesEdge edge(Integer a) {
		// TODO Auto-generated method stub
		return ProductosTransportesEdge.of(this, neighbor(a), a);
	}

}
