package adda.ej2.common;

import us.lsi.graphs.virtual.SimpleEdgeAction;

public record CestaEdge(
		CestaVertex source,
		CestaVertex target,
		Integer action,
		Double weight) 
implements SimpleEdgeAction<CestaVertex, Integer> {
    
	public static CestaEdge of(CestaVertex v1, CestaVertex v2, Integer action) {
		return new CestaEdge(v1, v2, action, action == -1 ? 0. : 1.);
	}
}
