package Cartes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import Joueurs.Joueur;
import Joueurs.JoueurIA;
import Joueurs.JoueurReel;
import Parties.Partie;
import Parties.Score;

/**
 * Classe qui représente une carte de type Ingrédient, elle est principalement
 * définie par 3 tableaux(Géant, Farfadet, Engrais) qui vont définir les
 * différentes valeurs de la carte.
 *
 *
 */

public class Ingredient extends Carte {

	private int[] F;
	private int[] E;
	private int[] G;
	private int grainesProtegee;

	public Ingredient(String nom, String description, int[] G, int[] E, int[] F) {
		super(nom, description);
		int i;
		this.F = F;
		this.E = E;
		this.G = G;
	}

	/**
	 * Permet au joueur qui joue cette carte de recupérer un nombre de graines
	 * défini par la carte pour la saison en cours.
	 * 
	 * @param mySelf
	 *            Joueur qui va recupérer les graines et qui a donc joué la
	 *            carte
	 * @param saisonEnCours
	 *            la saison en cours actuellement
	 * @param score
	 *            le score de la partie en cours
	 */
	public void recupGraine(Joueur mySelf, char saisonEnCours, Score score) {
		setChanged();
		this.notifyObservers(mySelf.nom()+" récupere " + this.G[this.saisonConversion(saisonEnCours)]
				+ " graines grâce a la carte " + this.getNom());
		score.modifierNbGraine(this.G[this.saisonConversion(saisonEnCours)], mySelf);

	}

	/**
	 * Permet au joueur qui joue cette carte de voler un nombre de graines à un
	 * autre joueur défini par la carte pour la saison en cours.
	 * 
	 * @param J
	 *            Joueur désigné par le joueur qui a joué la carte et qui va se
	 *            faire voler les graines.
	 * @param mySelf
	 *            Joueur qui joue la carte et qui souhaite donc voler les
	 *            graines
	 * @param saisonEnCours
	 *            la saison en cours actuellement
	 * @param score
	 *            le score de la partie en cours
	 */
	public void volerGraine(Joueur J, Joueur mySelf, char saisonEnCours, Partie partie) {
		
		int grainesVole;
		this.grainesProtegee = 0;
		boolean cAllieeTrouvee = false;
		Iterator<Carte> ita = J.getMainJoueur().iterator();
		while (ita.hasNext() && cAllieeTrouvee == false) {
			Carte aTemp = ita.next();
			if (aTemp instanceof Chien) {
				cAllieeTrouvee = true;
				if (J instanceof JoueurReel) {
					ArrayList<Object> tabICJ = new ArrayList<Object>();
					tabICJ.add(this);
					tabICJ.add(aTemp);
					tabICJ.add(J);
					this.setChanged();
					this.notifyObservers(tabICJ);					
				} else {
					grainesProtegee = ((JoueurIA) J).jouerChien(this, partie, saisonEnCours, (Chien)aTemp);
				}
			}
		}

		if (this.F[this.saisonConversion(saisonEnCours)] > partie.getScore().getNbGraineJoueur(J)) { // on
																							// vérifie
																							// que
																							// le
																							// joueur
																							// ne
																							// puisse
																							// pas
																							// voler
																							// plus
																							// de
																							// graines
																							// qu'il
																							// n'y
																							// en
																							// a
																							// de
			if (partie.getScore().getNbGraineJoueur(J) == 0){
				// disponible
				grainesVole = 0;
			}else{
				grainesVole = partie.getScore().getNbGraineJoueur(J) - grainesProtegee;
			}
			
		} else {
			if (partie.getScore().getNbGraineJoueur(J) == 0){
				// disponible
				grainesVole = 0;
			}else{
				grainesVole = this.F[this.saisonConversion(saisonEnCours)] - grainesProtegee;
			}
			
		}
		this.setChanged();
		this.notifyObservers(mySelf.nom()+" vole " + grainesVole + " graines grâce a la carte " + this.getNom() + " au Joueur " + J.getId());
		partie.getScore().modifierNbGraine((-1) * grainesVole, J);
		partie.getScore().modifierNbGraine(grainesVole, mySelf);
	
	}

	/**
	 * Permet au joueur qui joue cette carte de faire pousser un nombre de
	 * graines et donc de les transformer en menhir.
	 * 
	 * @param mySelf
	 *            Joueur qui joue la carte.
	 * @param saisonEnCours
	 *            la saison en cours actuellement
	 * @param score
	 *            le score de la partie en cours
	 */
	public void fairePousser(Joueur mySelf, char saisonEnCours, Score score) {
		int grainesPousse;
		if (this.E[this.saisonConversion(saisonEnCours)] > score.getNbGraineJoueur(mySelf)) {// on
																								// vérifie
																								// que
																								// le
																								// joueur
																								// ne
																								// puisse
																								// pas
																								// faire
																								// pousser
																								// plus
																								// de
																								// menhir
																								// qu'il
																								// n'a
																								// de
																								// graines.
			grainesPousse = score.getNbGraineJoueur(mySelf);
		} else {
			grainesPousse = this.E[this.saisonConversion(saisonEnCours)];
		}
		this.setChanged();
		this.notifyObservers(mySelf.nom()+" fait poussez " + grainesPousse + " graines grâce a la carte " + this.getNom());
		score.modifierNbGraine((-1) * grainesPousse, mySelf);
		score.modifierNbMenhir(grainesPousse, mySelf);

	}

	public int[] getF() {
		return F;
	}

	public void setF(int[] f) {
		F = f;
	}

	public int[] getE() {
		return E;
	}

	public void setE(int[] e) {
		E = e;
	}

	public int[] getG() {
		return G;
	}

	public void setG(int[] g) {
		G = g;
	}

	public int getFIndex(int index) {
		return F[index];
	}

	public int getEIndex(int index) {
		return E[index];
	}

	public int getGIndex(int index) {
		return G[index];
	}

	public int getGrainesProtegee() {
		return grainesProtegee;
	}

	public void setGrainesProtegee(int grainesProtegee) {
		this.grainesProtegee = grainesProtegee;
	}

	/**
	 * Permet d'afficher la carte et ses valeurs dans la console
	 */
	public void afficherCarte() {
		System.out.println("------------------");
		System.out.println(this.getNom() + " " + this.getDescription() + " Type : Ingrédient");
		System.out.println("           P E A H");
		System.out.println("Géant    : " + this.G[0] + " " + this.G[1] + " " + this.G[2] + " " + this.G[3]);
		System.out.println("Engrais  : " + this.E[0] + " " + this.E[1] + " " + this.E[2] + " " + this.E[3]);
		System.out.println("Farfadet : " + this.F[0] + " " + this.F[1] + " " + this.F[2] + " " + this.F[3]);
		System.out.println("------------------");
	}

}
