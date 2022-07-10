package machine;

import java.util.Scanner;

public class CoffeeMachine {

    State state;
    Supplies supplies;

    public static void main(String[] args) {

        String s;
        CoffeeMachine cm = new CoffeeMachine();
        Scanner scanner = new Scanner(System.in);

        cm.prompt();

        while (true) {

            s = scanner.nextLine();

            cm.machine(s);

        }

//        String line = "";
//        Scanner scanner = new Scanner(System.in);
//
//        System.out.println("Write action (buy, fill, take, remaining, exit):");
//
//        while (true) {
//
//            line = scanner.nextLine();
//            line = line.toLowerCase();
//
//            machine(line);
//
//
//            if(action.equals("exit")) {
//                break;
//            }
//
//            switch (action) {
//
//                case "buy" : {
//                    buy(scanner, supplies);
//                    break;
//                }
//
//                case "fill" : {
//                    fill(scanner, supplies);
//                    break;
//                }
//
//                case "take" : {
//                    take(supplies);
//                    break;
//                }
//
//                case "remaining" : {
//                    showSupply(supplies);
//                    break;
//                }
//            }
//        }
    }

    public CoffeeMachine() {
        this.state = State.ACTION;
        this.supplies = new Supplies(400, 540, 120, 9, 550);
    }

    enum State {
        ACTION, BUY, FILL_WATER, FILL_MILK, FILL_BEANS, FILL_CUPS
    }

    public void machine(String s) {

        switch (state) {

            case ACTION: {

                s = s.toUpperCase();

                switch (s) {

                    case "BUY": {
                        this.state = State.BUY;
                        break;
                    }

                    case "FILL": {
                        this.state = State.FILL_WATER;
                        break;
                    }

                    case "TAKE": {
                        take();
                        break;
                    }

                    case "REMAINING": {
                        showSupply();
                        break;
                    }

                    case "EXIT": {
                        System.exit(0);
                    }

                    default: {
                        break;
                    }
                }

                break;
            }

            case BUY: {

                switch (s) {

                    case "1": {

                        if (supplies.getWater() - 250 < 0) {
                            System.out.println("Sorry, not enough water!");
                            state = State.ACTION;
                            break;
                        }

                        if (supplies.getBeans() - 16 < 0) {
                            System.out.println("Sorry, not enough beans!");
                            state = State.ACTION;
                            break;
                        }

                        System.out.println("I have enough resources, making you a coffee!");
                        supplies.setWater(supplies.getWater() - 250);
                        supplies.setBeans(supplies.getBeans() - 16);
                        supplies.setMoney(supplies.getMoney() + 4);
                        supplies.setCups(supplies.getCups() - 1);
                        state = State.ACTION;
                        break;
                    }

                    case "2": {

                        if (supplies.getWater() - 350 < 0) {
                            System.out.println("Sorry, not enough water!");
                            state = State.ACTION;
                            break;
                        }

                        if (supplies.getMilk() - 75 < 0) {
                            System.out.println("Sorry, not enough beans!");
                            state = State.ACTION;
                            break;
                        }

                        if (supplies.getBeans() - 20 < 0) {
                            System.out.println("Sorry, not enough beans!");
                            state = State.ACTION;
                            break;
                        }

                        System.out.println("I have enough resources, making you a coffee!");
                        supplies.setWater(supplies.getWater() - 350);
                        supplies.setMilk(supplies.getMilk() - 75);
                        supplies.setBeans(supplies.getBeans() - 20);
                        supplies.setMoney(supplies.getMoney() + 7);
                        supplies.setCups(supplies.getCups() - 1);
                        state = State.ACTION;
                        break;
                    }

                    case "3": {

                        if (supplies.getWater() - 200 < 0) {
                            System.out.println("Sorry, not enough water!");
                            state = State.ACTION;
                            break;
                        }

                        if (supplies.getMilk() - 100 < 0) {
                            System.out.println("Sorry, not enough beans!");
                            state = State.ACTION;
                            break;
                        }

                        if (supplies.getBeans() - 12 < 0) {
                            System.out.println("Sorry, not enough beans!");
                            state = State.ACTION;
                            break;
                        }

                        System.out.println("I have enough resources, making you a coffee!");
                        supplies.setWater(supplies.getWater() - 200);
                        supplies.setMilk(supplies.getMilk() - 100);
                        supplies.setBeans(supplies.getBeans() - 12);
                        supplies.setMoney(supplies.getMoney() + 6);
                        supplies.setCups(supplies.getCups() - 1);
                        state = State.ACTION;
                        break;
                    }

                    case "back": {

                        state = State.ACTION;
                        break;
                    }

                    default: {
                        break;
                    }

                }

                break;
            }

            case FILL_WATER: {
                try {
                    supplies.setWater(supplies.getWater() + Integer.parseInt(s));
                    state = State.FILL_MILK;
                } catch (NumberFormatException e) {
                    System.out.println("try entering a number");
                    e.printStackTrace();
                }
                break;
            }

            case FILL_MILK: {
                try {
                    supplies.setMilk(supplies.getMilk() + Integer.parseInt(s));
                    state = State.FILL_BEANS;
                } catch (NumberFormatException e) {
                    System.out.println("try entering a number");
                    e.printStackTrace();
                }
                break;
            }

            case FILL_BEANS: {
                try {
                    supplies.setBeans(supplies.getBeans() + Integer.parseInt(s));
                    state = State.FILL_CUPS;
                } catch (NumberFormatException e) {
                    System.out.println("try entering a number");
                    e.printStackTrace();
                }
                break;
            }

            case FILL_CUPS: {
                try {
                    supplies.setCups(supplies.getCups() + Integer.parseInt(s));
                    state = State.ACTION;
                } catch (NumberFormatException e) {
                    System.out.println("try entering a number");
                    e.printStackTrace();
                }
                break;
            }
        }
        prompt();
    }

