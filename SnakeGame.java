import java.awt.*;
import java.awt.event.*;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Random;
import java.util.random.*;
import javax.swing.*;

public class SnakeGame extends JPanel implements ActionListener, KeyListener {
    private class Tile{
        int x;
        int y;
        Tile(int x , int y)
        {
            this.x=x;
            this.y=y;
        }
    }
    int boardwidth;
    int boardheight;
    int tileSize=25;
    Tile snakehead;
    ArrayList<Tile> snakebody;
    boolean gameOver =false;



    Tile food;
    Random random;

    //game logic
    Timer gameLoop;
    int velocityX;
    int velocityY;


    SnakeGame(int boardwidth,int boardheight)
    {
           this.boardwidth=boardwidth;
           this.boardheight=boardheight;
           setPreferredSize(new Dimension(this.boardwidth, this.boardheight));
           setBackground(Color.BLACK);
           addKeyListener(this);
           setFocusable(true);
           snakebody= new ArrayList<Tile>();

           snakehead= new Tile(5, 5);
           food= new Tile(10,10);
           random = new Random();
           placeFood();
           gameLoop= new Timer(100, this);
           gameLoop.start();

            velocityX=0;
            velocityY=1;

    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        draw(g);

    }
    public void draw(Graphics g)
    {
        for(int i=0; i<boardwidth/tileSize; i++)
        {
            g.drawLine(i*tileSize,0, i*tileSize, boardheight);
            g.drawLine(0, i*tileSize,boardwidth, i*tileSize);

        }
        //food
        g.setColor(Color.RED);
        g.fillRect(food.x*tileSize,food.y*tileSize, tileSize, tileSize);
         //snake
        g.setColor(Color.GREEN);
        g.fillRect(snakehead.x*tileSize, snakehead.y*tileSize, tileSize, tileSize);
       //snake body
       for(int i=0; i<snakebody.size(); i++ )
       {
        Tile snakpart = snakebody.get(i);
        g.fillRect(snakpart.x*tileSize, snakpart.y*tileSize, tileSize, tileSize);
       }

       g.setFont(new Font("Arial", Font.PLAIN, 16));
       if(gameOver)
       {
        g.setColor(Color.red);
        g.drawString("Game Over"+String.valueOf(snakebody.size()), tileSize-16, tileSize);
       }
       else{
        g.drawString("Score: "+String.valueOf(snakebody.size()), tileSize-16, tileSize);
       }

    
    }
    public void placeFood()
    {
        food.x=random.nextInt(boardwidth/tileSize);
        food.y=random.nextInt(boardheight/tileSize);

    }
    public boolean collision(Tile tile1, Tile tile2)
    {
        return tile1.x== tile2.x && tile1.y== tile2.y;
    }
    public void move()
    { 
        //eat food
        if(collision(snakehead, food))
        {
            snakebody.add(new Tile(food.x, food.y));
            placeFood();
        }
        //move snake head
        for(int i=snakebody.size()-1; i>=0; i--)
        {
           Tile snakepart = snakebody.get(i);
           if(i==0)
           {
            snakepart.x = snakehead.x;
            snakepart.y= snakehead.y;
           }
           else{
            Tile prevSnakepart = snakebody.get(i-1);
            snakepart.x= prevSnakepart.x;
            snakepart.y=prevSnakepart.y;
           }
        }
       

        snakehead.x+= velocityX;
        snakehead.y+= velocityY;
        //gameover conditions
        for(int i=0; i<snakebody.size();i++)
        {
            Tile snakepart = snakebody.get(i);
            if(collision(snakehead, snakepart))
            {
                gameOver=true;
            }
        }
        if(snakehead.x*tileSize<0 || snakehead.x*tileSize>boardwidth || snakehead.y*tileSize<0 || snakehead.y*tileSize>boardheight)

        {
           gameOver=true;
        }
    }
    @Override
    public void actionPerformed(ActionEvent e)
    {
        move();
       repaint();
       if(gameOver)
       {
        gameLoop.stop();
       }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode()== KeyEvent.VK_UP && velocityY!=1)
        {
            velocityX=0;
            velocityY=-1;
        }
        else if(e.getKeyCode()==KeyEvent.VK_DOWN && velocityY!=-1)
        {
            velocityX=0;
            velocityY=1;
        }
        else if(e.getKeyCode()==KeyEvent.VK_LEFT && velocityX!=1)
        {
            velocityX=-1;
            velocityY=0;
        }
        else if(e.getKeyCode()==KeyEvent.VK_RIGHT && velocityX!=-1)
        {
            velocityX=1;
            velocityY=0;
        }
    }

    //no use
    @Override
    public void keyTyped(KeyEvent e) {
       
    }

    

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
    
}
