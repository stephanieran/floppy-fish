package edu.uchicago.gerber._08final.floppyFish.model;

import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;

public class Fish {
    private final int x;
    private int y;
    private final int width;
    private final int height;
    private boolean dead;
    private double yVelocity;
    private final double gravity;
    private int jumpDelay;
    private double rotation;
    private Image image;
    private final String imagePath;
    private final Keyboard keyboard;

    public Fish() {
        imagePath = "floppyFish/resources/fish.png";
        x = 100;
        y = 150;
        yVelocity = 0;
        width = 45;
        height = 32;
        gravity = -0.5;
        jumpDelay = 0;
        rotation = 0.0;
        dead = false;

        keyboard = Keyboard.getInstance();
    }

    /**
     * Updates the fish,
     */
    public void update() {
        yVelocity += gravity;
        if (jumpDelay > 0)
            jumpDelay--;

        if (!dead && keyboard.keyDown(KeyEvent.VK_SPACE) && jumpDelay == 0) {
            yVelocity = 8; // positive means downward movement
            jumpDelay = 10;
        }

        if (dead && keyboard.keyDown(KeyEvent.VK_SPACE)) {} // do nothing if SPACE when dead

        //if fish dies, it'll drop down to the depths of the sea floor
        if (dead) {
            yVelocity = 6;
        }

        y += yVelocity;
    }

    /**
     * Get rendered fish
     */
    public Render getRender() {
        Render r = new Render();
        r.updateX(x);
        r.updateY(y);

        if (image == null) {
            image = Render.loadImage(imagePath);
        }
        r.image = image;

        rotation = (90 * (yVelocity + 20) / 20) - 90;
        rotation = rotation * Math.PI / 180;

        if (rotation > Math.PI / 2)
            rotation = Math.PI / 2;

        r.transform = new AffineTransform();
        r.transform.translate(x + (double) width / 2, y + (double) height / 2);
        r.transform.rotate(rotation);
        r.transform.translate((double) -width / 2, (double) -height / 2);

        return r;
    }

    // access methods
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void updateDead(boolean isDead) {
        dead = isDead;
    }
}