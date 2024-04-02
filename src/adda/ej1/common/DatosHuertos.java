package adda.ej1.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import us.lsi.common.Files2;
import us.lsi.common.String2;

public class DatosHuertos {
	public static int N;
	public static int M;
	private static List<Verdura> verduras;
	private static List<Huerto> huertos;
	
	public static record Huerto(String nombre, Integer metrosDisponibles) {
		public static Huerto of(String s) {
			s = s.replace(";", "");
			String[] partes = s.split(": metrosdisponibles=");
			return new Huerto(partes[0], Integer.valueOf(partes[1]));
		}
	}
	
	public static record Verdura(String nombre, Integer metrosRequeridos, List<String> incompatibles) {
		public static Verdura of(String s) {
			s = s.replace(" -> metrosrequeridos=", ";").replace(" incomp=", "");
			String[] partes = s.split(";");
			return new Verdura(partes[0], Integer.valueOf(partes[1]), Arrays.asList(partes[2].split(",")));
		}
	}
		
	public static void toConsole() {
		String2.toConsole(huertos, "Huertos de verdura:");
		String2.toConsole(verduras, "Verduras:");
		String2.toConsole(String2.linea());
	}
	
	public static void iniDatos(String fichero) {
		List<String> lineas = Files2.linesFromFile(fichero);
		Integer aux = lineas.indexOf("// VARIEDADES");
		List<String> h = lineas.subList(1, aux);
		List<String> v = lineas.subList(aux + 1, lineas.size());
		
		huertos = new ArrayList<>();
		for(String huerto : h) {
			huertos.add(Huerto.of(huerto));
		}
		
		verduras = new ArrayList<>();
		for(String verdura : v) {
			verduras.add(Verdura.of(verdura));
		}
		
		N = verduras.size();
		M = huertos.size();
//		toConsole();
	}
	
	public static Verdura getVerdura(Integer i) {
		return verduras.get(i);
	}
	
	public static Huerto getHuerto(Integer j) {
		return huertos.get(j);
	}
	
	public static Integer getN() {
		return N;
	}
	
	public static Integer getM() {
		return M;
	}
	
	public static Integer getMetrosRequeridos(Integer i) {
		return verduras.get(i).metrosRequeridos();
	}
	
	public static Integer getMetrosDisponibles(Integer j) {
		return huertos.get(j).metrosDisponibles();
	}
	
	public static Integer incompatible(Integer i, Integer k) {
		return verduras.get(i).incompatibles().contains(verduras.get(k).nombre()) ? 1 : 0;
	}
	
	public static void main(String[] args) {
		iniDatos("ficheros/ej1/Ejercicio1DatosEntrada1.txt");
	}
}
