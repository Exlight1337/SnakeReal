import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;


//панель на которой происходит основные действия игры
public class GameField extends JPanel implements ActionListener{
    //Задаю большое кол-во полей данного класа для игры(Игровые параметры)
    private final int Size = 320;
    private final int SnSc_Size = 16;//размер в пикселях, сколько будет занимать одна ячейка змейки и воппера
    private final int ALL_DOTS = 400;//Всего игровых единиц
    private Image snake;//сама змейка
    private Image vopper;//очки
    private int vopperX;//позиция воппера
    private int vopperY;
    private int[] x = new int[ALL_DOTS];//Массивы для хранения положения змейки
    private int[] y = new int[ALL_DOTS];
    private int Snake_Size;//размер змейки
    private Timer timer;//таймер
    private boolean left = false; //Отвечают за текущее направление движения змейки
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;
    private boolean inGame = true;//Мы еще в игре??

    //Игровое поле
    public GameField(){
       setBackground(Color.BLACK);
        loadImages();
        initGame();
        addKeyListener(new FieldKeyListener());
        setFocusable(true);//фокус на игровом поле

    }
    //метод, который инициализирует начало игры
    public void initGame(){
        Snake_Size = 3;//Начальное кол-во точек
        for (int i = 0; i < Snake_Size; i++) {
            x[i] = 48 - i*SnSc_Size;//Начальное значения для X
            y[i] = 48;
        }
        timer = new Timer(250,this);//250 мсек с такой частотой он будет тикать. this - филд отвечает за
        timer.start();//обработку каждого вызова таймера
        createApple();
    }

    public void createApple(){
        vopperX = new Random().nextInt(20)*SnSc_Size;//20 позиций от 0 до 19
        vopperY = new Random().nextInt(20)*SnSc_Size;
    }

    public void loadImages(){
        ImageIcon iia = new ImageIcon("Воппер.png");
        vopper = iia.getImage();
        ImageIcon iid = new ImageIcon("витлик.png");
        snake = iid.getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(inGame){
            g.drawImage(vopper,vopperX,vopperY,this);
            for (int i = 0; i < Snake_Size; i++) {
                g.drawImage(snake,x[i],y[i],this);
            }
        } else{
            String str = "Смерть";
            g.setColor(Color.white);
            g.drawString(str,125,Size/2);
        }
    }
    //логическое перестановка точек, сдвигаться в массиве x и y
    public void move(){
        for (int i = Snake_Size; i > 0; i--) {
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        if(left){
            x[0] -= SnSc_Size;
        }
        if(right){
            x[0] += SnSc_Size;
        } if(up){
            y[0] -= SnSc_Size;
        } if(down){
            y[0] += SnSc_Size;
        }
    }

    public void checkApple(){
        if(x[0] == vopperX && y[0] == vopperY){
            Snake_Size++;
            createApple();
        }
    }

    public void checkCollisions(){
        for (int i = Snake_Size; i >0 ; i--) {
            if(i>4 && x[0] == x[i] && y[0] == y[i]){
                inGame = false;
            }
        }

        if(x[0]>Size){
            inGame = false;
        }
        if(x[0]<0){
            inGame = false;
        }
        if(y[0]>Size){
            inGame = false;
        }
        if(y[0]<0){
            inGame = false;
        }
    }

    @Override //этот метод вызываться будет каждый раз, когда таймер будет проходить 250мсек
    public void actionPerformed(ActionEvent e) {
        if(inGame){
            checkApple();
            checkCollisions();
            move();

        }
        repaint();//перерисовую поле (вызывает paintcomponent)
    }

    class FieldKeyListener extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            if(key == KeyEvent.VK_LEFT && !right){
                left = true;
                up = false;
                down = false;
            }
            if(key == KeyEvent.VK_RIGHT && !left){
                right = true;
                up = false;
                down = false;
            }

            if(key == KeyEvent.VK_UP && !down){
                right = false;
                up = true;
                left = false;
            }
            if(key == KeyEvent.VK_DOWN && !up){
                right = false;
                down = true;
                left = false;
            }
        }
    }


}