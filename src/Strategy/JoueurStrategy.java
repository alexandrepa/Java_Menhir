package Strategy;

import java.util.ArrayList;

import Cartes.Allie;
import Cartes.Carte;
import Cartes.Ingredient;
import Exception.ActionException;
import Joueurs.Joueur;
import Joueurs.JoueurIA;
import Parties.Partie;
import Parties.Score;
/**
 * 
 * Interface qui permet d'implanter le patron Strategy
 *
 */
public interface JoueurStrategy {
	/**
	 * @param joueur Represente le joueur IA qui joue
	 * @param mainCarte Represente la main du joueur
	 * @param partie Represente la partie en cours
	 * 
	 * @throws ActionException Leve l'exeception si l'action choisie est inconnue
	 */
public void choixTourRapide(Joueur joueur, ArrayList<Carte> mainCarte,Partie partie) throws ActionException;
/**
 * @param joueur Represente le joueur IA qui joue
 * @param mainCarte Represente la main du joueur
 * @param partie Represente la partie en cours
 * 
 * @throws ActionException Leve l'exeception si l'action choisie est inconnue
 */
public void choixTourAvancee(Joueur joueur, ArrayList<Carte> mainCarte, Partie partie) throws ActionException;
/**
 * @param joueur Represente le joueur IA qui joue
 * @param mainCarte Represente la main du joueur
 * @param partie Represente la partie en cours
 * 
 * @return boolean Retourne vrai si l'IA pioche une carte, false si il récupere deux graines
 */
public boolean choixPiocheAllie(Joueur joueur, ArrayList mainJoueur, Partie partie);
/**
 * @param joueur Represente le joueur IA qui joue
 * @param mainCarte Represente la main du joueur
 * @param partie Represente la partie en cours
 * 
 * @return boolean Retourne vrai si l'IA joue la carte, false sinon
 */
public boolean choixJouerTaupe(Joueur joueur, Allie allie, Partie partie);
/**
 * @param joueur Represente le joueur IA qui joue
 * @param i Instance de l'ingredient qui a ciblé le joueur IA
 * @param a Instance de la carte allié Chien de l'IA
 * @param partie Represente la partie en cours
 * 
 * @return boolean Retourne vrai si l'IA joue la carte, false sinon
 */
public boolean choixJouerChien(Joueur jia, Ingredient i, Allie a, Partie partie);
}
