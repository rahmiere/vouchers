package org.killjoy.vouchers.voucher;

import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.killjoy.vouchers.util.Constants;
import org.killjoy.vouchers.util.MessageParser;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.NodeKey;
import org.spongepowered.configurate.objectmapping.meta.Required;

import static java.util.Objects.requireNonNull;

@ConfigSerializable
public final class Voucher {

    @NodeKey
    private String key;

    @Required
    private String type;

    private String name;

    private boolean disabled;

    private transient boolean dirty;

    public Voucher() {
        this.type = "POTATO";
        this.name = "Unnamed Voucher";
    }

    public Voucher(final String key, final String type) {
        this.key = key;
        this.type = type;
        this.dirty = true;
    }

    public void redeem(final @NonNull Player player) {
        // TODO: redeem code here
    }

    public @NonNull ItemStack toItemStack() {
        Material material = requireNonNull(Material.getMaterial(this.type), "Type is not valid Material");

        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        if (this.name != null) {
            meta.displayName(MessageParser.parse(this.name));
        }

        // TODO: lore support

        item.setItemMeta(meta);

        NBTItem nbtItem = new NBTItem(item);
        nbtItem.setString(Constants.NBT_KEY, this.getKey());

        return nbtItem.getItem();
    }

    public String getKey() {
        return key;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        setDirty();
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        setDirty();
        this.name = name;
    }

    public boolean isDirty() {
        return dirty;
    }

    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    public void setDirty() {
        this.dirty = true;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }
}
