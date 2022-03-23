package week4.threads;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {
        ArrayList<Integer> list = returnTheList();

        CountNumbers countOdd = new CountNumbers(list, Boolean.TRUE);
        CountNumbers countEven = new CountNumbers(list, Boolean.FALSE);

        ExecutorService executorOdd = Executors.newFixedThreadPool(4);
        ExecutorService executorEven = Executors.newFixedThreadPool(4);

        for (int i = 0; i < 4; i++) {
            executorOdd.execute(countOdd);
            executorEven.execute(countEven);
        }

        executorOdd.shutdown();
        executorEven.shutdown();
        try {
            executorOdd.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            executorEven.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println(countOdd.getCommon());
            System.out.println(countEven.getCommon());
        }
    }

    private static ArrayList<Integer> returnTheList() {
        ArrayList<Integer> list = new ArrayList<>();

        for (int i = 1; i <= 10000; i++) {
            list.add(i);
        }

        return list;
    }
}
