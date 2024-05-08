package adda.ej2.common.manual;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import adda.ej2.common.DatosCesta;
import adda.ej2.common.SolucionCesta;
import us.lsi.common.List2;
import us.lsi.common.Map2;

public class CestaPDR {
	
	public static record Spm(Integer a, Integer weight) implements Comparable<Spm> {
		public static Spm of(Integer a, Integer weight) {
			return new Spm(a, weight);
		}
		@Override
		public int compareTo(Spm sp) {
			return this.weight.compareTo(sp.weight);
		}
	}
	
	public static Map<CestaProblem, Spm> mem;
	public static Integer bestVal = Integer.MAX_VALUE;
	
	public static SolucionCesta sol() {
		List<Integer> actions = List2.empty();
		CestaProblem prob = CestaProblem.initial();
		Spm spm = mem.get(prob);
		while(spm != null && spm.a != null) {
			CestaProblem old = prob;
			actions.add(spm.a);
			prob = old.neighbor(spm.a);
			spm = mem.get(prob);
		}
		return new SolucionCesta(actions);
	}
	
	public static Double cota(Integer ac, CestaProblem prob, Integer a) {
		return ac + a + prob.neighbor(a).heuristic();
	}
	
	private static Spm pdr_search(CestaProblem prob, int ac, Map<CestaProblem, Spm> mem) {
		Spm res = null;
		
		boolean isTerminal = prob.index() == DatosCesta.getN();
		boolean isSolution = prob.categoriasPorCubrir().isEmpty() &&
				prob.presupuestoRestante().stream().allMatch(p -> p >= 0) &&
				prob.acumValoracion() >= 0;
		
		if(mem.containsKey(prob)) {
			res = mem.get(prob);
		} else if(isTerminal && isSolution) {
			res = Spm.of(null, 0);
			mem.put(prob, res);
			if(ac < bestVal) { // minimizando
				bestVal = ac;
			}
		} else {
			List<Spm> sols = List2.empty();
			for(Integer a : prob.actions()) {
				Double cota = cota(ac, prob, a);
				if(cota > bestVal) {
					continue;
				}
				CestaProblem n = prob.neighbor(a);
				Integer w = DatosCesta.getPrecio(prob.index()) * a;
				Spm spm = pdr_search(n, ac + w, mem);
				if(spm != null) {
					Spm aux = Spm.of(a, spm.weight() + w);
					sols.add(aux);
				}
			}
			// minimizando 
			res = sols.stream().min(Comparator.naturalOrder()).orElse(null);
			if(res != null) {
				mem.put(prob, res);
			}
		}
		return res;
	}
	
	public static SolucionCesta search() {
		mem = Map2.empty();
		bestVal = Integer.MAX_VALUE; // minimizando
		pdr_search(CestaProblem.initial(), 0, mem);
		return sol();
	}
	
}
