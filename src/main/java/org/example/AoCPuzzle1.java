package org.example;

import java.io.*;
import java.util.*;


public class AoCPuzzle1 {
    public static void main(String[] args) {
        InputStream inputStream = AoCPuzzle1.class.getClassLoader().getResourceAsStream("puzzel1.txt");

        if (inputStream == null) {
            System.out.println("Bestand niet gevonden.");
            return;
        }

        List<Integer> firstRow = new ArrayList<>();
        List<Integer> secondRow = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] numbers = line.split("\\s+");

                for (int i = 0; i < numbers.length; i++) {
                    if (i == 0) {
                        firstRow.add(Integer.parseInt(numbers[i]));
                    } else if (i == 1) {
                        secondRow.add(Integer.parseInt(numbers[i]));
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        Collections.sort(firstRow);
        Collections.sort(secondRow);

        Map<Integer, Integer> secondRowFrequency = getFrequencyMap(secondRow);

        int uitkomstPuzzle1 = 0;
        int uitkomstPuzzle2 = 0;

        for (int i = 0; i < firstRow.size() ; i++) {
            uitkomstPuzzle1 += Math.abs(firstRow.get(i) - secondRow.get(i));
        }

        for (Integer number : firstRow) {
            int frequencyInSecondRow = secondRowFrequency.getOrDefault(number, 0);
//           System.out.println("Getal " + number + " komt " + frequencyInSecondRow + " keer voor in de tweede lijst.");
            uitkomstPuzzle2 += frequencyInSecondRow * number;
        }

        System.out.println("uitkomst puzzel 1: " + uitkomstPuzzle1);
        System.out.println("uitkomst puzzel 2: " + uitkomstPuzzle2);

    }

    private static Map<Integer, Integer> getFrequencyMap(List<Integer> list) {
        Map<Integer, Integer> frequencyMap = new HashMap<>();
        for (Integer number : list) {
            frequencyMap.put(number, frequencyMap.getOrDefault(number, 0) + 1);
        }
        return frequencyMap;
    }
}