package org.killjoy.vouchers.listener;

import com.google.inject.Inject;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.framework.qual.DefaultQualifier;
import org.killjoy.vouchers.event.VoucherRedeemEvent;
import org.killjoy.vouchers.language.LangKey;
import org.killjoy.vouchers.language.Language;
import org.killjoy.vouchers.voucher.Voucher;
import org.killjoy.vouchers.voucher.VoucherRegistry;

import java.util.Optional;

import static java.util.Objects.requireNonNull;

@DefaultQualifier(NonNull.class)
public final class InteractListener implements Listener {

    private final VoucherRegistry registry;
    private final Language language;

    @Inject
    public InteractListener(VoucherRegistry registry, Language language) {
        this.registry = registry;
        this.language = language;
    }

    @EventHandler(ignoreCancelled = true)
    public void onInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK || event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        @Nullable ItemStack item = event.getItem();
        if (item == null || item.getType() == Material.AIR) {
            return;
        }

        Optional<Voucher> opt = registry.get(item);
        if (opt.isEmpty()) {
            return;
        }

        Player player = event.getPlayer();
        Voucher voucher = opt.get();

        if (voucher.isDisabled()) {
            player.sendMessage(language.get(LangKey.VOUCHER_DISABLED));
            return;
        }

        VoucherRedeemEvent e = new VoucherRedeemEvent(player, voucher);
        if (!e.callEvent()) {
            return;
        }

        event.setCancelled(true);

        EquipmentSlot hand = requireNonNull(event.getHand());
        int newAmount = item.getAmount() - 1;

        if (newAmount == 0) {
            setHand(player, hand, new ItemStack(Material.AIR));
        } else {
            item.setAmount(newAmount);
            setHand(player, hand, item);
        }

        voucher.redeem(player);
    }

    private void setHand(Player player, EquipmentSlot slot, ItemStack item) {
        PlayerInventory inventory = player.getInventory();

        if (slot == EquipmentSlot.HAND) {
            inventory.setItemInMainHand(item);
        } else if (slot == EquipmentSlot.OFF_HAND) {
            inventory.setItemInOffHand(item);
        } else {
            throw new IllegalArgumentException(String.format("'%s' is not a valid hand slot", slot));
        }
    }
}
