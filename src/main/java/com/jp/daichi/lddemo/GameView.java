package com.jp.daichi.lddemo;


import com.jp.daichi.designer.interfaces.Canvas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.function.Consumer;

public class GameView extends JPanel {
    private static final String PRESSED = "pressed";
    private static final String RELEASED = "released";

    private static String pressed(String key) {
        return key+PRESSED;
    }

    private static String released(String key) {
        return key+RELEASED;
    }

    private final Canvas canvas;
    private final Player player;

    public GameView(Canvas canvas) {
        this.canvas = canvas;
        this.player = new Player();
        addKeyBindings();
        Thread gameThread = new Thread(() -> {
            long lastTime = System.currentTimeMillis();
            while (true) {
                repaint();
                long current = System.currentTimeMillis();
                double deltaTime = (current - lastTime) / 1000.0;
                if (deltaTime > 20 / 1000.0) {
                    tick();
                    player.tick(deltaTime);
                    lastTime = current;
                }
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        gameThread.start();
        canvas.addRenderer(g->player.draw(g,canvas));
    }


    private void tick() {
        Rectangle viewport = canvas.getViewport();
        float x = (float) player.getX()-viewport.width/2.0f;
        canvas.setViewport(new Rectangle(Math.round(x),viewport.y,viewport.width,viewport.height));
    }

    private long lastTime = System.currentTimeMillis();
    private int drawCount = 0;
    @Override
    protected void paintComponent(Graphics g) {
        drawCount++;
        if (System.currentTimeMillis()-lastTime >= 1000) {
            System.out.println(drawCount);
            drawCount = 0;
            lastTime = System.currentTimeMillis();
        }
        super.paintComponent(g);
        canvas.draw((Graphics2D) g,getWidth(),getHeight());
    }


    private void addKeyBindings() {
        addKeyBinding("W", KeyEvent.VK_W, e->player.UP=true, e->player.UP=false);
        addKeyBinding("A",KeyEvent.VK_A,e->player.LEFT=true,e->player.LEFT=false);
        addKeyBinding("S",KeyEvent.VK_S,e->player.DOWN=true,e->player.DOWN=false);
        addKeyBinding("D",KeyEvent.VK_D,e->player.RIGHT=true,e->player.RIGHT=false);
    }

    private void addKeyBinding(String keyName, int keyCode, Consumer<ActionEvent> onPressed, Consumer<ActionEvent> onReleased) {
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(keyCode, 0, false),pressed(keyName));
        getActionMap().put(pressed(keyName), new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onPressed.accept(e);
            }
        });
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(keyCode, 0, true),released(keyName));
        getActionMap().put(released(keyName), new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onReleased.accept(e);
            }
        });
    }
}
