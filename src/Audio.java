import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;


public class Audio {
    private String track; // адресс трека
    private Clip clip = null;//ссылка на объект класса
    private FloatControl volumeC = null;//контролер громкости
    private double wt; //уровень громкости


    //конструктор (адрес файла, уровень громкости)
   // public Аudio(String track, double wt) {
     //   this.track = track;
       // this.wt = wt;
    //}

    public void sound(){
        File f = new File(this.track);//переда файла в f
        //поток для записи и считывания
        AudioInputStream x = null; // объект потока пуст
        try {
            x = AudioSystem.getAudioInputStream(f);//получение нужного файла
        }catch (UnsupportedAudioFileException e){
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
        try {
            clip = AudioSystem.getClip();//Получение реализации интерфейса Clip
            clip.open(x);//загрузка звукового потока в clip
            volumeC = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

            clip.setFramePosition(0);
            clip.start();
        }catch (LineUnavailableException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    //уровень громкости
    public void setVolume(){
        if (wt < 0) wt = 0;
        if (wt > 1) wt = 1;
        float min = volumeC.getMinimum();
        float max = volumeC.getMaximum();
        volumeC.setValue((max - min) * (float)wt + min);
    }
}
