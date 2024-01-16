package org.killjoy.vouchers.command.argument.factory;

import com.google.inject.assistedinject.Assisted;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.killjoy.vouchers.command.argument.VoucherArgument;

public interface CloudArgumentFactory {

    @NonNull VoucherArgument voucher(
            @Assisted String key
    );
}
