package comp1110.ass2.gui;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;


/**
 * A very simple viewer for piece placements in the link game.
 *
 * NOTE: This class is separate from your main game class.  This
 * class does not play a game, it just illustrates various piece
 * placements.
 */
public class Viewer extends Application {

    /* board layout */
    private static final int SQUARE_SIZE = 100;
    private static final int PIECE_IMAGE_SIZE = 3*SQUARE_SIZE;
    private static final double ROW_HEIGHT = SQUARE_SIZE * 0.8660254; // 60 degrees
    private static final int VIEWER_WIDTH = 750;
    private static final int VIEWER_HEIGHT = 500;

    private static final String URI_BASE = "assets/";

    private final Group root = new Group();
    private final Group controls = new Group();
    private TextField textField;
    private Group group=new Group();


    /**
     * Draw a placement in the window, removing any previously drawn one
     *
     * @param placement  A valid placement string
     */

    private void makePlacement(String placement) {
        if(root.getChildren().contains(group)){
            root.getChildren().remove(group);
        }
        group.getChildren().clear();
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
                iv1.relocate(position[i][1]*SQUARE_SIZE-SQUARE_SIZE/2,position[i][0]*ROW_HEIGHT-ROW_HEIGHT);
            }
            else{
                iv1.relocate(position[i][1]*SQUARE_SIZE,position[i][0]*ROW_HEIGHT-ROW_HEIGHT);
            }
            char orientation =list.get(i).charAt(2);
            int angle = (Character.getNumericValue(orientation)-10)*60;
            if(angle>360){
                iv1.setScaleY(-1);
            }
            iv1.setRotate(angle);
            group.getChildren().add(iv1);
        }

        root.getChildren().add(group);
    }


    /**
     * Create a basic text field for input and a refresh button.
     */
    private void makeControls() {
        Label label1 = new Label("Placement:");
        textField = new TextField ();
        textField.setPrefWidth(300);
        Button button = new Button("Refresh");
        button.setOnAction(e -> {
            makePlacement(textField.getText());
            textField.clear();
        });
        HBox hb = new HBox();
        hb.getChildren().addAll(label1, textField, button);
        hb.setSpacing(10);
        hb.setLayoutX(130);
        hb.setLayoutY(VIEWER_HEIGHT - 50);
        controls.getChildren().add(hb);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("LinkGame Viewer");
        Scene scene = new Scene(root, VIEWER_WIDTH, VIEWER_HEIGHT);


        for(int i =0;i<24;i++) {
            if((i/6)%2==0){
                Circle r = new Circle(((i%6)+1)*SQUARE_SIZE, (i/6)*ROW_HEIGHT +SQUARE_SIZE-ROW_HEIGHT / 2 + 7, 28);
                r.setFill(Color.GRAY);
                root.getChildren().add(r);}
            else{
                Circle r = new Circle(((i%6)+1)*SQUARE_SIZE+SQUARE_SIZE/2,(i/6)*ROW_HEIGHT +SQUARE_SIZE-ROW_HEIGHT / 2 + 7, 28);
                r.setFill(Color.GRAY);
                root.getChildren().add(r);}
        }

        root.getChildren().add(controls);

        makeControls();

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
