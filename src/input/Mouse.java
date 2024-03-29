package input;

import camera.Cam;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Mouse {

    public static Mouse instance = new Mouse();
    private MouseEventListener listener;
    // Timing
    private static final float SELECTION_DELAY = 0.15f;
    private float r_buttonDown_timer = 0f;
    private float l_buttonDown_timer = 0f;
    // Booleans
    private boolean r_buttonDown, l_buttonDown;
    private boolean r_justPressed, l_justPressed;
    private boolean r_justReleased, l_justReleased;
    // Screen Cordinates: (HUD/MENU)
    private final Vector2 mousePos_S;
    private final Vector2 r_clickPos_S, l_clickPos_S;
    private final Vector2 r_releasedPos_S, l_releasedPos_S;
    private final Vector2 r_dragVector_S, l_dragVector_S;
    private final Vector2 r_dragReleased_S, l_dragReleased_S;
    private final Rectangle r_highlightBox_S, l_highlightBox_S;
    private final Rectangle r_selectedBox_S, l_selectedBox_S;
    // World Cordinates: (WORLD)
    private final Vector2 mousePos_W;
    private final Vector2 r_clickPos_W, l_clickPos_W;
    private final Vector2 r_releasedPos_W, l_releasedPos_W;
    private final Vector2 r_dragVector_W, l_dragVector_W;
    private final Vector2 r_dragReleased_W, l_dragReleased_W;
    private final Rectangle r_highlightBox_W, l_highlightBox_W;
    private final Rectangle r_selectedBox_W, l_selectedBox_W;

    private final Vector2 tmpVector;

    private Mouse() {
        mousePos_S = new Vector2();
        r_clickPos_S = new Vector2();
        l_clickPos_S = new Vector2();
        r_releasedPos_S = new Vector2();
        l_releasedPos_S = new Vector2();
        r_dragVector_S = new Vector2();
        l_dragVector_S = new Vector2();
        r_dragReleased_S = new Vector2();
        l_dragReleased_S = new Vector2();
        r_highlightBox_S = new Rectangle();
        l_highlightBox_S = new Rectangle();
        r_selectedBox_S = new Rectangle();
        l_selectedBox_S = new Rectangle();

        tmpVector = new Vector2();
        mousePos_W = new Vector2();
        r_clickPos_W = new Vector2();
        l_clickPos_W = new Vector2();
        r_releasedPos_W = new Vector2();
        l_releasedPos_W = new Vector2();
        r_dragVector_W = new Vector2();
        l_dragVector_W = new Vector2();
        r_dragReleased_W = new Vector2();
        l_dragReleased_W = new Vector2();
        r_highlightBox_W = new Rectangle();
        l_highlightBox_W = new Rectangle();
        r_selectedBox_W = new Rectangle();
        l_selectedBox_W = new Rectangle();
    }

    public void setMouseEventListener(MouseEventListener listener) {
        this.listener = listener;
    }

    public void update(float dt) { // placed late in "update queues"

        if (r_buttonDown) {
            hover(Gdx.input.getX(),Gdx.input.getY());
            r_buttonDown_timer += dt;
            if (r_buttonDown_timer > SELECTION_DELAY) {
                float x_min = Math.min(r_clickPos_S.x, mousePos_S.x);
                float y_min = Math.min(r_clickPos_S.y, mousePos_S.y);
                float x_max = Math.max(r_clickPos_S.x, mousePos_S.x);
                float y_max = Math.max(r_clickPos_S.y, mousePos_S.y);
                r_highlightBox_S.set(x_min,y_min,x_max-x_min,y_max-y_min);
                listener.r_highlightBox_S(r_highlightBox_S);
                r_dragVector_S.set(mousePos_S.x - r_clickPos_S.x, mousePos_S.y - r_clickPos_S.y);
                listener.r_dragVector_S(r_dragVector_S);
                x_min = Math.min(r_clickPos_W.x, mousePos_W.x);
                y_min = Math.min(r_clickPos_W.y, mousePos_W.y);
                x_max = Math.max(r_clickPos_W.x, mousePos_W.x);
                y_max = Math.max(r_clickPos_W.y, mousePos_W.y);
                r_highlightBox_W.set(x_min,y_min,x_max-x_min,y_max-y_min);
                listener.r_highlightBox_W(r_highlightBox_W);
                r_dragVector_W.set(mousePos_W.x - r_clickPos_W.x, mousePos_W.y - r_clickPos_W.y);
                listener.r_dragVector_W(r_dragVector_W);
            }

        }
        if (l_buttonDown) {
            hover(Gdx.input.getX(),Gdx.input.getY());
            l_buttonDown_timer += dt;
            if (l_buttonDown_timer > SELECTION_DELAY) {
                float x_min = Math.min(l_clickPos_S.x, mousePos_S.x);
                float y_min = Math.min(l_clickPos_S.y, mousePos_S.y);
                float x_max = Math.max(l_clickPos_S.x, mousePos_S.x);
                float y_max = Math.max(l_clickPos_S.y, mousePos_S.y);
                l_highlightBox_S.set(x_min,y_min,x_max-x_min,y_max-y_min);
                listener.l_highlightBox_S(l_highlightBox_S);
                l_dragVector_S.set(mousePos_S.x - l_clickPos_S.x, mousePos_S.y - l_clickPos_S.y);
                listener.l_dragVector_S(l_dragVector_S);
                x_min = Math.min(l_clickPos_W.x, mousePos_W.x);
                y_min = Math.min(l_clickPos_W.y, mousePos_W.y);
                x_max = Math.max(l_clickPos_W.x, mousePos_W.x);
                y_max = Math.max(l_clickPos_W.y, mousePos_W.y);
                l_highlightBox_W.set(x_min,y_min,x_max-x_min,y_max-y_min);
                listener.l_highlightBox_W(l_highlightBox_W);
                l_dragVector_W.set(mousePos_W.x - l_clickPos_W.x, mousePos_W.y - l_clickPos_W.y);
                listener.l_dragVector_W(l_dragVector_W);
            }
        }

        r_justPressed = r_justReleased = false;
        l_justPressed = l_justReleased = false;

    }

    public void hover(int x, int y) {
        tmpVector.set(x,y);
        mousePos_S.set(x, Gdx.graphics.getHeight() - y);
        listener.hover_S(mousePos_S);
        mousePos_W.set(Cam.instance.adjustToWorldCords(tmpVector));
        listener.hover_W(mousePos_W);
    }

    public void rightClick(int x, int y) {
        System.out.println("gg");
        tmpVector.set(x,y);
        r_buttonDown = true;
        r_justPressed = true;
        r_clickPos_S.set(x, Gdx.graphics.getHeight() - y);
        listener.rightclick_S(r_clickPos_S);
        r_clickPos_W.set(mousePos_W);
        listener.rightclick_W(r_clickPos_W);
    }

    public void leftClick(int x, int y) {
        tmpVector.set(x,y);
        l_buttonDown = true;
        l_justPressed = true;
        l_clickPos_S.set(x, Gdx.graphics.getHeight() - y);
        listener.leftclick_S(l_clickPos_S);
        l_clickPos_W.set(mousePos_W);
        listener.leftclick_W(l_clickPos_W);
    }

    public void rightReleased(int x, int y) {
        tmpVector.set(x,y);
        r_buttonDown = false;
        r_justReleased = true;
        boolean select = r_buttonDown_timer > SELECTION_DELAY;
        r_buttonDown_timer = 0f;
        r_releasedPos_S.set(x, Gdx.graphics.getHeight() - y);
        r_releasedPos_W.set(mousePos_W);
        if (select) {
            r_selectedBox_S.set(r_highlightBox_S);
            r_selectedBox_W.set(r_highlightBox_W);
            listener.r_selectBox_S(r_selectedBox_S);
            listener.r_selectBox_W(r_selectedBox_W);
            r_dragReleased_S.set(r_dragVector_S);
            r_dragReleased_W.set(r_dragReleased_W);
            listener.r_dragRelease_S(r_dragReleased_S);
            listener.r_dragRelease_W(r_dragReleased_W);
        }
        r_highlightBox_S.set(0,0,0,0);
        r_highlightBox_W.set(0,0,0,0);
        r_dragVector_S.set(0,0);
        r_dragVector_W.set(0,0);
    }

    public void leftReleased(int x, int y) {
        tmpVector.set(x,y);
        l_buttonDown = false;
        l_justReleased = true;
        boolean select = l_buttonDown_timer > SELECTION_DELAY;
        l_buttonDown_timer = 0f;
        l_releasedPos_S.set(x, Gdx.graphics.getHeight() - y);
        l_releasedPos_W.set(mousePos_W);
        if (select) {
            l_selectedBox_S.set(l_highlightBox_S);
            l_selectedBox_W.set(l_highlightBox_W);
            listener.l_selectBox_S(l_selectedBox_S);
            listener.l_selectBox_W(l_selectedBox_W);
            l_dragReleased_S.set(l_dragVector_S);
            l_dragReleased_W.set(l_dragReleased_W);
            listener.l_dragRelease_S(l_dragReleased_S);
            listener.l_dragRelease_W(l_dragReleased_W);
        }
        l_highlightBox_S.set(0,0,0,0);
        l_highlightBox_W.set(0,0,0,0);
        l_dragVector_S.set(0,0);
        l_dragVector_W.set(0,0);
    }

    // TEMP

    public Vector2 getMousePos_W (){
        return mousePos_W;
    }

    public interface MouseEventListener {
        // HUD
        // SELECTIONS
        void l_highlightBox_S(Rectangle box);
        void l_highlightBox_W(Rectangle box);
        void r_highlightBox_S(Rectangle box);
        void r_highlightBox_W(Rectangle box);
        void l_selectBox_S(Rectangle box);
        void l_selectBox_W(Rectangle box);
        void r_selectBox_S(Rectangle box);
        void r_selectBox_W(Rectangle box);
        void l_dragRelease_S(Vector2 vec);
        void l_dragRelease_W(Vector2 vec);
        void r_dragRelease_S(Vector2 vec);
        void r_dragRelease_W(Vector2 vec);
        void l_dragVector_S(Vector2 vec);
        void l_dragVector_W(Vector2 vec);
        void r_dragVector_S(Vector2 vec);
        void r_dragVector_W(Vector2 vec);
        void rightclick_S(Vector2 pos);
        void rightclick_W(Vector2 pos);
        void leftclick_S(Vector2 pos);
        void leftclick_W(Vector2 pos);
        void hover_S(Vector2 pos);
        void hover_W(Vector2 pos);
    }


}
