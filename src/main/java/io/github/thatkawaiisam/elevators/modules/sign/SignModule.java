package io.github.thatkawaiisam.elevators.modules.sign;

import io.github.plugintemplate.module.Module;
import io.github.thatkawaiisam.elevators.ElevatorDirection;
import io.github.thatkawaiisam.elevators.ElevatorUtility;
import io.github.thatkawaiisam.elevators.HCFElevatorsPlugin;
import io.github.thatkawaiisam.utils.MessageUtility;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Arrays;
import java.util.List;

public class SignModule extends Module {

    private static final List<String> signOperators = Arrays.asList("up", "down");
    private static final List<Material> signMaterials = Arrays.asList(Material.SIGN_POST, Material.WALL_SIGN);

    public SignModule() {
        super("Sign", HCFElevatorsPlugin.getInstance(), false);
    }

    @Override
    public void onEnable() {
        getListeners().add(new Listener() {
            //Sign Interact
            @EventHandler
            public void onSignInteract(PlayerInteractEvent event) {
                if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
                    return;
                }
                if (!signMaterials.contains(event.getClickedBlock().getType())) {
                    return;
                }
                final BlockState blockState = event.getClickedBlock().getState();

                if (!(blockState instanceof Sign)) {
                    return;
                }

                Sign sign = (Sign) blockState;

                if (!sign.getLine(0).equalsIgnoreCase("&9[Elevators]")) {
                    return;
                }

                Location teleportLocation = ElevatorUtility.findValidLocation(event.getPlayer().getLocation(), ElevatorDirection.valueOf(sign.getLine(1)));

                if (teleportLocation != null) {
                    event.getPlayer().teleport(teleportLocation);
                } else {
                    event.getPlayer().sendMessage(MessageUtility.formatMessage("&cCould not find a valid location to teleport you to."));
                }
            }

            //Sign Format
            @EventHandler
            public void onSignChange(SignChangeEvent event) {
                if (!event.getLine(0).equalsIgnoreCase("[Elevator]")) {
                    return;
                }
                if (event.getLine(1) == null
                        || !signOperators.contains(event.getLine(1).toLowerCase())) {
                    return;
                }

                event.setLine(0, "&9[Elevators]");
                event.setLine(1, "&0" + MessageUtility.capitalizeFirstLetter(event.getLine(1)));
                event.getPlayer().sendMessage(MessageUtility.formatMessage("&aSuccessfully created sign elevator!"));
            }
        });
    }

    @Override
    public void onDisable() {

    }
}
