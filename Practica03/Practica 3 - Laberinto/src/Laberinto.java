/* -------------------------------------------------------------------                                      
 * Laberinto.java                                                                                             
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

import processing.core.PApplet;
import processing.core.PFont;

import java.util.LinkedList;
import java.util.Hashtable;
import java.util.Random;
import java.util.Stack;


/**                                                                                                         
 * @author Jose Ricardo Rodriguez Abreu                                                                     
 * @version 1.0                                                                                             
 * @since Mar 09 2016.                                                                                      
 * <p>                                                                                                      
 * Clase que crea un laberinto usando el algoritmo de backtracking.</p>                                                 
 *                                                                                                          
 * <p>                                                                                                      
 * Crea un objeto de clase tipo Laberinto que sirve para crear un laberinto.</p>                              
 */
public class Laberinto extends PApplet {


    /**
     * Clase interna para obtener el comportamiento de cada una de las casillas que tiene en principio el grid.
     */
    protected class Casilla{

	/** Valor de si la casilla ha sido visitada. */
	private boolean visitada;
	/** Valor de si la casilla es en la que estamos. */
	private boolean actual;
	/** Valor si existe pared superior. */
	private boolean arriba;
	/** Valor si existe pared inferior. */
	private boolean abajo;
	/** Valor si existe pared derecha. */       
	private boolean derecha;
	/** Valor si existe pared izquierda. */
	private boolean izquierda;
	/** Valor si la casilla es o no blanca. */
	private boolean blanca;
	/** Valor del eje x de la casilla. */
	private int x;
	/** Valor del eje y de la casilla. */
	private int y;
	
	/**
	 * Metodo constructor de una  casilla.
	 * @param x - Es la posición x de la casilla.
	 * @param y - Es la posición y de la casilla.
	 */
	public Casilla(boolean visitada,int x, int y){
	    this.visitada = visitada;
	    this.actual = false;
	    this.arriba = this.abajo = this.derecha = this.izquierda = true;
	    this.x = x;
	    this.y = y;
	    this.blanca = false;
	}

	/**
	 * Nos regresa el valor del eje x de nuestra casilla.
	 * @return El valor x del objeto casilla.
	 */
	public int getX(){
	    return this.x;
	}

	/**
	 * Nos regresa el valor del eje y de nuestra casilla.
	 * @return El valor y del objeto casilla.
	 */
	public int getY(){
	    return this.y;
	}

	/**
	 * Nos regresa el valor de si ya fue visitada o no.
	 * @return true si ya ha sido visitada la casilla.
	 */
	public boolean getVisitada(){
	    return visitada;
	}

	/**
	 * Asigna si ya fue visitada la casilla.
	 * @param visitada - El nuevo booleano que tendrá el valor visitada.
	 */
	public void setVisitada(boolean visitada){
	    this.visitada = visitada;
	}

	/**
	 * Metodo para saber si la casilla es la casilla actual en la que estamos parados.
	 * @return true si la casilla es la actual.
	 */
	public boolean getActual(){
	    return this.actual;
	}

	/**
	 * Este metodo proporciona un metodo para saber si tiene pared superior.
	 * @return true si tiene pared superior.
	 */
	public boolean getArriba(){
	    return this.arriba;
	}

	/**
	 * Este metodo proporciona un metodo para saber si tiene pared inferior.
	 * @return true si tiene pared inferior.
	 */
	public boolean getAbajo(){
	    return this.abajo;
	}

	/**
	 * Este metodo proporciona un metodo para saber si tiene pared del lado izquierdo.
	 * @return true si tiene pared izquierda.
	 */
	public boolean getIzquierda(){
	    return this.izquierda;
	}

	/**
	 * Este metodo proporciona un metodo para saber si tiene pared del lado derecho.
	 * @return true si tiene pared derecho.
	 */
	public boolean getDerecha(){
	    return this.derecha;
	}

	/**
	 * El metodo asigna un nuevo valor a la pared superior.
	 * @param arriba - el nuevo valor de la pared.
	 */
	public void setArriba(boolean arriba){
	    this.arriba = arriba;
	}

	/**
	 * El metodo asigna un nuevo valor a la pared inferior.
	 * @param abajo - el nuevo valor de la pared.
	 */
	public void setAbajo(boolean abajo){
	    this.abajo = abajo;
	}

