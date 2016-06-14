/* -------------------------------------------------------------------                                      
 * Minimax.java                                                                                             
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

/**                                                                                                         
 * @author Jose Ricardo Rodriguez Abreu                                                                     
 * @version 1.0                                                                                             
 * @since Apr 10 2016.                                                                                      
 * <p>                                                                                                      
 * Clase para jugar gato usando el algoritmo minimax.</p>                                                 
 *                                                                                                          
 * <p>                                                                                                      
 * Crea un juego de gato para poder probar el algoritmo minimax.</p>                              
 */
public class Minimax extends PApplet {

    /** Es el tamaño del tablero. */
    public static final int tamanio = 500;
    /** Es el objeto Gato, que define el juego. */
    public Gato gato;
    /** Son las fichas actuales del tablero. */
    public Ficha[][] tablero;

    /**
     * Son las configuraciones iniciales del juego.
     */
    public void settings(){
	size(tamanio, tamanio+100);    
    }

    /** 
     * Metodo para imprimir en el tablero las intrucciones.
     */
    private void pintaLetras(){
	background(200);
	PFont f = createFont("Arial",13,true); 
	String inst = "1. Haga click en alguna casilla para empezar el juego o presione"
	    +"\nla tecla de \"Flecha Derecha\" para que juegue la IA";
	textFont(f,13);                 
	fill(0);
	text(inst,10,tamanio+10);
	noFill();
	text("2. Flecha derecha para avanzar un turno",10,tamanio+50);
	text("3. Flecha izquierda para retroceder un turno",10,tamanio+70);
	text("4. Barra espaciadora para reiniciar el juego y para salir ESC",10,tamanio+90);
	
    }

    /**
     * Son las Configuraciones del juego.
     */
    @Override
    public void setup(){	
	surface.setResizable(false);
	surface.setSize(tamanio, tamanio+100);
	background(200);
	pintaLetras();
	gato = new Gato();
	tablero = gato.getTablero();
	noFill();
	int area = tamanio/3;
	for(int i = 0; i < 3; i++){
	    for(int j = 0; j < 3; j++){
		rect(i*area,j*area,area,area);
		stroke(0);
		line(i*area,j*area,(i*area),(j*area)+area);
	    }
	}
    }

    /**
     * Como debe actuar el juego en caso de presionar las teclas.
     */
    public void keyPressed() {
	if(key == CODED)
	    if(keyCode == RIGHT){
		int area = tamanio/3;
		gato = gato.gatoDecisionMinimax();
		tablero = gato.getTablero();
		pinta();
	    }else if(keyCode == LEFT){
		int area = tamanio/3;
		if(gato.padre != null){
		    gato = gato.padre;
		    tablero = gato.getTablero();
		    pinta();
		}
	    }else if(keyCode == ESC){
		System.exit(0);
	    }
	if(key == ' '){
	    gato = new Gato();
	    tablero = gato.getTablero();
	    pinta();
	}
    }

    /**
     * Que pasa si se presiona el mouse. (tira una persona)
     */
    public void mousePressed(){       
	int area = tamanio/3;
	int posX = (mouseX)/area;
	int posY = (mouseY)/area;
	if(posX <= 2 && posY <= 2)
	    if(tablero[posY][posX] == Ficha.NADA){
		gato.tira(posY,posX);
		pinta();
	    }
    }

    @Override
    public void draw(){}

    /**
     * Metodo para dibujar una nueva figura en el tablero.
     */
    public void pinta(){	
	pintaLetras();
	int area = tamanio/3;
	for(int i = 0; i < 3; i++){
	    for(int j = 0; j < 3; j++){		
		rect(i*area,j*area,area,area);
		stroke(0);
		if(tablero[j][i] == Ficha.EQUIS){
		    line(i*area,j*area,(i*area)+area,(j*area)+area);
		    line(i*area,(j*area)+area,(i*area)+area,(j*area));
		}else if(tablero[j][i] == Ficha.CIRCULO){
		    ellipse((i*area)+(area/2),(j*area)+(area/2),area-(area/10),area-(area/10));
		    }
	    }
	}	
    }

    /**
     * Enum para las fichas.
     */
    protected enum Ficha{
	EQUIS,CIRCULO,NADA
    }

    /**
     * Clase que usa nuestro juego para representar al gato.
     */
    public class Gato{

