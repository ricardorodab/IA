/* -------------------------------------------------------------------
 * rNeuronal.java
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

import java.util.Scanner;
import java.io.IOException;

/**
 * @author Jose Ricardo Rodriguez Abreu
 * @version 1.0
 * @since May 22 2016.
 * <p>
 * Clase principal de la practica 09.</p>
 *
 * <p>
 * Esta clase es donde nuestra neurona aprende y corre el programa..</p>
 */
public class rNeuronal{

    /**
     * Metodo que entrena a neuronas por igual AND/OR
     * @param n - Es la neurona a entrenar.
     */
    public static void entrenamientoNeutral(Neurona n){
	n.entrena(0,0,0,0);
	n.entrena(1,1,1,1);
    }

    /**
     * Metodo que entrena a neuronas AND.
     * @param n - Es la neurona a entrenar.
     */
    public static void entrenamiento1And(Neurona n){
	n.entrena(0,0,0,0);
	n.entrena(0,0,1,0);
	n.entrena(0,1,0,0);
	n.entrena(0,1,1,0);
	n.entrena(1,0,0,0);
	n.entrena(1,0,1,0);
	n.entrena(1,1,0,0);
	n.entrena(1,1,1,1);       
    }

    /**
     * Metodo que entrena a neuronas AND (2).
     * @param n - Es la neurona a entrenar.
     */
    public static void entrenamiento2And(Neurona n){
	for(int i = 0; i < 10; i++){
	    n.entrena(0,0,0,0);
	    n.entrena(0,0,1,0);
	    n.entrena(0,1,0,0);
	    n.entrena(0,1,1,0);
	    n.entrena(1,0,0,0);
	    n.entrena(1,0,1,0);
	    n.entrena(1,1,0,0);
	    n.entrena(1,1,1,1);
	}	
    }

    /**
     * Metodo que entrena a neuronas OR.
     * @param n - Es la neurona a entrenar.
     */
    public static void entrenamiento1Or(Neurona n){
	n.entrena(0,0,0,0);
	n.entrena(0,0,1,1);
	n.entrena(0,1,0,1);
	n.entrena(0,1,1,1);
	n.entrena(1,0,0,1);
	n.entrena(1,0,1,1);
	n.entrena(1,1,0,1);
	n.entrena(1,1,1,1);
    }

    /**
     * Metodo que entrena a neuronas OR (2).
     * @param n - Es la neurona a entrenar.
     */
    public static void entrenamiento2Or(Neurona n){
       for(int i = 0; i < 10; i++){
	    n.entrena(0,0,0,0);
	    n.entrena(0,0,1,1);
	    n.entrena(0,1,0,1);
	    n.entrena(0,1,1,1);
	    n.entrena(1,0,0,1);
	    n.entrena(1,0,1,1);
	    n.entrena(1,1,0,1);
	    n.entrena(1,1,1,1);
	}
    }

    /**
     * Metodo simple que imprime texto en pantalla.
     */
    public static void queNeurona(){
	System.out.println("Ingrese que neurona desea entrenar:"
			   +"\n AND \n OR \n Para salir en cualquier momento presione enter\n");
    }

    /**
     * Metodo simple que imprime texto en pantalla.
     */
    public static void entrenamiento(){
	System.out.println("Ingrese el entrenamiento del 1-3 que desee implementar para su neurona\n");	
    }

    /**
     * Metodo simple que imprime texto en pantalla.
     */
    public static void pruebaCasosAnd(Neurona n){
	System.out.println("X && Y && Z RESULT");
	System.out.println("0 && 0 && 0   "+n.calcula(0,0,0));
	System.out.println("0 && 0 && 1   "+n.calcula(0,0,1));
	System.out.println("0 && 1 && 0   "+n.calcula(0,1,0));
	System.out.println("0 && 1 && 1   "+n.calcula(0,1,1));
	System.out.println("1 && 0 && 0   "+n.calcula(1,0,0));
	System.out.println("1 && 0 && 1   "+n.calcula(1,0,1));
	System.out.println("1 && 1 && 0   "+n.calcula(1,1,0));
	System.out.println("1 && 1 && 1   "+n.calcula(1,1,1) +"\n");	
    }

