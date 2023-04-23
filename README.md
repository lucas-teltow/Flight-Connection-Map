# Flight-Connection-Map
A queryable graph of the time and monetary cost of flying inbetween various cities.

This program takes in a file with a list of the connections between cities, including the time it takes to fly between them and the cost, and creates a graph that stores that data. The graph can then be used to tell you the fastest or the cheapest way to get from any city on the graph to any other city on the graph. It prints all output to a text file, and you can inpute multiple queries at a time. This files makes use of a heap to find the most effecient path (either by cost or time) between any 2 cities.

The file storing the initial graph information is infile.txt, and the paths that the user wants to know about are listed in paths.txt. All output is printed to  outfile.txt.

**infile.txt**
The first line is the number of edges for the graph, or the number of lines in the file minus 1. All subsequent lines are in the following format: 
[name of starting city]|[name of destination city]|[cost of flight]|[how long the flight takes]

**paths.txt**
The first line is the number of queries, or the number of lines in the file minus 1. All subsequent lines are in the following format:
[name of starting city]|[name of destination city]|[either T or C depeding on if you want to sort by time or cost]
