package adda.ej1.common.manual;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import adda.ej1.common.DatosHuertos;
import adda.ej1.common.SolucionHuertos;
import us.lsi.common.List2;
import us.lsi.common.Map2;

public class HuertosPDR {
	
	public static record Spm(Integer a, Integer weight) implements Comparable<Spm> {
		public static Spm of(Integer a, Integer weight) {
			return new Spm(a, weight);
		}
		@Override
		public int compareTo(Spm sp) {
			return this.weight.compareTo(sp.weight);
		}
	}
	
	public static Map<HuertosProblem, Spm> mem;
	public static Integer bestVal = Integer.MIN_VALUE;
	
	private static SolucionHuertos sol() {
		List<Integer> actions = List2.empty();
		HuertosProblem prob = HuertosProblem.initial();
		Spm spm = mem.get(prob);
		while(spm != null && spm.a != null) {
			HuertosProblem old = prob;
			actions.add(spm.a);
			prob = old.neighbor(spm.a);
			spm = mem.get(prob);
		}
		return new SolucionHuertos(actions);
	}
	
	private static Double cota(Integer ac, HuertosProblem prob, Integer a) {
		Integer w = a == -1 ? 0 : 1;
		return ac + w + prob.neighbor(a).heuristic();
	}
	
	private static Spm pdr_search(HuertosProblem prob, Integer ac) {
		Spm res = null;
		boolean isTerminal = prob.index() == DatosHuertos.getN();
		boolean isSolution = true;
		
		if(mem.containsKey(prob)) {
			res = mem.get(prob);
		} else if(isTerminal && isSolution) {
			res = Spm.of(null, 0);
			mem.put(prob, res);
			if(ac > bestVal) { //maximizando
				bestVal = ac;
			}
		} else {
			List<Spm> sols = List2.empty();
			for(Integer a : prob.actions()) {
				Double cota = cota(ac, prob, a);
				if(cota <= bestVal) {
					continue;
				}
				HuertosProblem n = prob.neighbor(a);
				Integer w = a == -1 ? 0 : 1;
				
				Spm spm = pdr_search(n, ac + w);
				if(spm != null) {
					Spm aux = Spm.of(a, spm.weight() + w);
					sols.add(aux);
				}
			}
			// maximizando
			res = sols.stream().max(Comparator.naturalOrder()).orElse(null);
			if(res != null) {
				mem.put(prob, res);
			}
		}
		return res;
	}
	
	public static SolucionHuertos search() {
		mem = Map2.empty();
		bestVal = Integer.MIN_VALUE; // maximizando
		pdr_search(HuertosProblem.initial(),0);
		return sol();
	}
}
