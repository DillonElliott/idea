package com.redbuffalo.ideas;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GameThread extends Thread
{
    private int MAX_FPS=60;
    private double averageFPS;
    private SurfaceHolder surfaceHolder;
    private GamePanel gamePanel;
    private boolean running;
    public static Canvas canvas;

    public GameThread(SurfaceHolder surfaceHolder, GamePanel gamePanel)
    {
        super();
        this.surfaceHolder=surfaceHolder;
        this.gamePanel=gamePanel;

    }
    @Override
    public void run()
    {
        long startTime;
        long timeMillis;
        long totatlTime =0;
        long waitTime;
        int frameCount =0;
        long targetTime = 1000/MAX_FPS; //this is how fast the frame should update;

        while(running)
        {
            canvas=null;
            startTime = System.nanoTime();

            //try to lock canvas
            try{
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder)
                {
                    this.gamePanel.update();
                    this.gamePanel.draw(canvas);
                }
            }catch (Exception e)
            {
                System.out.println("Error exception: " + e);
            }
            finally {
              if(canvas!=null)
              {
                  try{
                      surfaceHolder.unlockCanvasAndPost(canvas);
                  }catch (Exception e){e.printStackTrace();}
              }
            }

            timeMillis = (System.nanoTime()-startTime)/1000000;
            waitTime = targetTime-timeMillis;

            try{
                this.sleep(waitTime);
            }catch(Exception e)
            {
                System.out.println("Error exception: " + e);
            }

            totatlTime +=System.nanoTime() - startTime;
            frameCount++;
            if(frameCount == MAX_FPS)
            {
                averageFPS = 1000/((totatlTime/frameCount)/1000000);
                frameCount=0;
                totatlTime=0;
                System.out.println("FPS: "+averageFPS );
            }
        }
    }
    public void setRunning(boolean running)
    {
        this.running = running;
    }
}
