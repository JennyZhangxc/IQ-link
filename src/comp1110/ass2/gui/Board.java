package comp1110.ass2.gui;

import comp1110.ass2.LinkGame;
import comp1110.ass2.PiecePlacement;
import comp1110.ass2.Puzzle;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Board extends Application{

    /* board layout */
    private static final int BOARD_WIDTH = 933;
    private static final int BOARD_HEIGHT = 700;
    private static final int GAME_HEIGHT = 350;
    private static final int SQUARE_SIZE = 100;
    private static final int MARGIN = 10;
    private static final int SIDE = 6;
    private static final double PIECE_IMAGE_SIZE = 1.5*SQUARE_SIZE;
    private static final int BOARD_X=50;
    private static final int BOARD_Y=50;

    private static final double ROW_HEIGHT = 0.5*SQUARE_SIZE * 0.8660254; // 60 degrees distance

    /* where to find media assets */
    private static final String URI_BASE = "assets/";

    /* node groups */
    private final Group root = new Group();
    private final Group controls = new Group();
    private final Group solution = new Group();
    private final ArrayList<String>pieces = new ArrayList<>();
    /* message on completion */
    private final Text competionText = new Text("Well done!");


    private final ArrayList<Peg>pegs=new ArrayList<>();

    /**
     * An inner class that represents transparent pieces used in the game.
     * Each of these is a visual representaton of an underlying piece.
     */
    class FXPiece extends ImageView {
        char piece;

        /**
         * Construct a particular playing piece
         *
         * @param piece The letter representing the piece to be created.
         */
        FXPiece(char piece) {
            if (!(piece >= 'A' && piece <= 'L')) {
                throw new IllegalArgumentException("Bad piece: \"" + piece + "\"");
            }
            setImage(new Image(Board.class.getResource(URI_BASE + piece + ".png").toString()));
            this.piece = piece;
            setFitHeight(PIECE_IMAGE_SIZE);
            setFitWidth(PIECE_IMAGE_SIZE);
        }
        /**
         * Construct a particular playing piece at a particular place on the
         * board at a given orientation.
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
                setLayoutX(BOARD_X + x * SQUARE_SIZE);
            else
                setLayoutX(BOARD_X + x * SQUARE_SIZE-SQUARE_SIZE/2);
            setLayoutY(BOARD_Y + y * ROW_HEIGHT);
            if (o>=6){
                setY(-1);
            }
            setRotate(60 * (o%6));
        }
    }


    /**
     * This class extends FXPiece with the capacity for it to be dragged and dropped,
     * and snap-to-grid.
     */
    class DraggableFXPiece extends FXPiece {
        double position;               // the current game position of the piece 0 .. 24 (-1 is off-board)
        double homeX, homeY;           // the position in the window where the piece should be when not on the board
        double mouseX, mouseY;      // the last known mouse positions (used when dragging)

        /**
         * Construct a draggable piece
         * @param piece The piece identifier ('A' - 'L')
         */
        DraggableFXPiece(char piece) {
            super(piece);
            position = -1; // off screen
            homeX = 1.5* SQUARE_SIZE * ((piece - 'A') % 6);
            homeY = 1.5* SQUARE_SIZE * ((piece - 'A') / 6) + GAME_HEIGHT;
            setLayoutX(homeX);
            setLayoutY(homeY);
            /* event handlers */
            setOnScroll(event -> {            // scroll to change orientation
//                hideSkulls();
//                hideCompletion();
                rotate(Character.toString(piece));
                event.consume();
            });
            setOnMousePressed(event -> {      // mouse press indicates begin of drag
                mouseX = event.getSceneX();
                mouseY = event.getSceneY();
            });
            setOnMouseDragged(event -> {      // mouse is being dragged
//                hideCompletion();
//                hideSkulls();
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


        /**
         * Snap the piece to the nearest grid position (if it is over the grid)
         */
        private void snapToGrid() {
            int x=(int)((getLayoutX() - (SQUARE_SIZE / 4)) / (SQUARE_SIZE/2));
//            System.out.println("x= "+x);
            int y=(((int) getLayoutY()-(SQUARE_SIZE / 4))/ (SQUARE_SIZE/2));
//            System.out.println("y= "+y);
            String current_piece="";
            if(y%2==0) {
                current_piece=""+(char)('A'+x+6*y)+this.piece+(char)('A'+this.getRotate()/60);
            }
            else{
                current_piece=""+(char)('A'+x-1+6*y)+this.piece+(char)('A'+this.getRotate()/60);
            }
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
         */
        private void snapToHome() {
            setLayoutX(homeX);
            setLayoutY(homeY);
            setRotate(0);
            position = -1;
        }


        /**
         * Rotate the piece by 60 degrees
         */
        private void rotate(String current_piece) {
            setRotate((getRotate() + 60) % 360);
            setPosition();
            checkMove(current_piece);
        }

        /**
         * A move has been made.  Determine whether there are errors,
         * and if so, show skulls, and determine whether the game is
         * complete, and if so, show the completion message.
         */
        private void checkMove(String current_piece) {
            String placement = "";
            for(String p : pieces) {
                placement += p.toString();
//                System.out.println(p.toString());
            }

            if (!LinkGame.isPlacementValid(placement)) {
                pieces.remove(current_piece);
                snapToHome();
                System.out.println("Wrong Placement");
            } else {
//                if (LinkGame.isPlacementComplete(placement)) {
//                    showCompletion();
//                }
            }
        }


        /**
         * Determine whether the whole piece is on the board, given x and y
         * coordinates representing the top-left corner of the piece in its
         * current rotation.
         * @param x The column that the origin of the piece is on
         * @param y The row that the origin of the piece is on
         * @return True if the entire piece is on the board
         */
        private boolean isOnBoard(double x, double y) {
            return true;
//            if(LinkGame.isPlacementValid(this.toString()))
//                return true;
//            else
//                return false;
//
//            if (piece < 'U')  // 'L'-shaped pieces are simple, because they're basically square
//                return x >= 0 && x < 3 && y >= 0 && y < 3;
//            else {            // For 'I'-shaped pieces it depends on the orientation
//                switch ((int) getRotate()) {
//                    case 0:
//                        return x >= 0  && x < 3 && y >= 0  && y < 4;
//                    case 90:
//                        return x >= -1 && x < 3 && y >= 0  && y < 3;
//                    case 180:
//                        return x >= 0  && x < 3 && y >= -1 && y < 3;
//                    case 270: default:
//                        return x >= 0  && x < 4 && y >= 0  && y < 3;
//                }
//            }
        }


        /**
         * Determine the grid-position of the origin of the piece (0 .. 15)
         * or -1 if it is off the grid, taking into account its rotation.
         */
        private void setPosition() {
            double x = Math.floor(((getLayoutX() - BOARD_X) / (SQUARE_SIZE/2))/2);
            double y = (getLayoutY() - BOARD_Y) / SQUARE_SIZE;
            if (isOnBoard(x,y)) {
                /*  find 'position' (reference point is top left of *un*rotated piece */
                int rotate = (int) getRotate() / 60;
//                x += (rotate == 0 || rotate == 3) ? 0 : 1;
//                y += rotate / 2;
                position = x + y * Board.SIDE;
            } else
                position = -1;
        }


        /** Represent the piece placement as a string */
        public String toString() {
            char orientation = (char) ('A' + (int) (getRotate()/ 60));
            return position == -1 ? "" : "" + (char)('A'+position) + piece + orientation;
        }
    }


    private class Peg extends Circle{
        Peg(double x,double y, double radius){
            this.setLayoutX(x);
            this.setLayoutY(y);
            this.setRadius(radius);
        }

    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("LinkGame Viewer");
        Scene scene = new Scene(root, BOARD_WIDTH, BOARD_HEIGHT);

//
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

        for (int i = 0; i < 12; i++) {
            DraggableFXPiece draggableFXPiece=new DraggableFXPiece((char)((int)'A'+i));
            root.getChildren().add(draggableFXPiece);
        }

        primaryStage.setScene(scene);
        primaryStage.show();
    }
    // FIXME Task 8: Implement a basic playable Link Game in JavaFX that only allows pieces to be placed in valid places

    // FIXME Task 9: Implement starting placements
    PiecePlacement start_placements(){
        //return new PiecePlacement();
        return null;
    }
    Puzzle start(){
        return new Puzzle();
    }

    // FIXME Task 11: Implement hints

    // FIXME Task 12: Generate interesting starting placements


}
