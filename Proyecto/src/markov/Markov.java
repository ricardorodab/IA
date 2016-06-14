/* -------------------------------------------------------------------
* Markov.java
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

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

/**
 * @author Ricardo Abreu
 * @version 1.0
 * @since Jun 13 2016.
 * <p> Clase principal del proyecto de IA.</p>
 *
 * <p> Aquí se encuentra el método principal del proyecto..</p>
 */
public class Markov extends PApplet {
    
    /** El valor de x del tablero. */
    public static final int xFinal = 700;
    /** El valor de y del tablero. */
    public static final int yFinal = 700;
    //public static final int yFinal = xFinal-200;
    /** El area de cada casilla en pixeles. */
    public static final int area = 50;
    /** El número de obstaculos -1 que tendra el mundo. */
    public static final int obstaculos = 10;
    /** Un valor para ver si seguimos iterando. */
    public boolean avanza;
    /** El angulo en el que estamos viendo. */
    int angulo;
    /** Un mundo en el que nos movemos. */
    public Mundo mundo;
    /** Un robot que movemos por el mundo. */
    public Robot robot;
    /** Cargamos imágenes para hacerlo más visual y boneto. */
    public PImage robotImagen, wallImagen, mesaImagen, sillaImagen;
    /** Son las casillas de nuestro mundo. */
    public Mundo.Casilla[][] casill;
    /** Un valor para movernos en un ángulo. */
    private float anguloMove = 0.7f;
    /** Un valor para agregar lecturas de variables del robot. */
    private float lectu = 0.7f;
    
    /**
     * Metodo para asignar el valor al programa.
     */
    public void settings() {
        size(xFinal, yFinal);
    }
    
    /**
     * Configuracion inicial.
     */
    @Override
    public void setup(){
        robotImagen = loadImage("robot.png");
        wallImagen = loadImage("wall.jpg");
        mesaImagen = loadImage("table.gif");
        sillaImagen = loadImage("silla.png");
        surface.setResizable(true);
        surface.setSize(xFinal,yFinal);
        background(200); //Fondo Gris
        robot = new Robot(0, 0, 0);
        mundo = new Mundo(xFinal, area, robot,obstaculos);
        avanza = true;
        mundo.ponerRobot();
        casill = mundo.getCasillas();
        for(int i = 0; i < casill.length; i++){
            for(int j = 0; j < casill[1].length; j++){
                noStroke();
                if(casill[i][j].getObjeto() == Obstaculo.PARED){
                    image(wallImagen, i*area, j*area, area, area);
                    fill(0, 0, 0);
                }else if(casill[i][j].getObjeto() == Obstaculo.ROBOT){
                    image(robotImagen, i*area, j*area, area, area);
                }else if(casill[i][j].getObjeto() == Obstaculo.MESA){
                    image(mesaImagen, i*area, j*area, area, area);
                }else if(casill[i][j].getObjeto() == Obstaculo.SILLA){
                    image(sillaImagen, i*area, j*area, area, area);
                }else{
                    fill(255,255,255);
                    rect(i*area,j*area,area,area);
                    stroke(0);
                    fill(0,0,0);
                    text(casill[i][j].getDistancia(),(i*area),(j*area)+area);
                }
                stroke(0);
                line(i*area,j*area,(i*area)+(area),j*area);
                line(i*area,j*area,(i*area),(j*area)+area);
                line((i*area)+(area),(j*area),(i*area)+(area),(j*area)+(area));
                line(i*area,(j*area)+area,(i*area)+(area),(j*(area))+area);
            }
        }
    }
    
    /**
     * Dibuja la imagen en cada ciclo.
     */
    @Override
    public void draw(){
        if(!avanza){
            mundo.getRobot().alphaT = 0;
        }else{
            delay(200);
            for (int j = 0; j < casill.length; j++) {
                for (int k = 0; k < casill[j].length; k++) {
                    float creencia2 = (float) (casill[j][k].getCreencia()*mundo.probaCond(casill[j][k]));
                    casill[j][k].setCreencia(creencia2);
                    mundo.getRobot().alphaT = (mundo.getRobot().alphaT+creencia2);
                }
            }
            for (int j = 0; j < casill.length; j++) {
                for (int k = 0; k < casill[j].length; k++) {
                    casill[j][k].setCreencia((float) (casill[j][k].getCreencia()/(mundo.getRobot().alphaT)));
                }
            }
            for(int i = 0; i < casill.length; i++){
                for(int j = 0; j < casill[1].length; j++){
                    noStroke();
                    if(casill[i][j].getObjeto() == Obstaculo.PARED){
                        image(wallImagen, i*area, j*area, area, area);
                        fill(0, 0, 0);
                    }else if(casill[i][j].getObjeto() == Obstaculo.ROBOT){
                        image(robotImagen, i*area, j*area, area, area);
                    }else if(casill[i][j].getObjeto() == Obstaculo.MESA){
                        image(mesaImagen, i*area, j*area, area, area);
                    }else if(casill[i][j].getObjeto() == Obstaculo.SILLA){
                        image(sillaImagen, i*area, j*area, area, area);
                    }else{
                        float f = casill[i][j].belief;
                        fill(255-map(f, 0, 0.2f, 20, 255),255-map(f, 0, 0.2f, 20, 255),255-map(f, 0, 0.2f, 20, 255));
                        rect(i*area,j*area,area,area);
                        stroke(0);
                        fill(0,0,0);
                        text(casill[i][j].getDistancia(),(i*area),(j*area)+area);
                    }
                    stroke(0);
                    line(i*area,j*area,(i*area)+(area),j*area);
                    line(i*area,j*area,(i*area),(j*area)+area);
                    line((i*area)+(area),(j*area),(i*area)+(area),(j*area)+(area));
                    line(i*area,(j*area)+area,(i*area)+(area),(j*(area))+area);
                }
            }
            busquedaDeMarkov();
        }
    }
    