    public void prompt() {

        switch (state) {
            case ACTION: {
                System.out.printf("%nWrite action (buy, fill, take, remaining, exit):%n");
                break;
            }

            case BUY: {
                System.out.printf("%nWhat do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:%n");
                break;
            }

            case FILL_WATER: {
                System.out.printf("%nWrite how many ml of water you want to add:%n");
                break;
            }

            case FILL_MILK: {
                System.out.printf("%nWrite how many ml of milk you want to add:%n");
                break;
            }

            case FILL_BEANS: {
                System.out.printf("%nWrite how many grams of coffee beans you want to add:%n");
                break;
            }

            case FILL_CUPS: {
                System.out.printf("%nWrite how many disposable cups of coffee you want to add:%n");
                break;
            }

            default: {
                break;
            }
        }
    }

//    public void action(String s) {
//        switch (s) {
//
//            case "buy" : {
//                buy(scanner, supplies);
//                break;
//            }
//
//            case "fill" : {
//                fill(scanner, supplies);
//                break;
//            }
//
//            case "take" : {
//                take(supplies);
//                break;
//            }
//
//            case "remaining" : {
//                showSupply();
//                break;
//            }
//        }
//    }

    public void buyFillTake() {

        Supplies supplies = new Supplies(400, 540, 120, 9, 550);

        showSupply();
        action(supplies);
        showSupply();
    }

    public void showSupply() {

        System.out.printf("%nThe coffee machine has:%n" +
                "%d ml of water%n" +
                "%d ml of milk%n" +
                "%d g of coffee beans%n" +
                "%d disposable cups%n" +
                "$%d of money%n",
                this.supplies.getWater(),
                this.supplies.getMilk(),
                this.supplies.getBeans(),
                this.supplies.getCups(),
                this.supplies.getMoney());
    }

    public void action(Supplies supplies) {

        Scanner scanner = new Scanner(System.in);
        String action = "";

        try {
            System.out.println("Write action (buy, fill, take):");
            action = scanner.nextLine();
        } catch (Exception e) {
            e.printStackTrace();
        }

        switch (action.toLowerCase()) {

            case "buy" : {
                buy(scanner, supplies);
                break;
            }
            case "fill" : {
                fill(scanner, supplies);
                break;
            }
            case "take" : {
                take();
                break;
            }
        }
    }