    /**
     * Metodo simple que imprime texto en pantalla.
     */
    public static void pruebaCasosOr(Neurona n){
	System.out.println("X || Y || Z RESULT");
	System.out.println("0 || 0 || 0   "+n.calcula(0,0,0));
	System.out.println("0 || 0 || 1   "+n.calcula(0,0,1));
	System.out.println("0 || 1 || 0   "+n.calcula(0,1,0));
	System.out.println("0 || 1 || 1   "+n.calcula(0,1,1));
	System.out.println("1 || 0 || 0   "+n.calcula(1,0,0));
	System.out.println("1 || 0 || 1   "+n.calcula(1,0,1));
	System.out.println("1 || 1 || 0   "+n.calcula(1,1,0));
	System.out.println("1 || 1 || 1   "+n.calcula(1,1,1) +"\n");	
    }
    
    /**
     * Metodo principal del programa.
     * @param args - son los argumentos del programa.
     */
    public static void main(String[] args){
	Neurona and = new Neurona();
	Neurona or = new Neurona();
	int entre = 0;
	String temp = "";
	while(true){
	    Scanner sc = new Scanner(System.in);
	    queNeurona();
	    temp = sc.nextLine();
	    if(temp.equals(""))
		System.exit(0);;
	    if(temp.equalsIgnoreCase("AND") || temp.equalsIgnoreCase("A")){
		while(true){
		    entrenamiento();
		    temp = sc.nextLine();
		    if(temp.equals(""))
			System.exit(0);
		    if(!EsNumero.esNumero(temp) || Integer.parseInt(temp) <= 0 || Integer.parseInt(temp) > 3){
			System.out.println("Favor de dar un número válido, mayor a cero y menor que 4.\n");
			continue;
		    }
		    entre = Integer.parseInt(temp);
		    System.out.println("Antes de entrenar a la neurona AND:\n");
		    pruebaCasosAnd(and);
		    switch(entre){
		    case 1:
			entrenamientoNeutral(and);
			System.out.println("Después de entrenar a la neurona AND:\n");
			pruebaCasosAnd(and);
			System.exit(0);
			break;
		    case 2:
			entrenamiento1And(and);
			System.out.println("Después de entrenar a la neurona AND:\n");
			pruebaCasosAnd(and);
			System.exit(0);
			break;
		    case 3:
			entrenamiento2And(and);
			System.out.println("Después de entrenar a la neurona AND:\n");
			pruebaCasosAnd(and);
			System.exit(0);
			break;
		    }
		}
	    }
	    if(temp.equalsIgnoreCase("OR") || temp.equalsIgnoreCase("O")){
		while(true){
		    entrenamiento();
		    temp = sc.nextLine();
		    if(temp.equals(""))
			System.exit(0);
		    if(!EsNumero.esNumero(temp) || Integer.parseInt(temp) <= 0 || Integer.parseInt(temp) > 3){
			System.out.println("Favor de dar un número válido, mayor a cero y menor que 4.\n");
			continue;
		    }
		    entre = Integer.parseInt(temp);
		    System.out.println("Antes de entrenar a la neurona OR:\n");
		    pruebaCasosOr(and);
		    switch(entre){
		    case 1:
			entrenamientoNeutral(or);
			System.out.println("Después de entrenar a la neurona OR:\n");
			pruebaCasosOr(or);
			System.exit(0);
			break;
		    case 2:
			entrenamiento1Or(or);
			System.out.println("Después de entrenar a la neurona OR:\n");
			pruebaCasosOr(or);
			System.exit(0);
			break;
		    case 3:
			entrenamiento2Or(or);
			System.out.println("Después de entrenar a la neurona OR:\n");
			pruebaCasosOr(or);
			System.exit(0);
			break;
		    }
		}
	    }
	    continue;
	}
    }    
} //Fin de rNeuronal.java
