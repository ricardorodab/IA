# -------------------------------------------------------------------
 # Parser.py
 # versión 1.0
 # Copyright (C) 2016  José Ricardo Rodríguez Abreu.
 # Facultad de Ciencias,
 # Universidad Nacional Autónoma de México, Mexico.
 #
 # Este programa es software libre; se puede redistribuir
 # y/o modificar en los términos establecidos por la
 # Licencia Pública General de GNU tal como fue publicada
 # por la Free Software Foundation en la versión 2 o
 # superior.
 #
 # Este programa es distribuido con la esperanza de que
 # resulte de utilidad, pero SIN GARANTÍA ALGUNA; de hecho
 # sin la garantía implícita de COMERCIALIZACIÓN o
 # ADECUACIÓN PARA PROPÓSITOS PARTICULARES. Véase la
 # Licencia Pública General de GNU para mayores detalles.
 #
 # Con este programa se debe haber recibido una copia de la
 # Licencia Pública General de GNU, de no ser así, visite el
 # siguiente URL:
 # http://www.gnu.org/licenses/gpl.html
 # o escriba a la Free Software Foundation Inc.,
 # 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 # ------------------------------------------------------------------
#
import sys
from Variable import *

def dame_valores(archivo_entrada):
    try:
        archivo_lectura = open(archivo_entrada, 'r+')
        # Linea de las Variables: 
        linea = archivo_lectura.readline()
        linea = linea.upper()
        linea = linea.replace("VARIABLE:","")
        linea = linea.replace("VARIABLES:","")
        linea = linea.replace(" ","")
        formato = linea.find("[")
        if formato == -1:
            raise Exception('El formato del archivo es incorrecto')
        linea = linea.replace("[","")
        linea = linea.replace("]","")
        variables = linea.split("},{")
        var1 = {}
        for x in variables:
            x = x.replace("{","")
            x = x.replace("}","")
            nom_valor = x.split(':')
            nombre = nom_valor[0]
            valores = nom_valor[1]
            variable_var = Variable("",[])
            variable_var.set_nombre(nombre)
            valoreslist = valores.split(',')            
            for y in valoreslist:
                variable_var.agrega_valor(y)                
            var1[nombre] = variable_var
        # Linea de las Probabilidades:
        linea = archivo_lectura.readline()
        linea = linea.upper()
        linea = linea.replace("PROBABILIDADES:","")
        linea = linea.replace("PROBABILIDAD:","")
        linea = linea.replace(" ","")
        formato = linea.find("[")
        if formato == -1:
            raise Exception('El formato del archivo es incorrecto')
        linea = linea.replace("[","")
        linea = linea.replace("]","")
        variables = linea.split("},{")
        var2 = {}
        for x in variables:
            x = x.replace("{","")
            x = x.replace("}","")
            cada_proba = x.split(",P")
            for y in cada_proba:
                y = y.replace("P(","")
                nombre = y[0]
                valor = y[2]
                print(nombre+" "+valor)
            # Aquí se complica la cosa.        
        return (var1,linea)
    except Exception as e:
        print(e)
        print("El archivo no tiene el formato correcto o esta corrupto")
        print("El archivo debe tener el siguiente fomato en dos lineas:")
        print("[{< V ar1 >:< val0 >, ..., < valm >},..., {<Varn >:<val0 >,...,<valm >}]")
        print("[{P(<Vari >=0)=0,1,P(<Vari >=1)=0,5,P"
        +"(<Vari >=2)=0,4}, {P(<Vari >|varj =0,...,vark =0)=<val>,P(<Vari >|varj =0,...,vark =1)=<val>,...}...]")
