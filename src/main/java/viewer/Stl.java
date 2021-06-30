package viewer;

import org.lwjgl.util.vector.Vector3f;
import java.io.*;
import java.nio.ByteBuffer;
import java.util.LinkedList;

public class Stl extends Model {
    public Stl(String fileName) {
        try {
            var fin = new FileInputStream(fileName);
            var temp = new byte[5];
            fin.read(temp);
            StringBuilder solid = new StringBuilder();
            for (var e : temp) {
                solid.append((char) e);
            }
            edges = solid.toString().equals("solid") ? loadText(fileName) : loadBytes(fin);
            fin.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Model load(String fileName) {
        return new Stl(fileName);
    }

    public static Integer save(String fileName, LinkedList<Model> models) {
        try {
            var fo = new FileOutputStream(fileName, false);
            fo.write(new byte[80]);
            var n = 0;
            for (var model : models) {
                n += model.edges.length;
            }
            fo.write(intToBytes(n));
            for (var model : models) {
                for (var edge : model.edges) {
                    for (var i = 3; i < 7; i++) {
                        writeVector(edge[i % 4], fo);
                    }
                    fo.write(new byte[]{0, 0});
                }
            }
            fo.close();
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
        return 0;
    }

    private static void writeVector(Vector3f v, FileOutputStream f) throws IOException {
        f.write(intToBytes(Float.floatToIntBits(v.x)));
        f.write(intToBytes(Float.floatToIntBits(v.y)));
        f.write(intToBytes(Float.floatToIntBits(v.z)));
    }

    private static byte[] intToBytes(int n) {
        var result = new byte[4];
        for (var i = 0; i < 4; i++) {
            result[i] = (byte)(Integer.remainderUnsigned(n, 256));
            n = Integer.divideUnsigned(n, 256);
        }
        return result;
    }

    private Vector3f[][] loadText(String fn) throws IOException {
        var fr = new BufferedReader(new FileReader(fn));
        fr.readLine();
        String normal;
        var result = new LinkedList<Vector3f[]>();
        while(!(normal = fr.readLine()).equals("")) {
            var edge = new Vector3f[4];
            edge[3] = readTextVector(normal.split(" "), 2);
            fr.readLine();
            for (var i = 0; i < 3; i++) {
                edge[i] = readTextVector(fr.readLine().split(" "), 1);
            }
            fr.readLine();
            fr.readLine();
        }
        fr.close();
        edges = new Vector3f[result.size()][4];
        result.toArray(edges);
        return edges;
    }

    private Vector3f[][] loadBytes(FileInputStream fin) throws IOException {
        var lenBuf = new byte[4];
        fin.skip(75);
        fin.read(lenBuf);
        reflect(lenBuf);
        var len = ByteBuffer.wrap(lenBuf).getInt();
        var result = new Vector3f[len][4];
        for (var i = 0; i < len; i++) {
            for (var l = 3; l < 7; l++) {
                result[i][l % 4] = readByteVector(fin);
            }
            fin.skip(2);
        }
        return result;
    }

    private Vector3f readTextVector(String[] nums, int n) {
        var x = Float.parseFloat(nums[n]);
        var y = Float.parseFloat(nums[n]);
        var z = Float.parseFloat(nums[n]);
        return new Vector3f(x, y, z);
    }

    private Vector3f readByteVector(FileInputStream fin) throws IOException {
        var temp = new byte[4];
        var coords = new float[3];
        for (var i = 0; i < 3; i++) {
            fin.read(temp);
            reflect(temp);
            coords[i] = ByteBuffer.wrap(temp).getFloat();
        }
        return new Vector3f(coords[0], coords[1], coords[2]);
    }

    private void reflect(byte[] buf) {
        for (var i = 0; i < 2; i++) {
            var temp = buf[i];
            buf[i] = buf[3-i];
            buf[3-i] = temp;
        }
    }
}
