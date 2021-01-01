package me.marcarrots.speller;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.logging.Level;

public final class Speller extends JavaPlugin {

    public HashMap<Character, Letter> getLetterMap() {
        return letterMap;
    }

    private HashMap<Character, Letter> letterMap = new HashMap<>();

    private void registerLetters() {
        letterMap.put(' ', new Letter(' ', "00000"));
        letterMap.put('A', new Letter('A', "55757"));
        letterMap.put('B', new Letter('B', "75657"));
        letterMap.put('C', new Letter('C', "74447"));
        letterMap.put('D', new Letter('D', "65556"));
        letterMap.put('E', new Letter('E', "74647"));
        letterMap.put('F', new Letter('F', "44647"));
        letterMap.put('G', new Letter('G', "F9B8F"));
        letterMap.put('H', new Letter('H', "55755"));
        letterMap.put('I', new Letter('I', "72227"));
        letterMap.put('J', new Letter('J', "25111"));
        letterMap.put('K', new Letter('K', "55655"));
        letterMap.put('L', new Letter('L', "74444"));
        letterMap.put('M', new Letter('M', "HHLRH"));
        letterMap.put('N', new Letter('N', "99BD9"));
        letterMap.put('O', new Letter('O', "75557"));
        letterMap.put('P', new Letter('P', "44757"));
        letterMap.put('Q', new Letter('Q', "36557"));
        letterMap.put('R', new Letter('R', "55656"));
        letterMap.put('S', new Letter('S', "71747"));
        letterMap.put('T', new Letter('T', "22227"));
        letterMap.put('U', new Letter('U', "75555"));
        letterMap.put('V', new Letter('V', "25555"));
        letterMap.put('W', new Letter('W', "VLLHH"));
        letterMap.put('X', new Letter('X', "52255"));
        letterMap.put('Y', new Letter('Y', "22755"));
        letterMap.put('Z', new Letter('Z', "74217"));
        letterMap.put('.', new Letter('.', "20000"));
        letterMap.put('!', new Letter('!', "20222"));
        letterMap.put(':', new Letter(':', "02020"));
    }

    @Override
    public void onEnable() {
        Bukkit.getLogger().log(Level.INFO, "Speller plugin starting...");
        registerLetters();
        getCommand("spell").setExecutor(new MainCmd(this));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
