package com.myg2x.game.lwjgl3;


// This is the code for the Audio class which will be used to manage the independent audio files in the game.
// Such as background music, sound effects, etc.

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class Audio {
    private Music music;
    private Sound soundEffect;

    public Audio(String path, boolean isMusic) {
        if (path == null || path.isEmpty()) {
            throw new IllegalArgumentException("Audio path cannot be null or empty.");
        }
        try {
            if (isMusic) {
                this.music = com.badlogic.gdx.Gdx.audio.newMusic(com.badlogic.gdx.Gdx.files.internal(path));
            } else {
                this.soundEffect = com.badlogic.gdx.Gdx.audio.newSound(com.badlogic.gdx.Gdx.files.internal(path));
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to load audio: " + path, e);
        }
    }

    public void playMusic(boolean loop, float volume) {
        try {
            if (this.music != null) {
                this.music.setLooping(loop);
                this.music.setVolume(volume);
                this.music.play();
            }
        } catch (Exception e) {
            System.err.println("Error playing music: " + e.getMessage());
        }
    }

    public void playSoundEffect(float volume) {
        try {
            if (this.soundEffect != null) {
                this.soundEffect.play(volume);
            }
        } catch (Exception e) {
            System.err.println("Error playing sound effect: " + e.getMessage());
        }
    }

    public void stopMusic() {
        try {
            if (this.music != null) {
                this.music.stop();
            }
        } catch (Exception e) {
            System.err.println("Error stopping music: " + e.getMessage());
        }
    }

    public void dispose() {
        try {
            if (this.music != null) {
                this.music.dispose();
            }
        } catch (Exception e) {
            System.err.println("Error disposing music: " + e.getMessage());
        }
        try {
            if (this.soundEffect != null) {
                this.soundEffect.dispose();
            }
        } catch (Exception e) {
            System.err.println("Error disposing sound effect: " + e.getMessage());
        }
    }
}