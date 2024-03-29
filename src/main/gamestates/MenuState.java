package main.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import input.GameKeys;
import input.adapters.MenuControl;
import graphics.Assets;
import graphics.DrwHandler;
import main.gamestates.abstr.GameState;

public class MenuState extends GameState {


    public MenuState(GameStateManager gsm) {
        super(gsm);
    }




    @Override
    public void init() {
        loadAssets();
        control = new MenuControl();
        Gdx.input.setInputProcessor(control);
    }

    @Override
    public void loadAssets() {
        Assets.instance.init(new AssetManager());
    }

    @Override
    public void update(float dt) {
        super.update(dt); // ha denne etter du oppdaterer annet
    }

    @Override
    public void draw() {
        DrwHandler.instance.draw();
    }

    @Override
    public void handleInput() {
        if (GameKeys.isPressed(GameKeys.ESCAPE)) Gdx.app.exit();
        if (GameKeys.isPressed(GameKeys.ENTER)) gsm.setState(GameStateManager.PLAY);
    }

    @Override
    public void dispose() {
        DrwHandler.instance.clear();
        Assets.instance.dispose();
    }

}
