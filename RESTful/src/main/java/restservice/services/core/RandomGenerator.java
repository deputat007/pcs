package restservice.services.core;


import java.util.Random;


public class RandomGenerator {

    public final static int DEFAULT_RANGE = 150;

    public static int generate(int maxRange) {
        final Random random = new Random();

        return random.nextInt(maxRange) + 1;
    }
}
