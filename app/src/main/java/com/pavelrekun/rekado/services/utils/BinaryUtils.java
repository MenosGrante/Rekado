package com.pavelrekun.rekado.services.utils;

public class BinaryUtils {

    public static int readInt32(byte[] buf, int off) {
        return ((buf[off] & 0xff) |
                ((buf[off + 1] & 0xff) << 8) |
                ((buf[off + 2] & 0xff) << 16) |
                ((buf[off + 3] & 0xff) << 24));
    }

    public static int readInt32BE(byte[] buf, int off) {
        return ((buf[off + 3] & 0xff) |
                ((buf[off + 2] & 0xff) << 8) |
                ((buf[off + 1] & 0xff) << 16) |
                ((buf[off] & 0xff) << 24));
    }

    public static void writeInt32(byte[] buf, int off, int i) {
        buf[off] = (byte) (i & 0xff);
        buf[off + 1] = (byte) ((i >> 8) & 0xff);
        buf[off + 2] = (byte) ((i >> 16) & 0xff);
        buf[off + 3] = (byte) ((i >> 24) & 0xff);
    }
}