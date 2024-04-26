package adda.ej1;

import java.util.List;
import java.util.stream.IntStream;

import org.jgrapht.GraphPath;

import adda.ej1.common.DatosHuertos;
import adda.ej1.common.HuertosEdge;
import adda.ej1.common.HuertosHeuristic;
import adda.ej1.common.HuertosVertex;
import adda.ej1.common.SolucionHuertos;
import adda.util.ConsoleColors;
import adda.util.Titles;
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
			testAstar();
			
			System.out.println(ConsoleColors.RED + "\nBACKTRACKING" 
				+ String2.linea() + ConsoleColors.RESET);
			testBT();
			
			System.out.println(ConsoleColors.RED + "\nDINAMICA" 
				+ String2.linea() + ConsoleColors.RESET);
			testPDR();
		});
	}
	
	private static List<Integer> transform(
			GraphPath<HuertosVertex,HuertosEdge> path) {
		return path.getEdgeList().stream()
			.map(e -> e.action()).toList();
	}
	
	private static void testAstar() {
		var g = EGraph.virtual(
			HuertosVertex.initial(),
			HuertosVertex.goal(),
			PathType.Sum, Type.Max
		)
		.heuristic(HuertosHeuristic::heuristic)
		.edgeWeight(e -> e.weight())
		.build();
		
		var alg = AStar.of(g);
		List<Integer> ls = transform(alg.search().get());
		System.out.println(new SolucionHuertos(ls));
	}
	
	private static void testBT() {
		var g = EGraph.virtual(
			HuertosVertex.initial(),
			HuertosVertex.goal(),
			PathType.Sum, Type.Max
		)
		.heuristic(HuertosHeuristic::heuristic)
		.edgeWeight(e -> e.weight())
		.build();
		
		var alg = BT.of(g);
		List<Integer> ls = transform(alg.search().get());
		System.out.println(new SolucionHuertos(ls));
	}
	
	private static void testPDR() {
		var g = EGraph.virtual(
			HuertosVertex.initial(),
			HuertosVertex.goal(),
			PathType.Sum, Type.Max
		)
		.heuristic(HuertosHeuristic::heuristic)
		.edgeWeight(e -> e.weight())
		.build();
		
		var alg = PDR.of(g);
		List<Integer> ls = transform(alg.search().get());
		System.out.println(new SolucionHuertos(ls));
	}
}
