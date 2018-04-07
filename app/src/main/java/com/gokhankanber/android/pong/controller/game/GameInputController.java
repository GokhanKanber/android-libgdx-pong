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

package com.gokhankanber.android.pong.controller.game;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.gokhankanber.android.pong.controller.BaseInputController;

/**
 * Input controller class for {@link GameController} class.
 * Uses touchDown and touchDragged methods for moving paddle.
 * Uses touchUp method for click events.
 */
public class GameInputController extends BaseInputController
{
    private InputListener inputListener;
    private float startY;
    private float amount;

    public GameInputController(OrthographicCamera camera, InputListener inputListener)
    {
        super(camera);

        this.inputListener = inputListener;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {
        camera.unproject(touchPoint1.set(screenX, screenY, 0)); // Translate screen coordinate to camera coordinate of world.
        startY = touchPoint1.y; // Init start y point before touchDragged method.

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button)
    {
        camera.unproject(touchPoint1.set(screenX, screenY, 0));
        inputListener.check(touchPoint1.x, touchPoint1.y); // Check click events for game menu items.

        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer)
    {
        // Track for touch with pointer zero.
        // Call move method with the amount of change in y coordinate.
        if(pointer == 0)
        {
            camera.unproject(touchPoint1.set(screenX, screenY, 0));
            amount = touchPoint1.y - startY;
            startY = touchPoint1.y;
            inputListener.move(amount);
        }

        return false;
    }

    @Override
    public boolean keyDown(int keycode)
    {
        // Listen for back button
        if(keycode == Keys.BACK)
        {
            inputListener.back();

            return true;
        }

        return false;
    }

    public interface InputListener
    {
        void back();
        void move(float amount);
        void check(float x, float y);
    }
}
