package me.pajic.accessorify.compat;

import dev.yurisuika.raised.api.RaisedApi;
import dev.yurisuika.raised.util.properties.Element;
import it.unimi.dsi.fastutil.ints.IntIntImmutablePair;

public class RaisedCompat {
    public static IntIntImmutablePair getOtherComponentOffsets() {
        return new IntIntImmutablePair(
                RaisedApi.getX(Element.OTHER) * -RaisedApi.getPosition(Element.OTHER).getX(),
                RaisedApi.getY(Element.OTHER) * -RaisedApi.getPosition(Element.OTHER).getY()
        );
    }
}
