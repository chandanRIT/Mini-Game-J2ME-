/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.game.ball;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import net.jscience.util.MathFP;
//import javax.microedition.lcdui.*;

 final class Ball extends Actor implements CommandListener
{
          int angle=45;
          private int floatX,floatY;
          private int FP_PI2 = MathFP.mul(MathFP.PI, MathFP.toFP(2));
          private int FP_DEGREES_PER_RAD = MathFP.div(MathFP.toFP(360), FP_PI2);
          private int speed=3; // speed (along x axis only)
          private Command start,exit;
          private Alert al;static int chances=3;

    Ball(GameScreen gameScreenArg,int xArg,int yArg,int angle)
    {
    super(gameScreenArg, xArg, yArg);//this.b=b;this.m=m;
    this.angle=angle;
    floatX=MathFP.toFP(xArg);floatY=MathFP.toFP(yArg);
    start=new Command("continue",Command.SCREEN,1);
    exit=new Command("exit",Command.EXIT,2);
    al=new Alert("game alert!", "ball fell down! "+(Ball.chances-1)+"chances left!", null, AlertType.CONFIRMATION);
    al.addCommand(start);al.addCommand(exit);
    al.setCommandListener(this);
    }

    public void commandAction(Command command,Displayable displayable)
    {
    if(command ==start) {gameScreen.mmenu.colcount2=gameScreen.fb2.colcount;gameScreen.mmenu.colcount=gameScreen.fb1.colcount;gameScreen.mmenu.score=gameScreen.score; gameScreen.mmenu.startGame();}
    if(command==exit) gameScreen.mmenu.gameOver(2);
    }

    public void setX(int newX)
     {
        x=newX;
     }

    public void setY(int newY)
{
y = newY;
}

     public int getActorWidth()
     {
        return 18;
     }

      public int getActorHeight()
     {
       return 18;
     }

      public void cycle(long deltaMS)
{         //System.out.println("angle="+angle);
          int dirRadians;
          dirRadians= MathFP.div(MathFP.toFP(angle), FP_DEGREES_PER_RAD);
          floatX = MathFP.add( floatX,speed*MathFP.cos(dirRadians) );
          floatY = MathFP.add( floatY,speed*(-MathFP.sin(dirRadians)) );


          if (MathFP.toInt(floatX) < 0 || MathFP.toInt(floatX) > gameScreen.getWidth() - getActorWidth())
              {
              angle = 180 - angle;}
              if(MathFP.toInt(floatY)<0)
          { angle=-angle;//speed=3;
          }
          
          if(MathFP.toInt(floatY)>gameScreen.getHeight()-5)
          {
                    gameScreen.mode=0;
                    Ball.chances--;if(Ball.chances==0)
                    { al.removeCommand(start);//Ball.chances=3;
                    }
                    else Display.getDisplay(gameScreen.mmenu.theMidlet).setCurrent(al);
                    gameScreen.running=false;
          }

          if(angle>=360)
          angle-=360;
          else if (angle < 360)
          {
              angle = 360 + angle;
          }
          setX(MathFP.toInt(floatX));setY(MathFP.toInt(floatY));
        }

  }
