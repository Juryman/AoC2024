package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;


public class AoCPuzzle2 {


    public static void main(String[] args) {
        InputStream inputStream = AoCPuzzle2.class.getClassLoader().getResourceAsStream("puzzel2.txt");

        if (inputStream == null) {
            System.out.println("Bestand niet gevonden.");
            return;
        }

        List<List<Integer>> reports = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] numbers = line.split("\\s+");
                List<Integer> integerArray = List.of(Arrays.stream(numbers)
                        .map(Integer::valueOf)
                        .toArray(Integer[]::new));
                reports.add(integerArray);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


        Map<Boolean, Long> resultCountsA = reports.parallelStream()
                .collect(Collectors.partitioningBy(
                        AoCPuzzle2::isSafeList,
                        Collectors.counting()
                ));

        Map<Boolean, Long> resultCountsB = reports.parallelStream()
                .collect(Collectors.partitioningBy(
                        AoCPuzzle2::isSafeListWithRemoval,
                        Collectors.counting()
                ));

        long uitkomstPuzzle1 = resultCountsA.getOrDefault(true, 0L);
        long uitkomstPuzzle2 = resultCountsB.getOrDefault(true, 0L);

        System.out.println("uitkomst puzzel 1: " + uitkomstPuzzle1);
        System.out.println("uitkomst puzzel 2: " + uitkomstPuzzle2);

    }



    public static boolean isSafeList(List<Integer> list) {
        if (list == null || list.size() < 2) {
            return true;
        }

        boolean isAscending = true;
        boolean isDescending = true;

        for (int i = 1; i < list.size(); i++) {
            int diff = Math.abs(list.get(i) - list.get(i - 1));

            if (diff < 1 || diff > 3) {
                System.out.println("Report verschil te groot of te klein: " + list);
                return false;
            }

            if (list.get(i) < list.get(i - 1)) {
                System.out.println("Report niet oplopend: " + list);
                isAscending = false;
            }

            if (list.get(i) > list.get(i - 1)) {
                System.out.println("Report niet aflopend: " + list);
                isDescending = false;
            }
        }
        boolean isSafe = isAscending || isDescending;

        if (isSafe) {
            System.out.println("Report is safe: " + list);
        }

        return isSafe;
    }

    public static boolean isSafeListWithRemoval(List<Integer> list) {
        if (isSafeList(list)) {
            return true;
        }

        for (int i = 0; i < list.size(); i++) {
            List<Integer> sublist = new ArrayList<>(list); // Maak een kopie
            sublist.remove(i); // Verwijder het i-de element
            if (isSafeList(sublist)) {
                System.out.println("Lijst werd veilig na verwijderen van index " + i + ": " + list);
                return true;
            }
        }
        return false;
    }



}