	/**
	 * El metodo asigna un nuevo valor a la pared izquierda.
	 * @param izquierda - el nuevo valor de la pared.
	 */
	public void setIzquierda(boolean izquierda){
	    this.izquierda = izquierda;
	}

	/**
	 * El metodo asigna un nuevo valor a la pared derecha.
	 * @param derecha - el nuevo valor de la pared.
	 */
	public void setDerecha(boolean derecha){
	    this.derecha = derecha;
	}

	/**
	 * El metodo asigna un nuevo valor a si la casilla es la casilla actual.
	 * @param actual - es el valor si la casilla es la casilla actual.
	 */
	public void setActual(boolean actual){
	    this.actual = actual;
	}
	
	/**
	 * Metodo que nos dice si una casilla ya es blanca porque regresamos a ella.
	 * @return true si la casilla es blanca.
	 */
	public boolean getBlanca(){
	    return this.blanca;
	}

	/**
	 * Metodo para asignar a una casilla si es blanca a partir de un punto.
	 * @param blanca - es el valor de si es o no blanca.
	 */
	public void setBlanca(boolean blanca){
	    this.blanca = blanca;
	}
    } //Fin de la clase Casilla.

    /**
     * Clase interna para simular un laberinto con un grid.
     */
    protected class Laberintos{

	/** El tamaño de ancho. */
	private int ancho;
	/** El tamaño de largo. */
	private int largo;
	/** Una matriz de casillas. */
	private Casilla matriz[][];
	/** Un objeto random para generar movimientos aleatorios. */
	private Random random;
	/** La casilla actual. */
	private Casilla actual;
	/** El tamaño de area de cada casilla. */
	private int area;
	
	/**
	 * Metodo constructor de un nuevo laberinto.
	 * @param x - es el tamaño total del eje x.
	 * @param y - es el tamaño total del eje y.
	 * @param area - es el area de cada casilla.
	 */
	public Laberintos(int x, int y, int area){
	    this.largo = x/area;
	    this.ancho = y/area;
	    this.area = area;
	    this.random = new Random();
	    this.matriz = new Casilla[this.largo][this.ancho];
	    for(int i = 0; i < largo; i++){
		for(int j = 0; j < ancho; j++){
		    this.matriz[i][j] = new Casilla(false,i,j);
		}
	    }
	    this.actual = matriz[random.nextInt(this.largo)][random.nextInt(this.ancho)];
	    this.actual.setActual(true);
	}

	/**
	 * Nos regresa el grid de las casillas.
	 * @return - Un arreglo de casillas.
	 */
	public Casilla[][] getCasillas(){
	    return this.matriz;
	}

	/**
	 * Nos regresa la casilla actual.
	 * @return la casilla actual.
	 */
	public Casilla getActual(){
	    return this.actual;
	}

	/** 
	 * Metodo para asignar la casilla actual.
	 * @param cas - es la nueva casilla actual.
	 */
	public void setActual(Casilla cas){
	    this.actual = cas;
	}

	/**
	 * Metodo para saber si una casilla tiene vecino superior.
	 * @param cas - Es la casilla a verificar el vecino.
	 * @return true si es que tiene vecino.
	 */
	public boolean tieneVecinoArriba(Casilla cas){
	    try{
		Casilla cas2 = this.matriz[cas.getX()][cas.getY()-1];
		return true;
	    }catch(Exception e){
		return false;
	    }
	}

	/**
	 * Metodo para saber si una casilla tiene vecino inferior.
	 * @param cas - Es la casilla a verificar el vecino.
	 * @return true si es que tiene vecino.
	 */
	public boolean tieneVecinoAbajo(Casilla cas){
	    try{
		Casilla cas2 = this.matriz[cas.getX()][cas.getY()+1];
		return true;
	    }catch(Exception e){
		return false;
	    }
	}

	/**
	 * Metodo para saber si una casilla tiene vecino izquierdo.
	 * @param cas - Es la casilla a verificar el vecino.
	 * @return true si es que tiene vecino.
	 */
	public boolean tieneVecinoIzquierda(Casilla cas){
	    try{
		Casilla cas2 = this.matriz[cas.getX()-1][cas.getY()];
		return true;
	    }catch(Exception e){
		return false;
	    }
	}

