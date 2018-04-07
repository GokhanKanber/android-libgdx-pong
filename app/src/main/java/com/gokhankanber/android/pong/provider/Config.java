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

public class Config
{
    public enum TextType
    {
        TEXTURE,
        FONT
    }

    public enum SoundType
    {
        WAVE,
        FILE
    }

    public static final Config.TextType TYPE_TEXT = TextType.TEXTURE;
    public static final Config.SoundType TYPE_SOUND = SoundType.WAVE;
    public static final float WIDTH = 400;
    public static final float BLOCK = 8;
    public static final int MAX_POINTS = 10;
    public static final float BALL_ACCELERATION = 100.0f;
    public static final float BALL_VELOCITY_RATIO = 0.1f;
    public static final int CPU_MIN_RANDOM_VALUE = 3;
    public static final int CPU_MAX_RANDOM_VALUE = 5;

    public static float getHeight()
    {
        float ratio = (float) Gdx.graphics.getHeight() / Gdx.graphics.getWidth();

        return WIDTH * ratio;
    }
}
