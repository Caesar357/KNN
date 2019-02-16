package a;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

//use APSP algorithm to solve the shortest path problem
public class test1{
//set 10000 as the largest number
public static final int MAX_VALUE = 10000;
public static int[][] apsp(int[][] nums) {
     int N = nums.length;
     int[][][] dp = new int[N + 1][N][N];
     int[][] res = new int[N][N];
     for(int i = 0; i < N; i++) {
     for(int j = 0; j < N; j++) { 
//it dose not use any node from i to j
				dp[0][i][j] = nums[i][j];
				res[i][j] = MAX_VALUE;
//distance to itself is defined to be 0
      if(i == j) res[i][j] = 0;
			}
		}
      for(int k = 1; k <= N; k++){
          for(int i = 0; i < N; i++){
      for(int j = 0; j < N; j++){
                     if(i == j) continue;
					dp[k][i][j] = Math.min(dp[k - 1][i][j], dp[k - 1][i][k - 1]+dp[k - 1][k - 1][j]);
					res[i][j] = Math.min(res[i][j], dp[k][i][j]);
				}	
			}
		}	
      return res;
	}

public static void main(String[] args) throws IOException {
    File dataset = new File("/Users/fengpeicheng/Desktop/Edges.txt");
    FileInputStream inputStream = new FileInputStream(dataset);
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
    //there are 100 vertices
    int[][] nums = new int[100][100];
    for(int i = 0; i < nums.length; i++) {
        for(int j = 0; j < nums.length; j++) {
              if(i != j) nums[i][j] = MAX_VALUE;
			}
		}
    String str = null;
    while((str = bufferedReader.readLine()) != null)
		{
    	
//read fromIndex(i), toIndex(j), edgeCost(dist) from file
         int i=Integer.parseInt(str.split(",")[0]);
         int j=Integer.parseInt(str.split(",")[1]);
         int dist=Integer.parseInt(str.split(",")[2]);
			nums[i][j] = dist;
			nums[j][i] = dist;
		}
    
    
    int[][] res = apsp(nums);
//change the input of start and end points in res[][], we can get different value of shortest path
    long startTime = System.nanoTime();
    System.out.println("Shortest path is: " + res[1][45]);
    long endTime = System.nanoTime();
    System.out.println("Astar Time: " + (endTime - startTime) + "ns");
	inputStream.close();
	bufferedReader.close();
	}
}
