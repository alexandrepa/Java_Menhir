package Cartes;

import Joueurs.Joueur;
import Parties.Score;

/**
 * Classe qui représente une carte de type Allié
 * 	Possède un tableau d'entier, representants les valeurs en fonction des saisons de la carte
 *
 */



public abstract class Allie extends Carte{
	private int[] A;
	
	
	/**
	 * 
	 * @param nom Represente le nom de la carte allié (C1, T2, ...)
	 * @param description Represente la description de la carte (Larmes de dryade, ...)
	 * @param A Represente le tableau de la carte allié
	 */
	public Allie(String nom, String description, int[] A){
		super(nom, description);
		this.A = A;
	}

	public int[] getA() {
		return A;
	}

	public void setA(int[] a) {
		A = a;
	}
	
	/**
	 * Permet d'afficher dans la console les les différentes valeurs d'actions de la carte
	 * @deprecated
	 * @see Carte
	 */
	public void afficherCarte (){
		
		System.out.println("         P E A H");
		System.out.println("Action : "+ this.A[0] + " "+ this.A[1] + " "+ this.A[2] + " "+ this.A[3] );
		System.out.println("------------------");
	}

	/**
	 * 
	 * @param jCible
	 * @param saisonEnCours
	 * @param score
	 */
	public void actionCarte(Joueur jCible, char saisonEnCours, Score score) {}
	
}
