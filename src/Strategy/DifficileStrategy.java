package Strategy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import Cartes.Allie;
import Cartes.Carte;
import Cartes.Ingredient;
import Cartes.Taupe;
import Exception.ActionException;
import Exception.JoueurException;
import Joueurs.Joueur;
import Joueurs.JoueurIA;
import Parties.Partie;
import Parties.PartieAvancee;

/**
 * 
 * Classe qui represente le comportement d'une IA difficile
 * Cette classe implemente l'interface JoueurStrategy et s'inscrit dans le patron Strategy
 *
 */
public class DifficileStrategy implements JoueurStrategy {

	public DifficileStrategy(){
		
	}
	/**
	 * @param joueur Represente le joueur IA qui joue
	 * @param mainCarte Represente la main du joueur
	 * @param partie Represente la partie en cours
	 * 
	 * @throws ActionException Leve l'exeception si l'action choisie est inconnue
	 */
	public void choixTourRapide(Joueur joueur, ArrayList<Carte> mainCarte,Partie partie) throws ActionException{
		// TODO Auto-generated method stub
		char actionAJouer = 'G';
		Ingredient carte=null;
		if(partie.getSaisonEnCoursChar() == 'P'){
		Ingredient[] P_G = new Ingredient[4];
		this.trierCarte(P_G, 0, 'G', mainCarte);
		Ingredient[] P_E = new Ingredient[4];
		this.trierCarte(P_E, 0, 'E', mainCarte);
		Ingredient[] P_F = new Ingredient[4];
		this.trierCarte(P_F, 0, 'F', mainCarte);
		
		Ingredient[] P = P_G;

		int valeurmax = P[0].getGIndex(0);
		char actionMax='G';
		if(valeurmax<P_E[0].getEIndex(0)){
			P=P_E;
			valeurmax = P_E[0].getEIndex(0);
			actionMax='E';
		}
		if(valeurmax<P_F[0].getFIndex(0)){
			P=P_F;
			actionMax='F';
		}
		carte = P[0];
		actionAJouer = actionMax; 
		}
		else if(partie.getSaisonEnCoursChar() == 'E'){
			Ingredient[] E_G = new Ingredient[3];
			this.trierCarte(E_G, 0, 'G', mainCarte);
			Ingredient[] E_E = new Ingredient[3];
			this.trierCarte(E_E, 0, 'E', mainCarte);
			Ingredient[] E_F = new Ingredient[3];
			this.trierCarte(E_F, 0, 'F', mainCarte);
			
			Ingredient[] E = E_G;

			int valeurmax = E[0].getGIndex(0)-1;
			char actionMax='G';
			if(valeurmax<E_E[0].getEIndex(0)){
				E=E_E;
				valeurmax = E_E[0].getEIndex(0);
				actionMax='E';
			}
			if(valeurmax<E_F[0].getFIndex(0)){
				E=E_F;
				actionMax='F';
			}
			carte = E[0];
			actionAJouer = actionMax; 
		}
		else if(partie.getSaisonEnCoursChar() == 'A'){
			Ingredient[] A_G = new Ingredient[2];
			this.trierCarte(A_G, 0, 'G', mainCarte);
			Ingredient[] A_E = new Ingredient[2];
			this.trierCarte(A_E, 0, 'E', mainCarte);
			Ingredient[] A_F = new Ingredient[2];
			this.trierCarte(A_F, 0, 'F', mainCarte);
			
			Ingredient[] A = A_G;

			int valeurmax = A[0].getGIndex(0)-2;
			char actionMax='G';
			if(valeurmax<A_E[0].getEIndex(0)){
				A=A_E;
				valeurmax = A_E[0].getEIndex(0);
				actionMax='E';
			}
			if(valeurmax<A_F[0].getFIndex(0)){
				A=A_F;
				actionMax='F';
			}
			carte = A[0];
			actionAJouer = actionMax; 
		}
		
		else if(partie.getSaisonEnCoursChar() == 'H'){
			Ingredient[] H_G = new Ingredient[1];
			this.trierCarte(H_G, 0, 'G', mainCarte);
			Ingredient[] H_E = new Ingredient[1];
			this.trierCarte(H_E, 0, 'E', mainCarte);
			Ingredient[] H_F = new Ingredient[1];
			this.trierCarte(H_F, 0, 'F', mainCarte);
			
			Ingredient[] H = H_G;

			
			char actionMax='E';
			
			carte = H[0];
			actionAJouer = actionMax; 
		}
		if(actionAJouer == 'G'){
			joueur.jouer(carte, "Geant", partie.getSaisonEnCoursChar(), partie.getScore());
			
		}
		else if(actionAJouer == 'E'){
			joueur.jouer(carte, "Engrais", partie.getSaisonEnCoursChar(), partie.getScore());
			
		}
		else if(actionAJouer == 'F'){
			Joueur joueurVole=null;
			for(Iterator itJ = partie.getJoueurs().iterator(); itJ.hasNext();){
            	Joueur j = (Joueur) itJ.next();
            	if(joueurVole == null){
            		joueurVole = j;
            	}
            	if(j.getId()!=joueur.getId()){
            	
            	if(partie.getScore().getNbGraineJoueur(j)>partie.getScore().getNbGraineJoueur(joueurVole)) {
            		joueurVole = j;
            		
            	}
            	}
			
			}
			joueur.jouer(carte, joueurVole, partie.getSaisonEnCoursChar(), partie);
			
		}
		
		
		
		
	}


