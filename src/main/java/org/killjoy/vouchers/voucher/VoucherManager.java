package org.killjoy.vouchers.voucher;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.framework.qual.DefaultQualifier;
import org.killjoy.vouchers.Vouchers;
import org.slf4j.Logger;
import org.spongepowered.configurate.BasicConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.gson.GsonConfigurationLoader;

import java.nio.file.Files;
import java.nio.file.Path;

@DefaultQualifier(NonNull.class)
public final class VoucherManager {

    private final Vouchers plugin;
    private final VoucherRegistry registry;

    private final GsonConfigurationLoader loader;
    private final Logger logger;

    @Inject
    public VoucherManager(@Named("dataFolder") Path dataFolder, Vouchers plugin, VoucherRegistry registry, Logger logger) {
        this.plugin = plugin;
        this.registry = registry;
        this.logger = logger;

        this.loader = build(dataFolder.resolve("vouchers.json"));
    }

    public void load() throws ConfigurateException {
        var root = loader.load();

        for (var entry : root.childrenMap().entrySet()) {
            BasicConfigurationNode node = entry.getValue();

            @Nullable Voucher voucher = node.get(Voucher.class);

            if (voucher != null) {
                registry.register(voucher);
            }
        }
    }

    public void save() {
        for (Voucher voucher : registry.all()) {
            if (voucher.isDirty()) {
                this.save(voucher);
            }
        }
    }

    public void save(Voucher voucher) {
        try {
            this.saveVoucher(voucher);
            voucher.setDirty(false);
        } catch (ConfigurateException ex) {
            logger.error(String.format("Failed to save %s", voucher.getKey()), ex);
        }
    }

    private void saveVoucher(Voucher voucher) throws ConfigurateException {
        var root = loader.load();

        BasicConfigurationNode node = root.node(voucher.getKey());
        node.set(Voucher.class, voucher);

        loader.save(root);
    }

    private GsonConfigurationLoader build(final Path source) {
        if (Files.notExists(source)) {
            plugin.saveResource("vouchers.json", false);
        }
        return GsonConfigurationLoader.builder().path(source).build();
    }
}
