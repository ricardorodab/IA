class Factor(object):
    """docstring for Factor"""
    def __init__(self, dict_variables, probabilidades =  []):
        super(Factor, self).__init__()
        self.dict_variables = dict_variables
        self.probabilidades = probabilidades


    def multiplica_factor(self, factor):
        nuevas_variables = []
        variables_comunes = []
        for key, values in self.dict_variables:
            nuevas_variables.append((key,values))

        for key, values in factor.dict_variables:
            if (key,values) in nuevas_variables:
                print key
                # variables_comunes.append(nuevas_variables.keys().index(key))
                variables_comunes.append((key,values))

            nuevas_variables.append((key,values))

        nuevo = Factor(nuevas_variables)
        print variables_comunes

        for x in xrange(0, len(self.probabilidades)):
            a = self.probabilidades[x][len(self.dict_variables)]

            for y in xrange(0, len(factor.probabilidades)):
                b = factor.probabilidades[y][len(factor.dict_variables)]
                
                if variables_comunes:
                    for key,values in variables_comunes:
                        index1 = self.dict_variables.index((key,values))
                        index2 = factor.dict_variables.index((key,values))

                        if self.probabilidades[x][index1] == factor.probabilidades[y][index2]:
                            nuevo.probabilidades.append(a*b)

                else:
                    nuevo.probabilidades.append(a*b)


        return nuevo





f1 = Factor(
    [
        ('LI',[0,1])
    ],
    [
        [0,0.78],
        [1,0.22]
    ]
)

f2 = Factor(
    [
        ('LF',[0,1])
    ],
    [
        [0,0.78],
        [1,0.22]
    ]
)

f1 = Factor(
    [
        ('F',[0,1]),
        ('LI',[0,1])
    ],
    [
        [0,0,0.1],
        [0,1,0.4],
        [1,0,0.4],
        [1,1,0.1]
    ]
)

f2 = Factor(
    [
        ('LI',[0,1]),
        ('LF',[0,1])
    ],
    [
        [0,0,0.6084],
        [0,1,0.1716],
        [1,0,0.1716],
        [1,1,0.0484]
    ]
)

fact = f1.multiplica_factor(f2)

print fact.dict_variables, fact.probabilidades


# f1 = Factor(
#     {
#         'F':[0,1],
#         'LI':[0,1]
#     },
#     [0.1,0.4,0.4,0.1]
# )

# f2 = Factor(
#     {
#         'LI':[0,1],
#         'LF':[0,1]
#     },
#     [0.6084,0.1716,0.1716,0.0484]
# )
