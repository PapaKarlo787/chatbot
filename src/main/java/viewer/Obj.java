package viewer;

import org.lwjgl.util.vector.Vector3f;

import java.io.*;
import java.util.LinkedList;

public class Obj extends Model  {
    public Obj(String fileName) {
        try {
            var fr = new BufferedReader(new FileReader(fileName));
            var normals = new LinkedList<Vector3f>();
            var vertexes = new LinkedList<Vector3f>();
            var indexes = new LinkedList<int[]>();
            String s;
            while ((s = fr.readLine()) != null) {
                processLine(s, normals, vertexes, indexes);
            }
            collectObj(vertexes, normals, indexes);
            loaded = true;
        } catch (IOException e) { }
    }

    public static Model load(String name) {
        return new Obj(name);
    }

    private void collectObj(LinkedList<Vector3f> vertexes, LinkedList<Vector3f> normals, LinkedList<int[]> indexes) {
        var n = indexes.size();
        edges = new Vector3f[n][];
        for(var i = 0; i < n; i++) {
            edges[i] = new Vector3f[4];
            for(var l = 0; l < 3; l++) {
                edges[i][l] = vertexes.get(indexes.get(i)[l]);
            }
            edges[i][3] = normals.get(indexes.get(i)[3]);
        }
    }

    private void processLine(String line,
                             LinkedList<Vector3f> normals,
                             LinkedList<Vector3f> vertexes,
                             LinkedList<int[]> indexes) {
        var parts = line.split(" ");
        if (parts[0].equals("v")) {
            vertexes.add(translate(parts));
        } else if (parts[0].equals("vn")) {
            normals.add(translate(parts));
        } else if (parts[0].equals("f")) {
            var normal = getI(parts[1]);
            var result = new int[4];
            for (var i = 0; i < 3; i++) {
                result[i] = getN(parts[i + 1]);
            }
            result[3] = normal;
            if (parts.length == 5) {
                var result1 = new int[4];
                result1[0] = getN(parts[1]);
                result1[1] = getN(parts[3]);
                result1[2] = getN(parts[4]);
                result1[3] = normal;
                indexes.add(result1);
            }
            indexes.add(result);
        }
    }

    private int getN(String s) {
        return Integer.parseInt(s.split("/")[0]) - 1;
    }

    private int getI(String s) {
        return Integer.parseInt(s.split("/")[2]) - 1;
    }

    private static Vector3f translate(String[] vector) {
        var x = Float.parseFloat(vector[1]);
        var y = Float.parseFloat(vector[2]);
        var z = Float.parseFloat(vector[3]);
        return new Vector3f(x, y, z);
    }

    public static Integer save(String fileName, LinkedList<Model> models) {
        try {
            var fo = new BufferedWriter(new FileWriter(fileName));
            var countEdges = 0;
            for (var model : models) {
                for (var edge : model.edges) {
                    for (var i = 0; i < 3; i++) {
                        fo.write(toString(edge[i], "v"));
                    }
                    fo.write(toString(edge[3], "vn"));
                    fo.write(getStrEdge(countEdges));
                    countEdges++;
                }
            }
            fo.close();
        } catch (IOException e) {
            return -1;
        }
        return 0;
    }

    private static String getStrEdge(int i) {
        var res = new String[4];
        res[0] = "f";
        res[1] = getPartStrEdge(i*3+1, i+1);
        res[2] = getPartStrEdge(i*3+2, i+1);
        res[3] = getPartStrEdge(i*3+3, i+1);
        return String.join(" ", res) + "\n";
    }

    private static String getPartStrEdge(int i1, int i2) {
        return String.join("/0/", String.valueOf(i1), String.valueOf(i2));
    }

    private static String toString(Vector3f vec, String starting) {
        var strEdge = new String[4];
        strEdge[0] = starting;
        strEdge[1] = String.valueOf(vec.x);
        strEdge[2] = String.valueOf(vec.y);
        strEdge[3] = String.valueOf(vec.z);
        return String.join(" ", strEdge) + "\n";
    }
}
