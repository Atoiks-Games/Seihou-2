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

package org.atoiks.games.nappou2.levels.level1.insane;

import javax.sound.sampled.Clip;

import org.atoiks.games.framework2d.ResourceManager;

import org.atoiks.games.nappou2.Drifter;
import org.atoiks.games.nappou2.Difficulty;

import org.atoiks.games.nappou2.levels.LevelState;
import org.atoiks.games.nappou2.levels.LevelContext;
import org.atoiks.games.nappou2.levels.SaveScoreState;

import org.atoiks.games.nappou2.levels.level1.AbstractBossWave;

import org.atoiks.games.nappou2.entities.Game;

import org.atoiks.games.nappou2.entities.enemy.*;

import static org.atoiks.games.nappou2.levels.level1.Data.*;

public class InsaneBossWave extends AbstractBossWave {

    private static final long serialVersionUID = -4184588301058618848L;

    public InsaneBossWave() {
        super(new SaveScoreState(0, Difficulty.INSANE), 250, 250);
    }

    @Override
    public void enter(final LevelContext ctx) {
        super.enter(ctx);

        final Game game = ctx.getGame();
        game.addEnemy(new Level1Insane(300, 375, -10, 20));

        final Drifter drift = game.drifter;
        drift.accelY = -20;
        drift.accelX = 20;
        drift.clampDx(0, 200);
    }
}
