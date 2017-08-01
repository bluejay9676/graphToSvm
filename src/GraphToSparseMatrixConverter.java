import com.google.common.io.Files;
import it.unimi.dsi.webgraph.ImmutableGraph;
import it.unimi.dsi.webgraph.LazyIntIterator;
import it.unimi.dsi.webgraph.NodeIterator;

import java.io.*;
import java.util.Arrays;

/**
 * Created by Jay Ryu on 7/29/17.
 */
public class GraphToSparseMatrixConverter {

    /**
     @param fileName: The name of the basefile. eg) uk-2014-tpd
     */
    public static void readAndWrite(String fileName) throws IOException {
        ImmutableGraph graph = ImmutableGraph.load(fileName);
        LazyIntIterator successors;

        String[] lines = new String[graph.numNodes()];
        Arrays.fill(lines, "-1");

        for ( NodeIterator nodeIterator = graph.nodeIterator(); nodeIterator.hasNext(); ) {
            int currNode = nodeIterator.nextInt();
            int d = nodeIterator.outdegree();
            successors = nodeIterator.successors();
            while ( d-- != 0 ) {
                int inNode = successors.nextInt();
                lines[inNode] = lines[inNode] + " " + currNode + ":1";
            }
        }

        FileOutputStream outStream = new FileOutputStream(fileName + ".svm");
        PrintWriter pw = new PrintWriter(fileName + ".svm");
        for(int i = 0; i < graph.numNodes(); i++) {
            pw.println(lines[i]);
        }
        pw.close();

        outStream.close();
    }

    public static void main(String[] args) {
        try {
            readAndWrite("uk-2007-05@100000");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
