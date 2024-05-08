package adda.ej3.common.manual;

import java.util.List;

public class ProductosTransportesBT {
	private static Double bestVal;
	private static ProductosTransportesState state;
	private static List<Integer> sol;
	
	public static void search() {
		sol = null;
		bestVal = Double.MAX_VALUE; // minimizando
		state = ProductosTransportesState.initial();
		bt_search();
	}
	
	private static void bt_search() {
		if(state.isSolution()) {
			Double d = state.ac;
			if(d < bestVal) { // minimizando
				bestVal = d;
				sol = state.sol();
			}
		} else if(!state.isTerminal()) {
			for(Integer a : state.actual.actions()) {
				if(state.cota(a) < bestVal) { // minimizando
					state.forward(a);
					bt_search();
					state.back();
				}
			}
		}
	}
	
	public static List<Integer> sol() {
		return sol;
	}
}
