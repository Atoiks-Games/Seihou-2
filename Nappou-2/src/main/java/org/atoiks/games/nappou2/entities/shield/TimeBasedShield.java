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

import java.awt.Color;

import org.atoiks.games.framework2d.IGraphics;

import org.atoiks.games.nappou2.Vector2;

public abstract class TimeBasedShield implements IShield {

    private static final long serialVersionUID = 172635916L;

    protected final float reloadTime;
    protected final float timeout;

    protected boolean active = false;
    protected float time = 0;
    protected float r;
    protected Vector2 position;

    protected TimeBasedShield(final float timeout, final float reloadTime, final float r) {
        this.timeout = timeout;
        this.reloadTime = reloadTime;
        this.time = timeout + reloadTime;
        this.r = r;
    }

    @Override
    public Vector2 getPosition() {
        return position;
    }

    @Override
    public float getR() {
        return r;
    }

    @Override
    public void setPosition(Vector2 pos) {
        this.position = pos;
    }

    @Override
    public void render(IGraphics g) {
        if (active) {
            g.setColor(Color.orange);

            final float x = position.getX();
            final float y = position.getY();
            // x, y are the center of the shield
            g.drawOval(x - r, y - r, x + r, y + r);
        }
    }

    @Override
    public void update(float dt) {
        time += dt;
        if (active && time >= timeout) {
            deactivate();
        }
    }

    @Override
    public void activate() {
        if (!active && isReady()) {
            active = true;
            time = 0;
        }
    }

    @Override
    public void deactivate() {
        active = false;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public boolean isReady() {
        return time > timeout + reloadTime;
    }
}