	/** Es el tablero del juego. */
	Ficha[][] tablero;
	/** Un booleano para saber quien está jugando. */
	boolean jugador1;
	/** Un gato para saber de que gato padre venimos. */
	Gato padre;
	/** Es para saber cuando ya acabamos un juego. */
	boolean ganado;
	/** Un entero para ver que nos conviene en el juego. */
	Integer utilidadOb;
	/** Nos dice que ficha ganó el juego. */
	Ficha ganador;
	/** Nos da el nivel del juego. */
	int nivel;

	/**
	 * Metodo contructor del gato.
	 * @param anterior - Nos hereda atributos de este tablero.
	 */
	public Gato(Gato anterior){	    
	    this();
	    this.nivel = anterior.nivel;
	    this.ganador = anterior.ganador;
	    this.utilidadOb = anterior.utilidadOb;
	    this.ganado = anterior.ganado;
	    this.padre = null;
	    this.jugador1 = anterior.jugador1;
	    this.setTablero(anterior.getTablero());
	    this.quienSigue();
	}

	/**
	 * Metodo constructor del gato.
	 * @param tablero2 - Es el tablero que estamos especificando.
	 */
	public Gato(Ficha[][] tablero2){
	    this();
	    this.setTablero(tablero2);
	    this.quienSigue();
	}

	/**
	 * Metodo constructor del gato.
	 */
	public Gato(){
	    this.nivel = -1;
	    this.ganador = Ficha.NADA;
	    this.utilidadOb = 0;
	    this.ganado = false;
	    this.padre = null;
	    this.tablero = new Ficha[3][3];
	    for(int i = 0; i < tablero.length; i++){
		for(int j = 0; j < tablero[0].length; j++){
		    this.tablero[i][j] = Ficha.NADA;
		}
	    }
	    this.jugador1 = true;
	    this.quienSigue();
	}

	// Metodo privado para saber quien sigue en el juego.
	private void quienSigue(){
	    int equis = 0,circulo = 0;
	    for(int i = 0; i < this.tablero.length; i++){
		for(int j = 0; j < this.tablero[0].length; j++){
		    if(this.tablero[i][j] == Ficha.EQUIS){
			equis++;
		    }else if(this.tablero[i][j] == Ficha.CIRCULO){
			circulo++;
		    }
		}
	    }
	    if(equis == circulo)
		this.jugador1 = true;
	    else
		this.jugador1 = false;
	}

	/** 
	 * Metodo para asignar un tablero al juego.
	 * @param tableroN - es el nuevo tablero del juego.
	 */
	public void setTablero(Ficha[][] tableroN){	    
	    for(int i = 0; i < this.tablero.length; i++){
		for(int j = 0; j < this.tablero[0].length; j++){
		    this.tira(i,j,tableroN[i][j]);
		}
	    }
	}

	/**
	 * Metodo para saber si un juego ya fue ganado.
	 * @return true si el juego tiene un ganador.
	 */
	public boolean getGanado(){
	    return this.ganado;
	}

	/**
	 * Metodo que nos regresa un tablero.
	 * @return el tablero del gato actual.
	 */
	public Ficha[][] getTablero(){
	    return this.tablero;
	}

	/**
	 * Nos dice si el que sigue es el jugador1.
	 * @return true si el que sigue es el jugador 1.
	 */
	public boolean turnoJugador1(){
	    return this.jugador1;
	}

	/**
	 * Metodo para tirar en el tablero.
	 * @param i - Es la posición i del eje x.
	 * @param j - Es la posición j del eje y.
	 * @param ficha - Es la ficha que estamos tirando.
	 */
	public void tira(int i, int j, Ficha ficha){
	    this.tablero[i][j] = ficha;
	    if(ficha != Ficha.NADA && this.hayGanador(i,j,ficha)){
		this.ganado = true;
		this.ganador = ficha;		
	    }
	}

	/**
	 * Nos dice si un juego fue ganado o terminó en tablas.
	 * @return true si ya finalizó y nadie ganó o alguien ganó
	 */
	public boolean tablas(){
	    if(this.ganado == true)
		return true;
	    boolean temp = true;
	    for(int i = 0; i < this.tablero.length; i++){
		for(int j = 0; j < this.tablero[0].length; j++){
		    if(this.tablero[i][j] == Ficha.NADA)
			temp = false;
		}
	    }
	    return temp;
	}

