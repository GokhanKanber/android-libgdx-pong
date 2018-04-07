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

import com.gokhankanber.android.pong.controller.game.Cpu;
import com.gokhankanber.android.pong.provider.Config;

public class Paddle extends Model
{
    // Score
    public int points = 0;
    public boolean[][] scoreBoard;

    // User or Cpu
    private boolean isCpu;
    private Cpu cpu;

    public Paddle(float x, float y, float width, float height)
    {
        super(x, y, width, height);

        updateScore();
    }

    @Override
    public void update(float delta)
    {
        if(isCpu)
        {
            cpu.move(delta);
        }
    }

    public void setCpu()
    {
        isCpu = true;
        cpu = new Cpu(this);
    }

    /**
     * Moves user paddle by amount of change in y coordinate.
     * @param amount is change in y coordinate.
     */
    public void move(float amount)
    {
        bounds.y += amount;
        checkWorld();
        setPosition(bounds.x, bounds.y);
    }

    /**
     * Checks bounds of paddle in y coordinate to stay paddle in world.
     */
    public void checkWorld()
    {
        if(bounds.y + bounds.height > Config.getHeight())
        {
            bounds.y = Config.getHeight() - bounds.height;
        }
        else if(bounds.y < 0)
        {
            bounds.y = 0;
        }
    }

    /**
     * Contains score data as numbers in a boolean array.
     */
    public void updateScore()
    {
        String score = String.valueOf(points);
        int length = score.length();
        int width = length * 3 + length - 1;
        int height = 5;

        scoreBoard = new boolean[width][height];
        int x = 0;

        for(char c : score.toCharArray())
        {
            switch(c)
            {
                case '0':
                    buildZero(x);
                    break;
                case '1':
                    buildOne(x);
                    break;
                case '2':
                    buildTwo(x);
                    break;
                case '3':
                    buildThree(x);
                    break;
                case '4':
                    buildFour(x);
                    break;
                case '5':
                    buildFive(x);
                    break;
                case '6':
                    buildSix(x);
                    break;
                case '7':
                    buildSeven(x);
                    break;
                case '8':
                    buildEight(x);
                    break;
                case '9':
                    buildNine(x);
                    break;
            }

            x += 3;

            if(length > 1)
            {
                x++;
                length--;
            }
        }
    }

    public int getSize()
    {
        int size = 0;

        for(boolean[] column : scoreBoard)
        {
            for(boolean row: column)
            {
                if(row)
                {
                    size++;
                }
            }
        }

        return size;
    }

    private void buildZero(int x)
    {
        scoreBoard[x][0] = true;
        scoreBoard[x][1] = true;
        scoreBoard[x][2] = true;
        scoreBoard[x][3] = true;
        scoreBoard[x][4] = true;
        scoreBoard[x+1][0] = true;
        scoreBoard[x+1][4] = true;
        scoreBoard[x+2][0] = true;
        scoreBoard[x+2][1] = true;
        scoreBoard[x+2][2] = true;
        scoreBoard[x+2][3] = true;
        scoreBoard[x+2][4] = true;
    }

    private void buildOne(int x)
    {
        scoreBoard[x+2][0] = true;
        scoreBoard[x+2][1] = true;
        scoreBoard[x+2][2] = true;
        scoreBoard[x+2][3] = true;
        scoreBoard[x+2][4] = true;
    }

    private void buildTwo(int x)
    {
        scoreBoard[x][0] = true;
        scoreBoard[x][2] = true;
        scoreBoard[x][3] = true;
        scoreBoard[x][4] = true;
        scoreBoard[x+1][0] = true;
        scoreBoard[x+1][2] = true;
        scoreBoard[x+1][4] = true;
        scoreBoard[x+2][0] = true;
        scoreBoard[x+2][1] = true;
        scoreBoard[x+2][2] = true;
        scoreBoard[x+2][4] = true;
    }

    private void buildThree(int x)
    {
        scoreBoard[x][0] = true;
        scoreBoard[x][2] = true;
        scoreBoard[x][4] = true;
        scoreBoard[x+1][0] = true;
        scoreBoard[x+1][2] = true;
        scoreBoard[x+1][4] = true;
        scoreBoard[x+2][0] = true;
        scoreBoard[x+2][1] = true;
        scoreBoard[x+2][2] = true;
        scoreBoard[x+2][3] = true;
        scoreBoard[x+2][4] = true;
    }

    private void buildFour(int x)
    {
        scoreBoard[x][0] = true;
        scoreBoard[x][1] = true;
        scoreBoard[x][2] = true;
        scoreBoard[x+1][2] = true;
        scoreBoard[x+2][0] = true;
        scoreBoard[x+2][1] = true;
        scoreBoard[x+2][2] = true;
        scoreBoard[x+2][3] = true;
        scoreBoard[x+2][4] = true;
    }

    private void buildFive(int x)
    {
        scoreBoard[x][0] = true;
        scoreBoard[x][1] = true;
        scoreBoard[x][2] = true;
        scoreBoard[x][4] = true;
        scoreBoard[x+1][0] = true;
        scoreBoard[x+1][2] = true;
        scoreBoard[x+1][4] = true;
        scoreBoard[x+2][0] = true;
        scoreBoard[x+2][2] = true;
        scoreBoard[x+2][3] = true;
        scoreBoard[x+2][4] = true;
    }

    private void buildSix(int x)
    {
        scoreBoard[x][0] = true;
        scoreBoard[x][1] = true;
        scoreBoard[x][2] = true;
        scoreBoard[x][3] = true;
        scoreBoard[x][4] = true;
        scoreBoard[x+1][0] = true;
        scoreBoard[x+1][2] = true;
        scoreBoard[x+1][4] = true;
        scoreBoard[x+2][0] = true;
        scoreBoard[x+2][2] = true;
        scoreBoard[x+2][3] = true;
        scoreBoard[x+2][4] = true;
    }

    private void buildSeven(int x)
    {
        scoreBoard[x][0] = true;
        scoreBoard[x+1][0] = true;
        scoreBoard[x+2][0] = true;
        scoreBoard[x+2][1] = true;
        scoreBoard[x+2][2] = true;
        scoreBoard[x+2][3] = true;
        scoreBoard[x+2][4] = true;
    }

    private void buildEight(int x)
    {
        scoreBoard[x][0] = true;
        scoreBoard[x][1] = true;
        scoreBoard[x][2] = true;
        scoreBoard[x][3] = true;
        scoreBoard[x][4] = true;
        scoreBoard[x+1][0] = true;
        scoreBoard[x+1][2] = true;
        scoreBoard[x+1][4] = true;
        scoreBoard[x+2][0] = true;
        scoreBoard[x+2][1] = true;
        scoreBoard[x+2][2] = true;
        scoreBoard[x+2][3] = true;
        scoreBoard[x+2][4] = true;
    }

    private void buildNine(int x)
    {
        scoreBoard[x][0] = true;
        scoreBoard[x][1] = true;
        scoreBoard[x][2] = true;
        scoreBoard[x][4] = true;
        scoreBoard[x+1][0] = true;
        scoreBoard[x+1][2] = true;
        scoreBoard[x+1][4] = true;
        scoreBoard[x+2][0] = true;
        scoreBoard[x+2][1] = true;
        scoreBoard[x+2][2] = true;
        scoreBoard[x+2][3] = true;
        scoreBoard[x+2][4] = true;
    }
}
