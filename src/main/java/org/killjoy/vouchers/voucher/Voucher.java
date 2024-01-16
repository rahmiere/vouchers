package org.killjoy.vouchers.voucher;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.NodeKey;
import org.spongepowered.configurate.objectmapping.meta.Required;

@ConfigSerializable
public final class Voucher {

    @NodeKey
    private String key;

    @Required
    private String type;

    public Voucher() {

    }

    public Voucher(final String key, final String type) {
        this.key = key;
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
