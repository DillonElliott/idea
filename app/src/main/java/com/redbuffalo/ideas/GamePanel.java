package com.redbuffalo.ideas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    private GameThread gameThread;
    private Background bg;
    public static final int WIDTH = 856; //hard coded but can set on create with the bg image
    public static final int HEIGHT = 480; //hard coded but can set on create with the bg image
    public static final int MOVESPEED = -5;
    private PlayerObject player;

    public GamePanel(Context context)
    {
        super(context);

        //add the callback to the surfaceholder to intercept touch events
        getHolder().addCallback(this);
        gameThread = new GameThread(getHolder(), this);
        //make gamePanel focusable to handle events


    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
    {

    }
    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {
        boolean retry = true;
        int counter =0;
        while (retry && counter < 500)
        {
            try {
                gameThread.setRunning(false);
                gameThread.join();
                retry = false;
            }catch (InterruptedException e){ e.printStackTrace();}
            counter ++;
        }
    }
    @Override
    public void surfaceCreated (SurfaceHolder holder)
    {
        //instantiate background
        bg = new Background(BitmapFactory.decodeResource(getResources(),R.drawable.grassbg1));
        player = new PlayerObject(BitmapFactory.decodeResource(getResources(), R.drawable.helicopter), 65, 25,3);


        //start thread (game loop)
        gameThread.setRunning(true);
        gameThread.start();
    }
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if(event.getAction()==MotionEvent.ACTION_DOWN) {
            if(!player.getPlaying())
            {
                player.setPlaying(true);
            }
            else
            {
                player.setUp(true);
            }
            return true;
        }
        if(event.getAction()  ==MotionEvent.ACTION_UP )
        {
            player.setUp(false);
        }
        return super.onTouchEvent(event);
    }

    public void update()
    {
        if(player.getPlaying()) {
            bg.update();
            player.update();
        }
    }
    @Override
    public void draw(Canvas canvas)
    {
        final float scaleFactorX = getWidth()/(WIDTH*1.f);
        final float scaleFactorY=getHeight()/(HEIGHT*1.f);
        if(canvas!=null) {
            final int savedState = canvas.save();
            canvas.scale(scaleFactorX,scaleFactorY);
            bg.draw(canvas);
            player.draw(canvas);
            canvas.restoreToCount(savedState);// this way we only scale from the saved state
        }
    }
}
