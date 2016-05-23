/* -------------------------------------------------------------------
 * Neurona.java
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
 * Clase que define el comportamiento de una neurona.</p>
 *
 * <p>
 * Esta clase representa una neurona..</p>
 */
public class Neurona{

    protected static final double alfa = 0.5;
    
    private Random random;
    private double x1;
    private double x2;
    private double x3;
    private double umbral;
    private int salida;

    /**
     * Metodo constructor de nuestra neurona.
     */
    public Neurona(){
	this.random = new Random();
	double temp = this.random.nextDouble();
	boolean neg = this.random.nextBoolean();
	while(temp > 0.5)
	    temp = this.random.nextDouble();
	if(neg)
	    temp *= -1;
	this.x1 = temp;
	temp = 1;
	neg = this.random.nextBoolean();
	while(temp > 0.5)
	    temp = this.random.nextDouble();
	if(neg)
	    temp *= -1;
	this.x2 = temp;;
	temp = 1;
	neg = this.random.nextBoolean();
	while(temp > 0.5)
	    temp = this.random.nextDouble();
	if(neg)
	    temp *= -1;
	this.x3 = temp;;
	this.umbral = this.random.nextDouble();
	this.salida = 0;
    }

    /**
     * Metodo constructor de nuestra neurona.
     * @param x1 - Es el peso del axon 1.
     * @param x2 - Es el peso del axon 2.
     * @param x2 - Es el peso del axon 3.  
     */
    public Neurona(double x1, double x2, double x3){
	this.random = new Random();
	this.x1 = x1;
	this.x2 = x2;
	this.x3 = x3;
	this.umbral = this.random.nextDouble();
	this.salida = 0;
    }

    /**
     * Metodo para entrenar a la neurona
     * @param input1 - es la entrada 1.
     * @param input2 - es la entrada 2.
     * @param input3 - es la entrada 3.
     * @param salida - es la salida deseada.
     */
    public void entrena(int input1,
			int input2,
			int input3,
			int salida){
	input1 = input1 == 0 ? -1 : input1;
	input2 = input2 == 0 ? -1 : input2;
	input3 = input3 == 0 ? -1 : input3;
	salida = salida == 0 ? -1 : salida;
	double y1 = (this.x1*input1)+(this.x2*input2)+(this.x3*input3)+(this.umbral*-1);
	int miSalida = y1 < 0 ? -1 : 1;
	if(miSalida != salida){
	    this.x1 = this.x1 + (alfa*salida*input1);
	    this.x2 = this.x2 + (alfa*salida*input2);
	    this.x3 = this.x3 + (alfa*salida*input3);
	    this.umbral = this.umbral+(alfa*salida*-1);
	}
	if(calculaPriv(input1,input2,input3) != salida)
	    entrena(input1,input2,input3,salida);
    }

    /**
     * Metodo para calcular una salida de la neurona
     * @param input1 - es la entrada 1.
     * @param input2 - es la entrada 2.
     * @param input3 - es la entrada 3.
     */
    public int calcula(int input1,
			int input2,
			int input3){
	input1 = input1 == 0 ? -1 : input1;
	input2 = input2 == 0 ? -1 : input2;
	input3 = input3 == 0 ? -1 : input3;
	double y1 = (this.x1*input1)+(this.x2*input2)+(this.x3*input3)-this.umbral;
	int miSalida = y1 < 0 ? 0 : 1;
	return miSalida;
    }

    //Exactamente el mismo método de arriba pero regresa -1 en lugar de 0.
    private int calculaPriv(int input1,
			int input2,
			int input3){
	input1 = input1 == 0 ? -1 : input1;
	input2 = input2 == 0 ? -1 : input2;
	input3 = input3 == 0 ? -1 : input3;
	double y1 = (this.x1*input1)+(this.x2*input2)+(this.x3*input3)-this.umbral;
	int miSalida = y1 < 0 ? -1 : 1;
	return miSalida;
    }
    
} //Fin de Neurona.java
