package com.fageniuscode.epargneplus.api.uitilities;

import java.util.concurrent.ThreadLocalRandom;

public class EpargnePlusGenerator {

    public static String generateUsername() {
        return generateIds("", 5);
    }

    private static String generateIds(String prefix, long lengthId) {
        String buffer = "abcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder stringBuilder = new StringBuilder();
        if (lengthId <= 0) {
            lengthId = 2;
        }
        if (prefix != null && prefix.trim().length() != 0) {
            stringBuilder.append(prefix.trim());
        }
        //Ici on aura une valeur entière aléatoire entre 0 et 26 non inclu
        int pos = ThreadLocalRandom.current().nextInt(26);
        stringBuilder.append(buffer.charAt(pos));
        for (int i = 0; i < lengthId; i++) {
            pos = ThreadLocalRandom.current().nextInt(buffer.length());
            stringBuilder.append(buffer.charAt(pos));
        }
        pos = ThreadLocalRandom.current().nextInt(26);
        stringBuilder.append(buffer.charAt(pos));
        return stringBuilder.toString().toUpperCase();
    }
}
