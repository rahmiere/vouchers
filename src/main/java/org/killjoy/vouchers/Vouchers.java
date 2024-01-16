package org.killjoy.vouchers;

import com.google.inject.Guice;
import com.google.inject.Injector;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.jetbrains.annotations.NotNull;
import org.killjoy.vouchers.inject.PluginModule;

public final class Vouchers extends JavaPlugin {

    @MonotonicNonNull
    private Injector injector;

    @Override
    public void onLoad() {
        try {
            this.injector = Guice.createInjector(new PluginModule(this));
        } catch (final Exception ex) {
            this.getSLF4JLogger().error("An error occurred while creating the Guice injector.");
            this.getSLF4JLogger().error("Please report this stacktrace to the developer: ", ex);
        }
    }

    @Override
    public void onEnable() {
        getSLF4JLogger().info("Enabled!");
    }
}