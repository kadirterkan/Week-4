package week4.booklist;

import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        ArrayList<Book> bookList = returnMockBookList();

        Map<String, String> map = bookList.stream()
                .collect(Collectors.toMap((book -> book.getName()), (book -> book.getAuthorName())));

        ArrayList<Book> filteredList = bookList.stream()
                .filter(book -> book.getPageNumber() > 100)
                .collect(Collectors.toCollection(ArrayList::new));

        for (Map.Entry<String, String> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }

        System.out.println();

        for (Book filtered : filteredList) {
            System.out.println(filtered.toString());
        }
    }

    private static ArrayList<Book> returnMockBookList() {
        return new ArrayList<>(Arrays.asList(new Book("Angel Named Billy, An", 132, "Vick Speere", new Date(1921 - 1900, 9, 06)), // 10 06 1921
                new Book("Sunnyside", 155, "Cam Churm", new Date(2019 - 1900, 5, 23)), // 06 23 2019
                new Book("Dirty Pictures", 207, "Celene Benneton", new Date(1977 - 1900, 0, 14)), // 01 14 1977
                new Book("Savages", 32, "Laurie Blazic", new Date(1903 - 1900, 2, 22)), // 03 22 1903
                new Book("Swing Kids", 142, "Leopold Belton", new Date(1945 - 1900, 6, 3)), // 07 03 1945
                new Book("Stray Dogs (Sag-haye velgard)", 253, "Ladonna Eliet", new Date(1986 - 1900, 5, 7)), // 06 07 1986
                new Book("Silk Stockings", 72, "Philip Commins", new Date(1928 - 1900, 2, 12)), // 03 12 1928
                new Book("Bullhead (Rundskop)", 144, "Brannon Lebbon", new Date(2009 - 1900, 9, 25)), // 10 25 2009
                new Book("Alice in Wonderland", 262, "Fons Oleszczak", new Date(1954 - 1900, 2, 22)), // 03 / 22 / 1954
                new Book("All Cheerleaders Die", 285, "Twyla Gaspar", new Date(1941 - 1900, 0, 19)))); // 01 / 19 / 1941
    }
}
