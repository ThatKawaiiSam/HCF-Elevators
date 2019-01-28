package io.github.thatkawaiisam.elevators.modules.carpet;

import io.github.plugintemplate.module.Module;
import io.github.thatkawaiisam.elevators.ElevatorDirection;
import io.github.thatkawaiisam.elevators.ElevatorUtility;
import io.github.thatkawaiisam.elevators.HCFElevatorsPlugin;
import io.github.thatkawaiisam.utils.MessageUtility;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class CarpetModule extends Module {

    public CarpetModule() {
        super("Carpet", HCFElevatorsPlugin.getInstance(), false);
    }

    @Override
    public void onEnable() {
        getListeners().add(new Listener() {
            //Elevator Sneak
            @EventHandler
            public void onSneak(PlayerToggleSneakEvent event) {
                final Player player = event.getPlayer();

                if (!player.isSneaking()) {
                    return;
                }

                Location floorLocation = player.getLocation();
                Block floorBlock = floorLocation.getBlock();

                if (floorBlock.getType() != Material.CARPET) {
                    return;
                }

                ElevatorDirection direction = null;

                //Up
                if (floorBlock.getData() == (byte) 13) {
                    direction = ElevatorDirection.UP;
                }

                //Down
                if (floorBlock.getData() == (byte) 14) {
                    direction = ElevatorDirection.DOWN;
                }

                if (direction == null) {
                    return;
                }

                Location teleportLocation = ElevatorUtility.findValidLocation(player.getLocation(), direction);
                if (teleportLocation != null) {
                    player.teleport(teleportLocation);
                } else {
                    player.sendMessage(MessageUtility.formatMessage("&cCould not find a valid location to teleport you to."));
                }
            }
        });
    }

    @Override
    public void onDisable() {

    }
}
