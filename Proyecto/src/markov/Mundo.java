/* -------------------------------------------------------------------
* Mundo.java
* versión 1.0
* Copyright (C) 2016  Jose Ricardo Rodriguez Abreu.
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
package markov;

import java.util.Random;

/**
 * @author Ricardo Abreu
 * @version 1.0
 * @since Jun 13 2016.
 * <p> En esta clase modelamos el mundo.</p>
 *
 * <p> Clase que implementa el mundo con sus casillas.</p>
 */
public class Mundo {
    
    /**
     * Clase protegida donde definimos las casillas.
     */
    protected class Casilla {
        
        /** Es el tipo de objeto que tiene la casilla dentro. */
        Obstaculo objeto;
        /** Es la posición x de la casilla. */
        int x;
        /** Es la posición y de la casilla. */
        int y;
        /** Es el tamaño que tiene la casilla en pixeles. */
        int tamanio;
        /** Es la creencia o belief promedio de la casilla. */
        float creencia;
        /** Es la distancia de la casilla. */
        int distancia;
        /** Son las distancias que tiene la casilla de un objeto. */
        float[] distancias;
        /** Es la creencia de las casillas. */
        float[] bel;
        /** Es el belief final de la casilla. */
        float belief;
        
        /**
         * Constructor de la casilla.
         * @param objeto - Es el objeto que tiene la casilla.
         * @param x - Es la posición x de la casilla.
         * @param y - Es la posición y de la casilla.
         * @param tamanio - Es el tamaño de la casilla en pixeles.
         * @param creencia  - Es la creencia o belief de la casilla.
         */
        public Casilla(Obstaculo objeto, int x, int y, int tamanio, float creencia) {
            this.objeto = objeto;
            this.x = x;
            this.y = y;
            this.tamanio = tamanio;
            this.creencia = creencia;
            this.distancia = 0;
            this.belief = 0;
            this.bel = new float[8];
            this.distancias = new float[8];
        }
        
        /**
         * Nos regresa el objeto de la casilla actual.
         * @return - Un objeto enum Obstaculo.
         */
        public Obstaculo getObjeto() {
            return objeto;
        }
        
        /**
         * Nos regresa la distancia de la casilla actual.
         * @return La distancia de mi casilla.
         */
        public int getDistancia(){
            return this.distancia;
        }
        
        /**
         * Nos regresa la creencia promedio.
         * @return - La creencia de que estamos en esta casilla.
         */
        public double getCreencia() {
            return creencia;
        }
        
        /**
         * Metodo para asignar un obstaculo de tipo enum Obstaculo.
         * @param objeto - El objeto de tipo enum.
         */
        public void setObjeto(Obstaculo objeto) {
            this.objeto = objeto;
        }
        
        /**
         * Metodo para asignar una nueva distancia en la casilla.
         * @param distancia - La nueva distancia.
         */
        public void setDistancia(int distancia){
            this.distancia = distancia;
        }
        
        /**
         * Metodo para asignarle una nueva creencia a la casilla.
         * @param creencia - La nueva creencia.
         */
        public void setCreencia(float creencia) {
            this.creencia = creencia;
        }
        
    } //Fin de Casilla
    
    /** Es el conjunto de casillas de nuestro mundo. */
    private Casilla[][] casillas;
    /** Es el tamaño del mundo. */
    private int tamanio;
    /** Es el robot de nuestro mundo. */
    private Robot robot;
    /** Un generador de números aleatorios del mundo. */
    private Random random;
    /** La cantidad de obstaculos que pondremos en nuestro mundo. */
    private int obstaculos;
    /** Una medida de varianza para el algoritmo. */
    public float varianza = 0.7f;
    /** Un contador para nuestra clase. */
    int contadorClase = 1;
    
    /**
     * Metodo constructor del mundo.
     * @param tamanio - Es el tamaño del mundo.
     * @param tamanioCasillas - Es el tamaño por casilla del mundo. 
     * @param robot - Es el robot del mundo. 
     * @param obstaculos - Es la cantidad de obtáculos que tendrá el mundo.
     */
    public Mundo(int tamanio, int tamanioCasillas, Robot robot, int obstaculos) {
        this.random = new Random();
        this.tamanio = tamanio;
        this.robot = robot;
        this.obstaculos = obstaculos;
        this.casillas = inicializaCasillas(tamanioCasillas);
        creaObstaculos(this.casillas);
        calculaDistanciaCeldas();
        iniciaBeliefs();
    }
    
    /**
     * Metodo para obtener las casillas.
     * @return Las casillas del mundo.
     */
    public Casilla[][] getCasillas() {
        return casillas;
    }
    
