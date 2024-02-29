package shaders;

import peasy.CameraState;
import peasy.PeasyCam;
import processing.core.PApplet;
import processing.core.PVector;
import processing.event.MouseEvent;
import processing.opengl.PShader;

/**
 * @author Emilio Zottel (5AHIF)
 * @since 29.02.2024, Do.
 */
public class MirrorBall extends PApplet {

    private PShader mirrorBallShader;
    private float pitch = 0;
    private float yaw = 0;

    public static void main(String[] args) {
        PApplet.main(MirrorBall.class);
    }

    @Override
    public void settings() {
        size(800, 800, P3D);
    }

    @Override
    public void setup() {
        mirrorBallShader = loadShader("src/main/resources/shaders/mirrorball.frag");
        mirrorBallShader.set("resolution", width, height);
        mirrorBallShader.set("image", loadImage("src/main/resources/mirrorball.jpg"));
    }

    @Override
    public void draw() {
        shader(mirrorBallShader);
        rect(0, 0, width, height);
    }

    @Override
    public void mouseDragged(MouseEvent event) {
        float yawDelta = radians(pmouseX - mouseX);
        float pitchDelta = radians(pmouseY - mouseY);
        yaw += yawDelta;
        pitch = constrain(pitch + pitchDelta, -HALF_PI, HALF_PI);

        float cosPitch = cos(pitch);
        float sinPitch = sin(pitch);
        float cosYaw = cos(yaw);
        float sinYaw = sin(yaw);
        //lookAt = new PVector(cos(pitch) * cos(yaw), sin(yaw), sin(pitch) * cos(yaw));
        mirrorBallShader.set("lookAt", new PVector(cosPitch * sinYaw, -sinPitch, cosPitch * cosYaw));
    }

}
