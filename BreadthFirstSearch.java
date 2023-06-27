import java.util.*;

public class BreadthFirstSearch {
    private static void bfs(List<List<Integer>> graph, int start) {
        int numNodes = graph.size();
        boolean[] visited = new boolean[numNodes];
        Queue<Integer> queue = new LinkedList<>();
        
        visited[start] = true;
        queue.offer(start);
        
        while (!queue.isEmpty()) {
            int node = queue.poll();
            System.out.print(node + " ");
            
            for (int neighbor : graph.get(node)) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    queue.offer(neighbor);
                }
            }
        }
    }
    
    public static void main(String[] args) {
        int numNodes = 7;
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < numNodes; i++) {
            graph.add(new ArrayList<>());
        }
        
        graph.get(0).add(1);
        graph.get(0).add(2);
        graph.get(1).add(3);
        graph.get(1).add(4);
        graph.get(2).add(5);
        graph.get(2).add(6);
        
        bfs(graph, 0);
    }
}
