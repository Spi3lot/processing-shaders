package shaders;

import processing.core.PApplet;
import processing.core.PShape;
import processing.opengl.PShader;

/**
 * @author Emilio Zottel (5AHIF)
 * @see <a href="https://web.archive.org/web/20210419055927/https://www.processing.org/tutorials/pshader/">Archived PShader tutorial</a>
 * @see <a href="https://github.com/codeanticode/pshader-tutorials">GitHub</a>
 * @since 07.02.2024, Mi.
 */
public class CubeExample extends PApplet {

    private static final float WEIGHT = 20;
    private float angle;
    private PShape cube;
    private PShader lineShader;

    public static void main(String[] args) {
        PApplet.main(CubeExample.class);
    }

    @Override
    public void settings() {
        size(640, 360, P3D);
    }

    @Override
    public void setup() {
        lineShader = loadShader("src/main/resources/shaders/line.frag", "src/main/resources/shaders/line.vert");
        lineShader.set("weight", WEIGHT);

        cube = createShape(BOX, 150);
        cube.setFill(false);
        cube.setStroke(color(255));
        cube.setStrokeWeight(WEIGHT);

        hint(DISABLE_DEPTH_MASK);
    }

    @Override
    public void draw() {
        background(0);

        translate(width / 2f, height / 2f);
        rotateX(angle);
        rotateY(angle);

        shader(lineShader, LINES);
        shape(cube);

        angle += 0.01f;
    }


}