	/**
	 * Metodo para tirar en el tablero.
	 * @param i - Es la posición i del eje x.
	 * @param j - Es la posición j del eje y.	
	 */	
	public void tira(int i, int j){
	    if(this.jugador1){
		this.tira(i,j,Ficha.EQUIS);
		this.jugador1 = false;
	    }else{
		this.tira(i,j,Ficha.CIRCULO);
		this.jugador1 = true;
	    }
	}

	/**
	 * Metodo que nos regresa el siguiente Gato dado uno anterior para el juego.
	 * @return La mejor estrategia Minimax del juego de gato.
	 */
	public Gato gatoDecisionMinimax(){
	    Gato padreTemp = this.padre;
	    this.padre = null; 
	    Gato g = asignarValor(this,0);
	    if(g.padre == null){
		this.padre = padreTemp;
		return g;
	    }
	    if(g.padre.padre == null){
		this.padre = padreTemp;
		if(this.equals(g.padre))
		    return g;		
		return g.padre;
	    }
	    while(g.padre.padre != null){
		g = g.padre;
	    }
	    this.padre = padreTemp;
	    return g;
	}

	// Aquí es donde se hace toda la magia.
	private Gato asignarValor(Gato g, int nivel){
	    g.nivel = nivel;
	    g.quienSigue();	   
	    Ficha turn = g.turnoJugador1() ? Ficha.EQUIS : Ficha.CIRCULO;
	    if(g.ganado || g.tablas()){
		if(g.ganador == Ficha.EQUIS){ 
		    g.utilidadOb = 1;
		    return g;
		}else if(g.ganador == Ficha.CIRCULO){
		    g.utilidadOb = -1;
		    return g;	        
		}else{
		    g.utilidadOb = 0;
		    return g;
		}
	    }else if(turn == Ficha.EQUIS){
		g.utilidadOb = Integer.MIN_VALUE;
		for(Gato temp : g.generaSucesores()){
		    g = maxUtilidad(g,asignarValor(temp,nivel+1));
		}		
		return g;
	    }else{	    
		g.utilidadOb = Integer.MAX_VALUE;
		for(Gato temp : g.generaSucesores()){
		    g = minUtilidad(g,asignarValor(temp,nivel+1));
		}	       
		return g;
	    }
	}

	//Metodo para dado dos gatos tener la máxima utilidad.
	private Gato maxUtilidad(Gato gato1, Gato gato2){
	    if(gato1.utilidadOb == gato2.utilidadOb){
		if(gato1.nivel > gato2.nivel)
		    return gato2;
		return gato1;
	    }
	    if(gato1.utilidadOb > gato2.utilidadOb)
		return gato1;
	    return gato2;
	}

	//Metodo para dado dos gatos tener la mínima utilidad.
	private Gato minUtilidad(Gato gato1, Gato gato2){
	    if(gato1.utilidadOb == gato2.utilidadOb){
		if(gato1.nivel > gato2.nivel)
		    return gato2;
		return gato1;
	    }
	    if(gato1.utilidadOb < gato2.utilidadOb)
		return gato1;
	    return gato2;
	}

	/**
	 * Nos dice si cuando alguien tira en una posición hay ganador.
	 * @param x - Es la posición x que se tira.
	 * @param y - Es la posición y que se tira.
	 * @param marca - Es la ficha que está tirando.
	 */
	public boolean hayGanador(int x, int y, Ficha marca){
	    // Horizontal
            if (tablero[x][(y + 1) % 3] == marca && tablero[x][(y + 2) % 3] == marca)
		return true;
            // Vertical
            if (tablero[(x + 1) % 3][y] == marca && tablero[(x + 2) % 3][y] == marca)
		return true;  
            // Diagonal
            if((x == 1 && y != 1) || (y == 1 && x!= 1))
		return false;// No pueden hacer diagonal
            // Centro y esquinas
            if(x == 1 && y == 1){
		// Diagonal					       
		if(tablero[0][0] == marca && tablero[2][2] == marca)
		    return true;  
	       if(tablero[2][0] == marca && tablero[0][2] == marca)
		   return true;
	       else
		   return false;
            } else if (x == y){
              // Diagonal \
                if (tablero[(x + 1) % 3][(y + 1) % 3] == marca && tablero[(x + 2) % 3][(y + 2) % 3] == marca)
		    return true;
		else
		    return false;
            } else {
              // Diagonal /
                if (tablero[(x + 2) % 3][(y + 1) % 3] == marca && tablero[(x + 1) % 3][(y + 2) % 3] == marca)
		    return true;
		else
		    return false;
            }
        }

