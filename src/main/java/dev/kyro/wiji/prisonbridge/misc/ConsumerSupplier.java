package dev.kyro.wiji.prisonbridge.misc;

import java.util.Objects;
import java.util.function.Consumer;

public interface ConsumerSupplier<T, U> {
	U consumerSupplier(T var1);
}
