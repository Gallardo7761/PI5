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
		int m = DatosProductosTransportes.getM();
		int n = DatosProductosTransportes.getN();
		for(int i = 0; i < 2; i++) {
			for(int j = 0; j < 5; j++) {
				int z = i*m+j;
				System.out.println(z%m);
				//System.out.println(z/m);
				//System.out.println(z%m);
			}
		}
	}
}
