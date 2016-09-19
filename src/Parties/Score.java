package Parties;
import java.util.ArrayList;
import java.util.Iterator;

import Exception.JoueurException;
import Joueurs.Joueur;
/**
 * 
 * Classe qui repr�sente le score d'une partie.
 * Englobe la totalit� des scores de tous les joueurs.
 *
 */

public class Score {
	/**
	 * L'index du tableau correspond � l'id du joueur.
	 * Le nombre de graines du joueur 1 est donc � la case "1" du tableau
	 */
 private int[] nbGraine;
	/**
	 * L'index du tableau correspond � l'id du joueur.
	 * Le nombre de menhirs du joueur 1 est donc � la case "1" du tableau
	 */

 private int[] nbMenhir;
 private ArrayList<Joueur> joueurs;
 
 /**
  * 
  * @param nbGraine tableau de score des graines
  * @param nbMenhir tableau de score des menhirs
  */
public Score(int[] nbGraine, int[] nbMenhir) {
	this.nbGraine = nbGraine;
	this.nbMenhir = nbMenhir;
}
/**
 * D�termine le gagnant d'une partie
 * @param partie La partie dont on souhaite trouver le gagnant
 * @return le gagnant de la partie
 */
public Joueur determinerGagnant(Partie partie){
	int gagnant = 0;
	for(int i=0; i<nbMenhir.length;i++){
	 if(nbMenhir[i]>nbMenhir[gagnant]){
		 gagnant=i;
	 }
	else if(nbMenhir[i]==nbMenhir[gagnant]){
			if(nbGraine[i]>nbGraine[gagnant]){
				gagnant=i;
			}
			
			
		}
	}
	Joueur jGagnant = null;
	try{
	jGagnant = partie.getJoueurById(gagnant);
	}
	catch( JoueurException e){
		System.out.println("Impossible de determiner le gagnant");
	}
	return jGagnant;
}
/**
 * Permet d'ajouter ou d'enlever un nombre de graines � un joueur.
 * @param nombre Le nombre de graines, n�gatif si on souhaite en retirer et positif si on souhaite en ajouter.
 * @param joueur le joueur auquel il faut modifier le nombre de graines
 */
public void modifierNbGraine(int nombre, Joueur joueur){
this.nbGraine[joueur.getId()]+=nombre;	
}
/**
 * Permet d'ajouter ou d'enlever un nombre de menhirs � un joueur.
 * @param nombre Le nombre de menhirs, n�gatif si on souhaite en retirer et positif si on souhaite en ajouter.
 * @param joueur le joueur auquel il faut modifier le nombre de menhirs.
 */
public void modifierNbMenhir(int nombre, Joueur joueur){
this.nbMenhir[joueur.getId()]+=nombre;	
}

public int[] getNbGraine() {
	return nbGraine;
}
/**
 * Permet de r�cuperer le nombre de graines d'un joueur.
 * @param joueur le joueur dont on souhaite connaitre le nombre de graines.
 * @return le nombre de graines du joueur.
 */
public int getNbGraineJoueur(Joueur joueur){
	return nbGraine[joueur.getId()];
}

public void setNbGraine(int[] nbGraine) {
	this.nbGraine = nbGraine;
}

public int[] getNbMenhir() {
	return nbMenhir;
}
public int getNbMenhirJoueur(Joueur joueur){
	return nbMenhir[joueur.getId()];
}

public void setNbMenhir(int[] nbMenhir) {
	this.nbMenhir = nbMenhir;
}

public ArrayList<Joueur> getJoueurs() {
	return joueurs;
}

public void setJoueurs(ArrayList<Joueur> joueurs) {
	this.joueurs =joueurs;
}
/**
 * Permet d'afficher les scores des joueurs dans la console.
 * 
 */
public void afficherScore(){

	for(Iterator<Joueur> itJ = this.joueurs.iterator(); itJ.hasNext(); ){
		Joueur joueur = itJ.next();
		System.out.println(joueur.nom()+" --> Graines : "+nbGraine[joueur.getId()]+", Menhir : "+nbMenhir[joueur.getId()] );
	}
	System.out.println();
	
	
}
/**
 * Initialisation des scores au debut du tour
 */
public void initialiserScore(){
	for (int i = 0; i < Joueur.getNbJoueurs(); i++){ //initialisation des scores  
		this.nbGraine[i] = this.nbGraine[i] + 2;
	}
}

 
 
 
}
