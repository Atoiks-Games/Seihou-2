package org.atoiks.games.framework;

import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Frame extends AbstractFrame<JFrame> {

    private final JPanel canvas = new JPanel() {
        
        @Override
        protected void paintComponent(final Graphics g) {
            super.paintComponent(g);
            sceneMgr.renderCurrentScene(g);
        }
    };

    private final JFrame frame;

    public Frame(FrameInfo info) {
        super(info.fps, new SceneManager(info.scenes));
        frame = new JFrame(info.titleName);

        frame.setContentPane(canvas);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(info.resizable);
        canvas.setPreferredSize(new java.awt.Dimension(info.width, info.height));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.addKeyListener(sceneMgr.keyboard());
    }

    @Override
    protected int getWidth() {
        return frame.getWidth();
    }

    @Override
    protected int getHeight() {
        return frame.getHeight();
    }

    @Override
    protected void renderGame() {
        canvas.repaint();
        java.awt.Toolkit.getDefaultToolkit().sync();
    }

    @Override
    public void setSize(int width, int height) {
        canvas.setPreferredSize(new java.awt.Dimension(width, height));
        frame.pack();
    }

    @Override
    public void setTitle(String title) {
        frame.setTitle(title);
    }

    @Override
    public JFrame getRawFrame() {
        return frame;
    }

    @Override
    public void init() {
        super.init();
        frame.setVisible(true);
    }

    @Override
    public void close() {
        super.close();
        frame.dispose();
    }
}