package shaders;

import processing.core.PApplet;
import processing.core.PVector;
import processing.event.KeyEvent;
import processing.event.MouseEvent;
import processing.opengl.PShader;

/**
 * @author Emilio Zottel (5AHIF)
 * @since 07.02.2024, Mi.
 */
public class Mandelbrot extends PApplet {

    private final PVector bottomLeft = new PVector(-2, -2);
    private final PVector topRight = new PVector(2, 2);
    private int iterationCount = 100;
    private PShader mandelbrotShader;

    public static void main(String[] args) {
        PApplet.main(Mandelbrot.class);
    }

    @Override
    public void settings() {
        size(800, 800, P3D);
    }

    @Override
    public void setup() {
        mandelbrotShader = loadShader("src/main/resources/shaders/mandelbrot.frag");
        mandelbrotShader.set("resolution", width, height);
        mandelbrotShader.set("iterationCount", iterationCount);
        updateBoundaryUniforms();
        textAlign(LEFT, TOP);
        background(0);
    }

    @Override
    public void draw() {
        shader(mandelbrotShader);
        rect(0, 0, width, height);
    }

    /**
     * Handles the mouse wheel event to zoom in and out
     * <p>
     * The x value of the zoom point is using the range <code>0</code> to <code>height</code> instead of
     * <code>0</code> to <code>width</code> because in the fragment shader we divide by height
     * to get the correct aspect ratio.
     * @param event the mouse event
     */
    @Override
    public void mouseWheel(MouseEvent event) {
        var zoomPoint = new PVector(
                map(mouseX, 0, height, bottomLeft.x, topRight.x),
                map(mouseY, height, 0, bottomLeft.y, topRight.y)
        );

        float zoomDelta = event.getCount() * -0.1f;
        bottomLeft.lerp(zoomPoint, zoomDelta);
        topRight.lerp(zoomPoint, zoomDelta);
        updateBoundaryUniforms();
    }

    @Override
    protected void handleKeyEvent(KeyEvent event) {
        super.handleKeyEvent(event);

        if (key == '+') {
            updateIterationCountUniform(10);
        } else if (key == '-') {
            updateIterationCountUniform(-10);
        }
    }

    private void updateBoundaryUniforms() {
        mandelbrotShader.set("bottomLeft", bottomLeft.x, bottomLeft.y);
        mandelbrotShader.set("topRight", topRight.x, topRight.y);
    }

    private void updateIterationCountUniform(int delta) {
        iterationCount = max(1, iterationCount + delta);
        mandelbrotShader.set("iterationCount", iterationCount);
    }

}
