package org.killjoy.vouchers.command;

import cloud.commandframework.Command;
import cloud.commandframework.CommandManager;
import cloud.commandframework.meta.CommandMeta;
import com.google.inject.Inject;
import com.google.inject.Injector;
import org.bukkit.command.CommandSender;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.framework.qual.DefaultQualifier;
import org.killjoy.vouchers.command.commands.CreateCommand;
import org.killjoy.vouchers.command.commands.EditCommand;
import org.killjoy.vouchers.command.commands.GiveCommand;

import java.util.List;
import java.util.function.UnaryOperator;

@DefaultQualifier(NonNull.class)
public final class Commands {

    private final Injector injector;
    private final CommandManager<CommandSender> commandManager;

    @Inject
    public Commands(Injector injector, CommandManager<CommandSender> commandManager) {
        this.injector = injector;
        this.commandManager = commandManager;
    }

    public void registerCommands() {
        final var commands = List.of(
                CreateCommand.class,
                EditCommand.class,
                GiveCommand.class
        );

        for (final var command : commands) {
            injector.getInstance(command).register();
        }
    }

    public void subcommand(UnaryOperator<Command.Builder<CommandSender>> modifier) {
        this.commandManager.command(modifier.apply(this.root()));
    }

    public Command.Builder<CommandSender> root() {
        return this.commandManager.commandBuilder("vouchers")
                .meta(CommandMeta.DESCRIPTION, "Base plugin command");
    }
}
