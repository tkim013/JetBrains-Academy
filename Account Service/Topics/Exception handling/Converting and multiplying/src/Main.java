import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here

        String s;
        Scanner scanner = new Scanner(System.in);

        while (true) {

            s = scanner.nextLine();

            try {
                if (Integer.parseInt(s) == 0) {
                    break;
                }

                System.out.println(Integer.parseInt(s) * 10);
            } catch (Exception e) {
                System.out.println("Invalid user input: " + s);
            }
        }
    }
}