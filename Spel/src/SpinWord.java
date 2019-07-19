
public class SpinWord {
    private String spunWord;
    private char   mark;
    private static char defaultDelim = '.';

    public SpinWord (String word) {
        this(defaultDelim, word);
    }

    public SpinWord(char delim, String word) {
        mark = delim;

        if (word.indexOf(mark) >= 0) { // Word contains mark already!
            spunWord = "" + mark;
        } else { // Okay, put mark at the beginning.
            spunWord = "" + mark + word;
        }
    }

    public static void setMark(char delim) {
        defaultDelim = delim;
    }

    public boolean hasNext() {
        // Word was empty, or mark was in word.  No spins available!
        if (spunWord == "" + mark) {
            return false;
        }

        // Here, spunWord is at least of length two.
        int last = spunWord.length() - 1;

        // If mark is at last position, we've finished spinning it.
        if (spunWord.charAt(last) == mark) {
            return false;
        }
        // Otherwise, more spins remain.
        return true;
    }

    public String next() {
        // Word was empty, or mark was in word.  No spins available!
        // And they asked anyway.  Give them an empty string.
        if (spunWord == "" + mark) {
            return "";
        }

        String reply = spunWord;
        // Set up next one: Rotate final char to front.
        spunWord = spunWord.charAt(spunWord.length() - 1)
                + spunWord.substring(0, spunWord.length() - 1);

        return reply;
    }

    public String unSpin() {
        int pos = spunWord.indexOf(mark);
        return spunWord.substring(pos + 1) + spunWord.substring(0, pos);
    }

    public static String unSpin(String spun) {
        int pos = spun.indexOf(defaultDelim);
        return spun.substring(pos + 1) + spun.substring(0, pos);
    }
}
