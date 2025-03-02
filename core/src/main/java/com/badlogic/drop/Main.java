package com.badlogic.drop;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
  Texture backgroundTexture;
  Texture bucketTexture;
  Texture dropTexture;
  Sound dropSound;
  Music music;
  SpriteBatch spriteBatch;
  FitViewport viewport;


    @Override
    public void create() {
        backgroundTexture = new Texture("background.png");
        bucketTexture = new Texture("bucket.png");
        dropTexture = new Texture("drop.png");
        dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.mp3"));
        music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
        spriteBatch = new SpriteBatch();
        viewport = new FitViewport(8, 5);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true); // -> true centers the camera
    }

    @Override
    public void render() {
        // Organizing the code into 3 methods
        input();
        logic();
        draw();
    }

    private void input(){

    }

    private void logic(){

    }

    private void draw(){
        // clears the screen, Otherwise weird graphical errors will be occurred. Any color can be used
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();

        /*
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        shows how the Viewport is applied to the SpriteBatch.
        This is necessary for the images to be shown in the correct place.
         */
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();
        /* Add lines to draw stuff here. It is important to order the begin and end lines appropriately.One should never draw from a SpriteBatch outside of a begin and an end.If one do, one will get an error message.*/

        //Store the worldWidth and worldHeight as local variables for brevity
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        spriteBatch.draw(backgroundTexture, 0, 0, worldWidth , worldHeight); // draws the background

        // This code should now draw the bucket at the bottom of the screen in the lower left corner.
        spriteBatch.draw(bucketTexture, 0, 0, 1 , 1); // draws the bucket with width/height of 1 meter

        spriteBatch.end();
    }

    @Override
    public void dispose() {
        System.out.println("Game has disposed!");
    }
}
