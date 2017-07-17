package src;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.Timer;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.ByteBuffer;

public class BeepThread implements Runnable{
	private Thread t;
	String id = null;
	double freq = 0.0;
	double millis = 0.0;
	BeepThread(String _id, double _freq, double _millis) {
		id = _id;
		freq = _freq;
		millis = _millis;
		System.out.println("Making Beep: " + id);
	}
	/*
	public void run() {
		try {
            final Clip clip = AudioSystem.getClip();
            AudioFormat af = clip.getFormat();

            if (af.getSampleSizeInBits() != 16) {
                System.err.println("Weird sample size.  Dunno what to do with it.");
                return;
            }

            //System.out.println("format " + af);

            int bytesPerFrame = af.getFrameSize();
            double fps = 11025;
            int frames = (int)(fps * (millis / 1000));
            frames *= 4; // No idea why it wasn't lasting as long as it should.

            byte[] data = new byte[frames * bytesPerFrame];

            double freqFactor = (Math.PI / 2) * freq / fps;
            double ampFactor = (1 << af.getSampleSizeInBits()) - 1;

            for (int frame = 0; frame < frames; frame++) {
                short sample = (short)(0.5 * ampFactor * Math.sin(frame * freqFactor));
                data[(frame * bytesPerFrame) + 0] = (byte)((sample >> (1 * 8)) & 0xFF);
                data[(frame * bytesPerFrame) + 1] = (byte)((sample >> (0 * 8)) & 0xFF);
                data[(frame * bytesPerFrame) + 2] = (byte)((sample >> (1 * 8)) & 0xFF);
                data[(frame * bytesPerFrame) + 3] = (byte)((sample >> (0 * 8)) & 0xFF);
            }
            clip.open(af, data, 0, data.length);

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
          
        } catch (LineUnavailableException ex) {
            System.err.println(ex);
        }
	}
	*/
	
	public void run() {
		System.out.println("hi");
	}
	public void start () {
		//System.out.println("Beeping: " +  id );
		if (t == null) {
	         t = new Thread (this, id);
	         System.out.println("Beeping: " +  id );
	         t.start ();
	         //kill thread 
	            t = null;
	      }
	}
	
	
}
