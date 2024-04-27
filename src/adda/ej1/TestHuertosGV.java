package adda.ej1;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.jgrapht.GraphPath;

import adda.ej1.common.DatosHuertos;
import adda.ej1.common.HuertosEdge;
import adda.ej1.common.HuertosHeuristic;
import adda.ej1.common.HuertosVertex;
import adda.ej1.common.SolucionHuertos;
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

public class TestHuertosGV {
	public static void main(String[] args) {
		IntStream.range(1, 4).forEach(i -> {
			System.out.println(ConsoleColors.BLUE + Titles.getTitle(i) 
				+ ConsoleColors.RESET);
			DatosHuertos.iniDatos("ficheros/ejercicios/Ejercicio1DatosEntrada"+i+".txt");
			
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
		EGraph<HuertosVertex, HuertosEdge> g = EGraph.virtual(
			HuertosVertex.initial(),
			HuertosVertex.goal(),
			PathType.Sum, Type.Max
		)
		.heuristic(HuertosHeuristic::heuristic)
		.edgeWeight(e -> e.weight())
		.build();
		
		AStar<HuertosVertex, HuertosEdge,?> alg = AStar.of(g);
		GraphPath<HuertosVertex, HuertosEdge> gp = alg.search().get();
		List<Integer> ls = gp.getEdgeList().stream().map(x -> x.action())
					.collect(Collectors.toList());
		SolucionHuertos sh = new SolucionHuertos(ls);
		System.out.println(sh);
		
		GraphColors.toDot(alg.outGraph(), 
				"generated/ej1_f"+i+".dot",
				v -> v.toString(),
				e -> e.action().toString());
	}
	
	private static void testBT(int i) {
		EGraph<HuertosVertex, HuertosEdge> g = EGraph.virtual(
			HuertosVertex.initial(),
			HuertosVertex.goal(),
			PathType.Sum, Type.Max
		)
		.heuristic(HuertosHeuristic::heuristic)
		.edgeWeight(e -> e.weight())
		.build();
		
		BT<HuertosVertex, HuertosEdge,?> alg = BT.of(g);
		GraphPath<HuertosVertex, HuertosEdge> gp = alg.search().get();
		List<Integer> ls = gp.getEdgeList().stream().map(x -> x.action())
					.collect(Collectors.toList());
		SolucionHuertos sh = new SolucionHuertos(ls);
		System.out.println(sh);
	}
	
	private static void testPDR(int i) {
		EGraph<HuertosVertex, HuertosEdge> g = EGraph.virtual(
			HuertosVertex.initial(),
			HuertosVertex.goal(),
			PathType.Sum, Type.Max
		)
		.heuristic(HuertosHeuristic::heuristic)
		.edgeWeight(e -> e.weight())
		.build();
		
		PDR<HuertosVertex, HuertosEdge,?> alg = PDR.of(g);
		GraphPath<HuertosVertex, HuertosEdge> gp = alg.search().get();
		List<Integer> ls = gp.getEdgeList().stream().map(x -> x.action())
					.collect(Collectors.toList());
		SolucionHuertos sh = new SolucionHuertos(ls);
		System.out.println(sh);
	}
}
