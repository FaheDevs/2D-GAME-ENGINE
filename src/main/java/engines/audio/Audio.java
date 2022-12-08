package engines.audio;


import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Audio {

    public Clip clip;


    public Audio(){

    }


    public void setFile(String path){
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(path));
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void play(){
        clip.start();
    }

    public void loop(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void stop(){
        clip.stop();
    }



}
