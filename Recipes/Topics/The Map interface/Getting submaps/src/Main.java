
import java.util.*;

class Main {
    public static void main(String[] args) {
        // put your code here
        int b;
        int e;
        int d;
        int k;
        String v;

        TreeMap<Integer, String> map = new TreeMap<>();
        Scanner scanner = new Scanner(System.in);

        b = scanner.nextInt();
        e = scanner.nextInt();
        d = scanner.nextInt();

        for (int i = 0; i < d; i++) {
            k = scanner.nextInt();
            v = scanner.nextLine();

            map.put(k, v);
        }

        SortedMap<Integer, String> sm = map.subMap(b, true, e, true);

        for (Map.Entry mapElement : sm.entrySet()) {
            System.out.println(mapElement.getKey() + "" + mapElement.getValue());
        }
    }
}