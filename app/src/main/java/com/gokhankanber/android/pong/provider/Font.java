/*
 * Copyright 2018 Gökhan Kanber
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gokhankanber.android.pong.provider;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

/**
 * Provides a font builder.
 */
public class Font
{
    public static final String FONT = "font";
    private final String unicodeCharacters = "\u0130\u0131";
    private FreeTypeFontGenerator generator;
    private FreeTypeFontParameter parameter;
    private BitmapFont bitmapFont;

    public Font create(String font)
    {
        generator = new FreeTypeFontGenerator(Gdx.files.internal(font + ".ttf"));
        parameter = new FreeTypeFontParameter();
        parameter.characters = FreeTypeFontGenerator.DEFAULT_CHARS + unicodeCharacters;

        return this;
    }

    public Font size(int size)
    {
        parameter.size = size;

        return this;
    }

    public Font border(float borderWidth, Color borderColor)
    {
        parameter.borderWidth = borderWidth;
        parameter.borderColor = borderColor;

        return this;
    }

    public Font shadow(int shadowOffsetX, int shadowOffsetY, Color shadowColor)
    {
        parameter.shadowOffsetX = shadowOffsetX;
        parameter.shadowOffsetY = shadowOffsetY;
        parameter.shadowColor = shadowColor;

        return this;
    }

    public BitmapFont build()
    {
        bitmapFont = generator.generateFont(parameter);
        generator.dispose();

        return bitmapFont;
    }

    /*
     * Default values for FreeTypeFontParameter
     *
     * int size = 16;                                           // The size in pixels
     * Color color = Color.WHITE;                               // Foreground color (required for non-black borders)
     * float borderWidth = 0;                                   // Border width in pixels, 0 to disable
     * Color borderColor = Color.BLACK;                         // Border color; only used if borderWidth > 0
     * boolean borderStraight = false;                          // true for straight (mitered), false for rounded borders
     * int shadowOffsetX = 0;                                   // Offset of text shadow on X axis in pixels, 0 to disable
     * int shadowOffsetY = 0;                                   // Offset of text shadow on Y axis in pixels, 0 to disable
     * Color shadowColor = new Color(0, 0, 0, 0.75f);           // Shadow color; only used if shadowOffset > 0
     * String characters = FreeTypeFontGenerator.DEFAULT_CHARS; // The characters the font should contain
     * boolean kerning = true;                                  // Whether the font should include kerning
     * PixmapPacker packer = null;                              // The optional PixmapPacker to use
     * boolean flip = false;                                    // Whether to flip the font vertically
     * boolean genMipMaps = false;                              // Whether or not to generate mip maps for the resulting texture
     * TextureFilter minFilter = TextureFilter.Nearest;         // Minification filter
     * TextureFilter magFilter = TextureFilter.Nearest;         // Magnification filter
     */
}
