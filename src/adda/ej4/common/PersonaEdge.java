package adda.ej4.common;

import us.lsi.graphs.virtual.SimpleEdgeAction;

public record PersonaEdge(
		PersonaVertex source, 
		PersonaVertex target, 
		Integer action,
		Double weight
) implements SimpleEdgeAction<PersonaVertex, Integer>{
	
	public static PersonaEdge of(PersonaVertex source, PersonaVertex taget, Integer action) {
		Double weight = 0.;
		for (int i = 0; i < DatosPersonas.getN(); i++) {
			weight = (double) DatosPersonas.getAfinidades(i).indexOf(action);
		}
		return new PersonaEdge(source, taget, action, weight);
	}
}
