package comp1110.ass2.gui;

import comp1110.ass2.LinkGame;
import comp1110.ass2.PiecePlacement;
import comp1110.ass2.Puzzle;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Board extends Application{
    public class DraggablePeg extends Peg{
        Board board;
        private double mousex;
        private double mousey;
        DraggablePeg(double x, double y, double side,Board board) {
            super(x, y, side);
            this.setFill(Color.RED);
            this.board=board;
            this.setOnMousePressed(event -> {
                mousex = event.getSceneX();
                mousey = event.getSceneY();
                this.toFront();
            });
            this.setOnMouseDragged(event -> {
                double x_move=event.getSceneX() - mousex;
                double y_move=event.getSceneY() - mousey;
                this.setLayoutX(this.getLayoutX()+x_move);
                this.setLayoutY(this.getLayoutY()+y_move);
                mousex=mousex+x_move;
                mousey=mousey+y_move;
                board.highlightNearestPeg(mousex,mousey);

            });
            this.setOnMouseReleased(event -> {
                this.setRotate(highlighted.getRotate());
                this.setLayoutX(highlighted.getLayoutX());
                this.setLayoutY(highlighted.getLayoutY());
            });
        }
    }
    private class Peg extends Circle{
        Peg(double x,double y, double radius){
            this.setLayoutX(x);
            this.setLayoutY(y);
            this.setRadius(radius);
        }
        private double distance(double x, double y){
            double x_distance=Math.abs(x-getLayoutX());
            double y_distance=Math.abs(y-getLayoutY());
            return Math.sqrt(x_distance*x_distance+y_distance*y_distance);
        }

    }
    private static final int BOARD_WIDTH = 933;
    private static final int BOARD_HEIGHT = 700;
    private static final int SQUARE_SIZE = 100;
    private static final int PIECE_IMAGE_SIZE = 3*SQUARE_SIZE;
    private static final double ROW_HEIGHT = SQUARE_SIZE * 0.8660254; // 60 degrees

    private final Group root = new Group();
    private final Group controls = new Group();
    private final ArrayList<Peg>pegs=new ArrayList<>();
    private Peg highlighted = null;

    private LinkGame linkGame;

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

    private Peg findNearestPeg(double x, double y){
        Peg output = pegs.get(0);
        for (Peg anArrayList : pegs) {
            if (anArrayList.distance(x, y) < output.distance(x, y)) {
                output = anArrayList;
            }

        }
        return output;
    }
    private void highlightNearestPeg(double x, double y){
        if(null != highlighted){
            highlighted.setFill(Color.LIGHTGREY);
        }
        highlighted=this.findNearestPeg(x,y);
        highlighted.setFill(Color.GREEN);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("LinkGame Viewer");
        Scene scene = new Scene(root, BOARD_WIDTH, BOARD_HEIGHT);


        for(int i =0;i<24;i++) {
            if((i/6)%2==0){
                Peg r = new Peg(((i%6)+1)*SQUARE_SIZE, (i/6)*ROW_HEIGHT +SQUARE_SIZE-ROW_HEIGHT / 2 + 7, 28);
                r.setFill(Color.LIGHTGREY);
                pegs.add(r);}
            else{
                Peg r = new Peg(((i%6)+1)*SQUARE_SIZE+SQUARE_SIZE/2,(i/6)*ROW_HEIGHT +SQUARE_SIZE-ROW_HEIGHT / 2 + 7, 28);
                r.setFill(Color.LIGHTGREY);
                pegs.add(r);}
        }
        pegs.forEach(peg -> root.getChildren().add(peg));

        root.getChildren().add(controls);

        DraggablePeg draggablePeg=new DraggablePeg(SQUARE_SIZE,SQUARE_SIZE-ROW_HEIGHT/2+7,28,this);
        root.getChildren().add(draggablePeg);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
