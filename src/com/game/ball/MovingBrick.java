/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.game.ball;

import javax.microedition.lcdui.Graphics;

  final class MovingBrick extends Actor {
private int dir=1;
private int speed; // speed (along x axis only)
//private long fluff = 0;


 MovingBrick(GameScreen gameScreenArg, int xArg, int yArg, int speedArg)
{
super(gameScreenArg, xArg, yArg);
this.gameScreen=gameScreenArg;
speed = speedArg;
}

 public int getActorWidth()
{
return 66;
}

 public void cycle(long deltaMS)
{
    if(x>(gameScreen.getWidth()-getActorWidth()-2))
    { dir=-1;
    //System.out.println(" mbrick outside ryt");
    }
    else if(x<1) dir=1;
    //System.out.println("inside render of mbrick");
    setX(x+(dir*speed));
}

 


 public void render(Graphics g){
    g.  setColor(60, 140, 90);
    g.fillRect(getX(), getY(), getActorWidth(),getActorHeight());

}
 public void setX(int newX)
{
x=newX;
 }

 }
