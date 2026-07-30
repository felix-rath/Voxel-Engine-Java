package de.rathfelix.engine.config;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class Config { // get and set all config files
    private static final File VIDEO_CONFIG_FILE = new File("src/main/resources/settings/VideoSettings.json");
    private static final File SOUND_CONFIG_FILE = new File("src/main/resources/settings/SoundSettings.json");

    private static final ObjectMapper mapper = new ObjectMapper();


    // ----- VIDEO SETTINGS -----

    public static ConfigFile getVideoConfig() {
        try {
            return mapper.readValue(VIDEO_CONFIG_FILE, ConfigFile.class);
        } catch (IOException e) { System.out.println(":::Config.class: getVideoConfig(): IOException:::"); return null; }
    }

    public static void setVideoConfig(ConfigFile newConfig) {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(VIDEO_CONFIG_FILE, newConfig);
        } catch (IOException e) { System.out.println(":::Config.class: setVideoConfig(): IOException:::"); }
    }

    // ----- SOUND SETTINGS-----

    public static ConfigFile getSoundConfig() {
        try {
            return mapper.readValue(SOUND_CONFIG_FILE, ConfigFile.class);
        } catch (IOException e) { System.out.println(":::Config.class: getSoundConfig(): IOException:::"); return null; }
    }

    public static void setSoundConfig(ConfigFile newConfig) {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(SOUND_CONFIG_FILE, newConfig);
        } catch (IOException e) { System.out.println(":::Config.class setSoundConfig() IOException:::"); }
    }

    //

}
