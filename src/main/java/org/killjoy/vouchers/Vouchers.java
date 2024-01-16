package org.killjoy.vouchers;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.incendo.interfaces.paper.PaperInterfaceListeners;
import org.killjoy.vouchers.inject.MenuModule;
import org.killjoy.vouchers.inject.PluginModule;
import org.killjoy.vouchers.inject.SingletonModule;
import org.killjoy.vouchers.menu.MenuFactory;
import org.killjoy.vouchers.voucher.VoucherManager;
import org.killjoy.vouchers.voucher.VoucherRegistry;
import org.spongepowered.configurate.ConfigurateException;

public final class Vouchers extends JavaPlugin implements Listener {

    @MonotonicNonNull
    private Injector injector;

    @Override
    public void onLoad() {
        try {
            this.injector = Guice.createInjector(
                    new PluginModule(this),
                    new SingletonModule(),
                    new MenuModule()
            );
        } catch (final Exception ex) {
            getSLF4JLogger().error("An error occurred while creating the Guice injector.");
            getSLF4JLogger().error("Please report this stacktrace to the developer: ", ex);
        }
    }

    @Override
    public void onEnable() {
        PaperInterfaceListeners.install(this);

        VoucherManager manager = injector.getInstance(VoucherManager.class);

        try {
            manager.load();
        } catch (final ConfigurateException ex) {
            getSLF4JLogger().error("Something went wrong while loading the vouchers from file.");
            getSLF4JLogger().error("Please report this stacktrace to the developer: ", ex);
        }

        VoucherRegistry registry = injector.getInstance(VoucherRegistry.class);
        getSLF4JLogger().info(String.format("Loaded %s voucher(s) from file.", registry.size()));

        getServer().getPluginManager().registerEvents(this, this);
    }
}