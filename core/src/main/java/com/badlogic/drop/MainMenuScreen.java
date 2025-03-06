package com.badlogic.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;

public class MainMenuScreen implements Screen {
    final Drop game;

    // the constructor for the MainMenuScreen class
    public MainMenuScreen(final Drop game){
        this.game = game;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLUE);

        game.viewport.apply();
        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);

        game.batch.begin();
        // draw text. Remember that x and y are in meters
        /*
        game.font.draw(SpriteBatch, String, float, float),
        is how a text is rendered to the screen.
        libGDX comes with a pre-made font, Arial,
        so that you can use the default constructor and still get a font.
         */
        game.font.draw(game.batch, "Welcome to Drop ", 1, 1.5f);
        game.font.draw(game.batch, "Tap anywhere to begin!  ", 1, 1);
        game.batch.end();

        if(Gdx.input.isTouched()){
            game.setScreen(new GameScreen(game));
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) {
        game.viewport.update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
