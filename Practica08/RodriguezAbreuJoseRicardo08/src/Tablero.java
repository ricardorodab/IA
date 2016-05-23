/* -------------------------------------------------------------------
 * Tablero.java
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

import java.util.Random;


/**
 * @author Jose Ricardo Rodriguez Abreu
 * @version 1.0
 * @since May 22 2016.
 * <p>
 * Clase principal de la práctica 8.</p>
 *
 * <p>
 * La clase tablero nos ayuda a tener un objeto tablero y hacer operacion sobre cada uno de ellos.</p>
 */
public class Tablero{

    protected static final double proba = 0.2;
    private int corte;
    private int[] tablero;
    private double fitness;
    private Random random;
    private int tamanio;

    /**
     * Constructor del tablero.
     * tamanio - Es el tamaño cuadrado del tablero.
     */
    public Tablero(int tamanio){
	this.tamanio = tamanio;
	this.random = new Random();
	this.tablero = new int[this.tamanio];
	if(tamanio <= 1)
	    this.corte = 0;
	else
	    this.corte = random.nextInt(this.tamanio-1)+1;
	for(int i = 0; i < this.tamanio; i++){
	    this.tablero[i] = random.nextInt(this.tamanio);
	}
	this.fitness = sacaFitness();
    }

    /**
     * Constructor del tablero. Cuando no recibe parámetros es de tamaño 8.
     */
    public Tablero(){
	this(8);
    }

    /**
     * Nos regresa el tamaño cuadrado del tablero 
     * @return el tamaño del objeto tablero.
     */
    public int size(){
	return this.tamanio;
    }

    /**
     * Nos genera el valor de la función Fitness.
     * @return el valor del tablero con la función actual.
     */
    public double sacaFitness(){
	int coincidencias = 0;
	int diag = 1;
	for(int i = 0; i < this.tamanio; i++){	    
	    int temp = this.tablero[i];
	    for(int j = i+1; j < this.tamanio; j++){
		if(this.tablero[j] == temp)
		    coincidencias++;	    	   
		if(temp == this.tablero[j]+diag)
		    coincidencias++;
		if(temp == this.tablero[j]-diag)
		    coincidencias++;
		diag++;
	    }
	    diag = 1;
	}
	return coincidencias;
	/*if(this.tamanio == 0)
	    return 0;
	if(this.tamanio == 1)
	    return 1;
	double acum = this.tamanio*this.tamanio;
	acum = ((double)coincidencias)/acum;
	return 1-acum;*/
    }

    /**
     * Nos regresa el valor fitness del objeto en cuestión.
     * @return el valor fitness de este tablero.
     */
    public double getFitness(){
	return this.fitness;
    }

    /**
     * Metodo para asignar un nuevo tablero a un nuevo objeto.
     * @param tablero - Es el tablero que se le asigna a este objeto.
     */
    public void setTablero(int[] tablero){	
	this.tamanio = tablero.length;
	this.corte = random.nextInt(this.tamanio-1)+1;
	this.tablero = tablero;
	this.fitness = sacaFitness();
    }

    /**
     * Metodo para obtener el valor de la posición de la reina en una columna definida.
     * @param posReina - Es la columna. 
     * @raturn El valor de la fila de la reina.
     */
    public int getReina(int posReina){
	return this.tablero[posReina];
    }

    /**
     * Metodo para recombinar un tablero con otro.
     * @param t - es el otro tablero.
     * @return el nuevo tablero con valores de ambos.
     */
    public Tablero recombina(Tablero t){
	Tablero temp = new Tablero(0);
	int[] tableroNuevo = new int[this.tamanio]; 
	if(t.tamanio != this.tamanio)
	    throw new IndexOutOfBoundsException("Los tableros tienen tamaño diferentes");
	for(int i = 0; i < this.tamanio; i++){
	    if(i <= this.corte){
		tableroNuevo[i] = this.tablero[i];
	    }else{
		tableroNuevo[i] = t.getReina(i);
	    }
	}
	temp.setTablero(tableroNuevo);
	return temp;
    }

    /**
     * Metodo para mutar cada uno de los valores del tablero.
     */
    public void muta(){
	for(int i = 0; i < this.tamanio; i++){
	    if(this.random.nextDouble() <= proba){
		this.tablero[i] = this.random.nextInt(this.tamanio-1);
	    }
	}
    }
}//Fin de Tablero.java
