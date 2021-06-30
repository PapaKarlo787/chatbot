package viewer;

import org.lwjgl.util.vector.Vector3f;

public class Tetrahedron extends Model {
    public Tetrahedron() {
        var a = new Vector3f(0, -0.25f, 2f / 3);
        var b = new Vector3f(0, 0.75f, 0);
        var c = new Vector3f((float) Math.sqrt(3) / 3, -0.25f, -1f / 3);
        var d = new Vector3f((float) -Math.sqrt(3) / 3, -0.25f, -1f / 3);
        edges = new Vector3f[][] {{a, c, b, a}, {a, b, d, a}, {a, d, c, a}, {c, d, b, a}};
        for (var edge : edges) {
            for (var i = 0; i < 3; i++)
                edge[i] = new Vector3f(edge[i]);
            edge[3] = getNormal(edge[0], edge[1], edge[2]);
        }
    }
}
