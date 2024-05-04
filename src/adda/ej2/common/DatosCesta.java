package adda.ej2.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import us.lsi.common.Files2;
import us.lsi.common.String2;

public class DatosCesta {
	public static int N; // productos
	public static int M; // categorias
	public static int PRES;
	private static List<Producto> productos;
	private static List<Integer> categorias;
	
	public static record Producto(Integer id, Integer precio, Integer categoria, Integer valoracion) {
		public static Producto of(String s) {
			List<Integer> datos = Stream.of(s.split(":"))
					.map(Integer::valueOf).toList();
			return new Producto(datos.get(0), datos.get(1), datos.get(2), datos.get(3));
		}
		
		public boolean tieneCategoriaEn(Collection<Integer> categorias) {
			boolean res = false;
			for(Integer c : categorias) {
				if(this.categoria == c) {
					res = true;
				}
			}
			return res;
		}
	}
	
	public static void toConsole() {
		String2.toConsole("Presupuesto: %d", PRES);
		String2.toConsole(productos, "Cesta de productos:");
		String2.toConsole(String2.linea());
	}
	
	public static void iniDatos(String fichero) {
		List<String> lineas = Files2.linesFromFile(fichero);
		List<String> productosStrings = lineas.subList(2, lineas.size());
		
		PRES = Integer.valueOf(lineas.get(0).replace("Presupuesto = ", ""));
		
		productos = new ArrayList<>();
		for(String producto : productosStrings) {
			productos.add(Producto.of(producto));
		}
		
		categorias = productos.stream().map(Producto::categoria).distinct().toList();
		
		N = productos.size();
		M = (int) productos.stream()
				.map(Producto::categoria)
				.distinct().count();
	}
	
	public static Integer getN() {
		return N;
	}
	
	public static Integer getM() {
		return M;
	}
	
	public static Integer getPrecio(Integer i) {
		return productos.get(i).precio();
	}
	
	public static Integer getValoracion(Integer i) {
		return productos.get(i).valoracion();
	}
	
	public static Integer getCategoria(Integer i) {
		return productos.get(i).categoria();
	}
	
	public static Integer tieneCategoria(Integer i, Integer j) {
		return productos.get(i).categoria.equals(categorias.get(j)) ? 1 : 0;
	}
		
	public static Integer getPresupuesto() {
		return PRES;
	}
	
	public static Producto getProducto(int i) {
		return productos.get(i);
	}
	
	public static List<Integer> getCategorias() {
		return categorias;
	}
	
	public static List<Producto> getProductos() {
		return productos;
	}
	
	public static void main(String[] args) {
		iniDatos("ficheros/ej2/Ejercicio2DatosEntrada1.txt");
	}
}
