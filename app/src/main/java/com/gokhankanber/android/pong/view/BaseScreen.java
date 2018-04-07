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

package com.gokhankanber.android.pong.view;

import com.badlogic.gdx.Screen;
import com.gokhankanber.android.pong.Pong;
import com.gokhankanber.android.pong.controller.BaseController;

/**
 * Base view class.
 * Contains game and base controller.
 */
public abstract class BaseScreen implements Screen
{
    protected Pong game;
    protected BaseController controller;

    public BaseScreen(Pong game)
    {
        this.game = game;
    }

    @Override
    public void show()
    {
        controller.init();
    }

    @Override
    public void render(float delta)
    {
        controller.update(delta);
        controller.draw(delta);
    }

    @Override
    public void resize(int width, int height)
    {
    }

    @Override
    public void pause()
    {
    }

    @Override
    public void resume()
    {
    }

    @Override
    public void hide()
    {
        controller.release();
    }

    @Override
    public void dispose()
    {
    }
}
