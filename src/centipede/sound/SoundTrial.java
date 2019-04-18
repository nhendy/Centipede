package centipede.sound;



import java.io.File;
import javax.sound.sampled.*;


public class SoundTrial implements Runnable{

    private  File _file;

    public SoundTrial(){
        this._file = new File("sounds/laser.wav");

    }

    public synchronized void run(){
        try {
            Clip clip  = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(_file));
            clip.start();
            // Thread.sleep(clip.getMicrosecondLength() / 1000);
        } catch (Exception e) {
            //TODO: handle exception
        }
    }
    
}