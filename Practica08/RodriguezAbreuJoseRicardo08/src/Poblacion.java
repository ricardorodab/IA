/* -------------------------------------------------------------------
 * Poblacion.java
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
import java.util.Set;
import java.util.HashSet;
import java.util.Random;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * @author Jose Ricardo Rodriguez Abreu
 * @version 1.0
 * @since May 22 2016.
 * <p>
 * Clase principal de la práctica 8.</p>
 *
 * <p>
 * La clase Poblacion  nos ayuda a tener control de varios objetos tableros y hacer operacion sobre ellos.</p>
 */
public class Poblacion{

    private Set<Tablero> individuos;
    private int tamanio;
    private int tamanioTablero;
    private Tablero elitismo;
    private Random ruleta;

    /**
     * Metodo constructor de la población.
     * @param tamanio - Es el tamaño de nuestro conjunto de estudio.
     * @param tamanioTablero - Es el tamaño del tablero.
     */
    public Poblacion(int tamanioMuestra, int tamanioTablero){
	this.ruleta = new Random();
	this.tamanio = tamanioMuestra;
	this.tamanioTablero = tamanioTablero;
	this.individuos = new HashSet<Tablero>();
	for(int i = 0; i < this.tamanio; i++){
	    Tablero temp = new Tablero(this.tamanioTablero);
	    if(this.elitismo == null || this.elitismo.getFitness() >= temp.getFitness())
		elitismo = temp;
	    this.individuos.add(temp);
	}
    }
    
    /**
     * Metodo constructor de la población.
     * @param tamanio - Es el tamaño de nuestro conjunto de estudio.
     */
    public Poblacion(int tamanio){
	this(tamanio,8);
    }

    /**
     * Metodo constructor. Cuando no recibe parámetro el tamaño es de 50.
     */
    public Poblacion(){
	this(50);
    }

    /**
     * Método para eliminar aleatoriamente un objeto del grupo muestral.
     * @return el Tablero que se eliminó.
     */
    public Tablero eliminaAleatorio(){
	int aleatorio = this.ruleta.nextInt(this.individuos.size());
	Iterator it = this.individuos.iterator();
	for(int i = 0; i < aleatorio; i++)
	    it.next();
	Tablero temp =  (Tablero)it.next();
	if(temp.equals(this.elitismo))
	    return eliminaAleatorio();
	this.individuos.remove(temp);
	this.tamanio--;
	return temp;
    }

    /**
     * Nos sirve para tener un Tablero de manera de seleccion ruleta.
     * @return Un tablero del conjunto muestral.
     */
    public Tablero getRuleta(){
	LinkedList<Double> prob = new LinkedList<Double>();
	LinkedList<Tablero> tab = new LinkedList<Tablero>();
	double aleatorio = this.ruleta.nextDouble()*this.individuos.size();
	Iterator it = this.individuos.iterator();
	double total = 0;
	while(it.hasNext()){
	    Tablero temp = (Tablero)it.next();
	    total += temp.getFitness();
	}
	it = this.individuos.iterator();
	int pos = 0;
	while(it.hasNext()){
	    tab.add((Tablero)it.next());
	    prob.add((tab.get(pos).getFitness()));
	    pos++;
	}
	int aleat = this.ruleta.nextInt((int)total);
	int ini = 0;
	int fin = 0;
	Tablero tempFinal = tab.peekFirst();;
	for(int m = 0; m < pos; m++){
	    fin += prob.get(m);
	    if(aleat >= ini && aleat <= fin)
		tempFinal = tab.get(m);
	    ini = fin;
	}
	return tempFinal;
    }
    
    /**
     * Metodo para colocar al conjunto un Tablero que tendrá preferencia sobre los demás. 
     * @param t - es el tablero con prioridad.
     */
    public void setElitismo(Tablero t){
	this.elitismo = t;
    }    

    /**
     * Metodo para obtener al Tablero con preferencia.
     * @return El tablero que tiene mejor función fitness.
     */
    public Tablero getElitismo(){
	return this.elitismo;
    }

    /**
     * Metodo que nos regresa a todos los tableros.
     * @return Los tableros de la población.
     */
    public Set<Tablero> getIndividuos(){
	return this.individuos;
    }

    /**
     * Nos dice el tamaño de la población que estamos estudiando.
     * @return el tamaño de tableros que tiene nuestro conjunto.
     */
    public int size(){
	return this.tamanio;
    }

    /**
     * Metodo para añadir un tablero al conjunto.
     * @param t - es el tablero nuevo a ser añadido.
     */
    public void addIndividuo(Tablero t){
	this.tamanio++;
	if(this.elitismo == null || this.elitismo.getFitness() >= t.getFitness())
	    this.elitismo = t;
	    this.individuos.add(t);
    }
} //Fin de Poblacion.java
