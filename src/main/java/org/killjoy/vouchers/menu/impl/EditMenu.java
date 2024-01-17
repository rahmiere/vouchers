package org.killjoy.vouchers.menu.impl;

import broccolai.corn.paper.item.PaperItemBuilder;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.framework.qual.DefaultQualifier;
import org.incendo.interfaces.core.click.ClickContext;
import org.incendo.interfaces.paper.PlayerViewer;
import org.incendo.interfaces.paper.element.ItemStackElement;
import org.incendo.interfaces.paper.pane.ChestPane;
import org.incendo.interfaces.paper.type.ChestInterface;
import org.killjoy.vouchers.language.LangKey;
import org.killjoy.vouchers.language.Language;
import org.killjoy.vouchers.listener.RenameListener;
import org.killjoy.vouchers.menu.Menu;
import org.killjoy.vouchers.menu.MenuFactory;
import org.killjoy.vouchers.voucher.Voucher;

import static java.util.Collections.singletonMap;
import static org.incendo.interfaces.paper.transform.PaperTransform.chestItem;

@DefaultQualifier(NonNull.class)
public final class EditMenu extends Menu {

    private final Language language;
    private final RenameListener listener;
    private final MenuFactory menuFactory;

    private final Voucher voucher;

    private static final ItemStack RENAME = PaperItemBuilder.ofType(Material.NAME_TAG)
            .name(Component.text("Rename Voucher")
                    .color(NamedTextColor.AQUA)
                    .decorate(TextDecoration.BOLD))
            .build();

    private static final ItemStack EDIT_ITEM = PaperItemBuilder.ofType(Material.NETHER_STAR)
            .name(Component.text("Edit Item")
                    .color(NamedTextColor.AQUA)
                    .decorate(TextDecoration.BOLD))
            .build();

    @Inject
    public EditMenu(Language language, RenameListener listener, MenuFactory menuFactory, @Assisted Voucher voucher) {
        this.language = language;
        this.listener = listener;
        this.menuFactory = menuFactory;
        this.voucher = voucher;
    }

    @Override
    protected ChestInterface build() {
        return ChestInterface.builder()
                .title(language.get(LangKey.EDIT_MENU_TITLE, singletonMap("key", voucher.getKey())))
                .rows(1)
                .cancelClicksInPlayerInventory(true)
                .addTransform(chestItem(this::renameElement, 0, 0))
                .addTransform(chestItem(this::editItemElement, 2, 0))
                .build();
    }

    private ItemStackElement<ChestPane> renameElement() {
        return ItemStackElement.of(RENAME, this::renameClick);
    }

    private void renameClick(final ClickContext<ChestPane, InventoryClickEvent, PlayerViewer> context) {
        final Player player = context.viewer().player();

        listener.getRenameMap().put(player, voucher);

        player.sendMessage(language.get(LangKey.RENAME));
        player.closeInventory();
    }

    private ItemStackElement<ChestPane> editItemElement() {
        return ItemStackElement.of(EDIT_ITEM, this::editItemClick);
    }

    private void editItemClick(final ClickContext<ChestPane, InventoryClickEvent, PlayerViewer> context) {
        final Player player = context.viewer().player();

        player.closeInventory();
        menuFactory.selectItemMenu(voucher).open(player);
    }
}
