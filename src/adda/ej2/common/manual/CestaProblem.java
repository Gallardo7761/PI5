package adda.ej2.common.manual;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import adda.ej2.common.DatosCesta;
import adda.ej2.common.DatosCesta.Producto;
import us.lsi.common.List2;
import us.lsi.common.Pair;
import us.lsi.common.Set2;

public record CestaProblem(
		Integer index,
		Set<Integer> categoriasPorCubrir,
		List<Integer> presupuestoRestante,
		Integer acumValoracion) {
	
	public static CestaProblem of(
			Integer index,
			Set<Integer> categoriasPorCubrir,
			List<Integer> presupuestoRestante,
			Integer acumValoracion) {
		return new CestaProblem(index,categoriasPorCubrir,
				presupuestoRestante,acumValoracion);
	}
	
	public static CestaProblem initial() {
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
	
	public CestaProblem neighbor(Integer a) {
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
	
	public Double heuristic() {
		// MÍNIMO PRECIO DESDE v1.index() HASTA DatosCesta.getN()
		return Math.abs((double) DatosCesta.getPrecio(this.index() == 0 ? 0 : this.index() - 1) 
				- DatosCesta.getPrecio(DatosCesta.getN() - 1));
    }  
	
}
