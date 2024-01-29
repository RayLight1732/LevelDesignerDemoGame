package com.jp.daichi.lddemo;

import com.jp.daichi.designer.interfaces.Canvas;
import com.jp.daichi.designer.interfaces.Point;

import java.awt.*;

public class Player {

    private static final double speed = 200;
    public boolean LEFT;
    public boolean RIGHT;
    public boolean UP;
    public boolean DOWN;

    private double x = 0;
    private double y = 0;

    /**
     * プレイヤーの描画を行う
     * @param g グラフィックオブジェクト
     */
    public void draw(Graphics2D g, Canvas canvas) {
        Point point = canvas.convertToScreenPosition(new Point(x-5,y-5),0);
        g.fillRect( (int) point.x(),(int) point.y(),10,10);
    }

    public void tick(double deltaTime) {
        double nextX = x;
        double nextY = y;
        if (LEFT) {
            nextX -= speed*deltaTime;
        }
        if (RIGHT) {
            nextX += speed*deltaTime;
        }
        if (UP) {
            nextY -= speed*deltaTime;
        }
        if (DOWN) {
            nextY += speed*deltaTime;
        }

        setX(Math.round(nextX));
        setY(Math.round(nextY));
    }

    /**
     * プレイヤーのx座標を設定
     * @param x プレイヤーのx座標
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * プレイヤーのy座標を設定
     * @param y プレイヤーのy座標
     */
    public void setY(double y) {
        this.y = y;
    }


    /**
     * プレイヤーのx座標を取得
     * @return プレイヤーのx座標
     */
    public double getX() {
        return x;
    }

    /**
     * プレイヤーのy座標を取得
     * @return プレイヤーのy座標
     */
    public double getY() {
        return y;
    }
}
