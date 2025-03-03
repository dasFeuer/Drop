package com.badlogic.drop;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
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
  Vector2 touchPos;
  Array<Sprite> dropSprites;
  float dropTimer;
  Sprite bucketSprite; // a new Sprite variable declaration



    @Override
    public void create() {
        backgroundTexture = new Texture("background.png");
        bucketTexture = new Texture("bucket.png");
        dropTexture = new Texture("drop.png");
        dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.mp3"));
        music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
        spriteBatch = new SpriteBatch();
        viewport = new FitViewport(8, 5);
        bucketSprite = new Sprite(bucketTexture); // Initialize the sprite based on the texture
        bucketSprite.setSize(1, 1); // Define the size of the sprite
        touchPos = new Vector2();
        dropSprites = new Array<>();
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
        // The variable speed dictates how fast the bucket moves
        float speed = 4f;

        /*
        Delta time is the measured time between frames.
        If we multiply our movement by delta time, the movement will be consistent no matter what hardware we run this game on.
         */
        float delta = Gdx.graphics.getDeltaTime(); // retrieve the current delta

        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            // todo -> DO something when the user presses the right arrow
            bucketSprite.translateX(speed * delta); // move the bucket right
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            bucketSprite.translateX(-speed * delta); // move the bucket right
        }

        if(Gdx.input.isTouched()) { // If the user has clicked or tapped the screen
            // todo -> React to the player touching the screen
            touchPos.set(Gdx.input.getX(), Gdx.input.getY()); // Get where the touch happened on screen
            viewport.unproject(touchPos); // Convert the units to the world units of the viewport
            bucketSprite.setCenterX(touchPos.x); // Change the horizontally centered position of the bucket
        }
    }


    private void logic(){
        // Preventing the bucket to go completely of the screen.
        // Store the worldWidth and worldHeight as local variables for brevity
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        // Store the bucket size for brevity
        float bucketWidth = bucketSprite.getWidth();
        float bucketHeight = bucketSprite.getHeight();

        // Clamp x to values between 0 and worldWidth
        bucketSprite.setX(MathUtils.clamp(bucketSprite.getX(), 0, worldWidth - bucketWidth)); // Subtract the bucket width

        float delta = Gdx.graphics.getDeltaTime(); // retrieve the current delta

        // loop through each drop
        for(Sprite dropSprite : dropSprites){
            dropSprite.translateY(-2f * delta); // move the drop downward every frame
        }

        dropTimer += delta; // Adds the current delta to the timer
        if(dropTimer > 1f){ // Check if it has been more than a second
            dropTimer = 0; // Reset the timer
            createDroplet(); // Create the droplet
        }
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
        // spriteBatch.draw(bucketTexture, 0, 0, 1 , 1); // draws the bucket with width/height of 1 meter
        bucketSprite.draw(spriteBatch); // Sprites have their own method

        // draw each sprite
        for(Sprite dropSprite : dropSprites){
            dropSprite.draw(spriteBatch);
        }
        spriteBatch.end();
    }

    private void createDroplet(){
        // create local variables for convenience
        float dropWidth = 1;
        float dropHeight = 1;
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        // creating the drop sprite
        Sprite dropSprite = new Sprite(dropTexture);
        dropSprite.setSize(dropWidth, dropHeight);
        dropSprite.setX(MathUtils.random(0f, worldWidth - dropWidth)); // Randomize the drop's x position
        dropSprite.setY(worldHeight);
        dropSprites.add(dropSprite); // Add it to the list
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
