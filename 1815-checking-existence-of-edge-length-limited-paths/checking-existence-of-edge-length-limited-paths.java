import java.util.*;

class DSU {

    int[] parent;
    int[] rank;

    // Constructor
    DSU(int n) {
        parent = new int[n + 1];
        rank = new int[n + 1];

        // Initially every node is its own parent
        for (int i = 0; i <= n; i++) {
            parent[i] = i;
            rank[i] = 0;
        }
    }

    // Find the leader (parent) of a node
    int findParent(int node) {
        if (node == parent[node]) {
            return node;
        }

        // Path compression
        parent[node] = findParent(parent[node]);
        return parent[node];
    }

    // Reset a node (break it from its group)
    void resetNode(int node) {
        parent[node] = node;
        rank[node] = 0;
    }

    // Join two groups together
    void unionNodes(int node1, int node2) {

        int parent1 = findParent(node1);
        int parent2 = findParent(node2);

        if (parent1 == parent2) {
            return;
        }

        if (rank[parent1] > rank[parent2]) {
            parent[parent2] = parent1;
        } 
        else if (rank[parent1] < rank[parent2]) {
            parent[parent1] = parent2;
        } 
        else {
            parent[parent1] = parent2;
            rank[parent2]++;
        }
    }
}
class Solution{

   public boolean[] distanceLimitedPathsExist(int n, int[][] edgeList, int[][] queries) {
    DSU dsu = new DSU(n);
        boolean[] ans = new boolean[queries.length];

        Arrays.sort(edgeList, (a,b) -> a[2] - b[2]);

        Integer[] idx = new Integer[queries.length];

        for(int i = 0; i < queries.length; i++){
            idx[i] = i;
        }

        Arrays.sort(idx, (a,b) -> queries[a][2] - queries[b][2]);

        int j = 0;

        for(int i : idx){

            while(j < edgeList.length && edgeList[j][2] < queries[i][2]){
                dsu.unionNodes(edgeList[j][0], edgeList[j][1]);
                j++;
            }

            if(dsu.findParent(queries[i][0]) == dsu.findParent(queries[i][1])){
                ans[i] = true;
            }
        }

        return ans;
    }
}
     

        

        
