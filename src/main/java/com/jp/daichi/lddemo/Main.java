package com.jp.daichi.lddemo;

import com.jp.daichi.designer.ingame.manager.InGameCanvasManager;
import com.jp.daichi.designer.interfaces.Canvas;
import com.jp.daichi.designer.interfaces.manager.CanvasManager;

import javax.swing.*;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

public class Main {
    public static void main(String[] args) throws URISyntaxException {
        CanvasManager manager = new InGameCanvasManager(new File(new URI(Main.class.getResource("/Test").toString())));
        Canvas canvas = manager.getInstance();
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.add(new GameView(canvas));
        frame.setVisible(true);
    }
}