package a;

import java.io.File;
import java.io.FileInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

//implement A* algorithm with Manhattan distance
//key equatation: f(n) = g(n) + h(n)
final class Vertice{
	int name;
	int gLen;
	int hLen;
	Set<Integer> roots;
	public Vertice(int name, int gLen, int hLen, Set<Integer> roots) {
		this.name = name;
		this.hLen = hLen;
		this.gLen = gLen;
		this.roots = roots;		
	} 
} 

public class test2 {	
	//let 100000000 as the largest number
	public static final int MAX_VALUE = 100000000;
	public static final int d = 10;
	public static int Astar(int start, int end, int[][] vertices, int[][] edges) {
		int res=0;
		//a priority queue which can sort vertices in increasing order
		PriorityQueue<Vertice> queue = new PriorityQueue<Vertice>(new Comparator<Vertice>() {

			public int compare(Vertice v1, Vertice v2) {
				return v1.hLen - v2.hLen;
			}
		});
		
		Set<Integer> root= new HashSet<Integer>();
		root.add(start);
		Vertice v = new Vertice(start, forhLen(start, end, vertices) + 0, 0, root); 
		queue.add(v);

		//when we dequeue a goal it just can stop
		while(queue.poll().name != end) {
			for(int i = 0; i < vertices.length; i++) {
				//get all connecting nodes which is new
				if(edges[start][i] != 0 && edges[start][i] != MAX_VALUE && !root.contains(i)){
					int dis = edges[start][i];
					int hLen = forhLen(i, end, vertices);
					int gLen = dis + v.gLen;
					Set<Integer> parents = new HashSet<Integer>();
					parents.addAll(root);
					parents.add(i);
					Vertice ve = new Vertice(i, gLen + hLen, gLen, parents);
					queue.add(ve);
				}
			}
			v = queue.peek();
			start = v.name;
			res = v.gLen;
			root = v.roots;
		}
		
		return res;
	}
	
	//hLen is each vertice to the end node.
	public static int forhLen(int start, int end, int[][] vertices) {
		return d * (Math.abs(vertices[start][0] - vertices[end][0]) + Math.abs(vertices[start][1] - vertices[end][1]));
	}
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		int[][] vertices = new int[100][2];
		File dataset1 = new File("/Users/fengpeicheng/Desktop/Vertices.txt");
		FileInputStream inputStream1 = new FileInputStream(dataset1);
		BufferedReader bufferedReader1 = new BufferedReader(new InputStreamReader(inputStream1));
		String str1 = null;
		int count=0;
		while((str1 = bufferedReader1.readLine()) != null)
		{
			//read SquareX(i), SquareY(j) from file
			int i = Integer.parseInt(str1.split(",")[1]);
			int j = Integer.parseInt(str1.split(",")[2]);
			vertices[count][0] = i;
			vertices[count][1] = j;
			count++;
		}
		
		int[][] edges = new int[100][100];	
		for(int i = 0; i < edges.length; i++) {
			for(int j = 0; j < edges.length; j++) {
				if(i != j) edges[i][j] = MAX_VALUE;
				else edges[i][j] = 0;
			}		
		}
		
		File dataset2 = new File("/Users/fengpeicheng/Desktop/Edges.txt");
		FileInputStream inputStream2 = new FileInputStream(dataset2); 
		BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(inputStream2));
		String str2 = null;
		while((str2 = bufferedReader2.readLine()) != null)
		{
			//read fromIndex(i), toIndex(j), edgeCost(dist) from file
			int i = Integer.parseInt(str2.split(",")[0]);
			int j = Integer.parseInt(str2.split(",")[1]);
			int dist = Integer.parseInt(str2.split(",")[2]);
			edges[i][j] = dist;
			edges[j][i] = dist;
		}
		
		//change the input of start and end points in res[][], we can get different value of shortest path
		int res = Astar(1, 45, vertices, edges);
		long startTime = System.nanoTime();
		System.out.println("Shortest path is: " + res);
		long endTime = System.nanoTime();
		System.out.println("Astar Time: " + (endTime - startTime) + "ns");
		inputStream1.close();
		bufferedReader1.close();
		inputStream2.close();
		bufferedReader2.close();
	}
}

