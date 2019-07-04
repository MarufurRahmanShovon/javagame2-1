import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.EntityBuilder;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.core.util.Consumer;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.physics.box2d.dynamics.FixtureDef;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.security.Key;
import java.util.Map;
import java.util.*;


import static com.almasb.fxgl.dsl.FXGLForKtKt.*;
import static com.almasb.fxgl.ui.FXGLUIConfig.getUIFactory;

public class MainGame extends GameApplication {

    //declaration
    private Entity startscreen, startbutton,quitbutton,startrun, background, player, coin, coin1 , coin2 , coin3 , coin4 ,coin5;
    private boolean isForward = true;
    private static boolean isStarted = false;
    private boolean isCoinOntained = false;
    private boolean isCoin1Obtained = false;
    private boolean isCoin2Obtained = false;
    private boolean isCoin3Obtained = false;
    private boolean isCoin4Obtained = false;
    private boolean isCoin5Obtained = false;



    //for initializing the game window
    @Override
    protected void initSettings(GameSettings settings) {
       /// System.out.println("ek");
        settings.setTitle("NewGame");
        settings.setWidth(1200);
        settings.setHeight(700);
        settings.setCloseConfirmation(true);
    }


    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("Coins", 0);
    }

    //for initializing the game entities
    @Override
    protected void initGame() {
        //System.out.println("dui");
        startscreen = entityBuilder()
                    .at(0, 0)
                    .view("startscreen.jpg")
                    .buildAndAttach();
            startbutton = entityBuilder()
                    .at(650,140)
                    .view("startbutton.png")
                    .buildAndAttach();
           quitbutton = entityBuilder()
                .at(-55,140)
                .view("quitbutton.png")
                .buildAndAttach();
           startrun = entityBuilder()
                   .at(450,420)
                   .view("player2.gif")
                   .buildAndAttach();

    }
    void yo()
    {
        background = FXGL.entityBuilder()
                .at(0, 0)
                .view("background.jpg")
                .buildAndAttach();

        player = FXGL.entityBuilder()
                .at(450, 560 - 138)
                .view("player2_still.png")
                .buildAndAttach();

        coin = entityBuilder()
                .at(800, 530)
                .viewWithBBox(new Circle(15, Color.ORANGE))
                .with(new CollidableComponent(true))
                .buildAndAttach();

        coin1 = entityBuilder()
                .at(1097, 400)
                .viewWithBBox(new Circle(15, Color.ORANGE))
                .with(new CollidableComponent(true))
                .buildAndAttach();

        coin2 = entityBuilder()
                .at(1705, 480)
                .viewWithBBox(new Circle(15, Color.RED))
                .with(new CollidableComponent(true))
                .buildAndAttach();

        coin3 = entityBuilder()
                .at(2270, 410)
                .viewWithBBox(new Circle(15, Color.ORANGE))
                .with(new CollidableComponent(true))
                .buildAndAttach();

        coin4 = entityBuilder()
                .at(2930, 460)
                .viewWithBBox(new Circle(15, Color.RED))
                .with(new CollidableComponent(true))
                .buildAndAttach();
        coin5 = entityBuilder()
                .at(3280, 300)
                .viewWithBBox(new Circle(15, Color.RED))
                .with(new CollidableComponent(true))
                .buildAndAttach();
    }
    boolean right_bound(){

        return (( background.getX()!=-56 && background.getX()!=-455 && background.getX()!=-510  && background.getX()!=-2296 && background.getX()!=-2612));
    }

    boolean right_bound2(){

        return ( background.getX()!=-1601 && background.getX()!=-1607 && background.getX()!=-1656 && background.getX()!=-2619 );
    }

    boolean down_function1(){ // for object 1
        return ((background.getX()<=-191 && background.getX()>=-400 && (player.getY() == 358 ||player.getY() == 366 )) );
    }

    boolean down_function2(){ // for object 2
        return  (background.getX()==-601||background.getX()==-653);
    }

    boolean down_function3(){ // for object 2
        return  (background.getX()==-1801||background.getX()==-1803||background.getX()==-1851);
    }

    //for initializing the user input
    @Override
    protected void initInput() {
        Input input = FXGL.getInput();
        System.out.println("tin");
        GameSettings s=new GameSettings();
        if (!isStarted) {
            input.addAction(new UserAction("Click") {
                @Override
                protected void onActionBegin() {
                    if (input.getMousePositionUI().getX() >= 815 && input.getMousePositionUI().getX() <= 1090
                            && input.getMousePositionUI().getY() >= 330 && input.getMousePositionUI().getY() <= 400) {
                        isStarted = true;
                        yo();
                        initUI();
                        startbutton.removeFromWorld();
                        startscreen.removeFromWorld();
                    }
                    /*else if (input.getMousePositionUI().getX() >= 110 && input.getMousePositionUI().getX() <= 350
                            && input.getMousePositionUI().getY() >= 330 && input.getMousePositionUI().getY() <= 400){

                        s.setCloseConfirmation(false);
                        System.out.println(3);
                    }*/

                }
            }, MouseButton.PRIMARY);
        }

        input.addAction(new UserAction("Go right") {
            @Override
            protected void onAction() {
                isForward = true;
                player.setView(FXGL.getAssetLoader().loadTexture("player2.gif")); //changing the player image

                System.out.println(player.getX());

                if( background.getX() <= -3200 && player.getX() < 1000){
                    player.translateX(4);
                }

                if(down_function1()){
                    player.translateY(+53);
                }

                if(down_function2()){
                    player.translateY(+54);
                }

                if(down_function3()){
                    player.translateY(+54);
                }
                /*boolean right_bound(){

                    return (( background.getX()!=-56 && background.getX()!=-460 && background.getX()!=-460 && background.getX()!=-2296 && background.getX()!=-2612));
                }*/

                System.out.println(player.getY() + " "+ background.getX());
                if (isStarted && background.getX() > -3200 && (right_bound() ) && (right_bound2()) ) { //right bound for background
                    background.translateX(-4);
                    coin.translateX(-4);
                    coin1.translateX(-4);
                    coin2.translateX(-4);
                    coin3.translateX(-4);
                    coin4.translateX(-4);
                    coin5.translateX(-4);

                }

                if (!isCoinOntained
                        && coin.getCenter().getX() - player.getCenter().getX() <= 150
                        && coin.getCenter().getX() - player.getCenter().getX() >= -30) {
                    FXGL.getGameState().increment("Coins", +150); //coin is obtained
                    isCoinOntained = true;
                    coin.removeFromWorld();
                }

                if (!isCoin1Obtained
                        && coin1.getCenter().getX() - player.getCenter().getX() <= 150
                        && coin1.getCenter().getY() - player.getCenter().getY() >= -20) {
                    FXGL.getGameState().increment("Coins", +150); //coin1 is obtained
                    isCoin1Obtained = true;
                    coin1.removeFromWorld();
                }

                if (!isCoin2Obtained
                        && coin2.getCenter().getX() - player.getCenter().getX() <= 150
                        && coin2.getCenter().getX() - player.getCenter().getX() >= -50) {
                    FXGL.getGameState().increment("Coins", +300); //coin1 is obtained
                    isCoin2Obtained = true;
                    coin2.removeFromWorld();
                }

                if (!isCoin3Obtained
                        && coin3.getCenter().getX() - player.getCenter().getX() <= 150
                        && coin3.getCenter().getX() - player.getCenter().getX() >= -50) {
                    FXGL.getGameState().increment("Coins", +300); //coin1 is obtained
                    isCoin3Obtained = true;
                    coin3.removeFromWorld();
                }

                if (!isCoin4Obtained
                        && coin4.getCenter().getX() - player.getCenter().getX() <= 150
                        && coin4.getCenter().getX() - player.getCenter().getX() >= -50) {
                    FXGL.getGameState().increment("Coins", +150); //coin1 is obtained
                    isCoin4Obtained = true;
                    coin4.removeFromWorld();
                }

                if (!isCoin5Obtained
                        && coin5.getCenter().getX() - player.getCenter().getX() <= 150
                        && coin5.getCenter().getX() - player.getCenter().getX() >= -50) {
                    FXGL.getGameState().increment("Coins", +300); //coin1 is obtained
                    isCoin5Obtained = true;
                    coin5.removeFromWorld();
                }
            }

            @Override
            protected void onActionEnd() {
                player.setView(FXGL.getAssetLoader().loadTexture("player2_still.png")); //changing the player image
            }
        }, KeyCode.RIGHT);

        input.addAction(new UserAction("Go left") {
            @Override
            protected void onAction() {
                System.out.println("4");
                isForward = false;
                player.setView(FXGL.getAssetLoader().loadTexture("player2_flipped.gif")); //changing the player image
                if (player.getX() > 0) { //right bound for player
                    // player.translateX(-2);
                }
                if (isStarted && background.getX() < 0) { //right bound for background
                    background.translateX(2);
                    coin.translateX(2);
                    coin1.translateX(2);
                    coin2.translateX(2);
                    coin3.translateX(2);
                    coin4.translateX(2);
                    coin5.translateX(2);
                }
            }

            @Override
            protected void onActionEnd() {
                System.out.println("5");
                player.setView(FXGL.getAssetLoader().loadTexture("player2_still_flipped.png")); //changing the player image
            }
        }, KeyCode.LEFT);

        input.addAction(new UserAction("Jump") {
            @Override
            protected void onActionBegin() {
                if (isStarted && player.getX() < 900 && isForward) {    ////change for jump temporaly
                    for (int i = 0; i <= 200; i += 50) {
                        getMasterTimer().runOnceAfter(() -> {
                            player.translate(0, -20); //player is moving up
                            System.out.println(player.getY() + " " + background.getX());
                        }, Duration.millis(i));
                    }
                    for (int i = 200; i <= 400; i += 50) {
                        getMasterTimer().runOnceAfter(() -> {
                            player.translate(0, 20); //player is moving down
                        }, Duration.millis(i));
                    }
                } else if (player.getX() > 0 && !isForward) {
                    for (int i = 0; i <= 200; i += 50) {
                        getMasterTimer().runOnceAfter(() -> {
                            player.translate(0, -20); //player is moving up
                        }, Duration.millis(i));
                    }
                    for (int i = 200; i <= 400; i += 50) {
                        getMasterTimer().runOnceAfter(() -> {
                            player.translate(0, 20); //player is moving down
                        }, Duration.millis(i));
                    }
                }

                if (!isCoinOntained
                        && coin.getCenter().getX() - player.getCenter().getX() <= 250
                        && coin.getCenter().getX() - player.getCenter().getX() >= -30) {
                    FXGL.getGameState().increment("Coins", +3); //coin is obtained
                    isCoinOntained = true;
                    coin.removeFromWorld();
                }

                if (!isCoin1Obtained
                        && coin1.getCenter().getX() - player.getCenter().getX() <= 300
                        && coin1.getCenter().getX() - player.getCenter().getX() >= -50) {
                    FXGL.getGameState().increment("Coins", +3); //coin1 is obtained
                    isCoin1Obtained = true;
                    coin1.removeFromWorld();
                }

                if (!isCoin2Obtained
                        && coin2.getCenter().getX() - player.getCenter().getX() <= 300
                        && coin2.getCenter().getX() - player.getCenter().getX() >= -50) {
                    FXGL.getGameState().increment("Coins", +3); //coin1 is obtained
                    isCoin2Obtained = true;
                    coin2.removeFromWorld();
                }

                if (!isCoin3Obtained
                        && coin3.getCenter().getX() - player.getCenter().getX() <= 300
                        && coin3.getCenter().getX() - player.getCenter().getX() >= -50) {
                    FXGL.getGameState().increment("Coins", +3); //coin1 is obtained
                    isCoin3Obtained = true;
                    coin3.removeFromWorld();
                }

                if (!isCoin4Obtained
                        && coin4.getCenter().getX() - player.getCenter().getX() <= 300
                        && coin4.getCenter().getX() - player.getCenter().getX() >= -50) {
                    FXGL.getGameState().increment("Coins", +3); //coin1 is obtained
                    isCoin4Obtained = true;
                    coin4.removeFromWorld();
                }

                if (!isCoin5Obtained
                        && coin5.getCenter().getX() - player.getCenter().getX() <= 300
                        && coin5.getCenter().getX() - player.getCenter().getX() >= -50) {
                    FXGL.getGameState().increment("Coins", +3); //coin1 is obtained
                    isCoin5Obtained = true;
                    coin5.removeFromWorld();
                }
            }

            @Override
            protected void onAction() {

                if (background.getX() == -56) { // 1st object ar age player er jump

                    for (int i = 0; i <= 300; i += 50) {
                        getMasterTimer().runOnceAfter(() -> {
                            background.translateX(-5); //player is moving up
                            coin.translateX(-5);
                            coin1.translateX(-5);
                            coin2.translateX(-5);
                            coin3.translateX(-5);
                            coin4.translateX(-5);
                            coin5.translateX(-5);
                            player.translateY(-8);
                        }, Duration.millis(i));
                    }

                }

                if (background.getX() == -455) { // 2nd object ar age player er jump

                    for (int i = 0; i <= 300; i += 50) {
                        getMasterTimer().runOnceAfter(() -> {
                            background.translateX(-5); //player is moving up
                            coin.translateX(-5);
                            coin1.translateX(-5);
                            coin2.translateX(-5);
                            coin3.translateX(-5);
                            coin4.translateX(-5);
                            coin5.translateX(-5);
                            player.translateY(-7);
                        }, Duration.millis(i));
                    }

                }

                if (background.getX() == -510) { // 2nd object ar upor player er jump

                    for (int i = 0; i <= 300; i += 50) {
                        getMasterTimer().runOnceAfter(() -> {
                            background.translateX(-5); //player is moving up
                            coin.translateX(-5);
                            coin1.translateX(-5);
                            coin2.translateX(-5);
                            coin3.translateX(-5);
                            coin4.translateX(-5);
                            coin5.translateX(-5);
                            player.translateY(-8);
                        }, Duration.millis(i));
                    }

                }

                if ( background.getX()==-1601 || background.getX()==-1607) { // 3rd object a player er 1st jump
                    for (int i = 0; i <= 300; i += 50) {
                        getMasterTimer().runOnceAfter(() -> {
                            background.translateX(-5); //player is moving up
                            coin.translateX(-5);
                            coin1.translateX(-5);
                            coin2.translateX(-5);
                            coin3.translateX(-5);
                            coin4.translateX(-5);
                            coin5.translateX(-5);
                            player.translateY(-7);
                        }, Duration.millis(i));
                    }

                }


                if ( background.getX()==-1656) { // 3rd object a player er 2nd jump
                    for (int i = 0; i <= 300; i += 50) {
                        getMasterTimer().runOnceAfter(() -> {
                            background.translateX(-5); //player is moving up
                            coin.translateX(-5);
                            coin1.translateX(-5);
                            coin2.translateX(-5);
                            coin3.translateX(-5);
                            coin4.translateX(-5);
                            coin5.translateX(-5);
                            player.translateY(-8);
                        }, Duration.millis(i));
                    }

                }

                if (background.getX() <= -2275 && background.getX() >= -2331 ) { // 1st platform rise a player er jump
                    for (int i = 0; i <= 150; i += 50) {
                        getMasterTimer().runOnceAfter(() -> {
                            background.translateX(-2); //player is moving up
                            coin.translateX(-2);
                            coin1.translateX(-2);
                            coin2.translateX(-2);
                            coin3.translateX(-2);
                            coin4.translateX(-2);
                            coin5.translateX(-2);
                            player.translateY(-2.75);
                        }, Duration.millis(i));
                    }
                }

                if (background.getX() <= -2423 && background.getX() >= -2483 ) { // 2nd platform rise a player er jump
                    for (int i = 0; i <= 150; i += 50) {
                        getMasterTimer().runOnceAfter(() -> {
                            background.translateX(-2); //player is moving up
                            coin.translateX(-2);
                            coin1.translateX(-2);
                            coin2.translateX(-2);
                            coin3.translateX(-2);
                            coin4.translateX(-2);
                            coin5.translateX(-2);
                            player.translateY(-2.5);
                        }, Duration.millis(i));
                    }
                }

                if (background.getX()==-2619 ) { // Last obstacle 1st jump
                    for (int i = 0; i <= 150; i += 50) {
                        getMasterTimer().runOnceAfter(() -> {
                            background.translateX(-5); //player is moving up
                            coin.translateX(-5);
                            coin1.translateX(-5);
                            coin2.translateX(-5);
                            coin3.translateX(-5);
                            coin4.translateX(-5);
                            coin5.translateX(-5);
                            player.translateY(-7);
                        }, Duration.millis(i));
                    }
                }

            }
        }, KeyCode.SPACE);
    }






//for keep tracking of how many coins have gained


    //for displaying additional elements like score etc.
    @Override
    protected void initUI() {
        if(isStarted) {
            Text textScore = new Text();
            textScore.setTranslateX(950);
            textScore.setTranslateY(50);
            textScore.setFont(Font.font("Agency FB", 50));
            textScore.setFill(Color.RED);
            textScore.setText("Score:");


            Text textPixels = new Text();
            textPixels.setTranslateX(1050);
            textPixels.setTranslateY(50);
            textPixels.setFont(Font.font("Agency FB", 50));
            textPixels.setFill(Color.RED);
            textPixels.setText("Score:");

            textPixels.textProperty().bind(FXGL.getGameState().intProperty("Coins").asString());
            System.out.println(textPixels.getText().toString());
            FXGL.getGameScene().addUINode(textScore);
            FXGL.getGameScene().addUINode(textPixels);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}