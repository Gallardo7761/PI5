package adda.ej1.common;

import java.util.function.Predicate;

public class HuertosHeuristic {

    public static Double heuristic(
            HuertosVertex v1, 
            Predicate<HuertosVertex> goal,
            HuertosVertex v2) {
    	return v1.index() * 1.;
    }     
}