	/**
        * Crea la lista sucesores y agrega a todos los estados que surjen de tiradas válidas.
        * Se consideran tiradas válidas a aquellas en una casilla libre.
        * Además, se optimiza el proceso no agregando estados con jugadas simétricas.
        * Los estados nuevos tendrán una tirada más y el jugador en turno será el jugador contrario.
        */
        public LinkedList<Gato> generaSucesores(){
	    LinkedList<Gato> lista = new LinkedList<Gato> ();
	    for(int i = 0; i < 3; i++){
		for(int j = 0; j < 3; j++){		    
		    Gato nuevo = new Gato(this);
		    nuevo.padre = this;
		    Ficha[][] tableroN = nuevo.getTablero();
		    if(tableroN[i][j] == Ficha.EQUIS || tableroN[i][j] == Ficha.CIRCULO)
			continue;
		    nuevo.tira(i,j);				
		    boolean existe = false;
		    for(int k = 0; k < lista.size(); k++){
			if(lista.get(k).equals(nuevo))
			    existe = true;
		    }
		    if(!existe)
			lista.add(nuevo);
		}
	    }
	    return lista;
	}


        // ------- *** ------- *** -------
        // Serie de funciones que revisan la equivalencia de estados considerando las simetrías de un cuadrado.
        // ------- *** ------- *** -------
        // http://en.wikipedia.org/wiki/Examples_of_groups#The_symmetry_group_of_a_square_-_dihedral_group_of_order_8
        // ba es reflexion sobre / y ba3 reflexion sobre \.

        /** Revisa si ambos gatos son exactamente el mismo. */
        boolean esIgual(Gato otro){
            for(int i = 0; i < 3; i++){
                for(int j = 0; j < 3; j++){
                    if(tablero[i][j] != otro.tablero[i][j]) return false;
                }
            }
            return true;
        }

        /** Al reflejar el gato sobre la diagonal \ son iguales (ie traspuesta) */
        boolean esSimetricoDiagonalInvertida(Gato otro){
	    for(int i = 0; i < 3; i++){
		for(int j = 0; j < 3; j++){
		    if(tablero[i][j] != otro.tablero[j][i]) return false;
		}
	    }
	    return true;
        }

        /** Al reflejar el gato sobre la diagonal / son iguales (ie traspuesta) */
        boolean esSimetricoDiagonal(Gato otro){
            // -------------------------------
            //        IMPLEMENTACION
            // -------------------------------
	    if(tablero[0][0] != otro.tablero[2][2]) return false;	 
	    if(tablero[0][1] != otro.tablero[2][1]) return false;
	    if(tablero[0][2] != otro.tablero[0][2]) return false;
	    if(tablero[1][0] != otro.tablero[2][1]) return false;
	    if(tablero[1][1] != otro.tablero[1][1]) return false;
	    if(tablero[1][2] != otro.tablero[0][1]) return false;
	    if(tablero[2][0] != otro.tablero[2][0]) return false;
	    if(tablero[2][1] != otro.tablero[0][1]) return false;
	    if(tablero[2][2] != otro.tablero[0][0]) return false;
	    return true;
        }
	
        /** Al reflejar el otro gato sobre la vertical son iguales */
        boolean esSimetricoVerticalmente(Gato otro){
            // -------------------------------
            //        IMPLEMENTACION
            // -------------------------------
	    for(int i = 0; i < 3; i++){
		for(int j = 0; j < 3; j++){
		    if(tablero[i][j] != otro.tablero[i][(j+2)%(3-j)]) return false;
		}
	    }
	    return true;
        }

        /** Al reflejar el otro gato sobre la horizontal son iguales */
        boolean esSimetricoHorizontalmente(Gato otro){
            // -------------------------------
            //        IMPLEMENTACION
            // -------------------------------
	    for(int i = 0; i < 3; i++){
		for(int j = 0; j < 3; j++){
		    if(tablero[i][j] != otro.tablero[(i+2)%(3-i)][j]) return false;
		}
	    }
	    return true;
        }

