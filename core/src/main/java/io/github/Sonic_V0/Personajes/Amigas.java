package io.github.Sonic_V0.Personajes;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;

public abstract class Amigas extends Personaje {
    protected  boolean izq = false,  der = false, arr = false, abj = false;  //Estados
    protected Animation<TextureRegion> abajo;
    protected Animation<TextureRegion> arriba;
    protected Animation<TextureRegion> diagonalarr;
    protected Animation<TextureRegion> diagonalabj;

    public Amigas(Body b) {
        super(b);
    }

    protected void actualizar(float delta) {
        this.posicion = body.getPosition(); // actualiza posición
        boolean diagonalArriba = arr && (izq || der);
        boolean diagonalAbajo = abj && (izq || der);

        // solo sumar stateTime si se anima
        if (diagonalArriba || diagonalAbajo || izq || der || abj || arr) {
            stateTime += delta;
        } else {
            stateTime = 0;
        }

        // Lógica de animaciones
        if (diagonalArriba) {
            frameActual = diagonalarr.getKeyFrame(stateTime, true);
            if (der && !frameActual.isFlipX()) frameActual.flip(true, false); // invertir para mirar derecha
            if (izq && frameActual.isFlipX()) frameActual.flip(true, false); // restaurar si está invertido
        } else if (diagonalAbajo) {
            frameActual = diagonalabj.getKeyFrame(stateTime, true);
            if (izq && !frameActual.isFlipX()) frameActual.flip(true, false); // invertir para mirar izquierda
            if (der && frameActual.isFlipX()) frameActual.flip(true, false); // restaurar si está invertido
        } else if (abj) {
            frameActual = abajo.getKeyFrame(stateTime, true);
        } else if (arr) {
            frameActual = arriba.getKeyFrame(stateTime, true);
        } else if (izq || der) {
            frameActual = correr.getKeyFrame(stateTime, true);
        } else {
            frameActual = sprite;
        }

        // Flip adicional por si hay inversión en otras direcciones
        if (!diagonalArriba && !diagonalAbajo) {
            if (izq && frameActual != null && !frameActual.isFlipX()) {
                frameActual.flip(true, false);
            } else if (der && frameActual != null && frameActual.isFlipX()) {
                frameActual.flip(true, false);
            }
        }

    }

}
