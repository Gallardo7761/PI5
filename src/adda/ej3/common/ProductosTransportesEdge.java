package adda.ej3.common;

import us.lsi.graphs.virtual.SimpleEdgeAction;

public record ProductosTransportesEdge(
		ProductosTransportesVertex source,
		ProductosTransportesVertex target,
		Integer action,
		Double weight) 
implements SimpleEdgeAction<ProductosTransportesVertex, Integer> {
	
	public static ProductosTransportesEdge of(ProductosTransportesVertex v1, 
			ProductosTransportesVertex v2, Integer action) {
		int i = v1.z() / DatosProductosTransportes.getM();
		int j = v1.z() % DatosProductosTransportes.getM();
		double w = DatosProductosTransportes.getCoste(i, j) * action;
		return new ProductosTransportesEdge(v1, v2, action, w);
	}
	
}
