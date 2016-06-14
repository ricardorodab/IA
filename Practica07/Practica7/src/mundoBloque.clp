;; Templates
(deftemplate on-top-of
    (slot upper)
    (slot lower))

(deftemplate goal
    (slot move)
    (slot on-top-of))

;; Mueve un cubo sobre otro
(defrule move-directly
    ?goal <- (goal (move ?block1) (on-top-of ?block2))
    (block ?block1)
    (block ?block2)
    ?stack1 <- (stack ?block1 $?rest1)
    ?stack2 <- (stack ?block2 $?rest2)
    =>
    (retract ?goal ?stack1 ?stack2)
    (assert (stack ?block1 ?block2 ?rest1))
    (assert (stack ?rest2))
    (printout t ?block1 " moved on top of " ?block2 crlf)
  )

;; Mueve un cubo al suelo
(defrule move-to-floor
    ?goal <- (goal (move ?block1) (on-top-of floor))
    (block ?block1)
    ?stack1 <- (stack ?block1 $?rest)
    =>
    (retract ?goal ?stack1)
    (assert (stack ?block))
    (assert (stack ?rest))
    (printout t ?block1 " moved on top of floor" crlf)
  )

;; Agrega la meta requerida: quitar el cubo arriba del cubo que quiero mover
(defrule clear-upper-block
    (goal (move ?block1))
    (block ?block1)
    ?stack1 <- (stack ?top $? ?block1 $?)
    =>
    (assert (stack $? ?top))
    (assert (stack ?block1 $?))
  )

;; Agrega la meta requerida: quitar el cubo arriba del cubo sobre el que colcaré
;; otro.
(defrule clear-lower-block
    (goal (on-top-of ?block1))
    (block ?block1)
    ?stack1 <- (stack ?top $? ?block1 $?)
    =>
    (assert (stack $? ?top))
    (assert (stack ?block1 $?))
  )

(deffacts initial-state
    (block A) (block B)
    (block C) (block D)
    (block E) (block F)
    (on-top-of (upper nothing) (lower A))
    (on-top-of (upper A) (lower B))
    (on-top-of (upper B) (lower C))
    (on-top-of (upper C) (lower floor))
    (on-top-of (upper nothing) (lower D))
    (on-top-of (upper D) (lower E))
    (on-top-of (upper E) (lower F))
    (on-top-of (upper F) (lower floor))
    (goal (move C) (on-top-of E))
)