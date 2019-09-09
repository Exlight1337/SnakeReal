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
    private final int SIZE = 320;
    private final int DOT_SIZE = 16;//размер в пикселях, сколько будет занимать одна ячейка змейки и воппера
    private final int ALL_DOTS = 400;//Всего игровых единиц
    private Image dot;//сама змейка
    private Image apple;//очки
    private int appleX;//позиция воппера
    private int appleY;
    private int[] x = new int[ALL_DOTS];//Массивы для хранения положения змейки
    private int[] y = new int[ALL_DOTS];
    private int dots;//размер змейки
    private Timer timer;//таймер
    private boolean left = false; //Отвечают за текущее направление движения змейки
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;
    private boolean inGame = true;//Мы еще в игре??

    //Игровое поле
    public GameField(){
        setBackground(Color.black);
        loadImages();
        initGame();
        addKeyListener(new FieldKeyListener());
        setFocusable(true);//фокус на игровом поле

    }
    //метод, который инициализирует начало игры
    public void initGame(){
        dots = 3;//Начальное кол-во точек
        for (int i = 0; i < dots; i++) {
            x[i] = 48 - i*DOT_SIZE;//Начальное значения для X
            y[i] = 48;
        }
        timer = new Timer(250,this);//250 мсек с такой частотой он будет тикать. this - филд отвечает за
        timer.start();//обработку каждого вызова таймера
        createApple();
    }

    public void createApple(){
        appleX = new Random().nextInt(20)*DOT_SIZE;//20 позиций от 0 до 19
        appleY = new Random().nextInt(20)*DOT_SIZE;
    }

    public void loadImages(){
        ImageIcon iia = new ImageIcon("Воппер.png");
        apple = iia.getImage();
        ImageIcon iid = new ImageIcon("витлик.png");
        dot = iid.getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(inGame){
            g.drawImage(apple,appleX,appleY,this);
            for (int i = 0; i < dots; i++) {
                g.drawImage(dot,x[i],y[i],this);
            }
        } else{
            String str = "Game Over";
            //Font f = new Font("Arial",14,Font.BOLD);
            g.setColor(Color.white);
            // g.setFont(f);
            g.drawString(str,125,SIZE/2);
        }
    }
    //логическое перестановка точек, сдвигаться в массиве x и y
    public void move(){
        for (int i = dots; i > 0; i--) {
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        if(left){
            x[0] -= DOT_SIZE;
        }
        if(right){
            x[0] += DOT_SIZE;
        } if(up){
            y[0] -= DOT_SIZE;
        } if(down){
            y[0] += DOT_SIZE;
        }
    }

    public void checkApple(){
        if(x[0] == appleX && y[0] == appleY){
            dots++;
            createApple();
        }
    }

    public void checkCollisions(){
        for (int i = dots; i >0 ; i--) {
            if(i>4 && x[0] == x[i] && y[0] == y[i]){
                inGame = false;
            }
        }

        if(x[0]>SIZE){
            inGame = false;
        }
        if(x[0]<0){
            inGame = false;
        }
        if(y[0]>SIZE){
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