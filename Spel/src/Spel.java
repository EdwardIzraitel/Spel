import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.SortedSet;

public class Spel implements AbsSpel {
	private Set<String> dic;
	private SortedSetSeries<String> spinDict;

	public Spel() {
		this.spinDict = new SortedSetSeries<String>();
		this.dic = new HashSet<String>();
	}

	public Spel(String textName) {
		this.spinDict = new SortedSetSeries<String>();
		this.dic = new HashSet<String>();
	}

	@Override
	public void buildDictionary(String dictFile) {
		try {
			// Opens the file for reading
			File newFile = new File(dictFile);
			BufferedReader file = new BufferedReader(new FileReader(newFile));
			String line;
			// Reads until no more lines
			while ((line = file.readLine()) != null) {
				// Checks to see if the word is not all lower case
				if (Character.isUpperCase(line.charAt(0))) {
					// Fixes the word
					String fixed = line.replace("\\p{P}", "").toLowerCase();
					// Creates a spinner for the word
					SpinWord spin = new SpinWord(fixed);
					// Adds the word to our main dictionary
					this.dic.add(fixed);
					// Spins the word and adds it to our secondary Dictionary
					// of spun words
					while (spin.hasNext()) {
						this.spinDict.add(fixed.length(), spin.next());
					}
					continue;
				}
				// Spins the word and adds it to our secondary Dictionary
				// of spun words
				SpinWord spin = new SpinWord(line);
				this.dic.add(line);
				while (spin.hasNext()) {
					this.spinDict.add(line.length(), spin.next());
				}
			}
			file.close();
		} catch (IOException e) {

		}

	}

	@Override
	public void checker(String textFile) {

		Scanner sc = null;
		Set<String> correct = new HashSet<String>();
		try {
			// Saves the file into the scanner
			sc = new Scanner(new File(textFile));
			// Keeps reading until no lines
			while (sc.hasNextLine()) {
				// Reads the next line
				Scanner sc2 = new Scanner(sc.nextLine());
				// Reads word by word
				while (sc2.hasNext()) {
					// Fixes the word
					String s = sc2.next().replaceAll("\\p{P}", "").toLowerCase();
					// Continues if the word is a proper word
					if (this.dic.contains(s)) {
						continue;
					}
					// Adds the word that needs to be corrected to a set
					correct.add(s);

				}
			}
		} catch (Exception e) {

		}
		// Finds possible corrections and prints them.
		for(String s: correct) {
			corrector(s);
		}

	}

	@Override
	public void corrector(String word) {
		// Create 4 list strings for each type of fix
		// create a set to store the possible corrections
		Set<String> fixedWords = new HashSet<String>();
		// Adds all possible corrections of word to our fixed words set
		omission(word, fixedWords);
		addition(word, fixedWords);
		swap(word, fixedWords);
		typo(word, fixedWords);

		// prints out the 'word ? corrections'
		System.out.print(word + " ?");
		for (String s : fixedWords) {
			System.out.print(" " + s);
		}
		System.out.println();
	}

	// Takes in a word that is not in our main dictionary
	// Finds all the possible omissions and adds it to our main fixed words set
	// returns null
	private void omission(String word, Set<String> fixed) {
		// Create a spinner and a list
		SpinWord spin = new SpinWord(word);
		// Spins the word until it can't
		while (spin.hasNext()) {
			// Finds possible corrections
			findOm(spin.next(), fixed);
		}
		// Spins it one more time to check for a possible omission at the last index
		findOm(spin.next(), fixed);
	}

	// Takes in a spun word and a list to store corrections in
	// returns null
	private void findOm(String spun, Set<String> fixed) {
		// Finds the subset of possible spun words of our 'spun'
		SortedSet<String> set = this.spinDict.subSet(spun.length(), spun + "@", spun + "~");
		// fixes the word and adds it to our list until there are no more words in our
		// set
		for (String s : set) {
			fixed.add(SpinWord.unSpin(s));
		}
	}

	// Takes in a word that is not in our main dictionary
	// Finds all the possible additions to the word and removes them and adds
	// possible corrections to our main set
	// Returns null
	private void addition(String word, Set<String> fixed) {
		// Create a list to store our words in
		// there are no additions if the word is of length 1
		if (word.length() < 2) {
			return;
		}
		String additionWord;
		// Loops and removes one letter from the word
		// checks if the word exists in our main dictionary without the letter 'i'
		for (int i = 0; i < word.length(); i++) {
			additionWord = word.substring(0, i) + word.substring(i + 1, word.length());
			if (this.dic.contains(additionWord)) {
				fixed.add(additionWord);
			}
		}

	}

	// Takes in a word that is not in our main dictionary
	// Checks for possible adjacent swaps in our word and adds possible corrections
	// to our main set
	// Returns null
	private void swap(String word, Set<String> fixed) {

		// No swaps if the length is 1
		if (word.length() < 2) {
			return;
		}
		// Loops and swaps adjacent letters in our word
		for (int i = 0; i < word.length() - 1; i++) {
			char i1 = word.charAt(i);
			char i2 = word.charAt(i + 1);
			String potWord = word.substring(0, i) + i2 + i1 + word.substring(i + 2);
			if (this.dic.contains(potWord)) {
				fixed.add(potWord);
			}
		}
	}

	// Takes in a word that is not in our main dictionary
	// Checks for a typo in our word and adds possible corrections to our set
	// Returns null
	private void typo(String word, Set<String> fixed) {
		SpinWord spin = new SpinWord(word);
		// No typos if the length of our word is 1
		if (word.length() < 2) {
			return;
		}
		String spun;
		// Spins until can't
		while (spin.hasNext()) {
			spun = spin.next();
			// Removes the final letter in our word as it might be a typo
			spun = spun.substring(0, spun.length() - 1);
			// Find all possible words in our secondary dictionary
			SortedSet<String> set = this.spinDict.subSet(word.length(), spun + "@", 
										spun + "~");
			for (String s : set) {
				fixed.add(SpinWord.unSpin(s));
			}
		}
	}

///////////////////////////////////////////
	public static void main(String args[]) {
		Spel spel = new Spel("words.txt");
		spel.buildDictionary("words.txt");
		spel.checker("bucky.txt");
	}

}
