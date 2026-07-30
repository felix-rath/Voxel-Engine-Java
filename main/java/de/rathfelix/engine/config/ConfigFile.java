package de.rathfelix.engine.config;

import java.util.List;

public class ConfigFile {
    public Resolution currentResolution;
    public List<Resolution> resolutionList;
    public boolean fullscreen;

    // Set so u can give old ConfigFileMapper as new ConfigFileMapper.
    // ----- RESOLUTION -----

    public Resolution getCurrentResolution(){return currentResolution;}
    public void setCurrentResolution(Resolution resolution){currentResolution = resolution;}

    // ----- FULLSCREEN -----
    public boolean isFullscreen(){return fullscreen;}
    public void setFullscreen(boolean bool){fullscreen = bool;}
}
