package me.marcarrots.speller;

import org.bukkit.Material;
import org.bukkit.World;

public class Task {

    public int getX() {
        return X;
    }

    private int X;

    public int getY() {
        return Y;
    }

    private int Y;

    public int getZ() {
        return Z;
    }

    private int Z;

    public World getWorld() {
        return world;
    }

    private World world;

    private Material materialNew;

    public Task(World world) {
        this.world = world;
    }

    public Material getMaterialOld() {
        return materialOld;
    }

    public Material getMaterialNew() {
        return materialNew;
    }

    private Material materialOld;

    public void setLocation(int X, int Y, int Z) {
        this.X = X;
        this.Y = Y;
        this.Z = Z;
    }

    public void setMaterialNew(Material material) {
        this.materialNew = material;
    }

    public void setMaterialOld(Material materialOld) {
        this.materialOld = materialOld;
    }


}
