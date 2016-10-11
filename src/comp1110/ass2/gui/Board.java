package comp1110.ass2.gui;

import comp1110.ass2.LinkGame;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Board extends Application{

    /* board layout */
    private static final int BOARD_WIDTH = 933;
    private static final int BOARD_HEIGHT = 700;
    private static final int GAME_HEIGHT = 350;
    private static final int SQUARE_SIZE = 100;
    private static final int SIDE = 6;
    private static final double PIECE_IMAGE_SIZE = 1.5*SQUARE_SIZE;
    private static final int BOARD_X=50;
    private static final int BOARD_Y=50;
    private static final boolean[]used_pieces=new boolean[12];
    private static final double ROW_HEIGHT = 0.5*SQUARE_SIZE * 0.8660254; // 60 degrees distance
    private static final String URI_BASE = "assets/";

    /* node groups */
    private final Group root = new Group();
    private final Group controls = new Group();
    private final Group SET_UP=new Group();
    private final Group SET_UP_pieces=new Group();
    private final ArrayList<String>pieces = new ArrayList<>();

    /** media assets used to play and stop background music
     * @author Wei Wei
     */
    String musicFile = "Misty-Bog.mp3";
    File file = new File(musicFile);
    Media sound = new Media(file.toURI().toString());
    MediaPlayer mediaPlayer = new MediaPlayer(sound);
    private boolean loopPlaying = false;

    /* time count
    yuqiang li
     */
    private Timeline timeline;
    private Label clock = new Label();
    private int secondCount = 0;
    private Duration timer = Duration.ZERO;

    public VBox timer(double vBoxX, double vBoxY){
        //clock.textProperty().bind(secondCount);
        clock.setText("0");
        clock.setStyle("-fx-font-size: 3.5em");

        clock.setTextFill(Color.NAVY);

        Button button = new Button();
        button.setText("START");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Starting_placements(Start);
                button.setText("RESTART");
                if (timeline != null){
                    Starting_placements(Start);
                    //timer = Duration.ZERO;
                    secondCount = 0;
                }
                else {
                    timeline = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            Duration duration = ((KeyFrame) event.getSource()).getTime();
                            //timer = timer.add(duration);
                            secondCount ++;

                            clock.setText("" + secondCount);
                        }
                    }));
                    timeline.setCycleCount(Timeline.INDEFINITE);
                    timeline.play();
                }
            }
        });
        VBox vBox = new VBox();
        vBox.setLayoutX(vBoxX);
        vBox.setLayoutY(vBoxY);
        vBox.getChildren().addAll(button, clock);
        return vBox;
    }

    /**
     * Set up the background music sound loop to by click the music button
     * @author Wei Wei,adapted from the board class code of assignment 1
     */
    private void setUpSoundLoop()
    {
        try{
            mediaPlayer.setOnEndOfMedia(new Runnable()
            {
                public void run()
                {
                    mediaPlayer.seek(Duration.ZERO);
                }
            });

            mediaPlayer.play();
        }
        catch(Exception e)
        {
            System.err.println(e.getStackTrace());
        }
    }

    /**
     * Turn the sound loop on or off
     * @author Wei Wei,adapted from the board class code of assignment 1
     */
    private void toggleSoundLoop()
    {
        if(loopPlaying) mediaPlayer.stop();
        else mediaPlayer.play();

        loopPlaying = !loopPlaying;
    }

    /* message on completion */
    private final Text competionText = new Text("Well Done!");

    /* pegs on board */
    private final ArrayList<Peg>pegs=new ArrayList<>();

    /**
     * An inner class that represents transparent pieces used in the game.
     * Each of these is a visual representaton of an underlying piece.
     * @author Lei Huang,adapted from the board class code of assignment 1
     */
    class FXPiece extends ImageView {
        char piece;

        /**
         * Construct a particular playing piece
         *
         * @author Lei Huang,adapted from the board class code of assignment 1
         * @param piece The letter representing the piece to be created.
         */
        FXPiece(char piece) {
            if (!(piece >= 'A' && piece <= 'L')) {
                throw new IllegalArgumentException("Bad piece: \"" + piece + "\"");
            }
            if (!used_pieces[piece - 'A']) {
            setImage(new Image(Board.class.getResource(URI_BASE + piece + ".png").toString()));
            this.piece = piece;
            setFitHeight(PIECE_IMAGE_SIZE);
            setFitWidth(PIECE_IMAGE_SIZE);
        }
        }

        /**
         * Construct a particular playing piece at a particular place on the
         * board at a given orientation.
         *
         * @author Lei Huang,adapted from the board class code of assignment 1
         * @param position A three-character string describing
         *                 - the place the piece is to be located ('A' - 'X'),
         *                 - the piece ('A' - 'L'), and
         *                 - the orientatiojn ('A' - 'F')
         */
        FXPiece(String position) {
            this(position.charAt(1));
            if (position.length() != 3 ||
                    position.charAt(0) < 'A' || position.charAt(0) > 'X' ||
                    position.charAt(2) < 'A' || position.charAt(2) > 'L') {
                throw new IllegalArgumentException("Bad position string: " + position);
            }

            int pos = position.charAt(0) - 'A';
            int o = (position.charAt(2) - 'A');
            int x = (pos % Board.SIDE);
            int y = (pos / Board.SIDE);

            if(y%2==0)
                setLayoutX(BOARD_X + x * SQUARE_SIZE/2+SQUARE_SIZE/4);
            else
                setLayoutX(BOARD_X + x * SQUARE_SIZE/2+SQUARE_SIZE/2);

            setLayoutY(BOARD_Y + y * ROW_HEIGHT);

            if (o>=6){
                setScaleY(-1);
            }
            setRotate(60 * (o%6));
        }
    }


    /**
     * This class extends FXPiece with the capacity for it to be dragged and dropped,
     * and snap-to-grid.
     * @author Lei Huang, Wei Wei adapted from the Board class code of assignment 1
     */
    private class DraggableFXPiece extends FXPiece {
        double position;               // the current game position of the piece 0 .. 24 (-1 is off-board)
        double homeX, homeY;           // the position in the window where the piece should be when not on the board
        double mouseX, mouseY;      // the last known mouse positions (used when dragging)
        boolean rotation_fixed=false;     // boolean to check the piece could be rotated or not.
        /**
         * Construct a draggable piece
         * @param piece The piece identifier ('A' - 'L')
         */
        DraggableFXPiece(char piece) {
            super(piece);
            if (SET_UP_pieces.getChildren().contains(this)) {

            } else {
                position = -1; // off screen
                homeX = 1.5 * SQUARE_SIZE * ((piece - 'A') % 6);
                homeY = 1.5 * SQUARE_SIZE * ((piece - 'A') / 6) + GAME_HEIGHT;
                setLayoutX(homeX);
                setLayoutY(homeY);
            /* event handlers */
                setOnScroll(event -> {            // scroll to change orientation
                    hideCompletion();
                    if(!rotation_fixed)
                        rotate();
                });
                setOnMouseClicked(event -> {       //mouse click indicates of flipping the piece
                    if(!rotation_fixed) {
                        if (event.getButton() == MouseButton.SECONDARY)
                            flip(Character.toString(piece));
                    }
                });
                setOnMousePressed(event -> {      // mouse press indicates begin of drag
                    mouseX = event.getSceneX();
                    mouseY = event.getSceneY();
                });
                setOnMouseDragged(event -> {      // mouse is being dragged
                    hideCompletion();
                    toFront();
                    double movementX = event.getSceneX() - mouseX;
                    double movementY = event.getSceneY() - mouseY;
                    setLayoutX(getLayoutX() + movementX);
                    setLayoutY(getLayoutY() + movementY);
                    mouseX = event.getSceneX();
                    mouseY = event.getSceneY();
                    event.consume();
                });
                setOnMouseReleased(event -> {     // drag is complete
                    snapToGrid();
                });
            }
        }

        /**
         * Hide the completion message
         * @author Lei Huang,adapted from the Board class code of assignment 1
         */

        private void hideCompletion() {
            competionText.toBack();
            competionText.setOpacity(0);
        }


        /**
         * Snap the piece to the nearest grid position (if it is over the grid)
         *
         * @author Lei Huang,adapted from the Board class code of assignment 1
         */
        private void snapToGrid() {
            int x=(int)((getLayoutX() - (SQUARE_SIZE / 3)) / (SQUARE_SIZE/2));

            int y=(((int) getLayoutY()-(SQUARE_SIZE / 4))/ (SQUARE_SIZE/2));

            String current_piece;
            int Flip_adjust=0;
            if(this.piece!='A'&&getScaleY()==-1.0)
                Flip_adjust=6;
            if(y%2==0)
                current_piece=""+(char)('A'+x+6*y)+this.piece+(char)('A'+this.getRotate()/60+Flip_adjust);
            else
                current_piece=""+(char)('A'+x-1+6*y)+this.piece+(char)('A'+this.getRotate()/60+Flip_adjust);
            String piece="";

            for(String p:pieces){
                if(p.charAt(1)==current_piece.charAt(1))
                    piece=p;
            }
            pieces.remove(piece);
            pieces.add(current_piece);
            if(y%2==0)
                setLayoutX(BOARD_X + x * SQUARE_SIZE/2+SQUARE_SIZE/4);
            else
                setLayoutX(BOARD_X + x * SQUARE_SIZE/2);

            setLayoutY(BOARD_Y + y * ROW_HEIGHT);

            setPosition();
            if (position != -1)
                checkMove(current_piece);
            else
                snapToHome();
        }

        /**
         * Snap the piece to its home position (if it is not on the grid)
         *
         * @author Lei Huang, Wei Wei adapted from the Board class code of assignment 1
         */
        private void snapToHome() {
            rotation_fixed=false;
            setLayoutX(homeX);
            setLayoutY(homeY);
            setRotate(0);
            position = -1;
        }

        /**
         * Rotate the piece by 60 degrees
         *
         * @author Lei Huang,adapted from the Board class code of assignment 1
         */
        private void rotate() {
            setRotate((getRotate() + 60) % 360);
            setPosition();
        }

        /**
         * Flip the piece
         *
         * @author Lei Huang
         */
        int Flip_count = 0;
        private void flip(String current_piece){
            if(current_piece.charAt(0)!='A') {
                setScaleY(-1);
                Flip_count++;
                setScaleY(Math.pow((-1), (Flip_count)));
            }
        }

        /**
         * A move has been made.  Determine whether there are errors,
         * and if so, show skulls, and determine whether the game is
         * complete, and if so, show the completion message.
         *
         * @author Lei Huang, Wei Wei adapted from the Board class code of assignment 1
         */
        private void checkMove(String current_piece) {
            String placement = "";
            for(String p : pieces) {
                placement += p.toString();
            }

            if (!LinkGame.isPlacementValid(placement)) {
                pieces.remove(current_piece);
                snapToHome();

            } else {
                rotation_fixed=true;
                if (LinkGame.isPlacementComplete(placement))
                    showCompletion();
            }
        }

        /**
         * Determine the grid-position of the origin of the piece (0 .. 23)
         * or -1 if it is off the grid, taking into account its rotation.
         * @author Lei Huang,adapted from the Board class code of assignment 1
         */
        private void setPosition() {
            double x = Math.floor(((getLayoutX() - BOARD_X) / (SQUARE_SIZE/2))/2);
            double y = (getLayoutY() - BOARD_Y) / SQUARE_SIZE;
            position = x + y * Board.SIDE;
        }


        /** Represent the piece placement as a string
         * @author Lei Huang
         * */
        public String toString() {
            char orientation = (char) ('A' + (int) (getRotate()/ 60));
            if(position==-1)
                return "";
            else
                return "" + (char)('A'+position) + piece + orientation;
        }
    }

    /**
     * An inner class that represents pegs of boardin the game.
     * @author Lei Huang
     */
    private class Peg extends Circle{
        Peg(double x,double y, double radius){
            this.setLayoutX(x);
            this.setLayoutY(y);
            this.setRadius(radius);
        }
    }

    /**
     * Create the message to be displayed when the player completes the puzzle.
     * @author Lei Huang,Wei Wei adapted from the Board class code of assignment 1
     */
    private void makeCompletion() {
        competionText.setFill(Color.RED);
        competionText.setFont(Font.font("Arial", 50));
        competionText.setLayoutX(5.6*SQUARE_SIZE);
        competionText.setLayoutY(0.7*SQUARE_SIZE);
        competionText.setTextAlignment(TextAlignment.CENTER);
        root.getChildren().add(competionText);
    }
    /**
     * Hide the completion message
     * @author Lei Huang,adapted from the Board class code of assignment 1
     */

    private void hideCompletion() {
        competionText.toBack();
        competionText.setOpacity(0);
    }
    /**
     * Show the completion message and stop the background music
     * @author Lei Huang, Wei Wei
     */
    private void showCompletion() {
        mediaPlayer.stop();
        competionText.toFront();
        competionText.setOpacity(1);
    }
    /**
     * Create Starting placement as the start of the game.
     * @author Lei Huang
     * @param setup String for the starting of the game.
     */
    private void Starting_placements(String setup){
        try {
            if (root.getChildren().contains(SET_UP)){
                root.getChildren().remove(SET_UP);}
            if (root.getChildren().contains(SET_UP_pieces)){
                root.getChildren().remove(SET_UP_pieces);}

            SET_UP.getChildren().clear();
            SET_UP_pieces.getChildren().clear();
            pieces.clear();
            Arrays.fill(used_pieces,false);
            for (int i = 0; i < setup.length() / 3; i++) {
                pieces.add(setup.substring(3 * i, 3 * i + 3));
                SET_UP.getChildren().add(new FXPiece(setup.substring(3 * i, 3 * i + 3)));
                used_pieces[setup.charAt(3 * i + 1) - 'A'] = true;
            }
            for (int i = 0; i < 12; i++) {
                SET_UP_pieces.getChildren().add(new DraggableFXPiece((char)((int)'A'+i)));
            }
            root.getChildren().add(SET_UP_pieces);
            root.getChildren().add(SET_UP);
        }catch (IllegalArgumentException e) {
            System.err.println("Uh oh. "+ e);
            Thread.dumpStack();
            Platform.exit();
        }
    }

    /**
     * Start a new game, resetting everything as necessary
     * @author Lei Huang, Wei Wei adapted from the Board class code of assignment 1
     */
    String Start="";
    private void newGame() {
        try {
            Starting_placements(Start);
        } catch (IllegalArgumentException e) {
            System.err.println("Uh oh. "+ e);
            Thread.dumpStack();
            Platform.exit();
        }
    }

    /**
     * Generate interesting starting placements by select different game levels to play the Game,
     * and play background music when game started.
     * @author Wei Wei
     */
    private void startGameLevel() {
        Label label1 = new Label("Game Level:");

        ChoiceBox choiceBox = new ChoiceBox(FXCollections.observableArrayList(
                "Easy", "Normal","Hard")
        );
        choiceBox.setTooltip(new Tooltip("Start Game Level"));

        Button button1 = new Button("Play");
        //Button button2 = new Button("Restart");
        Button button3 = new Button("Music");

        button1.setOnAction(event -> {
            root.getChildren().remove(Hint_Group);
            button_usable=true;
            hideCompletion();
            ObservableValue selectedIndices = choiceBox.getSelectionModel().selectedIndexProperty();

            int i=(int)selectedIndices.getValue();

            Random r= new Random();
            switch(i) {
                case 0: {
                    try {
                        ArrayList<String> easy=new ArrayList<String>();
                        easy.add("KAFCBGUCAGDFLEFPFBBGESHBOIAKJA");
                        easy.add("WBABCDJDALEFMFCCGLTIAQJCKKBILF");
                        easy.add("KAAODEFEGMFCEGERHGVJCJLFBIGDKF");
                        easy.add("JAAVCJRDCDEDSFBWGBFHECIFAJDHLG");
                        String Temp=easy.get(r.nextInt(easy.size()));
                        while (Start==Temp){
                            Temp=easy.get(r.nextInt(easy.size()));
                        }
                        Start=Temp;
                        Starting_placements(Start);
                    } catch (IllegalArgumentException e) {
                        System.err.println("Uh oh. " + e);
                        Thread.dumpStack();
                        Platform.exit();
                    }
                    break;
                }
                case 1: {
                    try {
                        ArrayList<String> normal=new ArrayList<String>();
                        normal.add("KAFUCAGDFPFBBGESHBOIAKJA");
                        normal.add("WBAJDAMFCCGLTIAKKBILFUHB");
                        normal.add("KAAFEGMFCEGEVJCDKFHBLTCA");
                        normal.add("JAAVCJDEDSFBWGBFHECIFHLG");
                        String Temp=normal.get(r.nextInt(normal.size()));
                        while (Start==Temp){
                            Temp=normal.get(r.nextInt(normal.size()));
                        }
                        Start=Temp;
                        Starting_placements(Start);
                    } catch (IllegalArgumentException e) {
                        System.err.println("Uh oh. " + e);
                        Thread.dumpStack();
                        Platform.exit();
                    }
                    break;
                }

                case 2:
                {
                    try {
                        ArrayList<String> hard=new ArrayList<String>();
                        hard.add("UCAGDFPFBSHBOIAKJA");
                        hard.add("WBALEFCGLQJCILFUHB");
                        hard.add("EGEDKFHBLTCAVJCBIG");
                        hard.add("JAAVCJDEDWGBCIFHLG");
                        String Temp=hard.get(r.nextInt(hard.size()));
                        while (Start==Temp){
                            Temp=hard.get(r.nextInt(hard.size()));
                        }
                        Start=Temp;
                        Starting_placements(Start);
                    }
                    catch (IllegalArgumentException e) {
                        System.err.println("Uh oh. "+ e);
                        Thread.dumpStack();
                        Platform.exit();
                    }
                    break;
                }
            }
            setUpSoundLoop();
        });

        /*button2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Starting_placements(Start);
            }
        });*/

        button3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                toggleSoundLoop();
            }
        });

        HBox hBox = new HBox();
        hBox.getChildren().addAll(label1, choiceBox, button1, /*button2,*/button3);
        hBox.setSpacing(10);
        hBox.setLayoutX(130);
        hBox.setLayoutY(BOARD_HEIGHT - 50);

        controls.getChildren().addAll(hBox);
    }


    /**
         * Show solution of current Game
         * @author Lei Huang
         */
    private Group Hint_Group=new Group();
    private void hint(){
        String placement=LinkGame.getSolutions(Start)[0];

        if(root.getChildren().contains(Hint_Group))
            root.getChildren().remove(Hint_Group);

        Hint_Group.getChildren().clear();
        int length=placement.length()/3;
        if(placement.length()%3!=0){
            System.out.println("Wrong input");
            return;
        }
        List<String> list=new ArrayList<>();
        for(int i=0;i<length;i++){
            list.add(placement.substring(i*3,(i+1)*3));
        }
        List<Integer> index = new ArrayList<>();
        for (String piece : list) {
            index.add(Character.getNumericValue(piece.charAt(0)) - 10);
        }
        int[][] position = new int[length][2];

        for(int i=0;i<length;i++){
            position[i][0]=index.get(i)/6;//row
            position[i][1]=index.get(i)%6;//column
        }

        for (int i = 0; i < length; i++) {
            char piece=list.get(i).charAt(1);
            Image image = new Image(Board.class.getResource(URI_BASE + piece + ".png").toString());
            ImageView iv1 = new ImageView();
            iv1.setImage(image);
            iv1.setFitHeight(PIECE_IMAGE_SIZE);
            iv1.setFitWidth(PIECE_IMAGE_SIZE);
            if(position[i][0]%2==0){
                iv1.relocate(position[i][1]*SQUARE_SIZE/2-SQUARE_SIZE/4+5*SQUARE_SIZE,
                        BOARD_Y+position[i][0]*ROW_HEIGHT);
            }
            else{
                iv1.relocate(position[i][1]*SQUARE_SIZE/2+5*SQUARE_SIZE,
                        BOARD_Y+position[i][0]*ROW_HEIGHT);
            }
            char orientation =list.get(i).charAt(2);
            int angle = (Character.getNumericValue(orientation)-10)*60;
            if(angle>=360)
                iv1.setScaleY(-1);
            iv1.setRotate(angle);
            Hint_Group.getChildren().add(iv1);
        }

        root.getChildren().add(Hint_Group);
    }

    /**
     * Show and Hide solution of current Game, and display the Game rules in the pop-up message box.
     * @author Wei Wei
     */
    Boolean button_usable=false;
    private void makecontrols(){
        Button button = new Button("Hint");
        Button button2 = new Button("Clear Hint");
        Button button3 = new Button("Help");

        button.setOnAction(e -> {
            if(button_usable)
                hint();
        });

        button2.setOnAction(e -> {
            if(button_usable)
                root.getChildren().remove(Hint_Group);
        });

        button3.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);

            alert.setTitle("IQ-Link Game");
            alert.setHeaderText("Game Rule");
            alert.setContentText("1.The goal of this game is to place all pieces on the borad.\n"+
                    "2.Open rings and balls of different puzzle pieces can occupy the same place when you link them the right way.\n"+
                    "3.You could rotate the piece by scroll your mouse or flip the piece by right click the piece.\n"+
                    "4.Please orientate your piece before you drag them onto the board.\n"+"" +
                    "5.There are 3 different difficult levels with various starting placements."+
                    "6.You can start/stop background music by clicking music button.");
            alert.showAndWait();
        });

        HBox hb = new HBox();
        hb.getChildren().addAll(button3, button, button2);
        hb.setSpacing(10);
        hb.setLayoutX(130);
        hb.setLayoutY(50);

        controls.getChildren().add(hb);

        startGameLevel();
    }

    /**
     * Starting stage for player to play the game.
     * @author Lei Huang, Wei Wei
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("IQ Link Game");
        Scene scene = new Scene(root, BOARD_WIDTH, BOARD_HEIGHT);

        makecontrols();
        for(int i =0;i<24;i++) {
            if((i/6)%2==0){
                Peg r = new Peg(((i%6)+1)*SQUARE_SIZE/2+SQUARE_SIZE/4+SQUARE_SIZE/2+SQUARE_SIZE/2
                        , (i/6)*ROW_HEIGHT +SQUARE_SIZE/2-ROW_HEIGHT / 2+3*ROW_HEIGHT + 10, 12);
                r.setFill(Color.LIGHTGREY);
                pegs.add(r);}
            else{
                Peg r = new Peg(((i%6)+1)*SQUARE_SIZE/2+SQUARE_SIZE/2+SQUARE_SIZE/2
                        , (i/6)*ROW_HEIGHT +SQUARE_SIZE/2-ROW_HEIGHT / 2 +1*ROW_HEIGHT+ 10, 12);
                r.setFill(Color.LIGHTGREY);
                pegs.add(r);}
        }
        root.getChildren().add(timer(750, 160));

        pegs.forEach(peg -> root.getChildren().add(peg));
        root.getChildren().add(controls);

        Starting_placements(Start);

        makeCompletion();
        hideCompletion();

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}