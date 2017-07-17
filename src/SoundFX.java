package src;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;


import javax.sound.midi.*;
import javax.sound.sampled.*;
import javax.swing.Timer;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.ByteBuffer;

import sun.audio.*;
/**
 * Write a description of class SoundFX here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SoundFX
{

    
    public static void CrappyBeep()
    {
        java.awt.Toolkit.getDefaultToolkit().beep();
        //checkOs();
        
    }
    
    public static void BettaBeep() {
    	try {
			beep(500, 100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public static void checkOs() {
    	String oz = System.getProperty("os.name");
    	
    	if(oz.substring(0, 7).equals("Windows")) {
    		System.out.println(true);
    	}
    	System.out.println(oz.substring(0, 7));
    }
    
    public static void beep(double freq, final double millis) throws InterruptedException, LineUnavailableException {

        final Clip clip = AudioSystem.getClip();
        /**
         * AudioFormat of the reclieved clip. Probably you can alter it
         * someway choosing proper Line.
         */
        AudioFormat af = clip.getFormat();

        /**
         * We assume that encoding uses signed shorts. Probably we could
         * make this code more generic but who cares.
         */
        if (af.getEncoding() != AudioFormat.Encoding.PCM_SIGNED){
            throw new UnsupportedOperationException("Unknown encoding");
        }

        if (af.getSampleSizeInBits() != 16) {
            System.err.println("Weird sample size.  Dunno what to do with it.");
            return;
        }

        /**
         * Number of bytes in a single frame
         */
        int bytesPerFrame = af.getFrameSize();
        /**
         * Number of frames per second
         */
        double fps = af.getFrameRate();
        /**
         * Number of frames during the clip .
         */
        int frames = (int)(fps * (millis / 1000));

        /**
         * Data
         */
        ByteBuffer data = ByteBuffer.allocate(frames * bytesPerFrame);

        /**
         * We will emit sinus, which needs to be scaled so it has proper
         * frequency --- here is the scaling factor.
         */
        double freqFactor = (Math.PI / 2) * freq / fps;
        /**
         * This sinus must also be scaled so it fills short.
         */
        double ampFactor = Short.MAX_VALUE;

        short sample;

        for (int frame = 0; frame < frames; frame++) {
            sample = (short) (ampFactor * Math.sin(frame * freqFactor));
            data.putShort(sample);
        }
        clip.open(af, data.array(), 0, data.position());

        // This is so Clip releases its data line when done.  Otherwise at 32 clips it breaks.
        clip.addLineListener(new LineListener() {
            @Override
            public void update(LineEvent event) {
                if (event.getType() == LineEvent.Type.START) {
                    Timer t = new Timer((int)millis + 1, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            clip.close();
                        }
                    });
                    t.setRepeats(false);
                    t.start();
                }
            }
        });
        clip.start();

        Thread.sleep((long)millis);



    }

   /*
   public static void main(String args[]) {
       java.awt.Toolkit.getDefaultToolkit().beep();

    }
    */
}
