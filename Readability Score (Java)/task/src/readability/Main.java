package readability;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner (System.in);

        try {
            String input = readFileAsString(args[0]);

            System.out.println("The text is: \n" + input);
            System.out.println();
            System.out.println("Words: " + getWordAmount(input));
            System.out.println("Sentences: " + getSentenceAmount(input));
            System.out.println("Characters: " + getCharacterAmount(input));
            System.out.println("Syllables: " + syllablesCount(input));
            System.out.println("Polysyllables: " + polysyllablesCount(input));
            System.out.print("Enter the score you want to calculate (ARI, FK, SMOG, CL, all):");
            String choice = scanner.next();
            System.out.println();
            printStatements(choice,input);




        } catch (IOException e) {
            System.out.println("Cannot read file: " + e.getMessage());
        }

    }

    public static void printStatements (String choice, String input) {

        switch (choice) {

            case "ARI":
                System.out.println("Automated Readability Index: " + ariIndex(input) + "about " + giveGrade(ariIndex(input)) + "-year-olds).");
                break;
            case "FK":
                System.out.println("Flesch–Kincaid readability tests: " + fleschIndex(input) + "about " + giveGrade(fleschIndex(input)) + "-year-olds).");
                break;
            case "SMOG":
                System.out.println("Simple Measure of Gobbledygook: " + smogIndex(input) + "about " + giveGrade(smogIndex(input)) + "-year-olds).");
                break;
            case "CL":
                System.out.println("Coleman–Liau index: " + liauIndex(input) + "about " + giveGrade(liauIndex(input)) + "-year-olds).");
                break;
            case "all":
                System.out.println("Automated Readability Index: "+ ariIndex(input) + "  about " + giveGrade(ariIndex(input)) + "-year-olds.");
                System.out.println("Flesch–Kincaid readability tests: " + fleschIndex(input) + " about " + giveGrade(fleschIndex(input)) + "-year-olds.");
                System.out.println("Simple Measure of Gobbledygook: " + smogIndex(input) + " about " + giveGrade(smogIndex(input)) + "-year-olds.");
                System.out.println("Coleman–Liau index: " + liauIndex(input) + " about " + giveGrade(liauIndex(input)) + "-year-olds.");
                System.out.println();
                System.out.println("This text should be understood in average by " + getAverageIndex(input)+ "-year-olds.");
                break;


        }
    }
    
    public static double getAverageIndex (String input) {
        
        int ari = Integer.parseInt(giveGrade(ariIndex(input)));
        int flesch = Integer.parseInt(giveGrade(fleschIndex(input)));
        int smog = Integer.parseInt(giveGrade(smogIndex(input)));
        int liau = Integer.parseInt(giveGrade(liauIndex(input)));
        
        return (ari+flesch+smog+liau)*1.0/ 4.0;



    }

    public static int getWordAmount (String input) {

        String [] words = input.split("\\s");

        return words.length;

    }

    public static int getSentenceAmount (String input) {

        String [] sentences = input.split("[.!?]");

        return sentences.length;

    }

    public static double ariIndex (String input) {

        double score = 4.71 * getCharacterAmount(input)/ getWordAmount(input) + 0.5 * getWordAmount(input)/getSentenceAmount(input) -21.43;
        double roundedNum = Math.round(100.0 * score) / 100.0;

        return roundedNum;
        
    }

    public static double fleschIndex (String input) {


        double index = 0.39 * getWordAmount(input)/getSentenceAmount(input) + 11.8 * syllablesCount(input)/getWordAmount(input) - 15.59 ;
        return Math.round (100.0 * index) / 100.0;
    }

    public static double smogIndex (String input) {

        double index = 1.043 * Math.sqrt(polysyllablesCount(input)* (30/getSentenceAmount(input))) + 3.1291;
        return Math.round (100.0* index) / 100.0;

    }

    public static double liauIndex (String input) {

        int charCount = getCharacterAmount(input);
        int wordCount = getWordAmount(input);
        int sentenceCount = getSentenceAmount(input);

        double L =  (double) charCount / wordCount   * 100;
        double S =  (double) sentenceCount / wordCount * 100;

        double index = 0.0588 * L - 0.296 * S - 15.8;
        return Math.round (100.0 * index) / 100.0;

    }

    public static String giveGrade (double input) {

        int score = (int) Math.ceil(input);


        switch (score) {

            case 1:
                return "6";
            case 2:
                return "7";
            case 3:
                return "8";
            case 4:
                return "9";
            case 5:
                return "10";
            case 6:
                return "11";
            case 7:
                return "12";
            case 8:
                return "13";
            case 9:
                return "14";
            case 10:
                return "15";
            case 11:
                return "16";
            case 12:
                return "17";
            case 13:
                return "18";
            case 14:
                return "22";
            default:
                if (score > 14) {
                    return "24";
                }
                else {
                    return "No grade";
                }
        }
    }



    public static int getCharacterAmount (String input){

        int count = 0;

        for (int i = 0; i < input.length(); i++) {

            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\n') {
                count++;
            }
        }
        return count;
    }

    public static String readFileAsString (String filename) throws IOException {

    return Files.readString(Paths.get(filename));

    }

    public static int calculatingPolysyllables (String word){

        if (calculatingVowels(word)>2) {
            return 1;
        }

        return 0;
    }


    public static int syllablesCount (String input) {

        int count = 0;
        String [] words = input.split(" ");

        for (String word : words) {
            count += calculatingSyllables(word);
        }

        return count;
    }





    public static int polysyllablesCount (String input) {

        int count = 0;
        String [] words = input.split(" ");

        for (String word : words) {
            count += calculatingPolysyllables(word);
        }
        return count;
    }




    public static int calculatingSyllables (String word) {

        if (calculatingVowels(word) == 0) {
            return 1;
        }

        return calculatingVowels(word);

    }

    public static int calculatingVowels (String word) {

        int count = 0;
        char prev = '\0';

            for ( int i = 0; i < word.length(); i++) {
                char c = word.charAt(i);
                if (isVowel(c) && !isVowel(prev)) {
                    count++;
                }
                prev = c;
            }

            if (word.charAt(word.length()-1) == 'e') {
                count--;
            }

            return count;
    }

    public static boolean isVowel (char c) {
        return "aeiouyAEIOUY".indexOf(c) != -1;
    }

}