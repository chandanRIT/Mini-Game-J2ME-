package com.game.ball;

import java.io.IOException;
import java.util.Random;
import javax.microedition.lcdui.*;

public final class GameScreen extends Canvas implements Runnable,CommandListener
{
    Bat bt;Ball bal; 
    private final int brickpower=1;int mode=1;
    private int btdir=3;//bat direction
    //private int btop,blft,brit,bbot,bttop,btlft,btrit,btbot;
    private long lastCycleTime;
    //private Vector actorList;
    boolean running=true;
    //private int cps=0;
private int cyclesThisSecond=0;
private long lastCPSTime=0;
MainMenu mmenu;
private MovingBrick mbrick;
FixedBricks fb1,fb2;
private static final int MAX_CPS = 50;
private static final int MS_PER_FRAME = 1000 / MAX_CPS;
private Image offScreenBuffer;
int score=0;
private int min=20,sec=30,method=3;
int pause=1;
private Command newGame,exit,yes,no,pauseCom;
//private Image pauseImage;
private Graphics pGrafics;

public GameScreen(MainMenu mm)
{
mmenu = mm;
initResources();Thread t1;
t1=new Thread(this);
t1.start();
}

private void initResources()
{   newGame = new Command("new Game", Command.SCREEN, 1);
    exit = new Command("exit", Command.EXIT, 2);
    pauseCom=new Command("pause",Command.OK,3);
    addCommand(newGame);addCommand(exit);addCommand(pauseCom);setCommandListener(this);
        /*try
        {
            pauseImage = Image.createImage("/com/game/ball/pause.png");
        } catch (IOException ex) {
            System.out.println("image load problem in pauseImage");
        }*/
    int angleArr[]=new int[]{140,25,52,72,115,155};
    //if changing the brick positions , change also at fixedvrick class actorwidth
    int brickpos1[]=new int[]{2,getWidth()/6,getWidth()/6*2,getWidth()/6*3,getWidth()/6*4,((getWidth()/6)*5)-2};
    int vpos[]=new int[]{20,40,60,80,30,70};
    
    Random r=new Random();

    //System.out.println("random values:"+r.nextInt(6)+","+r.nextInt(6)+angle);
    offScreenBuffer = Image.createImage(getWidth(), getHeight());
    bt=new Bat(this, (this.getWidth()/2-24), getHeight()-11);
    bal=new Ball(this, this.getWidth()/2-12, getHeight()-11-18,angleArr[r.nextInt(6)]);
    mbrick=new MovingBrick(this,20, this.getHeight()*2/4+40, 2);//speed b/n 1 to 5
    fb1=new FixedBricks(this,brickpos1[r.nextInt(6)], getHeight()/6);
    fb2=new FixedBricks(this,brickpos1[r.nextInt(6)], getHeight()/5+vpos[r.nextInt(6)]);
    fb1.colcount=mmenu.colcount;fb2.colcount=mmenu.colcount2;

}

public void commandAction(Command command,Displayable displayable)
{
    if(command==pauseCom)
    {
    pause=~pause;System.out.println("value of pause:"+pause); repaint();
    }
    else if (command == newGame)
    {   yes=new Command("yes", Command.OK, 1);no=new Command("no", Command.SCREEN, 2);
        Alert alert=new Alert("are u sure ?");
        alert.addCommand(yes);alert.addCommand(no);
        alert.setCommandListener(this);
         pause=~pause;
        Display.getDisplay(mmenu.theMidlet).setCurrent(alert);
       // mode=0;running=false;mmenu.startGame();
      System.out.println("inside newgame");

    }
 else if(command == exit) {
        running = false;
        mode = 0;
        mmenu.gameOver(0);
    }
 else if (command == no) {pause=~pause;Display.getDisplay(mmenu.theMidlet).setCurrent(this);System.out.println("inside no");
    }
 else if (command == yes) {
        //pause=~pause;
        Ball.chances=3;
        mmenu.colcount=0;
        mmenu.colcount2=0;
        mmenu.score=0;
        mode = 0;
        running = false;
        mmenu.startGame();
        System.out.println("inside yes");
    }
}

public void run()
{
while(running)
    {
long cycleStartTime = System.currentTimeMillis();
if(pause==0){cycle();
repaint();}
if (System.currentTimeMillis() - lastCPSTime > 1000)
{//System.out.println("time:"+min+":"+sec+"  cps = "+cps);
    
    lastCPSTime=System.currentTimeMillis();
    if(min==0 && sec==0){running=false;method=1;}
    if(sec==0) {sec=59;min--;}
    else sec--;
    //cps = cyclesThisSecond;
    cyclesThisSecond = 0;
}   else
    cyclesThisSecond++;
    long timeSinceStart = (cycleStartTime - System.currentTimeMillis());
    if (timeSinceStart < MS_PER_FRAME)
        {
        try
        {
        Thread.sleep(MS_PER_FRAME - timeSinceStart);
        }
    catch(java.lang.InterruptedException e){ }
        }
    }
    if(mode==1)mmenu.gameOver(method);
}


protected void paint(Graphics graphics)
{
renderWorld();//pGrafics.
if(pause==-1)// System.out.println("inside pas1");
    //pGrafics.drawImage(pauseImage, (getWidth()-pauseImage.getWidth())/2, (getHeight()-pauseImage.getHeight())/2, 0);
    {
     pGrafics.setFont(Font.getFont(Font.FACE_MONOSPACE, Font.STYLE_BOLD, Font.SIZE_LARGE));
     pGrafics.setColor(0,0,0);
     pGrafics.drawString("Game Paused", getWidth()/5, getHeight()/2-7, 0);
    }
    graphics.drawImage(offScreenBuffer, 0, 0, Graphics.LEFT | Graphics.TOP);
}

protected void cycle()
{
if (lastCycleTime > 0)
    {
                long msSinceLastCycle = System.currentTimeMillis() - lastCycleTime;
                bt.collides(bal);mbrick.collides(bal);
                if(fb1.colcount<brickpower)
                fb1.collides(bal);
                if(fb1.colcount==brickpower) mmenu.colcount=fb1.colcount;
                if(fb2.colcount<brickpower+3)
                fb2.collides(bal);//score++;}
                if(fb2.colcount==brickpower+3)mmenu.colcount2=fb2.colcount;
                if(fb1.colcount>=brickpower&&fb2.colcount>=brickpower+3){running=false;method=1;}
                bal.cycle(msSinceLastCycle);
                bt.cycle(btdir);//bt dir=bat direction
                mbrick.cycle(msSinceLastCycle);

    }
                lastCycleTime = System.currentTimeMillis();
}

private void renderWorld()
{
Graphics osg = offScreenBuffer.getGraphics();
osg.setColor(253, 250, 195);
if(pause==-1)osg.setColor(192,192,192);
osg.fillRect(0, 0, getWidth(),getHeight());
if(fb1.colcount<brickpower)fb1.render(osg);
if(fb2.colcount<brickpower+3)fb2.render(osg);
bt.render(osg);
bal.render(osg,"/com/game/ball/favicon.png");
mbrick.render(osg);

if(Ball.chances>0)
{
    int pos=4;
    for(int loop=0;loop<Ball.chances;loop++)
    { // osg.fillArc(pos, 3, 18, 18, 0, 360);
        Image im = null;
                try {
                    im = Image.createImage("/com/game/ball/favicon.png");
                    } catch (IOException ex)
                    {System.out.println("image loading problem in life image ");
                    }
        osg.drawImage(im, pos, 3, 0);
        pos+=20;

    }
}
else {
        osg.drawString("chances::over" , 2, 0, 0);
        osg.setFont(Font.getFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_LARGE));
        osg.setColor(0,0,0);
        osg.drawString("Game Over!", getWidth()/4, getHeight()/2-7, 0);
    
    }
osg.drawString("pts:"+score, getWidth()/12*9, 0, 0);
pGrafics=osg;

}

public void keyPressed(int keycode)
      {
        switch(getGameAction(keycode))
        {   //default:pause=0;
            case UP:
            break;

            case DOWN:
            break;

            case LEFT:pause=0; btdir=0;//System.out.println("left");
            break;

            case RIGHT:pause=0;btdir=1;//System.out.println("right");
            break;

            }
      }

public void keyReleased(int keycode)
      {
        switch(getGameAction(keycode))
        {
            case UP:
            break;

            case DOWN:
            break;

            case LEFT:btdir=3;//System.out.println("left release");
            break;

            case RIGHT:btdir=3;//System.out.println("right release");
            break;

            }
      }


}
