import javax.swing.*;
public class snake {
    public static void main(String[] args) {
        int boardwidth=600;
        int boardheight=600;
        

        JFrame frame = new JFrame("snake");
        frame.setVisible(true);
        frame.setSize(boardwidth, boardheight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        SnakeGame snakegame = new SnakeGame(boardwidth, boardheight);
        frame.add(snakegame);
        frame.pack();
        snakegame.requestFocus();
    }
}
