package adda.ej3.common.manual;

import java.util.List;

import adda.ej3.common.DatosProductosTransportes;
import us.lsi.common.List2;

public class ProductosTransportesState {
	ProductosTransportesProblem actual;
	Double ac;
	List<Integer> actions;
	List<ProductosTransportesProblem> prev;
	
	private ProductosTransportesState(ProductosTransportesProblem prob, Double a,
			List<Integer> li, List<ProductosTransportesProblem> lp) {
		actual = prob;
		ac = a;
		actions = li;
		prev = lp;
	}
	
	public static ProductosTransportesState of(ProductosTransportesProblem prob, Double ac, 
			List<Integer> li, List<ProductosTransportesProblem> lp) {
		return new ProductosTransportesState(prob,ac,li,lp);
	}
	
	public static ProductosTransportesState initial() {
		ProductosTransportesProblem p = ProductosTransportesProblem.initial();
		return of(p, 0., List2.empty(), List2.empty());
	}
	
	public void forward(Integer a) {
		ac += a * DatosProductosTransportes.getCoste(actual.z()/DatosProductosTransportes.getM(),
				actual.z()%DatosProductosTransportes.getM());
		actions.add(a);
		prev.add(actual);
		actual = actual.neighbor(a);
	}
	
	public void back() {
		int last = actions.size() - 1;
		var prev_prob = prev.get(last);
		
		ac -= actions.get(last) * DatosProductosTransportes.getCoste(prev_prob.z()/DatosProductosTransportes.getM(),
				prev_prob.z()%DatosProductosTransportes.getM());
		
		actions.remove(last);
		prev.remove(last);
		actual = prev_prob;
	}
	
	public List<Integer> actions() {
		return actual.actions();
	}
	
	public Double cota(Integer a) {
		Double w = (double) a * DatosProductosTransportes.getCoste(actual.z()/DatosProductosTransportes.getM(),
				actual.z()%DatosProductosTransportes.getM());
		return ac + w + actual.neighbor(a).heuristic();
	}
	
	public boolean isSolution() {
		return actual.demandasRestantes().stream().allMatch(x -> x <= 0);
	}
	
	public boolean isTerminal() {
		return actual.z() == DatosProductosTransportes.getN() * DatosProductosTransportes.getM();
	}
	
	public List<Integer> sol() {
		return actions;
	}
}
