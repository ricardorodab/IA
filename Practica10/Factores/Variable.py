class Variable:

    def __init__(self,nombre = "",valores = None):
        self.nombre = nombre
        self.valores = valores
        self.cond = False
        self.proba = []
        
    def get_nombre(self):
        return self.nombre

    def set_nombre(self,nombre):
        self.nombre = nombre

    def get_valores(self):
        return self.valores

    def set_valores(self,valores):
        self.valores = valores

    def agrega_valor(self,valor):
        if self.valores == None:
            self.valores = []
        self.valores.append(int(valor))

    def proba(self,proba):
        if self.cond:
            self.proba = self.proba.append(int(proba))
        self.proba = self.proba[0] = proba

    def __str__(self):
        cadena = "{"+self.nombre + ":"
        for x in self.valores:
            cadena = cadena+" "+str(x)+","
        ultimo = len(cadena)
        cadena = cadena+"}"
        return cadena
