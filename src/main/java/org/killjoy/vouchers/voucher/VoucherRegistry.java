package org.killjoy.vouchers.voucher;

import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.framework.qual.DefaultQualifier;
import org.killjoy.vouchers.util.Constants;

import java.util.*;

@DefaultQualifier(NonNull.class)
public final class VoucherRegistry {

    private final Map<String, Voucher> vouchers = new HashMap<>();

    public Optional<Voucher> get(final String key) {
        return Optional.ofNullable(this.vouchers.get(key));
    }

    public Optional<Voucher> get(final ItemStack item) {
        NBTItem nbtItem = new NBTItem(item);
        if (nbtItem.getKeys().contains(Constants.NBT_KEY)) {
            return get(nbtItem.getString(Constants.NBT_KEY));
        }
        return Optional.empty();
    }

    public void register(final Voucher voucher) {
        this.vouchers.put(voucher.getKey(), voucher);
    }

    public void clean(final Voucher voucher) {
        this.vouchers.remove(voucher.getKey());
    }

    public int size() {
        return this.vouchers.size();
    }

    public Collection<Voucher> all() {
        return Collections.unmodifiableCollection(this.vouchers.values());
    }

    public Collection<String> keys() {
        return Collections.unmodifiableCollection(this.vouchers.keySet());
    }
}
