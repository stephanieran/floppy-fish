package edu.uchicago.gerber._08final.floppyFish.controller;
import edu.uchicago.gerber._08final.floppyFish.model.*;
import edu.uchicago.gerber._08final.floppyFish.view.*;

import javax.swing.*;

public class GamePlayer {
    public static int WIDTH = 500;
    public static int HEIGHT = 520;

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setVisible(true);
        GamePanel panel = new GamePanel();
        frame.add(panel);
        frame.setResizable(false);
        frame.setSize(WIDTH, HEIGHT);
        frame.setTitle("Floppy Fish!");
        Keyboard keyboard = Keyboard.getInstance();
        frame.addKeyListener(keyboard);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}