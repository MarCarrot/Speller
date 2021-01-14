package me.marcarrots.speller;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
import java.util.UUID;
import java.util.logging.Level;

public final class Speller extends JavaPlugin {



    public HashMap<Character, Letter> getLetterMap() {
        return letterMap;
    }

    private HashMap<Character, Letter> letterMap = new HashMap<>();

    public Stack<ArrayList<Task> > getUndoStack(UUID uuid) {
        return UndoStack.get(uuid);
    }

    public void addUndo(ArrayList<Task> material, UUID uuid) {
        if (!UndoStack.containsKey(uuid)) {
            UndoStack.put(uuid, new Stack<>());
        }
        UndoStack.get(uuid).push(material);
    }

    public Stack<ArrayList<Task> > getRedoStack(UUID uuid) {
        return RedoStack.get(uuid);
    }

    public void addRedo(ArrayList<Task> material, UUID uuid) {
        if (!RedoStack.containsKey(uuid)) {
            RedoStack.put(uuid, new Stack<>());
        }
        RedoStack.get(uuid).push(material);
    }


    private HashMap<UUID, Stack<ArrayList<Task> >> UndoStack;
    private HashMap<UUID, Stack<ArrayList<Task> >> RedoStack;

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
        letterMap.put('^', new Letter('^', "00520"));
        letterMap.put('*', new Letter('*', "05250"));
        letterMap.put('(', new Letter('(', "24442"));
        letterMap.put(')', new Letter(')', "21112"));
        letterMap.put('_', new Letter('_', "70000"));
        letterMap.put('-', new Letter('-', "00700"));


        letterMap.put('1', new Letter('1', "72262"));
        letterMap.put('2', new Letter('2', "74717"));
        letterMap.put('3', new Letter('3', "71317"));
        letterMap.put('4', new Letter('4', "11755"));
        letterMap.put('5', new Letter('5', "71747"));
        letterMap.put('6', new Letter('6', "75747"));
        letterMap.put('7', new Letter('7', "11117"));
        letterMap.put('8', new Letter('8', "75757"));
        letterMap.put('9', new Letter('9', "11757"));
        letterMap.put('0', new Letter('0', "75557"));

    }

    public static void fillBlocks(ArrayList<Task> tasks, boolean doNewMaterial) {
        for (Task t: tasks) {
            Block block = t.getWorld().getBlockAt(t.getX(), t.getY(), t.getZ());
            final Material materialOld = doNewMaterial ? t.getMaterialNew() : t.getMaterialOld();
            t.setMaterialOld(block.getType());
            block.setType(materialOld);
        }
    }

    @Override
    public void onEnable() {
        Bukkit.getLogger().log(Level.INFO, "Speller plugin starting...");
        registerLetters();
        UndoStack = new HashMap<>();
        RedoStack = new HashMap<>();
        getCommand("spell").setExecutor(new MainCmd(this));
        getCommand("spell-undo").setExecutor(new Undo(this));
        getCommand("spell-redo").setExecutor(new Redo(this));

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
