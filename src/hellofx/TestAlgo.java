package hellofx;
import junit.framework.*;


import org.junit.jupiter.api.Test;

import javafx.scene.shape.Polygon;

public class TestAlgo{

	@Test
	void testNoeud() {
		Noeud n = new Noeud(0.0, 0.0, null);
		assert n.valeur.equals("rien") : "Erreur d'initialisation car valeur egal : " + n.valeur;
	}
	
	@Test
	void testGagnant() {
		//Main h = new Hexagone(0.00, 0.0);
	}

}
