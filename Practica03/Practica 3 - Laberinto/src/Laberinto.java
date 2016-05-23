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

    
    protected class Casilla{
	
	private boolean visitada;
	private boolean actual;
	private boolean arriba;
	private boolean abajo;
	private boolean derecha;
	private boolean izquierda;
	private boolean blanca;
	private int x;
	private int y;
	
	
	public Casilla(boolean visitada,int x, int y){
	    this.visitada = visitada;
	    this.actual = false;
	    this.arriba = this.abajo = this.derecha = this.izquierda = true;
	    this.x = x;
	    this.y = y;
	    this.blanca = false;
	}

	public int getX(){
	    return this.x;
	}

	public int getY(){
	    return this.y;
	}
	
	public boolean getVisitada(){
	    return visitada;
	}

	public void setVisitada(boolean visitada){
	    this.visitada = visitada;
	}

	public boolean getActual(){
	    return this.actual;
	}

	public boolean getArriba(){
	    return this.arriba;
	}

	public boolean getAbajo(){
	    return this.abajo;
	}

	public boolean getIzquierda(){
	    return this.izquierda;
	}

	public boolean getDerecha(){
	    return this.derecha;
	}

	public void setArriba(boolean arriba){
	    this.arriba = arriba;
	}
	public void setAbajo(boolean abajo){
	    this.abajo = abajo;
	}
	public void setIzquierda(boolean izquierda){
	    this.izquierda = izquierda;
	}
	public void setDerecha(boolean derecha){
	    this.derecha = derecha;
	}

	public void setActual(boolean actual){
	    this.actual = actual;
	}

	public boolean getBlanca(){
	    return this.blanca;
	}
	public void setBlanca(boolean blanca){
	    this.blanca = blanca;
	}
   }

    protected class Laberintos{

	private int ancho;
	private int largo;
	private Casilla matriz[][];
	private Random random;
	private Casilla actual;
	private int area;
	
	
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

	public Casilla[][] getCasillas(){
	    return this.matriz;
	}

	public Casilla getActual(){
	    return this.actual;
	}

	public void setActual(Casilla cas){
	    this.actual = cas;
	}

	public boolean tieneVecinoArriba(Casilla cas){
	    try{
		Casilla cas2 = this.matriz[cas.getX()][cas.getY()-1];
		return true;
	    }catch(Exception e){
		return false;
	    }
	}
	public boolean tieneVecinoAbajo(Casilla cas){
	    try{
		Casilla cas2 = this.matriz[cas.getX()][cas.getY()+1];
		return true;
	    }catch(Exception e){
		return false;
	    }
	}
	public boolean tieneVecinoIzquierda(Casilla cas){
	    try{
		Casilla cas2 = this.matriz[cas.getX()-1][cas.getY()];
		return true;
	    }catch(Exception e){
		return false;
	    }
	}
	public boolean tieneVecinoDerecha(Casilla cas){
	    try{
		Casilla cas2 = this.matriz[cas.getX()+1][cas.getY()];
		return true;
	    }catch(Exception e){
		return false;
	    }
	}

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


    }


	
       
    public static final int xFinal = 500;
    public static final int yFinal = 500;
    //public static final int yFinal = xFinal-200;
    public static final int area = 20;
    public Laberintos laberinto;
    public boolean avanza;
    public Stack<Casilla> pila;

 
    
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
    
    public static void main(String[] args) {
        PApplet.main(new String[] { "Laberinto" });
    }

}//Fin de la clase Laberinto.java
