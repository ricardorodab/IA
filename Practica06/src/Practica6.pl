%José Ricardo Rodríguez Abreu.
%Práctica06 de Inteliencia Artificial.




%EJERCICIO1

%Definimos todas las palabras con cada una de sus letras.
word(astante, a,s,t,a,n,t,e). 
word(astoria, a,s,t,o,r,i,a). 
word(baratto, b,a,r,a,t,t,o). 
word(cobalto, c,o,b,a,l,t,o). 
word(pistola, p,i,s,t,o,l,a). 
word(statale, s,t,a,t,a,l,e).    
%Lo único que hacemos es buscar las palabras que coincidan con las letras que nos convengan.
crossword(V1,V2,V3,H1,H2,H3) :- word(V1,_,C1,_,C2,_,C3,_),word(V2,_,C4,_,C5,_,C6,_),word(V3,_,C7,_,C8,_,C9,_),
				word(H1,_,C1,_,C4,_,C7,_),word(H2,_,C2,_,C5,_,C8,_),word(H3,_,C3,_,C6,_,C9,_),
				esDiferenteATodos(V1,V2,V3,H1,H2,H3).

%Al final revisamos que las palabras sean diferentes una de otras.
esDiferenteATodos(V1,V2,V3,V4,V5,V6) :- V1 \== V2, V1 \== V3, V1 \== V4, V1 \== V5, V1 \== V6,
					V2 \== V3, V2 \== V4, V2 \== V5, V2 \== V6,
					V3 \== V4, V3 \== V5, V3 \== V6,
					V4 \== V5, V4 \== V6,
					V5 \== V6.




%EJERCICIO2

%El cambio exacto de $0 es cualquier cosa.
%El cambio exacto de N es N.
%El cambio exacto de una lista de monedas de N es lo siguiente:
%Ordenamos la lista con msort y obtenemos la reversa para que sea de mayor a menor.
%Aplicamos a esa lista ordenada en reversa un aloritmo greedy.
cantex(_,0).
cantex(X,X).
cantex([H|T],N) :- msort([H|T],Y),myReversa(Y,W),greedy(W,N).

%Mi algoritmo de reversa.
myReversa([],[]).
myReversa([H|T],Y) :- myReversa(T,S),pega(S,[H],Y).

%Mi algoritmo para concatenar listas.
pega([],Y,Y).
pega([X|XS],YS,[X|ZS]) :- pega(XS,YS,ZS).

%Mi algoritmo greedy dice lo que sigue:
%Vemos si el primer elemento es el cambio exacto, si así es decimos que true.
%Si no, hay dos casos:
%Si la cabeza de la lista es más grande que el cambio exacto, la ignoramos y seguimos con la  cola.
%Caso contrario le sumamos el primer elemento a todos los elementos de la lista y recursamos o lo ignoramos.
greedy([H|T],N) :- cantex(H,N); (H > N -> greedy(T,N)
				 ;
				 pruebaElem(H,T,N) ; greedy(T,N)).

%Método auxiliar para revisar si el elemento más el que sigue es N
%o si sumando el elemento E a todos los de la lista encontramos el cambio.
pruebaElem(E,[H|T],N) :- cantex(E+H,N) ; myMap(E,[H|T],Y),greedy(Y,N).

%Método auxiliar para sumar un elemento a otros dos.
sumElem(E,N,R) :- R is N+E.

%Mi propia implementación del método map.
myMap(_,[],[]).
myMap(E,[H|T],R) :- myMap(E,T,X),sumElem(E,H,S),pega([S],X,R).





%EJERCICIO3
%Tomamos el color de la primera región,tenemos todos los vecinos de esa región y
%"recursamos" diciendo que asignamos el color C a la region 1.
colorea(reg1,reg2,reg3,reg4,reg5,reg6) :- setColor(reg1,[],[],[]).
%Todos los colores (4).
color(rojo).
color(azul).
color(amarillo).
color(verde).
%Asignamos el primer color a una region aleatoria.
%Buscamos un color C, sacamos todos los vecinos N y vemos que C no esté prohibido de usar en esta área.
%Después vamos con los vecinos para hacer uno a uno lo mismo.
%Params:
% X - región actual.
% C0 - Color de la region de V.
% V - La región.
% PR - Los vecinos a recorrer.
setColor(X,C0,V,PR) :- color(C1),allVecinos(X,N),prohibido(C1,C0,V,N),pega(N,PR,NPR),pega([C1],C0,C2),pega([X],V,XV),veVecinos(NPR,C2,XV).

%Método auxiliar para ver que el color no esté prohibido.
prohibido(_,[],[],_).
prohibido(C,[CH|CT],[VH|VT],N) :- (member(VH,N) -> (C \== CH,prohibido(C,CT,VT,N))
				   ;
				   prohibido(C,CT,VT,N)).

%
veVecinos([],_,_).
veVecinos([NH|NT],C,V) :- setColor(NH,C,V,NT).


vecinos(reg1, reg2).
vecinos(reg1, reg3).
vecinos(reg1, reg4).
vecinos(reg1, reg5).
vecinos(reg2, reg3).
vecinos(reg3, reg5).
vecinos(reg4, reg5).
vecinos(reg4, reg6).

areVecinos(X,Y) :- vecinos(X,Y) ; vecinos(Y,X).

allVecinos(X,T) :- findall(Y,vecinos(X,Y),T).
