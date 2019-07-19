import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

public class SortedSetSeries<E> {
	private Map<Integer, SortedSet<E>> map;

	// Initializes our main storage
	public SortedSetSeries() {
		this.map = new HashMap<Integer, SortedSet<E>>();
	}
	
	// Takes in a length and and element
	// Stores the element to the corresponding length in our map
	// Returns true if it was successfully added
	public boolean add(int s, E e) {
		if (!this.map.containsKey(s)) {
			SortedSet<E> set = new TreeSet<E>();
			set.add(e);
			this.map.put(s, set);
			return true;
		} else {
			if(this.map.get(s).contains(e)) return false;
			this.map.get(s).add(e);
			return true;
		}

	}
	// Takes in a length and an element
	// Removes the element if the size exists and the element in our map
	// Returns true if it was successfully removed
	public boolean remove(int s, E e) {
		if (!this.map.containsKey(s)) {
			return false;
		} else {
			if(!this.map.get(s).contains(e)) {
				return false;
			}
			this.map.get(s).remove(e);
			return true;
		}
	}
	
	// Takes in length and an element
	// Sees if the element is in our map
	// Returns true if it is and otherwise if it isn't
	public boolean contains(int s, E e) {
		if (!this.map.containsKey(s)) {
			return false;
		} else {
			return this.map.get(s).contains(e);
		}
	}
	// Takes in an element
	// Searches through the keys to see if the element is in it
	// Returns the key if the element is found otherwise -1
	public int contains(E e) {
		for (Integer key : this.map.keySet()) {
			if (this.map.get(key).contains(e)) {
				return key;
			}
		}
		return -1;
	}
	// Takes in a length
	// Returns the size of the set of the length inputed 
	public int size(int s) {
		return this.map.get(s).size();
	}
	// Takes in a length
	// Returns true if the set of the length is empty otherwise false
	public boolean isEmpty(int s) {
		return this.map.get(s).isEmpty();
	}
	// Takes in a length and two elements of the same type
	// Returns a subset of from-to
	public SortedSet<E> subSet(int s, E from, E to) {
		return this.map.get(s).subSet(from, to);
	}
	// Takes in a length
	// Creates and returns an iterator of the corresponding set
	public Iterator<E> iterator(int s) {
		return this.map.get(s).iterator();
	}
}
