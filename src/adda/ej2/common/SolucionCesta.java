package adda.ej2.common;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SolucionCesta {
	private static Integer precioTotal;
	private static List<Integer> solucion;
	
	public static SolucionCesta of_list(List<Integer> ls) {
		return new SolucionCesta(ls);
	}
	
	public SolucionCesta() {
		precioTotal = 0;
		solucion = new ArrayList<>();
	}
	
	public SolucionCesta(List<Integer> ls) {
		solucion =  new ArrayList<>();
		precioTotal = 0;
		for(int i = 0; i < ls.size(); i++) {
			if(ls.get(i) > 0) {
				precioTotal += DatosCesta.getProducto(i).precio();
				solucion.add(DatosCesta.getProducto(i).id());
			}
		}
	}
	
	public String toString() {
		String productos = solucion.stream()
				.map(x -> "Producto " + x)
				.collect(Collectors.joining(", ", "Productos elegidos: {", "}\n"));
		return String.format("%sPrecio total de la cesta: %d", productos, precioTotal);
	}
}
