import processing.core.PApplet;
import processing.core.PShape;
import queasycam.QueasyCam;

public class FlameThrower extends PApplet {
    final int WIDTH = 1500;
    final int HEIGHT = 1000;
    QueasyCam cam;
    Ground ground;
    PShape tree;
    PShape flameThrower;
    FireParticleSystem ps;

    @Override
    public void settings() {
        size(WIDTH, HEIGHT, P3D);
    }

    @Override
    public void setup() {
        surface.setTitle("Processing");
        noStroke();
        cam = new QueasyCam(this);
        cam.sensitivity = 1f;
        cam.speed = 2f;
        ground = new Ground(this,
                new Vector3D(0, 0, 0), new Vector3D(0, 0, 1), new Vector3D(1, 0, 0),
                2048, 2048,
                loadImage("grass.jpg"));
        tree = loadShape("BirchTree_1.obj");
        tree.rotate(PI, 0, 0, 1);
        tree.scale(60);
        flameThrower = loadShape("LongPistol.obj");
        flameThrower.scale(10);
        flameThrower.rotate(PI, 0, 0, 1);
        flameThrower.setStroke(color(255));
        flameThrower.setFill(color(128, 0, 0));
        ps = new FireParticleSystem(this,
                new Vector3D(300, 8, 125), new Vector3D(0, 0, -1),
                100, 200, 100000);
    }

    @Override
    public void draw() {
        // background
        background(255);
        // ground and tree
        pushMatrix();
        translate(300, 100, 0);
        ground.render();
        shape(tree);
        popMatrix();
        // flame thrower
        pushMatrix();
        translate(300, 10, 150);
        shape(flameThrower);
        popMatrix();

        int frameStart = millis();
        // physics
        ps.physics(0.015f);
        int physicsEnd = millis();
        // rendering
        ps.render();
        int frameEnd = millis();
        // text overlay
        surface.setTitle("Processing"
                + " FPS: " + round(frameRate)
                + " Phy: " + round(physicsEnd - frameStart) + "ms"
                + " Ren: " + round(frameEnd - physicsEnd) + "ms"
                + " #par: " + ps.particles.size()
        );
    }

    static public void main(String[] passedArgs) {
        String[] appletArgs = new String[]{"FlameThrower"};
        if (passedArgs != null) {
            PApplet.main(concat(appletArgs, passedArgs));
        } else {
            PApplet.main(appletArgs);
        }
    }
}