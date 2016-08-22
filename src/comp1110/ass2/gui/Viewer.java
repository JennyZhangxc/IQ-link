package comp1110.ass2.gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

import static com.sun.corba.se.impl.util.Utility.printStackTrace;

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
    TextField textField;


    /**
     * Draw a placement in the window, removing any previously drawn one
     *
     * @param placement  A valid placement string
     */

    void makePlacement(String placement) {
        // FIXME Task 5: implement the simple placement viewer
        int length=placement.length()/3;
        if(length%3!=0){
            System.out.println("Wrong input");
        }
        List<String> list=new ArrayList<>();
        for(int i=0;i<length;i++){
            list.add(placement.substring(i*3,(i+1)*3));
        }
        List<Integer> index = new ArrayList<>();
        for (int j = 0; j < list.size(); j++) {
            index.add(Character.getNumericValue(list.get(j).charAt(0)) - 10);

        }
        int[][] position = new int[length][2];

        for(int i=0;i<length;i++){
            position[i][0]=index.get(i)/6;//row
            position[i][1]=index.get(i)%6;//column

        }

        try {
            Pane pane =new Pane();
            for (int i = 0; i < length; i++) {
                char piece=list.get(i).charAt(1);
                Image image = new Image("file:src/comp1110/ass2/gui/assets/"+piece+".png");
                ImageView iv1 = new ImageView();
                iv1.setImage(image);
                iv1.setFitHeight(PIECE_IMAGE_SIZE);
                iv1.setFitWidth(PIECE_IMAGE_SIZE);
                if(position[i][0]%2==0){
                    iv1.relocate(position[i][1]*SQUARE_SIZE-SQUARE_SIZE/2,position[i][0]*ROW_HEIGHT-SQUARE_SIZE);
                }
                else{
                    iv1.relocate(position[i][1]*SQUARE_SIZE,position[i][0]*ROW_HEIGHT-SQUARE_SIZE);
                }
                char orientation =list.get(i).charAt(2);
                int angle = (Character.getNumericValue(orientation)-10)*60;
                if(angle>360){
                    iv1.setScaleY(-1);
                }
                iv1.setRotate(angle);
                pane.getChildren().add(iv1);
            }

            root.getChildren().add(pane);
        }
        catch (Exception e)
        {
            printStackTrace();
        }

    }


    /**
     * Create a basic text field for input and a refresh button.
     */
    private void makeControls() {
        Label label1 = new Label("Placement:");
        textField = new TextField ();
        textField.setPrefWidth(300);
        Button button = new Button("Refresh");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                makePlacement(textField.getText());
                textField.clear();
            }
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

        root.getChildren().add(controls);

        makePlacement("BAAHBATCJRDKWEB");
        makeControls();

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
