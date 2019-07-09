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

package org.atoiks.games.nappou2.entities;

import org.atoiks.games.nappou2.Vector2;

import org.atoiks.games.nappou2.graphics.shapes.Circular;

public interface Collidable {

    public boolean isOutOfScreen(int width, int height);
    public boolean collidesWith(float x, float y, float r);

    public default boolean collidesWith(final Circular circle) {
        final Vector2 pos = circle.getPosition();
        return collidesWith(pos.getX(), pos.getY(), circle.getRadius());
    }
}
