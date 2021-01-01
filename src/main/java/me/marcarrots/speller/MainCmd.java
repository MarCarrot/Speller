package me.marcarrots.speller;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

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

    private void setBlock(int X, int Y, int Z, int offset, Direction direction, Material material, World world) {
        switch (direction) {
            case NORTH:
                world.getBlockAt(X, Y, Z - offset).setType(material);
                break;
            case EAST:
                world.getBlockAt(X + offset, Y, Z).setType(material);
                break;
            case SOUTH:
                world.getBlockAt(X, Y, Z + offset).setType(material);
                break;
            case WEST:
                world.getBlockAt(X - offset, Y, Z).setType(material);
                break;
        }

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
        player.sendMessage("Direction: " + direction);

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
        player.sendMessage(argsString);
        for (int i = 0; i < argsString.length(); i++) {
            char letter = argsString.charAt(i);
            String pattern = null;
            try {
                pattern = speller.getLetterMap().get(letter).getPattern();
            } catch (Exception e) {
                e.printStackTrace();
                player.sendMessage("Unsupported character: " + argsString.charAt(i));
                return false;
            }
            int heightLocal = locY;
            for (int j = 0; j < pattern.length(); j++) {
                switch (pattern.charAt(j)) {
                    case '0':
                        break;
                    case '1': // 001
                        setBlock(locX, heightLocal, locZ, 3, direction, handItem.getData().getItemType(), world);
                        break;
                    case '2': // 010
                        setBlock(locX, heightLocal, locZ, 2, direction, handItem.getData().getItemType(), world);
                        break;
                    case '3': // 011
                        setBlock(locX, heightLocal, locZ, 3, direction, handItem.getData().getItemType(), world);
                        setBlock(locX, heightLocal, locZ, 2, direction, handItem.getData().getItemType(), world);
                        break;
                    case '4': // 100
                    case '8': // 1000
                        setBlock(locX, heightLocal, locZ, 1, direction, handItem.getData().getItemType(), world);
                        break;
                    case '5': // 101
                        setBlock(locX, heightLocal, locZ, 3, direction, handItem.getData().getItemType(), world);
                        setBlock(locX, heightLocal, locZ, 1, direction, handItem.getData().getItemType(), world);
                        break;
                    case '6': // 110
                        setBlock(locX, heightLocal, locZ, 2, direction, handItem.getData().getItemType(), world);
                        setBlock(locX, heightLocal, locZ, 1, direction, handItem.getData().getItemType(), world);
                        break;
                    case '7': // 111
                        setBlock(locX, heightLocal, locZ, 3, direction, handItem.getData().getItemType(), world);
                        setBlock(locX, heightLocal, locZ, 2, direction, handItem.getData().getItemType(), world);
                        setBlock(locX, heightLocal, locZ, 1, direction, handItem.getData().getItemType(), world);
                        break;
                    case '9': // 1001
                        setBlock(locX, heightLocal, locZ, 4, direction, handItem.getData().getItemType(), world);
                        setBlock(locX, heightLocal, locZ, 1, direction, handItem.getData().getItemType(), world);
                        break;
                    case 'B': // 1011
                        setBlock(locX, heightLocal, locZ, 4, direction, handItem.getData().getItemType(), world);
                        setBlock(locX, heightLocal, locZ, 3, direction, handItem.getData().getItemType(), world);
                        setBlock(locX, heightLocal, locZ, 1, direction, handItem.getData().getItemType(), world);
                        break;
                    case 'D': // 1101
                        setBlock(locX, heightLocal, locZ, 4, direction, handItem.getData().getItemType(), world);
                        setBlock(locX, heightLocal, locZ, 2, direction, handItem.getData().getItemType(), world);
                        setBlock(locX, heightLocal, locZ, 1, direction, handItem.getData().getItemType(), world);
                        break;
                    case 'F': // 1111
                        setBlock(locX, heightLocal, locZ, 4, direction, handItem.getData().getItemType(), world);
                        setBlock(locX, heightLocal, locZ, 3, direction, handItem.getData().getItemType(), world);
                        setBlock(locX, heightLocal, locZ, 2, direction, handItem.getData().getItemType(), world);
                        setBlock(locX, heightLocal, locZ, 1, direction, handItem.getData().getItemType(), world);
                        break;
                    case 'H': // 10001
                        setBlock(locX, heightLocal, locZ, 5, direction, handItem.getData().getItemType(), world);
                        setBlock(locX, heightLocal, locZ, 1, direction, handItem.getData().getItemType(), world);
                        break;
                    case 'L': // 10101
                        setBlock(locX, heightLocal, locZ, 5, direction, handItem.getData().getItemType(), world);
                        setBlock(locX, heightLocal, locZ, 3, direction, handItem.getData().getItemType(), world);
                        setBlock(locX, heightLocal, locZ, 1, direction, handItem.getData().getItemType(), world);
                        break;
                    case 'R': // 11011
                        setBlock(locX, heightLocal, locZ, 5, direction, handItem.getData().getItemType(), world);
                        setBlock(locX, heightLocal, locZ, 4, direction, handItem.getData().getItemType(), world);
                        setBlock(locX, heightLocal, locZ, 2, direction, handItem.getData().getItemType(), world);
                        setBlock(locX, heightLocal, locZ, 1, direction, handItem.getData().getItemType(), world);
                        break;
                    case 'V': // 11111
                        setBlock(locX, heightLocal, locZ, 5, direction, handItem.getData().getItemType(), world);
                        setBlock(locX, heightLocal, locZ, 4, direction, handItem.getData().getItemType(), world);
                        setBlock(locX, heightLocal, locZ, 3, direction, handItem.getData().getItemType(), world);
                        setBlock(locX, heightLocal, locZ, 2, direction, handItem.getData().getItemType(), world);
                        setBlock(locX, heightLocal, locZ, 1, direction, handItem.getData().getItemType(), world);
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
        return false;

    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return null;
    }

}
