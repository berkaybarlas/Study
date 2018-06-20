import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

public class Solution {
    
    static class Graph{
        int V;
        LinkedList<Edge>[] adjListA;
        
        Graph(int V){
            this.V = V;
            adjListA = new LinkedList[V];
            
            for(int i=0; i<V;i++){
                adjListA[i] = new LinkedList<>();
            }
        }
        
    }
    static void addEdge(Graph G, int src, int dest,int cost){
        G.adjListA[src].addLast(new Edge(cost,src,dest));
        G.adjListA[dest].addLast(new Edge(cost,src,dest));
    }
    static class Edge{
        int cost;
        int src;
        int dest;
        
        Edge(int c,int s,int d){
            cost = c;
            src = s;
            dest = d;
        }
        
        int opposite(int v){
            if(src==v) return dest;
            if(dest==v) return src;
            return -1;
        }
        
        
        
    }
    static class Verteks implements Comparable<Verteks> {
        int vertex;
        int cost;
        
        Verteks(int v,int c){
            vertex =v;
            cost = c;
        }
        
        public int compareTo(Verteks e ){
            if(cost>e.cost) return 1;
            if(cost<e.cost) return -1;
            return 0;
        }
        public boolean equals(Object o) {
 
        // If the object is compared with itself then return true  
        if (o == this) {
            return true;
        }
 
        /* Check if o is an instance of Complex or not
          "null instanceof [type]" also returns false */
        if (!(o instanceof Verteks)) {
            return false;
        }
         
        // typecast o to Complex so that we can compare data members 
        Verteks c = (Verteks) o;
         
        // Compare the data members and return accordingly 
        return vertex == c.vertex ;
    }
    }
    
    static int calculateCost(int k,int vCost,int eCost){
        int cost = vCost+eCost;
        
        if((vCost/k)%2==1) //hangi aralikta oldugunu anlamak icin yazdim
           cost+=(k-vCost%k); //isigin yesil yanmasi icin gereken sureyi hesaplamak icin
        
        return cost;
    }
    
    // Complete the leastTimeToInterview function below.
    static long leastTimeToInterview(Graph G, int n, int k, int m) {
        
        PriorityQueue<Verteks> queue = new PriorityQueue<Verteks>();
        int[] cost = new int[n];
        for(int i=0;i<n;i++){
            cost[i] = 1000;
        }
        cost[0] = 0;
        int[] visited = new int[n];
        queue.add(new Verteks(0,0));
        while(!queue.isEmpty()){
            Verteks  uv = queue.poll();
            int  u = uv.vertex;
            if(visited[u]==0){
                visited[u]=1;
                System.out.println("visited "+(u+1)+" cost"+cost[u]);
                for(Edge e : G.adjListA[u]){
                    int w = e.opposite(u);
                    if(visited[w]!=1 && cost[w]>calculateCost(k,cost[u],e.cost)){
                        cost[w] = calculateCost(k,uv.cost,e.cost);
                        queue.add(new Verteks(w,cost[w]));
                    }
                }
            }
        }
        for(int b=0;b<n;b++){
            System.out.println("costs "+(b+1)+" "+cost[b]);
        }
        return cost[n-1];
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int n = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        int k = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        int m = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");
        Graph graph = new Graph(n);
        
        for(int i=0; i<m;i++){
            int a = scanner.nextInt();
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");
            int j = scanner.nextInt();
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");
            int t = scanner.nextInt();
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");
            
            addEdge(graph,a-1,j-1,t);
            
        }
        long result = leastTimeToInterview(graph, n, k, m);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedWriter.close();

        scanner.close();
    }
}
