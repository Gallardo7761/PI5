package adda.ej2.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import adda.ej2.common.DatosCesta.Producto;
import us.lsi.common.List2;
import us.lsi.common.Pair;
import us.lsi.common.Set2;
import us.lsi.graphs.virtual.VirtualVertex;

public record CestaVertex(
		Integer index,
		Set<Integer> categoriasPorCubrir,
		List<Integer> presupuestoRestante,
		Integer acumValoracion)
implements VirtualVertex<CestaVertex, CestaEdge, Integer> {
	
	public static CestaVertex of(
			Integer index,
			Set<Integer> categoriasPorCubrir,
			List<Integer> presupuestoRestante,
			Integer acumValoracion) {
		return new CestaVertex(index,categoriasPorCubrir,
				presupuestoRestante,acumValoracion);
	}
	
	public static CestaVertex initial() {
		return of(0,initValues().first(),initValues().second(),0);
	}
	
	private Integer catActual() {
		return DatosCesta.getCategoria(this.index);
	}
	
	private static Pair<Set<Integer>,List<Integer>> initValues() {
		Set<Integer> cpc = DatosCesta.getProductos().stream()
				.map(Producto::categoria)
				.collect(Collectors.toSet());
		
		List<Integer> pr = new ArrayList<>();
		
		IntStream.range(0, DatosCesta.getM())
			.forEach(i -> pr.add(DatosCesta.getPresupuesto()));
		
		return Pair.of(cpc, pr);	
	}
	
	// index ES EL PRODUCTO i
	public static Predicate<CestaVertex> goal()  {
		return v -> v.index() == DatosCesta.getN();
	}
	
	public static Predicate<CestaVertex> goalHasSolution() {
		return v -> cubreTodas(v) && !superaPresupuesto(v) && mediaValoracionesSupera(v);
	}

	@Override
	public List<Integer> actions() {
		// Si se selecciona el producto i -> List.of(1)
		// Si no se selecciona -> List.of(0)
		if(this.index < DatosCesta.getN()) {
			Integer cat = DatosCesta.getCategoria(this.index);
			if((this.categoriasPorCubrir.size() != 0 && 
					DatosCesta.getProducto(this.index)
						.tieneCategoriaEn(this.categoriasPorCubrir)) &&
				DatosCesta.getPresupuesto() - this.presupuestoRestante.get(cat) >= 0) {
				return List.of(1,0);
			} else {
				return List.of(0);
			}
		} 
		return List.of();
	}
	
	private static boolean mediaValoracionesSupera(CestaVertex v) {
		return v.acumValoracion >= 0;
	}

	private static boolean superaPresupuesto(CestaVertex v) {
		boolean res = false;
		for(Integer p : v.presupuestoRestante())  {
			if(p > DatosCesta.getPresupuesto()) {
				res = true;
			}
		}
		return res;
	}

	private static boolean cubreTodas(CestaVertex v) {
		return v.categoriasPorCubrir().size() == 0;
	}

	@Override
	public CestaVertex neighbor(Integer a) {
		if(a == 0) {
			return of(this.index() + 1, Set2.copy(this.categoriasPorCubrir()),
					List2.copy(this.presupuestoRestante()), this.acumValoracion());
		} else {
			Set<Integer> nuevasCategorias = Set2.copy(this.categoriasPorCubrir());
			nuevasCategorias.remove(DatosCesta.getCategoria(this.index()));
			
			List<Integer> nuevoPresupuesto = List2.copy(this.presupuestoRestante());
			nuevoPresupuesto.set(this.catActual(), 
					nuevoPresupuesto.get(this.catActual()) -
						DatosCesta.getPrecio(this.index()));
			
			Integer nuevoAcum = this.acumValoracion();
			
			return of(this.index() + 1, nuevasCategorias, nuevoPresupuesto, 
					nuevoAcum + DatosCesta.getValoracion(this.index) - 3);
		}
	}

	@Override
	public CestaEdge edge(Integer a) {
		return CestaEdge.of(this, neighbor(a), a);
	}

	public String toString() {
		return "("+this.index+", "+this.categoriasPorCubrir()+", "+
			this.presupuestoRestante()+", "+this.acumValoracion()+")";
	}
	
}





