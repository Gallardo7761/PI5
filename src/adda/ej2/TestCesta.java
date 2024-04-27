package adda.ej2;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.jgrapht.GraphPath;

import adda.ej2.common.CestaEdge;
import adda.ej2.common.CestaHeuristic;
import adda.ej2.common.CestaVertex;
import adda.ej2.common.DatosCesta;
import adda.ej2.common.SolucionCesta;
import adda.util.ConsoleColors;
import adda.util.Titles;
import us.lsi.colors.GraphColors;
import us.lsi.common.String2;
import us.lsi.graphs.alg.AStar;
import us.lsi.graphs.alg.BT;
import us.lsi.graphs.alg.PDR;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.EGraph.Type;
import us.lsi.path.EGraphPath.PathType;

public class TestCesta {
	public static void main(String[] args) {
		IntStream.range(1, 4).forEach(i -> {
			System.out.println(ConsoleColors.BLUE + Titles.getTitle(i) 
				+ ConsoleColors.RESET);
			DatosCesta.iniDatos("ficheros/ejercicios/Ejercicio2DatosEntrada"+i+".txt");
			
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
		EGraph<CestaVertex, CestaEdge> g = EGraph.virtual(
			CestaVertex.initial(),
			CestaVertex.goal(),
			PathType.Last, Type.Min
		)
		.goalHasSolution(CestaVertex.goalHasSolution())
		.heuristic(CestaHeuristic::heuristic)
		.edgeWeight(e -> e.weight())
		.build();
		
		AStar<CestaVertex, CestaEdge,?> alg = AStar.of(g);
		GraphPath<CestaVertex, CestaEdge> gp = alg.search().get();
		List<Integer> ls = gp.getEdgeList().stream().map(x -> x.action())
					.collect(Collectors.toList());
		SolucionCesta sh = new SolucionCesta(ls);
		System.out.println(ls);
		
		/*GraphColors.toDot(alg.outGraph(), 
				"generated/ej1_f"+i+".dot",
				v -> v.toString(),
				e -> e.action().toString());*/
	}
	
	private static void testBT(int i) {
		EGraph<CestaVertex, CestaEdge> g = EGraph.virtual(
			CestaVertex.initial(),
			CestaVertex.goal(),
			PathType.Last, Type.Min
		)
		.goalHasSolution(CestaVertex.goalHasSolution())
		.heuristic(CestaHeuristic::heuristic)
		.edgeWeight(e -> e.weight())
		.build();
		
		BT<CestaVertex, CestaEdge,?> alg = BT.of(g);
		GraphPath<CestaVertex, CestaEdge> gp = alg.search().get();
		List<Integer> ls = gp.getEdgeList().stream().map(x -> x.action())
					.collect(Collectors.toList());
		SolucionCesta sh = new SolucionCesta(ls);
		System.out.println(ls);
	}
	
	private static void testPDR(int i) {
		EGraph<CestaVertex, CestaEdge> g = EGraph.virtual(
			CestaVertex.initial(),
			CestaVertex.goal(),
			PathType.Last, Type.Min
		)
		.goalHasSolution(CestaVertex.goalHasSolution())
		.heuristic(CestaHeuristic::heuristic)
		.edgeWeight(e -> e.weight())
		.build();
		
		PDR<CestaVertex, CestaEdge,?> alg = PDR.of(g);
		GraphPath<CestaVertex, CestaEdge> gp = alg.search().get();
		List<Integer> ls = gp.getEdgeList().stream().map(x -> x.action())
					.collect(Collectors.toList());
		SolucionCesta sh = new SolucionCesta(ls);
		System.out.println(ls);
	}
}
