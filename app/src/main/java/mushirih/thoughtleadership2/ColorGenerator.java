package mushirih.thoughtleadership2;



/**
 * Created by p-tah on 12/10/2015.
 */


import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ColorGenerator {

    public static ColorGenerator DEFAULT;

    public static ColorGenerator MATERIAL;

    static {
        DEFAULT = create(Arrays.asList(
                0xff00338D,
                0xff005E8B,
                0xff0091DA,
                0xff483698,
                0xff470A68,
                0xff6D2077,
                0xff00BAB3

                /*originally 9*
                0x->prefixx
                ff->for solid inner/
                 */
        ));
        MATERIAL = create(Arrays.asList(
                0xff00338D,
                0xff005E8B,
                0xff0091DA,
                0xff483698,
                0xff470A68,
                0xff6D2077,
                0xff00BAB3
        ));
    }

    private final List<Integer> mColors;
    private final Random mRandom;

    public static ColorGenerator create(List<Integer> colorList) {
        return new ColorGenerator(colorList);
    }

    private ColorGenerator(List<Integer> colorList) {
        mColors = colorList;
        mRandom = new Random(System.currentTimeMillis());
    }
//call random colors fom drawable provider
    public int getRandomColor() {
        return mColors.get(mRandom.nextInt(mColors.size()));
    }
//call specific colors
    public int getColor(Object key) {
        return mColors.get(Math.abs(key.hashCode()) % mColors.size());
    }
}

