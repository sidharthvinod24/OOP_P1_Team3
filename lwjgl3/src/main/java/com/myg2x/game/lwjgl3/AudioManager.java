package com.myg2x.game.lwjgl3;

// This class manages all the audio files in the game such as background music, sound effects, etc.
// Easy to add new audio files if needed in the future.
// We will use a HashMap to store the audio files. The key will be the name of the audio file and the value will be the Audio object.
// This will allow it to be easily accessed and managed as hasmap is a key-value pair data structure.
import java.util.HashMap;

public class AudioManager {
    private HashMap<String, Audio> audioMap;

    public AudioManager() {
        audioMap = new HashMap<>();
    }

    // Add audio file to the audioMap
    public void addAudio(String name, String path, boolean isMusic) {
        Audio audio = new Audio(path, isMusic);
        audioMap.put(name, audio);
    }

    // Play music
    public void playMusic(String name, boolean loop, float volume) {
        Audio audio = audioMap.get(name);
        if (audio != null) {
            audio.playMusic(loop, volume);
        }
    }

    // Play sound effect
    public void playSoundEffect(String name, float volume) {
        Audio audio = audioMap.get(name);
        if (audio != null) {
            audio.playSoundEffect(volume);
        }
    }

    // Stop music
    public void stopMusic(String name) {
        Audio audio = audioMap.get(name);
        if (audio != null) {
            audio.stopMusic();
        }
    }

    // Dispose all audio
    public void dispose() {
        for (Audio audio : audioMap.values()) {
            audio.dispose();
        }
    }
}
