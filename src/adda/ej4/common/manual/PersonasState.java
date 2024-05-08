package adda.ej4.common.manual;

import java.util.List;

import adda.ej4.common.DatosPersonas;
import adda.ej4.common.SolucionPersonas;
import us.lsi.common.List2;

public class PersonasState {
	
	PersonasProblem actual;
	Double ac;
	List<Integer> actions;
	List<PersonasProblem> prev;
	
	private PersonasState(PersonasProblem prob, Double a, 
			List<Integer> li, List<PersonasProblem> lp) {
		actual = prob;
		ac = a;
		actions = li;
		prev = lp;
	}
	
	public static PersonasState of(PersonasProblem prob, Double ac, 
			List<Integer> li, List<PersonasProblem> lp) {
		return new PersonasState(prob, ac, li, lp);
	}
	
	public static PersonasState initial() {
		PersonasProblem p = PersonasProblem.initial();
		return of(p, 0., List2.empty(), List2.empty());
	}
	
	public void forward(Integer a) {
		if(actual.ultima() != DatosPersonas.getN()) {
			ac += DatosPersonas.getAfinidad(actual.ultima(), a);
		}
		actions.add(a);
		prev.add(actual);
		actual = actual.neighbor(a);
	}
	
	public void back() {
		int last = actions.size() - 1;
		var prev_prob = prev.get(last);
		if(prev_prob.ultima() != DatosPersonas.getN()) {
			ac -= DatosPersonas.getAfinidad(prev_prob.ultima(), actions.get(last));
		}
		actions.remove(last);
		prev.remove(last);
		actual = prev_prob;
	}
	
	public List<Integer> actions() {
		return actual.actions();
	}
	
	public Double cota(Integer a) {
		Integer w = 0;
		if(actual.ultima() != DatosPersonas.getN()) {
			w = DatosPersonas.getAfinidad(actual.ultima(), a);
		}
		return ac + w + actual.neighbor(a).heuristic();
	}
	
	public boolean isSolution() {
		return true;
	}
	
	public boolean isTerminal() {
		return actual.index() == DatosPersonas.getN();
	}
	
	public SolucionPersonas sol() {
		return new SolucionPersonas(actions);
	}
}
