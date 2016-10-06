package comp1110.ass2.gui;

import comp1110.ass2.LinkGame;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

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

    /* where to find media assets */
    private static final String URI_BASE = "assets/";

    /* node groups */
    private final Group root = new Group();
    private final Group controls = new Group();
    private final Group SET_UP=new Group();
    private final Group SET_UP_pieces=new Group();
    private final Group solution = new Group();
    private final Group Pieces=new Group();
    private final ArrayList<String>pieces = new ArrayList<>();

    /* message on completion */
    private final Text competionText = new Text("Well done!");

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
//            System.out.println(pos);
            int x = (pos % Board.SIDE);
//            System.out.println("X="+x);
            int y = (pos / Board.SIDE);
//            System.out.println("Y="+y);

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
     * @author Lei Huang,adapted from the Board class code of assignment 1
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
//                    System.out.println("Scroll:"+rotation_fixed);
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
//            System.out.println("x= "+x);
            int y=(((int) getLayoutY()-(SQUARE_SIZE / 4))/ (SQUARE_SIZE/2));
//            System.out.println("y= "+y);
            String current_piece;
            int Flip_adjust=0;
            if(this.piece!='A'&&getScaleY()==-1.0){
                Flip_adjust=6;
            }
            if(y%2==0) {
                current_piece=""+(char)('A'+x+6*y)+this.piece+(char)('A'+this.getRotate()/60+Flip_adjust);
            }
            else{
                current_piece=""+(char)('A'+x-1+6*y)+this.piece+(char)('A'+this.getRotate()/60+Flip_adjust);
            }
            String piece="";

            for(String p:pieces){
                if(p.charAt(1)==current_piece.charAt(1)){
                    piece=p;
                }
            }
            pieces.remove(piece);
            pieces.add(current_piece);
            if(y%2==0)
                setLayoutX(BOARD_X + x * SQUARE_SIZE/2+SQUARE_SIZE/4);
            else
                setLayoutX(BOARD_X + x * SQUARE_SIZE/2);

            setLayoutY(BOARD_Y + y * ROW_HEIGHT);

            setPosition();
            if (position != -1) {
                checkMove(current_piece);
            } else {
                snapToHome();

            }
        }


        /**
         * Snap the piece to its home position (if it is not on the grid)
         *
         * @author Lei Huang,adapted from the Board class code of assignment 1
         */
        private void snapToHome() {
            rotation_fixed=false;
            setLayoutX(homeX);
            setLayoutY(homeY);
            setRotate(0);
            position = -1;
//            System.out.println(rotation_fixed);
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
//                System.out.println(getScaleY());
            }
        }
        /**
         * A move has been made.  Determine whether there are errors,
         * and if so, show skulls, and determine whether the game is
         * complete, and if so, show the completion message.
         *
         * @author Lei Huang,adapted from the Board class code of assignment 1
         */
        private void checkMove(String current_piece) {
            String placement = "";
            for(String p : pieces) {
                placement += p.toString();
            }

            if (!LinkGame.isPlacementValid(placement)) {
                pieces.remove(current_piece);
                snapToHome();
//                System.out.println("Wrong Placement");
            } else {
                rotation_fixed=true;
//                System.out.println(placement);
                if (LinkGame.isPlacementComplete(placement)) {
                    showCompletion();
                }
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
            if(position==-1){
                return "";
            }else{
                return "" + (char)('A'+position) + piece + orientation;
            }
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
     * @author Lei Huang,adapted from the Board class code of assignment 1
     */
    private void makeCompletion() {
        competionText.setFill(Color.BLACK);
        competionText.setFont(Font.font("Arial", 80));
        competionText.setLayoutX(3.1*SQUARE_SIZE);
        competionText.setLayoutY(0.9*SQUARE_SIZE);
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
     * Show the completion message
     * @author Lei Huang
     */
    private void showCompletion() {
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
     * @author Lei Huang,adapted from the Board class code of assignment 1
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
     * Generate interesting starting placements by select different game levels to play the Game
     * @author Wei Wei
     */
    private void startGameLevel() {
        Label label1 = new Label("Game Level:");

        ChoiceBox choiceBox = new ChoiceBox(FXCollections.observableArrayList(
                "Easy", "Normal","Hard")
        );
        choiceBox.setTooltip(new Tooltip("Start Game Level"));

        Button button1 = new Button("Play");
        Button button2 = new Button("Restart");

        button1.setOnAction(event -> {
            root.getChildren().remove(Hint_Group);
            ObservableValue selectedIndices = choiceBox.getSelectionModel().selectedIndexProperty();
//            System.out.println(selectedIndices);
            int i=(int)selectedIndices.getValue();
//            System.out.println(i);
            Random r= new Random();
            switch(i) {
                case 0: {
                    try {
                        ArrayList<String> easy=new ArrayList<String>();
                        easy.add("KAFCBGUCAGDFLEFPFBBGESHBOIAKJA");
                        easy.add("WBABCDJDALEFMFCCGLTIAQJCKKBILF");
                        String Temp=easy.get(r.nextInt(easy.size()));
                        while (Start==Temp){
                            Temp=easy.get(r.nextInt(easy.size()));
                        }
                        Start=Temp;
                        Starting_placements(Start);
//                        System.out.println("easy");
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
        });

        button2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Starting_placements(Start);
            }
        });

        HBox hBox = new HBox();
        hBox.getChildren().addAll(label1, choiceBox, button1, button2);
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

        if(root.getChildren().contains(Hint_Group)){
            root.getChildren().remove(Hint_Group);
        }
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
            if(angle>=360){
                iv1.setScaleY(-1);
            }
            iv1.setRotate(angle);
            Hint_Group.getChildren().add(iv1);
        }

        root.getChildren().add(Hint_Group);
    }
    private void makecontrols(){
        Button button = new Button("Hint");
        Button button2 = new Button("Clear Hint");
        Button button3 = new Button("Help");

        button.setOnAction(e -> {
            hint();
        });

        button2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                root.getChildren().remove(Hint_Group);
            }
        });

        button3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("Look, an Information Dialog");
                alert.setContentText("I have a great message for you!");

                alert.showAndWait();
            }
        });

        HBox hb = new HBox();
        hb.getChildren().addAll(button3, button, button2);
        hb.setSpacing(10);
        hb.setLayoutX(130);
        hb.setLayoutY(50);
        controls.getChildren().add(hb);

        startGameLevel();
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("LinkGame Viewer");
        Scene scene = new Scene(root, BOARD_WIDTH, BOARD_HEIGHT);
//        newGame();
        Starting_placements(Start);
//        startGameLevel();
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

        pegs.forEach(peg -> root.getChildren().add(peg));
        root.getChildren().add(controls);

        makeCompletion();
        hideCompletion();

        primaryStage.setScene(scene);
        primaryStage.show();
    }
    // FIXME Task 8: Implement a basic playable Link Game in JavaFX that only allows pieces to be placed in valid places

    // FIXME Task 9: Implement starting placements

    // FIXME Task 11: Implement hints

    // FIXME Task 12: Generate interesting starting placements
}
