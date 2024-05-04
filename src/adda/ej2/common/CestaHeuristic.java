package adda.ej2.common;

import java.util.function.Predicate;

public class CestaHeuristic {
	public static Double heuristic(
            CestaVertex v1, 
            Predicate<CestaVertex> goal,
            CestaVertex v2) {
		// MÍNIMO PRECIO DESDE v1.index() HASTA DatosCesta.getN()
		return Math.abs((double) DatosCesta.getPrecio(v1.index() == 0 ? 0 : v1.index() - 1) 
				- DatosCesta.getPrecio(DatosCesta.getN() - 1));
    }   
}
