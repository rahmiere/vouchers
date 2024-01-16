package org.killjoy.vouchers;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.killjoy.vouchers.inject.PluginModule;
import org.killjoy.vouchers.inject.SingletonModule;
import org.killjoy.vouchers.voucher.VoucherManager;
import org.killjoy.vouchers.voucher.VoucherRegistry;
import org.spongepowered.configurate.ConfigurateException;

public final class Vouchers extends JavaPlugin {

    @MonotonicNonNull
    private Injector injector;

    @Override
    public void onLoad() {
        try {
            this.injector = Guice.createInjector(new PluginModule(this),
                    new SingletonModule()
            );
        } catch (final Exception ex) {
            getSLF4JLogger().error("An error occurred while creating the Guice injector.");
            getSLF4JLogger().error("Please report this stacktrace to the developer: ", ex);
        }
    }

    @Override
    public void onEnable() {
        VoucherManager voucherManager = this.injector.getInstance(VoucherManager.class);

        try {
            voucherManager.load();
        } catch (final ConfigurateException ex) {
            getSLF4JLogger().error("Something went wrong while loading the vouchers from file.");
            getSLF4JLogger().error("Please report this stacktrace to the developer: ", ex);
        }

        VoucherRegistry voucherRegistry = this.injector.getInstance(VoucherRegistry.class);
        getSLF4JLogger().info(String.format("Loaded %s voucher(s) from file.", voucherRegistry.size()));
    }
}