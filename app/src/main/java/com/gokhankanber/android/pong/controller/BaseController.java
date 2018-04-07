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

package com.gokhankanber.android.pong.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gokhankanber.android.pong.Pong;
import com.gokhankanber.android.pong.provider.Asset;
import com.gokhankanber.android.pong.provider.Config;

/**
 * Base controller class for view controllers.
 * Contains game, asset, batch, and camera context.
 */
public abstract class BaseController implements IBaseController
{
    protected Pong game;
    protected Asset asset;
    protected SpriteBatch batch;
    protected OrthographicCamera camera;

    public BaseController(Pong game)
    {
        this.game = game;
    }

    @Override
    public void init()
    {
        // Get asset and create camera.
        // Set camera position to screen center.
        // Create batch.
        asset = Asset.get();
        camera = new OrthographicCamera(Config.WIDTH, Config.getHeight());
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();
        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);
    }

    @Override
    public void release()
    {
        batch.dispose();
    }

    /**
     * Clears the screen with black color.
     */
    protected void clear()
    {
        GL20 gl = Gdx.gl;
        gl.glClearColor(0, 0, 0, 1);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }
}
