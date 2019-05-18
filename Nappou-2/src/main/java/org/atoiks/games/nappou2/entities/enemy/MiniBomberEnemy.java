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

package org.atoiks.games.nappou2.entities.enemy;

import org.atoiks.games.nappou2.entities.bullet.PointBullet;

public final class MiniBomberEnemy extends AbstractMiniBomber {

    private static final long serialVersionUID = 5619264522L;

    public MiniBomberEnemy(int hp, float x, float y, float r, int direction, float speed) {
        super(hp, x, y, r, direction, speed);
    }

    @Override
    protected void customFireAction(float dt) {
        game.addEnemyBullet(new PointBullet(x, y, 2, 0, 1000));
    }

    @Override
    public int getScore() {
        return 1;
    }
}
