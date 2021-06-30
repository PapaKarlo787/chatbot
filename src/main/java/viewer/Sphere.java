package viewer;

import org.lwjgl.util.vector.Vector3f;
import java.util.LinkedList;

public class Sphere extends Model {
    public  Sphere() {
        var n = 100;
        var result = new LinkedList<Vector3f[]>();
        var points  = getPoints(n);
        for (var i = 0; i < n - 1; i++) {
            for (var l = 0; l < n - 1; l++) {
                getTriangles(points, i, l, result);
            }
        }
        for (var i = 0; i < n - 1; i++) {
            var edge = new Vector3f[4];
            edge[0] = new Vector3f(0, 1, 0);
            edge[1] = points[0][i];
            edge[2] = points[0][i+1];
            edge[3] = getNormal(edge[0], edge[1], edge[2]);
            result.add(edge);
        }
        reflect(result, new Vector3f(-1, 1, 1));
        reflect(result, new Vector3f(1, -1, 1));
        reflect(result, new Vector3f(1, 1, -1));
        edges = new Vector3f[result.size()][4];
        result.toArray(edges);
    }

    private void reflect(LinkedList<Vector3f[]> result, Vector3f sign) {
        var tempArray = new Vector3f[result.size()][4];
        result.toArray(tempArray);
        for (var edge : tempArray) {
            var newEdge = new Vector3f[edge.length];
            for (var i = 0; i < edge.length; i++) {
                newEdge[i] = new Vector3f(edge[i].x * sign.x, edge[i].y * sign.y, edge[i].z * sign.z);
            }
            result.add(newEdge);
        }
    }

    private void getTriangles(Vector3f[][] points, int i, int l, LinkedList<Vector3f[]> result) {
        var edge1 = new Vector3f[4];
        var edge2 = new Vector3f[4];
        edge1[0] = new Vector3f(points[i][l]);
        edge1[1] = new Vector3f(points[i+1][l+1]);
        edge1[2] = new Vector3f(points[i+1][l]);
        edge2[0] = new Vector3f(points[i][l]);
        edge2[1] = new Vector3f(points[i][l+1]);
        edge2[2] = new Vector3f(points[i+1][l+1]);
        var normal = getNormal(edge1[0], edge1[1], edge1[2]);
        edge1[3] = normal;
        edge2[3] = normal;
        result.add(edge1);
        result.add(edge2);
    }

    private Vector3f[][] getPoints(int n) {
        var angle = Math.PI / 2 / (n - 1);
        var result = new Vector3f[n][n];
        for (var i = 0; i < n; i++) {
            var r = Math.sin(angle * i);
            var y = (float) Math.cos(angle * i);
            for (var l = 0; l < n; l++) {
                var x = (float) (Math.cos(angle * l) * r);
                var z = (float) (Math.sin(angle * l) * r);
                result[i][l] = new Vector3f(x, y, z);
            }
        }
        return result;
    }
}
