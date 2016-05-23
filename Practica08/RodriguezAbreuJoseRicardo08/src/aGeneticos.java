/* -------------------------------------------------------------------
 * aGeneticos.java
 * versión 1.0
 * Copyright (C) 2016  José Ricardo Rodríguez Abreu.
 * Facultad de Ciencias,
 * Universidad Nacional Autónoma de México, Mexico.
 *
 * Este programa es software libre; se puede redistribuir
 * y/o modificar en los términos establecidos por la
 * Licencia Pública General de GNU tal como fue publicada
 * por la Free Software Foundation en la versión 2 o
 * superior.
 *
 * Este programa es distribuido con la esperanza de que
 * resulte de utilidad, pero SIN GARANTÍA ALGUNA; de hecho
 * sin la garantía implícita de COMERCIALIZACIÓN o
 * ADECUACIÓN PARA PROPÓSITOS PARTICULARES. Véase la
 * Licencia Pública General de GNU para mayores detalles.
 *
 * Con este programa se debe haber recibido una copia de la
 * Licencia Pública General de GNU, de no ser así, visite el
 * siguiente URL:
 * http://www.gnu.org/licenses/gpl.html
 * o escriba a la Free Software Foundation Inc.,
 * 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 * -------------------------------------------------------------------
 */

/**
 * @author Jose Ricardo Rodriguez Abreu
 * @version 1.0
 * @since May 22 2016.
 * <p>
 * Clase principal de la práctica 8.</p>
 *
 * <p>
 * Esta clase es el algoritmo genetico principal.</p>
 */

public class aGeneticos{

    /**
     * Metodo principal del programa
     * args - Son los argumentos del programa.
     */
    public static void main(String[] args){
	int tamanioTablero = 8;
	int tamanioMuestra = 50;
	if(args.length == 1){
	    try{
		tamanioMuestra = 50;
		tamanioTablero = Integer.parseInt(args[0]);
	    }catch(NumberFormatException e){
		System.out.println("El parámetro que indicó no es un número");
	    }
	}else{
	    tamanioMuestra = 50;
	    tamanioTablero = 8;
	}	
	if(args.length == 2){
	    try{
		tamanioTablero = Integer.parseInt(args[0]);
		tamanioMuestra = Integer.parseInt(args[1]);
	    }catch(NumberFormatException e){
		System.out.println("El parámetro que indicó no es un número");
	    }
	}else{
	    tamanioMuestra = 50;
	}
	if(tamanioTablero < 2){
	    System.out.println("El tamaño del tablero debe ser mayor a 1");
	    System.exit(0);
	}
	if(tamanioMuestra < 2){
	    System.out.println("El tamaño de la muestra debe ser mayor a 1");
	    System.exit(0);
	}
	Poblacion anterior = new Poblacion(tamanioMuestra,tamanioTablero);
	int i = 0;
	boolean optimo = false;
	while(i < 2000 && !optimo){
	    Poblacion nueva = new Poblacion(0,tamanioTablero);
	    nueva.setElitismo(anterior.getElitismo());
	    while(anterior.size() != nueva.size()){
		Tablero tablero1 = anterior.getRuleta();
		Tablero tablero2 = anterior.getRuleta();
		Tablero hijo = tablero1.recombina(tablero2);
		hijo.muta();
		nueva.addIndividuo(hijo);
	    }	    
	    anterior = nueva;
	    if(anterior.getElitismo().getFitness() == 0)
		optimo = true;
	    i++;
	    if(i % 50 == 0 || optimo){
		System.out.print("Mejor solución en iteración "+i+" es:\n [");
		for(int j = 0; j < anterior.getElitismo().size(); j++){		   
		    System.out.print(anterior.getElitismo().getReina(j)+1+", ");
		}
		System.out.print("] \nFitnes = "+anterior.getElitismo().getFitness()+"\n");
	    }
	}	
    }
}//Fin de aGeneticos.java
