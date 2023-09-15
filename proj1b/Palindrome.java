public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> D = new LinkedListDeque<>();
        for (int i = 0; i < word.length(); i++) {
            D.addLast(word.charAt(i));
        }
        return D;
    }

    public boolean isPalindrome(String word) {
        Deque<Character> D = wordToDeque(word);
        return isPalindromeHelper(D);
    }

    public boolean isPalindromeHelper(Deque<Character> D) {
        if (D.size() <= 1) {
            return true;
        } else if (D.removeFirst() != D.removeLast()) {
            return false;
        } else {
            return isPalindromeHelper(D);
        }
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque<Character> D = wordToDeque(word);
        return isPalindromeHelper(D, cc);
    }

    public boolean isPalindromeHelper(Deque<Character> D, CharacterComparator cc) {
        if (D.size() <= 1) {
            return true;
        } else if (!cc.equalChars(D.removeFirst(), D.removeLast())) {
            return false;
        } else {
            return isPalindromeHelper(D, cc);
        }
    }
}
