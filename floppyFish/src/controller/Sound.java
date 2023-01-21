package edu.uchicago.gerber._08final.floppyFish.controller;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {
    public static Clip clipForLoopFactory(String strPath){
        Clip clp = null;
        try {
            InputStream audioSrc = edu.uchicago.gerber._08final.mvc.controller.Sound.class.getResourceAsStream("/mySounds/" + strPath);
            InputStream bufferedIn = new BufferedInputStream(audioSrc);
            AudioInputStream aisStream = AudioSystem.getAudioInputStream(bufferedIn);
            clp = AudioSystem.getClip();
            clp.open( aisStream );

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }

        return clp;

    }
}
