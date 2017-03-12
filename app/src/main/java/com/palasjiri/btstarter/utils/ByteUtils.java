package com.palasjiri.btstarter.utils;


import java.nio.ByteBuffer;

public class ByteUtils {

    private static int SIZE_OF_INT = 4;

    public static byte[] intToBytes(int x) {
        return ByteBuffer.allocate(SIZE_OF_INT).putInt(x).array();
    }

    public static int bytesToInt(byte[] bytes) {
        return ByteBuffer.allocate(SIZE_OF_INT).put(bytes).getInt();
    }

}
