// Eric Song
// TA: Kyle Pierce
//2/17/18
//HuffmanNode is utilized in the HuffmanTree class, 
//stores a int representing a character, an int
//representing the frequency, and two references 
//to two other HuffmanNodes


public class HuffmanNode implements Comparable<HuffmanNode> {

   public int character;
   public int frequency;
   public HuffmanNode left;
   public HuffmanNode right;
   
   //constructs a HuffmanNode with data in the form of a string, and
   //references to two other HuffmanNodes
   public HuffmanNode(int character, int frequency, HuffmanNode left, 
         HuffmanNode right) {
      this.character = character;
      this.frequency = frequency;
      this.left = left;
      this.right = right;
   }
   
   //contructs a leaf node, with just the character and frequency
   public HuffmanNode(int character, int frequency) {
      this(character, frequency, null, null);
   }
   
   //constructs a leaf node to be used when reconstructing the tree
   public HuffmanNode(int character) {
      this(character, -1, null, null);
   }
   
   //used to allow HuffmanNodes to be compared in terms of their
   //frequency value
   public int compareTo(HuffmanNode other) {
      return (frequency - other.frequency);
   }
   
   //returns true if the HuffmanNode is a leaf node, 
   //returns false otherwise
   public boolean isLeafNode() { 
      return (left == null && right == null);
   }
}


