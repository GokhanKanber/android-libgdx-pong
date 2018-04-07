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

package com.gokhankanber.android.pong.controller.main;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.gokhankanber.android.pong.controller.BaseInputController;

/**
 * Input controller class for {@link MainMenuController} class.
 * Uses touchUp method for click events.
 */
public class MainInputController extends BaseInputController
{
    private InputListener inputListener;

    public MainInputController(OrthographicCamera camera, InputListener inputListener)
    {
        super(camera);

        this.inputListener = inputListener;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button)
    {
        camera.unproject(touchPoint1.set(screenX, screenY, 0));
        inputListener.check(touchPoint1.x, touchPoint1.y);

        return false;
    }

    public interface InputListener
    {
        void check(float x, float y);
    }
}
