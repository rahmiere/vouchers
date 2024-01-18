package org.killjoy.vouchers.menu.impl;

import broccolai.corn.paper.item.PaperItemBuilder;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.framework.qual.DefaultQualifier;
import org.incendo.interfaces.paper.click.InventoryClickContext;
import org.incendo.interfaces.paper.element.ItemStackElement;
import org.incendo.interfaces.paper.pane.ChestPane;
import org.incendo.interfaces.paper.type.ChestInterface;
import org.incendo.interfaces.paper.view.ChestView;
import org.killjoy.vouchers.Vouchers;
import org.killjoy.vouchers.language.LangKey;
import org.killjoy.vouchers.language.Language;
import org.killjoy.vouchers.menu.Menu;
import org.killjoy.vouchers.voucher.Voucher;
import org.killjoy.vouchers.voucher.VoucherManager;

import java.util.List;

import static java.util.Collections.singletonMap;
import static net.kyori.adventure.text.Component.text;
import static org.incendo.interfaces.paper.transform.PaperTransform.chestItem;

@DefaultQualifier(NonNull.class)
public final class SelectItemStackMenu extends Menu {

    private static final ItemStack GUIDE = PaperItemBuilder.ofType(Material.NETHER_STAR)
            .name(text("Select an item...")
                    .color(NamedTextColor.GREEN)
                    .decorate(TextDecoration.BOLD))
            .lore(List.of(text("Place an item in this menu to select it.")
                    .color(NamedTextColor.GRAY)))
            .build();

    private final Vouchers plugin;
    private final Language language;

    private final Voucher voucher;
    private final VoucherManager manager;

    @Inject
    public SelectItemStackMenu(Vouchers plugin, Language language, @Assisted Voucher voucher, VoucherManager manager) {
        this.plugin = plugin;
        this.language = language;
        this.voucher = voucher;
        this.manager = manager;
    }

    @Override
    protected ChestInterface build() {
        return ChestInterface.builder()
                .rows(1)
                .title(text("Select an item..."))
                .addTransform(chestItem(this::guideElement, 4, 0))
                .clickHandler(this::clickHandler)
                .build();
    }

    private ItemStackElement<ChestPane> guideElement() {
        return ItemStackElement.of(GUIDE);
    }

    private void clickHandler(InventoryClickContext<ChestPane, ChestView> context) {
        final Player player = context.viewer().player();
        final InventoryClickEvent event = context.click().cause();

        ItemStack item = event.getCursor();

        if (item.getType() == Material.AIR || item.isSimilar(GUIDE)) {
            return;
        }

        String typeString = item.getType().toString();

        voucher.setType(typeString);

        new BukkitRunnable() {
            @Override
            public void run() {
                manager.save(voucher);
            }
        }.runTaskAsynchronously(this.plugin);

        player.sendMessage(language.get(LangKey.ITEM_UPDATED, singletonMap("type", typeString)));
        player.closeInventory();
    }
}
