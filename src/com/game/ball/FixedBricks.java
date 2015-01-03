/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.game.ball;

import javax.microedition.lcdui.Graphics;
//import javax.microedition.midlet.*;

final class FixedBricks extends Actor {
int colcount;
    FixedBricks(GameScreen gsArg, int xArg, int yArg)
{
         super(gsArg,xArg,yArg);

}

    public void render(Graphics g){
    g.setColor(255, 135, 15);
    g.fillRect(getX(), getY(), getActorWidth(),getActorHeight());

}

    public int getActorWidth()
{
// square by default
return gameScreen.getWidth()/6;
}

   
}
