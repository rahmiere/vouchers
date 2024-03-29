package org.killjoy.vouchers.command.commands;

import cloud.commandframework.arguments.standard.StringArgument;
import cloud.commandframework.context.CommandContext;
import cloud.commandframework.meta.CommandMeta;
import com.google.inject.Inject;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;
import org.killjoy.vouchers.Vouchers;
import org.killjoy.vouchers.command.BaseCommand;
import org.killjoy.vouchers.command.Commands;
import org.killjoy.vouchers.language.LangKey;
import org.killjoy.vouchers.language.Language;
import org.killjoy.vouchers.menu.MenuFactory;
import org.killjoy.vouchers.util.Permissions;
import org.killjoy.vouchers.voucher.Voucher;
import org.killjoy.vouchers.voucher.VoucherManager;
import org.killjoy.vouchers.voucher.VoucherRegistry;

public class CreateCommand extends BaseCommand {

    private final Vouchers plugin;

    private final Language language;

    private final VoucherRegistry registry;
    private final VoucherManager manager;

    private final MenuFactory menuFactory;

    @Inject
    protected CreateCommand(
            Commands commands,
            Language language,
            VoucherRegistry registry,
            VoucherManager manager,
            MenuFactory menuFactory,
            Vouchers plugin
    ) {
        super(commands);
        this.language = language;
        this.registry = registry;
        this.manager = manager;
        this.menuFactory = menuFactory;
        this.plugin = plugin;
    }

    @Override
    public void register() {
        this.commands.subcommand(builder -> builder.literal("create")
                .meta(CommandMeta.DESCRIPTION, "Creates a new voucher from the item in your hand.")
                .permission(Permissions.CREATE_COMMAND)
                .senderType(Player.class)
                .argument(StringArgument.of("key"))
                .handler(this::execute)
        );
    }

    private void execute(final CommandContext<CommandSender> context) {
        Player player = (Player) context.getSender();
        PlayerInventory inventory = player.getInventory();

        if (inventory.getItemInMainHand().getType() == Material.AIR) {
            player.sendMessage(language.get(LangKey.HOLD_ITEM));
            return;
        }

        String key = context.get("key");

        if (registry.get(key).isPresent()) {
            player.sendMessage(language.get(LangKey.ALREADY_EXISTS));
            return;
        }

        String type = inventory.getItemInMainHand().getType().name();
        Voucher voucher = new Voucher(key, type);

        registry.register(voucher);

        new BukkitRunnable() {
            @Override
            public void run() {
                manager.save(voucher);
            }
        }.runTaskAsynchronously(this.plugin);

        menuFactory.editMenu(voucher).open(player);
    }
}
