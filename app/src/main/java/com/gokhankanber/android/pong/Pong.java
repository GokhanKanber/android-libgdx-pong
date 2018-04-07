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

package com.gokhankanber.android.pong;

import android.content.res.Resources;
import com.badlogic.gdx.Game;
import com.gokhankanber.android.pong.provider.Asset;
import com.gokhankanber.android.pong.provider.Character;
import com.gokhankanber.android.pong.view.MainMenuScreen;

/**
 * Pong main game class.
 * Loads assets.
 * Contains resources.
 */
public class Pong extends Game
{
    private Asset asset;
    private Resources resources;

    public Pong(Resources resources)
    {
        this.resources = resources;
    }

    @Override
    public void create()
    {
        asset = Asset.get();
        setScreen(new MainMenuScreen(this));
    }

    @Override
    public void dispose()
    {
        super.dispose();

        asset.dispose();
    }

    public Resources getResources()
    {
        return resources;
    }
}
