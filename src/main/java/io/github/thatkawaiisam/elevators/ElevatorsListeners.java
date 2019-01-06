package io.github.thatkawaiisam.elevators;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.List;

public class ElevatorsListeners implements Listener {

    private static final List<String> signOperators = Arrays.asList("up", "down");

    @EventHandler
    public void onSignChange(SignChangeEvent event) {
        if (!event.getLine(0).equalsIgnoreCase("[Elevator]")) {
            return;
        }
        if (event.getLine(1) == null
                || !signOperators.contains(event.getLine(1))) {
            return;
        }
        ElevatorDirection direction = ElevatorDirection.valueOf(event.getLine(1));
        Location teleportLocation = findValidLocation(event.getPlayer().getLocation(), direction);

        if (teleportLocation != null) {
            event.getPlayer().teleport(teleportLocation);
        } else {
            event.getPlayer().sendMessage("&cCould not find a valid location to teleport you to.");
        }
    }

    //Credit: Bageth
    @EventHandler
    public void onMinecartExit(VehicleExitEvent event){
        if(event.getVehicle() instanceof Minecart && event.getExited() instanceof Player){
            Player player = (Player) event.getExited();
            Location location = event.getVehicle().getLocation();

            if(location.clone().getBlock().getType() != Material.FENCE_GATE) return;

            new BukkitRunnable(){
                @Override
                public void run(){
                    Location teleportLocation = findValidLocation(location, ElevatorDirection.UP);
                    if (teleportLocation != null) {
                        player.teleport(teleportLocation);
                    } else {
                        player.sendMessage("&cCould not find a valid location to teleport you to.");
                    }
                }
            }.runTaskLater(HCFElevatorsPlugin.getInstance(), 1L);
        }
    }

    public static Location findValidLocation(final Location location, ElevatorDirection direction) {
        if (direction == ElevatorDirection.UP) {
            for (int yLevel = location.getBlockY(); yLevel < 256; yLevel++) {
                Material first = new Location(location.getWorld(), location.getX(), yLevel, location.getBlockZ()).getBlock().getType();
                Material second = new Location(location.getWorld(), location.getX(), (yLevel + 1), location.getBlockZ()).getBlock().getType();
                if (first == Material.AIR && second == Material.AIR) {
                    return new Location(location.getWorld(), location.getBlockX(), yLevel, location.getBlockZ());
                }
            }
            return null;
        } else {
            for (int yLevel = location.getBlockY(); yLevel > 0; yLevel--) {
                Material first = new Location(location.getWorld(), location.getX(), yLevel, location.getBlockZ()).getBlock().getType();
                Material second = new Location(location.getWorld(), location.getX(), (yLevel - 1), location.getBlockZ()).getBlock().getType();
                if (first == Material.AIR && second == Material.AIR) {
                    return new Location(location.getWorld(), location.getBlockX(), yLevel, location.getBlockZ());
                }
            }
            return null;
        }
    }
}
