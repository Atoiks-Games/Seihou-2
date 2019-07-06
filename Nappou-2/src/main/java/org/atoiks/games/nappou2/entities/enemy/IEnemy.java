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

import org.atoiks.games.nappou2.entities.Game;
import org.atoiks.games.nappou2.entities.Drawable;
import org.atoiks.games.nappou2.entities.ICollidable;
import org.atoiks.games.nappou2.entities.IDriftEntity;

import org.atoiks.games.nappou2.graphics.Renderer;

import org.atoiks.games.nappou2.graphics.shapes.Circular;

public interface IEnemy extends Drawable, IDriftEntity, Circular {

    public boolean isDead();
    public int changeHp(int delta);

    public int getScore();

    public void attachGame(Game game);
    public Game getAssocGame();

    public Renderer getRenderer();

    public default boolean collidesWith(ICollidable col) {
        return col.collidesWith(this.getPosition(), this.getRadius());
    }
}
