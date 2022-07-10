// do not remove imports
import java.util.*;
import java.util.function.Function;

class ArrayUtils {
    public static <E> E[] invert(E[] array) {
        Collections.reverse(Arrays.asList(array));
        return array;
    }
}