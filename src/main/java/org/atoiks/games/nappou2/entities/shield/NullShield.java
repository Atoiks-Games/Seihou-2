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

package org.atoiks.games.nappou2.entities.shield;

import java.io.ObjectStreamException;

import org.atoiks.games.nappou2.Vector2;

import org.atoiks.games.nappou2.graphics.Renderer;
import org.atoiks.games.nappou2.graphics.NullRenderer;

import org.atoiks.games.nappou2.entities.Collidable;

public final class NullShield implements ShieldEntity {

    private static final long serialVersionUID = -6024720306180805901L;

    public static final NullShield INSTANCE = new NullShield();

    private NullShield() {
    }

    @Override
    public Renderer getRenderer() {
        return NullRenderer.INSTANCE;
    }

    @Override
    public void update(float dt) {
        // Do nothing
    }

    @Override
    public Vector2 getPosition() {
        return null;
    }

    @Override
    public float getRadius() {
        return 0;
    }

    @Override
    public void setPosition(Vector2 pos) {
        // Do nothing
    }

    @Override
    public void activate() {
        // Do nothing
    }

    @Override
    public void deactivate() {
        // Do nothing
    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public boolean isReady() {
        return false;
    }

    @Override
    public NullShield copy() {
        // Yes, returning this is not copying
        // but then NullShield is state free
        return this;
    }

    @Override
    public boolean collidesWith(Collidable col) {
        // It is impossible for this shield to collide with anything.
        return false;
    }

    // Exists to make sure singleton works
    private Object readResolve() throws ObjectStreamException {
        return INSTANCE;
    }
}
