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
import org.killjoy.vouchers.voucher.Voucher;

import java.util.Collections;

import static org.incendo.interfaces.paper.transform.PaperTransform.chestItem;

@DefaultQualifier(NonNull.class)
public final class EditMenu extends Menu {

    private final Language language;
    private final RenameListener listener;

    private final Voucher voucher;

    @Inject
    public EditMenu(Language language, RenameListener listener, @Assisted Voucher voucher) {
        this.language = language;
        this.listener = listener;
        this.voucher = voucher;
    }

    @Override
    protected ChestInterface build() {
        return ChestInterface.builder()
                .title(language.get(LangKey.EDIT_MENU_TITLE, Collections.singletonMap("key", voucher.getKey())))
                .rows(1)
                .cancelClicksInPlayerInventory(true)
                .addTransform(chestItem(this::renameElement, 0, 0))
                .build();
    }

    private ItemStackElement<ChestPane> renameElement() {
        final ItemStack item = PaperItemBuilder.ofType(Material.NAME_TAG)
                .name(language.get(LangKey.EDIT_MENU_RENAME))
                .build();

        return ItemStackElement.of(item, this::renameClick);
    }

    private void renameClick(final ClickContext<ChestPane, InventoryClickEvent, PlayerViewer> context) {
        final Player player = context.viewer().player();

        listener.getRenameMap().put(player, voucher);

        player.sendMessage(language.get(LangKey.RENAME));
        player.closeInventory();
    }
}
