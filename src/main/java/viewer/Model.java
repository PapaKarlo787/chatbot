package viewer;

import org.lwjgl.util.Color;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glColor3f;
import java.util.HashMap;
import org.lwjgl.util.vector.Vector3f;

public abstract class Model {
    private Color color = (Color) Color.RED;
    public Vector3f[][] edges;
    private final HashMap<String, Color> colors = new HashMap();
    private Vector3f centre = new Vector3f(0,0,0);
    protected boolean loaded = false;
    
    public Model() {
        colors.put("red", (Color) Color.RED);
        colors.put("black", (Color) Color.BLACK);
        colors.put("blue", (Color) Color.BLUE);
        colors.put("cyan", (Color) Color.CYAN);
        colors.put("dark_grey", (Color) Color.DKGREY);
        colors.put("green", (Color) Color.GREEN);
        colors.put("grey", (Color) Color.GREY);
        colors.put("light_grey", (Color) Color.LTGREY);
        colors.put("orange", (Color) Color.ORANGE);
        colors.put("purple", (Color) Color.PURPLE);
        colors.put("white", (Color) Color.WHITE);
        colors.put("yellow", (Color) Color.YELLOW);
    }
    
    public void move(float x, float y, float z) {
        centre = new Vector3f(x + centre.x, y + centre.y, z + centre.z);
    }
    
    public void rotate(float x, float y, float z) {
        for (var i = 0; i < edges.length; i++) {
            edges[i] = rotateEdge(edges[i], x, y, z);
        }
    }

    protected Vector3f getNormal(Vector3f a, Vector3f b, Vector3f c) {
        var v1 = new Vector3f(a.x - b.x, a.y - b.y, a.z - b.z);
        var v2 = new Vector3f(b.x - c.x, b.y - c.y, b.z - c.z);
        var result = new Vector3f(v1.y * v2.z - v1.z * v2.y, v1.z * v2.x - v1.x * v2.z, v1.x * v2.y - v1.y * v2.x);
        var len = (float) Math.sqrt(result.x * result.x + result.y * result.y + result.z * result.z);
        return new Vector3f(result.x / len, result.y / len, result.z / len);
    }

    protected Vector3f[] rotateEdge(Vector3f[] edge, float x, float y, float z) {
        var result = new Vector3f[edge.length];
        for (var i = 0; i < result.length; i++) {
            result[i] = new Vector3f(0, 0, 0);
            result[i].z = (float) (edge[i].z * Math.cos(x) - edge[i].y * Math.sin(x));
            result[i].y = (float) (edge[i].z * Math.sin(x) + edge[i].y * Math.cos(x));
            var temp = (float) (result[i].z * Math.cos(y) - edge[i].x * Math.sin(y));
            result[i].x = (float) (result[i].z * Math.sin(y) + edge[i].x * Math.cos(y));
            result[i].z = temp;
            temp = (float) (result[i].y * Math.cos(z) - result[i].x * Math.sin(z));
            result[i].x = (float) (result[i].y * Math.sin(z) + result[i].x * Math.cos(z));
            result[i].y = temp;
        }
        return result;
    }
    
    public void size(float k) {
        for (var vertex : edges) {
            for (var e = 0; e < 3; e++) {
                vertex[e] = new Vector3f(vertex[e].x * k, vertex[e].y * k, vertex[e].z * k);
            }
        }
    }
    
    private Vector3f add(Vector3f self, Vector3f other) {
        return new Vector3f(self.x + other.x, self.y + other.y, self.z + other.z);
    }
    
    public void setColor(String[] line) {
        if (line.length == 1 && colors.containsKey(line[0])) {
            color = colors.get(line[0]);
        } else {
            color.set(Integer.parseInt(line[0]), Integer.parseInt(line[1]), Integer.parseInt(line[2]));
        }
    }
    
    public void rendering(boolean selected) {
        glBegin(GL_TRIANGLES);
        var colors = new Color[] {(Color) Color.RED, (Color) Color.GREEN, (Color) Color.BLUE};
        if (!selected) {
            glColor3f(color.getRed() / 256f, color.getGreen() / 256f, color.getBlue() / 256f);
        }
        for(var edge : edges) {
            var v = edge[3];
            glNormal3f(v.x, v.y, v.z);
            for (var i = 0; i < 3; i++) {
                v = add(edge[i], centre);
                if (selected) {
                    glColor3f(colors[i].getRed() / 256f, colors[i].getGreen() / 256f, colors[i].getBlue() / 256f);
                }
                glVertex3f(v.x, v.y, v.z);
            }
        }
        glEnd();
    }
}
