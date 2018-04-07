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

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteCache;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.gokhankanber.android.pong.model.Block;
import com.gokhankanber.android.pong.model.World;
import com.gokhankanber.android.pong.provider.Asset;
import com.gokhankanber.android.pong.provider.Config;

/**
 * World renderer class.
 * Creates and draws world, scoreboard, and models.
 */
public class WorldRenderer
{
    // Score board
    private final float scoreY = Config.getHeight() - Config.BLOCK * 7;
    private final float scorePaddle1X = Config.WIDTH / 2 - Config.BLOCK * 4;
    private final float scorePaddle2X = Config.WIDTH / 2 + Config.BLOCK * 4;

    // Render
    private World world;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Asset asset;
    private TextureRegion ballRegion, blockRegion, paddle1Region, paddle2Region;
    private SpriteCache cache;
    private int cacheId;

    public WorldRenderer(World world, SpriteBatch batch, OrthographicCamera camera)
    {
        // Creates texture regions for models and create world.
        this.world = world;
        this.batch = batch;
        this.camera = camera;
        asset = Asset.get();
        ballRegion = new TextureRegion(asset.getTexture(), (int) world.getBall().getWidth(), (int) world.getBall().getHeight());
        blockRegion = new TextureRegion(asset.getTexture(), (int) Config.BLOCK, (int) Config.BLOCK);
        paddle1Region = new TextureRegion(asset.getTexture(), (int) world.getPaddle1().getWidth(), (int) world.getPaddle1().getHeight());
        paddle2Region = new TextureRegion(asset.getTexture(), (int) world.getPaddle2().getWidth(), (int) world.getPaddle2().getHeight());
        resetWorld();
    }

    /**
     * Draw world and models.
     */
    public void render()
    {
        drawWorld();

        batch.begin();
        batch.draw(ballRegion, world.getBall().getX(), world.getBall().getY());
        batch.draw(paddle1Region, world.getPaddle1().getX(), world.getPaddle1().getY());
        batch.draw(paddle2Region, world.getPaddle2().getX(), world.getPaddle2().getY());
        batch.end();
    }

    /**
     * Releases renderer resources
     */
    public void dispose()
    {
        ballRegion.getTexture().dispose();
        blockRegion.getTexture().dispose();
        paddle1Region.getTexture().dispose();
        paddle2Region.getTexture().dispose();
        cache.dispose();
    }

    /**
     * Creates world with a border, up and down walls, and a scoreboard by using SpriteCache.
     */
    public void resetWorld()
    {
        int size = world.getBorder().size
                + world.getWalls().size
                + world.getPaddle1().getSize()
                + world.getPaddle2().getSize();
        cache = new SpriteCache(size, false);
        cache.beginCache();

        addBorder();
        addWalls();
        addScoreBoard(world.getPaddle1().scoreBoard, scorePaddle1X, true);
        addScoreBoard(world.getPaddle2().scoreBoard, scorePaddle2X, false);

        cacheId = cache.endCache();
    }

    private void addBorder()
    {
        for(Block block : world.getBorder())
        {
            cache.add(blockRegion, block.getX(), block.getY());
        }
    }

    private void addWalls()
    {
        for(Block block : world.getWalls())
        {
            cache.add(blockRegion, block.getX(), block.getY(), block.getWidth(), block.getHeight());
        }
    }

    /**
     * Adds user's scoreboard to screen.
     * @param scoreBoard contains score data.
     * @param scoreX sets the base x coordinate for the user's scoreboard.
     * @param left defines the left or right scoreboard.
     */
    private void addScoreBoard(boolean[][] scoreBoard, float scoreX, boolean left)
    {
        int width = scoreBoard.length;
        int height = scoreBoard[0].length;

        if(left)
        {
            scoreX = scoreX - width * Config.BLOCK;
        }

        for(int x = 0; x < width; x++)
        {
            for(int y = 0; y < height; y++)
            {
                if(scoreBoard[x][y])
                {
                    cache.add(blockRegion, scoreX + x * Config.BLOCK, scoreY + (height - y - 1) * Config.BLOCK);
                }
            }
        }
    }

    /**
     * Draws world with SpriteCache.
     */
    private void drawWorld()
    {
        cache.setProjectionMatrix(camera.combined);
        cache.begin();
        cache.draw(cacheId);
        cache.end();
    }
}
