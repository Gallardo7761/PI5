package adda.ej1.common;

import java.util.function.Predicate;

public class HuertosHeuristic {

	public static Double heuristic(
            HuertosVertex v1, 
            Predicate<HuertosVertex> goal,
            HuertosVertex v2) {
		// MAXIMO DE VERDURAS PLANTABLES DE v1.index() HASTA DatosHuertos.getN()
		return (double) DatosHuertos.getN() - v1.index();
    }    
}