    /**
     * Metodo para obtener el robot del mundo.
     * @return - El robot que tiene a nuestro mundo.
     */
    public Robot getRobot() {
        return robot;
    }
    
    /**
     * Metodo para poner el robot aleatroriamente en el mundo.
     */
    public void ponerRobot() {
        int i = 0, j = 0;
        while(casillas[i][j].getObjeto() != Obstaculo.NADA){
            i = random.nextInt(casillas.length);
            j = random.nextInt(casillas[0].length);
        }
        this.robot.x = i;
        this.robot.y = j;
        this.casillas[i][j].setObjeto(Obstaculo.ROBOT);
    }
    
    /**
     * Metodo para mover aleatoriamente el robot en el mundo sin tocar obstaculos.
     */
    public void mueveRobot() {
        int m = 0;
        while(m != -1){
            m = this.random.nextInt(8);
            switch(m){
                case 0:
                    if(this.casillas[this.robot.x-1][this.robot.y].getObjeto() == Obstaculo.NADA){
                        this.casillas[this.robot.x][this.robot.y].setObjeto(Obstaculo.NADA);
                        this.robot.x = this.robot.x-1;
                        this.casillas[this.robot.x][this.robot.y].setObjeto(Obstaculo.ROBOT);
                        m = -1;
                        calculaAlphaT();
                    }
                    break;
                case 1:
                    if(this.casillas[this.robot.x-1][this.robot.y-1].getObjeto() == Obstaculo.NADA){
                        this.casillas[this.robot.x][this.robot.y].setObjeto(Obstaculo.NADA);
                        this.robot.x = this.robot.x-1;
                        this.robot.y = this.robot.y-1;
                        this.casillas[this.robot.x][this.robot.y].setObjeto(Obstaculo.ROBOT);
                        m = -1;
                        calculaAlphaT();
                    }
                    break;
                case 2:
                    if(this.casillas[this.robot.x][this.robot.y-1].getObjeto() == Obstaculo.NADA){
                        this.casillas[this.robot.x][this.robot.y].setObjeto(Obstaculo.NADA);
                        this.robot.y = this.robot.y-1;
                        this.casillas[this.robot.x][this.robot.y].setObjeto(Obstaculo.ROBOT);
                        m = -1;
                        calculaAlphaT();
                    }
                    break;
                case 3:
                    if(this.casillas[this.robot.x+1][this.robot.y].getObjeto() == Obstaculo.NADA){
                        this.casillas[this.robot.x][this.robot.y].setObjeto(Obstaculo.NADA);
                        this.robot.x = this.robot.x+1;
                        this.casillas[this.robot.x][this.robot.y].setObjeto(Obstaculo.ROBOT);
                        m = -1;
                        calculaAlphaT();
                    }
                    break;
                case 4:
                    if(this.casillas[this.robot.x+1][this.robot.y+1].getObjeto() == Obstaculo.NADA){
                        this.casillas[this.robot.x][this.robot.y].setObjeto(Obstaculo.NADA);
                        this.robot.x = this.robot.x+1;
                        this.robot.y = this.robot.y+1;
                        this.casillas[this.robot.x][this.robot.y].setObjeto(Obstaculo.ROBOT);
                        m = -1;
                        calculaAlphaT();
                    }
                    break;
                case 5:
                    if(this.casillas[this.robot.x][this.robot.y+1].getObjeto() == Obstaculo.NADA){
                        this.casillas[this.robot.x][this.robot.y].setObjeto(Obstaculo.NADA);
                        this.robot.y = this.robot.y+1;
                        this.casillas[this.robot.x][this.robot.y].setObjeto(Obstaculo.ROBOT);
                        m = -1;
                        calculaAlphaT();
                    }
                    break;
                case 6:
                    if(this.casillas[this.robot.x-1][this.robot.y+1].getObjeto() == Obstaculo.NADA){
                        this.casillas[this.robot.x][this.robot.y].setObjeto(Obstaculo.NADA);
                        this.robot.x = this.robot.x-1;
                        this.robot.y = this.robot.y+1;
                        this.casillas[this.robot.x][this.robot.y].setObjeto(Obstaculo.ROBOT);
                        m = -1;
                        calculaAlphaT();
                    }
                    break;
                case 7:
                    if(this.casillas[this.robot.x+1][this.robot.y-1].getObjeto() == Obstaculo.NADA){
                        this.casillas[this.robot.x][this.robot.y].setObjeto(Obstaculo.NADA);
                        this.robot.x = this.robot.x+1;
                        this.robot.y = this.robot.y-1;
                        this.casillas[this.robot.x][this.robot.y].setObjeto(Obstaculo.ROBOT);
                        m = -1;
                        calculaAlphaT();
                    }
                    break;
            }
        }
    }
    
