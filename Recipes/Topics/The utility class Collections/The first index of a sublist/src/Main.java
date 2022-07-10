import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);
        String a = scanner.nextLine();
        String b = scanner.nextLine();

        List<String> list = Arrays.asList(a.split(" "));
        List<String> c = Arrays.asList(b.split(" "));

        int fo = -1;
        int lo = -1;

        List<String> subList;
        for (int i = 0; i <= list.size() - c.size(); i++) {
            if (list.get(i).equals(c.get(0))) {
                subList = list.subList(i, i + c.size());
                if (c.equals(subList)) {
                    if (fo == -1) {
                        fo = i;
                    }
                    lo = i;
                }
            }
        }

        System.out.println(fo + " " + lo);
    }
}