import java.awt.Graphics;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.util.*;

public class RobotSoccerSimulation extends JPanel {
    private static final long serialVersionUID = -5228718339006830546L;

    //Global variables
    private static double WIDTH = 400;
    private static double HEIGHT = 600;

    private static double PLAYER_RADIUS;
    private static double ENEMY_RADIUS;
    private static double PLAYER_SPEED;
    private static double ENEMY_SPEED;
    private static double FRICTION;

    private static Ball[] balls;

    private volatile String endMessage;

    //RobotSoccerSimulation class constructor
    public RobotSoccerSimulation(double pr, double er, double ps, double es, double f){
      this.PLAYER_RADIUS = pr;
      this.ENEMY_RADIUS = er;
      this.PLAYER_SPEED = ps;
      this.ENEMY_SPEED = es;
      this.FRICTION = f;
    }

    //Ball class
    static class Ball {
        private double x;
        private double y;
        private double radius;
        private double speed;
        private Color color;

        //Ball class constructor
        Ball(double x, double y, double radius, double speed, Color color) {
            this.x = x;
            this.y = y;
            this.radius = radius;
            this.speed = speed;
            this.color = color;
        }

        //Gives the balls direction
        void moveToward(double targetX, double targetY) {
            double dx = targetX - this.x;
            double dy = targetY - this.y;
            double v = this.speed / Math.hypot(dx, dy);
            this.x = this.x + v * dx;
            this.x = constrain(this.x + v * dx, this.radius, WIDTH - this.radius);
            this.y = constrain(this.y + v * dy, this.radius, HEIGHT - this.radius);
        }

        //Applies friction to balls
        void applyFriction() {
            this.speed = constrain(this.speed - FRICTION, 0, 1000000);
        }

        //Checks if the player is COMPLETELY inside of the goal
        boolean inside(Goal goal) {
          double dx = goal.x;
          double dy = goal.y;
          double dw = goal.w;
          double dh = goal.h;
          boolean ins = x - radius > dx - dw/2 && x + radius < dx + dw/2 && y - radius > dy - dh/2 && y + radius < dy + dh/2;
          return ins;
        }
    }

    //Goal class
    private static class Goal {
        double x = WIDTH / 2;
        double y = 0;
        double w = 100;
        double h = 100;
    }

    //Goal object
    private static Goal goal = new Goal();

    //Paint components for environment
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (var ball : balls) {
            g.setColor(ball.color);
            g.fillOval((int) (ball.x - ball.radius), (int) (ball.y - ball.radius), (int) ball.radius * 2,
                    (int) ball.radius * 2);
        }
        g.setColor(new Color(255, 255, 255, 128));
        g.fillRect((int) (goal.x - goal.w / 2), (int) (goal.y - goal.h / 2), (int) goal.w, (int) goal.h);
        if (endMessage != null) {
            g.setFont(new Font("Arial", Font.PLAIN, 50));
            g.setColor(Color.RED);
            g.drawString(endMessage, 30, (int) HEIGHT / 2);
        }
    }

    //Executes animation
    private void runTheAnimation() {
            while (endMessage == null) {
              for (var i = 0; i < balls.length; i++) {
                balls[i].applyFriction();
                balls[i].moveToward(i == 0 ? goal.x : balls[0].x, i == 0 ? goal.y : balls[0].y );
              }
              adjustIfCollisions();
              repaint();
              endSimulationIfNecessary();
            try {
              Thread.sleep(10);
          } catch (InterruptedException e) {
        }
      }
    }

    //Used for friction and moveToward method
    private static double constrain(double value, double low, double high){
      return Math.min(Math.max(low, value), high);
    }

    //Ensures that all of the balls in the environment do not overlap
    private static void adjustIfCollisions(){
      for(int b1 = 0; b1 < balls.length; b1++){
        for(int b2 = 0; b2 < balls.length; b2++){
          if(b1 != b2){
            double dx = balls[b2].x - balls[b1].x;
            double dy = balls[b2].y - balls[b1].y;
            double distance = Math.hypot(dx, dy);
            double overlap = balls[b1].radius + balls[b2].radius - distance;
            if(overlap > 0){
              double adjustX = (overlap / 2) * (dx / distance);
              double adjustY = (overlap / 2) * (dy / distance);
              balls[b1].x -= adjustX;
              balls[b1].y -= adjustY;
              balls[b2].x += adjustX;
              balls[b2].y += adjustY;
            }
          }
        }
      }
    }

    //Ensures that if the player's speed ends up to be zero or scores that the animation ends
    private void endSimulationIfNecessary(){
      if(balls[0].speed <= 0){
        endMessage = "Oh no!";
      } else if(balls[0].inside(goal)){
        endMessage = "GOOOALLLLL!!";
      }
    }

    //Executes program
    public static void main(String[] args) {
      try{
        if(args.length != 5){
          throw new IllegalArgumentException("Exactly five arguments required");
        }
        for(int i = 0; i < 5; i++){
          double temp = Double.parseDouble(args[i]);
          if(temp < 0){
            throw new IllegalArgumentException("Negative numbers are not accepted");
          }
        }
        double pr = Double.parseDouble(args[0]);
        double er = Double.parseDouble(args[1]);
        double ps = Double.parseDouble(args[2]);
        double es = Double.parseDouble(args[3]);
        double f = Double.parseDouble(args[4]);
        SwingUtilities.invokeLater(() -> {
            var panel = new RobotSoccerSimulation(pr, er, ps, es, f);
            balls = new Ball[] {
                new Ball(0.0, HEIGHT, PLAYER_RADIUS, PLAYER_SPEED, Color.BLUE),
                new Ball(WIDTH * 0.25, 40, ENEMY_RADIUS, ENEMY_SPEED, Color.RED),
                new Ball(WIDTH * 0.75, 40, ENEMY_RADIUS, ENEMY_SPEED, Color.RED),
                new Ball(WIDTH / 2, HEIGHT / 2, ENEMY_RADIUS, ENEMY_SPEED, Color.RED)
            };
            panel.setBackground(Color.GREEN.brighter());
            var frame = new JFrame("Robotic Soccer");
            frame.setSize(400, 600);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(panel, BorderLayout.CENTER);
            frame.setVisible(true);
            new Thread(() -> panel.runTheAnimation()).start();
        });
      } catch (NumberFormatException e){
        System.err.println("Arguments must all be doubles");
      } catch(IllegalArgumentException e){
        System.err.println(e.getMessage());
      }
    }
}
