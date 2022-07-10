import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);
        List<LocalDateTime> list = new ArrayList<>();
        LocalDateTime start;
        LocalDateTime end;
        int count = 0;

        LocalDateTime a = LocalDateTime.parse(scanner.nextLine());
        LocalDateTime b = LocalDateTime.parse(scanner.nextLine());
        int n = Integer.parseInt(scanner.nextLine());

        while (n != 0) {
            list.add(LocalDateTime.parse(scanner.nextLine()));
            n--;
        }

        if (a.compareTo(b) < 0) {
            start = a;
            end = b;
        } else {
            start = b;
            end = a;
        }

        for (LocalDateTime ldt : list) {
            if (ldt.isEqual(start) || ldt.isAfter(start) && ldt.isBefore(end)) {
                count++;
            }
        }

        System.out.println(count);
    }
}