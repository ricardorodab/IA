/* -------------------------------------------------------------------
* Robot.java
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

/**
 * @author Ricardo Abreu
 * @version 1.0
 * @since Jun 13 2016.
 * <p> Enum de Obstaculos.</p>
 *
 * <p> Clase que enumera los obstaculos de nuestro mundo.</p>
 */
public class Robot {
    /** La posición x del robot. */
    int x;
    /** La posición y del robot. */
    int y;
    /** La primer posición x del robot. */
    int xOriginal;
    /** La primer posición y del robot. */
    int yOriginal;
    /** Los grados en el que mira el robot. */
    int grado;
    /** La lectura del laser del robot. */
    int st;
    /** La lectuta del sensor odometrico. */
    double alphaT;
    /** La dirección actual del robot. */
    int direccion;
    /** Arreglo con el desplazamiento y  ángulo del robot. */
    float[] alpha;
    /** Arreglo con las mediciones de los sensores en las 8 direcciones. */
    float[] sensores;

    /**
     * Metodo constructor del robot.
     * @param x - Es la posición x del robot.
     * @param y - Es la posición y del robot.
     * @param grado - Es el grado en el que está mirando el robot.
     */
    public Robot(int x, int y, int grado) {
        this.x = x;
        this.y = y;
        this.xOriginal = x;
        this.yOriginal = y;
        this.grado = grado;
        this.st = 0;
        this.alphaT = 0;
        this.alpha = new float[3];
        this.sensores = new float[8];
        this.direccion = direccion;
    }
} //Fin de Robot.java