    public void buy(Scanner scanner, Supplies supplies) {
        try {
            String bOption = "";
            System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:");
            bOption = scanner.nextLine();
            bOption = bOption.toLowerCase();
            //todo? input validation

            switch (bOption) {

                case "1" : {

                    if (supplies.getWater() - 250 < 0) {
                        System.out.println("Sorry, not enough water!");
                        break;
                    }

                    if (supplies.getBeans() - 16 < 0) {
                        System.out.println("Sorry, not enough beans!");
                        break;
                    }

                    System.out.println("I have enough resources, making you a coffee!");
                    supplies.setWater(supplies.getWater() - 250);
                    supplies.setBeans(supplies.getBeans() - 16);
                    supplies.setMoney(supplies.getMoney() + 4);
                    supplies.setCups(supplies.getCups() - 1);
                    break;
                }

                case "2" : {

                    if (supplies.getWater() - 350 < 0) {
                        System.out.println("Sorry, not enough water!");
                        break;
                    }

                    if (supplies.getMilk() - 75 < 0) {
                        System.out.println("Sorry, not enough beans!");
                        break;
                    }

                    if (supplies.getBeans() - 20 < 0) {
                        System.out.println("Sorry, not enough beans!");
                        break;
                    }

                    System.out.println("I have enough resources, making you a coffee!");
                    supplies.setWater(supplies.getWater() - 350);
                    supplies.setMilk(supplies.getMilk() - 75);
                    supplies.setBeans(supplies.getBeans() - 20);
                    supplies.setMoney(supplies.getMoney() + 7);
                    supplies.setCups(supplies.getCups() - 1);
                    break;
                }

                case "3" : {

                    if (supplies.getWater() - 200 < 0) {
                        System.out.println("Sorry, not enough water!");
                        break;
                    }

                    if (supplies.getMilk() - 100 < 0) {
                        System.out.println("Sorry, not enough beans!");
                        break;
                    }

                    if (supplies.getBeans() - 12 < 0) {
                        System.out.println("Sorry, not enough beans!");
                        break;
                    }

                    System.out.println("I have enough resources, making you a coffee!");
                    supplies.setWater(supplies.getWater() - 200);
                    supplies.setMilk(supplies.getMilk() - 100);
                    supplies.setBeans(supplies.getBeans() - 12);
                    supplies.setMoney(supplies.getMoney() + 6);
                    supplies.setCups(supplies.getCups() - 1);
                    break;
                }

//                case "back" : {
//                    break;
//                }

                default: {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fill(Scanner scanner, Supplies supplies) {

        int input;

        try {
            System.out.println("Write how many ml of water you want to add:");
            input = scanner.nextInt();
            supplies.setWater(supplies.getWater() + input);
            System.out.println("Write how many ml of milk you want to add:");
            input = scanner.nextInt();
            supplies.setMilk(supplies.getMilk() + input);
            System.out.println("Write how many grams of coffee beans you want to add:");
            input = scanner.nextInt();
            supplies.setBeans(supplies.getBeans() + input);
            System.out.println("Write how many disposable cups of coffee you want to add:");
            input = scanner.nextInt();
            supplies.setCups(supplies.getCups() + input);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void take() {
        System.out.printf("%nI gave you %d%n", this.supplies.getMoney());
        this.supplies.setMoney(0);
    }

    public void stage3() {

        int water = -1;
        int milk = -1;
        int beans = -1;
        int cups = -1;
        int wCount = 0;
        int mCount = 0;
        int bCount = 0;
        int cCount = 0;

        Scanner scanner = new Scanner(System.in);
//        System.out.println("Starting to make a coffee\nGrinding coffee beans\nBoiling water\nMixing boiled water with crushed coffee beans\nPouring coffee into the cup\nPouring some milk into the cup\nCoffee is ready!");
//        System.out.println("Write how many cups of coffee you will need: ");
//        cups = scanner.nextInt();
//        System.out.printf("For %d cups of coffee you will need:%n" +
//                "%d ml of water%n" +
//                "%d ml of milk%n" +
//                "%d g of coffee beans%n", cups, cups * 200, cups * 50, cups * 15);

        try {
            System.out.println("Write how many ml of water the coffee machine has:");
            water = scanner.nextInt();
            System.out.println("Write how many ml of milk the coffee machine has:");
            milk = scanner.nextInt();
            System.out.println("Write how many grams of coffee beans the coffee machine has:");
            beans = scanner.nextInt();
            System.out.println("Write how many cups of coffee you will need:");
            cups = scanner.nextInt();

            wCount = water / 200;
            mCount = milk / 50;
            bCount = beans / 15;

            cCount = wCount;
            if (mCount < wCount) {
                cCount = mCount;
            } else if (bCount < mCount) {
                cCount = bCount;
            }

            if (cCount == cups) {
                System.out.println("Yes, I can make that amount of coffee");
            } else if (cCount < cups){
                System.out.printf("No, I can only make %d cup(s) of coffee%n", cCount);
            } else if (cCount > cups) {
                System.out.printf("Yes, I can make that amount of coffee (and even %d more than that)%n", cCount - cups);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class Supplies {
        private int water = 400;
        private int milk = 540;
        private int beans = 120;
        private int cups = 9;
        private int money = 550;

        public Supplies() {
        }

        public Supplies(int water, int milk, int beans, int cups, int money) {
            this.water = water;
            this.milk = milk;
            this.beans = beans;
            this.cups = cups;
            this.money = money;
        }

        public int getWater() {
            return water;
        }

        public void setWater(int water) {
            this.water = water;
        }

        public int getMilk() {
            return milk;
        }

        public void setMilk(int milk) {
            this.milk = milk;
        }

        public int getBeans() {
            return beans;
        }

        public void setBeans(int beans) {
            this.beans = beans;
        }

        public int getCups() {
            return cups;
        }

        public void setCups(int cups) {
            this.cups = cups;
        }

        public int getMoney() {
            return money;
        }

        public void setMoney(int money) {
            this.money = money;
        }

        @Override
        public String toString() {
            return "Supplies{" +
                    "water=" + water +
                    ", milk=" + milk +
                    ", beans=" + beans +
                    ", cups=" + cups +
                    ", money=" + money +
                    '}';
        }
    }
}
