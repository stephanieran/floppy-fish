package edu.uchicago.gerber._08final.floppyFish.controller;
import edu.uchicago.gerber._08final.floppyFish.model.*;

import javax.sound.sampled.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Game {
    private int restartDelay;
    private int pipeDelay;
    private int pipeDelayInc = 0;
    private Fish fish;
    private ArrayList<Pipe> pipes;
    private final Keyboard keyboard;
    private boolean scoreChanged = false;
    private int level = 0;
    private boolean levelUp = false;
    private int displayLevelTime = 0;
    private int score;
    private int highScore = 0;
    private Boolean gameOver;
    public int roundCounter = 0;
    public Boolean gameStarted;

    public Game() {
        keyboard = Keyboard.getInstance();
        Clip clpMusicBackground = Sound.clipForLoopFactory("dreamlandMusic.wav");
        Clip clpSoundBackground = Sound.clipForLoopFactory("underwaterSound.wav");
        clpMusicBackground.loop(Clip.LOOP_CONTINUOUSLY);
        clpSoundBackground.loop(Clip.LOOP_CONTINUOUSLY);
        startOrRestart();
    }

    /**
     * Starts or restarts the game, resets everything
     */
    public void startOrRestart() {
        scoreChanged = false;
        gameStarted = false;
        gameOver = false;

        displayLevelTime = 0;
        score = 0;
        level = 0;
        pipeDelayInc = 0;
        restartDelay = 0;
        pipeDelay = 0;

        fish = new Fish();
        pipes = new ArrayList<>();
    }

    /**
     * Updates the game
     */
    public void update() {
        listenStart();

        if (!gameStarted) {
            return;
        }

        listenReset();

        fish.update();

        if (gameOver) {
            fish.update();
            return;
        }

        // level up and increase speed
        if (score%10 == 0 && score != 0 && scoreChanged && pipeDelayInc <= 40) {
            scoreChanged = false;
            levelUp = true;
            displayLevelTime = 50;
            level += 1;
            pipeDelayInc +=10;
        }

        if (levelUp) {
            displayLevelTime--;
        }

        movePipes(pipeDelayInc);
        checkCollisions();
    }

    /**
     * Returns the renders to be painted
     */
    public ArrayList<Render> getRenders() {
        ArrayList<Render> renders = new ArrayList<>();
        renders.add(new Render(0, 0, "floppyFish/resources/background.png"));
        for (Pipe pipe : pipes)
            renders.add(pipe.getRender());
        renders.add(new Render(0, 0, "floppyFish/resources/foreground.png"));
        renders.add(fish.getRender());
        return renders;
    }

    /**
     * Starts if SPACE is pressed
     */
    private void listenStart() {
        if (!gameStarted && keyboard.keyDown(KeyEvent.VK_SPACE)) {
            gameStarted = true;
            roundCounter++;
        }
    }

    /**
     * Starts again if R is pressed
     */
    private void listenReset() {

        if (restartDelay > 0)
            restartDelay--;

        if (keyboard.keyDown(KeyEvent.VK_R) && restartDelay == 0) {
            startOrRestart();
            restartDelay = 10;
        }
    }

    /**
     * Moves the pipes
     */
    private void movePipes(int inc) {
        pipeDelay--;

        if (pipeDelay < 0) {
            pipeDelay = 80 - inc; //80

            Pipe upPipe = new Pipe("up");
            pipes.add(upPipe);

            Pipe downPipe = new Pipe("down");
            pipes.add(downPipe);

            upPipe.updateY(downPipe.getY() + downPipe.getHeight() + 145); // gapsize
        }

        for (Pipe pipe : pipes) {
            pipe.update();
        }
    }

    /**
     * Checks if the fish has collided with the surface, the
     * ground, or any pipes
     */
    private void checkCollisions() {
        // Pipe Collision
        for (Pipe pipe : pipes) {
            if (pipe.collides(fish.getX(), fish.getY(), fish.getWidth(), fish.getHeight())) {
                gameOver = true;
                fish.updateDead(true);
            } else if (pipe.getX() == fish.getX() && pipe.orientation.equalsIgnoreCase("down")) {
                score++;
                scoreChanged = true;
                if (score > highScore) {
                    highScore = score;
                }
            }
        }

        // Ground and Surface Collision
        if ((fish.getY() + fish.getHeight() < 5) || (fish.getY() + fish.getHeight() > GamePlayer.HEIGHT)) { // changed
            gameOver = true;
            fish.updateDead(true);
        }
    }

    // access methods
    public int getLevel() {
        return level;
    }
    public int getDisplayLevelTime() {
        return displayLevelTime;
    }

    public int getHighScore() {
        return highScore;
    }

    public int getScore() {
        return score;
    }

    public Boolean getGameOver() {
        return gameOver;
    }
}