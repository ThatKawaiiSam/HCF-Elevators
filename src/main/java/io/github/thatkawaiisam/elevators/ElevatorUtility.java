package io.github.thatkawaiisam.elevators;

import org.bukkit.Location;
import org.bukkit.Material;

public class ElevatorUtility {

    public static Location findValidLocation(final Location location, ElevatorDirection direction) {
        if (direction == ElevatorDirection.UP) {
            for (int yLevel = location.getBlockY(); yLevel < 256; yLevel++) {
                Material first = new Location(location.getWorld(), location.getX(), yLevel, location.getBlockZ()).getBlock().getType();
                Material second = new Location(location.getWorld(), location.getX(), (yLevel + 1), location.getBlockZ()).getBlock().getType();
                if (evalMaterials(first, second)) {
                    return new Location(location.getWorld(), location.getBlockX(), yLevel, location.getBlockZ());
                }
            }
            return null;
        } else {
            for (int yLevel = location.getBlockY(); yLevel > 0; yLevel--) {
                Material first = new Location(location.getWorld(), location.getX(), yLevel, location.getBlockZ()).getBlock().getType();
                Material second = new Location(location.getWorld(), location.getX(), (yLevel - 1), location.getBlockZ()).getBlock().getType();
                if (evalMaterials(first, second)) {
                    return new Location(location.getWorld(), location.getBlockX(), yLevel, location.getBlockZ());
                }
            }
            return null;
        }
    }

    private static boolean evalMaterials(Material first, Material second) {
        return first == Material.AIR && second == Material.AIR;
    }

}
