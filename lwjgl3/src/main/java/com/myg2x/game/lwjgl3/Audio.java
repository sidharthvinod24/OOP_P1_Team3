package com.myg2x.game.lwjgl3;


// This is the code for the Audio class which will be used to manage the independent audio files in the game.
// Such as background music, sound effects, etc.


import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class Audio {
    private Music music;
    private Sound soundEffect;


    public Audio(String path,boolean isMusic){
        if (path == null || path.isEmpty()) {
            return;
        }
        if (isMusic) {
            this.music = com.badlogic.gdx.Gdx.audio.newMusic(com.badlogic.gdx.Gdx.files.internal(path));
        } else {
            this.soundEffect = com.badlogic.gdx.Gdx.audio.newSound(com.badlogic.gdx.Gdx.files.internal(path));
        }
    }

    public void playMusic(boolean loop, float volume) {
        if (this.music != null) {
            this.music.setLooping(loop);
            this.music.setVolume( volume);
            this.music.play();
        }
    }

    public void playSoundEffect(float volume) {
        if (this.soundEffect != null) {
            this.soundEffect.play(volume);
        }
    }

    public void stopMusic() {
        if (this.music != null) {
            this.music.stop();
        }
    }

    public void dispose() {
        if (this.music != null) {
            this.music.dispose();
        }
        if (this.soundEffect != null) {
            this.soundEffect.dispose();
        }
    }

}
