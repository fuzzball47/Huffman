//This class allows a client to compress a text file
//using a method called Huffman coding, and also allows
//the client to decode a previously encoded file

import java.util.*;
import java.io.*;

public class HuffmanTree {

   private HuffmanNode overallRoot;
   
   //constructs a HuffmanTree by using a given array containing the 
   //counts of each character
   public HuffmanTree(int[] count) {
      Queue<HuffmanNode> priorityQueue = new PriorityQueue<HuffmanNode>();
      
      for (int i = 0; i < count.length; i++) {
         if (count[i] > 0) { //prevents nodes for nonexistent characters
            HuffmanNode toAddNode = new HuffmanNode(i, count[i]);
            priorityQueue.add(toAddNode);
         }
      }
      
      priorityQueue.add(new HuffmanNode(count.length, 1));                     
      
      while(priorityQueue.size() > 1) {
         HuffmanNode smallestNode = priorityQueue.remove();
         HuffmanNode secondSmallestNode = priorityQueue.remove();
         HuffmanNode toAddToQueue = new HuffmanNode(-1, smallestNode.frequency
               + secondSmallestNode.frequency, smallestNode, secondSmallestNode);
         priorityQueue.add(toAddToQueue);
      }
      
      overallRoot = priorityQueue.remove();
   }
   
   //uses a given PrintStream to write the current tree to a file in standard format
   public void write(PrintStream output) {
      exploreAndWriteTree(overallRoot, output, "");
   }
   
   //recursively traverses through the tree and prints to a printstream
   //each leaf and the path it took to get there in the form of bits
   private void exploreAndWriteTree(HuffmanNode currentNode, PrintStream output, 
         String currentCode) {
      if (currentNode.isLeafNode()) {
         output.println(currentNode.character);
         output.println(currentCode);
      } else {
         exploreAndWriteTree(currentNode.left, output, currentCode + "0");
         exploreAndWriteTree(currentNode.right, output, currentCode + "1"); 
      }
   }
   
   
   //reconstructs a tree from a given scanner of a file in standard format
   //to the current tree
   public HuffmanTree(Scanner input) {
      while(input.hasNextLine()) {
         int number = Integer.parseInt(input.nextLine());
         String code = input.nextLine();
         overallRoot = buildTree(overallRoot, code, number);
      }
   }
   
   //uses the currentNode, the current code, and the character to recursively 
   //construct a path through the HuffmanTree and creates a leaf node once
   //it has traveled completely through the code, and returns the current node
   private HuffmanNode buildTree(HuffmanNode currentNode, String code, int character) {
      HuffmanNode tempNode = currentNode;
      if (currentNode == null) {
         tempNode = new HuffmanNode(-1);
      } 
      
      if (code.length() == 0) {
         return new HuffmanNode(character);
      } else if (code.charAt(0) == '0') {
         tempNode.left = buildTree(tempNode.left, code.substring(1), character);
      } else {
         tempNode.right = buildTree(tempNode.right, code.substring(1), character);
      }
      return tempNode;
   }
   
   
   //takes an input of bits in the form of a BitInputStream, and prints
   //out the corresponding characters to a file using the given 
   //PrintStream, stopping when it reaches the given "end of file" 
   //character
   public void decode(BitInputStream input, PrintStream output, int eof){
      HuffmanNode currentNode = overallRoot;
      while(currentNode.character != eof){
         if(currentNode.right == null && currentNode.left == null){
            output.write(currentNode.character);
            currentNode = overallRoot;
         }else if(input.readBit() == 1){
            currentNode = currentNode.right;
         }else{
            currentNode = currentNode.left;
         }   
      } 
   }
}
