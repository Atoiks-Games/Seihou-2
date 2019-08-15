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

package org.atoiks.games.nappou2.levels.level1.easy;

import javax.sound.sampled.Clip;

import org.atoiks.games.framework2d.ResourceManager;

import org.atoiks.games.nappou2.GameConfig;

import org.atoiks.games.nappou2.levels.LevelState;
import org.atoiks.games.nappou2.levels.LevelContext;

import org.atoiks.games.nappou2.entities.Game;

import static org.atoiks.games.nappou2.Utils.squirts;
import static org.atoiks.games.nappou2.Utils.streamBeam;

public class EasyWave6 implements LevelState {

    private static final long serialVersionUID = -4818209983669418715L;

    private transient int cycles;

    private transient Clip bgm;

    @Override
    public void enter(final LevelContext ctx) {
        this.cycles = 0;

        this.bgm = ResourceManager.get("/music/Level_One.wav");
        if (ResourceManager.<GameConfig>get("./game.cfg").bgm) {
            bgm.start();
        }
    }

    @Override
    public void exit() {
        bgm.stop();
    }

    @Override
    public void updateLevel(final LevelContext ctx, final float dt) {
        final Game game = ctx.getGame();

        ++cycles;
        switch (cycles) {
            case 40:
                game.addEnemy(squirts(10, 375, -10, 20));
                break;
            case 50:
            case 100:
            case 150:
            case 200:
                game.addEnemy(streamBeam(1, 10, 610, 8, true));
                game.addEnemy(streamBeam(1, 740, 610, 8, true));
                break;
        }
        if (cycles > 200 && game.noMoreEnemies()) {
            ctx.setState(new EasyWave7());
            return;
        }
    }
}
