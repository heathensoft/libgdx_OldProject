package ui.hud.hud2;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import ui.UI;

public class GameUI extends UI {

    public static boolean mouseOnHud;

    private HUD hud;
    private UnitSelection unitManager;

    public GameUI() {
        // pass in world to accsess all antities and tilemap.
        unitManager = new UnitSelection(this);
        hud = new HUD(this);
    }

    public HUD getHud() {
        return hud;
    }

    public UnitSelection getUnitManager() {
        return unitManager;
    }

    @Override
    public void hover_S(Vector2 pos) {
        mouseOnHud = hud.mouseOnHud(pos);
        if (mouseOnHud) hud.hover(pos);
    }

    @Override
    public void hover_W(Vector2 pos) {
        if (!mouseOnHud) unitManager.hover(pos);
    }

    @Override
    public void leftclick_S(Vector2 pos) {
        if (mouseOnHud) hud.leftClick(pos);
    }

    @Override
    public void leftclick_W(Vector2 pos) {
        if (!mouseOnHud) unitManager.leftClick(pos);
    }

    @Override
    public void rightclick_S(Vector2 pos) {

    }

    @Override
    public void rightclick_W(Vector2 pos) {
        if (!mouseOnHud) unitManager.rightClick(pos);
    }

    @Override
    public void l_selectBox_W(Rectangle box) {
        unitManager.selectBox(box);
    }
}
