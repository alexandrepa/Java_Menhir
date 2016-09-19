package Strategy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import Cartes.Allie;
import Cartes.Carte;
import Cartes.Ingredient;
import Cartes.Taupe;
import Exception.ActionException;
import Joueurs.Joueur;
import Joueurs.JoueurIA;
import Parties.Partie;
import Parties.PartieAvancee;
/**
 * 
 * Strategie normale qui implémente le pattern Strategy
 *
 */
public class NormalStrategy implements JoueurStrategy {
	/**
	 * @param joueur Represente le joueur IA qui joue
	 * @param mainCarte Represente la main du joueur
	 * @param partie Represente la partie en cours
	 * 
	 * @throws ActionException Leve l'exeception si l'action choisie est inconnue
	 */
	public void choixTourRapide(Joueur joueur, ArrayList<Carte> mainCarte, Partie partie) throws ActionException {
		// TODO Auto-generated method stub

		if (partie.getSaisonEnCoursChar() == 'P') {
			Ingredient maxGeant = null;
			for (Iterator itC = joueur.getMainJoueur().iterator(); itC.hasNext();) {
				Ingredient carte = (Ingredient) itC.next();
				if (maxGeant == null) {
					maxGeant = carte;
				} else if (carte.getGIndex(0) > maxGeant.getGIndex(0)) {
					maxGeant = carte;
				}

			}

			joueur.jouer(maxGeant, "Geant", partie.getSaisonEnCoursChar(), partie.getScore());
		} else if (partie.getSaisonEnCoursChar() == 'E') {
			Ingredient maxEngrais = null;
			for (Iterator itC = joueur.getMainJoueur().iterator(); itC.hasNext();) {
				Ingredient carte = (Ingredient) itC.next();
				if (maxEngrais == null) {
					maxEngrais = carte;
				} else if (carte.getEIndex(1) > maxEngrais.getEIndex(1)) {
					maxEngrais = carte;
				}

			}
			joueur.jouer(maxEngrais, "Engrais", partie.getSaisonEnCoursChar(), partie.getScore());
		} else if (partie.getSaisonEnCoursChar() == 'A') {

			Ingredient maxFarfadet = null;
			for (Iterator itC = joueur.getMainJoueur().iterator(); itC.hasNext();) {
				Ingredient carte = (Ingredient) itC.next();
				if (maxFarfadet == null) {
					maxFarfadet = carte;
				} else if (carte.getFIndex(2) > maxFarfadet.getEIndex(2)) {
					maxFarfadet = carte;
				}

			}
			Joueur joueurVole = null;

			for (Iterator itJ = partie.getJoueurs().iterator(); itJ.hasNext();) {
				Joueur j = (Joueur) itJ.next();
				if (j.getId() != joueur.getId()) {
					if (joueurVole == null) {
						joueurVole = j;
					} else if (partie.getScore().getNbGraineJoueur(j) > partie.getScore()
							.getNbGraineJoueur(joueurVole)) {
						joueurVole = j;

					}
				}
			}
			joueur.jouer(maxFarfadet, joueurVole, partie.getSaisonEnCoursChar(), partie);
		} else {
			Ingredient maxEngrais = null;
			for (Iterator itC = joueur.getMainJoueur().iterator(); itC.hasNext();) {
				Ingredient carte = (Ingredient) itC.next();
				if (maxEngrais == null) {
					maxEngrais = carte;
				} else if (carte.getEIndex(1) > maxEngrais.getEIndex(1)) {
					maxEngrais = carte;
				}

			}
			joueur.jouer(maxEngrais, "Engrais", partie.getSaisonEnCoursChar(), partie.getScore());
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
		// TODO Auto-generated method stub
		Random rand = new Random();
		boolean choix = rand.nextBoolean();
		if (choix == true) {
			joueur.getMainJoueur().add(((PartieAvancee) partie).getPaquetAllie().piocher());
		} else {
			partie.getScore().modifierNbGraine(2, joueur);
			;
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
		if (partie.getSaisonEnCoursChar() == 'H'){
			Random randJoueur = new Random();
			Joueur jCible = partie.getJoueurs().get(rand.nextInt(partie.getJoueurs().size()));
			joueur.jouer(allie, partie.getSaisonEnCoursChar(), partie.getScore(), jCible);
			((PartieAvancee)partie).getPaquetAllie().getPaquetDeCarte().add(allie);
			return true;
		}else{
			return false;
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
		if (partie.getSaisonEnCoursChar() == 'P') {
			Ingredient maxGeant = null;
			for (Iterator<Carte> itC = joueur.getMainJoueur().iterator(); itC.hasNext();) {
				Carte cTemp = itC.next();
				if (cTemp instanceof Ingredient){
					Ingredient carte = (Ingredient) cTemp;
					if (maxGeant == null) {
						maxGeant = carte;
					} else if (carte.getGIndex(0) > maxGeant.getGIndex(0)) {
						maxGeant = carte;
					}
				}
				

			}

			joueur.jouer(maxGeant, "Geant", partie.getSaisonEnCoursChar(), partie.getScore());
			((PartieAvancee)partie).getPaquetIngredient().getPaquetDeCarte().add(maxGeant);
		} else if (partie.getSaisonEnCoursChar() == 'E') {
			Ingredient maxEngrais = null;
			for (Iterator<Carte> itC = joueur.getMainJoueur().iterator(); itC.hasNext();) {
				Carte cTemp = itC.next();
				if (cTemp instanceof Ingredient){
					Ingredient carte = (Ingredient) cTemp;
					if (maxEngrais == null) {
						maxEngrais = carte;
					} else if (carte.getEIndex(1) > maxEngrais.getEIndex(1)) {
						maxEngrais = carte;
					}
				}
				

			}
			joueur.jouer(maxEngrais, "Engrais", partie.getSaisonEnCoursChar(), partie.getScore());
			((PartieAvancee)partie).getPaquetIngredient().getPaquetDeCarte().add(maxEngrais);
		} else if (partie.getSaisonEnCoursChar() == 'A') {

			Ingredient maxFarfadet = null;
			for (Iterator<Carte> itC = joueur.getMainJoueur().iterator(); itC.hasNext();) {
				Carte cTemp = itC.next();
				if (cTemp instanceof Ingredient){
					Ingredient carte = (Ingredient) cTemp;
					if (maxFarfadet == null) {
						maxFarfadet = carte;
					} else if (carte.getFIndex(2) > maxFarfadet.getEIndex(2)) {
						maxFarfadet = carte;
					}
				}
				

			}
			Joueur joueurVole = null;

			for (Iterator itJ = partie.getJoueurs().iterator(); itJ.hasNext();) {
				Joueur j = (Joueur) itJ.next();
				if (j.getId() != joueur.getId()) {
					if (joueurVole == null) {
						joueurVole = j;
					} else if (partie.getScore().getNbGraineJoueur(j) > partie.getScore()
							.getNbGraineJoueur(joueurVole)) {
						joueurVole = j;

					}
				}
			}
			joueur.jouer(maxFarfadet, joueurVole, partie.getSaisonEnCoursChar(), partie);
			((PartieAvancee)partie).getPaquetIngredient().getPaquetDeCarte().add(maxFarfadet);
		} else {
			Ingredient maxEngrais = null;
			for (Iterator<Carte> itC = joueur.getMainJoueur().iterator(); itC.hasNext();) {
				Carte cTemp = itC.next();
				if (cTemp instanceof Ingredient){
					Ingredient carte = (Ingredient) cTemp;
					if (maxEngrais == null) {
						maxEngrais = carte;
					} else if (carte.getEIndex(1) > maxEngrais.getEIndex(1)) {
						maxEngrais = carte;
					}
				}
			}
			boolean cAllieTrouvee = false;
			Iterator<Carte> itca = joueur.getMainJoueur().iterator();
			while (itca.hasNext() && cAllieTrouvee == false){
				Carte cTemp = itca.next();
				if (cTemp instanceof Taupe){
					this.choixJouerTaupe(joueur, (Allie)cTemp, partie);
					cAllieTrouvee = true;
				}
			}
			joueur.jouer(maxEngrais, "Engrais", partie.getSaisonEnCoursChar(), partie.getScore());
			((PartieAvancee)partie).getPaquetIngredient().getPaquetDeCarte().add(maxEngrais);
		}
	}

	/**
	 * @param joueur Represente le joueur IA qui joue
	 * @param i Instance de l'ingredient qui a ciblé le joueur IA
	 * @param a Instance de la carte allié Chien de l'IA
	 * @param partie Represente la partie en cours
	 * 
	 * @return boolean Retourne vrai si l'IA joue la carte, false sinon
	 */
	public boolean choixJouerChien(Joueur jia, Ingredient i, Allie a, Partie partie) {
		Random rand = new Random();
		boolean choix = rand.nextBoolean();
		if (choix == true){
		}
		return choix;
	}

}
