package org.example;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AoCPuzzle4 {

    public static void main(String[] args) {
        // Bestand ophalen als InputStream
        InputStream inputStream = AoCPuzzle4.class.getClassLoader().getResourceAsStream("puzzel4.txt");

        if (inputStream == null) {
            System.out.println("Bestand niet gevonden!");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
//            List<List<Character>> list = new ArrayList<>();
//            List<Character> lines = new ArrayList<>();
            String line;

            List<List<Character>> listOfLists = new ArrayList<>();
            // Lees het bestand regel voor regel
            while ((line = reader.readLine()) != null) {
                List<Character> charList = new ArrayList<>();
                for (char c : line.toCharArray()) {
                    charList.add(c);
                }
                listOfLists.add(charList);// Voeg regels toe als string-arrays
            }
            countXmas(listOfLists);
//            int patternCount = countPatternOccurrences(lines, "M.S", ".A.", "M.S");
            System.out.println("Totaal: " +  countXmas(listOfLists));

            // Combineer kolommen en voeg ze toe aan de lijst
//            combineColumns(lines, list);
//
//            combineDiagonals(lines, list);
//
////            // Print de volledige lijst voor verificatie
////            System.out.println("Volledige lijst inclusief kolommen:");
////
////            for (String[] array : list) {
////                System.out.println(String.join("", array));
////            }
//
//            int xmasCount = countPatternOccurrences(list, "XMAS");
//            int samxCount = countPatternOccurrences(list, "SAMX");
//
//            System.out.println("Aantal keer 'XMAS': " + xmasCount);
//            System.out.println("Aantal keer 'SAMX': " + samxCount);
//            System.out.println("Totaal: " + (samxCount+xmasCount));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void combineColumns(List<String> lines, List<String[]> list) {
        if (lines.isEmpty()) {
            return;
        }

        int columnCount = lines.get(0).length();
        StringBuilder[] columnBuilders = new StringBuilder[columnCount];

        // Initialiseer StringBuilders voor elke kolom
        for (int i = 0; i < columnCount; i++) {
            columnBuilders[i] = new StringBuilder();
        }

        // Voeg elk karakter van dezelfde kolom toe aan de corresponderende StringBuilder
        for (String line : lines) {
            for (int i = 0; i < line.length(); i++) {
                columnBuilders[i].append(line.charAt(i));
            }
        }

        // Zet de kolomstrings om naar een array en voeg toe aan de lijst
        for (StringBuilder columnBuilder : columnBuilders) {
            list.add(columnBuilder.toString().split("")); // Voeg kolomstrings toe als string-arrays
        }
    }


    private static void combineDiagonals(List<String> lines, List<String[]> list) {
        int rowCount = lines.size();
        int columnCount = lines.get(0).length();

        // Diagonalen van linksboven naar rechtsonder (↘️)
        for (int startRow = 0; startRow < rowCount; startRow++) {
            StringBuilder diagonal = new StringBuilder();
            for (int row = startRow, col = 0; row < rowCount && col < columnCount; row++, col++) {
                diagonal.append(lines.get(row).charAt(col));
            }
            if (diagonal.length() >= 4) {
                list.add(diagonal.toString().split(""));
            }
        }
        for (int startCol = 1; startCol < columnCount; startCol++) {
            StringBuilder diagonal = new StringBuilder();
            for (int row = 0, col = startCol; row < rowCount && col < columnCount; row++, col++) {
                diagonal.append(lines.get(row).charAt(col));
            }
            if (diagonal.length() >= 4) {
                list.add(diagonal.toString().split(""));
            }
        }

        // Diagonalen van linksonder naar rechtsboven (↗️)
        for (int startRow = rowCount - 1; startRow >= 0; startRow--) {
            StringBuilder diagonal = new StringBuilder();
            for (int row = startRow, col = 0; row >= 0 && col < columnCount; row--, col++) {
                diagonal.append(lines.get(row).charAt(col));
            }
            if (diagonal.length() >= 4) {
                list.add(diagonal.toString().split(""));
            }
        }
        for (int startCol = 1; startCol < columnCount; startCol++) {
            StringBuilder diagonal = new StringBuilder();
            for (int row = rowCount - 1, col = startCol; row >= 0 && col < columnCount; row--, col++) {
                diagonal.append(lines.get(row).charAt(col));
            }
            if (diagonal.length() >= 4) {
                list.add(diagonal.toString().split(""));
            }
        }
    }

    private static int countPatternOccurrences(List<String[]> list, String pattern) {
        int count = 0;
        for (String[] array : list) {
            String joinedString = String.join("", array);
            int index = joinedString.indexOf(pattern);
            while (index != -1) {
                count++;
                index = joinedString.indexOf(pattern, index + 1);
            }
        }
        return count;
    }

    private static int countPatternOccurrences(List<String> lines, String pattern1, String pattern2, String pattern3) {
        int count = 0;
        // Regex voor M.S en .A. waarbij de punt staat voor elk willekeurig karakter
        String regex1 = pattern1.replace(".", "\\."); // Escapen van de punt in regex
        String regex2 = pattern2.replace(".", "\\."); // Escapen van de punt in regex
        String regex3 = pattern3.replace(".", "\\."); // Escapen van de punt in regex

        // We doorlopen de tekstregel voor regel en zoeken naar de patronen
        for (int i = 0; i < lines.size() - 2; i++) {
            String first = lines.get(i);
            String second = lines.get(i + 1);
            String third = lines.get(i + 2);

            // Maak een regex voor de rijen
            String row1 = first.replace(".", "."); // Elke punt is een willekeurig teken
            String row2 = second.replace(".", "."); // Elke punt is een willekeurig teken
            String row3 = third.replace(".", "."); // Elke punt is een willekeurig teken

            // Gebruik regex om te zoeken naar het patroon
            Pattern pattern = Pattern.compile(regex1 + ".*" + regex2 + ".*" + regex3);
            Matcher matcher = pattern.matcher(row1 + row2 + row3);

            // Check of het patroon voorkomt
            while (matcher.find()) {
                count++;
            }
        }
        return count;
    }

    private static int countXmas(List<List<Character>> listOfLists){
        int count = 0;
        for (int i = 1; i < listOfLists.size() - 1; i++) { // Sla eerste en laatste regel over
            List<Character> charList = listOfLists.get(i);

            for (int j = 1; j < charList.size() - 1; j++) { // Sla eerste en laatste character over
                if (charList.get(j) == 'A') {
                   String diagonaal1 = listOfLists.get(i-1).get(j-1) + "A" + listOfLists.get(i+1).get(j+1);
                   String diagonaal2 = listOfLists.get(i-1).get(j+1) + "A" + listOfLists.get(i+1).get(j-1);
                   if (diagonaal1.equalsIgnoreCase("sam") && diagonaal2.equalsIgnoreCase("sam")) {
                       System.out.println("################ SAM 2x gevonden in regel: " + i);
                       System.out.println("Diagonaal 1 = " + diagonaal1);
                       System.out.println("Diagonaal 2 = " + diagonaal2);
                       count++;
                    }
                    if (diagonaal1.equalsIgnoreCase("sam") && diagonaal2.equalsIgnoreCase("mas")) {
                        System.out.println("################ SAM en MAS gevonden in regel: " + i);
                        System.out.println("Diagonaal 1 = " + diagonaal1);
                        System.out.println("Diagonaal 2 = " + diagonaal2);
                        count++;
                    }
                    if (diagonaal1.equalsIgnoreCase("mas")  && diagonaal2.equalsIgnoreCase("mas")) {
                        System.out.println("################ MAS 2x gevonden in regel: " + i);
                        System.out.println("Diagonaal 1 = " + diagonaal1);
                        System.out.println("Diagonaal 2 = " + diagonaal2);
                        count++;
                    }
                    if (diagonaal1.equalsIgnoreCase("mas")  && diagonaal2.equalsIgnoreCase("sam")) {
                        System.out.println("################ MAS en SAM gevonden in regel: " + i);
                        System.out.println("Diagonaal 1 = " + diagonaal1);
                        System.out.println("Diagonaal 2 = " + diagonaal2);
                        count++;
                    }
                }
            }
        }
        return count;
    }

}
