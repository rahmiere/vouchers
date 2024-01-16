package org.killjoy.vouchers.command;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.framework.qual.DefaultQualifier;

@DefaultQualifier(NonNull.class)
public abstract class BaseCommand {

    protected final Commands commands;

    protected BaseCommand(final Commands commands) {
        this.commands = commands;
    }

    public abstract void register();
}
