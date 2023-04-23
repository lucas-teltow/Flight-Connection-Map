/******************************************************************************

Lucas Teltow, lkt190000
Node.java

*******************************************************************************/
//imports
import java.io.*;
import java.lang.*;
import java.util.*;

class Node
{
    //the variables
    String name;                //for the name of the city
    int timeAway;               //for storing the time to get from the prior city to this one
    double cost;                //for storing the cost to get from the prior city to this one
    
    
    /*the constructors
     *
     */
    public Node()
    {
        name = "";
        timeAway = 0;
        cost = 0;
    }//end of default constructor
    
    //overloaded for inputing a name, time away, and cost
    public Node(String n, double c, int t)
    {
        name = n;
        timeAway = t;
        cost = c;
    }//end of overloaded constructor (string int double)
    
    //overloaded for inputing a name
    public Node(String n)
    {
        name = n;
        timeAway = 0;
        cost = 0;
    }//end of overloaded constructor (string)
    
    
    /*the getters and setters
     *
     */
    public String getName()
    {
        return name;
    }//end of getName
    
    public int getTime()
    {
        return timeAway;
    }//end of getTime
    
    public double getCost()
    {
        return cost;
    }//end of getCost
    
    public void setName(String n)
    {
        name = n;
    }//end of setName
    
    public void setTime(int n)
    {
        timeAway = n;
    }//end of setTime
    
    public void setCost(double n)
    {
        cost = n;
    }//end of setCost
}//end of node class