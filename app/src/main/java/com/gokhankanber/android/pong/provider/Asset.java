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
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

/**
 * Provides assets: texture and sounds.
 */
public class Asset
{
    private Character characters;
    private Sound buttonSound;
    private Sound hitSound;
    private Sound hitWallSound;
    private Sound pointsSound;
    private static Asset instance;
    private com.gokhankanber.android.pong.provider.Sound sound;

    private Asset()
    {
    }

    public static Asset get()
    {
        if(instance == null)
        {
            instance = new Asset();
            instance.init();
        }

        return instance;
    }

    /**
     * Inits assets.
     * For content (logo and menu items); if texture is selected in config class, then gets characters for textures, otherwise bitmap font is used in code.
     * If wave is selected in config class, generates sound waves by Sound class, otherwise loads sound files.
     */
    public void init()
    {
        if(Config.TYPE_TEXT == Config.TextType.TEXTURE)
        {
            characters = Character.get();
        }

        switch(Config.TYPE_SOUND)
        {
            case WAVE:
                sound = com.gokhankanber.android.pong.provider.Sound.get();
                break;
            case FILE:
                load();
                break;
        }
    }

    /**
     * Load sounds.
     */
    private void load()
    {
        buttonSound = Gdx.audio.newSound(Gdx.files.internal("button.ogg"));
        hitSound = Gdx.audio.newSound(Gdx.files.internal("hit.ogg"));
        hitWallSound = Gdx.audio.newSound(Gdx.files.internal("hit_wall.ogg"));
        pointsSound = Gdx.audio.newSound(Gdx.files.internal("points.ogg"));
    }

    public void dispose()
    {
        switch(Config.TYPE_SOUND)
        {
            case WAVE:
                sound.releaseAll();
                break;
            case FILE:
                disposeAll();
                break;
        }
    }

    private void disposeAll()
    {
        buttonSound.dispose();
        hitSound.dispose();
        hitWallSound.dispose();
        pointsSound.dispose();
    }

    /**
     * Creates texture for game models: border, walls, ball, and paddles.
     * @return texture.
     */
    public Texture getTexture()
    {
        Pixmap pixmap = new Pixmap(1, 2, Pixmap.Format.RGBA8888);
        pixmap.drawPixel(0, 0, 0xffffffff);
        pixmap.drawPixel(0, 1, 0x555555ff);
        Texture texture = new Texture(pixmap);
        texture.setWrap(Texture.TextureWrap.ClampToEdge, Texture.TextureWrap.Repeat); // Repeats texture in vertical.
        pixmap.dispose();

        return texture;
    }

    /**
     * Creates logo texture: PONG.
     * @return texture.
     */
    public Texture getLogoTexture()
    {
        int[] whitePixels = {0, 0, 0, 2122219134, 0, 1717986912, 0, 2120640110, 0, 1617323622, 0, 1618896510, 0, 0, 0, 0};
        int[] grayPixels = {0, 0, 0, 0, 2122219134, 0, 1717986912, 0, 2120640110, 0, 1617323622, 0, 1618896510, 0, 0, 0};

        Pixmap pixmap = new Pixmap(32, 16, Pixmap.Format.RGBA8888);
        drawPixels(pixmap, whitePixels, 0xffffffff);
        drawPixels(pixmap, grayPixels, 0x555555ff);
        Texture texture = new Texture(pixmap);
        pixmap.dispose();

        return texture;
    }

    /**
     * Draws pixels of logo with specified pixel data.
     * @param pixmap to draw pixels.
     * @param numbers are pixel data.
     * @param color is pixel color.
     */
    private void drawPixels(Pixmap pixmap, int[] numbers, int color)
    {
        int length = numbers.length;

        for(int y = 0; y < length; y++)
        {
            for(int i = 31; i >= 0; i--)
            {
                if(((numbers[y] >> i) & 1) == 1)
                {
                    pixmap.drawPixel((31 - i), y, color);
                }
            }
        }
    }

    /**
     * Creates texture for specified text (menu items).
     * @param text for texture content.
     * @return texture.
     */
    public Texture getTexture(String text)
    {
        if(text == null)
        {
            text = "";
        }

        Pixmap pixmap = new Pixmap(text.length() * 8, 8, Pixmap.Format.RGBA8888);
        drawText(pixmap, text);
        Texture texture = new Texture(pixmap);
        pixmap.dispose();

        return texture;
    }

    /**
     * Draws pixels of characters of specified text.
     * @param pixmap to draw pixels.
     * @param text data.
     */
    private void drawText(Pixmap pixmap, String text)
    {
        int index = 0;

        for(char character : text.toCharArray())
        {
            byte[] bytes = characters.getBytes(character);

            if(bytes != null)
            {
                int length = bytes.length;

                for(int y = 0; y < length; y++)
                {
                    for(int i = 7; i >= 0; i--)
                    {
                        if(((bytes[y] >> i) & 1) == 1)
                        {
                            pixmap.drawPixel(index * 8 + (7 - i), y, 0xffffffff);
                        }
                    }
                }
            }

            index++;
        }
    }

    public void playButton()
    {
        switch(Config.TYPE_SOUND)
        {
            case WAVE:
                sound.play(com.gokhankanber.android.pong.provider.Sound.Track.BUTTON.getIndex());
                break;
            case FILE:
                buttonSound.play(1.0f);
                break;
        }
    }

    public void playHit()
    {
        switch(Config.TYPE_SOUND)
        {
            case WAVE:
                sound.play(com.gokhankanber.android.pong.provider.Sound.Track.PADDLE.getIndex());
                break;
            case FILE:
                hitSound.play(1.0f);
                break;
        }
    }

    public void playHitWall()
    {
        switch(Config.TYPE_SOUND)
        {
            case WAVE:
                sound.play(com.gokhankanber.android.pong.provider.Sound.Track.WALL.getIndex());
                break;
            case FILE:
                hitWallSound.play(1.0f);
                break;
        }
    }

    public void playPoints()
    {
        switch(Config.TYPE_SOUND)
        {
            case WAVE:
                sound.play(com.gokhankanber.android.pong.provider.Sound.Track.POINTS.getIndex());
                break;
            case FILE:
                pointsSound.play(1.0f);
                break;
        }
    }
}
