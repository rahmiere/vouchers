package org.killjoy.vouchers.command.commands;

import cloud.commandframework.context.CommandContext;
import cloud.commandframework.meta.CommandMeta;
import com.google.inject.Inject;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.killjoy.vouchers.command.BaseCommand;
import org.killjoy.vouchers.command.Commands;

public class CreateCommand extends BaseCommand {

    @Inject
    protected CreateCommand(Commands commands) {
        super(commands);
    }

    @Override
    public void register() {
        this.commands.subcommand(builder -> builder.literal("create")
                .meta(CommandMeta.DESCRIPTION, "Creates a new voucher from the item in your hand.")
                .senderType(Player.class)
                .handler(this::execute)
        );
    }

    private void execute(final CommandContext<CommandSender> context) {
        context.getSender().sendMessage(Component.text("Hello World!"));
    }
}
