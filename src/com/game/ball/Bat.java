package com.game.ball;

import javax.microedition.lcdui.Graphics;

final class Bat extends Actor {

     Bat(GameScreen gsArg, int xArg, int yArg)
{
         super(gsArg,xArg,yArg);

}

public void cycle(int dir)
{
       if(dir==0)//go left
       setX(getX()-4);
       else if(dir==1)//go ryt
       setX(getX()+4);
}


public void render(Graphics g){
    g.setColor(255, 28, 28);
    g.fillRect(getX(), getY(), getActorWidth(),getActorHeight());

}

public int getActorWidth()
{
// square by default
return 44;
}

public int getActorHeight()
{
return 5;
}

}

