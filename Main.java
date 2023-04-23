/******************************************************************************

Lucas Teltow
Main.java

*******************************************************************************/
//the imports
import java.util.Scanner;
import java.io.*;
import java.util.*;

//the main class
//1 is cost and 0 is time
public class Main
{
    //function for parsing the requested flight plans
    private static void pathsHandler(Graph data, Scanner input, FileWriter output) throws IOException
    {
        //various variables
        String line = "";                                               //for storing the current line of input
        String[] splitInput = new String[3];                            //for storing the split input
        String stringTemp = "";                                         //for temporarily storing a string value
        int i = 0;                                                      //counter variable
        
        line = input.nextLine();
        i = 0;
        //1 is cost and 0 is time
        while(input.hasNextLine())
        {
            //splitting the line up
            line = input.nextLine();
            splitInput = line.split("\\|");
            //figure out which type of sort they want
            if(splitInput[2].equals("T"))
            {
                output.write("Flight Path " + i + ": " + splitInput[0] + " to " + splitInput[1] + " (Time)\n");
                output.write(data.sort(splitInput[0], splitInput[1], 0) + "\n\n");
            }//end of if they want to sort by time
            else
            {
                output.write("Flight Path " + i + ": " + splitInput[0] + " to " + splitInput[1] + " (Cost)\n");
                output.write(data.sort(splitInput[0], splitInput[1], 1) + "\n");
            }//end of if they want to sort by cost
            i++;
        }//end of for loop running through the inputed file
        output.flush();
    }//end of the pathsHandler function
    
    
    //main method
	public static void main(String[] args) throws FileNotFoundException, IOException
	{
	    //various variables
	    File dataFile = new File(args[0]);      //the inital data file
	    File pathFile = new File(args[1]);      //the paths to be calculated file
	    File outFile = new File(args[2]);       //the output file
	    Graph data = new Graph();               //for storing the data in a graph
	    
	    //openning scanners/writers for the files and making sure the files opened correctly
	    if(!dataFile.exists() || !outFile.exists() || !pathFile.exists())
            return;
        Scanner dataFileScanner = new Scanner(dataFile);
        Scanner pathFileScanner = new Scanner(pathFile);
        FileWriter outFileWriter = new FileWriter(args[2]);
        
        //filling the graph with the inputed data
        data = data.fillGraph(dataFileScanner);

        

		pathsHandler(data, pathFileScanner, outFileWriter);
		outFileWriter.flush();
	}//end of main function
}//end of main class
