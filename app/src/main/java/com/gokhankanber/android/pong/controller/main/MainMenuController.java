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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Rectangle;
import com.gokhankanber.android.pong.Pong;
import com.gokhankanber.android.pong.R;
import com.gokhankanber.android.pong.controller.BaseController;
import com.gokhankanber.android.pong.controller.main.MainInputController.InputListener;
import com.gokhankanber.android.pong.provider.Config;
import com.gokhankanber.android.pong.provider.Font;
import com.gokhankanber.android.pong.view.GameScreen;

/**
 * Controller class for {@link com.gokhankanber.android.pong.view.MainMenuScreen}
 */
public class MainMenuController extends BaseController
{
    // Screen top padding
    private final float paddingTop = 30.0f;

    // Logo
    private float logoX;
    private float logoY;

    // For drawing logo with texture
    private final int logoTextureWidth = 96;
    private final int logoTextureHeight = 48;
    private Texture logoTexture;

    // For drawing logo text with bitmap font
    private final float logoHeight = 48.0f;
    private final int logoFontSize = 36;
    private String logo;
    private BitmapFont logoFont;

    // Main menu
    private final float mainMenuItemWidth = 200.0f;
    private final float mainMenuItemHeight = 36.0f;
    private final float mainMenuItemX = (Config.WIDTH - mainMenuItemWidth) / 2;
    private float mainMenuHeight;
    private float[] mainMenuItemsFontX;
    private float[] mainMenuItemsFontY;
    private float[] mainMenuItemsBoundY;
    private String[] mainMenuItems;
    private Rectangle playBounds;

    // For drawing main menu text with texture
    private final int menuItemFontSize = 26;
    private Texture[] menuItemTextures;

    // For drawing main menu text with bitmap font
    private final int mainMenuFontSize = 26;
    private BitmapFont mainMenuFont;

    // For drawing logo and main menu text with bitmap font
    private Font font;
    private GlyphLayout glyphLayout;

    private MainInputController inputController;

    public MainMenuController(Pong game)
    {
        super(game);
    }

    @Override
    public void init()
    {
        super.init();

        // Init input.
        inputController = new MainInputController(camera, inputListener);
        Gdx.input.setInputProcessor(inputController);
        Gdx.input.setCatchBackKey(false);

        // Init logo and main menu.
        if(Config.TYPE_TEXT == Config.TextType.FONT)
        {
            font = new Font();
            glyphLayout = new GlyphLayout();
        }

        initLogo();
        initMainMenu();
    }

    /**
     * Creates logo font and sets x and y coordinates.
     */
    private void initLogo()
    {
        switch(Config.TYPE_TEXT)
        {
            case TEXTURE:
                initLogoWithTexture();
                break;
            case FONT:
                initLogoWithFont();
                break;
        }
    }

    private void initLogoWithTexture()
    {
        logoTexture = asset.getLogoTexture();
        logoX = (Config.WIDTH - logoTextureWidth) / 2;
        logoY = Config.getHeight() - paddingTop - logoTextureHeight;
    }

    private void initLogoWithFont()
    {
        logo = game.getResources().getString(R.string.app_name);

        logoFont = font.create(Font.FONT)
                .size(logoFontSize)
                .build();

        glyphLayout.setText(logoFont, logo);
        logoX = (Config.WIDTH - glyphLayout.width) / 2;
        logoY = Config.getHeight() - paddingTop - logoHeight / 2 + glyphLayout.height / 2;
        glyphLayout.reset();
    }

    /**
     * Creates main menu font and sets x and y coordinates.
     * Creates menu item click bounds.
     */
    private void initMainMenu()
    {
        mainMenuItems = new String[]{
                game.getResources().getString(R.string.play)
        };

        int menuItemsLength = mainMenuItems.length;
        mainMenuItemsFontX = new float[menuItemsLength];
        mainMenuItemsFontY = new float[menuItemsLength];
        mainMenuItemsBoundY = new float[menuItemsLength];
        mainMenuHeight = Config.getHeight() - paddingTop - logoHeight;

        switch(Config.TYPE_TEXT)
        {
            case TEXTURE:
                initMainMenuWithTexture(menuItemsLength);
                break;
            case FONT:
                initMainMenuWithFont(menuItemsLength);
                break;
        }

        playBounds = new Rectangle(mainMenuItemX, mainMenuItemsBoundY[0], mainMenuItemWidth, mainMenuItemHeight);
    }

    private void initMainMenuWithTexture(int menuItemsLength)
    {
        int i = 0;
        menuItemTextures = new Texture[menuItemsLength];

        for(String item : mainMenuItems)
        {
            menuItemTextures[i] = asset.getTexture(item);
            mainMenuItemsFontX[i] = (Config.WIDTH - item.length() * menuItemFontSize) / 2;
            mainMenuItemsBoundY[i] = (mainMenuHeight + (menuItemsLength * mainMenuItemHeight)) / 2 - (i + 1) * mainMenuItemHeight;
            mainMenuItemsFontY[i] = mainMenuItemsBoundY[i] + (mainMenuItemHeight - menuItemFontSize) / 2;
            i++;
        }
    }

    private void initMainMenuWithFont(int menuItemsLength)
    {
        mainMenuFont = font.create(Font.FONT)
                .size(mainMenuFontSize)
                .border(1, Color.BLACK)
                .shadow(1, 1, Color.DARK_GRAY)
                .build();

        for(int i = 0; i < menuItemsLength; i++)
        {
            glyphLayout.setText(mainMenuFont, mainMenuItems[i]);
            mainMenuItemsFontX[i] = (Config.WIDTH - glyphLayout.width) / 2;
            mainMenuItemsBoundY[i] = (mainMenuHeight + (menuItemsLength * mainMenuItemHeight)) / 2 - (i + 1) * mainMenuItemHeight;
            mainMenuItemsFontY[i] = mainMenuItemsBoundY[i] + (mainMenuItemHeight + glyphLayout.height) / 2;
        }

        glyphLayout.reset();
    }

    @Override
    public void update(float delta)
    {
    }

    @Override
    public void draw(float delta)
    {
        clear();

        // Draws logo and main menu.
        batch.begin();

        switch(Config.TYPE_TEXT)
        {
            case TEXTURE:
                drawWithTexture();
                break;
            case FONT:
                drawWithFont();
                break;
        }

        batch.end();
    }

    private void drawWithTexture()
    {
        batch.draw(logoTexture, logoX, logoY, logoTextureWidth, logoTextureHeight);
        batch.draw(menuItemTextures[0], mainMenuItemsFontX[0], mainMenuItemsFontY[0], mainMenuItems[0].length() * menuItemFontSize, menuItemFontSize);
    }

    private void drawWithFont()
    {
        logoFont.draw(batch, logo, logoX, logoY);
        mainMenuFont.draw(batch, mainMenuItems[0], mainMenuItemsFontX[0], mainMenuItemsFontY[0]);
    }

    @Override
    public void release()
    {
        super.release();

        switch(Config.TYPE_TEXT)
        {
            case TEXTURE:
                logoTexture.dispose();
                menuItemTextures[0].dispose();
                break;
            case FONT:
                logoFont.dispose();
                mainMenuFont.dispose();
                break;
        }
    }

    private InputListener inputListener = new InputListener()
    {
        @Override
        public void check(float x, float y)
        {
            // Check user touch
            if(playBounds.contains(x, y))
            {
                // Main menu: play
                asset.playButton();
                game.setScreen(new GameScreen(game));
            }
        }
    };
}
