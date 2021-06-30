package viewer;

import org.lwjgl.util.vector.Vector3f;

public class Cube extends Model {
    public Cube()
    {
        edges = new Vector3f[12][4];
        edges[0] = new Vector3f[] { 
            new Vector3f(-1, 1, -1), new Vector3f(-1, -1, -1), new Vector3f(1, 1, -1), new Vector3f(0, 0, -1)
        };
        
        edges[1] = new Vector3f[] { 
            new Vector3f(-1, -1, -1), new Vector3f(1, 1, -1), new Vector3f(1, -1, -1), new Vector3f(0, 0, -1)
        };
        var pi = (float) (Math.PI / 2);
        edges[2] = rotateEdge(edges[0], pi, 0, 0);
        edges[3] = rotateEdge(edges[1], pi, 0, 0);
        edges[4] = rotateEdge(edges[0], 0, pi, 0);
        edges[5] = rotateEdge(edges[1], 0, pi, 0);
        edges[6] = rotateEdge(edges[0], -pi, 0, 0);
        edges[7] = rotateEdge(edges[1], -pi, 0, 0);
        edges[8] = rotateEdge(edges[0], 0, -pi, 0);
        edges[9] = rotateEdge(edges[1], 0, -pi, 0);
        edges[10] = rotateEdge(edges[0], 2 * pi, 0, 0);
        edges[11] = rotateEdge(edges[1], 2 * pi, 0, 0);
    }
}
