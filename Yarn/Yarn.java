// package yarn;

public class Yarn implements YarnInterface {

	// -----------------------------------------------------------
	// Fields
	// -----------------------------------------------------------
	private Entry[] items;
	private int size;
	private int uniqueSize;
	private final int MAX_SIZE = 100;

	// -----------------------------------------------------------
	// Constructor
	// -----------------------------------------------------------
	Yarn() {
		size = 0;
		uniqueSize = 0;
		items = new Entry[MAX_SIZE];
	}

	// -----------------------------------------------------------
	// Methods
	// -----------------------------------------------------------
	public boolean isEmpty() {
		return this.size == 0;
	}

	public int getSize() {
		return this.size;
	}

	public int getUniqueSize() {
		return this.uniqueSize;
	}

	public boolean insert(String toAdd) {
		if (this.size == 0) {
			items[0] = new Entry(toAdd, 1);
			this.uniqueSize++;
			this.size++;
			return true;
		} else if (this.uniqueSize > MAX_SIZE) {
			return false;
		} else {
			if (this.contains(toAdd)) {
				items[this.indexOf(toAdd)].count++;
			} else {
				items[this.uniqueSize] = new Entry(toAdd, 1);
				this.uniqueSize++;
			}
			this.size++;
			return true;
		}
	}

	public int remove(String toRemove) {
		this.size--;
		int indexOccurence = this.indexOf(toRemove);
		if (indexOccurence >= 0) {
			if (this.items[indexOccurence].count > 1) {
				this.items[indexOccurence].count--;
				return this.items[indexOccurence].count;
			} else {
				this.removeAll(toRemove);
				this.size++;
				return 0;
			}
		}
		return 0;
	}

	public void removeAll(String toNuke) {
		int indexOccurence = this.indexOf(toNuke);
		if (indexOccurence >= 0) {
			this.size = this.size - this.items[indexOccurence].count;
			for (int i = indexOccurence; i < this.uniqueSize - 1; i++) {
				items[i] = this.items[i + 1];
			}
			this.uniqueSize--;
		}
	}

	public int count(String toCount) {
		if (this.indexOf(toCount) < 0) {
			return 0;
		}
		return this.items[this.indexOf(toCount)].count;
	}

	public boolean contains(String toCheck) {
		boolean doesContain = false;
		for (int i = 0; i < this.uniqueSize; i++) {
			if (this.items[i].text.equals(toCheck)) {
				doesContain = true;
				break;
			}
		}
		return doesContain;
	}

	public String getNth(int n) {
		if (n > size) {
			return null;
		}
		int newCount = n;
		for (int i = 0; i < this.uniqueSize; i++) {
			newCount = n - this.items[i].count;
			if (newCount <= 0) {
				return this.items[i].text;
			}
		}
		return this.items[this.uniqueSize - 1].text;
	}

	public String getMostCommon() {
		int countIndex = -1;
		int countNumber = 0;
		for (int i = 0; i < this.uniqueSize; i++) {
			if (this.items[i].count > countNumber) {
				countNumber = this.items[i].count;
				countIndex = i;
			}
		}
		return items[countIndex].text;
	}

	public Yarn clone() {
		Yarn cloned = new Yarn();
		cloned.items = this.items;
		cloned.size = this.size;
		cloned.uniqueSize = this.uniqueSize;
		return cloned;
	}

	public void swap(Yarn other) {
		Yarn temporary = new Yarn();
		temporary.items = other.items;
		temporary.size = other.size;
		temporary.uniqueSize = other.uniqueSize;
		other.items = this.items;
		other.size = this.size;
		other.uniqueSize = this.uniqueSize;
		this.items = temporary.items;
		this.size = temporary.size;
		this.uniqueSize = temporary.uniqueSize;
	}

	// -----------------------------------------------------------
	// Static methods
	// -----------------------------------------------------------

	public static Yarn knit(Yarn y1, Yarn y2) {
		Yarn combination = new Yarn();
		for (int i = 0; i < y1.uniqueSize; i++) {
			combination.insert(y1.items[i].text);
			combination.items[i].count = y1.items[i].count;
		}
		combination.size = y1.size;
		combination.uniqueSize = y1.uniqueSize;

		for (int i = 0; i < y2.uniqueSize; i++) {
			for (int j = 0; j < y2.items[i].count; j++) {
				combination.insert(y2.items[i].text);
			}
		}
		return combination;
	}

	public static Yarn tear(Yarn y1, Yarn y2) {
		Yarn y3 = y1.clone();
		for (int i = 0; i < y1.uniqueSize; i++) {
			if (y2.contains(y1.items[i].text)) {
				if (y2.items[y2.indexOf(y1.items[i].text)].count >= 1) {
					y3.remove(y1.items[i].text);
				} else {
					y3.removeAll(y1.items[i].text);
				}
			}
		}
		return y3;
	}

	public static boolean sameYarn(Yarn y1, Yarn y2) {
		for (int i = 0; i < y1.uniqueSize; i++) {
			if (!(y2.contains(y1.items[i].text) && y2.items[y2.indexOf(y1.items[i].text)].count == y1.items[i].count
					&& y1.size == y2.size)) {
				return false;
			}
		}
		return true;
	}

	private int indexOf(String phrase) {
		for (int i = 0; i < this.uniqueSize; i++) {
			if (phrase.equals(items[i].text)) {
				return i;
			}
		}
		return -1;
	}
}

class Entry {
	String text;
	int count;

	Entry(String s, int c) {
		text = s;
		count = c;
	}
}
