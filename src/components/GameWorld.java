package components;

import camera.Cam;
import camera.FocusPoint;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import components.effects.Effect;
import components.entity.Entity;
import components.entity.Knight;
import components.map.TileMap;
import graphics.Assets;
import com.badlogic.gdx.utils.Disposable;
import components.effects.Effects;
import components.weather.Weather;
import input.GameKeys;
import input.Mouse;
import main.Settings;
import graphics.DrwHandler;
import components.map.tilecoding.process.Setup;
import ui.hud.hud2.GameUI;
import utils.Screenshot;


public class GameWorld implements Disposable {

    private Array<Knight> knights;
    private Biome biome;
    public TileMap map;
    private Weather weather;
    public GameUI gameUI;

    public static Rectangle bounds;

    public void newArea() {

        Mouse.instance.setMouseEventListener(gameUI = new GameUI());
        DrwHandler.instance.clear();
        if (weather!=null) weather.dispose();
        if (knights!=null) {
            for (Knight knight : knights) {
                knight.dispose();
            }
            knights.clear();
        }
        DrwHandler.instance.set(1,true);
        DrwHandler.instance.set(2,true);
        DrwHandler.instance.set(3,true);
        DrwHandler.instance.set(4,true);
        DrwHandler.instance.set(5,true);
        DrwHandler.instance.set(6,true);
        DrwHandler.instance.set(7,true);
        DrwHandler.instance.sort(5,true);
        biome = new Biome(Biome.BIOME.TUNDRA);
        Assets.instance.loadBiome(biome);
        map = Setup.init(800,800, biome);
        bounds = map.boundary;
        weather = new Weather();
        Cam.instance.reset();
        FocusPoint.position.set(map.getCentre());
        knights = new Array<>();
        Effects.instance.newInstance();

        /*
        for (int i = 0; i < 50; i++) {
            Knight knight;

            if (i == 41) {
                knight = new Knight(Entity.Type.KNIGHT, Entity.Disposition.NEUTRAL,this, FocusPoint.position.x + i*32*Settings.SCALE,FocusPoint.position.y);
            }
            else if (i == 40) {
                knight = new Knight(Entity.Type.KNIGHT, Entity.Disposition.HOSTILE,this, FocusPoint.position.x + i*32*Settings.SCALE,FocusPoint.position.y);
            }

            else {knight = new Knight(Entity.Type.KNIGHT, Entity.Disposition.FRIENDLY,this, FocusPoint.position.x + i*32*Settings.SCALE,FocusPoint.position.y);}
            knights.add(knight);
        }

         */

        Cam.instance.lockOnTarget(FocusPoint.position);
        Cam.instance.SetZoom(Settings.SCALE);
    }


    public void update(float dt) {

        for (Knight knight : knights) {
            knight.update(dt);
        }

        if (GameKeys.isPressed(GameKeys.CONTROL)) {
            Screenshot.snap();
        }
        map.rendercheck(); // on pause tiles go black
        weather.update(dt);
        Effects.instance.update(dt);
    }

    // TEMP

    public void spawnKnight() {
        Knight knight = new Knight(Entity.Type.KNIGHT, Entity.Disposition.FRIENDLY,this, Mouse.instance.getMousePos_W().x ,Mouse.instance.getMousePos_W().y);
        knights.add(knight);
    }

    public void explodeSelectedKnights() {
        for (Knight knight : knights) {
            if (knight.selected) {
                Effects.instance.create(Effect.Type.KNIGHT_EXPLODE,knight.getPosition().x,knight.getPosition().y + 32);
                System.out.println(knights.size);
                System.out.println(gameUI.getUnitManager().getSelectedUnits().size);
                knight.deSelected();
                knight.deHovered();
                gameUI.getUnitManager().removeUnit2(knight);
                knight.dispose();
                knights.removeValue(knight, true);

                System.out.println(gameUI.getUnitManager().getSelectedUnits().size);
                System.out.println(knights.size);
            }
        }
    }

    @Override
    public void dispose() {
        weather.dispose();
        Effects.instance.dispose();
        DrwHandler.instance.clear();
        for (Knight knight : knights) {
            knight.dispose();
        }
    }
}
