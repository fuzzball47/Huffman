// Encode prompts the user for an input file name, a code file name and the
// name to use for the binary (encoded) output file.  It assumes that MakeCode
// has been run to generate a Huffman tree appropriate for encoding the input
// file.
import java.io.*;
import java.util.*;

public class Encode {
    public static final int CHAR_MAX = 256;  // max char value to be encoded

    public static void main(String[] args) throws IOException {
        System.out.println("This program encodes a file with a Huffman code.");
        System.out.println();

        Scanner console = new Scanner(System.in);
        System.out.print("Input file name to encode? ");
        String inFile = console.nextLine();
        System.out.print("Code file name? (Where the code table will be stored)");
        String codeFile = console.nextLine();
        System.out.print("Output file name? (Where the encoded txt will be saved)");
        String outputFile = console.nextLine();
        
        // open code file and record codes
        String[] codes = new String[CHAR_MAX + 1];
        Scanner codeInput = new Scanner(new File(codeFile));
        while (codeInput.hasNextLine()) {
            int n = Integer.parseInt(codeInput.nextLine());
            codes[n] = codeInput.nextLine();
        }
            
        // open source file, open output, encode
        FileInputStream input = new FileInputStream(inFile);
        BitOutputStream output = new BitOutputStream(outputFile);
        boolean done = false;
        int n = input.read();
        while (n != -1) {
            if (codes[n] == null) {
                System.out.println("Your code file has no code for " + n +
                                   " (the character '" + (char)n + "')");
                System.out.println("exiting...");
                System.exit(1);
            }
            writeString(codes[n], output);
            n = input.read();
        }
        writeString(codes[CHAR_MAX], output);
        output.close();
    }

    public static void writeString(String s, BitOutputStream output) {
        for (int i = 0; i < s.length(); i++) {
            output.writeBit(s.charAt(i) - '0');
        }
    }
}
