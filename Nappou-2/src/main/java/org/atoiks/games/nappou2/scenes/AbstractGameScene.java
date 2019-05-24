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

import java.awt.Color;
import java.awt.Image;
import java.awt.event.KeyEvent;

import org.atoiks.games.framework2d.Input;
import org.atoiks.games.framework2d.IGraphics;

import org.atoiks.games.nappou2.Drifter;
import org.atoiks.games.nappou2.Difficulty;

import org.atoiks.games.nappou2.entities.*;
import org.atoiks.games.nappou2.entities.bullet.*;

import static org.atoiks.games.nappou2.App.SANS_FONT;

public abstract class AbstractGameScene extends CenteringScene {

    public static final int WIDTH = 900;
    public static final int HEIGHT = 600;
    public static final int GAME_BORDER = 750;

    public static final float DEFAULT_DX = 300f;
    public static final float DEFAULT_DY = 300f;

    public static final Color STATS_GREY = new Color(106, 106, 106);

    private final PauseOverlay pauseOverlay = new PauseOverlay();

    protected final Game game = new Game();

    private boolean disableInput = false;

    protected float playerFireTimeout;
    protected Image hpImg;
    protected Difficulty difficulty;

    protected final Drifter drift = new Drifter();

    public final int sceneId;

    private String msgSpeaker;
    private String[] msgLines;
    private Image imgMsg;
    private int xoffMsgImg;
    private int yoffMsgImg;

    protected AbstractGameScene(int id) {
        sceneId = id;
    }

    protected final void disableDamage() {
        game.player.setIgnoreHpChange(true);
    }

    protected final void enableDamage() {
        game.player.setIgnoreHpChange(false);
    }

    protected final void disableInput() {
        disableInput = true;
    }

    protected final void enableInput() {
        disableInput = false;
    }

    protected final void displayMessage(final Message msg) {
        if (msg == null) {
            this.imgMsg = null;
            this.msgSpeaker = null;
            this.msgLines = null;
            return;
        }

        if (msg.imgRes != null) {
            this.imgMsg = (Image) scene.resources().get(msg.imgRes);
        }

        if (this.imgMsg != null) {
            switch (msg.getImageHorizontalAlignment()) {
                case LEFT:
                    this.xoffMsgImg = 0;
                    break;
                case RIGHT:
                    this.xoffMsgImg = GAME_BORDER - imgMsg.getWidth(null);
                    break;
                default:
                    // Assumes center alignment, but prints out warning
                    System.err.println("Unknown horizontal alignment for msg:" + msg);
                case CENTER:
                    this.xoffMsgImg = (GAME_BORDER - imgMsg.getWidth(null)) / 2;
                    break;
            }

            switch (msg.getImageVerticalAlignment()) {
                case TOP:
                    this.yoffMsgImg = 0;
                    break;
                case BOTTOM:
                    this.yoffMsgImg = HEIGHT - imgMsg.getHeight(null);
                    break;
                case CENTER:
                    this.yoffMsgImg = (HEIGHT - imgMsg.getHeight(null)) / 2;
                    break;
                default:
                    // Assumes center alignment, ut prints out warning
                    System.err.println("Unknown vertical alignment for msg:" + msg);
                case ABOVE_DIALOGUE:
                    this.yoffMsgImg = 400 - imgMsg.getHeight(null);
                    break;
            }
        }

        this.msgSpeaker = msg.speaker == null ? null : msg.speaker + ":";
        this.msgLines = msg.lines;
    }

    @Override
    public void init() {
        hpImg = (Image) scene.resources().get("hp.png");
        game.clipGameBorder(GAME_BORDER, HEIGHT);
        pauseOverlay.attachSceneManager(this.scene);
    }

    @Override
    public void enter(String prevSceneId) {
        difficulty = (Difficulty) scene.resources().get("difficulty");

        playerFireTimeout = 0f;
        enableInput();
    }

    @Override
    public void leave() {
        scene.resources().put("level.id", sceneId);
        scene.resources().put("level.score", game.getScore());
        game.cleanup();
    }

    public void renderBackground(final IGraphics g) {
        g.setColor(Color.black);
        g.fillRect(0, 0, GAME_BORDER, HEIGHT);

        if (imgMsg != null) {
            g.drawImage(imgMsg, xoffMsgImg, yoffMsgImg);
        }
    }

    public void renderStats(final IGraphics g) {
        g.setColor(STATS_GREY);
        g.fillRect(GAME_BORDER, 0, WIDTH, HEIGHT);

        g.setColor(Color.white);
        g.setFont(SANS_FONT);
        g.drawString("HP Remaining", GAME_BORDER + 2, 16);
        g.drawString("Score", GAME_BORDER + 2, 58);

        if (hpImg != null) {
            final int hp = game.player.getHp();
            final int w = hpImg.getWidth(null);
            for (int i = 0; i < hp; ++i) {
                g.drawImage(hpImg, GAME_BORDER + 5 + i * w, 24);
            }
        }

        final String str = game.getScore() == 0 ? "0" : Integer.toString(game.getScore()) + "000";
        g.drawString(str, GAME_BORDER + 5, 74);

        if (game.player.shield.isReady()) {
            g.drawString("Shield Ready", GAME_BORDER + 30, 96);
        }

        if (msgSpeaker != null) {
            drawDialog(g, msgSpeaker, msgLines);
        }
    }

