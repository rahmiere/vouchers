package org.killjoy.vouchers.inject;

import cloud.commandframework.CommandManager;
import cloud.commandframework.bukkit.CloudBukkitCapabilities;
import cloud.commandframework.execution.CommandExecutionCoordinator;
import cloud.commandframework.paper.PaperCommandManager;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import org.bukkit.command.CommandSender;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.framework.qual.DefaultQualifier;
import org.killjoy.vouchers.Vouchers;

import java.util.function.Function;

@DefaultQualifier(NonNull.class)
public final class CommandModule extends AbstractModule {

    @Provides
    @Singleton
    public CommandManager<CommandSender> provideCommandManager(Vouchers plugin) {
        try {
            CommandManager<CommandSender> manager = new PaperCommandManager<>(
                    plugin,
                    CommandExecutionCoordinator.simpleCoordinator(),
                    Function.identity(),
                    Function.identity()
            );

            if (manager.hasCapability(CloudBukkitCapabilities.ASYNCHRONOUS_COMPLETION)) {
                ((PaperCommandManager<?>) manager).registerAsynchronousCompletions();
            }

            return manager;
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize PaperCommandManager", e);
        }
    }
}
