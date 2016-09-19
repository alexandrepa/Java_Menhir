package Cartes;
import java.util.Scanner;

import Joueurs.Joueur;
import Parties.Partie;
import Parties.Score;
/**
 * Classe qui r�presente une carte de type Taupe
 *
 */

public class Taupe extends Allie{
	
	/**
	 * 
	 * @param nom Represente le nom de la carte alli� (C1, T2, ...)
	 * @param description Represente la description de la carte (Chien, ...)
	 * @param A Represente le tableau de la carte alli�
	 */
	public Taupe(String nom, String description, int[] A){
		super(nom, description,  A);
	}
	
	/**
	 * 
	 * @param jCible Instance du joueur cibl� par la carte taupe jou�e
	 * @param saisonEnCours Saison en cours pour la partie en cours, transmise sous forme de caract�re
	 * @param score Instance du score de la partie
	 */
	public void actionCarte(Joueur jCible, char saisonEnCours, Score score){
		switch (saisonEnCours) {
		case 'P' :
			if (this.getA()[0] < score.getNbMenhirJoueur(jCible)){
				score.modifierNbMenhir(-this.getA()[0], jCible);
				this.setChanged();
				this.notifyObservers("Le joueur " + jCible.getId() + " a perdu " + this.getA()[0] + " menhirs car une taupe l'a attaqu�");
				break;
			}else{
				int menhirsdetruits = -score.getNbMenhirJoueur(jCible); 
				score.modifierNbMenhir(menhirsdetruits, jCible);
				this.setChanged();
				this.notifyObservers("Le joueur " + jCible.getId() + " a perdu " + -menhirsdetruits + " menhirs car une taupe l'a attaqu�");
				break;
			}	
		case 'E' : 
			if (this.getA()[1] < score.getNbMenhirJoueur(jCible)){
				score.modifierNbMenhir(-this.getA()[1], jCible);
				this.setChanged();
				this.notifyObservers("Le joueur " + jCible.getId() + " a perdu " + this.getA()[1] + " menhirs car une taupe l'a attaqu�");
				break;
			}else{
				int menhirsdetruits = -score.getNbMenhirJoueur(jCible); 
				score.modifierNbMenhir(menhirsdetruits, jCible);
				this.setChanged();
				this.notifyObservers("Le joueur " + jCible.getId() + " a perdu " + -menhirsdetruits + " menhirs car une taupe l'a attaqu�");
				break;
			}
		case 'A' : 
			if (this.getA()[2] < score.getNbMenhirJoueur(jCible)){
				score.modifierNbMenhir(-this.getA()[2], jCible);
				this.setChanged();
				this.notifyObservers("Le joueur " + jCible.getId() + " a perdu " + this.getA()[2] + " menhirs car une taupe l'a attaqu�");
				break;
			}else{
				int menhirsdetruits = -score.getNbMenhirJoueur(jCible);
				score.modifierNbMenhir(menhirsdetruits, jCible);
				this.setChanged();
				this.notifyObservers("Le joueur " + jCible.getId() + " a perdu " + -menhirsdetruits + " menhirs car une taupe l'a attaqu�");
				break;
			}
		case 'H' : 
			if (this.getA()[3] < score.getNbMenhirJoueur(jCible)){
				score.modifierNbMenhir(-this.getA()[3], jCible);
				this.setChanged();
				this.notifyObservers("Le joueur " + jCible.getId() + " a perdu " + this.getA()[3] + " menhirs car une taupe l'a attaqu�");
				break;
			}else{
				int menhirsdetruits = -score.getNbMenhirJoueur(jCible);
				score.modifierNbMenhir(menhirsdetruits, jCible);
				this.setChanged();
				this.notifyObservers("Le joueur " + jCible.getId() + " a perdu " + -menhirsdetruits + " menhirs car une taupe l'a attaqu�");
				break;
			}
		}
		
	}
	
	/**
	 * Permet d'afficher dans la console les valeurs de la carte
	 * @deprecated
	 * @see Carte
	 */
	public void afficherCarte(){
		System.out.println("------------------");
		System.out.println(this.getNom()+ " " + this.getDescription() + " Type : Alli�");
		super.afficherCarte();
	}
}
