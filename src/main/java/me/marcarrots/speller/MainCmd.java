package me.marcarrots.speller;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class MainCmd implements TabExecutor {

    private final Speller speller;

    public MainCmd(Speller speller) {
        this.speller = speller;
    }


    // 0 = south
    // 1 = west
    // 2 = north
    // 3 = east
    public Direction getDirection(float yaw) {
        double rotation = (yaw - 180) % 360;
        if (rotation < 0) {
            rotation += 360.0;
        }
        if (0 <= rotation && rotation < 22.5) {
            return Direction.NORTH;
        } else if (67.5 <= rotation && rotation < 112.5) {
            return Direction.EAST;
        } else if (157.5 <= rotation && rotation < 202.5) {
            return Direction.SOUTH;
        } else if (247.5 <= rotation && rotation < 292.5) {
            return Direction.WEST;
        } else if (337.5 <= rotation && rotation < 360.0) {
            return Direction.NORTH;
        } else {
            return Direction.INVALID;
        }
    }

    private Task setBlock(int X, int Y, int Z, int offset, Direction direction, Material material, World world) {
        Task task = new Task(world);
        task.setMaterial(material);
        Block block;
        switch (direction) {
            case NORTH:
                block = world.getBlockAt(X, Y, Z - offset);
                task.setLocation(X, Y, Z - offset);
                break;
            case EAST:
                block = world.getBlockAt(X + offset, Y, Z);
                task.setLocation(X + offset, Y, Z);
                break;
            case SOUTH:
                block = world.getBlockAt(X, Y, Z + offset);
                task.setLocation(X, Y, Z + offset);
                break;
            case WEST:
                block = world.getBlockAt(X - offset, Y, Z);
                task.setLocation(X - offset, Y, Z);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + direction);
        }
        task.setMaterialOld(block.getType());
        block.setType(material);
        return task;

    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender;

        World world = player.getWorld();
        Location loc = player.getLocation();
        ItemStack handItem = player.getInventory().getItemInMainHand();
        int locX = loc.getBlockX();
        int locY = loc.getBlockY();
        int locZ = loc.getBlockZ();
        Direction direction = getDirection(loc.getYaw());

        if (args.length == 0) {
            player.sendMessage("Need more args.");
            return false;
        }

        if (direction == Direction.INVALID) {
            player.sendMessage("Must be facing North, East, South, or West");
            return false;
        }

        if (handItem.getData() == null || !handItem.getData().getItemType().isBlock()) {
            player.sendMessage("You must be holding a block to set as the material.");
            return false;
        }

        String argsString = String.join(" ", args).toUpperCase();
        player.sendMessage(String.format("%sPrinting: %s%s", ChatColor.YELLOW, ChatColor.GOLD, argsString));
        ArrayList<Task> tasks = new ArrayList<>();

        for (int i = 0; i < argsString.length(); i++) {
            char letter = argsString.charAt(i);
            String pattern = null;
            try {
                pattern = speller.getLetterMap().get(letter).getPattern();
            } catch (Exception e) {
                player.sendMessage("Unsupported character: " + argsString.charAt(i));
                return false;
            }
            int heightLocal = locY;


            for (int j = 0; j < pattern.length(); j++) {
                switch (pattern.charAt(j)) {
                    case '0':
                        break;
                    case '1': // 001
                        tasks.add(setBlock(locX, heightLocal, locZ, 3, direction, handItem.getType(), world));
                        break;
                    case '2': // 010
                        tasks.add(setBlock(locX, heightLocal, locZ, 2, direction, handItem.getType(), world));
                        break;
                    case '3': // 011
                        tasks.add(setBlock(locX, heightLocal, locZ, 3, direction, handItem.getType(), world));
                        tasks.add(setBlock(locX, heightLocal, locZ, 2, direction, handItem.getType(), world));
                        break;
                    case '4': // 100
                    case '8': // 1000
                        tasks.add(setBlock(locX, heightLocal, locZ, 1, direction, handItem.getType(), world));
                        break;
                    case '5': // 101
                        tasks.add(setBlock(locX, heightLocal, locZ, 3, direction, handItem.getType(), world));
                        tasks.add(setBlock(locX, heightLocal, locZ, 1, direction, handItem.getType(), world));
                        break;
                    case '6': // 110
                        tasks.add(setBlock(locX, heightLocal, locZ, 2, direction, handItem.getType(), world));
                        tasks.add(setBlock(locX, heightLocal, locZ, 1, direction, handItem.getType(), world));
                        break;
                    case '7': // 111
                        tasks.add(setBlock(locX, heightLocal, locZ, 3, direction, handItem.getType(), world));
                        tasks.add(setBlock(locX, heightLocal, locZ, 2, direction, handItem.getType(), world));
                        tasks.add(setBlock(locX, heightLocal, locZ, 1, direction, handItem.getType(), world));
                        break;
                    case '9': // 1001
                        tasks.add(setBlock(locX, heightLocal, locZ, 4, direction, handItem.getType(), world));
                        tasks.add(setBlock(locX, heightLocal, locZ, 1, direction, handItem.getType(), world));
                        break;
                    case 'B': // 1011
                        tasks.add(setBlock(locX, heightLocal, locZ, 4, direction, handItem.getType(), world));
                        tasks.add(setBlock(locX, heightLocal, locZ, 3, direction, handItem.getType(), world));
                        tasks.add(setBlock(locX, heightLocal, locZ, 1, direction, handItem.getType(), world));
                        break;
                    case 'D': // 1101
                        tasks.add(setBlock(locX, heightLocal, locZ, 4, direction, handItem.getType(), world));
                        tasks.add(setBlock(locX, heightLocal, locZ, 2, direction, handItem.getType(), world));
                        tasks.add(setBlock(locX, heightLocal, locZ, 1, direction, handItem.getType(), world));
                        break;
                    case 'F': // 1111
                        tasks.add(setBlock(locX, heightLocal, locZ, 4, direction, handItem.getType(), world));
                        tasks.add(setBlock(locX, heightLocal, locZ, 3, direction, handItem.getType(), world));
                        tasks.add(setBlock(locX, heightLocal, locZ, 2, direction, handItem.getType(), world));
                        tasks.add(setBlock(locX, heightLocal, locZ, 1, direction, handItem.getType(), world));
                        break;
                    case 'H': // 10001
                        tasks.add(setBlock(locX, heightLocal, locZ, 5, direction, handItem.getType(), world));
                        tasks.add(setBlock(locX, heightLocal, locZ, 1, direction, handItem.getType(), world));
                        break;
                    case 'L': // 10101
                        tasks.add(setBlock(locX, heightLocal, locZ, 5, direction, handItem.getType(), world));
                        tasks.add(setBlock(locX, heightLocal, locZ, 3, direction, handItem.getType(), world));
                        tasks.add(setBlock(locX, heightLocal, locZ, 1, direction, handItem.getType(), world));
                        break;
                    case 'R': // 11011
                        tasks.add(setBlock(locX, heightLocal, locZ, 5, direction, handItem.getType(), world));
                        tasks.add(setBlock(locX, heightLocal, locZ, 4, direction, handItem.getType(), world));
                        tasks.add(setBlock(locX, heightLocal, locZ, 2, direction, handItem.getType(), world));
                        tasks.add(setBlock(locX, heightLocal, locZ, 1, direction, handItem.getType(), world));
                        break;
                    case 'V': // 11111
                        tasks.add(setBlock(locX, heightLocal, locZ, 5, direction, handItem.getType(), world));
                        tasks.add(setBlock(locX, heightLocal, locZ, 4, direction, handItem.getType(), world));
                        tasks.add(setBlock(locX, heightLocal, locZ, 3, direction, handItem.getType(), world));
                        tasks.add(setBlock(locX, heightLocal, locZ, 2, direction, handItem.getType(), world));
                        tasks.add(setBlock(locX, heightLocal, locZ, 1, direction, handItem.getType(), world));
                        break;
                    default:
                        Bukkit.getLogger().log(Level.SEVERE, "Error " + i);
                        break;
                }
                heightLocal += 1;

            }


            int offset;

            if ("MW".contains(Character.toString(letter))) {
                offset = 6;
            } else if ("GN".contains(Character.toString(letter))) {
                offset = 5;
            }
            else {
                offset = 4;
            }

            switch (direction) {
                case NORTH:
                    locZ -= offset;
                    break;
                case EAST:
                    locX += offset;
                    break;
                case SOUTH:
                    locZ += offset;
                    break;
                case WEST:
                    locX -= offset;
                    break;
            }
        }
        speller.addUndo(tasks, player.getUniqueId());

        return false;

    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return null;
    }

}
