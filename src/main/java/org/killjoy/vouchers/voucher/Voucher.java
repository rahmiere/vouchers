package org.killjoy.vouchers.voucher;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.NodeKey;

@ConfigSerializable
public final class Voucher {

    @NodeKey
    private String key;

    public Voucher() {

    }

    public Voucher(final String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
