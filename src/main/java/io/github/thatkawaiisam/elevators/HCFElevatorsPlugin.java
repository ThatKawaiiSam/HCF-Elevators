package io.github.thatkawaiisam.elevators;

import io.github.plugintemplate.PluginTemplate;
import io.github.plugintemplate.handler.Handler;
import io.github.plugintemplate.handler.impl.ModuleHandler;
import lombok.Getter;

import java.util.ArrayList;

public class HCFElevatorsPlugin extends PluginTemplate {

    @Getter private static HCFElevatorsPlugin instance;

    @Override
    public void onEnable() {
        instance = this;

        if (getHandlers() == null) {
            setHandlers(new ArrayList<>());
            getHandlers().add(new ModuleHandler("io.github.thatkawaiisam.elevators.modules", this,false));
        }

        getHandlers().forEach(Handler::enable);
    }

    @Override
    public void onDisable() {
        getHandlers().forEach(Handler::disable);
    }
}
