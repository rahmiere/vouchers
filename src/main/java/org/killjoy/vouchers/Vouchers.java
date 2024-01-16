package org.killjoy.vouchers;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.incendo.interfaces.paper.PaperInterfaceListeners;
import org.killjoy.vouchers.command.Commands;
import org.killjoy.vouchers.inject.CommandModule;
import org.killjoy.vouchers.inject.FactoryModule;
import org.killjoy.vouchers.inject.PluginModule;
import org.killjoy.vouchers.inject.SingletonModule;
import org.killjoy.vouchers.listener.InteractListener;
import org.killjoy.vouchers.listener.RenameListener;
import org.killjoy.vouchers.voucher.VoucherManager;
import org.killjoy.vouchers.voucher.VoucherRegistry;
import org.spongepowered.configurate.ConfigurateException;

import java.util.List;

public final class Vouchers extends JavaPlugin implements Listener {

    @MonotonicNonNull
    private Injector injector;

    @Override
    public void onLoad() {
        try {
            this.injector = Guice.createInjector(
                    new PluginModule(this),
                    new SingletonModule(),
                    new CommandModule(),
                    new FactoryModule()
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

        Commands commands = injector.getInstance(Commands.class);
        commands.registerCommands();

        PluginManager pm = getServer().getPluginManager();

        final var listeners = List.of(
                InteractListener.class,
                RenameListener.class
        );

        for (final var listener : listeners) {
            pm.registerEvents(injector.getInstance(listener), this);
        }
    }

    @Override
    public void onDisable() {
        VoucherManager manager = injector.getInstance(VoucherManager.class);
        manager.save();
    }
}