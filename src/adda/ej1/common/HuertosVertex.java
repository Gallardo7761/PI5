package adda.ej1.common;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import adda.ej1.common.DatosHuertos.Huerto;
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
		Stream<Integer> actions = IntStream.range(0, DatosHuertos.getM()).boxed()
				.filter(j -> !plantada(this.index,j))
				.filter(j -> compatible(this.index,j))
				.filter(j -> cabe(this.index,j));
		List<Integer> res = actions.collect(Collectors.toList());
		res.add(-1);
		return res;
	}

	private boolean cabe(Integer i, Integer j) {
		return this.metrosDisponibles.get(j) >= DatosHuertos.getMetrosRequeridos(i);
	}

	private boolean compatible(Integer i, Integer j) {
		return this.reparto.get(j).stream()
	            .noneMatch(k -> DatosHuertos.incompatible(i, k) == 1);
	}

	private boolean plantada(Integer i, Integer j) {
		return this.reparto.get(j).contains(i);
	}
	
	@Override
	public HuertosVertex neighbor(Integer a) {
		if(a == -1) {
			return HuertosVertex.of(this.index + 1, this.reparto, this.metrosDisponibles);
		} else {			
			List<Set<Integer>> nuevoReparto = new ArrayList<>(this.reparto);
			Set<Integer> nuevoSet = new HashSet<>(nuevoReparto.get(a));
			nuevoSet.add(this.index);
			nuevoReparto.set(a, nuevoSet);
			
			List<Integer> nuevosMetrosDisponibles = new ArrayList<>(this.metrosDisponibles);
			nuevosMetrosDisponibles.set(a, nuevosMetrosDisponibles.get(a) - 
					DatosHuertos.getMetrosRequeridos(this.index));
			return HuertosVertex.of(this.index + 1, nuevoReparto, nuevosMetrosDisponibles);
		}
	}
	
	@Override
	public HuertosEdge edge(Integer a) {
		return HuertosEdge.of(this, neighbor(a), a);
	}
}
