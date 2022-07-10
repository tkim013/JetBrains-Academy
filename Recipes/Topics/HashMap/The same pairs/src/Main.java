
import java.util.*;


class MapFunctions {

    public static void calcTheSamePairs(Map<String, String> map1, Map<String, String> map2) {
        // write your code here
        int count = 0;
        for (var entry : map1.entrySet()) {
            if (map2.containsKey(entry.getKey())) {
                if (map1.get(entry.getKey()).equals(map2.get(entry.getKey()))) {
                    count++;
                }
            }
        }
        System.out.println(count);
    }
}