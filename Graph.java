/******************************************************************************

Lucas Teltow
Graph.java

*******************************************************************************/
//imports
import java.io.*;
import java.lang.*;
import java.util.*;

class Graph
{
    //variables
    int size;                               //for storing the number of cities
    LinkedList<LinkedList> cities;          //for storing the cities as an array of linked lists
    
    
    /*the constructors
     *
     */
    public Graph()
    {
        size = 1;
        cities = new LinkedList<LinkedList>();
    }//end of defualt constructor
    
    //overloaded constructor for taking in a size
    public Graph(int s)
    {
        size = s;
        cities = new LinkedList<LinkedList>();
    }//end of overloaded constructor(int)
    
    
    /* General functions
     *
     */
    //fill graph takes in a scanner and fills a graph with the inputed data
    public Graph fillGraph(Scanner input)
    {
        //various variables
        String line = "";                                               //for storing the current line of input
        String[] splitInput = new String[4];                            //for storing the split input
        String stringTemp = "";                                         //for temporarily storing a string value
        Graph output = new Graph(Integer.parseInt(input.nextLine()));   //for storing the graph being editted and outputed
        int i;                                                          //counter variable
        boolean dupFlag;                                                //a flag for checking if the inputed city already has a list
        
        //creating the first city and connection
        //splitting the line up
        line = input.nextLine();
        splitInput = line.split("\\|");
        Node temp1 = new Node(splitInput[0]);
        output.addCity(temp1);
        
        Node temp5 = new Node(splitInput[1]);
        output.addCity(temp5);
        
        //adding in the rest of the connection
        Node temp2 = new Node(splitInput[1], Double.parseDouble(splitInput[2]), Integer.parseInt(splitInput[3]));
        output.getCities().get(0).add(temp2);
        
        //going through the whole file and filling the adjacncyMatrix of output
        while(input.hasNextLine())
        {
            dupFlag = false;
            
            //splitting the line up
            line = input.nextLine();
            splitInput = line.split("\\|");
            
            //checking to make sure there are no cities of the given name
            for(i = 0; i < output.getCities().size(); i++)
            {
                if(output.getCities().get(i) != null && ((Node)output.getCities().get(i).peek()).getName().equals(splitInput[0]))
                {
                    dupFlag = true;
                    break;
                }//end of if the current line already is a city
            }//end of for loop looking for duplicates
            
            //now if the city already exists then we add a new connection, other wise we add a new city and a new connection
            if(dupFlag)
            {
                Node temp3 = new Node(splitInput[1], Double.parseDouble(splitInput[2]), Integer.parseInt(splitInput[3]));
                output.getCities().get(i).add(temp3);
            }//end of if current line is already a city
            else
            {
                Node temp3 = new Node(splitInput[0]);
                output.addCity(temp3);
        
                //adding in the rest of the connection
                Node temp4 = new Node(splitInput[1], Double.parseDouble(splitInput[2]), Integer.parseInt(splitInput[3]));
                output.addCity(temp4);
            }//end of if current line is not already a city
            
            //checking to make sure there are no cities of the given name (second city)
            dupFlag = true;
            for(i = 0; i < output.getCities().size(); i++)
            {
                if(output.getCities().get(i) != null && ((Node)output.getCities().get(i).peek()).getName().equals(splitInput[1]))
                {
                    dupFlag = false;
                    break;
                }//end of if the current line already is a city
            }//end of for loop looking for duplicates
            
            //now if the city already exists then we add a new connection, other wise we add a new city and a new connection
            if(dupFlag)
            {
                Node temp6 = new Node(splitInput[1], 0, 0);
                output.addCity(temp6);
            }//end of if current line is already a city
        }//end of while loop getting each line of input
        
        
        return output;
    }//end of fill graph
     
