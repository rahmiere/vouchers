package org.killjoy.vouchers.command.argument;

import cloud.commandframework.arguments.CommandArgument;
import cloud.commandframework.arguments.parser.ArgumentParseResult;
import cloud.commandframework.arguments.parser.ArgumentParser;
import cloud.commandframework.context.CommandContext;
import cloud.commandframework.exceptions.parsing.NoInputProvidedException;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import org.bukkit.command.CommandSender;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.framework.qual.DefaultQualifier;
import org.killjoy.vouchers.voucher.Voucher;
import org.killjoy.vouchers.voucher.VoucherRegistry;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;

import static cloud.commandframework.arguments.parser.ArgumentParseResult.failure;
import static cloud.commandframework.arguments.parser.ArgumentParseResult.success;

@DefaultQualifier(NonNull.class)
public final class VoucherArgument extends CommandArgument<CommandSender, Voucher> {

    @Inject
    public VoucherArgument(VoucherRegistry registry, final @Assisted String key) {
        super(true, key, new Parser(registry), Voucher.class);
    }

    @DefaultQualifier(NonNull.class)
    public static final class Parser implements ArgumentParser<CommandSender, Voucher> {

        private final VoucherRegistry registry;

        public Parser(final VoucherRegistry registry) {
            this.registry = registry;
        }

        @Override
        public @NonNull ArgumentParseResult<@NonNull Voucher> parse(
                @NonNull CommandContext<@NonNull CommandSender> commandContext,
                @NonNull Queue<@NonNull String> inputQueue
        ) {
            final @Nullable String input = inputQueue.peek();

            if (input == null) {
                return failure(new NoInputProvidedException(Parser.class, commandContext));
            }

            final Optional<Voucher> opt = this.registry.get(input);

            if (opt.isEmpty()) {
                return failure(new VoucherNotFoundException());
            }

            inputQueue.remove();
            return success(opt.get());
        }

        @Override
        public @NonNull List<@NonNull String> suggestions(@NonNull CommandContext<CommandSender> commandContext, @NonNull String input) {
            return new ArrayList<>(this.registry.keys());
        }
    }

    public static final class VoucherNotFoundException extends IllegalArgumentException {
        @Serial
        private static final long serialVersionUID = 2194915891515166L;

        public VoucherNotFoundException() {
        }
    }
}
