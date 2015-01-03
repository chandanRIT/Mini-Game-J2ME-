package com.game.ball;

import java.io.IOException;
import javax.microedition.lcdui.*;
//import javax.microedition.lcdui.game.Sprite;


public final class MainMenu extends Form implements CommandListener
{   private  GameScreen g;
    MainMidlet theMidlet;
    private final Command start;
    private final Command exit;
     int colcount=0,colcount2,score=0;
    //Displayable d;
    protected MainMenu(MainMidlet midlet)
    {

    super("welcome to the game");
    theMidlet =midlet;
    try{ 
    Image mi=Image.createImage(getWidth(), getHeight()-19);
    Image i=Image.createImage("/com/game/ball/kadu-logo-arcisz.png");
    Graphics gmi=mi.getGraphics();
    gmi.drawImage(i, (getWidth()-i.getWidth())/2, (getHeight()-19-i.getHeight())/2+20, 0);
    gmi.drawString("break all blocks\nto win the game", getWidth()/6,3, 0);
    append(mi);
        }
    catch(IOException io){
        System.out.println("main image path problem");}
    
    start=new Command("start",Command.SCREEN,1);
    exit=new Command("exit",Command.EXIT,2);
    addCommand(start);
    addCommand(exit);
    setCommandListener(this);
    }
    
    public void commandAction(Command command,Displayable displayable)
    {
    if(command ==start) {startGame();}
    if(command==exit) theMidlet.close();
    }

   public void startGame()
   {   g=new GameScreen(this);Display.getDisplay(theMidlet).setCurrent(g);
       g.score=score;
       g.fb1.colcount=colcount;
       g.fb2.colcount=colcount2;//change at ball,gameScreen,mainmenu colc,colc2
   }

    public void gameOver(int method)
{   Ball.chances=3;colcount=0;score=0;colcount2=0;
    String s;
    if(method==1)s="all bricks broken!\n ur score:"+g.score; else s="Game Over!\n ur score:"+g.score;
    Alert alert = new Alert("", s, null, AlertType.INFO);
    Display.getDisplay(theMidlet).setCurrent(alert, this);
}

}
