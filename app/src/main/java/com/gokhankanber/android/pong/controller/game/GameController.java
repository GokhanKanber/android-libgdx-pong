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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.gokhankanber.android.pong.Pong;
import com.gokhankanber.android.pong.R;
import com.gokhankanber.android.pong.controller.BaseController;
import com.gokhankanber.android.pong.controller.game.GameInputController.InputListener;
import com.gokhankanber.android.pong.model.World;
import com.gokhankanber.android.pong.provider.Config;
import com.gokhankanber.android.pong.provider.Font;

/**
 * Controller class for {@link com.gokhankanber.android.pong.view.GameScreen} class.
 * Listens for user input.
 * Creates, updates and renders world.
 * Draws game menu: pause menu, win / game over menu.
 */
public class GameController extends BaseController
{
    // Game menu: pause menu, win / game over menu
    private enum GameMenuType
    {
        PAUSE,
        END
    }

    private final float gameMenuPaddingTop = 20;
    private final float gameMenuWidth = 300;
    private final float gameMenuHeight = 200;
    private final float gameMenuX = (Config.WIDTH - gameMenuWidth) / 2;
    private final float gameMenuY = (Config.getHeight() - gameMenuHeight) / 2;

    private final float gameMenuItemWidth = 200.0f;
    private final float gameMenuItemHeight = 36.0f;
    private final float gameMenuItemX = (Config.WIDTH - gameMenuItemWidth) / 2;
    private final int gameMenuFontSize = 26;
    private BitmapFont gameMenuFont;
    private String gameEndMessage;

    private ShapeRenderer shapeRenderer;
    private Color gameMenuBackgroundColor;
    private Rectangle[] menuItemBounds;
    private String[] gameMenuItems;
    private float gameEndMessageX;
    private float gameEndMessageY;
    private float[] gameMenuItemsFontX;
    private float[] gameMenuItemsFontY;
    private float[] gameMenuItemsBoundY;
    private final int menuItemFontSize = 26;
    private Texture[] menuItemTextures;
    private Texture gameEndMessageTexture;

    // Input, world, world renderer
    private GameInputController inputController;
    private World world;
    private WorldRenderer worldRenderer;

    public GameController(Pong game)
    {
        super(game);
    }

    @Override
    public void init()
    {
        super.init();

        // Init input
        inputController = new GameInputController(camera, inputListener);
        Gdx.input.setInputProcessor(inputController);
        Gdx.input.setCatchBackKey(true);

        // Init world
        world = new World();
        worldRenderer = new WorldRenderer(world, batch, camera);

        // Init pause menu
        resetGameMenu(GameMenuType.PAUSE);
    }

