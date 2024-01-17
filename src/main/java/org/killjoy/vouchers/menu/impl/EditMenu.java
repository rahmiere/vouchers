package org.killjoy.vouchers.menu.impl;

import broccolai.corn.paper.item.PaperItemBuilder;
import com.google.common.collect.Lists;
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

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.util.Collections.singletonMap;
import static net.kyori.adventure.text.Component.text;
import static org.incendo.interfaces.paper.transform.PaperTransform.chestItem;

@DefaultQualifier(NonNull.class)
public final class EditMenu extends Menu {

    private final Language language;
    private final RenameListener listener;
    private final MenuFactory menuFactory;

    private final Voucher voucher;

    private static final ItemStack RENAME = PaperItemBuilder.ofType(Material.NAME_TAG)
            .name(text("Rename Voucher")
                    .color(NamedTextColor.AQUA)
                    .decorate(TextDecoration.BOLD))
            .lore(List.of(text("Change the display name of the voucher.").color(NamedTextColor.GRAY)))
            .build();

    private static final ItemStack EDIT_ITEM = PaperItemBuilder.ofType(Material.NETHER_STAR)
            .name(text("Edit Item")
                    .color(NamedTextColor.AQUA)
                    .decorate(TextDecoration.BOLD))
            .lore(List.of(text("Change the item that a player receives when this voucher is given to them.")
                    .color(NamedTextColor.GRAY)))
            .build();

    private static final ItemStack TOGGLE_ON = PaperItemBuilder.ofType(Material.GRAY_DYE)
            .name(text("Enable Voucher")
                    .color(NamedTextColor.GREEN)
                    .decorate(TextDecoration.BOLD))
            .lore(List.of(
                    text("Left-click to enable the voucher to be redeemed.").color(NamedTextColor.GRAY),
                    text("Warning: Players will be able to redeem this voucher").color(NamedTextColor.GRAY)))
            .build();

    private static final ItemStack TOGGLE_OFF = PaperItemBuilder.ofType(Material.LIME_DYE)
            .name(text("Disable Voucher")
                    .color(NamedTextColor.RED)
                    .decorate(TextDecoration.BOLD))
            .lore(List.of(
                    text("Left-click to disable the voucher.").color(NamedTextColor.GRAY),
                    text("Warning: Players will no longer be able to redeem this voucher.").color(NamedTextColor.GRAY)))
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
                .addTransform(chestItem(this::renameElement, 1, 0))
                .addTransform(chestItem(this::editItemElement, 3, 0))
                .addTransform(chestItem(this::toggleElement, 7, 0))
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

    private ItemStackElement<ChestPane> toggleElement() {
        return ItemStackElement.of(voucher.isDisabled() ? TOGGLE_ON : TOGGLE_OFF, this::toggleClick);
    }

    private void toggleClick(final ClickContext<ChestPane, InventoryClickEvent, PlayerViewer> context) {
        final boolean toggle = !voucher.isDisabled();

        voucher.setDisabled(toggle);

        Map<String, Object> placeholders = singletonMap("key", voucher.getKey());

        Component component = toggle ?
                language.get(LangKey.VOUCHER_TOGGLE_OFF, placeholders) :
                language.get(LangKey.VOUCHER_TOGGLE_ON, placeholders);

        final Player player = context.viewer().player();

        player.closeInventory();
        player.sendMessage(component);
    }
}
