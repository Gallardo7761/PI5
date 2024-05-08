package adda.ej4;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.jgrapht.GraphPath;

import adda.ej4.common.DatosPersonas;
import adda.ej4.common.PersonaEdge;
import adda.ej4.common.PersonaHeuristic;
import adda.ej4.common.PersonaVertex;
import adda.ej4.common.SolucionPersonas;
import adda.util.ConsoleColors;
import adda.util.Titles;
import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.common.String2;
import us.lsi.graphs.alg.AStar;
import us.lsi.graphs.alg.BT;
import us.lsi.graphs.alg.PDR;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.EGraph.Type;
import us.lsi.path.EGraphPath.PathType;

public class TestPersonas {
	public static void main(String[] args) {
		IntStream.range(1, 4).forEach(i -> {
			System.out.println(ConsoleColors.BLUE + Titles.getTitle(i) 
				+ ConsoleColors.RESET);
			DatosPersonas.iniDatos("ficheros/ejercicios/Ejercicio4DatosEntrada"+i+".txt");
			
			System.out.println(ConsoleColors.RED + "A ESTRELLA"
				+ String2.linea() + ConsoleColors.RESET);
			testAstar(i);
			
			System.out.println(ConsoleColors.RED + "\nBACKTRACKING" 
				+ String2.linea() + ConsoleColors.RESET);
			testBT(i);
			
			System.out.println(ConsoleColors.RED + "\nDINAMICA" 
				+ String2.linea() + ConsoleColors.RESET);
			testPDR(i);
		});
	}
	
	private static void testAstar(int i) {
		EGraph<PersonaVertex, PersonaEdge> g = EGraph.virtual(
			PersonaVertex.initial(),
			PersonaVertex.goal(),
			PathType.Sum, Type.Min
		)
		.heuristic(PersonaHeuristic::heuristic)
		.goalHasSolution(PersonaVertex.goalHasSolution())
		.edgeWeight(e -> e.weight())
		.build();
		
		AStar<PersonaVertex, PersonaEdge,?> alg = AStar.of(g);
		GraphPath<PersonaVertex, PersonaEdge> gp = alg.search().get();
		List<Integer> ls = gp.getEdgeList().stream().map(x -> x.action())
					.collect(Collectors.toList());
		SolucionPersonas sh = new SolucionPersonas(ls);
		System.out.println(sh);
		
		GraphColors.toDot(alg.outGraph(), 
				"generated/ej4_f"+i+".dot",
				v -> v.toString(),
				e -> e.action().toString(),
				v -> GraphColors.color(Color.black),
				e -> GraphColors.colorIf(Color.red, gp.getEdgeList().contains(e)));
	}
	
	private static void testBT(int i) {
		EGraph<PersonaVertex, PersonaEdge> g = EGraph.virtual(
			PersonaVertex.initial(),
			PersonaVertex.goal(),
			PathType.Sum, Type.Min
		)
		.heuristic(PersonaHeuristic::heuristic)
		.goalHasSolution(PersonaVertex.goalHasSolution())
		.edgeWeight(e -> e.weight())
		.build();
		
		BT<PersonaVertex, PersonaEdge,?> alg = BT.of(g);
		GraphPath<PersonaVertex, PersonaEdge> gp = alg.search().get();
		List<Integer> ls = gp.getEdgeList().stream().map(x -> x.action())
					.collect(Collectors.toList());
		SolucionPersonas sh = new SolucionPersonas(ls);
		System.out.println(sh);
	}
	
	private static void testPDR(int i) {
		EGraph<PersonaVertex, PersonaEdge> g = EGraph.virtual(
			PersonaVertex.initial(),
			PersonaVertex.goal(),
			PathType.Sum, Type.Min
		)
		.heuristic(PersonaHeuristic::heuristic)
		.goalHasSolution(PersonaVertex.goalHasSolution())
		.edgeWeight(e -> e.weight())
		.build();
		
		PDR<PersonaVertex, PersonaEdge,?> alg = PDR.of(g);
		GraphPath<PersonaVertex, PersonaEdge> gp = alg.search().get();
		List<Integer> ls = gp.getEdgeList().stream().map(x -> x.action())
					.collect(Collectors.toList());
		SolucionPersonas sh = new SolucionPersonas(ls);
		System.out.println(sh);
	}
}
