package adda.ej3.common;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import us.lsi.common.Files2;
import us.lsi.common.String2;

public class DatosProductosTransportes {
	public static int N; // productos
	public static int M; // destinos
	public static List<Destino> destinos;
	public static List<Producto> productos;
	
	public static record Destino(Integer id, Integer demanda) {
		public static Destino of(String s) {
			String[] partes = s.replaceAll("D(\\d+): demandaminima=(\\d+);", "$1,$2").split(",");
			return new Destino(Integer.valueOf(partes[0]), Integer.valueOf(partes[1]));
		}
	}
	
	public static record Producto(Integer id, Integer cantidad, List<Integer> coste) {
		public static Producto of(String s) {
			String[] partes = s.replace(" -> uds=", ";").replace(" coste_almacenamiento=", "").split(";");
			String[] costes = partes[2].replaceAll("\\([0-9]+:", "").replace(")", "").split(",");
			return new Producto(Integer.valueOf(partes[0].substring(1)), Integer.valueOf(partes[1]),
					Stream.of(costes).map(Integer::valueOf).collect(Collectors.toList()));
		}
	}
	
	public static void toConsole() {
		String2.toConsole(destinos, "Destinos posibles:");
		String2.toConsole(productos, "Productos del distribuidor:");
		String2.toConsole(String2.linea());
	}
	
	public static void iniDatos(String fichero) {
		List<String> lineas = Files2.linesFromFile(fichero);
		Integer aux = lineas.indexOf("// PRODUCTOS Id_producto -> unidades=integer;coste_almacenamiento=(destino:coste)");
		List<String> d = lineas.subList(1, aux);
		List<String> p = lineas.subList(aux + 1, lineas.size());
		
		destinos = new ArrayList<>();
		for(String destino : d) {
			destinos.add(Destino.of(destino));
		}
		
		productos = new ArrayList<>();
		for(String producto : p) {
			productos.add(Producto.of(producto));
		}
		
		N = productos.size();
		M = destinos.size();
	}
	
	public static Integer getN() {
		return N;
	}
	
	public static Integer getM() {
		return M;
	}
	
	public static Integer getCantidad(Integer i) {
		return productos.get(i).cantidad();
	}
	
	public static Integer getDemanda(Integer j) {
		return destinos.get(j).demanda();
	}
	
	public static Integer getCoste(Integer i, Integer j) {
		return productos.get(i).coste().get(j);
	}
	
	public static Producto getProducto(int i) {
		return productos.get(i);
	}
	
	public static List<Producto> getProductos() {
		return productos;
	}
	
	public static List<Destino> getDestinos() {
		return destinos;
	}
	
	public static void main(String[] args) {
		iniDatos("ficheros/ej3/Ejercicio3DatosEntrada1.txt");
	}
}
