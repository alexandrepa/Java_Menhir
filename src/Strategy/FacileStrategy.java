package Strategy;

import java.util.ArrayList;
import java.util.Random;

import Cartes.Allie;
import Cartes.Carte;
import Cartes.Chien;
import Cartes.Ingredient;
import Cartes.Taupe;
import Exception.ActionException;
import Joueurs.Joueur;
import Joueurs.JoueurIA;
import Parties.Partie;
import Parties.PartieAvancee;
import Parties.Score;

/**
 * Strategie facile qui implémente le pattern Strategy
 *
 */
public class FacileStrategy implements JoueurStrategy {

	public FacileStrategy(){
		
	}
	
	/**
	 * @param joueur Represente le joueur IA qui joue
	 * @param mainCarte Represente la main du joueur
	 * @param partie Represente la partie en cours
	 * 
	 * @throws ActionException Leve l'exeception si l'action choisie est inconnue
	 */
	public void choixTourRapide(Joueur joueur, ArrayList<Carte> mainCarte,Partie partie) throws ActionException {
		// TODO Auto-generated method stub
		
		if(partie.getSaisonEnCoursChar() == 'P'){
			Random rand = new Random();
			Carte carteJoue = mainCarte.get(rand.nextInt(mainCarte.size()));
			joueur.jouer((Ingredient)carteJoue, "Geant", partie.getSaisonEnCoursChar(), partie.getScore());
			
		}
		else if(partie.getSaisonEnCoursChar() == 'E'){
			Random rand = new Random();
			Carte carteJoue = mainCarte.get(rand.nextInt(mainCarte.size()));
			joueur.jouer((Ingredient)carteJoue, "Engrais", partie.getSaisonEnCoursChar(), partie.getScore());
			
		}
		else if(partie.getSaisonEnCoursChar() == 'A'){
			Random rand = new Random();
			Carte carteJoue = mainCarte.get(rand.nextInt(mainCarte.size()));
			Joueur joueurVole = partie.getJoueurs().get(rand.nextInt(partie.getJoueurs().size()));
			joueur.jouer((Ingredient)carteJoue, joueurVole, partie.getSaisonEnCoursChar(), partie);
			
		}
		else{
			Random rand = new Random();
			Carte carteJoue = mainCarte.get(rand.nextInt(mainCarte.size()));
			joueur.jouer((Ingredient)carteJoue, "Engrais", partie.getSaisonEnCoursChar(), partie.getScore());
			
		}
		
       
	}
	/**
	 * @param joueur Represente le joueur IA qui joue
	 * @param mainCarte Represente la main du joueur
	 * @param partie Represente la partie en cours
	 * 
	 * @throws ActionException Leve l'exeception si l'action choisie est inconnue
	 */
	public void choixTourAvancee(Joueur joueur, ArrayList<Carte> mainCarte, Partie partie) throws ActionException{
		Random rand = new Random();
		Carte carteJoue = mainCarte.get(rand.nextInt(mainCarte.size()));
		int nbCarteMax = 0;
		while (carteJoue instanceof Allie && nbCarteMax < 6) {
			if (carteJoue instanceof Taupe){
				((JoueurIA) joueur).jouerTaupe(partie, (Allie) carteJoue);
			}else if(carteJoue instanceof Chien){
				
			}
			carteJoue = mainCarte.get(rand.nextInt(mainCarte.size()));
			nbCarteMax = nbCarteMax + 1;
		}
		if (partie.getSaisonEnCoursChar() == 'P') {

			joueur.jouer((Ingredient) carteJoue, "Geant", partie.getSaisonEnCoursChar(), partie.getScore());
			if (partie instanceof PartieAvancee) {
				((PartieAvancee) partie).getPaquetIngredient().getPaquetDeCarte().add(carteJoue);
			}
		} else if (partie.getSaisonEnCoursChar() == 'E') {
			joueur.jouer((Ingredient) carteJoue, "Engrais", partie.getSaisonEnCoursChar(), partie.getScore());
			if (partie instanceof PartieAvancee) {
				((PartieAvancee) partie).getPaquetIngredient().getPaquetDeCarte().add(carteJoue);
			}
		} else if (partie.getSaisonEnCoursChar() == 'A') {
			Joueur joueurVole = partie.getJoueurs().get(rand.nextInt(partie.getJoueurs().size()));
			joueur.jouer((Ingredient) carteJoue, joueurVole, partie.getSaisonEnCoursChar(), partie);
			if (partie instanceof PartieAvancee) {
				((PartieAvancee) partie).getPaquetIngredient().getPaquetDeCarte().add(carteJoue);
			}
		} else {
			joueur.jouer((Ingredient) carteJoue, "Engrais", partie.getSaisonEnCoursChar(), partie.getScore());
			if (partie instanceof PartieAvancee) {
				((PartieAvancee) partie).getPaquetIngredient().getPaquetDeCarte().add(carteJoue);
			}
		}
		
	}

	/**
	 * @param joueur Represente le joueur IA qui joue
	 * @param mainCarte Represente la main du joueur
	 * @param partie Represente la partie en cours
	 * 
	 * @return boolean Retourne vrai si l'IA pioche une carte, false si il récupere deux graines
	 */
	public boolean choixPiocheAllie(Joueur joueur, ArrayList mainJoueur, Partie partie) {
		Random rand = new Random();
		boolean choix = rand.nextBoolean();
		if (choix == true){
			joueur.getMainJoueur().add(((PartieAvancee)partie).getPaquetAllie().piocher());
		}else{
			partie.getScore().modifierNbGraine(2, joueur);;
		}
		return choix;
		
	}

	/**
	 * @param joueur Represente le joueur IA qui joue
	 * @param mainCarte Represente la main du joueur
	 * @param partie Represente la partie en cours
	 * 
	 * @return boolean Retourne vrai si l'IA joue la carte, false sinon
	 */
	public boolean choixJouerTaupe(Joueur joueur, Allie allie, Partie partie) {
		Random rand = new Random();
		boolean choix = rand.nextBoolean();
		if (choix == true){
			Random randJoueur = new Random();
			Joueur jCible = partie.getJoueurs().get(rand.nextInt(partie.getJoueurs().size()));
			joueur.jouer(allie, partie.getSaisonEnCoursChar(), partie.getScore(), jCible);
			if (partie instanceof PartieAvancee){
				((PartieAvancee)partie).getPaquetAllie().getPaquetDeCarte().add(allie);
			}
		}
		return choix;
		
	}
	/**
	 * @param joueur Represente le joueur IA qui joue
	 * @param i Instance de l'ingredient qui a ciblé le joueur IA
	 * @param a Instance de la carte allié Chien de l'IA
	 * @param partie Represente la partie en cours
	 * 
	 * @return boolean Retourne vrai si l'IA joue la carte, false sinon
	 */
	public boolean choixJouerChien(Joueur jia, Ingredient i, Allie a, Partie partie){
		Random rand = new Random();
		boolean choix = rand.nextBoolean();
		if (choix == true){
		}
		return choix;
	}


}
