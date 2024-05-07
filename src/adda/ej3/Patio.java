package adda.ej3;

import java.util.List;

import adda.ej3.common.DatosProductosTransportes;
import adda.ej3.common.DatosProductosTransportes.Destino;
import adda.ej3.common.DatosProductosTransportes.Producto;
import us.lsi.common.Pair;

public class Patio {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DatosProductosTransportes.iniDatos("ficheros/ejercicios/Ejercicio3DatosEntrada1.txt");
		System.out.println("(0,"+initialValues().toString().replace("(","").replace(")","")+")");
	}
	
	private static Pair<List<Integer>,List<Integer>> initialValues() {
		List<Integer> ur = DatosProductosTransportes.getProductos().stream()
				.map(Producto::cantidad).toList();
		List<Integer> dr = DatosProductosTransportes.getDestinos().stream()
				.map(Destino::demanda).toList();
		return Pair.of(ur, dr);
	}
}
