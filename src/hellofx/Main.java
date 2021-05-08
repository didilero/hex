package hellofx;

import java.util.Iterator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	
	//tableau qui correspond au tableau du jeu
	// Pour commencer set à 9 cases
    private int[] tableauY = new int[3];
    private int[][] tableau = {tableauY, tableauY, tableauY};

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("hellofx.fxml"));
        primaryStage.setTitle("Hex");
        primaryStage.setScene(new Scene(root, 400, 300));
        primaryStage.show();
        
        //this.afficherTab();
    }


    public static void main(String[] args) {
        launch(args);
    }
    
    public void afficherTab() {
    	System.out.println("[ ");
        for (int i = 0; i < this.tableau.length; i ++) {
        	System.out.println("[ ");
			for (int j = 0; j < this.tableau[i].length; j++) {
				System.out.println(this.tableau[i][j] + ",");
			}
			System.out.println("],");
		}
        System.out.println("]");
    }
}