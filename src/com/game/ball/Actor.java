/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.game.ball;

import java.io.IOException;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

abstract public class Actor
{
 GameScreen gameScreen;
 int x, y,score=0;
/*public boolean isCollidingWith(int px, int py)
{
if (px >= getX() && px <= (getX() + getActorWidth()) &&
py >= getY() && py <= (getY() + getActorHeight()) )
return true;
return false;
}*/

/*public boolean isCollidingWith(Actor another)
{
// check if any of our corners lie inside the other actors’
// bounding rectangles
if (isCollidingWith(another.getX(), another.getY()) ||
isCollidingWith(another.getX() + another.getActorWidth(), another.getY()) ||
isCollidingWith(another.getX(), another.getY() + another.getActorHeight()) ||
isCollidingWith(another.getX() + another.getActorWidth(),
another.getY() + another.getActorHeight()))
return true;
else
return false;
}*/

public Actor(GameScreen gsArg, int xArg, int yArg)
{
gameScreen = gsArg;
x = xArg;
y = yArg;
}

void render(Graphics g,String s)
    {
        try
        {
         Image actorImage = Image.createImage(s);
         g.drawImage(actorImage, getX(),getY(), 0);
        }
        catch (IOException ex)
        {
            System.out.println("image path problem");
           //ex.printStackTrace();
        }
    }


public int getActorWidth()
{
// square by default
return getActorHeight();
}

public int getActorHeight()
{
return 7;
}

public int getX() { return x; }

public void setX(int newX)
{
x = newX;

if (x <1)
x = 1;
if (x >( gameScreen.getWidth()-getActorWidth()-1))
x = gameScreen.getWidth()-getActorWidth()-1;

}
public int getY() { return y; }

public void setY(int newY)
{
y = newY;
if (y < 0)
y = 0;
if (y > (gameScreen.getHeight()-getActorHeight()-1))
y = gameScreen.getHeight()-getActorHeight()-1;
}

void collides(Ball bal){
 //for top/bottom collisions
    if( ((bal.y+bal.getActorHeight()) > this.y && (bal.y+bal.getActorHeight()<this.y+this.getActorHeight())) && ((bal.x>this.x && bal.x < this.x+this.getActorWidth()) || (bal.x+bal.getActorWidth()>this.x && bal.x+bal.getActorWidth()<this.x+this.getActorWidth()) ) )
                {bal.angle=-bal.angle;
                 if(this instanceof FixedBricks) {gameScreen.score++;((FixedBricks)this).colcount++;}

                }
 else if ((bal.y < this.y + this.getActorHeight() && bal.y > this.y) && ((bal.x > this.x && bal.x < this.x + this.getActorWidth()) || (bal.x + bal.getActorWidth() > this.x && bal.x + bal.getActorWidth() < this.x + this.getActorWidth())))
                {bal.angle=-bal.angle;
                 if(this instanceof FixedBricks) {gameScreen.score++;((FixedBricks)this).colcount++;}
                }
//for side collisions

 else if (((bal.y >= this.y && bal.y <= this.y + this.getActorHeight()) || (bal.y + bal.getActorHeight() >= this.y && bal.y + bal.getActorHeight() <= this.y + this.getActorHeight())) && (bal.x + bal.getActorWidth() >= this.x && bal.x + bal.getActorWidth() <= this.x + this.getActorWidth()))
    if(this instanceof FixedBricks)
    {
        bal.angle = 180 - bal.angle;gameScreen.score++;((FixedBricks)this).colcount++;
    }
    else  if (((bal.y >= this.y && bal.y <= this.y + this.getActorHeight()) || (bal.y + bal.getActorHeight() >= this.y && bal.y + bal.getActorHeight() <= this.y + this.getActorHeight())) && (bal.x <= this.x + this.getActorWidth() && bal.x >= this.x))
            if(this instanceof FixedBricks) 
            {   bal.angle=180-bal.angle;gameScreen.score++;((FixedBricks)this).colcount++;}
    //if(bal.angle>=360)bal.angle=bal.angle-360;
    //if(bal.angle<360)bal.angle=360-bal.angle;


}

}