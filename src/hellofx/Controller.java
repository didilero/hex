package hellofx;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class Controller {

    @FXML
    private GridPane tab;
    
    
    private int[] tableauY = new int[3];
    private int[][] tableau = {tableauY, tableauY, tableauY};

    public void initialize() {
    	for (int i = 0; i < this.tableau.length; i ++) {
			for (int j = 0; j < this.tableau[i].length; j++) {
				Label t = new Label(String.valueOf(this.tableau[i][j]));
				tab.add(t, i, j);
			}
		}
    	this.addGridEvent();
    }
    
    private void addGridEvent() {
    	
    	tab.getChildren().forEach(item -> {
    		item.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (event.getClickCount() == 2) {
                        System.out.println("doubleClick");
                    }
                    if (event.isPrimaryButtonDown()) {
                        System.out.println("PrimaryKey event");
                    }

                }
    		  });
    	});
    }
}