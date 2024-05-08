package adda.ej4.common.manual;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import adda.ej4.common.DatosPersonas;
import us.lsi.common.Set2;

public record PersonasProblem(Integer index, Set<Integer> restantes, Integer ultima) {
	public static PersonasProblem of(Integer i, Set<Integer> restantes, Integer ultima) {
		return new PersonasProblem(i, restantes, ultima);
	}

	public static PersonasProblem initial() {
		Set<Integer> restantes = IntStream.range(0, DatosPersonas.getN()).boxed().map(i -> DatosPersonas.getId(i))
				.collect(Collectors.toSet());
		return of(0, restantes, DatosPersonas.getN());
	}

	public List<Integer> actions() {
	    List<Integer> actions = new ArrayList<>();
	    
	    if (this.index() < DatosPersonas.getN()) {
	        List<Integer> copyRestantes = new ArrayList<>(restantes());
	        for (int i = 0; i < copyRestantes.size(); i++) {
	            if (this.index() % 2 == 0) {
	                Integer p = copyRestantes.get(0);
	                actions.add(p);
	                copyRestantes.remove(p);
	            } else if (this.index() % 2 == 1) {
	                Integer p = copyRestantes.get(i);
	                if (restsOk(ultima(), p)) {
	                    actions.add(p);
	                    copyRestantes.remove(p);
	                }
	            }
	        }
	    }
	    
	    return actions;
	}


	private boolean restsOk(Integer persona1, Integer persona2) {
		return Math.abs(DatosPersonas.getEdad(persona1) - DatosPersonas.getEdad(persona2)) <= 5
				&& !Collections.disjoint(DatosPersonas.getIdiomas(persona1), DatosPersonas.getIdiomas(persona2))
				&& !DatosPersonas.getNacionalidad(persona1).equals(DatosPersonas.getNacionalidad(persona2));		
	}

	public PersonasProblem neighbor(Integer a) {
		Set<Integer> nuevasRestantes = new HashSet<>();
		Integer nuevaUltima = a;
		
		if(this.index % 2 == 0) {
			nuevasRestantes = Set2.copy(this.restantes);
			nuevasRestantes.remove(a);
			nuevaUltima = a;
		} else {
			nuevasRestantes = Set2.copy(this.restantes);
			nuevasRestantes.remove(a);
			nuevaUltima = DatosPersonas.getN();
		}
		
		return of(this.index + 1, nuevasRestantes, nuevaUltima);
	}
	
	public Double heuristic() {
		//AFINIDAD MAXIMA EN [i,n)
		if(this.index() % 2 == 0) {
		    return (double) IntStream.range(this.index(), DatosPersonas.getN()).boxed()
		            .map(i -> Collections.max(DatosPersonas.getAfinidades(i)))
		            .max(Comparator.naturalOrder()).orElse(0);
		} else {
		    return 0.;
		}
	}
}
