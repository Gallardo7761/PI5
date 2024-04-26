package adda.ej1.common;

import java.util.function.Predicate;
import java.util.stream.IntStream;

public class HuertosHeuristic {

    public static Double heuristic(
            HuertosVertex v1, 
            Predicate<HuertosVertex> goal,
            HuertosVertex v2) {
        //return heuristic1(v1, DatosHuertos.getN());
    	return v1.index() * 1.0;
    }
    
    public static Double heuristic1(HuertosVertex v1, Integer N) {
    	return IntStream.range(v1.index(), N)
    			.mapToDouble(i -> DatosHuertos.getMetrosRequeridos(i))
    			.min()
    			.getAsDouble();
    }
     
}