    //sortTime is the public function for sorting the graph by time and returning the 3 fastest times between
    //the given start and end nodes
    //1 is cost and 0 is time
    public String sort(String start, String end, int type)
    {
        //various variables
        String output = "";                                 //for storing the output
        LinkedList<Node> minHeap = new LinkedList<Node>();  //the stack used for finding the lowest times
        
        //running the inital exhaustive search for paths
        //1 is cost and 0 is time
        printHeap(minHeap);
        runSearch(start, end, minHeap, type);
        
        //printing out the paths
        if(minHeap.peekFirst() == null)
            return "Error, not a valid request!\n\n";
        
        printHeap(minHeap);
        Node temp = minHeap.pop();
        heapify(minHeap, type);
        if(temp.getCost() == 0 && temp.getTime() == 0)
            output = temp.getName() + "\n";
        else
        {
            printHeap(minHeap);
            output = temp.getName() + "\nCost: " + temp.getCost() + " Time: " + temp.getTime() + "\n";
            if(minHeap.peek() == null)
                return output;
            temp = minHeap.pop();
            heapify(minHeap, type);
            output = output + temp.getName() + "\nCost: " + temp.getCost() + " Time: " + temp.getTime() + "\n";
            if(minHeap.peek() == null)
                return output;
            temp = minHeap.pop();
            output = output + temp.getName() + "\nCost: " + temp.getCost() + " Time: " + temp.getTime() + "\n";
        }//if there is an error, the time and cost will be zero, and only the error message gets printed out
        return output;
    }//end of sortTime
    
    //runTime starts at a given node and begins the exahustive search for paths to the end node
    //basically just starts the iteration of the recursive function for finding paths, with a bit of edge case checking
    //takes in start and end name, and the min heap to add output to 
    //1 is cost and 0 is time
    private void runSearch(String start, String end, LinkedList minHeap, int type)
    {
        boolean flag = false;               //for checking if a city is in the list
        int i = 0;                          //counter variable
        int[] visited = new int[cities.size()];

        
        //check to see if you are already in the end city
        if(cities.size() == 0 || start.equals(end))
        {
            Node temp = new Node("You are already in the city!\n", 0, 0);
            minHeap.push(temp);
            heapify(minHeap, type);
            return;
        }
        
        //check to see if there are no nodes connected to the start city
        if(cities.size() == 1)
        {
            Node temp = new Node(("No path to " + end + ".\n"), 0, 0);
            minHeap.push(temp);
            heapify(minHeap, type);
            return;
        }
        
        //check to see if the ending city is in the graph
        for(i = 0; i < cities.size(); i++)
        {
            if(findNode(end, cities) != -1)
            {
                flag = true;
                break;
            }
        }//end of loop checking if the starting city is in the array
        if(!flag)
        {
            Node temp = new Node(("The city " + end + " is not in the graph.\n"), 0, 0);
            minHeap.push(temp);
            heapify(minHeap, type);
            return;
        }
        
        i = 0;
        flag = false;
        //check to see if the starting city is in the graph
        for(i = 0; i < cities.size(); i++)
        {
            if(start.equals(((Node)cities.get(i).getFirst()).getName()))
            {
                visited[i] = 1;
                flag = true;
                break;
            }
        }//end of loop checking if the starting city is in the array
        if(!flag)
        {
            Node temp = new Node(("The city " + start + " is not in the graph.\n"), 0, 0);
            minHeap.push(temp);
            heapify(minHeap, type);
            return;
        }
        
        //if no issues are found, then starting the exhaustive search
        for(int j = 1; j < cities.get(i).size(); j++)
        {
            if(cities.get(i).get(j) == null)
                break;
            
            visited[i] = 1;
            Node tempNode = new Node(start, ((Node)cities.get(i).get(j)).getCost(), ((Node)cities.get(i).get(j)).getTime());
            recursiveSearch(((Node)cities.get(i).get(j)).getName(), end, tempNode, visited, minHeap, type);
        }//end of looping through each city connected to the starting city
    }//end of runTime
    
