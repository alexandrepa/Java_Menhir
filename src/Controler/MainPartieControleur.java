package Controler;

import java.util.Iterator;

import Cartes.Allie;
import Cartes.Carte;
import Cartes.Ingredient;
import Exception.CarteException;
import Exception.JoueurException;
import Interface.CarteGraphique;
import Interface.VueGraphique;
import Joueurs.Joueur;
import Joueurs.JoueurReel;
import Parties.Partie;
import Parties.PartieRapide;
import Parties.PartieAvancee;
/**
 * Controleur qui va s'occuper de gérér la partie, c'est le lien entre la vue et le modèle.
 * 
 * @author Alexandre
 *
 */

public class MainPartieControleur {
private Partie partie;
private VueGraphique vueG;


public MainPartieControleur(){
	

}
/**
 * Permet d'initialiser une partie rapide et d'ajouter les differents Observers.
 * 
 * 
 */
public void debuterPartieRapide(){
	this.partie = vueG.getPartie();														  
	partie.addObserver(vueG);
	
	this.partie.debutPartie();
	for(Iterator<Joueur> itJ = this.partie.getJoueurs().iterator();itJ.hasNext();){
		Joueur joueur = itJ.next();
		joueur.addObserver(vueG);
		for(Iterator itC = joueur.getMainJoueur().iterator();itC.hasNext();){
			Carte carte = (Carte) itC.next();
			carte.addObserver(vueG);
		}
	}

		this.partie.changeJoueurActif();
		this.partie.chgtTour();
	
}
/**
 * Permet d'initialiser une partie avancee et d'ajouter les differents Observers.
 * 
 * 
 */
public void debuterPartieAvancee(){
	this.partie = vueG.getPartie();														  
	partie.addObserver(vueG);
	
	this.partie.debutPartie();
	for(Iterator<Joueur> itJ = this.partie.getJoueurs().iterator();itJ.hasNext();){
		Joueur joueur = itJ.next();
		joueur.addObserver(vueG);
		
	}
	
	

		this.partie.changeJoueurActif();
		this.partie.chgtTour();
}

/**
 * Permet au joueur de jouer une carte et de gérer son affichage
 * 
 * @param carteGraphique la carteGraphique que le joueur veut jouer
 * @param action l'action qu'il souhaite jouer	
 * @param joueurcible le joueur cible de l'action si il y en a un sinon null
 */
public void jouerCarte(CarteGraphique carteGraphique, String action, Joueur joueurcible) {
	// TODO Auto-generated method stub
		
		if (partie.getJoueurActif() instanceof JoueurReel) {
			vueG.getMainPanel().remove(carteGraphique);
			vueG.getCartesG().remove(carteGraphique);
			vueG.repaint();
			Carte carte = null;
			try {
				carte = partie.getJoueurActif().getMainCarteByName(carteGraphique.getNom());
				carte.afficherCarte();

				if (action.equals("Engrais")) {
					partie.getJoueurActif().jouer((Ingredient) carte, "Engrais", partie.getSaisonEnCoursChar(),
							partie.getScore());
				} else if (action.equals("Geant")) {
					partie.getJoueurActif().jouer((Ingredient) carte, "Geant", partie.getSaisonEnCoursChar(),
							partie.getScore());
				} else if (action.equals("farfadet")) {
					partie.getJoueurActif().jouer((Ingredient) carte, joueurcible, partie.getSaisonEnCoursChar(),
							this.partie);
				} else if (action.equals("taupe")) {
					partie.getJoueurActif().jouer((Allie) carte, partie.getSaisonEnCoursChar(), partie.getScore(),
							joueurcible);
				} else if (action.equals("chien")) {

				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (carte instanceof Ingredient) {
				partie.changeJoueurActif();
				partie.chgtTour();
			}
		}		
}

	public Partie getPartie() {
	return partie;
}
public void setPartie(Partie partie) {
	this.partie = partie;
}
public VueGraphique getVueG() {
	return vueG;
}
public void setVueG(VueGraphique vueG) {
	this.vueG = vueG;
}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		MainPartieControleur mainControl = new MainPartieControleur();
		VueGraphique test = new VueGraphique(mainControl); 
		
	
		
		test.vueMenu();

	}
	

}
