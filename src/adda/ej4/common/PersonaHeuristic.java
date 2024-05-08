package adda.ej4.common;

import java.util.Collections;
import java.util.Comparator;
import java.util.function.Predicate;
import java.util.stream.IntStream;

public class PersonaHeuristic {
	public static Double heuristic(PersonaVertex v1, Predicate<PersonaVertex> goal, PersonaVertex v2) {
		//AFINIDAD MAXIMA EN [i,n)
		if(v1.index() % 2 == 0) {
		    return (double) IntStream.range(v1.index(), DatosPersonas.getN()).boxed()
		            .map(i -> Collections.max(DatosPersonas.getAfinidades(i)))
		            .max(Comparator.naturalOrder()).orElse(0);
		} else {
		    return 0.;
		}
	}
}