    // Inicializamos los belifs de cada una de las casillas considerando sus casos.
    private void iniciaBeliefs() {
        for (int i = 0; i < this.casillas.length; i++) {
            for (int j = 0; j < this.casillas[i].length; j++) {
                if (casillas[i][j].objeto == Obstaculo.NADA) {
                    casillas[i][j].belief = 1.0f / (this.casillas.length*this.casillas[0].length-this.obstaculos);
                    /* Buscamos el obstáculo más cercano a la derecha. */
                    while (this.casillas[i+contadorClase][i].objeto == Obstaculo.NADA)
                        contadorClase++;
                    casillas[i][j].distancias[0] = this.casillas[i+contadorClase][i].x - casillas[i][j].x;
                    contadorClase = 1;
                    /* Buscamos el obstáculo más cercano a la diag. der. sup. */
                    while (this.casillas[i+contadorClase][i-contadorClase].objeto == Obstaculo.NADA)
                        contadorClase++;
                    int pot1 = (int)Math.pow(this.casillas[i+contadorClase][i].x - casillas[i][j].x, 2);
                    int pot2 = (int)Math.pow(this.casillas[i][i-contadorClase].y, 2);
                    casillas[i][j].distancias[1] = (float)Math.sqrt(pot1 + pot2);
                    contadorClase = 1;
                    /* Buscamos el obstáculo más cercano hacia arriba. */
                    while (this.casillas[i][i-contadorClase].objeto == Obstaculo.NADA)
                        contadorClase++;
                    casillas[i][j].distancias[2] = casillas[i][j].y - this.casillas[i][i-contadorClase].y;
                    contadorClase = 1;
                    /* Buscamos el obstáculo más cercano a la diag. izq. sup. */
                    while (this.casillas[i-contadorClase][i-contadorClase].objeto == Obstaculo.NADA)
                        contadorClase++;
                    pot1 = (int)Math.pow(casillas[i][j].x - this.casillas[i-contadorClase][i].x, 2);
                    pot2 = (int)Math.pow(casillas[i][j].y - this.casillas[i][i-contadorClase].y, 2);
                    casillas[i][j].distancias[3] = (float)Math.sqrt(pot1 + pot2);
                    contadorClase = 1;
                    /* Buscamos el obstáculo más cercano a la izquierda. */
                    while (this.casillas[i-contadorClase][i].objeto == Obstaculo.NADA)
                        contadorClase++;
                    casillas[i][j].distancias[4] = casillas[i][j].x - this.casillas[i-contadorClase][i].x;
                    contadorClase = 1;
                    /* Buscamos el obstáculo más cercano a la diag. izq. inf. */
                    while (this.casillas[i-contadorClase][i+contadorClase].objeto == Obstaculo.NADA)
                        contadorClase++;
                    pot1 = (int)Math.pow(casillas[i][j].x - this.casillas[i-contadorClase][i].x, 2);
                    pot2 = (int)Math.pow(this.casillas[i][i+contadorClase].y - casillas[i][j].y, 2);
                    casillas[i][j].distancias[5] = (float)Math.sqrt(pot1 + pot2);
                    contadorClase = 1;
                    /* Buscamos el obstáculo más cercano hacia abajo. */
                    while (this.casillas[i][i+contadorClase].objeto == Obstaculo.NADA)
                        contadorClase++;
                    casillas[i][j].distancias[6] = this.casillas[i][i+contadorClase].y - casillas[i][j].y;
                    contadorClase = 1;
                    /* Buscamos el obstáculo más cercano en la diag. der. inf. */
                    while (this.casillas[i+contadorClase][i+contadorClase].objeto == Obstaculo.NADA)
                        contadorClase++;
                    pot1 = (int)Math.pow(this.casillas[i+contadorClase][i].x - casillas[i][j].x, 2);
                    pot2 = (int)Math.pow(this.casillas[i][i+contadorClase].y - casillas[i][j].y, 2);
                    casillas[i][j].distancias[7] = (float)Math.sqrt(pot1 + pot2);
                    contadorClase = 1;
                }
            }
        }
    }
    
    //Aquí sacamos la probabilidad condicional de casa una de las casillas.
    public float probaCond(Casilla casill) {
        float div = 1.0f/((float)Math.sqrt(2*Math.PI)*varianza);
        this.robot.st = putST();
        float equis = (float)Math.pow(this.robot.x+casill.x,2);
        float yes =(float)Math.pow(this.robot.y+casill.y,2);
        float rest =  this.robot.st- (float)Math.sqrt(equis+yes);
        float restc = (float)Math.pow(rest, 2);
        float restneg = restc*-1;
        float restdiv = (restneg/(2*(float)Math.pow(varianza,2)));
        float restf = (float)Math.rint(restdiv);
        float e = (float)Math.exp(restf);
        return (e*div);
    }
    
