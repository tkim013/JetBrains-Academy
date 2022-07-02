package machine;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        String s;
        CoffeeMachine cm = new CoffeeMachine();
        Scanner scanner = new Scanner(System.in);

        cm.prompt();

        while (true) {

            s = scanner.nextLine();

            cm.machine(s);

        }
    }
}
