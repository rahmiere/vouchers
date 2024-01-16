package org.killjoy.vouchers.command.commands;

import cloud.commandframework.context.CommandContext;
import cloud.commandframework.meta.CommandMeta;
import com.google.inject.Inject;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.framework.qual.DefaultQualifier;
import org.killjoy.vouchers.command.BaseCommand;
import org.killjoy.vouchers.command.Commands;
import org.killjoy.vouchers.command.argument.factory.CloudArgumentFactory;
import org.killjoy.vouchers.menu.MenuFactory;
import org.killjoy.vouchers.util.Permissions;

@DefaultQualifier(NonNull.class)
public final class EditCommand extends BaseCommand {

    private final CloudArgumentFactory argumentFactory;
    private final MenuFactory menuFactory;

    @Inject
    private EditCommand(Commands commands, CloudArgumentFactory argumentFactory, MenuFactory menuFactory) {
        super(commands);
        this.argumentFactory = argumentFactory;
        this.menuFactory = menuFactory;
    }

    @Override
    public void register() {
        this.commands.subcommand(builder -> builder.literal("edit")
                .meta(CommandMeta.DESCRIPTION, "Open the editor for a specific voucher")
                .permission(Permissions.EDIT_COMMAND)
                .senderType(Player.class)
                .argument(this.argumentFactory.voucher("voucher"))
                .handler(this::execute)
        );
    }

    private void execute(final CommandContext<CommandSender> context) {
        menuFactory.editMenu(context.get("voucher")).open((Player) context.getSender());
    }
}
