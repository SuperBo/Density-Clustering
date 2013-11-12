package hcmut.clustering;/**
 * Created with IntelliJ IDEA.
 * User: Super Bo
 * Date: 11/12/13
 * Time: 4:53 PM
 * To change this template use File | Settings | File Templates.
 */

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class MainApp extends Application {
    private BorderPane rootLayout;
    private GraphicsContext gc;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("view/RootLayout.fxml"));
            BorderPane rootLayout = (BorderPane) loader.load();
            this.rootLayout = rootLayout;
            Scene scene = new Scene(rootLayout);
            primaryStage.setTitle("Clustering Graph Info");
            primaryStage.setScene(scene);
            showGraphicsContext();
            primaryStage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void showGraphicsContext() {
        Canvas canvas = new Canvas(500,500);
        gc = canvas.getGraphicsContext2D();
        GraphController graphCtrl = new GraphController(gc);
        graphCtrl.draw();
        rootLayout.setCenter(canvas);
    }
}
