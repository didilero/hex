package hellofx;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application {
	
	public boolean fin2PartieSamuelbeckett = false;
	
	ArrayList<Noeud> noeuds = new ArrayList<Noeud>();
	
	StackPane plan;
	
	private final static int LARGEUR_FENETRE = 1280;
    private final static int LONGUEUR_FENETRE = 720;

    private final static double TAILLE_HEXAGONE = 30; // la taille en pixel en partant du haut vers le bas
    private final static double RAYON = Math.sqrt( TAILLE_HEXAGONE * TAILLE_HEXAGONE * 0.75); // le rayon d'un hexagone
    private final static double LONGUEUR_LOSANGE = 2 * TAILLE_HEXAGONE;
    private final static double LARGEUR_LOSANGE = 2 * RAYON;
    
    boolean rouge = true; // Boolean qui defini le tour du joueur soit Joueur Rouge / rouge == true sinon l'inverse
    
    List<Noeud> listBleuDebut = new ArrayList<Noeud>(); // List des Noeuds bleu de depart
    List<Noeud> listBleuArrive = new ArrayList<Noeud>(); // List des Noeuds bleu de fin
    List<Noeud> listRougeDebut = new ArrayList<Noeud>(); // List des Noeuds rouge de depart
    List<Noeud> listRougeArrive = new ArrayList<Noeud>(); // List des Noeuds rouge de fin
    
    List<List<Noeud>> matriceBleu = new ArrayList<List<Noeud>>();
    List<List<Noeud>> matriceRouge = new ArrayList<List<Noeud>>();
    
    public final int NB_CASE_LIGNE_COLONNE = 10; // la taille du plateau de jeu
    

    @Override
    public void start(Stage primaryStage) throws Exception{
        
        Group group = new Group();

        int xStartOffset = 20; // offsets the entire field to the right
        int yStartOffset = 20; // offsets the entire fiels downwards

        for (int x = 0; x < NB_CASE_LIGNE_COLONNE; x++) {
            for (int y = 0; y < NB_CASE_LIGNE_COLONNE; y++) {
            	
                double xCoord = x * LARGEUR_LOSANGE + y  * RAYON + xStartOffset;
                double yCoord = y * LONGUEUR_LOSANGE * 0.75 + yStartOffset;
                
                if (x == 0 || x == NB_CASE_LIGNE_COLONNE - 1) { // Pour set les bords du plateau
                	Hexagone tile = new Hexagone(xCoord, yCoord, Color.BLUE);
                    group.getChildren().add(tile);
                    Noeud currentNoeud = new Noeud(xCoord, yCoord, tile);
	                tile.leNoeudCorrespondant = currentNoeud;
                	this.noeuds.add(x * NB_CASE_LIGNE_COLONNE + y , currentNoeud);
                	
                	if (x == 0) {
                		this.listBleuDebut.add(currentNoeud);
                	} else {
                		this.listBleuArrive.add(currentNoeud);
                	}
                	
                } else if ( y == 0 || y == NB_CASE_LIGNE_COLONNE - 1) {
                		
                	Hexagone tile = new Hexagone(xCoord, yCoord, Color.RED);
                    group.getChildren().add(tile);
                    Noeud currentNoeud = new Noeud(xCoord, yCoord, tile);
	                tile.leNoeudCorrespondant = currentNoeud;
                	this.noeuds.add(x * NB_CASE_LIGNE_COLONNE + y, currentNoeud);
                	
                	if (y == 0) {
                		this.listRougeDebut.add(currentNoeud);
                	} else {
                		this.listRougeArrive.add(currentNoeud);
                	}
                	
                } else {
                	Hexagone tile = new Hexagone(xCoord, yCoord);
	                group.getChildren().add(tile);
	                Noeud currentNoeud = new Noeud(xCoord, yCoord, tile);
	                tile.leNoeudCorrespondant = currentNoeud;
                	this.noeuds.add(x * NB_CASE_LIGNE_COLONNE + y, currentNoeud); // correspond a l'index si x=0 et y = 3 = 3  
                }
            }
        }
        
        // la syntaxe new ArrayList<> (oldList) permet de copier la liste oldList pour la conservé intact
        this.matriceBleu.add(new ArrayList<> (this.listBleuDebut));
        this.matriceBleu.add(new ArrayList<> (this.listBleuArrive));
        
        this.matriceRouge.add(new ArrayList<> (this.listRougeDebut));
        this.matriceRouge.add(new ArrayList<> (this.listRougeArrive));
        
        this.plan = new StackPane(group);
        
        // Affichage
        primaryStage.setTitle("Hex");
        primaryStage.setScene(new Scene(plan, LARGEUR_FENETRE, LONGUEUR_FENETRE));
        primaryStage.show();
        
        
        System.out.println("Le joueur qui commence est le joueur rouge !");
    }


    public static void main(String[] args) {
        launch(args);
    }
    
    private class Hexagone extends Polygon {
    	public Noeud leNoeudCorrespondant;
    	
    	Hexagone(double x, double y) {
            // Formule pour creer un hexagone
            getPoints().addAll(
                    x, y,
                    x, y + TAILLE_HEXAGONE,
                    x + RAYON, y + TAILLE_HEXAGONE * 1.5,
                    x + LARGEUR_LOSANGE, y + TAILLE_HEXAGONE,
                    x + LARGEUR_LOSANGE, y,
                    x + RAYON, y - TAILLE_HEXAGONE * 0.5
            );

            // Création des interractions
            setFill(Color.ANTIQUEWHITE);
            setStrokeWidth(1);
            setStroke(Color.BLACK);
            setOnMouseClicked(e -> {
            	
            	if(!fin2PartieSamuelbeckett && getFill() == Color.ANTIQUEWHITE) {
            		this.changeColor();
            		updateListesVoisinages();
            		rouge = !rouge;
            		String joueur = rouge == true ? "rouge" : "non rouge";
            		if(!fin2PartieSamuelbeckett)
            			System.out.println("Au tour du joueur : " + joueur);
            	}
            	
            });
            
        }
        
    	Hexagone(double x, double y, Color color) {
    		// Formule pour creer un hexagone
            getPoints().addAll(
                    x, y,
                    x, y + TAILLE_HEXAGONE,
                    x + RAYON, y + TAILLE_HEXAGONE * 1.5,
                    x + LARGEUR_LOSANGE, y + TAILLE_HEXAGONE,
                    x + LARGEUR_LOSANGE, y,
                    x + RAYON, y - TAILLE_HEXAGONE * 0.5
            );
            setFill(color);
            
        }
        
        public void changeColor() {
        	if(rouge) {
        		this.setFill(Color.RED);
        		this.leNoeudCorrespondant.valeur = "rouge";
        	} else {
        		this.setFill(Color.BLUE);
        		this.leNoeudCorrespondant.valeur = "bleu";
        	}
        }
        
        /**
         * Méthode qui permet de connaitre ces voisins et de fusionner les differentes listes pour obtenir un chemin.
         * */
        public void updateListesVoisinages() {
        	List<List<Noeud>> currentMatrice = null;
        	if (rouge) {
        		currentMatrice = matriceRouge;
        	} else {
        		currentMatrice = matriceBleu;
        	}
        	
        	// On recupere les voisins du Noeud actuel
        	HashMap<String, Noeud> voisins = new HashMap<String, Noeud>();
        	Integer positionDuNoeudCourant = noeuds.indexOf(leNoeudCorrespondant);
        	voisins.put("sudEst", noeuds.get(positionDuNoeudCourant + 1));
        	voisins.put("sudOuest", noeuds.get(positionDuNoeudCourant - NB_CASE_LIGNE_COLONNE + 1 ));
        	voisins.put("ouEst", noeuds.get(positionDuNoeudCourant - NB_CASE_LIGNE_COLONNE ));
        	voisins.put("est", noeuds.get(positionDuNoeudCourant + NB_CASE_LIGNE_COLONNE ));
        	voisins.put("nordOuest", noeuds.get(positionDuNoeudCourant - 1 ));
        	voisins.put("nordEst", noeuds.get(positionDuNoeudCourant + NB_CASE_LIGNE_COLONNE - 1));
        	
        	// list vide nouveau voisinage
        	List<Noeud> listTemporis = new ArrayList<Noeud>(); 
        	
        	for ( Noeud v : voisins.values() ) {
        		//Si un des voisins est de la meme couleurs que le noeud courant alors
        		if ( v.forme.getFill() == leNoeudCorrespondant.forme.getFill() ) {
        			
        			Iterator<List<Noeud>> it = currentMatrice.iterator();
        			
        			while (it.hasNext()) {
        				List<Noeud> listActuelle = it.next();
        				
        				if (listActuelle.contains(v)) {
        					listTemporis.addAll(listActuelle);
        					it.remove();
        				}
        			}
        			
        		}
        	}
        	
        	checkGagnant(listTemporis);
        	listTemporis.add(leNoeudCorrespondant);
        	currentMatrice.add(listTemporis);
        	
        }
    }
    
    /**
     * Méthode pour parcourir la liste et regarder si elle possède au moins une case de départ et d'arrivé
     * @param List<Noeud>
     * */
    public void checkGagnant(List<Noeud> list) {
    	List<Noeud> listDebut = null;
    	List<Noeud> listArrive = null;
    	
    	if (rouge) {
    		
    		listDebut = this.listRougeDebut;
    		listArrive = this.listRougeArrive;
    	} else {
    		
    		listDebut = this.listBleuDebut;
    		listArrive = this.listBleuArrive;
    	}
    	
    	Iterator<Noeud> it = list.iterator();
    	boolean estDansDebut = false; 
    	boolean estDansArrive = false;
    	
    	while (it.hasNext()) {
    		Noeud chercher = it.next();
    		if (listDebut.contains(chercher)) {
    			estDansDebut = true;
    		}
    		if (listArrive.contains(chercher)) {
    			estDansArrive = true;
    		}
    		if (estDansDebut && estDansArrive) {
    			fin2PartieSamuelbeckett = true;
    			String vainc = rouge == true ? "ROUGE" : "BLEU";
    			System.out.println("Le Joueur de couleur : " + vainc + " remporte la partie. GG !");
    			
    			Label texteVictoire = new Label( "Le Joueur de couleur : " + vainc + " remporte la partie. GG !");
    			texteVictoire.setFont( new Font("Impact", 50) );
    			texteVictoire.setTextFill( Color.web("#33FF33") );
    			
    			plan.getChildren().add( texteVictoire );
    			return;
    		}
    	}
    }
}