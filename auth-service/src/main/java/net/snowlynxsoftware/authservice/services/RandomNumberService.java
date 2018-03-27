package net.snowlynxsoftware.authservice.services;

import java.util.concurrent.ThreadLocalRandom;

public class RandomNumberService {

    /**
     * Returns a random integer within the range of min/max.
     * @param min
     * @param max
     * @return
     */
    public static int generateRandomInteger(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max);
    }

}
