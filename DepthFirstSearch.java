import java.io.*;
import java.util.*;


public class DepthFirstSearch {
	boolean[] visited;
	Graph graph;
	int nodeAccessCount = 0;
	
	public DepthFirstSearch(Graph g) {
		visited = new boolean[g.getVerticesCount()];
		graph = g;
	}
	
	public void init() {
		for(int i = 0; i < graph.getNodes().size(); i++) {
			if(visited[i] == false) {
				dfs(i);
			}
		}
		System.err.println("Total access: "+ nodeAccessCount);
		System.err.println("Access/NodeCount = "+ nodeAccessCount/graph.getVerticesCount());
	}
	
	public void dfs(int vertex) {
		visited[vertex] = true;
		nodeAccessCount++;
		Graph.Node node = graph.getNodes().get(vertex);
		if(node.vertex == vertex) {
			Iterator<Integer> edgeIterator = node.getEdges().listIterator();
			System.err.println(vertex +" ");
			while(edgeIterator.hasNext()) {
				Integer nextNode = edgeIterator.next();
				if(!visited[nextNode]) {
					dfs(nextNode);
				}
			}
		}

	}	
	
	static class Graph{
		int numVertices;
		int numEdges;
		ArrayList<Graph.Node> nodes;
		
		Graph(List<String> data){
			createNodes(data);
			numVertices = nodes.size();
			numEdges = countEdges();
		}
		
		public void createNodes(List<String> data) {
			nodes = new ArrayList<Graph.Node>();
			for(int i = 0; i < data.size(); i++) {
				String vertex = data.get(i).split(":")[0];
				String edgesString ="";
				String[] edges = new String[0];
				if(data.get(i).split(":").length > 1) {
					edgesString = data.get(i).split(":")[1].trim();
					edges = new String[edgesString.split(",").length];
					for(int x = 0; x <= (edgesString.split(",").length-1); x++) {
						edges[x] = edgesString.split(",")[x];
					}
				}

				try {
					nodes.add(new Node(vertex, edges));

				}
				catch(Exception e) {
					System.err.println("Unable to create new Node.");
					throw e;
				}
			}
		}
		
		public int countEdges(){
			int count = 0;
			for(int i = 0; i < nodes.size(); i++){
				Node node = nodes.get(i);		
				count += node.getEdges().size();
			}
			return count;
		}
		
		public int getVerticesCount() {
			return this.numVertices;
		}
		
		public ArrayList<Graph.Node> getNodes(){
			return this.nodes;
		}
		
		public int getEdgeCount() {
			return this.numEdges;
		}
		
		class Node{
			int vertex;
			ArrayList<Integer> edges;
			
			Node(String vertex_data, String[] edges_data){
				try {
					this.vertex = Integer.parseInt(vertex_data);
					this.edges = new ArrayList<Integer>();
					for(int i = 0; i < edges_data.length; i++) {
						this.edges.add(Integer.parseInt(edges_data[i]));
					}
				}
				catch(Exception e) {
					e.printStackTrace();
					throw e;
				}
			}
			
			public ArrayList<Integer> getEdges(){
				return this.edges;
			}
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void main(String[] args) {
		if(args.length < 1){
			System.err.println("Provide a file as argument"); 
			System.exit(0);
		}
		String fileName = args[0];
		String line = null;
		DepthFirstSearch dfs;
		Graph graph;
		List<String> unstructuredNodes = new ArrayList<String>();
		try{
		  FileReader fileReader = new FileReader(fileName);
		  BufferedReader bufferedReader = new BufferedReader(fileReader);
		  while((line = bufferedReader.readLine()) != null){
			unstructuredNodes.add(line);
		  }
		  graph = new Graph(unstructuredNodes);
		  dfs = new DepthFirstSearch(graph);
		  dfs.init();
		  
		  bufferedReader.close();

		}
		catch(FileNotFoundException ex){
			System.err.println("Unable to open file "+fileName);
		}
		catch(IOException ex){
			System.err.println("Error reading file");
			ex.printStackTrace();
		}
	}
}
