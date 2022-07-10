import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String regNum = scanner.nextLine(); // a valid or invalid registration number

        /* write your code here */
        String regex = "[ABCEHKMOPTXY]\\d\\d\\d[ABCEHKMOPTXY][ABCEHKMOPTXY]";

        boolean check = regNum.matches(regex);

        System.out.println(check);
    }
}