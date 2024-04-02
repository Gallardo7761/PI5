package adda.ej1.common;

import java.util.List;
import java.util.function.Predicate;

import us.lsi.graphs.virtual.VirtualVertex;

public record HuertosVertex(Integer index)
implements VirtualVertex<HuertosVertex, HuertosEdge, Integer> {
	
	public static HuertosVertex initial() {
		//return of(0, Set2.range(0, DatosHuertos.getN()));
		return of(0);
	}
	
	public static HuertosVertex of(Integer index) {
		return new HuertosVertex(index);
	}
	
	public static Predicate<HuertosVertex> goal() {
		return v -> v.index() == DatosHuertos.getM();
	}

	@Override
	public List<Integer> actions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HuertosVertex neighbor(Integer a) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HuertosEdge edge(Integer a) {
		// TODO Auto-generated method stub
		return null;
	}

}
