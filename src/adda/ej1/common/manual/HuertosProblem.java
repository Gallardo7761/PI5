package adda.ej1.common.manual;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import adda.ej1.common.DatosHuertos;
import adda.ej1.common.DatosHuertos.Huerto;
import us.lsi.common.Set2;

public record HuertosProblem(
		Integer index, 
		List<Set<Integer>> reparto, 
		List<Integer> metrosDisponibles) {
	
	public static HuertosProblem of(Integer index, 
			List<Set<Integer>> reparto, List<Integer> metrosDisponibles) {
		return new HuertosProblem(index,reparto,metrosDisponibles);
	}
	
	public static HuertosProblem initial() {
		return of(0,repartoInicial(), 
				DatosHuertos.getHuertos().stream()
					.map(Huerto::metrosDisponibles)
					.toList());
	}
	
	private static List<Set<Integer>> repartoInicial() {
		List<Set<Integer>> res = new ArrayList<>();
		IntStream.range(0, DatosHuertos.getM())
		.forEach(i -> {
			res.add(Set.of());
		});
		return res;
	}
	
	public List<Integer> actions() {
		Stream<Integer> actions = IntStream.range(0, DatosHuertos.getM())
				.boxed()
				.filter(j -> cabe(this.index, j))
				.filter(j -> compatible(this.index, j));
		
		if(this.index < DatosHuertos.getN()) {
			List<Integer> res = actions.collect(Collectors.toList());
			res.add(-1);
			return res;
		} else {
			return List.of();
		}
	}

	private boolean cabe(Integer i, Integer j) {
		return this.metrosDisponibles.get(j) >= DatosHuertos.getMetrosRequeridos(i);
	}

	private boolean compatible(Integer i, Integer j) {
		return this.reparto.get(j).stream()
	            .noneMatch(k -> DatosHuertos.incompatible(i, k) == 1);
	}
	
	public HuertosProblem neighbor(Integer a) {
		List<Set<Integer>> nuevoReparto = new ArrayList<>();
		for(Set<Integer> s : this.reparto) {
			nuevoReparto.add(Set2.copy(s));
		}
		List<Integer> nuevosMetrosDisponibles = new ArrayList<>(this.metrosDisponibles);
		
		if(a == -1) {
			return of(this.index + 1, nuevoReparto, nuevosMetrosDisponibles);
		} else {			
			Set<Integer> nuevoSet = nuevoReparto.get(a);
			nuevoSet.add(this.index);
			nuevoReparto.set(a, nuevoSet);
			
			nuevosMetrosDisponibles.set(a, nuevosMetrosDisponibles.get(a) - 
					DatosHuertos.getMetrosRequeridos(this.index));
			
			return of(this.index + 1, nuevoReparto, nuevosMetrosDisponibles);
		}
	}
	
	public Double heuristic() {
		// MAXIMO DE VERDURAS PLANTABLES DE v1.index() HASTA DatosHuertos.getN()
		return (double) DatosHuertos.getN() - this.index;
    }
}
