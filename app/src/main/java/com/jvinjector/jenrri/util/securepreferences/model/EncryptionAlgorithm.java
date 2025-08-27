/*
 * Created by ZacDevz Official VPN on 27/01/24 22:59
 *  Copyright (c) Telegram: @ZacDevzOfficial1 . All rights reserved.
 */
package com.speedlite.vpn.util.securepreferences.model;

/**
 * Encryption algorithms supported by the {@link com.github.hussainderry.securepreferences.crypto.CipherService}
 * @author Hussain Al-Derry <hussain.derry@gmail.com>
 * */
public enum EncryptionAlgorithm {

    AES(new int[]{128, 196, 256}), TripleDES(new int[]{128, 192});

    private int[] keySizesInBits;

    EncryptionAlgorithm(int[] keySizes) {
        this.keySizesInBits = keySizes;
    }

    public int[] getKeySizes() {
        return keySizesInBits;
    }
}
