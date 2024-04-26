package adda.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import adda.ej1.common.DatosHuertos;

public class Prueba {
	private static int index = 3;
	private static int a = -1;
	private static List<Set<Integer>> reparto = List.of(Set.of(2,3), Set.of(0,1));
	private static List<Integer> metrosDisponibles = List.of(0,0);

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<Set<Integer>> nuevoReparto = new ArrayList<>();
		for(Set<Integer> s : reparto) {
			nuevoReparto.add(new HashSet<>(s));
		}
		
		Set<Integer> nuevoSet = new HashSet<>(nuevoReparto.get(a));
		nuevoSet.add(index);
		nuevoReparto.set(a, nuevoSet);
		
		System.out.println("Reparto original: " + reparto);
		System.out.println("Reparto nuevo: " + nuevoReparto);
	}
	
	public static List<Integer> actions() {
		Stream<Integer> actions = IntStream.range(0, DatosHuertos.getM())
				.boxed()
				.filter(j -> !plantada(Prueba.index,j))
				.filter(j -> cabe(Prueba.index, j))
				.filter(j -> compatible(Prueba.index, j));
		
		if(Prueba.index < DatosHuertos.getN()) {
			List<Integer> res = actions.collect(Collectors.toList());
			res.add(-1);
			return res;
		} else {
			return List.of();
		}
	}

	private static boolean cabe(Integer i, Integer j) {
		return metrosDisponibles.get(j) >= DatosHuertos.getMetrosRequeridos(i);
	}

	private static boolean compatible(Integer i, Integer j) {
		return reparto.get(j).stream()
	            .noneMatch(k -> DatosHuertos.incompatible(i, k) == 1);
	}

	private static boolean plantada(Integer i, Integer j) {
		return reparto.get(j).contains(i);
	}

}
