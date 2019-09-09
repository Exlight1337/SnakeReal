import javax.swing.*;
import java.util.Scanner;

//JFrame - основной класс swing, от которого наследуется каждый класс, который хочет быть окном.
public class Window extends JFrame {

    public Window(){
        setTitle("Змейка");//Название
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);//Нажимаем крестик - программа прекращает работу.
        setSize(320,345);//Размер окна
        setLocation(400,400);
        add(new GameField());
        setVisible(true);
    }
    //Основные настройки игры. Название, закрытие, размер, добавляется игровое поле.

    //создаем экземпляр окна
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Выбери игру: ");
        int x = in.nextInt();
        if (x == 1){
            Window win = new Window();
        }
    }
}