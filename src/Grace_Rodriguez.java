/**
 * Ryley Rodriguez
 * CSCI 476 ~ Assignment 3
 */

//imports
import java.util.Scanner;
import java.io.FileReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;

/**
 * Main class to ....
 */
public class Grace_Rodriguez {

    /**
     * Instance variables
     */
    String[] hashed_passwords = new String[6];

    /**
     * Constructor
     */
    public Grace_Rodriguez()
    {
        hashed_passwords[0] = "6f047ccaa1ed3e8e05cde1c7ebc7d958";
        hashed_passwords[1] = "275a5602cd91a468a0e10c226a03a39c";
        hashed_passwords[2] = "b4ba93170358df216e8648734ac2d539";
        hashed_passwords[3] = "dc1c6ca00763a1821c5af993e0b6f60a";
        hashed_passwords[4] = "8cd9f1b962128bd3d3ede2f5f101f4fc";
        hashed_passwords[5] = "554532464e066aba23aee72b95f18ba2";

        //String password = "181003";
        //String password = "41167";
        //String password = "QINGFANG";
        //String password = "lion8888";
        //String password = "victorboy";
        //String password = "wakemeupwhenseptemberends";
    }

    /**
     * Method to compare a generated hash value with the hashed passwords on record
     * @param hash_value as a String value
     * @return a boolean to signal whether or not a match was found
     */
    public boolean compare(String hash_value)
    {
        //loop through all 6 hashed passwords
        for (int i = 0; i < 6; i++)
        {
            //compare the generated value with the one on record
            if (hashed_passwords[i].equals(hash_value))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Method to generate a MD5 hash value for a dictionary of passwords
     * @param input original password from file
     * @param line_number current line number in file, for testing
     * @param start_time represents the time the algorithm began searching for a password
     * @throws NoSuchAlgorithmException if the MD5 string is invalid
     */
    public void dictionary_attack(String input, int line_number, long start_time) throws NoSuchAlgorithmException
    {
        //long start_time = System.nanoTime(); //start timer once searching begins i.e. password about to be hashed
        String potential_password = input; //normal password

        //build the hash string in the next 6 lines
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] hashInBytes = md.digest(potential_password.getBytes(StandardCharsets.UTF_8));

        StringBuilder sb = new StringBuilder();
        for (byte b : hashInBytes) {
            sb.append(String.format("%02x", b));
        } //System.out.println(sb.toString().equals(hash)); //testing

        String hashed_password = sb.toString();

        //compare the hashed password to the passwords on record; sb.toString() = hashed password
        if (compare(hashed_password))
        {
            long end_time = System.nanoTime(); //end timer once searching ends i.e. a hashed password has been matched to one on record
            long elapsed_time_sec = (end_time - start_time)/1000000000; //converted to seconds
            long elapsed_time_msec = (end_time - start_time)/1000000;
            long elapsed_time_nsec = end_time - start_time;
            System.out.println("The password for hash value " + hashed_password +
                                " is " + potential_password + ", it takes the program " +
                                elapsed_time_nsec + " nanoseconds to recover this password.");

            //System.out.println(input + " successfully matched on line: " + line_number);
        }
    }

    /**
     * Main method
     * @param args
     */
    public static void main(String[] args) {
        Grace_Rodriguez gr = new Grace_Rodriguez();
        int line = 1; //current line in file

        //try-catch block
        try {
            //open the file
            Scanner fileRead = new Scanner(new FileReader("passwords.txt"));

            //loop through the whole file
            while (fileRead.hasNext())
            {
                long time = System.nanoTime(); //start timer once searching begins i.e. password file entered

                //attempt to match the current password's hash value to a hash value on record
                gr.dictionary_attack(fileRead.nextLine(), line, time);
                line++;

                //System.out.println(fileRead.nextLine()); //testing
            }
        } catch (Exception exception) {
            System.out.println(exception);
        }
    }
}
