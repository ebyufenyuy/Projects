//Ellison Yufenuyuy
//Finish implementing a program; WordSearch.java

//Imports
import java.io.InputStream; 
import java.io.*;
import java.util.NoSuchElementException;
import java.util.Scanner;

/** class WordSearch
 *
 * This class implements a Word Search game.
 * The main() method reads in a puzzle from System.in .
 * main() also reads a "search word" from args[0] .
 *
 * The class implements several static methods that search for
 * the "search word" throughout the puzzle and that handle
 * successful and unsuccessful searches.
 *
 * There are several methods below that must be completed by the student.
 */

public class WordSearch {

    static char ToLower(char x) {
        if (('A' <= x) && (x <= 'Z')) {
            x += 'a' - 'A';
        }
        return x;
    }

    static char ToUpper(char x) {
        if (('a' <= x) && (x <= 'z')) {
            x += 'A' - 'a';
        }
        return x;
    }

/** GetPuzzle()
     *
     * Read in a puzzle from an InputStream (System.in or a FileInputStream).
     * Return the result as a two-dimensional array of chars.
     * @returns - two-dimensional array of chars with the puzzle read from 'inp'
     */

    static char[][] GetPuzzle(InputStream inp) {
        char[][] retval = null;
        try {
            Scanner scan = new Scanner(inp);

            String txtLine = scan.nextLine().trim(); // Read first line and see how long it is.
            final int D = txtLine.length();

            retval = new char[D][D]; // Create output two-dimensional array.

            for (int n = 0; n < D; n++) {
                retval[0][n] = txtLine.charAt(n);
            }

            for (int lineNum = 1; lineNum < D; lineNum++) { // Get and save the rest of the lines.
                txtLine = scan.nextLine();
                for (int n = 0; n < D; n++) {
                    retval[lineNum][n] = txtLine.charAt(n);
                }
            }
            scan.close(); //Close Scan
        } catch (NoSuchElementException e) {
            System.err.println("ERROR: not enough input data: " + e);
            retval = null;
        } catch (IndexOutOfBoundsException f) {
            System.err.println("ERROR: line too short: " + f);
            retval = null;
        }

        final int D = retval.length; // Convert the entire output array to lowercase.
        for (int lineNum = 0; lineNum < D; lineNum++) {
            for (int colNum = 0; colNum < D; colNum++) {
                retval[lineNum][colNum] = ToLower(retval[lineNum][colNum]);
            }
        }
        return retval; //Return the resulting puzzle
    }

    /** SearchFwd()
     *
     * Search through 'puzzle' for occurrences of 'word' that happen
     * in a forward left-to-right order.
     * If we find 'word', convert it to uppercase in the puzzle and
     * return true.
     * Otherwise return false.
     */

    static boolean SearchFwd(char[][] puzzle, String word) {
        final int D = puzzle.length;
        for (int row = 0; row < D; row++) {
            String thisRow = new String(puzzle[row]);
            final int indx = thisRow.indexOf(word);
            if (indx >= 0) { //Convert chars to uppercase
                ConvertRowToUpper(puzzle, row, indx, indx + word.length());
                return true;
            }
        }
        return false;
    }



    static void ConvertRowToUpper(char[][] puzzle, int row, int start, int end) {
        for (int n = start; n < end; n++) {
            puzzle[row][n] = ToUpper(puzzle[row][n]);
        }
    }

    static boolean SearchBwd(char[][] puzzle, String word) {
    final int D = puzzle.length;
    
    for (int row = 0; row < D; row++) { //Convert the row to a string 
        String thisRow = new String(puzzle[row]);

        for (int start = D - word.length(); start >= 0; start--) {
            String substring = thisRow.substring(start, start + word.length());

            if (substring.equals(word)) {
                ConvertRowToUpper(puzzle, row, start, start + word.length());
                return true;
            }
        }
    }

    return false;
}


    static boolean SearchDown(char[][] puzzle, String word) {
    final int D = puzzle.length;

    for (int currColumns = 0; currColumns < D; currColumns++) {
        String thiscurrColumns = "";
        for (int row = 0; row < D; row++) {
            thiscurrColumns += puzzle[row][currColumns];
        }


        final int indx = thiscurrColumns.indexOf(word); //Find index of the word in the currColumnsumn
        if (indx >= 0) {
            for (int row = indx; row < indx + word.length(); row++) {
                puzzle[row][currColumns] = ToUpper(puzzle[row][currColumns]);
            }
            return true;
        }
    }

    return false; //Word is not found in any currColumnsumn
}


    static boolean SearchUp(char[][] puzzle, String word) {
    final int D = puzzle.length;

    for (int currColumns = 0; currColumns < D; currColumns++) {
        String thiscurrColumns = "";
        for (int row = D - 1; row >= 0; row--) {
            thiscurrColumns += puzzle[row][currColumns];
        }

        String reversedWord = reverseString(word);
        final int indx = thiscurrColumns.indexOf(reversedWord);

        if (indx >= 0) {
            for (int row = D - 1 - indx; row > D - 1 - indx - word.length(); row--) {
                puzzle[row][currColumns] = ToUpper(puzzle[row][currColumns]);
            }
            return true;
        }
    }

    return false;
}

static String reverseString(String str) { //Reverses a string

    char[] charArray = str.toCharArray();
    for (int i = 0, j = charArray.length - 1; i < j; i++, j--) {
        char temp = charArray[i];
        charArray[i] = charArray[j];
        charArray[j] = temp;
    }
    return new String(charArray);
}


    static void PrintPuzzle(char[][] puzzle) {
    final int D = puzzle.length;

    for (int row = 0; row < D; row++) {
        for (int currColumns = 0; currColumns < D; currColumns++) {
            System.out.print(puzzle[row][currColumns] + " ");
        }
        System.out.println(); //Move to the next line for the following row
    }
}


    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("ERROR: no command line argument"); //Print error and exit the program if no search word/argument is provided
            return; 
        }

        String searchWord = args[0].toLowerCase();
        char[][] puzzle = null;
        puzzle = GetPuzzle(System.in);

        boolean success = SearchFwd(puzzle, searchWord); //Search for the search word in various directions
        success |= SearchBwd(puzzle, searchWord);
        success |= SearchUp(puzzle, searchWord);
        success |= SearchDown(puzzle, searchWord);

        if (success) {
            PrintPuzzle(puzzle);
        } else {
            System.out.println("NO MATCH"); //Print resulting puzzle or "NO MATCH" if the search was not successful
        }
    }
}






