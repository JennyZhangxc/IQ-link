package comp1110.ass2.gui;

import comp1110.ass2.LinkGame;
import comp1110.ass2.PiecePlacement;
import comp1110.ass2.Puzzle;
import javafx.application.Application;
import javafx.stage.Stage;

public class Board extends Application{
    private static final int BOARD_WIDTH = 933;
    private static final int BOARD_HEIGHT = 700;
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

    @Override
    public void start(Stage primaryStage) throws Exception {

    }
}
