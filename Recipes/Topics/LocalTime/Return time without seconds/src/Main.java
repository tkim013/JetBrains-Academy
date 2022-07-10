import java.time.LocalTime;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);
        String time = scanner.nextLine();

        String[] t = time.split(":");

        System.out.println(t[0] + ":" + t[1]);
    }
}