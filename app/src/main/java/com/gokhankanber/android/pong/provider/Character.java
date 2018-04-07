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

package com.gokhankanber.android.pong.provider;

import java.util.HashMap;
import java.util.Map;

public class Character
{
    // Characters

    private byte[] charA = {0, 16, 40, 68, 68, 124, 68, 68};
    private byte[] charB = {0, 120, 68, 68, 120, 68, 68, 120};
    private byte[] charC = {0, 56, 68, 64, 64, 64, 68, 56};
    private byte[] charD = {0, 120, 68, 68, 68, 68, 68, 120};
    private byte[] charE = {0, 124, 64, 64, 120, 64, 64, 124};
    private byte[] charF = {0, 124, 64, 64, 120, 64, 64, 64};
    private byte[] charG = {0, 60, 64, 64, 76, 68, 68, 60};
    private byte[] charH = {0, 68, 68, 68, 124, 68, 68, 68};
    private byte[] charI = {0, 56, 16, 16, 16, 16, 16, 56};
    private byte[] charIWithDot = {16, 0, 56, 16, 16, 16, 16, 56};
    private byte[] charJ = {0, 4, 4, 4, 4, 4, 68, 56};
    private byte[] charK = {0, 68, 72, 80, 96, 80, 72, 68};
    private byte[] charL = {0, 64, 64, 64, 64, 64, 64, 124};
    private byte[] charM = {0, 68, 108, 84, 84, 68, 68, 68};
    private byte[] charN = {0, 68, 68, 100, 84, 76, 68, 68};
    private byte[] charO = {0, 56, 68, 68, 68, 68, 68, 56};
    private byte[] charP = {0, 120, 68, 68, 120, 64, 64, 64};
    private byte[] charQ = {0, 56, 68, 68, 68, 84, 72, 52};
    private byte[] charR = {0, 120, 68, 68, 120, 80, 72, 68};
    private byte[] charS = {0, 56, 68, 64, 56, 4, 68, 56};
    private byte[] charT = {0, 124, 16, 16, 16, 16, 16, 16};
    private byte[] charU = {0, 68, 68, 68, 68, 68, 68, 56};
    private byte[] charV = {0, 68, 68, 68, 68, 68, 40, 16};
    private byte[] charW = {0, 68, 68, 68, 84, 84, 108, 68};
    private byte[] charX = {0, 68, 68, 40, 16, 40, 68, 68};
    private byte[] charY = {0, 68, 68, 40, 16, 16, 16, 16};
    private byte[] charZ = {0, 124, 4, 8, 16, 32, 64, 124};
    private byte[] charZero = {0, 56, 68, 76, 84, 100, 68, 56};
    private byte[] charOne = {0, 16, 48, 16, 16, 16, 16, 56};
    private byte[] charTwo = {0, 56, 68, 4, 24, 32, 64, 124};
    private byte[] charThree = {0, 124, 4, 8, 24, 4, 68, 56};
    private byte[] charFour = {0, 8, 24, 40, 72, 124, 8, 8};
    private byte[] charFive = {0, 124, 64, 120, 4, 4, 68, 56};
    private byte[] charSix = {0, 28, 32, 64, 120, 68, 68, 56};
    private byte[] charSeven = {0, 124, 4, 8, 16, 32, 32, 32};
    private byte[] charEight = {0, 56, 68, 68, 56, 68, 68, 56};
    private byte[] charNine = {0, 56, 68, 68, 60, 4, 8, 112};
    private byte[] charQuestion = {0, 56, 68, 8, 16, 16, 0, 16};
    private byte[] charExclamation = {0, 16, 16, 16, 16, 16, 0, 16};
    private byte[] charDot = {0, 0, 0, 0, 0, 0, 0, 16};
    private byte[] charColon = {0, 0, 0, 0, 0, 16, 0, 16};
    private byte[] charPlus = {0, 0, 16, 16, 124, 16, 16, 0};
    private byte[] charMinus = {0, 0, 0, 0, 124, 0, 0, 0};
    private byte[] charEqual = {0, 0, 0, 124, 0, 124, 0, 0};
    private byte[] charSlash = {0, 4, 8, 8, 16, 32, 32, 64};
    private byte[] charLess = {0, 8, 16, 32, 64, 32, 16, 8};
    private byte[] charGreater = {0, 32, 16, 8, 4, 8, 16, 32};
    private byte[] charBracketsBegin = {0, 24, 16, 16, 16, 16, 16, 24};
    private byte[] charBracketsEnd = {0, 24, 8, 8, 8, 8, 8, 24};
    private byte[] charParenthesesBegin = {0, 8, 16, 32, 32, 32, 16, 8};
    private byte[] charParenthesesEnd = {0, 32, 16, 8, 8, 8, 16, 32};
    private byte[] charBracesBegin = {0, 8, 16, 16, 32, 16, 16, 8};
    private byte[] charBracesEnd = {0, 32, 16, 16, 8, 16, 16, 32};
    private byte[] charPercent = {0, 98, -108, 104, 22, 41, 70, 0};
    private byte[] charAmpersand = {0, 32, 80, 80, 32, 84, 72, 52};
    private byte[] charHash = {0, 0, 40, 124, 40, 124, 40, 0};
    private Map<Short, byte[]> cMap;
    private static Character instance;

    private Character()
    {
        cMap = new HashMap<>();
    }

    public static Character get()
    {
        if(instance == null)
        {
            instance = new Character();
            instance.init();
        }

        return instance;
    }

    private void init()
    {
        put('A', charA);
        put('B', charB);
        put('C', charC);
        put('D', charD);
        put('E', charE);
        put('F', charF);
        put('G', charG);
        put('H', charH);
        put('I', charI);
        put((char) 304, charIWithDot);
        put('J', charJ);
        put('K', charK);
        put('L', charL);
        put('M', charM);
        put('N', charN);
        put('O', charO);
        put('P', charP);
        put('Q', charQ);
        put('R', charR);
        put('S', charS);
        put('T', charT);
        put('U', charU);
        put('V', charV);
        put('W', charW);
        put('X', charX);
        put('Y', charY);
        put('Z', charZ);
        put('0', charZero);
        put('1', charOne);
        put('2', charTwo);
        put('3', charThree);
        put('4', charFour);
        put('5', charFive);
        put('6', charSix);
        put('7', charSeven);
        put('8', charEight);
        put('9', charNine);
        put('?', charQuestion);
        put('!', charExclamation);
        put('.', charDot);
        put(':', charColon);
        put('+', charPlus);
        put('-', charMinus);
        put('=', charEqual);
        put('/', charSlash);
        put('<', charLess);
        put('>', charGreater);
        put('[', charBracketsBegin);
        put(']', charBracketsEnd);
        put('(', charParenthesesBegin);
        put(')', charParenthesesEnd);
        put('{', charBracesBegin);
        put('}', charBracesEnd);
        put('%', charPercent);
        put('&', charAmpersand);
        put('#', charHash);
    }

    private void put(char character, byte[] bytes)
    {
        cMap.put((short) character, bytes);
    }

    public byte[] getBytes(char character)
    {
        return cMap.get((short) character);
    }
}
