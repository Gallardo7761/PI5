package adda.ej3;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.jgrapht.GraphPath;

import adda.ej3.common.DatosProductosTransportes;
import adda.ej3.common.ProductosTransportesEdge;
import adda.ej3.common.ProductosTransportesHeuristic;
import adda.ej3.common.ProductosTransportesVertex;
import adda.ej3.common.SolucionProductosTransportes;
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

public class TestProductosTransportes {
	public static void main(String[] args) {
		IntStream.range(1, 4).forEach(i -> {
			System.out.println(ConsoleColors.BLUE + Titles.getTitle(i) 
				+ ConsoleColors.RESET);
			DatosProductosTransportes.iniDatos("ficheros/ejercicios/Ejercicio3DatosEntrada"+i+".txt");
			
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
		EGraph<ProductosTransportesVertex, ProductosTransportesEdge> g = EGraph.virtual(
			ProductosTransportesVertex.initial(),
			ProductosTransportesVertex.goal(),
			PathType.Sum, Type.Min
		)
		.heuristic(ProductosTransportesHeuristic::heuristic)
		.goalHasSolution(ProductosTransportesVertex.goalHasSolution())
		.edgeWeight(e -> e.weight())
		.build();
		
		AStar<ProductosTransportesVertex, ProductosTransportesEdge,?> alg = AStar.of(g);
		GraphPath<ProductosTransportesVertex, ProductosTransportesEdge> gp = alg.search().get();
		List<Integer> ls = gp.getEdgeList().stream().map(x -> x.action())
					.collect(Collectors.toList());
		SolucionProductosTransportes sh = new SolucionProductosTransportes(ls);
		System.out.println(sh);
		
		GraphColors.toDot(alg.outGraph(), 
				"generated/ej3_f"+i+".dot",
				v -> v.toString(),
				e -> e.action().toString());
	}
	
	private static void testBT(int i) {
		EGraph<ProductosTransportesVertex, ProductosTransportesEdge> g = EGraph.virtual(
			ProductosTransportesVertex.initial(),
			ProductosTransportesVertex.goal(),
			PathType.Sum, Type.Min
		)
		.heuristic(ProductosTransportesHeuristic::heuristic)
		.goalHasSolution(ProductosTransportesVertex.goalHasSolution())
		.edgeWeight(e -> e.weight())
		.build();
		
		BT<ProductosTransportesVertex, ProductosTransportesEdge,?> alg = BT.of(g);
		GraphPath<ProductosTransportesVertex, ProductosTransportesEdge> gp = alg.search().get();
		List<Integer> ls = gp.getEdgeList().stream().map(x -> x.action())
					.collect(Collectors.toList());
		SolucionProductosTransportes sh = new SolucionProductosTransportes(ls);
		System.out.println(sh);
	}
	
	private static void testPDR(int i) {
		EGraph<ProductosTransportesVertex, ProductosTransportesEdge> g = EGraph.virtual(
			ProductosTransportesVertex.initial(),
			ProductosTransportesVertex.goal(),
			PathType.Sum, Type.Min
		)
		.heuristic(ProductosTransportesHeuristic::heuristic)
		.goalHasSolution(ProductosTransportesVertex.goalHasSolution())
		.edgeWeight(e -> e.weight())
		.build();
		
		PDR<ProductosTransportesVertex, ProductosTransportesEdge,?> alg = PDR.of(g);
		GraphPath<ProductosTransportesVertex, ProductosTransportesEdge> gp = alg.search().get();
		List<Integer> ls = gp.getEdgeList().stream().map(x -> x.action())
					.collect(Collectors.toList());
		SolucionProductosTransportes sh = new SolucionProductosTransportes(ls);
		System.out.println(sh);
	}
}
