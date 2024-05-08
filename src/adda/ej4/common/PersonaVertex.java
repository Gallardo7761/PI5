package adda.ej4.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import us.lsi.common.Set2;
import us.lsi.graphs.virtual.VirtualVertex;

public record PersonaVertex(Integer index, Set<Integer> restantes, Integer ultima)
		implements VirtualVertex<PersonaVertex, PersonaEdge, Integer> {

	public static PersonaVertex of(Integer i, Set<Integer> restantes, Integer ultima) {
		return new PersonaVertex(i, restantes, ultima);
	}

	public static PersonaVertex initial() {
		Set<Integer> restantes = IntStream.range(0, DatosPersonas.getN()).boxed().map(i -> DatosPersonas.getId(i))
				.collect(Collectors.toSet());
		return of(0, restantes, DatosPersonas.getN());
	}

	public static Predicate<PersonaVertex> goal() {
		return v -> v.index() == DatosPersonas.getN();
	}

	public static Predicate<PersonaVertex> goalHasSolution() {
		return v -> v.restantes().isEmpty();
	}

	@Override
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

	@Override
	public PersonaVertex neighbor(Integer a) {
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

	@Override
	public PersonaEdge edge(Integer a) {
		return PersonaEdge.of(this, neighbor(a), a);
	}

	public String toString() {
		return "(" + this.index + "," + this.restantes + "," + this.ultima + ")";
	}
}