    //recursiveTime is the recursive function that is being used to finish the exhaustive search
    //takes in the visited list, the end node, the current node, and the min heap
    //1 is cost and 0 is time
    private void recursiveSearch(String current, String end, Node data, int[] visited, LinkedList minHeap, int type)
    {
        int[] tempArr = new int[visited.length];
        for(int k = 0; k < visited.length; k++)
            tempArr[k] = visited[k];
        int i = 0;
        
        System.out.println("Current: " + current + " End: " + end);
        for(int j = 0; j < tempArr.length; j++)
            System.out.print(tempArr[j] + " ");
        System.out.println();
        //checking if the current node is the target end node
        if(current.equals(end))
        {
            System.out.println("current == end");
            String temp = data.getName() + " -> " + end;
            Node tempNode = new Node(temp, data.getCost(), data.getTime());
            minHeap.push(tempNode);
            heapify(minHeap, type);
            return;
        }//end of if the current node is the target node
        
        //if the current node is not the end node, then we add to the path and continue iterating
        int currentLocation = findNode(current, cities);
        if(currentLocation == -1)
        {
            System.out.println("breaking, " + current + " is not in the list");
            return;
        }
        
        tempArr[currentLocation] = 1;
        for(i = 0; i < tempArr.length; i++)
        {
            if(i >= cities.get(currentLocation).size())
                break;
                
            int n = findNode(((Node)cities.get(currentLocation).get(i)).getName(), cities);
            if(cities.get(currentLocation).get(i) != null && tempArr[n] == 0)
            {
                System.out.println("Recuring");
                String temp = data.getName() + " -> " + current;
                tempArr[currentLocation] = 1;
                tempArr[i] = 1;
                Node tempNode = new Node(temp, (data.getCost() + ((Node)cities.get(currentLocation).get(i)).getCost()), (data.getTime() + ((Node)cities.get(currentLocation).get(i)).getTime()));
                
                recursiveSearch(((Node)cities.get(currentLocation).get(i)).getName(), end, tempNode, tempArr, minHeap, type);
            }//end of if the node i is not one that has already been visited
            else
            {
                System.out.println("City " + ((Node)cities.get(currentLocation).get(i)).getName() + " is has been visited. (array index " + n + ")");
            }
        }//end of looping through and calling the function again on each connected city
    }//end of recursiveTime
    
    
    //find searches a LinkedList of LinkedLists for a specific node as the start of a linkedList
    //returns -1 if the node isnt found
    //takes in a linkedList and a string for the name of the node. returns the location of that node 
    private int findNode(String name, LinkedList list)
    {
        int i = 0;
        for(i = 0; i < list.size(); i++)
        {
            if(((Node)((LinkedList)list.get(i)).peek()).getName().equals(name))
            {
                return i;
            }
        }
        
        //System.out.println("Failed to find " + name);
        return -1;
    }//end of findNode
    
    //heapify function for the stack being used as a min heap
    //takes in a LinkedList to be heapified and the type of value sorting for (time or cost)
    //1 is cost and 0 is time
    private void heapify(LinkedList minHeap, int type)
    {
        System.out.println("Heapify called on heap of size " + minHeap.size());
        
        //checking if there is only 1 element in the heap
        if(minHeap.size() == 1 || minHeap.size() == 0)
            return;
        
        int i = 0;
        double max = 1000000;
        int maxLocation = 0;
        
        if(type == 1)
        {
        for(i = 0; i < minHeap.size(); i++)
        {
            if(((Node)minHeap.get(i)).getCost() < max)
            {
                maxLocation = i;
                max = ((Node)minHeap.get(i)).getCost();
            }
        }//end of looping throught the heap
        }
        else
        {
            for(i = 0; i < minHeap.size(); i++)
            {
                if(((Node)minHeap.get(i)).getTime() < max)
                {
                    maxLocation = i;
                    max = ((Node)minHeap.get(i)).getTime();
                }
            }//end of looping throught the heap
        }
        
        minHeap.push(minHeap.get(maxLocation));
        minHeap.remove((maxLocation + 1));
    }//end of heapify
    
    /*the getters and setters
     *
     */
    public int getSize()
    {
        return size;
    }//end of getSize
    
    public LinkedList<LinkedList> getCities()
    {
        return cities;
    }//end of getCities
    
    public void setSize(int n)
    {
        size = n;
    }//end of setSize
    
    public void addCity(Node n)
    {
        LinkedList<Node> newList = new LinkedList<Node>();
        newList.add(n);
        cities.add(newList);
    }//end of addCity
    
    public void printCities()
    {
        for(int i = 0; i < cities.size(); i++)
        {
            System.out.println(((Node)cities.get(i).peek()).getName());
        }
        System.out.println();
    }
    
    //so i can see what is in the LinkedLists
    public void printHeap(LinkedList<Node> list)
    {
        System.out.println("Printing the heap");
        for(int i = 0; i < list.size(); i++)
        {
            System.out.println(((Node)list.get(i)).getName());
        }
        System.out.println();
    }
    
    public void printEdges()
    {
        for(int i = 0; i < cities.size(); i++)
        {
            System.out.print(((Node)cities.get(i).peek()).getName() + " is connected to ");
            for(int j = 0; j < cities.get(i).size(); j++)
            {
                System.out.print(((Node)cities.get(i).get(j)).getName() + " with a cost of " + ((Node)cities.get(i).get(j)).getCost() + " and a time of " + ((Node)cities.get(i).get(j)).getTime() + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}//end of graph class