    @Override
    public void update(float delta)
    {
        // Update world on resume and ready states
        // Init win / game over menu on ending state and set world's state to end
        if(world.isResumed() || world.isReady())
        {
            world.update(delta);

            if(world.isScoreChanged())
            {
                worldRenderer.resetWorld();
                world.resetScoreChanged();

                if(world.isEnding())
                {
                    if(world.isGameOver())
                    {
                        gameEndMessage = game.getResources().getString(R.string.game_over);
                    }
                    else
                    {
                        gameEndMessage = game.getResources().getString(R.string.win);
                    }

                    Gdx.app.postRunnable(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            resetGameMenu(GameMenuType.END);
                            world.end();
                        }
                    });
                }
            }
        }
    }

    @Override
    public void draw(float delta)
    {
        clear();

        // Render world
        worldRenderer.render();

        // Draw game menu: pause menu, win / game over menu
        if(world.isPaused() || world.isEnd())
        {
            drawGameMenu();
        }
    }

    @Override
    public void release()
    {
        super.release();

        if(Config.TYPE_TEXT == Config.TextType.FONT)
        {
            gameMenuFont.dispose();
        }

        shapeRenderer.dispose();
        worldRenderer.dispose();
    }

    /**
     * Resets game menu content and creates game menu.
     * Creates menu item click bounds.
     * @param gameMenuType is used to reset menu items.
     */
    private void resetGameMenu(GameMenuType gameMenuType)
    {
        switch(gameMenuType)
        {
            case PAUSE:
                pauseMenuItems();
                break;
            case END:
                endMenuItems();
                break;
        }

        initGameMenu();

        int menuItemsLength = gameMenuItems.length;
        menuItemBounds = new Rectangle[menuItemsLength];

        for(int i = 0; i < gameMenuItems.length; i++)
        {
            menuItemBounds[i] = new Rectangle(gameMenuItemX, gameMenuItemsBoundY[i], gameMenuItemWidth, gameMenuItemHeight);
        }
    }

    private void pauseMenuItems()
    {
        // Get string values from res/values/strings.xml resource files.
        gameMenuItems = new String[]{
                game.getResources().getString(R.string.resume),
                game.getResources().getString(R.string.new_game)
        };
    }

    private void endMenuItems()
    {
        gameMenuItems = new String[]{
                game.getResources().getString(R.string.new_game)
        };
    }

    /**
     * Creates black transparent background of game menu by using ShapeRenderer.
     * Creates a font and sets x and y coordinates of the content by using GlyphLayout.
     */
    private void initGameMenu()
    {
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(camera.combined);
        gameMenuBackgroundColor = new Color(0.0f, 0.0f, 0.0f, 0.9f);

        int menuItemsLength = gameMenuItems.length;
        gameMenuItemsFontX = new float[menuItemsLength];
        gameMenuItemsFontY = new float[menuItemsLength];
        gameMenuItemsBoundY = new float[menuItemsLength];
        menuItemTextures = new Texture[menuItemsLength];

        float height = gameMenuHeight;

        switch(Config.TYPE_TEXT)
        {
            case TEXTURE:
                initGameMenuWithTexture(menuItemsLength, height);
                break;
            case FONT:
                initGameMenuWithFont(menuItemsLength, height);
                break;
        }
    }

    private void initGameMenuWithTexture(int menuItemsLength, float height)
    {
        if(world.isEnding())
        {
            gameEndMessageTexture = asset.getTexture(gameEndMessage);
            gameEndMessageX = (Config.WIDTH - gameEndMessage.length() * menuItemFontSize) / 2;
            gameEndMessageY = height + gameMenuY - gameMenuPaddingTop - (gameMenuItemHeight + menuItemFontSize) / 2;
            height -= (gameMenuPaddingTop + gameMenuItemHeight);
        }

        for(int i = 0; i < menuItemsLength; i++)
        {
            menuItemTextures[i] = asset.getTexture(gameMenuItems[i]);
            gameMenuItemsFontX[i] = (Config.WIDTH - gameMenuItems[i].length() * menuItemFontSize) / 2;
            gameMenuItemsBoundY[i] = (height + 2 * gameMenuY + (menuItemsLength * gameMenuItemHeight)) / 2 - (i + 1) * gameMenuItemHeight;
            gameMenuItemsFontY[i] = gameMenuItemsBoundY[i] + (gameMenuItemHeight - menuItemFontSize) / 2;
        }
    }

    private void initGameMenuWithFont(int menuItemsLength, float height)
    {
        Font font = new Font();
        gameMenuFont = font.create(Font.FONT)
                .size(gameMenuFontSize)
                .border(1, Color.BLACK)
                .shadow(1, 1, Color.DARK_GRAY)
                .build();

        GlyphLayout glyphLayout = new GlyphLayout();

        if(world.isEnding())
        {
            glyphLayout.setText(gameMenuFont, gameEndMessage);
            gameEndMessageX = (Config.WIDTH - glyphLayout.width) / 2;
            gameEndMessageY = height + gameMenuY - gameMenuPaddingTop - (gameMenuItemHeight - glyphLayout.height) / 2;
            height -= (gameMenuPaddingTop + gameMenuItemHeight);
            glyphLayout.reset();
        }

        for(int i = 0; i < menuItemsLength; i++)
        {
            glyphLayout.setText(gameMenuFont, gameMenuItems[i]);
            gameMenuItemsFontX[i] = (Config.WIDTH - glyphLayout.width) / 2;
            gameMenuItemsBoundY[i] = (height + 2 * gameMenuY + (menuItemsLength * gameMenuItemHeight)) / 2 - (i + 1) * gameMenuItemHeight;
            gameMenuItemsFontY[i] = gameMenuItemsBoundY[i] + (gameMenuItemHeight + glyphLayout.height) / 2;
        }
    }

    /**
     * Draws game menu.
     * If world state is end, shows game end message: win / game over.
     * Draws menu items.
     */
    private void drawGameMenu()
    {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(gameMenuBackgroundColor);
        shapeRenderer.rect(gameMenuX, gameMenuY, gameMenuWidth, gameMenuHeight);
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);

        batch.begin();

        switch(Config.TYPE_TEXT)
        {
            case TEXTURE:
                drawGameMenuWithTexture();
                break;
            case FONT:
                drawGameMenuWithFont();
                break;
        }

        batch.end();
    }

    private void drawGameMenuWithTexture()
    {
        if(world.isEnd())
        {
            batch.draw(gameEndMessageTexture, gameEndMessageX, gameEndMessageY, gameEndMessage.length() * menuItemFontSize, menuItemFontSize);
        }

        for(int i = 0; i < gameMenuItems.length; i++)
        {
            batch.draw(menuItemTextures[i], gameMenuItemsFontX[i], gameMenuItemsFontY[i], gameMenuItems[i].length() * menuItemFontSize, menuItemFontSize);
        }
    }

    private void drawGameMenuWithFont()
    {
        if(world.isEnd())
        {
            gameMenuFont.draw(batch, gameEndMessage, gameEndMessageX, gameEndMessageY);
        }

        for(int i = 0; i < gameMenuItems.length; i++)
        {
            gameMenuFont.draw(batch, gameMenuItems[i], gameMenuItemsFontX[i], gameMenuItemsFontY[i]);
        }
    }

    private InputListener inputListener = new InputListener()
    {
        @Override
        public void back()
        {
            asset.playButton();

            // Back button toggles between pause and resume
            if(world.isResumed())
            {
                world.pause();
            }
            else if(world.isPaused())
            {
                world.resume();
            }
        }

        @Override
        public void move(float amount)
        {
            // Move by amount of change in y coordinate
            if(world.isResumed())
            {
                world.getPaddle1().move(amount);
            }
        }

        @Override
        public void check(float x, float y)
        {
            // Check user touch for world states
            if(world.isReady())
            {
                world.resume();
            }
            else if(world.isPaused())
            {
                if(menuItemBounds[0].contains(x, y))
                {
                    // Pause menu: resume
                    asset.playButton();
                    world.resume();
                }
                else if(menuItemBounds[1].contains(x, y))
                {
                    // Pause menu: new
                    asset.playButton();
                    world.newGame();
                }
            }
            else if(world.isEnd())
            {
                if(menuItemBounds[0].contains(x, y))
                {
                    // Win / game over menu: new
                    asset.playButton();
                    world.newGame();
                    resetGameMenu(GameMenuType.PAUSE);
                }
            }
        }
    };
}
