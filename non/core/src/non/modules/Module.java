package non.modules;

public abstract class Module {
    public void update(float dt) {}
    public void updateAfter(float dt) {}
    public void resize(float w, float h) {}
    public void keyPressed(int key) {}
    public void keyTyped(char key) {}
    public void dispose() {}
}