package adda.ej4.common.manual;

import adda.ej4.common.SolucionPersonas;

public class PersonasBT {
	
	private static Double bestVal;
	private static PersonasState state;
	private static SolucionPersonas sol;
	
	public static void search() {
		sol = null;
		bestVal = Double.MIN_VALUE; // maximizando
		state = PersonasState.initial();
		bt_search();
	}
	
	private static void bt_search() {
		if(state.isTerminal()) {
			Double d = state.ac;
			if(d > bestVal) { // maximizando
				bestVal = d;
				sol = state.sol();
			}
		} else {
			for(Integer a : state.actual.actions()) {
				Double cota = state.cota(a);
				if(cota < bestVal) {
					continue;
				}
				state.forward(a);
				bt_search();
				state.back();
			}
		}
	}
	
	public static SolucionPersonas sol() {
		return sol;
	}
}
