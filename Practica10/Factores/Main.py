import os
from Parser import * 
import sys

class Main:

    def __init__(self, archivo_entrada):
        self.archivo = archivo_entrada
        if isinstance(archivo_entrada, str):
            filename, file_extension = os.path.splitext(archivo_entrada)
            self.name = filename
            self.ext = file_extension
            self.dic = dame_valores(self.archivo)
        else:
            raise ValueError('Favor de pasar el archivo como una cadena')

    def get_valores(self):
        return self.dic
        

args = sys.argv
if len(args) != 2:
    print("Favor de ejecutar el programa como:")
    print("-:$python3 Main.py <file>")
else:
    main = Main(args[1])
    dic,linea = main.get_valores()
    print(linea)
    for x in dic:
        print(dic[x])
