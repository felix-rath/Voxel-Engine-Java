package de.rathfelix;

import de.rathfelix.engine.GameEngine;
import de.rathfelix.game.logic.*;
import de.rathfelix.game.language.ILanguage;
import de.rathfelix.game.language.Ger;
import de.rathfelix.engine.config.Config;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static GameEngine engine; // One and only main window.

    private static ILanguage lang; // Current language.

    public Main() {
        createGame();
        System.out.println(lang.getTitle() + lang.getConsoleStart()); // Console start message.
    }

    public static void main(String[] args) {
        Main mainC = new Main();
    }

    private static void setLanguage(ILanguage newLanguage) {
        lang = newLanguage;
    }

    private static void  createGame() {
        try {
            boolean vSync = true;
            setLanguage(new Ger());

            loadLogic();
            engine = new GameEngine(lang.getTitle(), Config.getVideoConfig().currentResolution, vSync); // Create Main Window
            engine.start();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    private static void loadLogic() {
        new VoxelGame();
    }
}