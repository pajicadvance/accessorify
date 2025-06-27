package me.pajic.accessorify.util.compat;

import dev.yurisuika.raised.api.RaisedApi;
import dev.yurisuika.raised.registry.LayerRegistry;
import it.unimi.dsi.fastutil.ints.IntIntImmutablePair;

public class RaisedCompat {
    public static IntIntImmutablePair getOtherComponentOffsets() {
        return new IntIntImmutablePair(
                -RaisedApi.getX(LayerRegistry.OTHER),
                -RaisedApi.getY(LayerRegistry.OTHER)
        );
    }
}
