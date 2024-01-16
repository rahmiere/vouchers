package org.killjoy.vouchers.inject;

import com.google.inject.AbstractModule;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.framework.qual.DefaultQualifier;
import org.killjoy.vouchers.voucher.VoucherManager;
import org.killjoy.vouchers.voucher.VoucherRegistry;

@DefaultQualifier(NonNull.class)
public final class SingletonModule extends AbstractModule {

    @Override
    protected void configure() {
        this.bind(VoucherRegistry.class).asEagerSingleton();
        this.bind(VoucherManager.class).asEagerSingleton();
    }
}
