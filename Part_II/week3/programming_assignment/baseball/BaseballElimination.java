import java.util.ArrayList;
import java.util.HashMap;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;

public class BaseballElimination {

    private final  int numTeams;
    private final int numVertices;
    private final int sourceVertexIdx;
    private final int targetVertexIdx;
    private final ArrayList<String> teams;
    private final HashMap<String, Integer> teamToVertex;
    private final HashMap<String, Integer[]> teamRecord;
    private String prevTestedTeam;
    private FordFulkerson ff;
    private ArrayList<String> subsetR;

    // create a baseball division from given filename in format specified below
    public BaseballElimination(String filename) {

        In in = new In(filename);
        numTeams = Integer.parseInt(in.readLine());
        // include all the teams and games in the flow network
        numVertices = numTeams + (numTeams * numTeams - numTeams) / 2 + 2;
        sourceVertexIdx = 0;
        targetVertexIdx = numVertices - 1;
        teams = new ArrayList<>();
        teamToVertex = new HashMap<>();
        teamRecord = new HashMap<>();

        // encode team vertex index from 1
        int vertexIdx = 1;
        while (in.hasNextLine()) {
            String line = in.readLine().trim();
            String[] entries = line.split("\\s+");
            teams.add(entries[0]);
            teamToVertex.put(entries[0], vertexIdx);
            Integer[] numbers = new Integer[entries.length-1];
            for (int i = 1; i < entries.length; i++) {
                numbers[i-1] = Integer.parseInt(entries[i]);
            }
            teamRecord.put(entries[0], numbers);
            vertexIdx += 1;
        }
    }

    // number of teams
    public int numberOfTeams() {
        return numTeams;
    }
    // all teams
    public Iterable<String> teams() {
        return teams;
    }                      

    // number of wins for given team
    public int wins(String team) {
        checkTeamValidity(team);
        return teamRecord.get(team)[0];
    }
    // number of losses for given team
    public int losses(String team) {
        checkTeamValidity(team);
        return teamRecord.get(team)[1];
    }
    // number of remaining games for given team
    public int remaining(String team) {
        checkTeamValidity(team);
        return teamRecord.get(team)[2];
    }
    // number of remaining games between team1 and team2
    public int against(String team1, String team2) {
        checkTeamValidity(team1);
        checkTeamValidity(team2);
        return teamRecord.get(team1)[2 + teamToVertex.get(team2)];
    }
    // is given team eliminated?
    public boolean isEliminated(String team) {
        checkTeamValidity(team);
        runFordFulkerson(team);
        if (!subsetR.isEmpty()) {
            return true;
        }
        int maxFlow = 0;
        for (String team1 : teams()) {
            int team1VertexIdx = teamToVertex.get(team1);
            for (String team2 : teams()) {
                int team2VertexIdx = teamToVertex.get(team2);
                if (team1VertexIdx > team2VertexIdx && !team1.equals(team) 
                        && !team2.equals(team)) {
                    maxFlow += against(team1, team2);
                }
            }
        }  
        if (ff.value() == maxFlow) {
            return false;
        } 
        return true;
    }

    // subset R of teams that eliminates given team; null if not eliminated
    public Iterable<String> certificateOfElimination(String team) {
        if (!isEliminated(team)) {
            return null;
        }

        if (!subsetR.isEmpty()) {
            return subsetR;
        }

        subsetR = new ArrayList<>();
        for (String t: teams()) {
            int idx = teamToVertex.get(t);
            if (ff.inCut(idx)) {
                subsetR.add(t);
            }
        }
        return subsetR;
    }

    private void checkTeamValidity(String team) {
        if (!teams.contains(team)) {
            throw new IllegalArgumentException(
                    "input arguments contain a invalid team");
        }
    }

    private void runFordFulkerson(String team) {

        if (team.equals(prevTestedTeam)) {
            return;
        }

        int mostWins = wins(team) + remaining(team);

        // game index
        int vertexIdx = numTeams + 1;

        // build a FlowNetwork
        FlowNetwork network = new FlowNetwork(numVertices);
        FlowEdge e;
        subsetR = new ArrayList<>();

        for (String team1 : teams()) {
            int team1VertexIdx = teamToVertex.get(team1);
            if (!team1.equals(team)) {
                // add edges between team vertices and target vertex
                if (mostWins - wins(team1) < 0) {
                    subsetR.add(team1);
                } else {
                    e = new FlowEdge(team1VertexIdx, targetVertexIdx,
                            mostWins - wins(team1));
                    network.addEdge(e);
                }
            }

            if (!subsetR.isEmpty()) {
                continue;
            }

            for (String team2 : teams()) {
                int team2VertexIdx = teamToVertex.get(team2);

                if (team1VertexIdx <= team2VertexIdx) {
                    continue;
                }

                if (!team1.equals(team) && !team2.equals(team)) {
                    // add edges between source (vertex 0) and game vertices
                    e = new FlowEdge(sourceVertexIdx, vertexIdx,
                            against(team1, team2));
                    network.addEdge(e);
                    
                    // add edges between game vertices and team vertices
                    e = new FlowEdge(vertexIdx, team1VertexIdx,
                            Double.POSITIVE_INFINITY);
                    network.addEdge(e);
                    e = new FlowEdge(vertexIdx, team2VertexIdx,
                            Double.POSITIVE_INFINITY);
                    network.addEdge(e);

                }
                vertexIdx += 1;
            }
        }

        prevTestedTeam = team;

        if (!subsetR.isEmpty()) {
            return;
        }

        ff = new FordFulkerson(network, sourceVertexIdx, targetVertexIdx);
    }

    public static void main(String[] args) {

        BaseballElimination division = new BaseballElimination(args[0]);

	for (String team : division.teams()) {
	    if (division.isEliminated(team)) {
		StdOut.print(team + " is eliminated by the subset R = { ");
		for (String t : division.certificateOfElimination(team)) {
		    StdOut.print(t + " ");
		}
		StdOut.println("}");
	    }
	    else {
		StdOut.println(team + " is not eliminated");
	    }
	}
    }
}
