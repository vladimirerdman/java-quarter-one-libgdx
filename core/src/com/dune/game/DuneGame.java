package com.dune.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import jdk.nashorn.internal.parser.Scanner;

public class DuneGame extends ApplicationAdapter {
    private static class Tank {
        private Vector2 position;
        private Texture texture;
        private float angle;
        private float speed;

        public Tank(float x, float y) {
            this.position = new Vector2(x, y);
            this.texture = new Texture("tank.png");
            this.speed = 200.0f;
        }

        public void update(float dt) {
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                angle += 180.0f * dt;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                angle -= 180.0f * dt;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                position.x += speed * MathUtils.cosDeg(angle) * dt;
                position.y += speed * MathUtils.sinDeg(angle) * dt;
            }

            //Don't let player go outside the screen
            if (position.x < 0) { position.x = 0; }
            if (position.x > 1280) { position.x = 1280; }
            if (position.y < 0) { position.y = 0; }
            if (position.y > 720) { position.y = 720; }
        }

        public void render(SpriteBatch batch) {
            batch.draw(texture, position.x - 40, position.y - 40, 40, 40, 80, 80, 1, 1, angle, 0, 0, 80, 80, false, false);
        }

        public void dispose() {
            texture.dispose();
        }
    }

    //Round
    private static class Round {
        private Vector2 roundPosition;
        private Texture textureRound;

        public Round(float x, float y) {
            this.roundPosition = new Vector2(x, y);
            this.textureRound = new Texture("round.png");
        }

        public void render(SpriteBatch batch) {
            batch.draw(textureRound, roundPosition.x - 25, roundPosition.y - 25, 50, 50);
        }

        public void dispose() {
            textureRound.dispose();
        }
    }

    private SpriteBatch batch;
    private Tank tank;
    private Round round;

    //Запуск
    @Override
    public void create() {
        batch = new SpriteBatch();
        tank = new Tank(200, 200);
        round = new Round(300, 200);
    }

    //Отрисовка
    @Override
    public void render() {
        float dt = Gdx.graphics.getDeltaTime();
        update(dt);
        Gdx.gl.glClearColor(0, 0.4f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        tank.render(batch);
        round.render(batch);
        batch.end();
    }

    //Математика, вычисление, проверки
    public void update(float dt) {
        tank.update(dt);
    }

    //Освобождение ресурсов
    @Override
    public void dispose() {
        batch.dispose();
        tank.dispose();
        round.dispose();
    }

}