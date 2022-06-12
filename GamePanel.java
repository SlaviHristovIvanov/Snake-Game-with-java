import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;
import java.util.Random;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Color;
        
import javax.swing.JPanel;

public class GamePanel extends JPanel implements ActionListener{
    
    static final int SCREEN_WIDTH = 600;  
    static final int SCREEN_HEIGHT = 600;   
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
    static final int DELAY = 75;
    final int x[] = new int[GAME_UNITS]; //this array is going to hold all the array for x coodinates including the head of the snake
    final int y[] = new int[GAME_UNITS]; //this array is going to hold all the array for y coodinates
    int bodyParts = 6;
    int applesEaten;
    int appleX;
    int appleY;      
    char direction = 'R';
    boolean running = false; 
    Timer timer;
    Random random;
    
    GamePanel(){
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }
    
    public void startGame(){
       newApple();
       running = true;
       timer = new Timer(DELAY, this);
       timer.start();
    }
    
    public void paintComponent(Graphics g){
        //each item in this game is going to have a demension of 25 px for teh width and th height
        super.paintComponent(g);
        draw(g);
    }  
    
    public void draw(Graphics g){
        //with this for loop i'm going to draw lines on the map
        if(running){
           
           g.setColor(Color.red);
           g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
        
           for(int i = 0; i < bodyParts; i++){
               if(i == 0){
                   g.setColor(Color.green);
                   g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);   
               }
               else{  
                   g.setColor(new Color(45,180,0));
                   g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
               }
            }     
           //score  
           g.setColor(Color.red);
           g.setFont(new Font("Ink Free", Font.BOLD, 40));  //Font metricks are usefill for lean up text in the center of the screen
           FontMetrics metrics = getFontMetrics(g.getFont());
           g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: "+ applesEaten))/2, g.getFont().getSize()); 
         }
        else{
            gameOver(g);  //g is my graphics that we are reciving with this Premitiven
        }
     }
    
    public void newApple(){
        appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
    }
    
    public void move(){
        //MOVE Method
        for(int i = bodyParts; i > 0; i--){
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        switch(direction){
            case 'U':    //this is the y coordinate the head of the snake
              y[0] = y[0] - UNIT_SIZE;
              break;
            case 'D':    
              y[0] = y[0] + UNIT_SIZE;
              break;
            case 'L':    
              x[0] = x[0] - UNIT_SIZE;
              break;
            case 'R':    
              x[0] = x[0] + UNIT_SIZE;
              break;
        }
    }
    
    public void checkApple(){    //x[0] is the head of the snake
          if((x[0] == appleX) && (y[0] == appleY)){
              bodyParts++;
              applesEaten++;
              newApple();
          }                    
    }
    
    public void checkCollisions(){  
       //checks if head collide with body
       for(int i = bodyParts; i > 0; i--){    
          if((x[0] == x[i]) && (y[0] == y[i])){     //head of my snake
             running = false;
       }                                 
    }
     //check if head touches left border
     if(x[0] < 0){
        running = false; 
     }
     //check if head touches right border
     if(x[0] > SCREEN_WIDTH){
        running = false; 
     }   
      //check if head touches top border   
     if(y[0] < 0){
        running = false; 
     }
     //check if head touches bottom border
     if(y[0] > SCREEN_HEIGHT){
        running = false; 
     }
     
     if(!running){
         timer.stop();  
     }
 }  
    
    public void gameOver(Graphics g){
        //score
         g.setColor(Color.red);
         g.setFont(new Font("Ink Free", Font.BOLD, 40));  //Font metricks are usefill for lean up text in the center of the screen
         FontMetrics metrics1 = getFontMetrics(g.getFont());
         g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics1.stringWidth("Score: "+ applesEaten))/2, g.getFont().getSize());  
           
         //Game Over text
         g.setColor(Color.red);
         g.setFont(new Font("Ink Free", Font.BOLD, 75));  //Font metricks are usefill for lean up text in the center of the screen
         FontMetrics metrics2 = getFontMetrics(g.getFont());
         g.drawString("Game Over", (SCREEN_WIDTH - metrics2.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2); 
    }
    @Override
    public void actionPerformed(ActionEvent e){
        
        if(running){
           move();
           checkApple();
           checkCollisions();
        }
        repaint();    
    }
    //interclass
    public class MyKeyAdapter extends KeyAdapter{
        //override method
        @Override  
        public void keyPressed(KeyEvent e){
           switch(e.getKeyCode()){
           case KeyEvent.VK_LEFT:
               if(direction != 'R'){
                   direction = 'L';
               }
               break;
           case KeyEvent.VK_RIGHT:
               if(direction != 'L'){
                   direction = 'R';
               }   
               break; 
           case KeyEvent.VK_UP:
               if(direction != 'D'){
                   direction = 'U';
               }
               break;  
           case KeyEvent.VK_DOWN:
               if(direction != 'U'){
                   direction = 'D';
               }
               break; 
           } 
        }
    }
}
   
