package ui;

import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import sun.audio.AudioPlayer;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class mainController implements Initializable {

    //Fxml
    @FXML
    private ImageView img_dice;
    @FXML
    private Label lbl_int, lbl_cond1, lbl_cond2, lbl_cond3, lbl_cond4, lbl_rest;
    @FXML
    private Circle circle_main;
    @FXML
    private Button btt_stop, btt_reset, btt_10time, btt_customspin;
    @FXML
    private Spinner<Integer> spinner_sub, spinner_main;

    //Default
    private RotateTransition rotateTransition;
    private TranslateTransition translateTransition;
    private int repeat = 0;

    private void checkrepeat(){
        repeat --;
        if(repeat > 0){
            roll();
        }else if (repeat < 0){
            repeat = 0;
        }
        lbl_rest.setText("남은횟수: "+repeat+"회");
    }

    private void displaynum(){
        lbl_int.setVisible(true);
        int rand = new Random().nextInt(8)+1;
        lbl_int.setText(String.valueOf(rand));
        if(rand <= 2){
            lbl_cond1.setTextFill(Color.RED);
            translateTransition.setByX(-27.5);
            translateTransition.setByY(0);
            translateTransition.play();
            translateTransition.setOnFinished( e -> {
                checkrepeat();
                lbl_cond1.setTextFill(Color.BLACK);
            });
        }else if(rand <= 4){
            lbl_cond2.setTextFill(Color.RED);
            translateTransition.setByX(27.5);
            translateTransition.setByY(0);
            translateTransition.play();
            translateTransition.setOnFinished( e -> {
                checkrepeat();
                lbl_cond2.setTextFill(Color.BLACK);
            });
        }else if(rand <= 6){
            lbl_cond3.setTextFill(Color.RED);
            translateTransition.setByY(-27.5);
            translateTransition.setByX(0);
            translateTransition.play();
            translateTransition.setOnFinished( e -> {
                checkrepeat();
                lbl_cond3.setTextFill(Color.BLACK);
            });
        }else{
            lbl_cond4.setTextFill(Color.RED);
            translateTransition.setByY(27.5);
            translateTransition.setByX(0);
            translateTransition.play();
            translateTransition.setOnFinished( e -> {
                checkrepeat();
                lbl_cond4.setTextFill(Color.BLACK);
            });
        }
    }

    private void roll(){
        lbl_int.setVisible(false);
        AudioInputStream audioIn;
        try {
            InputStream bufferedIn = new BufferedInputStream(getClass().getResourceAsStream("/resources/sound.wav"));
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(bufferedIn);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (Exception  e) {
            e.printStackTrace();
        }
        rotateTransition.play();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //pre
        rotateTransition = new RotateTransition(Duration.millis(800), img_dice);
        rotateTransition.setByAngle(360);
        rotateTransition.setCycleCount(1);
        rotateTransition.setInterpolator(Interpolator.LINEAR);
        rotateTransition.setOnFinished( e -> displaynum() );
        translateTransition = new TranslateTransition();
        translateTransition.setNode(circle_main);
        translateTransition.setAutoReverse(false);
        translateTransition.setCycleCount(1);
        translateTransition.setDuration(Duration.millis(1500));
        lbl_cond1.setTextFill(Color.BLACK);
        lbl_cond2.setTextFill(Color.BLACK);
        lbl_cond3.setTextFill(Color.BLACK);
        lbl_cond4.setTextFill(Color.BLACK);

        //spinner
        spinner_sub.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(500, 3000, 1500, 500));
        spinner_sub.valueProperty().addListener( e -> translateTransition.setDuration(Duration.millis(spinner_sub.getValue())) );
        spinner_main.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 20, 1));

        //Listener
        btt_customspin.setOnMouseClicked( e -> {
            if(repeat == 0){
                repeat = spinner_main.getValue();
                roll();
            }
        });
        img_dice.setOnMouseClicked( e-> {
            if(repeat == 0){
                repeat = 1;
                roll();
            }
        });
        btt_stop.setOnMouseClicked( e -> {
            if(repeat > 0){
                repeat = 0;
            }
        });
        btt_reset.setOnMouseClicked( e -> {
           circle_main.setTranslateX(0);
           circle_main.setTranslateY(0);
        });
        btt_10time.setOnMouseClicked( e-> {
            if(repeat == 0) {
                repeat = 10;
                roll();
            }
        });
    }
}

