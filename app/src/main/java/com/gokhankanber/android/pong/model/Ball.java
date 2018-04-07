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

package com.gokhankanber.android.pong.model;

import com.badlogic.gdx.math.Rectangle;
import com.gokhankanber.android.pong.provider.Config;

public class Ball extends Model
{
    private final int negativeDirection = -1;

    public Ball(float x, float y, float width, float height)
    {
        super(x, y, width, height);

        setAcceleration(-Config.BALL_ACCELERATION);
    }

    @Override
    public void update(float delta)
    {
        // Set state time, velocity, and bounds.
        stateTime += delta;
        float value = increaseVelocityValue();
        velocity.x = acceleration.x * delta * value;
        velocity.y = acceleration.y * delta * value;

        bounds.x += velocity.x;
        bounds.y += velocity.y;

        // Check collision with bounds.
        checkCollision();

        // Set position.
        position.x = bounds.x;
        position.y = bounds.y;

        // Checks ball position for score.
        checkWorld();
    }

    @Override
    protected void checkCollision()
    {
        // Check collision with paddle 1 and paddle 2.
        checkCollisionX(iWorld.getPaddle1().bounds);
        checkCollisionX(iWorld.getPaddle2().bounds);

        // Check collision with walls.
        for(Block wall : iWorld.getWalls())
        {
            checkCollisionY(wall.bounds);
        }
    }

    /**
     * Resets ball position and state after new score.
     * @param x coordinate of ball.
     * @param y coordinate of ball.
     */
    public void reset(float x, float y)
    {
        setPosition(x, y);
        resetStateTime();
    }

    public void setAcceleration(float accelerationValue)
    {
        acceleration.x = accelerationValue;
        acceleration.y = -Math.abs(accelerationValue);
    }

    /**
     * Checks collision with paddles.
     * @param rectangle is paddle model.
     */
    private void checkCollisionX(Rectangle rectangle)
    {
        if(bounds.overlaps(rectangle))
        {
            if(velocity.x < 0 && bounds.x > rectangle.x)
            {
                // Collision with left paddle. Change ball direction to right.
                bounds.x = rectangle.x + rectangle.width;
                acceleration.x *= negativeDirection;
            }
            else if(velocity.x > 0 && bounds.x < rectangle.x + rectangle.width)
            {
                // Collision with right paddle. Change ball direction to left.
                bounds.x = rectangle.x - bounds.width;
                acceleration.x *= negativeDirection;
            }
            else
            {
                // Top or bottom side of paddle model.
                if((bounds.y > rectangle.y && velocity.y < 0) || (bounds.y < rectangle.y && velocity.y > 0))
                {
                    acceleration.y *= negativeDirection;
                }
            }

            iWorld.playHitSound();
        }
    }

    /**
     * Checks collision with walls.
     * @param rectangle is a wall block.
     */
    private void checkCollisionY(Rectangle rectangle)
    {
        if(bounds.overlaps(rectangle))
        {
            if(velocity.y < 0)
            {
                // Collision with down walls.
                bounds.y = rectangle.y + rectangle.height;
            }
            else if(velocity.y > 0)
            {
                // Collision with up walls.
                bounds.y = rectangle.y - bounds.height;
            }

            acceleration.y *= negativeDirection;
            iWorld.playHitWallSound();
        }
    }

    private void checkWorld()
    {
        if(position.x + bounds.width < 0)
        {
            // Paddle 2 scores new point.
            iWorld.points(2);
        }
        else if(position.x > Config.WIDTH)
        {
            // Paddle 1 scores new point.
            iWorld.points(1);
        }
    }

    /**
     * Increases velocity of ball.
     * Calculates new value with the state time.
     * Ratio is an user defined value to simulate game difficulty.
     * @return value to set new velocity.
     */
    private float increaseVelocityValue()
    {
        return (1 + stateTime * Config.BALL_VELOCITY_RATIO);
    }
}
