package engines.audio;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Audio {
    private Clip clip;

    public Audio(String son) {
        try {
            //handle
            AudioInputStream clip = AudioSystem.getAudioInputStream(getClass().getResource(son));
            this.clip = AudioSystem.getClip();
            this.clip.open(clip);
        } catch (Exception e) {
        }
    }

    public Clip getClip() {
        return clip;
    }

    public void play() {
        clip.start();
    }
    public void stop() {
        clip.stop();
    }

    public static void play(String son) {
        Audio s = new Audio(son);
        s.play();

    }



}
