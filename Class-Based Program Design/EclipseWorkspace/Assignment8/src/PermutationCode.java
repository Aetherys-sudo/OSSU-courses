import java.util.*;
import tester.*;

/**
 * A class that defines a new permutation code, as well as methods for encoding
 * and decoding of the messages that use this code.
 */
class PermutationCode {
    // The original list of characters to be encoded
    ArrayList<Character> alphabet = 
        new ArrayList<Character>(Arrays.asList(
                    'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 
                    'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 
                    't', 'u', 'v', 'w', 'x', 'y', 'z'));

    ArrayList<Character> code = new ArrayList<Character>(26);

    // A random number generator
    Random rand = new Random();

    // Create a new instance of the encoder/decoder with a new permutation code 
    PermutationCode() {
        this.code = this.initEncoder();
    }

    // Create a new instance of the encoder/decoder with the given code 
    PermutationCode(ArrayList<Character> code) {
        this.code = code;
    }

    // Initialize the encoding permutation of the characters
    ArrayList<Character> initEncoder() {
    	int ran;
    	ArrayList<Character> res = new ArrayList<Character>();
        for (int i = 0; i < 26; i ++) {
        	ran = rand.nextInt() % 26;
        	res.set(i, alphabet.get(ran));
        }
        return res;
    }

    // produce an encoded String from the given String
    String encode(String source) {
    	String res = "";
        for (int i = 0; i < source.length(); i ++) {
        	res += this.code.get(this.alphabet.indexOf(source.charAt(i)));
        }
        
        return res;
    }

    // produce a decoded String from the given String
    String decode(String code) {
    	String res = "";
        for (int i = 0; i < code.length(); i ++) {
        	res += this.alphabet.get(this.code.indexOf(code.charAt(i)));
        }
        
        return res;
    }
}

class ExamplesCode {
	PermutationCode code = new PermutationCode(new ArrayList<Character>(Arrays.asList(
            'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 
            'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 
            't', 'u', 'v', 'w', 'x', 'y', 'z', 'a')));
	
	String test1 = "abcdef";
	String test2 = "bcdefg";
	
	void testEncode(Tester t) {
		t.checkExpect(this.code.encode(test1), test2);
		t.checkExpect(this.code.decode(test2), test1);
	}
}
