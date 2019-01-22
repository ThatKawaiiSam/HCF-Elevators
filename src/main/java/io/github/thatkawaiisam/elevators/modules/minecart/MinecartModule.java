package io.github.thatkawaiisam.elevators.modules.minecart;

import io.github.plugintemplate.module.Module;
import io.github.thatkawaiisam.elevators.ElevatorDirection;
import io.github.thatkawaiisam.elevators.ElevatorUtility;
import io.github.thatkawaiisam.elevators.HCFElevatorsPlugin;
import io.github.thatkawaiisam.utils.MessageUtility;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class MinecartModule extends Module {

    public MinecartModule() {
        super("Minecart", HCFElevatorsPlugin.getInstance(), false);
    }

    @Override
    public void onEnable() {
        getListeners().add(new Listener() {
            @EventHandler
            public void onMinecartExit(VehicleExitEvent event){
                if(event.getVehicle() instanceof Minecart && event.getExited() instanceof Player){
                    final Player player = (Player) event.getExited();
                    final Location location = event.getVehicle().getLocation();

                    if(location.clone().getBlock().getType() != Material.FENCE_GATE) return;

                    new BukkitRunnable(){
                        @Override
                        public void run(){
                            Location teleportLocation = ElevatorUtility.findValidLocation(location, ElevatorDirection.UP);
                            if (teleportLocation != null) {
                                player.teleport(teleportLocation);
                            } else {
                                player.sendMessage(MessageUtility.formatMessage("&cCould not find a valid location to teleport you to."));
                            }
                        }
                    }.runTaskLater(HCFElevatorsPlugin.getInstance(), 1);
                }
            }
        });
    }

    @Override
    public void onDisable() {

    }
}
