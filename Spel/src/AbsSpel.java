// Class Imports
import java.util.Set;
import java.util.SortedSet;

public interface AbsSpel {
    /* Suggested internals.
    private Set             dictionary;
    private SortedSetSeries spinDict;
    */

    /* Java doesn't allow constructors in Interfaces!  It should. */
    /* Anyway, it is recommended that you implement (at least)    */
    /* the following two.                                         */

    /**
    Make a new Spel but with no dictionary!
    public AbsSpel();
    */

    /**
    Make a new Spel reading from file for the dictionary!
    public AbsSpel(String from);
    */

    /**
    Initialize the Spel object using file for the dictionary.
    */
    public void buildDictionary(String dictFile);

    /**
    Invoke Spel's checker on the textfile.
    */
    public void checker(String textFile);

    /**
    Spel should list possible corrections for that word.
    Generally, this will be called from checker on every detected
    misspelled word.
    It is public though, as other applications might want this
    functionality.  (Also, this would be useful for debugging.)
    */
    public void corrector(String word);

    /* A possible MAIN for stand-alone version!
    public static void main(String[] args) {
        // args[0]  = Dictionary file
        // args[1+] = Files to spell-check
        if (args.length < 2) {
            System.out.println("Spel : dictionary_file file_to_spellcheck+");
            System.exit(-1);
        }

        // Start a new speler object, initialized with a dictionary.
        Spel spell = new Spel(args[0]);

        // Spell check and correct those files!
        for (int i=1; i < args.length; i++) {
            spell.checker(args[i]);
        }
    }
    */
}
