package Cartes;

import Joueurs.Joueur;
import Parties.Score;

/**
 * Classe qui représente une carte de type Chien
 *
 */

public class Chien extends Allie{
	
	/**
	 * 
	 * @param nom Represente le nom de la carte allié (C1, T2, ...)
	 * @param description Represente la description de la carte (Larmes de dryade, ...)
	 * @param A Represente le tableau de la carte allié
	 */
	public Chien(String nom, String description,  int[] A){
		super(nom, description, A);
	}
	

	/**
	 * 
	 * @param cIngredient Instance de la carte Ingredient qui a ciblé le joueur avec l'action farfadet
	 * @param saisonEnCours Saison en cours pour la partie en cours, transmise sous forme de caractère
	 * @return Retourne la valeur des graines protégées par le chien
	 */
public int actionCarteChien(Carte cIngredient, char saisonEnCours){
	this.setChanged();
	this.notifyObservers("Une carte chien a été jouée");
		if (this.getA()[this.saisonConversion(saisonEnCours)] >= ((Ingredient)cIngredient).getF()[saisonConversion(saisonEnCours)]){
			return ((Ingredient)cIngredient).getF()[saisonConversion(saisonEnCours)];
		}else{
			return this.getA()[this.saisonConversion(saisonEnCours)];
		}
		
		
	}

/**
 * Permet d'afficher les informations de la cartes ainsi que ses valeurs.
 * @deprecated
 * @see Carte
 */
public void afficherCarte(){
	System.out.println("------------------");
	System.out.println(this.getNom()+ " " + this.getDescription() + " Type : Allié");
	super.afficherCarte();
}
	
}
