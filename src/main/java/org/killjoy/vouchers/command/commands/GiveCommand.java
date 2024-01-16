package org.killjoy.vouchers.command.commands;

import cloud.commandframework.bukkit.parsers.PlayerArgument;
import cloud.commandframework.context.CommandContext;
import cloud.commandframework.meta.CommandMeta;
import com.google.inject.Inject;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.framework.qual.DefaultQualifier;
import org.killjoy.vouchers.command.BaseCommand;
import org.killjoy.vouchers.command.Commands;
import org.killjoy.vouchers.command.argument.factory.CloudArgumentFactory;
import org.killjoy.vouchers.language.LangKey;
import org.killjoy.vouchers.language.Language;
import org.killjoy.vouchers.util.Permissions;
import org.killjoy.vouchers.voucher.Voucher;

import java.util.Map;

@DefaultQualifier(NonNull.class)
public final class GiveCommand extends BaseCommand {

    private final CloudArgumentFactory argumentFactory;
    private final Language language;

    @Inject
    private GiveCommand(Commands commands, CloudArgumentFactory argumentFactory, Language language) {
        super(commands);
        this.argumentFactory = argumentFactory;
        this.language = language;
    }

    @Override
    public void register() {
        this.commands.subcommand(builder -> builder.literal("give")
                .meta(CommandMeta.DESCRIPTION, "Gives a player a voucher")
                .permission(Permissions.GIVE_COMMAND)
                .argument(PlayerArgument.of("target"))
                .argument(argumentFactory.voucher("voucher"))
                .handler(this::execute)
        );
    }

    private void execute(final CommandContext<CommandSender> context) {
        Player target = context.get("target");
        Voucher voucher = context.get("voucher");

        // TODO: amount

        final ItemStack item = voucher.toItemStack();

        target.getInventory().addItem(item);

        Map<String, Object> placeholders = Map.of(
                "name", voucher.getName(),
                "target", target.getName()
        );

        context.getSender().sendMessage(language.get(LangKey.GIVE, placeholders));
        target.sendMessage(language.get(LangKey.RECEIVED, placeholders));
    }
}
