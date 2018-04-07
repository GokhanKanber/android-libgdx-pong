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
import com.badlogic.gdx.math.Vector2;

/**
 * Base model class.
 * Contains bounds, state time, position, acceleration, velocity.
 * Contains world interface.
 */
public class Model
{
    protected Rectangle bounds;
    protected float stateTime;
    protected Vector2 position;
    protected Vector2 acceleration;
    protected Vector2 velocity;
    protected IWorld iWorld;

    public Model(float x, float y, float width, float height)
    {
        bounds = new Rectangle(x, y, width, height);
        position = new Vector2(x, y);
        acceleration = new Vector2();
        velocity = new Vector2();
    }

    public IWorld getListener()
    {
        return iWorld;
    }

    public void setListener(IWorld iWorld)
    {
        this.iWorld = iWorld;
    }

    public float getX()
    {
        return position.x;
    }

    public float getY()
    {
        return position.y;
    }

    public void setPositionY(float y)
    {
        position.y = y;
    }

    public void setPosition(float x, float y)
    {
        bounds.x = x;
        position.x = x;
        bounds.y = y;
        position.y = y;
    }

    public Rectangle getBounds()
    {
        return bounds;
    }

    public void setBoundsY(float y)
    {
        bounds.y = y;
    }

    public float getWidth()
    {
        return bounds.width;
    }

    public float getHeight()
    {
        return bounds.height;
    }

    public Vector2 getAcceleration()
    {
        return acceleration;
    }

    public void setAccelerationY(float y)
    {
        acceleration.y = y;
    }

    public Vector2 getVelocity()
    {
        return velocity;
    }

    public void setVelocityY(float y)
    {
        velocity.y = y;
    }

    public void resetStateTime()
    {
        stateTime = 0;
    }

    public void update(float delta)
    {
    }

    protected void checkCollision()
    {
    }
}
