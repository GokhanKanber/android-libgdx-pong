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

import com.gokhankanber.android.pong.model.IWorld;
import com.gokhankanber.android.pong.model.Paddle;
import com.gokhankanber.android.pong.provider.Config;
import java.util.Random;

/**
 * Cpu class for cpu {@link Paddle}.
 * Moves cpu paddle with the same acceleration of ball.
 * Generates a random value with min and max limits to calculate velocity of paddle when ball is moving right.
 */
public class Cpu
{
    private Paddle paddle;
    private IWorld iWorld;

    // Random velocity
    private boolean randomGenerated = false;
    private int randomVelocityValue = 1;

    public Cpu(Paddle paddle)
    {
        this.paddle = paddle;
        iWorld = paddle.getListener();
    }

    /**
     * Simulates movement of cpu paddle.
     * @param delta is time span between the current and the last frame in seconds.
     */
    public void move(float delta)
    {
        if(iWorld.getBall().getVelocity().x > 0)
        {
            // Ball is moving to right.
            if(iWorld.getBall().getVelocity().y > 0
                    && (paddle.getBounds().y + paddle.getBounds().height) / 2 <
                    (iWorld.getBall().getBounds().y + iWorld.getBall().getBounds().height) / 2)
            {
                // Ball is moving up and paddle should move up.
                paddle.setAccelerationY(iWorld.getBall().getAcceleration().y);
            }
            else if(iWorld.getBall().getVelocity().y < 0
                    && (paddle.getBounds().y + paddle.getBounds().height) / 2 >
                    (iWorld.getBall().getBounds().y + iWorld.getBall().getBounds().height) / 2)
            {
                // Ball is moving down and paddle should move down.
                paddle.setAccelerationY(iWorld.getBall().getAcceleration().y);
            }
            else
            {
                paddle.setAccelerationY(0);
            }

            paddle.setVelocityY(paddle.getAcceleration().y * delta);
            randomVelocity();
            paddle.setBoundsY(paddle.getBounds().y + paddle.getVelocity().y);

            paddle.checkWorld();

            paddle.setPositionY(paddle.getBounds().y);
        }
        else
        {
            // Ball is moving to left, reset random number generation state.
            randomGenerated = false;
        }
    }

    /**
     * Generates a random number between min and max values to simulate velocity changes for cpu paddle.
     */
    private void randomVelocity()
    {
        if(!randomGenerated)
        {
            Random random = new Random();
            randomVelocityValue = random.nextInt(Config.CPU_MAX_RANDOM_VALUE - Config.CPU_MIN_RANDOM_VALUE + 1) + Config.CPU_MIN_RANDOM_VALUE;
            randomGenerated = true;
        }

        paddle.setVelocityY(paddle.getVelocity().y * randomVelocityValue);
    }
}
