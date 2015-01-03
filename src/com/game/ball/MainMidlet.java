package com.game.ball;
import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;


public final class MainMidlet extends MIDlet
{
    private MainMenu menu;


    public MainMidlet()
{
        menu = new MainMenu(this);
}

public void close()
{
try
{
destroyApp(true);
notifyDestroyed();
}
catch (MIDletStateChangeException e)
{ }
}

public void startApp() throws MIDletStateChangeException
{
Display.getDisplay(this).setCurrent(menu);
}
public void pauseApp()
{
}

public void destroyApp(boolean unconditional) throws MIDletStateChangeException
{
}
}