    @Override
    public final void render(final IGraphics g) {
        // setup aspect ratio
        super.render(g);

        // The bullet-curtain part
        renderBackground(g);
        game.render(g);

        // The game stats part
        g.setColor(Color.black);
        g.fillRect(GAME_BORDER, 0, WIDTH, HEIGHT);
        g.setColor(Color.white);
        g.drawLine(GAME_BORDER, 0, GAME_BORDER, HEIGHT);

        renderStats(g);

        pauseOverlay.render(g);

        g.setColor(STATS_GREY);
        drawSideBlinder(g);
    }

    @Override
    public boolean update(final float dt) {
        if (pauseOverlay.isEnabled()) {
            pauseOverlay.update(dt);
            return true;
        }

        if (Input.isKeyPressed(KeyEvent.VK_ESCAPE)) {
            pauseOverlay.enable();
            return true;
        }

        playerFireTimeout -= dt;

        // This is the magic number that makes all of this work!
        // it is 5 because the update sequence used to be split
        // between phases 0 to 4 (which adds up to 5 phases)
        final float dtDiv5 = dt / 5;
        drift.update(dtDiv5);

        procPlayerPos(dt);

        final float driftX = dtDiv5 * drift.getDx();
        final float driftY = dtDiv5 * drift.getDy();
        game.updateEnemySpawner(dtDiv5);
        game.updateEnemyPosition(dtDiv5, driftX, driftY);
        game.updateEnemyBulletPosition(dtDiv5, driftX, driftY);
        game.updatePlayerBulletPosition(dtDiv5, driftX, driftY);

        // testCollisions() returns true if a scene change is requested
        // which means we return as soon as it happens (hence || not &&)
        return testCollisions() || postUpdate(dtDiv5);
    }

    private void procPlayerPos(final float dt) {
        if (disableInput) return;

        // TODO: Simplify this
        float tmpVal = drift.getDy();
        float tmpPos = game.player.getY();
        if (Input.isKeyDown(KeyEvent.VK_DOWN))  tmpVal += DEFAULT_DY;
        if (Input.isKeyDown(KeyEvent.VK_UP))    tmpVal -= DEFAULT_DY;
        if ((tmpPos + Player.RADIUS >= HEIGHT && tmpVal > 0) || (tmpPos - Player.RADIUS <= 0 && tmpVal < 0)) {
            tmpVal = 0;
        }
        game.player.setDy(tmpVal);

        tmpVal = drift.getDx();
        tmpPos = game.player.getX();
        if (Input.isKeyDown(KeyEvent.VK_RIGHT)) tmpVal += DEFAULT_DX;
        if (Input.isKeyDown(KeyEvent.VK_LEFT))  tmpVal -= DEFAULT_DX;
        if ((tmpPos + Player.RADIUS >= GAME_BORDER && tmpVal > 0) || (tmpPos - Player.RADIUS <= 0 && tmpVal < 0)) {
            tmpVal = 0;
        }
        game.player.setDx(tmpVal);

        game.player.setSpeedScale(Input.isKeyDown(KeyEvent.VK_SHIFT) ? 0.55f : 1);

        game.player.update(dt);

        if (playerFireTimeout <= 0 && Input.isKeyDown(KeyEvent.VK_Z)) {
            final float px = game.player.getX();
            final float py = game.player.getY();
            game.addPlayerBullet(new PointBullet(px, py, 5, 0, -DEFAULT_DY * 4.5f));
            playerFireTimeout = 0.2f;  // 0.2 second cap
        }

        if (Input.isKeyPressed(KeyEvent.VK_X)) {
            game.player.shield.activate();
        }
    }

    private boolean testCollisions() {
        game.performCollisionCheck();
        if (game.player.getHp() <= 0) {
            return scene.switchToScene("TitleScene");
        }
        return false;
    }

    public static void drawDialog(IGraphics g, String speaker, String[] msg) {
        // Draw msgbox border
        g.setColor(Color.white);
        g.fillRect(12, HEIGHT - 200, GAME_BORDER - 12, HEIGHT - 12);
        // Draw grey area
        g.setColor(Color.gray);
        g.fillRect(20, HEIGHT - 192, GAME_BORDER - 20, HEIGHT - 20);
        // Draw name inside msgbox
        g.setColor(Color.white);
        g.setFont(TitleScene.OPTION_FONT);
        g.drawString(speaker, 28, HEIGHT - 162);
        // Draw message inside msgbox
        g.setFont(SANS_FONT);
        for (int i = 0; i < msg.length; ++i) {
            g.drawString(msg[i], 28, HEIGHT - 142 + i * (SANS_FONT.getSize()) + 10);
        }
        // Draw footer
        g.drawString("Press Enter to continue", GAME_BORDER - 180, HEIGHT - 26);
    }

    public abstract boolean postUpdate(float dt);
}
