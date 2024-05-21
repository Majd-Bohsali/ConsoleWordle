import java.util.HashMap;
import java.util.Scanner;
import java.util.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main
{
    
	public static void main(String[] args) {
	    int guessNum = 1; 
	    String[] allGuesses = new String[6];
	    Scanner scanner = new Scanner (System.in);
	    ArrayList<String> wordsList = new ArrayList<>();
	    BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader("words.txt"));
			String line = reader.readLine();

			while (line != null) {
				wordsList.add(line); 
				// read next line
				line = reader.readLine();
			}

			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String mainWord = wordsList.get((int)(Math.random() * ((wordsList.size() + 1))));
		int[][] guessStatus = new int[6][5]; // represents the state of the guess, 0 is not in answer, 1 is green, 2 is yellow
		
		for(int i = 0; i < 6; i++) {
		    String userGuess = scanner.nextLine();
		    
		    while(userGuess.length() != 5) { 
		        System.out.println("Please enter a 5 letter word");
		        userGuess = scanner.nextLine();
		    }
		    
		    while(!wordsList.contains(userGuess)) { 
		        System.out.println("Please enter a valid word");
		        
		        userGuess = scanner.nextLine();
		    }
		    
		    
            allGuesses[guessNum-1] = userGuess.toLowerCase(); 
            findGreen(mainWord, allGuesses[guessNum-1], guessStatus[guessNum-1]); 
            findYellow(mainWord, allGuesses[guessNum-1], guessStatus[guessNum-1]); 
            System.out.print("\033[H\033[2J");
            for(int g = 0; g < guessNum; g++) {
                displayGuessResult(allGuesses[g], guessStatus[g]);
            }
            if(mainWord.equals(allGuesses[guessNum-1])) {
                System.out.println("You Win");
                return; 
            }
            guessNum++;
		}
		
		System.out.println("You Lost, the word was " + mainWord);
    }
    
  
    
    public static void findGreen(String mainWord, String guessWord, int[] guessStatus) { 

        String[] arrMain = mainWord.split("");
        String[] arrGuess = guessWord.split(""); 
        
        for(int i = 0; i < arrGuess.length; i++) {
            if(arrMain[i].equals(arrGuess[i])) {
                guessStatus[i] = 1;  
            }
        }
    } 
    public static void findYellow(String mainWord, String guessWord, int[] guessStatus) {
        boolean[] yellowStatus = new boolean[5];
        boolean[] usedMain = new boolean[5]; // keep track of yellow letters
        String[] arrMain = mainWord.split("");
        String[] arrGuess = guessWord.split("");
        
        // saves already green as set in the usedMain array
        for (int i = 0; i < arrGuess.length; i++) {
            if (arrGuess[i].equals(arrMain[i])) {
               usedMain[i] = true; // Mark position as used
            }
        }
        
        for (int i = 0; i < arrGuess.length; i++) {
            if (guessStatus[i] != 1) { // Skip green letters
                for (int j = 0; j < arrMain.length; j++) {
                    if (!usedMain[j] && arrGuess[i].equals(arrMain[j])) {
                        guessStatus[i] = 2; // Sets to yellow
                        usedMain[j] = true; // Mark position used
                        break; 
                    }
                }
            }
        }
    }
    
    public static void displayGuessResult(String guessWord, int[] guessStatus) { 
        String[] arrGuess = guessWord.split("");
        for(int i = 0; i < guessStatus.length; i++) {
            if(guessStatus[i] == 1) 
                System.out.print("\u001b[42m" + arrGuess[i]);
            else if(guessStatus[i] == 2) 
                System.out.print("\u001b[43m" + arrGuess[i]);
            else 
                System.out.print("\u001b[0m" + arrGuess[i]);
        }
        System.out.println("\u001b[0m");
    } 
}
