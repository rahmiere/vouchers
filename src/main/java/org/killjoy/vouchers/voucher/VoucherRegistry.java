package org.killjoy.vouchers.voucher;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.framework.qual.DefaultQualifier;

import java.util.*;

@DefaultQualifier(NonNull.class)
public final class VoucherRegistry {

    private final Map<String, Voucher> vouchers = new HashMap<>();

    public Optional<Voucher> get(final String key) {
        return Optional.ofNullable(this.vouchers.get(key));
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