        /** Rota el otro tablero 90° en la dirección de las manecillas del reloj. */
        boolean esSimetrico90(Gato otro){
            // -------------------------------
            //        IMPLEMENTACION
            // -------------------------------
	    Ficha[][] tableroNuevo = new Ficha[3][3];
	    for(int i = 0; i < 3; i++){
		for(int j = 0; j < 3; j++){
		    tableroNuevo[j][2-i] = otro.tablero[i][j];
		}
	    }
	    Gato prueba = new Gato(otro);
	    prueba.tablero = tableroNuevo;
            return this.esIgual(prueba);
        }

        /** Rota el otro tablero 180° en la dirección de las manecillas del reloj. */
        boolean esSimetrico180(Gato otro){
            // -------------------------------
            //        IMPLEMENTACION
            // -------------------------------
	    Ficha[][] tableroNuevo = new Ficha[3][3];
	    for(int i = 0; i < 3; i++){
		for(int j = 0; j < 3; j++){
		    tableroNuevo[2-i][2-j] = otro.tablero[i][j];
		}
	    }
	    Gato prueba = new Gato(otro);
	    prueba.tablero = tableroNuevo;
            return this.esIgual(prueba);

        }

        /** Rota el otro tablero 270° en la dirección de las manecillas del reloj. */
        boolean esSimetrico270(Gato otro){
            // -------------------------------
            //        IMPLEMENTACION
            // -------------------------------
	    Ficha[][] tableroNuevo = new Ficha[3][3];
	    for(int i = 0; i < 3; i++){
		for(int j = 0; j < 3; j++){
		    tableroNuevo[2-j][i] = otro.tablero[i][j];
		}
	    }
	    Gato prueba = new Gato(otro);
	    prueba.tablero = tableroNuevo;
            return this.esIgual(prueba);
        }

        /**
        * Indica si dos estados del juego del gato son iguales, considerando simetrías, 
        * de este modo el problema se vuelve manejable.
        */
        @Override
        public boolean equals(Object o){
            Gato otro = (Gato)o;
            if(esIgual(otro)) return true;
            if(esSimetricoDiagonalInvertida(otro)) return true;
            if(esSimetricoDiagonal(otro)) return true;
            if(esSimetricoVerticalmente(otro)) return true;
            if(esSimetricoHorizontalmente(otro)) return true;
            if(esSimetrico90(otro)) return true;
            if(esSimetrico180(otro)) return true;
            if(esSimetrico270(otro)) return true;
            return false;
        }

        /** Devuelve una representación con caracteres de este estado.
        *  Se puede usar como auxiliar al probar segmentos del código. 
        */
        @Override
        public String toString(){
            char simbolo = jugador1 ? 'x' : 'o';
            String gs = "";
            for(int i = 0; i < 3; i++){
                for(int j = 0; j < 3; j++){
                    gs += tablero[i][j] + " ";
                }
                gs += '\n';
            }
            return gs;
        }
    }	   				     	
    
    public static void main(String[] args){
	/*Ficha[][] t = {{Ficha.EQUIS , Ficha.EQUIS , Ficha.NADA},
		       {Ficha.NADA , Ficha.CIRCULO , Ficha.NADA},
		       {Ficha.NADA , Ficha.NADA , Ficha.CIRCULO}};

	Ficha[][] t1 = {{Ficha.NADA , Ficha.CIRCULO , Ficha.EQUIS},
			{Ficha.EQUIS , Ficha.CIRCULO , Ficha.CIRCULO},
			{Ficha.NADA , Ficha.NADA , Ficha.EQUIS}};

	Ficha[][] t2 = {{Ficha.NADA , Ficha.NADA , Ficha.EQUIS},
			{Ficha.NADA , Ficha.NADA , Ficha.CIRCULO},
			{Ficha.EQUIS , Ficha.CIRCULO , Ficha.EQUIS}};

	Ficha[][] t3 = {{Ficha.NADA , Ficha.CIRCULO , Ficha.EQUIS},
			{Ficha.EQUIS , Ficha.CIRCULO , Ficha.CIRCULO},
			{Ficha.NADA , Ficha.NADA , Ficha.EQUIS}};
	Gato g1 = new Minimax().new Gato(t1);
	System.out.println(g1.toString());
	Gato g = new Minimax().new Gato(g1.decisionMinimax());
	System.out.println(g.toString() +"\n");	*/
	PApplet.main(new String[] { "Minimax" });
    }    
}
