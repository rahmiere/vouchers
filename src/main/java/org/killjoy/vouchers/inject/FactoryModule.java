package org.killjoy.vouchers.inject;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.framework.qual.DefaultQualifier;
import org.killjoy.vouchers.command.argument.factory.CloudArgumentFactory;
import org.killjoy.vouchers.menu.MenuFactory;

@DefaultQualifier(NonNull.class)
public final class FactoryModule extends AbstractModule {

    @Override
    protected void configure() {
        this.install(new FactoryModuleBuilder().build(CloudArgumentFactory.class));
        this.install(new FactoryModuleBuilder().build(MenuFactory.class));
    }
}
