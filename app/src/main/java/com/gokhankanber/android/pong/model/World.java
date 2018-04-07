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

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.gokhankanber.android.pong.provider.Asset;
import com.gokhankanber.android.pong.provider.Config;

/**
 * Creates models.
 * Updates and checks score.
 * Manages world's state.
 */
public class World
{
    // State
    private enum State
    {
        READY,
        PAUSE,
        RESUME,
        ENDING,
        END
    }

    private State state = State.READY;
    private float stateTime = 0;
    private boolean scoreChanged = false;
    private boolean gameOver = false;

    // Asset
    private Asset asset;

    // Models
    private final float paddingHorizontal = Config.BLOCK;
    private final float ballSize = Config.BLOCK;
    private final float paddleWidth = Config.BLOCK;
    private final float paddleHeight = Config.BLOCK * 4;
    private final int borderBlockCount = (int) (Config.getHeight() / (Config.BLOCK * 2));
    private final float wallX = 2 * Config.BLOCK;
    private final float wallY = (int) Config.getHeight() - Config.BLOCK;
    private final float wallWidth = Config.WIDTH - 4 * Config.BLOCK;
    private final float wallHeight = Config.BLOCK;
    private final Vector2 ballStartPoint;
    private Ball ball;
    private Paddle paddle1, paddle2;
    private Array<Block> border;
    private Array<Block> walls;

    public World()
    {
        // Get asset, create models.
        asset = Asset.get();
        ballStartPoint = new Vector2((Config.WIDTH - ballSize) / 2, borderBlockCount * Config.BLOCK - (borderBlockCount % 2 == 0 ? 1 : 2) * Config.BLOCK);
        ball = new Ball(ballStartPoint.x, ballStartPoint.y, ballSize, ballSize);
        ball.setListener(iWorld);
        paddle1 = new Paddle(paddingHorizontal, (Config.getHeight() - paddleHeight) / 2, paddleWidth, paddleHeight);
        paddle2 = new Paddle(Config.WIDTH - paddingHorizontal - paddleWidth, (Config.getHeight() - paddleHeight) / 2, paddleWidth, paddleHeight);
        paddle2.setListener(iWorld);
        paddle2.setCpu();
        createBorder();
        createWalls();
    }

    public boolean isScoreChanged()
    {
        return scoreChanged;
    }

    public void resetScoreChanged()
    {
        scoreChanged = false;
    }

    /**
     * Resets world on new score.
     */
    private void reset()
    {
        scoreChanged = true;
        ball.reset(ballStartPoint.x, ballStartPoint.y);
    }

    /**
     * Updates score.
     * @param paddle is paddle number.
     */
    private void points(int paddle)
    {
        asset.playPoints();

        if(paddle == 1)
        {
            getPaddle1().points++;
            getPaddle1().updateScore();
            getBall().setAcceleration(-Config.BALL_ACCELERATION);
        }
        else
        {
            getPaddle2().points++;
            getPaddle2().updateScore();
            getBall().setAcceleration(Config.BALL_ACCELERATION);
        }

        reset();
        checkPoints();
    }

    /**
     * Checks score.
     */
    private void checkPoints()
    {
        if(getPaddle1().points == Config.MAX_POINTS)
        {
            ending();
        }
        else if(getPaddle2().points == Config.MAX_POINTS)
        {
            ending();
            gameOver = true;
        }
    }

    public boolean isGameOver()
    {
        return gameOver;
    }

    /**
     * Resets world for a new game.
     */
    public void newGame()
    {
        state = State.READY;
        gameOver = false;
        paddle1.setPosition(paddingHorizontal, (Config.getHeight() - paddleHeight) / 2);
        getPaddle1().points = 0;
        getPaddle1().updateScore();
        paddle2.setPosition(Config.WIDTH - paddingHorizontal - paddleWidth, (Config.getHeight() - paddleHeight) / 2);
        getPaddle2().points = 0;
        getPaddle2().updateScore();
        ball.setAcceleration(-Config.BALL_ACCELERATION);
        reset();
    }

    public void pause()
    {
        state = State.PAUSE;
    }

    public void resume()
    {
        state = State.RESUME;
    }

    public void ending()
    {
        state = State.ENDING;
    }

    public void end()
    {
        state = State.END;
    }

    public boolean isReady()
    {
        return state == State.READY;
    }

    public boolean isPaused()
    {
        return state == State.PAUSE;
    }

    public boolean isResumed()
    {
        return state == State.RESUME;
    }

    public boolean isEnding()
    {
        return state == State.ENDING;
    }

    public boolean isEnd()
    {
        return state == State.END;
    }

    private void createBorder()
    {
        border = new Array<>();

        for(int i = 0; i < borderBlockCount; i++)
        {
            border.add(new Block((Config.WIDTH - Config.BLOCK) / 2, Config.BLOCK + Config.BLOCK * i * 2, Config.BLOCK, Config.BLOCK));
        }
    }

    private void createWalls()
    {
        walls = new Array<>();
        walls.add(new Block(wallX, 0, wallWidth, wallHeight));
        walls.add(new Block(wallX, wallY, wallWidth, wallHeight));
    }

    public Ball getBall()
    {
        return ball;
    }

    public Paddle getPaddle1()
    {
        return paddle1;
    }

    public Paddle getPaddle2()
    {
        return paddle2;
    }

    public Array<Block> getBorder()
    {
        return border;
    }

    public Array<Block> getWalls()
    {
        return walls;
    }

    public void update(float delta)
    {
        if(isResumed())
        {
            ball.update(delta);
            paddle2.update(delta);
        }
        else if(isReady())
        {
            // Wait for 3 seconds on game start or touch to start.
            wait(delta, 3);
        }
    }

    public void wait(float delta, int waitTime)
    {
        if(stateTime >= waitTime)
        {
            stateTime = 0;
            resume();
        }
        else
        {
            stateTime += delta;
        }
    }

    // World interface instance for models.
    private IWorld iWorld = new IWorld()
    {
        @Override
        public Ball getBall()
        {
            return ball;
        }

        @Override
        public Paddle getPaddle1()
        {
            return paddle1;
        }

        @Override
        public Paddle getPaddle2()
        {
            return paddle2;
        }

        @Override
        public Array<Block> getWalls()
        {
            return walls;
        }

        @Override
        public void points(int paddle)
        {
            World.this.points(paddle);
        }

        @Override
        public void playHitSound()
        {
            asset.playHit();
        }

        @Override
        public void playHitWallSound()
        {
            asset.playHitWall();
        }
    };
}
