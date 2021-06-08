package hellofx;

import javafx.scene.shape.Polygon;

public class Noeud {
	
	public String valeur;
	public double coordx;
	public double coordy;
	public Polygon forme;
	
	
	public Noeud(double cx, double cy, Polygon f) {
		this.coordx = cx;
		this.coordy = cy;
		this.valeur = "rien";
		this.forme = f;
	}
	
}
