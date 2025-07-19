package io.github.Sonic_V0.Personajes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import io.github.Sonic_V0.Mundo;

public class Sonic extends Amigas {
    private final Mundo mundo;

    public Sonic (Body body, Mundo mundo) {
        super(body);
        this.mundo = mundo;
        inicializarAnimaciones(body.getPosition().x, body.getPosition().y);
        this.name = "Sonic";

    }

    @Override
    void inicializarAnimaciones(float x, float y) {
        atlas = new TextureAtlas(Gdx.files.internal("SpriteSonic/SonicSprite.atlas"));
        sprite = atlas.createSprite("SonicSprite0");
        sprite.setSize(30f / PPM, 39f / PPM); // ≈ 0.91 x 1.19
        sprite.setPosition(
            x - sprite.getWidth() / 2f,
            y - sprite.getHeight() / 2f
        );
        correr = crearAnimacion("SonicSprite", 8, 0.09f);       // del 1 al 8
        abajo = crearAnimacion("abajo", 10, 0.1f);
        arriba = crearAnimacion("arriba", 6, 0.1f);
        diagonalarr = crearAnimacion("diagonal", 6, 0.1f);
        diagonalabj = crearAnimacion("Diagonalabj", 7, 0.1f);

        frameActual = new TextureRegion();
    }

    @Override
    public void actualizar(float delta) {
        izq = der = abj = arr = false;
        boolean presionando = false;

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            body.setLinearVelocity(0, velocidad.y);
            arr = true;
            presionando = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            body.setLinearVelocity(0, -velocidad.y);
            abj = true;
            presionando = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            body.setLinearVelocity(-velocidad.x, body.getLinearVelocity().y);
            izq = true;
            presionando = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            body.setLinearVelocity(velocidad.x, body.getLinearVelocity().y);
            der = true;
            presionando = true;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            atacar();
        }
        if (!presionando) {
            body.setLinearVelocity(0, 0);
            frameActual = sprite;
            stateTime = 0f;
        }
        super.actualizar(delta);
    }

    private void atacar() {
        mundo.limpiarArea(body.getPosition());
    }

    public Body getBody() {
        return body;
    }

    @Override
    public void dispose() {
        atlas.dispose();
    }
}

