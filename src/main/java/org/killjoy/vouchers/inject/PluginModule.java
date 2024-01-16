package org.killjoy.vouchers.inject;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Named;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.framework.qual.DefaultQualifier;
import org.killjoy.vouchers.Vouchers;
import org.slf4j.Logger;

import java.nio.file.Path;

@DefaultQualifier(NonNull.class)
public class PluginModule extends AbstractModule {

    private final Vouchers plugin;

    public PluginModule(final Vouchers plugin) {
        this.plugin = plugin;
    }

    @Override
    protected void configure() {
        this.bind(Vouchers.class).toInstance(this.plugin);
    }

    @Provides
    @Named("dataFolder")
    public Path provideDataFolder() {
        return this.plugin.getDataFolder().toPath();
    }

    @Provides
    public Logger provideSLF4JLogger() {
        return this.plugin.getSLF4JLogger();
    }
}