	/**
	 * Metodo para saber si una casilla tiene vecino derecho.
	 * @param cas - Es la casilla a verificar el vecino.
	 * @return true si es que tiene vecino.
	 */
	public boolean tieneVecinoDerecha(Casilla cas){
	    try{
		Casilla cas2 = this.matriz[cas.getX()+1][cas.getY()];
		return true;
	    }catch(Exception e){
		return false;
	    }
	}

	/**
	 * Metodo que nos regresa un vecino random de una casilla.
	 * @param cas - es la casilla a regresar el vecino.
	 * @return una casilla vecina de cas.
	 */
	public Casilla getVecinoRandom(Casilla cas){
	    try{
		int next = this.random.nextInt(4);		
		switch(next){
		case 0:
		    return this.matriz[cas.getX()][cas.getY()+1];
		case 1:
		    return this.matriz[cas.getX()][cas.getY()-1];
		case 2:
		    return this.matriz[cas.getX()+1][cas.getY()];
		case 3:
		    return this.matriz[cas.getX()-1][cas.getY()];
		}
	    }catch(Exception e){
		return getVecinoRandom(cas);
	    }
	    return null;
	}

	// Metodo privado para quitar las paredes entre dos casillas.
	private void quitaPared(Casilla cas1, Casilla cas2){
	    if(tieneVecinoArriba(cas1)){
		if(this.matriz[cas1.getX()][cas1.getY()-1].equals(cas2)){
		    cas1.setArriba(false);
		    cas2.setAbajo(false);
		}
	    }
	    if(tieneVecinoAbajo(cas1)){
		if(this.matriz[cas1.getX()][cas1.getY()+1].equals(cas2)){
		    cas2.setArriba(false);
		    cas1.setAbajo(false);
		}
	    }
	    if(tieneVecinoDerecha(cas1)){
		if(this.matriz[cas1.getX()+1][cas1.getY()].equals(cas2)){
		    cas1.setDerecha(false);
		    cas2.setIzquierda(false);
		}
	    }
	    if(tieneVecinoIzquierda(cas1)){
		if(this.matriz[cas1.getX()-1][cas1.getY()].equals(cas2)){
		    cas2.setDerecha(false);
		    cas1.setIzquierda(false);
		}
	    }
	}
	
	/**
	 * Metodo que dada una casilla nos regresa un vecino no visitado.
	 * @param cas - La casilla a verificar sus vecinos.
	 * @return Una casilla que no ha sido visitada.
	 */
	public Casilla getVecinoNoVisitado(Casilla cas){
	    LinkedList<Casilla> visitados = new LinkedList<Casilla>();
	    int next = this.random.nextInt(4);
	    int next3 = this.random.nextInt(3);
	    boolean nextB = this.random.nextBoolean();
	    if(tieneVecinoArriba(cas))
		if(!this.matriz[cas.getX()][cas.getY()-1].getVisitada())
		    visitados.add(this.matriz[cas.getX()][cas.getY()-1]);
	    if(tieneVecinoAbajo(cas))
		if(!this.matriz[cas.getX()][cas.getY()+1].getVisitada())
		    visitados.add(this.matriz[cas.getX()][cas.getY()+1]);
	    if(tieneVecinoIzquierda(cas))
		if(!this.matriz[cas.getX()-1][cas.getY()].getVisitada())
		    visitados.add(this.matriz[cas.getX()-1][cas.getY()]);
	    if(tieneVecinoDerecha(cas))
		if(!this.matriz[cas.getX()+1][cas.getY()].getVisitada())
		    visitados.add(this.matriz[cas.getX()+1][cas.getY()]);
	    if(visitados.size() == 1){
		return visitados.get(0);
	    }else if(visitados.size() == 2){
		if(nextB)
		    return visitados.get(0);
		return visitados.get(1);
	    }else if(visitados.size() == 3){
		switch(next3){
		case 0:
		    return visitados.get(0);
		case 1:
		    return visitados.get(1);
		case 2:
		    return visitados.get(2);
		}
	    }else if(visitados.size() == 4){
		switch(next){
		case 0:
		    return visitados.get(0);
		case 1:
		    return visitados.get(1);
		case 2:
		    return visitados.get(2);
		case 3:
		    return visitados.get(3);
		}
	    }
	    return null;
	}

