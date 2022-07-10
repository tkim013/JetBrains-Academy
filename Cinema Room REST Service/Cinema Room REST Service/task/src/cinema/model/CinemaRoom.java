package cinema.model;

import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CinemaRoom {

    private int total_rows;
    private int total_columns;

    private List<Seat> seatList;
    private List<Boolean> availability;
    private Map<String, Integer> indexMap;
    private Map<UUID, Integer> tokenIndexMap;
    private Statistics statistics;

    public CinemaRoom() {
        this.total_rows = 9;
        this.total_columns = 9;

        List<Seat> seats = new ArrayList<>();
        List<Boolean> availability = new ArrayList<>();
        Map<String, Integer> index = new HashMap<>();
        Statistics stats = new Statistics();

        for (int i = 0; i < total_rows; i++) {

            for (int j = 0; j < total_columns; j++) {

                int price;

                String sb = String.valueOf(i + 1) + (j + 1);
                price = i + 1 <= 4 ? 10 : 8;
                seats.add(new Seat(i + 1, j + 1, price));
                index.put(sb, seats.size() - 1);
                availability.add(true);
                stats.setNumber_of_available_seats(stats.getNumber_of_available_seats() + 1);
            }
        }

        this.seatList = seats;
        this.indexMap = index;
        this.availability = availability;
        this.tokenIndexMap = new HashMap<>();
        this.statistics = stats;
    }

    public CinemaRoom(int total_rows, int total_columns) {
        this.total_rows = total_rows;
        this.total_columns = total_columns;

        List<Seat> seats = new ArrayList<>();
        List<Boolean> availability = new ArrayList<>();
        Map<String, Integer> index = new HashMap<>();
        Statistics stats = new Statistics();

        for (int i = 0; i < total_rows; i++) {

            for (int j = 0; j < total_columns; j++) {

                int price;

                String sb = String.valueOf(i + 1) + (j + 1);
                price = i + 1 <= 4 ? 10 : 8;
                seats.add(new Seat(i + 1, j + 1, price));
                index.put(sb, seats.size() - 1);
                availability.add(true);
                stats.setNumber_of_available_seats(stats.getNumber_of_available_seats() + 1);
            }
        }

        this.seatList = seats;
        this.indexMap = index;
        this.availability = availability;
        this.tokenIndexMap = new HashMap<>();
        this.statistics = stats;
    }

    public int getTotal_rows() {
        return total_rows;
    }

    public void setTotal_rows(int total_rows) {
        this.total_rows = total_rows;
    }

    public int getTotal_columns() {
        return total_columns;
    }

    public void setTotal_columns(int total_columns) {
        this.total_columns = total_columns;
    }

    public List<Seat> getSeatList() {
        return seatList;
    }

    public void setSeatList(List<Seat> seatList) {
        this.seatList = seatList;
    }

    public List<Boolean> getAvailability() {
        return availability;
    }

    public void setAvailability(List<Boolean> availability) {
        this.availability = availability;
    }

    public Map<String, Integer> getIndexMap() {
        return indexMap;
    }

    public void setIndexMap(Map<String, Integer> indexMap) {
        this.indexMap = indexMap;
    }

    public Map<UUID, Integer> getTokenIndexMap() {
        return tokenIndexMap;
    }

    public void setTokenIndexMap(Map<UUID, Integer> tokenIndexMap) {
        this.tokenIndexMap = tokenIndexMap;
    }

    public Statistics getStatistics() {
        return statistics;
    }

    public void setStatistics(Statistics statistics) {
        this.statistics = statistics;
    }
}
