package phonebook;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PhoneBook {

    static long linearSearchTime;

    public static void main(String[] args) {

//        final String directoryFilePath = "src/resources/directory.txt";
//        final String findFilePath = "src/resources/find.txt";
        final String directoryFilePath = "src/resources/small_directory.txt";
        final String findFilePath = "src/resources/small_find.txt";

        List<String> directory = new ArrayList<>();
        List<String> find = new ArrayList<>();

        try {
            directory = loadFile(directoryFilePath);
            find = loadFile(findFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        linearSearch(find, directory);

        bubbleSortAndJumpSearch(find, directory);

        quickSortAndBinarySearch(find, directory);

        hashMapAndSearch(find, directory);
    }

    public static List<String> loadFile(String filePath) throws IOException {

        return new ArrayList<>(Files.readAllLines(Path.of(filePath)));
    }

    public static List<String> bubbleSort(List<String> directory) {

        long startTime;
        long endTime;

        int n = directory.size();

        List<String> sortList = new ArrayList<>(directory);

        String temp;

        System.out.println("Start bubble sort...");
        startTime = System.currentTimeMillis();
        for (int i=0; i < n; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (sortList.get(j).substring(8).trim().compareTo(sortList.get(j + 1).substring(8).trim()) > 0) {
                    //swap elements
                    temp = sortList.get(j);
                    sortList.set(j, sortList.get(j + 1));
                    sortList.set(j + 1, temp);
                }
            }
        }
        endTime = System.currentTimeMillis();

        System.out.printf("Sorting time: %d min. %d sec. %d ms.%n",
                ((endTime - startTime) / 1000) / 60, ((endTime - startTime) / 1000) % 60, (endTime - startTime) % 1000);

        return sortList;
    }

    public static void linearSearch(List<String> find, List<String> directory) {


        long startTime;
        long endTime;
        int count = 0;

        System.out.println("Start searching (linear search)...");
        startTime = System.currentTimeMillis();
        for (String name : find) {
            //search directory for name
            for (String line : directory) {
                if (name.equals(line.substring(8).trim())) {
                    count++;
                    break;
                }
            }
        }

        endTime = System.currentTimeMillis();
        linearSearchTime = endTime - startTime;
//        Date date = new Date(endTime - startTime);
//        System.out.printf("Found %d / %d entries. Time taken: %3$tM min. %3$tS sec. %3$tL ms.%n", count, find.size(), date);
        System.out.printf("Found %d / %d entries. Time taken: %d min. %d sec. %d ms.%n", count, find.size(),
                ((endTime - startTime) / 1000) / 60, ((endTime - startTime) / 1000) % 60, (endTime - startTime) % 1000);
    }

    private static void jumpSearch(List<String> find, List<String> directory) {

        long startTime;
        long endTime;
        int count = 0;

        startTime = System.currentTimeMillis();

        int blockSize = (int) Math.floor(Math.sqrt(directory.size()));

        for (String name : find) {

            int currentLastIndex = blockSize - 1;

            // Jump to next block as long as target element is > currentLastIndex
            // and the array end has not been reached
            while (currentLastIndex < directory.size() && name.compareTo(directory.get(currentLastIndex).substring(8).trim()) > 0) {
                currentLastIndex += blockSize;
            }

            // Find accurate position of target element using Linear Search
            for (int currentSearchIndex = currentLastIndex - blockSize + 1;
                 currentSearchIndex <= currentLastIndex && currentSearchIndex < directory.size(); currentSearchIndex++) {
                if (name.equals(directory.get(currentSearchIndex).substring(8).trim())) {
                    count++;
                    break;
                }
            }
        }

        endTime = System.currentTimeMillis();

        System.out.printf("Found %d / %d entries. Time taken: %d min. %d sec. %d ms.%n", count, find.size(),
                ((endTime - startTime) / 1000) / 60, ((endTime - startTime) / 1000) % 60, (endTime - startTime) % 1000);
    }

    private static void bubbleSortAndJumpSearch(List<String> find, List<String> directory) {

        int n = directory.size();
        int blockSize = (int) Math.floor(Math.sqrt(directory.size()));
        long startTime;
        long endTime;
        long bubbleSortEndTime;
        int count = 0;
        boolean tookTooLong = false;

        List<String> sortList = new ArrayList<>(directory);

        String temp;

        System.out.println("Start searching (bubble sort + jump search)...");
        startTime = System.currentTimeMillis();
        for (int i=0; i < n; i++) {
            for (int j = 1; j < (n - i); j++) {
                if (sortList.get(j-1).substring(8).trim().compareTo(sortList.get(j).substring(8).trim()) > 0) {
                    //swap elements
                    temp = sortList.get(j - 1);
                    sortList.set(j - 1, sortList.get(j));
                    sortList.set(j, temp);
                }
            }
            //if bubble sort takes longer than 10 * linear search time stop
            if (System.currentTimeMillis() - startTime > linearSearchTime * 10) {
                tookTooLong = true;
                break;
            }
        }
        bubbleSortEndTime = System.currentTimeMillis();

        if (tookTooLong) {

            //if search took too long, linear search
            for (String name : find) {
                //search directory for name
                for (String line : directory) {
                    if (name.equals(line.substring(8).trim())) {
                        count++;
                        break;
                    }
                }
            }

            endTime = System.currentTimeMillis();
        } else {

            for (String name : find) {

                int currentLastIndex = blockSize - 1;

                // Jump to next block as long as target element is > currentLastIndex
                // and the array end has not been reached
                while (currentLastIndex < sortList.size() && name.compareTo(sortList.get(currentLastIndex).substring(8).trim()) > 0) {
                    currentLastIndex += blockSize;
                }

                // Find accurate position of target element using Linear Search
                for (int currentSearchIndex = currentLastIndex - blockSize + 1;
                     currentSearchIndex <= currentLastIndex && currentSearchIndex < sortList.size(); currentSearchIndex++) {
                    if (name.equals(sortList.get(currentSearchIndex).substring(8).trim())) {
                        count++;
                        break;
                    }
                }
            }
            endTime = System.currentTimeMillis();
        }
        System.out.printf("Found %d / %d entries. Time taken: %d min. %d sec. %d ms.%n", count, find.size(),
                ((endTime - startTime) / 1000) / 60, ((endTime - startTime) / 1000) % 60, (endTime - startTime) % 1000);

        if (tookTooLong) {
            System.out.printf("Sorting time: %d min. %d sec. %d ms. - STOPPED, moved to linear search%n",
                    ((bubbleSortEndTime - startTime) / 1000) / 60, ((bubbleSortEndTime - startTime) / 1000) % 60, (bubbleSortEndTime - startTime) % 1000);
        } else {
            System.out.printf("Sorting time: %d min. %d sec. %d ms.%n",
                    ((bubbleSortEndTime - startTime) / 1000) / 60, ((bubbleSortEndTime - startTime) / 1000) % 60, (bubbleSortEndTime - startTime) % 1000);
        }
        System.out.printf("Searching time: %d min. %d sec. %d ms.%n",
                ((endTime - bubbleSortEndTime) / 1000) / 60, ((endTime - bubbleSortEndTime) / 1000) % 60, (endTime - bubbleSortEndTime) % 1000);
    }

    public static List<String> quickSort(List<String> directory) {

        int n = directory.size();
        long startTime;
        long endTime;

        List<String> sortList = new ArrayList<>(directory);

        System.out.println("Start quick sort...");
        startTime = System.currentTimeMillis();
        quickSort(sortList, 0, n - 1);
        endTime = System.currentTimeMillis();

        System.out.printf("Sorting time: %d min. %d sec. %d ms.%n",
                ((endTime - startTime) / 1000) / 60, ((endTime - startTime) / 1000) % 60, (endTime - startTime) % 1000);

        return sortList;
    }

    // A utility function to swap two elements
    static void swap(List<String> list, int i, int j)
    {
        String temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }

    /* This function takes last element as pivot, places
   the pivot element at its correct position in sorted
   array, and places all smaller (smaller than pivot)
   to left of pivot and all greater elements to right
   of pivot */
    static int partition(List<String> list, int low, int high)
    {

        // pivot
        String pivot = list.get(high).substring(8).trim();

        // Index of smaller element and
        // indicates the right position
        // of pivot found so far
        int i = (low - 1);

        for(int j = low; j <= high - 1; j++)
        {

            // If current element is smaller
            // than the pivot
            if (list.get(j).substring(8).trim().compareTo(pivot) < 0)
            {

                // Increment index of
                // smaller element
                i++;
                swap(list, i, j);
            }
        }
        swap(list, i + 1, high);
        return (i + 1);
    }

    /* The main function that implements QuickSort
          list --> ArrayList to be sorted,
          low --> Starting index,
          high --> Ending index */
    static void quickSort(List<String> list, int low, int high)
    {
        if (low < high)
        {

            // pi is partitioning index, arr[p]
            // is now at right place
            int pi = partition(list, low, high);

            // Separately sort elements before
            // partition and after partition
            quickSort(list, low, pi - 1);
            quickSort(list, pi + 1, high);
        }
    }

    static void binarySearch(List<String> find, List<String> directory) {

        int count = 0;
        long startTime;
        long endTime;
        List<String> sortList = new ArrayList<>(directory);

        System.out.println("Start binary search...");
        startTime = System.currentTimeMillis();
        for (String name : find) {

            int result = binarySearch(sortList, name);

            if (result != -1) {
                count++;
            }
        }
        endTime = System.currentTimeMillis();

        System.out.printf("Found %d / %d entries. Time taken: %d min. %d sec. %d ms.%n", count, find.size(),
                ((endTime - startTime) / 1000) / 60, ((endTime - startTime) / 1000) % 60, (endTime - startTime) % 1000);
    }

    static int binarySearch(List<String> list, String name)
    {
        int l = 0;
        int r = list.size() - 1;
        int m = (l + r) / 2;

        while (l <= r) {
//            int m = l + (r - l) / 2;

            // If x greater, ignore left half
            if (list.get(m).substring(8).trim().compareTo(name) < 0) {
                l = m + 1;
            } else
                // Check if x is present at mid
                if (list.get(m).substring(8).trim().equals(name)) {
                    return m;
                }
                // If x is smaller, ignore right half
                else {
                    r = m - 1;
                }
            m = (l + r) / 2;
        }

        // if we reach here, then element was
        // not present
        return -1;
    }

    static void quickSortAndBinarySearch(List<String> find, List<String> directory) {

        int n = directory.size();
        int count = 0;
        long startTime;
        long endTime;
        long sortEndTime;

        List<String> sortList = new ArrayList<>(directory);

        System.out.println("Start searching (quick sort + binary search)...");
        startTime = System.currentTimeMillis();
        quickSort(sortList, 0, n - 1);
        sortEndTime = System.currentTimeMillis();

        for (String name : find) {

            int result = binarySearch(sortList, name);

            if (result != -1) {
                count++;
            }
        }
        endTime = System.currentTimeMillis();

        System.out.printf("Found %d / %d entries. Time taken: %d min. %d sec. %d ms.%n", count, find.size(),
                ((endTime - startTime) / 1000) / 60, ((endTime - startTime) / 1000) % 60, (endTime - startTime) % 1000);
        System.out.printf("Sorting time: %d min. %d sec. %d ms.%n",
                ((sortEndTime - startTime) / 1000) / 60, ((sortEndTime - startTime) / 1000) % 60, (sortEndTime - startTime) % 1000);
        System.out.printf("Searching time: %d min. %d sec. %d ms.%n",
                ((endTime - sortEndTime) / 1000) / 60, ((endTime - sortEndTime) / 1000) % 60, (endTime - sortEndTime) % 1000);
    }

    static HashMap<String, String> createHashMap(List<String> directory) {

        HashMap<String, String> map = new HashMap<>();

        for (String s : directory) {
            map.put(s.substring(8).trim(), s.substring(0, 8).trim());
        }

        return map;
    }

    static void hashMapAndSearch(List<String> find, List<String> directory) {

        int count = 0;
        long startTime;
        long endTime;
        long createEndTime;

        List<String> sortList = new ArrayList<>(directory);

        System.out.println("Start searching (hash table)...");
        startTime = System.currentTimeMillis();
        HashMap<String, String> map = createHashMap(sortList);
        createEndTime = System.currentTimeMillis();
        for (String name : find) {
            if (map.get(name) != null) {
                count++;
            }
        }
        endTime = System.currentTimeMillis();

        System.out.printf("Found %d / %d entries. Time taken: %d min. %d sec. %d ms.%n", count, find.size(),
                ((endTime - startTime) / 1000) / 60, ((endTime - startTime) / 1000) % 60, (endTime - startTime) % 1000);
        System.out.printf("Creating time: %d min. %d sec. %d ms.%n",
                ((createEndTime - startTime) / 1000) / 60, ((createEndTime - startTime) / 1000) % 60, (createEndTime - startTime) % 1000);
        System.out.printf("Searching time: %d min. %d sec. %d ms.%n",
                ((endTime - createEndTime) / 1000) / 60, ((endTime - createEndTime) / 1000) % 60, (endTime - createEndTime) % 1000);
    }
}
