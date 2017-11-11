// package autocompleter;

import java.util.ArrayList;

public class Autocompleter implements AutocompleterInterface {

	TTNode root;

	Autocompleter() {
		root = null;
	}

	public boolean isEmpty() {
		return root == null;
	}

	public void addTerm(String toAdd) {
		root = recurseAdd(root, normalizeTerm(toAdd), 0);
	}

	public boolean hasTerm(String query) {
		return recurseHasTerm(root, normalizeTerm(query), 0);
	}

	public String getSuggestedTerm(String query) {
		if (hasTerm(normalizeTerm(query))) {
			return normalizeTerm(query);
		}
		return recurseSuggestedTerm(root, normalizeTerm(query), "", 0);
	}

	public ArrayList<String> getSortedTerms() {
		return recurseSortedTerms(root, "", new ArrayList<String>());
	}

	private String normalizeTerm(String s) {
		if (s == null || s.equals("")) {
			throw new IllegalArgumentException();
		}
		return s.trim().toLowerCase();
	}

	private int compareChars(char c1, char c2) {
		return Character.toLowerCase(c1) - Character.toLowerCase(c2);
	}

	private TTNode recurseAdd(TTNode recurseRoot, String toAdd, int indexNumber) {
		char currentLetter = toAdd.charAt(indexNumber);
		boolean endOfWord = indexNumber == toAdd.length() - 1;
		if (recurseRoot == null) {
			recurseRoot = new TTNode(currentLetter, endOfWord);
		}
		if (compareChars(currentLetter, recurseRoot.letter) < 0) {
			recurseRoot.left = recurseAdd(recurseRoot.left, toAdd, indexNumber);
		} else if (compareChars(currentLetter, recurseRoot.letter) > 0) {
			recurseRoot.right = recurseAdd(recurseRoot.right, toAdd, indexNumber);
		} else {
			if (endOfWord) {
				recurseRoot.wordEnd = true;
			} else {
				recurseRoot.mid = recurseAdd(recurseRoot.mid, toAdd, indexNumber + 1);
			}
		}
		return recurseRoot;
	}

	private boolean recurseHasTerm(TTNode current, String checkedTerm, int indexNumber) {
		boolean endOfWord = indexNumber >= checkedTerm.length() - 1;
		char currentLetter = checkedTerm.charAt(indexNumber);
		if (current == null) {
			return false;
		}
		if (compareChars(currentLetter, current.letter) < 0) {
			return recurseHasTerm(current.left, checkedTerm, indexNumber);
		} else if (compareChars(currentLetter, current.letter) > 0) {
			return recurseHasTerm(current.right, checkedTerm, indexNumber);
		} else {
			if (current.wordEnd && endOfWord) {
				return true;
			} else if (endOfWord) {
				return false;
			} else {
				return recurseHasTerm(current.mid, checkedTerm, indexNumber + 1);
			}
		}
	}

	private String recurseSuggestedTerm(TTNode current, String checkedTerm, String suggestedTerm, int indexNumber) {
		boolean endOfWord = indexNumber >= checkedTerm.length();
		char currentLetter = 'z';
		if (!endOfWord) {
			currentLetter = checkedTerm.charAt(indexNumber);
		}
		if (current == null || checkedTerm.equals("") || checkedTerm == null) {
			return null;
		}
		if (current.wordEnd && endOfWord) {
			return current.letter + suggestedTerm;
		} else if (compareChars(currentLetter, current.letter) < 0 && !endOfWord) {
			suggestedTerm += recurseSuggestedTerm(current.left, checkedTerm, suggestedTerm, indexNumber);
		} else if (compareChars(currentLetter, current.letter) > 0 && !endOfWord) {
			suggestedTerm += recurseSuggestedTerm(current.right, checkedTerm, suggestedTerm, indexNumber);
		} else {
			suggestedTerm += recurseSuggestedTerm(current.mid, checkedTerm, suggestedTerm, indexNumber + 1);
			return current.letter + suggestedTerm;
		}
		if (!endOfWord && !current.wordEnd && suggestedTerm.contains("null")) {
			return null;
		}
		return suggestedTerm;
	}

	private ArrayList<String> recurseSortedTerms(TTNode current, String term, ArrayList<String> list) {
		if (current == null) {
			return list;
		}
		recurseSortedTerms(current.left, term, list);
		term += current.letter;
		if (current.wordEnd) {
			list.add(term);
		}
		recurseSortedTerms(current.mid, term, list);
		term = term.substring(0, term.length() - 1);
		recurseSortedTerms(current.right, term, list);
		return list;
	}

	private class TTNode {

		boolean wordEnd;
		char letter;
		TTNode left, mid, right;

		TTNode(char c, boolean w) {
			letter = c;
			wordEnd = w;
			left = null;
			mid = null;
			right = null;
		}
	}
}