	/**
	 * @param joueur Represente le joueur IA qui joue
	 * @param mainCarte Represente la main du joueur
	 * @param partie Represente la partie en cours
	 * 
	 * @throws ActionException Leve l'exeception si l'action choisie est inconnue
	 */
	public void choixTourAvancee(Joueur joueur, ArrayList<Carte> mainCarte, Partie partie) throws ActionException {
		char actionAJouer = 'G';
		Ingredient carte=null;
		if(partie.getSaisonEnCoursChar() == 'P'){
		Ingredient[] P_G = new Ingredient[4];
		this.trierCarte(P_G, 0, 'G', mainCarte);
		Ingredient[] P_E = new Ingredient[4];
		this.trierCarte(P_E, 0, 'E', mainCarte);
		Ingredient[] P_F = new Ingredient[4];
		this.trierCarte(P_F, 0, 'F', mainCarte);
		
		Ingredient[] P = P_G;

		int valeurmax = P[0].getGIndex(0);
		char actionMax='G';
		if(valeurmax<P_E[0].getEIndex(0)){
			P=P_E;
			valeurmax = P_E[0].getEIndex(0);
			actionMax='E';
		}
		if(valeurmax<P_F[0].getFIndex(0)){
			P=P_F;
			actionMax='F';
		}
		carte = P[0];
		actionAJouer = actionMax; 
		}
		else if(partie.getSaisonEnCoursChar() == 'E'){
			Ingredient[] E_G = new Ingredient[3];
			this.trierCarte(E_G, 0, 'G', mainCarte);
			Ingredient[] E_E = new Ingredient[3];
			this.trierCarte(E_E, 0, 'E', mainCarte);
			Ingredient[] E_F = new Ingredient[3];
			this.trierCarte(E_F, 0, 'F', mainCarte);
			
			Ingredient[] E = E_G;

			int valeurmax = E[0].getGIndex(0)-1;
			char actionMax='G';
			if(valeurmax<E_E[0].getEIndex(0)){
				E=E_E;
				valeurmax = E_E[0].getEIndex(0);
				actionMax='E';
			}
			if(valeurmax<E_F[0].getFIndex(0)){
				E=E_F;
				actionMax='F';
			}
			carte = E[0];
			actionAJouer = actionMax; 
		}
		else if(partie.getSaisonEnCoursChar() == 'A'){
			Ingredient[] A_G = new Ingredient[2];
			this.trierCarte(A_G, 0, 'G', mainCarte);
			Ingredient[] A_E = new Ingredient[2];
			this.trierCarte(A_E, 0, 'E', mainCarte);
			Ingredient[] A_F = new Ingredient[2];
			this.trierCarte(A_F, 0, 'F', mainCarte);
			
			Ingredient[] A = A_G;

			int valeurmax = A[0].getGIndex(0)-2;
			char actionMax='G';
			if(valeurmax<A_E[0].getEIndex(0)){
				A=A_E;
				valeurmax = A_E[0].getEIndex(0);
				actionMax='E';
			}
			if(valeurmax<A_F[0].getFIndex(0)){
				A=A_F;
				actionMax='F';
			}
			carte = A[0];
			actionAJouer = actionMax; 
		}
		
		else if(partie.getSaisonEnCoursChar() == 'H'){
			Iterator<Carte> itmc = mainCarte.iterator();
			boolean cAllieTrouvee = false;
			while (itmc.hasNext() && !cAllieTrouvee){
				Carte cTemp = itmc.next();
				if (cTemp instanceof Taupe){
					this.choixJouerTaupe(joueur, (Allie)cTemp, partie);
					cAllieTrouvee = true;
				}
			}
			Ingredient[] H_G = new Ingredient[1];
			this.trierCarte(H_G, 0, 'G', mainCarte);
			Ingredient[] H_E = new Ingredient[1];
			this.trierCarte(H_E, 0, 'E', mainCarte);
			Ingredient[] H_F = new Ingredient[1];
			this.trierCarte(H_F, 0, 'F', mainCarte);
			
			Ingredient[] H = H_G;

			
			char actionMax='E';
			
			carte = H[0];
			actionAJouer = actionMax; 
		}
		if(actionAJouer == 'G'){
			joueur.jouer(carte, "Geant", partie.getSaisonEnCoursChar(), partie.getScore());
			((PartieAvancee)partie).getPaquetIngredient().getPaquetDeCarte().add(carte);
			
		}
		else if(actionAJouer == 'E'){
			joueur.jouer(carte, "Engrais", partie.getSaisonEnCoursChar(), partie.getScore());
			((PartieAvancee)partie).getPaquetIngredient().getPaquetDeCarte().add(carte);
			
		}
		else if(actionAJouer == 'F'){
			Joueur joueurVole=null;
			for(Iterator itJ = partie.getJoueurs().iterator(); itJ.hasNext();){
            	Joueur j = (Joueur) itJ.next();
            	if(joueurVole == null){
            		joueurVole = j;
            	}
            	if(j.getId()!=joueur.getId()){
            	
            	if(partie.getScore().getNbGraineJoueur(j)>partie.getScore().getNbGraineJoueur(joueurVole)) {
            		joueurVole = j;
            		
            	}
            	}
			
			}
			joueur.jouer(carte, joueurVole, partie.getSaisonEnCoursChar(), partie);
			if (partie instanceof PartieAvancee){
				((PartieAvancee)partie).getPaquetIngredient().getPaquetDeCarte().add(carte);
				
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
		// TODO Auto-generated method stub
		Random rand = new Random();
		boolean choix = rand.nextBoolean();
		if (choix == true) {
			joueur.getMainJoueur().add(((PartieAvancee) partie).getPaquetAllie().piocher());
		} else {
			partie.getScore().modifierNbGraine(2, joueur);
			
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
		if (partie.getSaisonEnCoursChar() == 'H'){
			Iterator<Joueur> itj = partie.getJoueurs().iterator();
			int jCible = 0;
			while (itj.hasNext()){
				Joueur jTemp = itj.next();
				if ((partie.getScore().getNbMenhir()[jTemp.getId()] > partie.getScore().getNbMenhir()[jCible]) && (jTemp.getId() != joueur.getId())){
					jCible = jTemp.getId();
				}
			}
			try {
				joueur.jouer(allie, partie.getSaisonEnCoursChar(), partie.getScore(), partie.getJoueurById(jCible));
				((PartieAvancee)partie).getPaquetAllie().getPaquetDeCarte().add(allie);
			} catch (JoueurException e) {
				e.printStackTrace();
			}
			return true;
		}else{
			return false;
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
		boolean choix = false;
		if (partie.getScore().getNbMenhirJoueur((Joueur)jia) > a.getA()[a.saisonConversion(partie.getSaisonEnCoursChar())]){
			choix = true;
		}
		return choix;
	}
	/**
	 * 
	 * @param tableau Tableau de cartes ingrédients
	 * @param saison saison en cours, sous forme d'entier
	 * @param action action a jouer
	 * @param mainCarte main du joueur
	 */
	public void trierCarte(Ingredient[] tableau, int saison, char action, ArrayList<Carte> mainCarte){
		int compteur =0;
		Iterator<Carte> itC = mainCarte.iterator();
		while(itC.hasNext()){
			Carte cTemp = itC.next();
			
			if (cTemp instanceof Ingredient){
				Ingredient carte = (Ingredient) cTemp;
				tableau[compteur]=carte;
				compteur++;
			}
		}
		
		int longueur =tableau.length;
		boolean permut;
		do{
			permut=false;
			for(int i=0; i< longueur-1;i++){
				if(action=='F'){
					if(tableau[i].getFIndex(saison)<tableau[i+1].getFIndex(saison)){
						Ingredient temp = tableau[i];
						tableau[i]=tableau[i+1];
						tableau[i+1]=temp;
						permut=true;
					}
				}
				else if(action=='G'){
					if(tableau[i].getGIndex(saison)<tableau[i+1].getGIndex(saison)){
						Ingredient temp = tableau[i];
						tableau[i]=tableau[i+1];
						tableau[i+1]=temp;
						permut=true;
					}
				}
				else if(action=='E'){
					if(tableau[i].getEIndex(saison)<tableau[i+1].getEIndex(saison)){
						Ingredient temp = tableau[i];
						tableau[i]=tableau[i+1];
						tableau[i+1]=temp;
						permut=true;
					}
					}
			}
		
		}while(permut);
	}
	
	

}
