package tracker;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {

        tracker();
    }

    public static void tracker() {

        int idCounter = 1;

        String command;
        List<String> notifyList = new ArrayList<>();
        Map<String, String> emailMap = new HashMap<>();
        SortedMap<String, String> idMap = new TreeMap<>();
        Map<String, List<Double>> javaGradeMap = new HashMap<>();
        Map<String, List<Double>> dsaGradeMap = new HashMap<>();
        Map<String, List<Double>> databasesGradeMap = new HashMap<>();
        Map<String, List<Double>> springGradeMap = new HashMap<>();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Learning Progress Tracker");

        while (true) {

            command = scanner.nextLine().toLowerCase();

            if (command.isBlank()) {
                System.out.println("no input");
                break;
            }

            switch (command) {

                case "add students": {

                    int count = 0;
                    int code;

                    String input;
                    String email;

                    System.out.println("Enter student credentials or 'back' to return");

                    while (true) {

                        input = scanner.nextLine();

                        if (input.equalsIgnoreCase("back")) {
                            System.out.println("Total " + count + " students have been added.");
                            break;
                        }

                        email = input.substring(input.lastIndexOf(' ') + 1).toLowerCase();
                        if (emailMap.containsKey(email)) {
                            System.out.println("This email is already taken.");
                            continue;
                        }

                        code = verify(input);

                        switch (code) {
                            case 0: {
                                System.out.println("The student has been added.");
                                count++;
                                emailMap.put(email, input.substring(0, input.lastIndexOf(" ")) + " " + idCounter);
                                idMap.put(String.valueOf(idCounter++), "0 0 0 0");
                                break;
                            }
                            case 1: {
                                System.out.println("Incorrect credentials.");
                                break;
                            }
                            case 2: {
                                System.out.println("Incorrect first name.");
                                break;
                            }
                            case 3: {
                                System.out.println("Incorrect last name.");
                                break;
                            }
                            case 4: {
                                System.out.println("Incorrect email.");
                                break;
                            }
                        }
                    }
                    break;
                }

                case "list": {

                    if (idMap.isEmpty()) {
                        System.out.println("No students found");
                        break;
                    }

                    System.out.println("Students:");

                    for (Map.Entry<String, String> mapElement : idMap.entrySet()) {
                        System.out.println(mapElement.getKey());
                    }

                    break;
                }

                case "add points": {

                    String data;
                    String id;
                    String [] dataArray;

                    System.out.println("Enter an id and points or 'back' to return");

                    while (true) {

                        data = scanner.nextLine();

                        if (data.equalsIgnoreCase("back")) {
                            break;
                        }

                        try {
                            id = data.substring(0, data.indexOf(' '));
                            data = data.substring(data.indexOf(' ') + 1);

                            if (!idMap.containsKey(id)) {
                                System.out.printf("No student is found for id=%s%n", id);
                                continue;
                            }

                            if (!checkData(data)) {
                                System.out.println("Incorrect points format");
                            } else {

                                dataArray = data.split(" ");

                                idMap.put(id, addData(idMap.get(id), data));

                                if (Integer.parseInt(dataArray[0]) > 0) {
                                    if (javaGradeMap.containsKey(id)) {
                                        javaGradeMap.get(id).add(Double.parseDouble(dataArray[0]));
                                    } else {
                                        List<Double> list = new ArrayList<>();
                                        list.add(Double.parseDouble(dataArray[0]));
                                        javaGradeMap.put(id, list);
                                    }
                                    if (checkGrade(javaGradeMap.get(id), 0)) {
                                        notifyList.add(id + " Java");
                                    }
                                }
                                if (Integer.parseInt(dataArray[1]) > 0) {
                                    if (dsaGradeMap.containsKey(id)) {
                                        dsaGradeMap.get(id).add(Double.parseDouble(dataArray[1]));
                                    } else {
                                        List<Double> list = new ArrayList<>();
                                        list.add(Double.parseDouble(dataArray[1]));
                                        dsaGradeMap.put(id, list);
                                    }
                                    if (checkGrade(dsaGradeMap.get(id), 1)) {
                                        notifyList.add(id + " DSA");
                                    }
                                }
                                if (Integer.parseInt(dataArray[2]) > 0) {
                                    if (databasesGradeMap.containsKey(id)) {
                                        databasesGradeMap.get(id).add(Double.parseDouble(dataArray[2]));
                                    } else {
                                        List<Double> list = new ArrayList<>();
                                        list.add(Double.parseDouble(dataArray[2]));
                                        databasesGradeMap.put(id, list);
                                    }
                                    if(checkGrade(databasesGradeMap.get(id), 2)) {
                                        notifyList.add(id + " Databases");
                                    }
                                }
                                if (Integer.parseInt(dataArray[3]) > 0) {
                                    if (springGradeMap.containsKey(id)) {
                                        springGradeMap.get(id).add(Double.parseDouble(dataArray[3]));
                                    } else {
                                        List<Double> list = new ArrayList<>();
                                        list.add(Double.parseDouble(dataArray[3]));
                                        springGradeMap.put(id, list);
                                    }
                                    if (checkGrade(springGradeMap.get(id), 3)) {
                                        notifyList.add(id + " Spring");
                                    }
                                }

                                System.out.println("Points updated");
                            }
                        } catch (StringIndexOutOfBoundsException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                }

                case "find": {

                    System.out.println("Enter an id or 'back' to return");

                    while (true) {

                        String id = scanner.nextLine();

                        if (id.equalsIgnoreCase("back")) {
                            break;
                        }

                        if (!idMap.containsKey(id)) {
                            System.out.printf("No student is found for id=%s%n", id);
                            continue;
                        }

                        String[] data = idMap.get(id).split(" ");

                        System.out.printf("%s points: Java=%d; DSA=%d; Databases=%d; Spring=%d%n", id,
                                Integer.parseInt(data[0]),
                                Integer.parseInt(data[1]),
                                Integer.parseInt(data[2]),
                                Integer.parseInt(data[3]));
                    }

                    break;
                }

                case "statistics": {

                    String[] pop = popularity(javaGradeMap.size(), dsaGradeMap.size(), databasesGradeMap.size(), springGradeMap.size());
                    String[] act = activity(javaGradeMap, dsaGradeMap, databasesGradeMap, springGradeMap);
                    String[] diff = difficulty(javaGradeMap, dsaGradeMap, databasesGradeMap, springGradeMap);

                    System.out.println("Type the name of a course to see details or 'back' to quit");
                    System.out.println("Most popular: " + pop[0]);
                    System.out.println("Least popular: " + pop[1]);
                    System.out.println("Highest activity: " + act[0]);
                    System.out.println("Lowest activity: " + act[1]);
                    System.out.println("Easiest course: " + diff[0]);
                    System.out.println("Hardest course: " + diff[1]);

                    while (true) {

                        String course = scanner.nextLine().toLowerCase();

                        if (course.equalsIgnoreCase("back")) {
                            break;
                        }

                        switch (course) {

                            case "java": {

                                System.out.println("Java");
                                System.out.println("id     points completed");
                                courseStat(javaGradeMap, 0);
                                break;
                            }

                            case "dsa": {

                                System.out.println("DSA");
                                System.out.println("id     points completed");
                                courseStat(dsaGradeMap, 1);
                                break;
                            }

                            case "databases": {

                                System.out.println("Databases");
                                System.out.println("id     points completed");
                                courseStat(databasesGradeMap, 2);
                                break;
                            }

                            case "spring": {

                                System.out.println("Spring");
                                System.out.println("id     points completed");
                                courseStat(springGradeMap, 3);
                                break;
                            }

                            default: {
                                System.out.println("Unknown course");
                                break;
                            }
                        }
                    }
                    break;
                }

                case "notify": {

                    Set<String> notified = new HashSet<>();

                    for (String s : notifyList) {
                        String id = s.substring(0, s.indexOf(" "));
                        String course = s.substring(s.indexOf(" ") + 1);
                        String email = "";
                        String fullName = "";

                        for (Map.Entry<String, String> entry : emailMap.entrySet()) {
                            if (entry.getValue()
                                    .substring(entry.getValue().lastIndexOf(" ") + 1).equals(id)) {
                                email = entry.getKey();
                                fullName = (entry.getValue().substring(0, entry.getValue().lastIndexOf(" ")));
                                break;
                            }
                        }

                        System.out.printf("To: %s%n", email);
                        System.out.println("Re: Your Learning Progress");
                        System.out.printf("Hello, %s! You have accomplished our %s course!%n", fullName, course);

                        notified.add(email);
                    }
                    notifyList.clear();

                    System.out.println("Total " + notified.size() + " students have been notified.");
                    break;
                }

                case "back": {

                    System.out.println("Enter 'exit' to exit the program");
                    break;
                }

                case "exit": {
                    System.out.println("Bye!");
                    System.exit(0);
                }

                default: {
                    System.out.println("Unknown command!");
                }
            }
        }
    }

    private static int verify(String s) {

        String[] array = new String[2];

        try {

            //check for invalid information
            if (Arrays.stream(s.split(" ")).count() < 3
            ) {
                return 1;
            }

            //String into String array, element 0 - first name, element 1 - lastname, element 2 - email
            array = new String[]{s.substring(0, s.indexOf(' ')),
                    s.substring(s.indexOf(' ') + 1, s.lastIndexOf(' ')),
                    s.substring(s.lastIndexOf(' ') + 1)};

        } catch (StringIndexOutOfBoundsException e) {
            e.printStackTrace();
        }

        if (!checkName(array[0])) {
            return 2;
        } else if (!checkName(array[1])) {
            return 3;
        } else if (!checkEmail(array[2])) {
            return 4;
        }

        return 0;
    }

    private static boolean checkName(String name) {

        String pattern = "^[a-zA-Z '-]*$";
        Pattern p = Pattern.compile(pattern);
        Matcher matcher = p.matcher(name);

        //check for pattern, minimum length, starts and ends with - and '
        if (!matcher.find() || name.length() < 2 || name.startsWith("-") || name.startsWith("'") ||
                name.endsWith("-") || name.endsWith("'")) {
            return false;
        }
        //check for consecutive - ' chars
        for (int i = 0; i < name.length(); i++) {
            if (name.charAt(i) == '-' || name.charAt(i) == '\'') {
                if(name.charAt(i - 1) == '-' || name.charAt(i - 1) == '\'' ||
                        name.charAt(i + 1) == '-' || name.charAt(i + 1) == '\'') {
                    return false;
                }
            }
        }

        return true;
    }

    private static boolean checkEmail(String email) {

        String pattern = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9]+$";
        Pattern p = Pattern.compile(pattern);
        Matcher matcher = p.matcher(email);

        return matcher.find();
    }

    private static boolean checkData(String data) {

        String[] values = data.split(" ");
        List<String> list = Arrays.asList(values);

        if (list.size() != 4) {
            return false;
        }

        try {
            for (String s : list) {
                if (Integer.parseInt(s) < 0) {
                    return false;
                }
            }
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }

    private static String addData(String mapData, String data) {

        StringBuilder sb = new StringBuilder();
        String[] md = mapData.split(" ");
        String[] d = data.split(" ");

        for (int i = 0; i < 4; i++) {
            sb.append(Integer.parseInt(md[i]) + Integer.parseInt(d[i]))
                    .append(" ");
        }
        return sb.toString().trim();
    }

    private static String[] popularity(int java, int dsa, int databases, int spring) {

        int max;
        int min;

        StringBuilder most = new StringBuilder();
        StringBuilder least = new StringBuilder();
        String[] ret = new String[2];

        //check for no data
        if (java == 0 && dsa == 0 && databases == 0 && spring == 0) {
            ret[0] = "n/a";
            ret[1] = "n/a";
            return ret;
        }

        max = java;
        max = Math.max(max, dsa);
        max = Math.max(max, databases);
        max = Math.max(max, spring);

        if (max == java && java != 0) {
            most.append("Java, ");
        }
        if (max == dsa && dsa != 0) {
            most.append("DSA, ");
        }
        if (max == databases && databases != 0) {
            most.append("Databases, ");
        }
        if (max == spring && spring != 0) {
            most.append("Spring, ");
        }
        ret[0] = most.substring(0, most.length() - 2);

        min = java;
        min = Math.min(min, dsa);
        min = Math.min(min, databases);
        min = Math.min(min, spring);

        if (min == java && min != max && java != 0) {
            least.append("Java, ");
        }
        if (min == dsa && min != max && dsa != 0) {
            least.append("DSA, ");
        }
        if (min == databases && min != max && databases != 0) {
            least.append("Databases, ");
        }
        if (min == spring && min != max && spring != 0) {
            least.append("Spring, ");
        }
        if (least.toString().isEmpty()) {
            ret[1] = "n/a";
        } else {
            ret[1] = least.substring(0, least.length() - 2);
        }

        return ret;
    }

    public static String[] activity(Map<String, List<Double>> javaMap,
                                    Map<String, List<Double>> dsaMap,
                                    Map<String, List<Double>> databasesMap,
                                    Map<String, List<Double>> springMap
    ) {

        int javaCount = 0;
        int dsaCount = 0;
        int databasesCount = 0;
        int springCount = 0;
        int max;
        int min;

        StringBuilder highest = new StringBuilder();
        StringBuilder lowest = new StringBuilder();
        String[] ret = new String[2];

        //check for empty maps
        if (javaMap.isEmpty() && dsaMap.isEmpty() && databasesMap.isEmpty() && springMap.isEmpty()) {
            ret[0] = "n/a";
            ret[1] = "n/a";
            return ret;
        }

        for (List<Double> list : javaMap.values()) {
            for (Double d : list) {
                javaCount += 1;
            }
        }
        for (List<Double> list : dsaMap.values()) {
            for (Double d : list) {
                dsaCount += 1;
            }
        }
        for (List<Double> list : databasesMap.values()) {
            for (Double d : list) {
                databasesCount += 1;
            }
        }
        for (List<Double> list : springMap.values()) {
            for (Double d : list) {
                springCount += 1;
            }
        }

        max = javaCount;
        max = Math.max(max, dsaCount);
        max = Math.max(max, databasesCount);
        max = Math.max(max, springCount);

        if (max == javaCount && javaCount != 0) {
            highest.append("Java, ");
        }
        if (max == dsaCount && dsaCount != 0) {
            highest.append("DSA, ");
        }
        if (max == databasesCount && databasesCount != 0) {
            highest.append("Databases, ");
        }
        if (max == springCount && springCount != 0) {
            highest.append("Spring, ");
        }
        ret[0] = highest.substring(0, highest.length() - 2);

        min = javaCount;
        min = Math.min(min, dsaCount);
        min = Math.min(min, databasesCount);
        min = Math.min(min, springCount);

        if (min == javaCount && min != max && javaCount != 0) {
            lowest.append("Java, ");
        }
        if (min == dsaCount && min != max && dsaCount != 0) {
            lowest.append("DSA, ");
        }
        if (min == databasesCount && min != max && databasesCount != 0) {
            lowest.append("Databases, ");
        }
        if (min == springCount && min != max && springCount != 0) {
            lowest.append("Spring, ");
        }
        if (lowest.toString().isEmpty()) {
            ret[1] = "n/a";
        } else {
            ret[1] = lowest.substring(0, lowest.length() - 2);
        }

        return ret;
    }

    public static String[] difficulty(Map<String, List<Double>> javaGrade,
                                      Map<String, List<Double>> dsaGrade,
                                      Map<String, List<Double>> databasesGrade,
                                      Map<String, List<Double>> springGrade
    ) {

        double javaSum = 0;
        double dsaSum = 0;
        double databasesSum = 0;
        double springSum = 0;
        double javaAverage;
        double dsaAverage;
        double databasesAverage;
        double springAverage;
        double max;
        double min;

        String[] ret = new String[2];
        StringBuilder easiest = new StringBuilder();
        StringBuilder hardest = new StringBuilder();

        //check for empty lists
        if (javaGrade.isEmpty() && dsaGrade.isEmpty() && databasesGrade.isEmpty() && springGrade.isEmpty()) {
            ret[0] = "n/a";
            ret[1] = "n/a";
            return ret;
        }

        for (List<Double> list : javaGrade.values()) {
            for (Double d : list) {
                javaSum += d;
            }
        }
        javaAverage = javaSum / javaGrade.size();
        for (List<Double> list : dsaGrade.values()) {
            for (Double d : list) {
                dsaSum += d;
            }
        }
        dsaAverage = dsaSum / dsaGrade.size();
        for (List<Double> list : databasesGrade.values()) {
            for (Double d : list) {
                databasesSum += d;
            }
        }
        databasesAverage = databasesSum / databasesGrade.size();
        for (List<Double> list : springGrade.values()) {
            for (Double d : list) {
                springSum += d;
            }
        }
        springAverage = springSum / springGrade.size();

        max = javaAverage;
        max = Math.max(max, dsaAverage);
        max = Math.max(max, databasesAverage);
        max = Math.max(max, springAverage);

        if (max == javaAverage && javaAverage != 0) {
            easiest.append("Java, ");
        }
        if (max == dsaAverage && dsaAverage != 0) {
            easiest.append("DSA, ");
        }
        if (max == databasesAverage && databasesAverage != 0) {
            easiest.append("Databases, ");
        }
        if (max == springAverage && springAverage != 0) {
            easiest.append("Spring, ");
        }
        ret[0] = easiest.substring(0, easiest.length() - 2);

        min = javaAverage;
        min = Math.min(min, dsaAverage);
        min = Math.min(min, databasesAverage);
        min = Math.min(min, springAverage);

        if (min == javaAverage && min != max && javaAverage != 0) {
            hardest.append("Java, ");
        }
        if (min == dsaAverage && min != max && dsaAverage != 0) {
            hardest.append("DSA, ");
        }
        if (min == databasesAverage && min != max && databasesAverage != 0) {
            hardest.append("Databases, ");
        }
        if (min == springAverage && min != max && springAverage != 0) {
            hardest.append("Spring, ");
        }
        if (hardest.toString().isEmpty()) {
            ret[1] = "n/a";
        } else {
            ret[1] = hardest.substring(0, hardest.length() - 2);
        }

        return ret;
    }

    private static void courseStat(Map<String, List<Double>> courseGradeMap, int type) {

        double points;
        double completed;

        List<Student> studentList = new ArrayList<>();

        for (Map.Entry<String, List<Double>> entry : courseGradeMap.entrySet())  {

            Student s = new Student();
            s.setId(entry.getKey());

            points = 0;
            for (Double d : entry.getValue()) {
                points += d;
            }
            s.setPoints((int) points);

            completed = 0;
            switch (type) {
                case 0: {
                    completed = points / 6;
                    break;
                }
                case 1: {
                    completed = points / 4;
                    break;
                }
                case 2: {
                    completed = points / 4.8;
                    break;
                }
                case 3: {
                    completed = points / 5.5;
                    break;
                }
                default: {
                    break;
                }
            }

            s.setCompleted(completed);

            studentList.add(s);
        }

        studentList.sort(new byPoints());
        for (Student s : studentList) {
            System.out.printf("%s %d %.1f%%%n", s.getId(), s.getPoints(), s.getCompleted());
        }
    }

    private static boolean checkGrade(List<Double> doubles, int type) {

        int count = 0;
        for (Double d : doubles) {
            count += d;
        }

        switch (type) {
            case 0: {
                if (count >= 600) {
                    return true;
                }
                break;
            }
            case 1: {
                if (count >= 400) {
                    return true;
                }
                break;
            }
            case 2: {
                if (count >= 480) {
                    return true;
                }
                break;
            }
            case 3: {
                if (count >= 550) {
                    return true;
                }
                break;
            }
            default: {
                break;
            }
        }
        return false;
    }

    static class byPoints implements Comparator<Student> {

        public int compare(Student a, Student b) {

            int pointCompare = b.getPoints() - a.getPoints();
            int idCompare = a.getId().compareTo(b.getId());

            return pointCompare == 0 ? idCompare : pointCompare;
        }

    }
}