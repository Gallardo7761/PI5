package adda.ej1.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import adda.ej1.common.DatosHuertos.Huerto;
import us.lsi.common.Set2;
import us.lsi.graphs.virtual.VirtualVertex;

public record HuertosVertex(Integer index, 
		List<Set<Integer>> reparto, // reparto en cada huerto de las verduras ya plantadas
		List<Integer> metrosDisponibles) // metros disponibles que quedan en cada huerto
implements VirtualVertex<HuertosVertex, HuertosEdge, Integer> {
	
	public static HuertosVertex of(Integer index, 
			List<Set<Integer>> reparto, List<Integer> metrosDisponibles) {
		return new HuertosVertex(index,reparto,metrosDisponibles);
	}
	
	public static HuertosVertex initial() {
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

	public static Predicate<HuertosVertex> goal() {
		return v -> v.index == DatosHuertos.getN();
	}
	
	// X_i = en que huerto (X) se planta la verdura i -> X_0 = 1 Verdura0 en huerto 1.
	// this.index es ese i
	@Override
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
		/*List<Integer> actions = new ArrayList<>();
		if(this.index < DatosHuertos.getN()) {
			for(int j = 0; j < DatosHuertos.getM(); j++) {
				if(cabe(this.index, j) && compatible(this.index,j)) {
					actions.add(j);
				}
			}
			actions.add(-1);
		} else {
			return List.of();
		}
		return actions;*/
	}

	private boolean cabe(Integer i, Integer j) {
		return this.metrosDisponibles.get(j) >= DatosHuertos.getMetrosRequeridos(i);
	}

	private boolean compatible(Integer i, Integer j) {
		return this.reparto.get(j).stream()
	            .noneMatch(k -> DatosHuertos.incompatible(i, k) == 1);
	}

	@Override
	public HuertosVertex neighbor(Integer a) {
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
	
	@Override
	public HuertosEdge edge(Integer a) {
		return HuertosEdge.of(this, neighbor(a), a);
	}
	
	@Override
	public String toString() {
		return "("+this.index+","+this.reparto+","+this.metrosDisponibles+")";
	}
}
