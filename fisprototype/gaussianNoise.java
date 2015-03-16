package fisprototype;

import java.util.Random;
import java.util.logging.Logger;


public class gaussianNoise {

    int[] input;
    int[] output;
    int progress;
    float variance;
    int width;
    int height;
    Random randgen;

    public void gaussianNoise() {
        progress = 0;
    }

    public void init(int[] original, int widthIn, int heightIn, float varianceIn) {
        randgen = new Random();
        variance = varianceIn;
        width = widthIn;
        height = heightIn;
        input = new int[width * height];
        output = new int[width * height];
        input = original;
    }

    public int[] process() {
        double rand;
        int result = 0;

        for (int x = 0; x < width; x++) {
            progress++;
            for (int y = 0; y < height; y++) {

                //Generate amount to be added
                rand = (variance * (randgen.nextGaussian()));
                result = (int) ((input[y * width + x] & 0xff) + rand);

                //Clip final result
                if (result < 0) {
                    result = 0;
                } else if (result > 255) {
                    result = 255;
                }

                //Convert back to grayscale pixel
                output[y * width + x] = 0xff000000 | (result + (result << 16) + (result << 8));
            }
        }
        return output;
    }

    public int getProgress() {
        return progress;
    }
    private static final Logger LOG = Logger.getLogger(gaussianNoise.class.getName());

}