    //Aquí calculamos el aplha t del robot.
    private void calculaAlphaT(){
        double x = Math.abs(this.robot.x-this.robot.xOriginal);
        double y = Math.abs(this.robot.y-this.robot.yOriginal);
        x *= x;
        y *= y;
        double dis = Math.sqrt(x+y);
        this.robot.alphaT = dis;
    }
    
    //En este método privado lo que hacemos es lanzar el laser y medir la distancia mínima.
    private int putST(){
        int d = 1, i = this.robot.x, j = this.robot.y;
        boolean cond = true;
        while(cond){
            if(this.casillas[i+d][j].getObjeto() != Obstaculo.NADA)
                return d-1;
            if(this.casillas[i+d][j+d].getObjeto() != Obstaculo.NADA)
                return d-1;
            if(this.casillas[i][j+d].getObjeto() != Obstaculo.NADA)
                return d-1;
            if(this.casillas[i-d][j+d].getObjeto() != Obstaculo.NADA)
                return d-1;
            if(this.casillas[i-d][j].getObjeto() != Obstaculo.NADA)
                return d-1;
            if(this.casillas[i-d][j-d].getObjeto() != Obstaculo.NADA)
                return d-1;
            if(this.casillas[i][j-d].getObjeto() != Obstaculo.NADA)
                return d-1;
            if(this.casillas[i+d][j-d].getObjeto() != Obstaculo.NADA)
                return d-1;
            d++;
        }
        return d-1;
    }
   
    //Inicialización de las casillas de nuestro mundo.
    private Casilla[][] inicializaCasillas(int tamanioCasillas) {
        int tam = this.tamanio/tamanioCasillas;
        float creencia = (1.0f/(((float)Math.pow(tam, 2))-this.obstaculos));
        Casilla[][] temp = new Casilla[tam][tam];
        for (int i = 0; i < temp.length;  i++) {
            for (int j = 0; j < temp[i].length; j++) {
                Obstaculo o = Obstaculo.NADA;
                if(i == 0 || j == 0 || i == temp.length-1 || j == temp[i].length-1)
                    o = Obstaculo.PARED;
                temp[i][j] = new Casilla(o, i, j, tamanioCasillas, creencia);
            }
        }
        return temp;
    }
    
    //Creamos los obstáculos de nuestro mundo.
    private void creaObstaculos(Casilla[][] casillas) {
        int c = 0, i = 0, j = 0;
        while(c <= this.obstaculos){
            i = random.nextInt(casillas.length);
            j = random.nextInt(casillas[0].length);
            if(casillas[i][j].getObjeto() == Obstaculo.NADA){
                Obstaculo ob = getObstaculo();
                casillas[i][j].setObjeto(ob);
                c++;
            }
        }
    }
    
    //Nos genera un obstaculo aleatorio.
    private Obstaculo getObstaculo(){
        int i = this.random.nextInt(3);
        switch(i){
            case 0:
                return Obstaculo.MESA;
            case 1:
                return Obstaculo.SILLA;
            default:
                return Obstaculo.PARED;
        }
    }
    
    //Calcula la distancia de cada una de las celdas.
    private void calculaDistanciaCeldas() {
        for (int i = 0; i < this.casillas.length; i++) {
            for (int j = 0; j < this.casillas[i].length; j++) {
                if(this.casillas[i][j].getObjeto() == Obstaculo.NADA){
                    this.casillas[i][j].setDistancia(getDistancia(i,j));
                }
            }
        }
    }
    
    //Nos da la distancia de un objeto a otra de las celdas con nada.
    private int getDistancia(int i, int j){
        int d = 0;
        boolean cond = true;
        while(cond){
            if(this.casillas[i+d][j].getObjeto() != Obstaculo.NADA)
                return d;
            if(this.casillas[i+d][j+d].getObjeto() != Obstaculo.NADA)
                return d;
            if(this.casillas[i][j+d].getObjeto() != Obstaculo.NADA)
                return d;
            if(this.casillas[i-d][j+d].getObjeto() != Obstaculo.NADA)
                return d;
            if(this.casillas[i-d][j].getObjeto() != Obstaculo.NADA)
                return d;
            if(this.casillas[i-d][j-d].getObjeto() != Obstaculo.NADA)
                return d;
            if(this.casillas[i][j-d].getObjeto() != Obstaculo.NADA)
                return d;
            if(this.casillas[i+d][j-d].getObjeto() != Obstaculo.NADA)
                return d;
            d++;
        }
        return d;
    } 
}//Fin de Mundo.java