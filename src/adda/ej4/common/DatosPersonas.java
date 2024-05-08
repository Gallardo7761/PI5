package adda.ej4.common;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import us.lsi.common.Files2;
import us.lsi.common.String2;

public class DatosPersonas {
	private static int N;
	private static List<Persona> personas;
	
	public static record Persona(Integer id, Integer edad, Set<String> idiomas, 
			String nacionalidad, List<Integer> afinidades) {
		public static Persona of(String s) {
			String[] partes = s.replaceAll("P(\\d+) -> ", "$1; ")
							   .replaceAll("\\s\\w+=","").split(";");
			return new Persona(
					Integer.valueOf(partes[0]),
					Integer.valueOf(partes[1]),
					Set.of(partes[2].replaceAll("[()\\s]","").split(",")),
					partes[3],
					List.of(partes[4].replaceAll("\\((\\d+):(\\d+)\\)", "$2").split(",")).stream()
							.map(Integer::valueOf)
							.toList()
					);
		}
	}
	
	public static void toConsole() {
		String2.toConsole(personas, "Personas posibles:");
		String2.toConsole(String2.linea());
	}
	
	public static void iniDatos(String fichero) {
		List<String> lineas = Files2.linesFromFile(fichero);
		List<String> personasStrings = lineas.subList(1, lineas.size());
		
		personas = new ArrayList<>();
		for(String persona : personasStrings) {
			personas.add(Persona.of(persona));
		}
		
		N = personas.size();
	}
	
	public static Integer getN() {
		return N;
	}
	
	public static Integer getAfinidad(Integer i, Integer j) {
		j = i > j ? j : j - 1;
		return personas.get(i).afinidades().get(j);
	}
	
	public static List<Integer> getAfinidades(Integer i) {
		return personas.get(i).afinidades();
	}
	
	public static Integer getEdad(Integer i) {
		return personas.get(i).edad();
	}
	
	public static Set<String> getIdiomas(Integer i) {
		return personas.get(i).idiomas();
	}
	
	public static String getNacionalidad(Integer i) {
		return personas.get(i).nacionalidad();
	}
	
	public static Boolean idiomaComun(Integer i, Integer j) {
		Set<String> idiomas = new HashSet<>(personas.get(i).idiomas());
		idiomas.retainAll(personas.get(j).idiomas());
		return idiomas.size() >= 1;
	}
	
	public static Boolean mismaNacionalidad(Integer i, Integer j) {
		return personas.get(i).nacionalidad().equals(personas.get(j).nacionalidad());
	}
	
	public static Boolean maximaDiferenciaEdad(Integer i, Integer j) {
		return Math.abs(personas.get(i).edad() - personas.get(j).edad()) <= 5;
	}

	public static Integer getId(Integer i) {
		return personas.get(i).id();
	}
}
