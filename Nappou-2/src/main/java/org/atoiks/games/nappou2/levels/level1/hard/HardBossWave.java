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

package org.atoiks.games.nappou2.levels.level1.hard;

import javax.sound.sampled.Clip;

import org.atoiks.games.framework2d.ResourceManager;

import org.atoiks.games.nappou2.Drifter;
import org.atoiks.games.nappou2.Difficulty;

import org.atoiks.games.nappou2.levels.ILevelState;
import org.atoiks.games.nappou2.levels.ILevelContext;
import org.atoiks.games.nappou2.levels.SaveScoreState;

import org.atoiks.games.nappou2.entities.Game;

import org.atoiks.games.nappou2.entities.enemy.*;

import org.atoiks.games.nappou2.levels.level1.PostbossDialog;

import static org.atoiks.games.nappou2.levels.level1.Data.*;

import static org.atoiks.games.nappou2.scenes.GameLevelScene.HEIGHT;
import static org.atoiks.games.nappou2.scenes.GameLevelScene.GAME_BORDER;

public class HardBossWave implements ILevelState {

    private static final long serialVersionUID = -963938928723781698L;

    private static final SaveScoreState EXIT_STATE = new SaveScoreState(0, Difficulty.HARD);

    private transient int cycles;
    private transient int phase;

    @Override
    public void enter(final ILevelContext ctx) {
        ctx.enableDamage();
        ctx.shouldSkipPlayerUpdate(false);
    }

    @Override
    public void updateLevel(final ILevelContext ctx, final float dt) {
        final Game game = ctx.getGame();
        final Drifter drift = game.drifter;

        if (cycles++ == 0) {
            game.addEnemy(new Level1Hard(300, 375, -10, 20));
            drift.accelY = -20;
            drift.accelX = 20;
            drift.clampDx(0, 200);
            return;
        }

        if (cycles % 4000 == 0) {
            switch (++phase) {
                case 0:
                    drift.accelY = -20;
                    drift.accelX = 20;
                    drift.clampDx(0, 200);
                    break;
                case 1:
                    drift.accelX = -20;
                    drift.accelY = 20;
                    drift.clampDy(0, 200);
                    break;
                case 2:
                    drift.accelY = -20;
                    drift.clampDx(-200, 0);
                    break;
                case 3:
                    drift.accelX = 20;
                    drift.clampDy(-200, 0);
                    break;
            }
        }
        if (cycles > 40 && game.noMoreEnemies()) {
            ctx.setState(new PostbossDialog(EXIT_STATE));
            return;
        }
    }
}
