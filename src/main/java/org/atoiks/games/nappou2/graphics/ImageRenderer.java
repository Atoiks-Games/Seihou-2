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

package org.atoiks.games.nappou2.graphics;

import org.atoiks.games.framework2d.IGraphics;

import org.atoiks.games.framework2d.resource.Texture;

import org.atoiks.games.nappou2.graphics.shapes.Shape;

public final class ImageRenderer implements Renderer {

    private final Texture image;

    public ImageRenderer(final Texture image) {
        this.image = image;
    }

    public void render(IGraphics g, Shape shape) {
        shape.renderTexture(g, image);
    }
}
