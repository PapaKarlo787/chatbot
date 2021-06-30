package viewer;

import static org.lwjgl.util.glu.GLU.gluPerspective;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.*;
import java.nio.FloatBuffer;
import java.util.LinkedList;
import java.util.Stack;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class Viewer extends Thread{
    private boolean isActive = true;
    private final LinkedList<Model> models = new LinkedList<>();
    public Stack<Model> modelsPublic = new Stack<>();
    private int nSelected = -1;

    private FloatBuffer floatBuffer(float... values) {
       FloatBuffer buffer = BufferUtils.createFloatBuffer(Math.max(4, values.length));
       buffer.put(values);
       return buffer.rewind();
    }

    public void removeCurrent() {
        models.remove(nSelected);
        nSelected = models.size() != 0 ? nSelected % models.size() : -1;
    }

    public void incSelection() {
        if (models.size() > 0) {
            nSelected = (nSelected + 1) % models.size();
        }
    }

    public void decSelection() {
        if (models.size() > 0) {
            nSelected = (nSelected - 1 + models.size()) % models.size();
        }
    }

    public Model getSelected() {
        return models.get(nSelected);
    }

    private void prepare() {
        try {
            Display.setDisplayMode(new DisplayMode(1024, 768));
            Display.setTitle("3D");
            Display.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
        }
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        gluPerspective(60, (float) 1200 / (float) 900, 0.001f, 1000);
        glMatrixMode(GL_MODELVIEW);
        glClearColor(0.1f, 0.2f, 0.5f, 0.0f);
        glEnable(GL_DEPTH_TEST);
        glLight(GL_LIGHT0, GL_POSITION, floatBuffer(20, 20, 50));
        glEnable(GL_LIGHTING);
	    glEnable(GL_LIGHT0);
        glEnable(GL_COLOR_MATERIAL);
    }

    @Override
    public void run() {
        prepare();
        while(isActive) {
            glClear(GL_COLOR_BUFFER_BIT|GL_DEPTH_BUFFER_BIT);
            var n = 0;
            for (Model model : models) {
                model.rendering(n++ == nSelected);
            }
            Controller.running();
            Display.update();
            Display.sync(75);
            for (var i = 0; i < modelsPublic.size(); i++) {
                nSelected = models.size();
                models.add(modelsPublic.pop());
            }
        }
        Display.destroy();
    }

    public void exit() {
        isActive = false;
    }

    public LinkedList<Model> getModels() {
        return models;
    }
}
