// package linked_yarn;

import java.util.NoSuchElementException;

public class LinkedYarn implements LinkedYarnInterface {

	// -----------------------------------------------------------
	// Fields
	// -----------------------------------------------------------
	private Node head;
	private int size, uniqueSize, modCount;

	// -----------------------------------------------------------
	// Constructor
	// -----------------------------------------------------------

	LinkedYarn() {
		head = null;
		size = 0;
		uniqueSize = 0;
		modCount = 0;
	}

	// -----------------------------------------------------------
	// Methods
	// -----------------------------------------------------------

	public boolean isEmpty() {
		return size == 0;
	}

	public int getSize() {
		return this.size;
	}

	public int getUniqueSize() {
		return this.uniqueSize;
	}

	public void insert(String toAdd) {
		if (!this.isEmpty() && this.contains(toAdd)) {
			this.nodeContaining(toAdd).count++;
		} else {
			uniqueSize++;
			Node unique = new Node(toAdd, 1);
			if (head == null) {
				head = unique;
			} else {
				unique.next = head;
				unique.next.prev = unique;
				head = unique;
			}
		}
		size++;
		modCount++;
	}

	public int remove(String toRemove) {
		if (this.contains(toRemove)) {
			if (this.nodeContaining(toRemove).count > 1) {
				this.nodeContaining(toRemove).count--;
				this.size--;
				this.modCount++;
				return this.nodeContaining(toRemove).count;
			} else {
				this.removeAll(toRemove);
				return 0;
			}
		} else {
			return 0;
		}
	}

	public void removeAll(String toNuke) {
		if (!this.isEmpty() && this.contains(toNuke)) {
			Node removedNode = this.nodeContaining(toNuke);
			this.size -= removedNode.count;
			this.modCount++;
			uniqueSize--;
			if (head.text.equals(removedNode.text)) {
				head = removedNode.next;
			} else {
				if (removedNode.next != null) {
					removedNode.next.prev = removedNode.prev;
				}
				if (removedNode.prev != null) {
					removedNode.prev.next = removedNode.next;
				}
				removedNode = null;
			}
		}
	}

	public int count(String toCount) {
		if (this.contains(toCount)) {
			return this.nodeContaining(toCount).count;
		} else {
			return 0;
		}
	}

	public boolean contains(String toCheck) {
		for (Node a = head; a != null; a = a.next) {
			if (a.text.equals(toCheck)) {
				return true;
			}
		}
		return false;
	}

	public String getMostCommon() {
		String mostCommon = null;
		int mostCommonCount = 0;
		for (Node a = head; a != null; a = a.next) {
			if (a.count > mostCommonCount) {
				mostCommonCount = a.count;
				mostCommon = a.text;
			}
		}
		return mostCommon;
	}

	public LinkedYarn clone() {
		LinkedYarn cloned = new LinkedYarn();
		for (Node a = head; a != null; a = a.next) {
			for (int i = 0; i < a.count; i++) {
				cloned.insert(a.text);
			}
		}
		return cloned;
	}

	public void swap(LinkedYarn other) {
		this.modCount++;
		LinkedYarn temporaryThis = this.clone();
		this.clearAndReplace(other);
		other.clearAndReplace(temporaryThis);
	}

	public LinkedYarn.Iterator getIterator() {
		if (this.size == 0) {
			throw new IllegalStateException();
		} else {
			return new LinkedYarn.Iterator(this);
		}
	}

	// -----------------------------------------------------------
	// Static methods
	// -----------------------------------------------------------

	public static LinkedYarn knit(LinkedYarn y1, LinkedYarn y2) {
		LinkedYarn combination = y1.clone();
		for (Node a = y2.head; a != null; a = a.next) {
			for (int i = 0; i < a.count; i++) {
				combination.insert(a.text);
			}
		}
		return combination;
	}

	public static LinkedYarn tear(LinkedYarn y1, LinkedYarn y2) {
		LinkedYarn inY2 = y1.clone();
		for (Node a = y1.head; a != null; a = a.next) {
			for (int i = 0; i < y2.count(a.text); i++) {
				inY2.remove(a.text);
			}
		}
		return inY2;
	}

	public static boolean sameYarn(LinkedYarn y1, LinkedYarn y2) {
		for (Node a = y1.head; a != null; a = a.next) {
			if (!(y2.contains(a.text) && a.count == y2.nodeContaining(a.text).count && y1.size == y2.size
					&& y1.uniqueSize == y2.uniqueSize)) {
				return false;
			}
		}
		return true;
	}

	// -----------------------------------------------------------
	// Inner Classes
	// -----------------------------------------------------------

	private Node nodeContaining(String item) {
		for (Node a = head; a != null; a = a.next) {
			if (a.text.equals(item)) {
				return a;
			}
		}
		return null;
	}

	private void clearAndReplace(LinkedYarn other) {
		for (Node a = this.head; a != null; a = a.next) {
			this.removeAll(a.text);
		}
		for (Node a = other.head; a != null; a = a.next) {
			for (int i = 0; i < a.count; i++) {
				this.insert(a.text);
			}
		}
	}

	public class Iterator implements LinkedYarnIteratorInterface {
		LinkedYarn owner;
		Node current;
		int itModCount;

		Iterator(LinkedYarn y) {
			owner = y;
			current = owner.head;
			itModCount = owner.modCount;
		}

		public boolean hasNext() {
			return current != null && (current.position < current.count - 1 || current.next != null);
		}

		public boolean hasPrev() {
			return current != null && (current.position > 0 || current.prev != null);
		}

		public boolean isValid() {
			return owner.modCount == itModCount;
		}

		public String getString() {
			return current.text;
		}

		public void next() {
			if (this.isValid()) {
				if (current.position < current.count - 1) {
					current.position++;
				} else {
					current.position = 0;
					current = current.next;
				}
			} else {
				throw new IllegalStateException();
			}
		}

		public void prev() {
			if (this.isValid()) {
				if (current.position > 0) {
					current.position--;
				} else {
					current.position = 0;
					current = current.prev;
					current.position = current.count - 1;
				}
			} else {
				throw new IllegalStateException();
			}
		}

		public void replaceAll(String toReplaceWith) {
			if (!this.isValid()) {
				throw new IllegalStateException();
			}
			current.text = toReplaceWith;
		}
	}

	class Node {
		Node next, prev;
		String text;
		int count;
		int position;

		Node(String t, int c) {
			text = t;
			count = c;
			position = 0;
		}
	}
}