    /**
     * Metodo principal del programa.
     * Desde este método se ejecuta el algoritmo de localización.
     */
    public void busquedaDeMarkov() {
        if (random(1) > 0.5) {
            angulo = robot.direccion;
            mundo.mueveRobot();
            robot.alpha[2] = abs(angulo-robot.direccion)+randomGaussian()
                    *(float)Math.sqrt(anguloMove);
            odometro();
            revisaSensorOdometro();
        } else {
            modificaRobot();
            revisarRobotSensor();
        }
    }
    
    // En este método modificamos los sensores del robot.
    private void modificaRobot() {
        for (int i = 0; i < 8; i++) {
            robot.sensores[i] = casill[robot.x][robot.y].distancias[i]
                    + randomGaussian() + sqrt(lectu);
        }
    }
    
    // En este método lo que hacemos es revisar que los sensores busquen en el mundo.
    private void revisarRobotSensor() {
        float producto;
        float beta = 0.0f;
        for (int i = 0; i < casill.length; i++) {
            for (int j = 0; j < casill[0].length; j++) {
                if (casill[j][i].objeto == Obstaculo.NADA) {
                    for (int l = 0; l < 8; l++) {
                        producto = 1.0f;
                        for (int k = 0; k < 8; k++) {
                            producto = (float) (producto*(1.0/sqrt(lectu*2*PI))
                                    *exp(-sq(robot.sensores[(k+l)%8]
                                            - casill[j][i].distancias[k])/2*lectu));
                        }
                        casill[j][i].bel[l] = producto;
                        casill[j][i].belief = producto + casill[j][i].belief;
                    }
                }
                beta += casill[j][i].belief;
            }
        }
        for (int i = 0; i < casill.length; i++) {
            for (int j = 0; j < casill[0].length; j++) {
                if (casill[j][i].objeto == Obstaculo.NADA)
                    casill[j][i].belief = casill[j][i].belief / beta;
            }
        }
    }
    
    //Modificamos el sensor odometro.
    private void odometro() {
        if (robot.direccion == 0 || robot.direccion == 4) {
            robot.alpha[0] = 1.0f + randomGaussian() * sqrt(lectu);
            robot.alpha[1] = randomGaussian() * sqrt(lectu);
        } else if (robot.direccion == 2  || robot.direccion == 6) {
            robot.alpha[0] = randomGaussian() * sqrt(lectu);
            robot.alpha[1] = 1.0f + randomGaussian() * sqrt(lectu);
        } else if (robot.direccion == 1 || robot.direccion == 3 &&
                robot.direccion == 5 && robot.direccion == 7) {
            robot.alpha[0] = 1.0f + randomGaussian() * sqrt(lectu);
            robot.alpha[1] = 1.0f + randomGaussian() * sqrt(lectu);
        }
    }
    
    //Metodo para revisar y modificar en el mundo el odometro.
    private void revisaSensorOdometro() {
        float suma;
        for (int i = 0; i < casill.length; i++) {
            for (int j = 0; j < casill[i].length; j++) {
                if (casill[i][j].objeto == Obstaculo.NADA) {
                    suma = 0;
                    for (int r = 0; r < 8; r++) {
                        for (int k = 0; k < casill.length; k++) {
                            for (int l = 0; l < casill[0].length; l++) {
                                if (casill[k][l].objeto == Obstaculo.NADA) {
                                    for (int s = 0; s < 8; s++) {
                                        suma = (float)(suma
                                                + casill[k][l].bel[s]
                                                *(1.0/sqrt(2*PI*lectu))
                                                *exp(-sq(abs(j-l)-robot.alpha[0])/2*lectu)
                                                *(1.0/sqrt(2*PI*lectu))
                                                *exp(-sq(abs(i-k)-robot.alpha[1])/2*lectu)
                                                *(1.0/sqrt(2*PI*anguloMove))
                                                *exp(-sq(abs(r-s)-robot.alpha[2])/2*anguloMove));
                                    }
                                }
                            }
                        }
                    }
                    casill[i][j].belief = suma;
                }
            }
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        PApplet.main(new String[] { Markov.class.getName() });
    }
    
}//Fin de Markov.java
