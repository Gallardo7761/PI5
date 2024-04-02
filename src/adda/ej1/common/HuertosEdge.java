package adda.ej1.common;

import us.lsi.graphs.virtual.SimpleEdgeAction;

public record HuertosEdge(
		HuertosVertex source,
		HuertosVertex target,
		Integer action,
		Double weight) 
implements SimpleEdgeAction<HuertosVertex, Integer> {
	public static HuertosEdge of(HuertosVertex v1, HuertosVertex v2, Integer a) {
		return new HuertosEdge(v1, v2, a, 0.);
	}
}
