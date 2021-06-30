package viewer;

import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

public class Controller {
    private static Vector3f totalPosition = new Vector3f(0, -1.1f, -5);
    private static Vector3f position = new Vector3f(0, -1.1f, -5);
    private static Vector3f rotation = new Vector3f(0, 0, 0);
    private static float walkingSpeed = 40;
    private static long lastFrame;

    private static long getTime() {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }

    public static void setPosition(float x, float y, float z) {
        position = new Vector3f(x, y, z);
        totalPosition = new Vector3f(position);
    }

    private static float getDelta() {
        var currentTime = getTime();
        var delta = (currentTime - lastFrame) * walkingSpeed * 0.0002f;
        lastFrame = getTime();
        return delta;
    }

    public static void running() {
        glLoadIdentity();
        glRotatef(rotation.x, 1, 0, 0);
        glRotatef(rotation.y, 0, 1, 0);
        glRotatef(rotation.z, 0, 0, 1);
        glTranslatef(position.x, position.y, position.z);

        if(Mouse.isButtonDown(0) || Mouse.isGrabbed()) {
            rotation.y = (360 + Mouse.getDX() * 0.3f + rotation.y) % 360;
            rotation.x = Math.min(85, Math.max(-85, rotation.x - Mouse.getDY() * 0.3f));
        }
        
        if (Keyboard.isKeyDown(Keyboard.KEY_G)) {
            Mouse.setGrabbed(true);
        } else if(Keyboard.isKeyDown(Keyboard.KEY_U)) {
            Mouse.setGrabbed(false);
        } else if(Keyboard.isKeyDown(Keyboard.KEY_F)) {
            rotation = new Vector3f(0, 0, 0);
            position = new Vector3f(totalPosition);
        }

        var keyUp = Keyboard.isKeyDown(Keyboard.KEY_W);
        var keyDown = Keyboard.isKeyDown(Keyboard.KEY_S);
        var keyRight = Keyboard.isKeyDown(Keyboard.KEY_D);
        var keyLeft = Keyboard.isKeyDown(Keyboard.KEY_A);
        var flyUp = Keyboard.isKeyDown(Keyboard.KEY_Q);
        var flyDown = Keyboard.isKeyDown(Keyboard.KEY_Z);
        var moveFaster = Keyboard.isKeyDown(Keyboard.KEY_LSHIFT);
        var moveSlower = Keyboard.isKeyDown(Keyboard.KEY_LCONTROL);
        var delta = getDelta();

        walkingSpeed = moveFaster ? 80 : (moveSlower ? 10 : 40);
        position.y += (flyDown ? 1 : (flyUp ? -1 : 0)) * delta;

        if(keyUp) {
            move(0, delta);
        }
        if(keyDown) {
            move(2, delta);
        }
        if(keyLeft) {
            move(1, delta);
        }
        if(keyRight) {
            move(3, delta);
        }
    }

    private static void move(int n, float delta){
        float angle = rotation.y - 90 * n;
        Vector3f newPosition = new Vector3f(position);
        newPosition.z += delta * (float) Math.cos(Math.toRadians(angle));
        newPosition.x -= (float) (Math.sin(Math.toRadians(angle)) * delta);
        position = newPosition;
    }
}
