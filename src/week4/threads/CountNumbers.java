package week4.threads;

import java.util.ArrayList;

public class CountNumbers implements Runnable {

    private ArrayList<Integer> list;
    private ArrayList<Integer> common = new ArrayList<>();
    private Boolean countOdds;
    private final Object lock = new Object();
    private Integer count = 0;

    public CountNumbers(ArrayList<Integer> list, Boolean countOdds) {
        this.list = list;
        this.countOdds = countOdds;
    }

    @Override
    public void run() {
        synchronized (lock) {
            Integer startValue = Integer.valueOf(count);

            count++;

            for (int i = startValue * 2500; i < startValue + 2500; i++ ) {
                if (countOdds) {
                    if (list.get(i) % 2 != 0) common.add(list.get(i));
                } else {
                    if (list.get(i) % 2 == 0) common.add(list.get(i));
                }
            }
        }
    }

    public ArrayList<Integer> getCommon() {
        return common;
    }
}
