// Class Imports
import java.io.Reader;
import java.io.FileReader;
import java.io.StreamTokenizer;
// Exception Imports
import java.io.FileNotFoundException;
import java.io.IOException;

public class ParseWord {
    private String filename;
    private Reader theReader;
    private StreamTokenizer theTokenizer;
    private boolean theEnd;

    ParseWord(String name) throws FileNotFoundException {
        filename     = name;
        theReader    = new FileReader(filename);
        theTokenizer = new StreamTokenizer(theReader);

        // Skip end-of-lines.  We don't care.
        theTokenizer.eolIsSignificant(false);

        // Only allow letters in words to check.
        theTokenizer.wordChars(65, 90);  // A .. Z
        theTokenizer.wordChars(97, 122); // a .. z

        // The tokenizer allowed "." in words.
        theTokenizer.ordinaryChar(46); // \. is now a regular character.

        // QUOTE (") was treated as a comment character.
        theTokenizer.ordinaryChar(34); // \" is now a regular character.

        // Turn all words into lowercase.
        theTokenizer.lowerCaseMode(true);
    }

    public String nextWord() throws IOException {
        if (theEnd) {
            return "";
        }

        while (true) { // Skipping anything that's not a word or the END.
            theTokenizer.nextToken();
            if (theTokenizer.ttype == StreamTokenizer.TT_WORD) {
                return theTokenizer.sval;
            }
            if (theTokenizer.ttype == StreamTokenizer.TT_EOF) {
                theEnd = true;
                return "";
            }
        }
    }
}