	/**
	 * Metodo que mueve a una nueva casilla.
	 * @return true si se pudo realizar la acción.
	 */
	public boolean moveNuevo(){
	    Casilla act = laberinto.getActual();
	    act.setActual(false);
	    act.setVisitada(true);
	    Casilla temp = laberinto.getVecinoNoVisitado(act);
	    quitaPared(temp,act);
	    act = temp;
	    if(act == null)
		return false;
	    act.setActual(true);
	    laberinto.setActual(act);
	    return true;
	}


    } //Fin de la clase Laberintos.
	
    /** El valor de x del tablero. */
    public static final int xFinal = 500;
    /** El valor de y del tablero. */
    public static final int yFinal = 500;
    //public static final int yFinal = xFinal-200;
    /** El area de cada casilla en pixeles. */
    public static final int area = 20;
    /** El laberinto en el que nos moveremos. */
    public Laberintos laberinto;
    /** Un valor para ver si seguimos iterando. */
    public boolean avanza;
    /** Una pila con las casillas que hemos visitados. */
    public Stack<Casilla> pila;

 
    /**
     * Metodo para asignar el valor al programa.
     */
    public void settings() {
  	size(xFinal, yFinal);  
    }

    /** Configuracion inicial */
    @Override
    public void setup(){
	surface.setResizable(true);
	surface.setSize(xFinal,yFinal); 
	background(200); //Fondo Gris
	laberinto = new Laberintos(xFinal,yFinal,area);
	pila = new Stack<Casilla>();
	avanza = true;
	noFill();
	Casilla[][] casill = laberinto.getCasillas();
	for(int i = 0; i < casill.length; i++){
	    for(int j = 0; j < casill[1].length; j++){
		noStroke();
		rect(i*area,j*area,area,area);
		    stroke(0);
		    if(casill[i][j].getArriba()) 
			line(i*area,j*area,(i*area)+(area),j*area);
		    if(casill[i][j].getIzquierda()) 
			line(i*area,j*area,(i*area),(j*area)+area);
		    if(casill[i][j].getDerecha()) 
			line((i*area)+(area),(j*area),(i*area)+(area),(j*area)+(area));
		    if(casill[i][j].getIzquierda()) 
			line(i*area,(j*area)+area,(i*area)+(area),(j*(area))+area);		    
	    }
	}       
    }

    /** Dibuja la imagen en cada ciclo */
    @Override
    public void draw(){
	Casilla[][] casill = laberinto.getCasillas();
	for(int i = 0; i < casill.length; i++){
	    for(int j = 0; j < casill[1].length; j++){
		noStroke();
		if(casill[i][j].getActual()){
		    fill(240,0,0);
		}else if(casill[i][j].getBlanca()){
		    fill(255);
		} else if(casill[i][j].getVisitada()){	
		    fill(0,0,240);
		}else{
		    fill(255);			
		}
		rect(i*area,j*area,area,area);		
		stroke(0);		    
		if(casill[i][j].getArriba()) 
		    line(i*area,j*area,(i*area)+(area),j*area);
		if(casill[i][j].getIzquierda()) 
		    line(i*area,j*area,(i*area),(j*area)+area);
		if(casill[i][j].getDerecha()) 
		    line((i*area)+(area),(j*area),(i*area)+(area),(j*area)+(area));
		if(casill[i][j].getIzquierda()) 
		    line(i*area,(j*area)+area,(i*area)+(area),(j*(area))+area);		    
	    }
	}
	if(laberinto.moveNuevo()){
	    pila.push(laberinto.getActual());
	}else{
	    if(!pila.empty()){		
		laberinto.setActual(pila.pop());
		Casilla act = laberinto.getActual();
		act.setBlanca(true);;
		fill(255);
		rect(act.getX()*area,act.getY()*area,area,area);		
	    }
	}
    }

    /**
     * Metodo principal del programa.
     * @args - Los argumentos que recibe.
     */
    public static void main(String[] args) {
        PApplet.main(new String[] { "Laberinto" });
    }

}//Fin de la clase Laberinto.java
