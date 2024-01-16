package org.killjoy.vouchers.inject;

import com.google.inject.AbstractModule;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.framework.qual.DefaultQualifier;
import org.killjoy.vouchers.Vouchers;

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
}
