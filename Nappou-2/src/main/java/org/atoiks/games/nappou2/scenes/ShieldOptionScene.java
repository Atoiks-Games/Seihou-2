/**
 *  Nappou-2
 *  Copyright (C) 2017-2019  Atoiks-Games <atoiks-games@outlook.com>
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package org.atoiks.games.nappou2.scenes;

import java.awt.Font;
import java.awt.Color;

import java.awt.event.KeyEvent;

import javax.sound.sampled.Clip;

import org.atoiks.games.framework2d.Input;
import org.atoiks.games.framework2d.IGraphics;
import org.atoiks.games.framework2d.SceneManager;
import org.atoiks.games.framework2d.ResourceManager;

import org.atoiks.games.nappou2.GameConfig;

import org.atoiks.games.nappou2.entities.shield.*;

public final class ShieldOptionScene extends CenteringScene {

    private static final String[] SHIELD_MSG = {
        "Bonfire", "Firefly", "None"
    };
    private static final int[] shieldSelY = {356, 414, 498};
    private static final int OPT_HEIGHT = 37;

    private Clip bgm;
    private int shieldSel;

    private boolean skipSelection;

    private Font font30;
    private Font font80;

    @Override
    public void render(IGraphics g) {
        g.setClearColor(Color.black);
        g.clearGraphics();
        super.render(g);

        if (!skipSelection) {
            g.setColor(Color.white);
            g.setFont(this.font80);
            g.drawString("Choose Your Shield", 130, 120);
            g.setFont(this.font30);
            for (int i = 0; i < SHIELD_MSG.length; ++i) {
                g.drawString(SHIELD_MSG[i], 98, shieldSelY[i] + this.font30.getSize());
            }
            g.drawRect(90, shieldSelY[shieldSel], 94, shieldSelY[shieldSel] + OPT_HEIGHT);
        }
    }

    @Override
    public boolean update(float dt) {
        if (skipSelection) {
            return startGame();
        }

        if (Input.isKeyPressed(KeyEvent.VK_ESCAPE)) {
            return SceneManager.switchToScene("TitleScene");
        }
        if (Input.isKeyPressed(KeyEvent.VK_ENTER)) {
            return startGame();
        }

        if (Input.isKeyPressed(KeyEvent.VK_DOWN)) {
            if (++shieldSel >= shieldSelY.length) shieldSel = 0;
        }
        if (Input.isKeyPressed(KeyEvent.VK_UP)) {
            if (--shieldSel < 0) shieldSel = shieldSelY.length - 1;
        }
        return true;
    }

    private boolean startGame() {
        return SceneManager.switchToScene("GameLevelScene");
    }

    @Override
    public void init() {
        bgm = ResourceManager.get("/music/Enter_The_Void.wav");

        final Font fnt = ResourceManager.get("/Logisoso.ttf");
        this.font30 = fnt.deriveFont(30f);
        this.font80 = fnt.deriveFont(80f);
    }

    @Override
    public void enter(String previousSceneId) {
        final GameConfig cfg = ResourceManager.get("./game.cfg");
        if ((skipSelection = cfg.challengeMode)) {
            // Challenge mode does not use NullShield
            // Also, line above is intentional assignment, not test equality
            shieldSel = shieldSelY.length - 1;
            return;
        }

        if (cfg.bgm) {
            bgm.start();
            bgm.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    @Override
    public void leave() {
        SceneManager.resources().put("shield", getShieldFromOption());

        if (bgm != null) bgm.stop();
    }

    private IShield getShieldFromOption() {
        switch (shieldSel) {
            default:
            case 0: return new FixedTimeShield(3.5f, 2, 50);
            case 1: return new TrackingTimeShield(2f, 3, 35);
            case 2: return new NullShield();
        }
    }
}
