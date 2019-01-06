package io.github.thatkawaiisam.elevators;

import io.github.plugintemplate.PluginTemplate;
import io.github.plugintemplate.handler.Handler;
import io.github.plugintemplate.handler.impl.ListenerHandler;
import io.github.thatkawaiisam.configs.BukkitConfigHelper;
import lombok.Getter;

import java.util.ArrayList;

public class HCFElevatorsPlugin extends PluginTemplate {

    @Getter private static HCFElevatorsPlugin instance;

    private BukkitConfigHelper elevatorsConfig;

    @Override
    public void onEnable() {
        instance = this;

        elevatorsConfig = new BukkitConfigHelper(this, "config", getDataFolder().getAbsolutePath());

        if (getHandlers() == null) {
            setHandlers(new ArrayList<>());
            getHandlers().add(new ListenerHandler("io.github.thatkawaiisam.elevators", this));

        }

        getHandlers().forEach(Handler::enable);
    }

    @Override
    public void onDisable() {
        getHandlers().forEach(Handler::disable);
    }
}
