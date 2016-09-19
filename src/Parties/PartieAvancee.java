package Parties;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.Timer;

import Cartes.Allie;
import Cartes.Carte;
import Cartes.Chien;
import Cartes.Ingredient;
import Cartes.Taupe;
import Exception.ActionException;
import Exception.CarteException;
import Exception.JoueurException;
import Interface.VueChoixAllieGraines;
import Joueurs.Joueur;
import Joueurs.JoueurIA;
import Joueurs.JoueurReel;
import Paquets.PAllie;
import Paquets.PIngredient;

/**
 * Classe qui représente une partie Avancée.
 *
 */
public class PartieAvancee extends Partie {
	/**
	 * stocke la manche en cours pour la partie
	 */
	private int mancheEnCours;
	/**
	 * Attribut du paquet d'ingrédient de la partie Avancee
	 */
	private PIngredient paquetIngredient;
	/**
	 * Attribut du paquet de cartes Allie de la partie Rapide
	 */
	private PAllie paquetAllie;

	public PartieAvancee() {
		super();
		this.mancheEnCours = 0; // initialisée a 0, pour permettre de proposer
								// aux joueurs lors du debut de la partie de
								// piocher une carte allie ou de recuperer deux
								// graines
	}
/**
 * Initialisation de la partie, Dans cette methode, les paquets sont générés, mélangés, et distribués aux différents joueurs de la partie
 * Cette methode determine également quel est le joueur le plus jeune
 */
	public void debutPartie() {

		this.genererPaquet();
		this.setChanged();
		this.notifyObservers("cartes_generate");
		this.paquetIngredient.melanger();
		this.paquetAllie.melanger();
		this.distribuer();
		Iterator<Joueur> it = this.getJoueurs().iterator();
		youngJoueur = it.next();
		while(it.hasNext()){            // on parcourt la liste des joueurs et on affecte a la variables youngJoueur le joueur le plus jeune
			Joueur jTemp = it.next();
			if (jTemp.getAge() < youngJoueur.getAge()){
				youngJoueur=jTemp;
			}
		}
		
		if(getJoueurs().indexOf(youngJoueur)!=0){
		this.setJoueurActif(getJoueurs().get(getJoueurs().indexOf(youngJoueur)-1));
		}
		
		int[] nbGraines = { 0, 0, 0, 0, 0, 0 };
		int[] nbMenhirs = { 0, 0, 0, 0, 0, 0 };
		Score score = new Score(nbGraines, nbMenhirs);
		score.initialiserScore();
		this.setScore(score);
		this.getScore().setJoueurs(this.getJoueurs());
		
		this.setChanged();
		this.notifyObservers("lancer_partie");
		this.chgtManche();
	}
/**
 * Methode qui change la saison, et la manche le cas ou l'appel de la méthode est effectué en hiver
 */
	public void chgtSaison() {
		if (super.saisonEnCours == 'P') {
			this.setSaisonEnCours('E');
		} else if (super.saisonEnCours == 'E') {
			this.setSaisonEnCours('A');
		} else if (super.saisonEnCours == 'A') {
			this.setSaisonEnCours('H');
		} else if (super.saisonEnCours == 'H') {
			this.setSaisonEnCours('P');
			this.chgtManche();
		}else{
			this.setSaisonEnCours('P');
		}
	}
/**
 * Methode qui permet de générer les paquets de les melanger et de les distribuer lorsque la partie change de manche
 * Elle permet egalement de lancer les methodes permettant de faire piocher une carte Allié ou de recuperer deux graines
 * Cette methode determine le gagnant de la partie
 */
	public void chgtManche() {
		if (this.mancheEnCours < Joueur.getNbJoueurs()) {
			if (this.mancheEnCours > 0) {
				for (int i = 0; i < this.getJoueurs().size() - 1; i++) {
					
					this.viderMain();
				}
				if (this.getJoueurs().indexOf(youngJoueur)+1 < this.getJoueurs().size()){
					this.setYoungJoueur(this.getJoueurs().get(this.getJoueurs().indexOf(youngJoueur)+1));
					this.setJoueurActif(youngJoueur);
				}else if (this.getJoueurs().indexOf(youngJoueur)+1 == this.getJoueurs().size()){
					this.setYoungJoueur(this.getJoueurs().get(0));
					this.setJoueurActif(youngJoueur);
				}
				this.genererPaquet();
				this.setChanged();
				this.notifyObservers("cartes_generate");
				this.paquetIngredient.melanger();
				this.paquetAllie.melanger();
				this.distribuer();
			}
			this.setChanged();
			this.notifyObservers("changement_joueur");
			
		
			Iterator<Joueur> it = this.getJoueurs().iterator();
			while (it.hasNext()) {
				Joueur jTemp = it.next();
				if (jTemp instanceof JoueurReel) {
					this.setChanged();
					this.notifyObservers("choix_allie_graines");
				}
				else {
					((JoueurIA) jTemp).piocherAllie(this);
				}
			}
					
					
					
			this.mancheEnCours++;
		} else {
			Joueur jGagnant = this.getScore().determinerGagnant(this); // on
																		// recupère
																		// le
																		// joueur
																		// gagnant
			super.termine = true;// on indique que la partie est terminée
			this.getScore().afficherScore();
			this.setChanged();
			this.notifyObservers(jGagnant);
		}
	}

	public PIngredient getPaquetIngredient() {
		return paquetIngredient;
	}

	public void setPaquetIngredient(PIngredient paquetIngredient) {
		this.paquetIngredient = paquetIngredient;
	}

	public PAllie getPaquetAllie() {
		return paquetAllie;
	}

	public void setPaquetAllie(PAllie paquetAllie) {
		this.paquetAllie = paquetAllie;
	}
/**
 * Methode qui distribue, en appelant la methode piocher du paquet d'ingredient, les cartes en prenant la carte au dessus de la pile.
 */
	public void distribuer() {
		int i = 0;
		while (!paquetIngredient.getPaquetDeCarte().isEmpty() && i < 4) {
			Iterator<Joueur> it = super.getJoueurs().iterator();
			while (it.hasNext()) {
				it.next().getMainJoueur().add(paquetIngredient.piocher());
			}
			i++;
		}
	}
/**
 * Methode qui vide la main de tout les joueurs de la partie, a la fin d'une manche
 */
	public void viderMain(){
		Iterator<Joueur> itj = this.getJoueurs().iterator();
		boolean carteFind = false;
		while (itj.hasNext()){
			Joueur jTemp = itj.next();
			Iterator<Carte> itc = jTemp.getMainJoueur().iterator();
			while (itc.hasNext() && carteFind == false){
				Carte cTemp = itc.next();
				if (cTemp instanceof Allie){
					paquetAllie.getPaquetDeCarte().add((Allie)cTemp);
					jTemp.getMainJoueur().remove(cTemp);
					carteFind = true;
				}else{
					paquetIngredient.getPaquetDeCarte().add((Ingredient)cTemp);
					jTemp.getMainJoueur().remove(cTemp);
					carteFind = true;
				}
			}
		}
	}
	
	/**
	 * Methode qui change de tour et qui donne la main a un autre joueur
	 */
	public void chgtTour() {
		super.chgtTour();
		if(!this.termine){
		this.setChanged();
		this.notifyObservers("changement_joueur");
		if(super.getJoueurActif() instanceof JoueurReel){//on regarde si c'est un joueur IA ou un joueur Reel qui doit jouer
	
		this.setChanged();
		this.notifyObservers("afficher_main_joueur");
		}else {
			Timer t = new Timer(1000 * 2, new ActionListener() {
				
			    public void actionPerformed(ActionEvent e) {
			    	try{
						((JoueurIA)getJoueurActif()).executeStrategyAvancee(getPartie());
						}
						catch(ActionException e1){
							e1.printStackTrace();
						}
						changeJoueurActif();
						chgtTour();
			    }
			});
			t.setRepeats(false);
			t.start();
		}
		}
		
	}
/**
 * Methode qui génère les deux paquets de la partie en créant les carte et en les ajoutants aux paquets
 */
	public void genererPaquet() {
		// generation du paquet de carte ingredient
		Ingredient I1 = new Ingredient("I1", "Larmes de dryade", new int[] {1,2,1,2}, new int[] {1,0,1,4}, new int[] {2,4,0,0});
		Ingredient I2 = new Ingredient("I2", "Larmes de dryade", new int[] {2,1,1,2}, new int[] {1,1,1,3}, new int[] {2,0,2,2});
		Ingredient I3 = new Ingredient("I3", "Larmes de dryade", new int[] {0,3,0,3}, new int[] {2,1,3,0}, new int[] {1,1,3,1});
		Ingredient I4 = new Ingredient("I4", "Rayon de lune", new int[] {0,0,4,0}, new int[] {0,2,2,0}, new int[] {0,0,1,3});
		Ingredient I5 = new Ingredient("I5", "Rayon de lune", new int[] {1,1,1,1}, new int[] {2,0,1,1}, new int[] {2,0,2,0});
		Ingredient I6 = new Ingredient("I6", "Rayon de lune", new int[] {2,0,1,1}, new int[] {1,3,0,0}, new int[] {0,1,2,1});
		Ingredient I7 = new Ingredient("I7", "Esprit de dolmen", new int[] {3,3,3,0}, new int[] {1,3,3,2}, new int[] {2,3,1,3});
		Ingredient I8 = new Ingredient("I8", "Esprit de dolmen", new int[] {2,4,1,2}, new int[] {2,2,2,3}, new int[] {1,4,3,1});
		Ingredient I9 = new Ingredient("I9", "Esprit de dolmen", new int[] {3,1,4,1}, new int[] {2,1,3,3}, new int[] {2,3,2,2});
		Ingredient I10 = new Ingredient("I10", "Racines d'arc-en-ciel", new int[] {2,3,2,0}, new int[] {0,4,3,0}, new int[] {2,1,1,3});
		Ingredient I11 = new Ingredient("I11", "Racines d'arc-en-ciel", new int[] {2,2,3,0}, new int[] {1,1,1,4}, new int[] {2,0,3,2});
		Ingredient I12 = new Ingredient("I12", "Racines d'arc-en-ciel", new int[] {4,1,1,1}, new int[] {1,2,1,3}, new int[] {1,2,2,2});
		Ingredient I13 = new Ingredient("I13", "Rires de fées", new int[] {1,2,2,1}, new int[] {1,2,3,0}, new int[] {0,2,2,2});
		Ingredient I14 = new Ingredient("I14", "Rires de fées", new int[] {2,0,1,3}, new int[] {0,3,0,3}, new int[] {1,2,2,1});
		Ingredient I15 = new Ingredient("I15", "Rires de fées", new int[] {4,0,1,1}, new int[] {1,1,3,1}, new int[] {0,0,3,3});
		Ingredient I16 = new Ingredient("I16", "Fontaine d'eau pure", new int[] {2,2,0,3}, new int[] {1,1,4,1}, new int[] {1,2,1,3});
		Ingredient I17 = new Ingredient("I17", "Fontaine d'eau pure", new int[] {1,3,1,2}, new int[] {2,1,2,2}, new int[] {0,0,3,4});
		Ingredient I18 = new Ingredient("I18", "Fontaine d'eau pure", new int[] {2,2,3,1}, new int[] {2,3,0,3}, new int[] {1,1,3,3});
		Ingredient I19 = new Ingredient("I19", "Chant de sirène", new int[] {2,1,1,1}, new int[] {1,0,2,2}, new int[] {3,0,0,2});
		Ingredient I20 = new Ingredient("I20", "Chant de sirène", new int[] {1,2,2,0}, new int[] {1,1,2,1}, new int[] {2,0,1,2});
		Ingredient I21 = new Ingredient("I21", "Chant de sirène", new int[] {1,3,1,0}, new int[] {1,2,1,1}, new int[] {0,1,4,0});
		Ingredient I22 = new Ingredient("I22", "Poudre d'or", new int[] {2,2,2,2}, new int[] {0,4,4,0}, new int[] {1,3,2,2});
		Ingredient I23 = new Ingredient("I23", "Poudre d'or", new int[] {2,2,3,1}, new int[] {2,3,0,3}, new int[] {1,1,3,3});
		Ingredient I24 = new Ingredient("I24", "Poudre d'or", new int[] {3,1,3,1}, new int[] {1,4,2,1}, new int[] {2,4,1,1});
		
		paquetIngredient = new PIngredient();
		paquetIngredient.setPaquetDeCarte(new LinkedList<Carte>());
		paquetIngredient.getPaquetDeCarte().add(I1);
		paquetIngredient.getPaquetDeCarte().add(I2);
		paquetIngredient.getPaquetDeCarte().add(I3);
		paquetIngredient.getPaquetDeCarte().add(I4);
		paquetIngredient.getPaquetDeCarte().add(I5);
		paquetIngredient.getPaquetDeCarte().add(I6);
		paquetIngredient.getPaquetDeCarte().add(I7);
		paquetIngredient.getPaquetDeCarte().add(I8);
		paquetIngredient.getPaquetDeCarte().add(I9);
		paquetIngredient.getPaquetDeCarte().add(I10);
		paquetIngredient.getPaquetDeCarte().add(I11);
		paquetIngredient.getPaquetDeCarte().add(I12);
		paquetIngredient.getPaquetDeCarte().add(I13);
		paquetIngredient.getPaquetDeCarte().add(I14);
		paquetIngredient.getPaquetDeCarte().add(I15);
		paquetIngredient.getPaquetDeCarte().add(I16);
		paquetIngredient.getPaquetDeCarte().add(I17);
		paquetIngredient.getPaquetDeCarte().add(I18);
		paquetIngredient.getPaquetDeCarte().add(I19);
		paquetIngredient.getPaquetDeCarte().add(I20);
		paquetIngredient.getPaquetDeCarte().add(I21);
		paquetIngredient.getPaquetDeCarte().add(I22);
		paquetIngredient.getPaquetDeCarte().add(I23);
		paquetIngredient.getPaquetDeCarte().add(I24);
		// generation du paquet de carte allié
		Taupe T1 = new Taupe("T1", "Taupe", new int[] { 1, 1, 1, 1 });
		Taupe T2 = new Taupe("T2", "Taupe", new int[] { 0, 2, 2, 0 });
		Taupe T3 = new Taupe("T3", "Taupe", new int[] { 0, 1, 2, 1 });
		Chien C1 = new Chien("C1", "Chien", new int[] { 2, 0, 2, 0 });
		Chien C2 = new Chien("C2", "Chien", new int[] { 1, 2, 0, 1 });
		Chien C3 = new Chien("C3", "Chien", new int[] { 0, 1, 3, 0 });
		paquetAllie = new PAllie();
		paquetAllie.setPaquetDeCarte(new LinkedList<Carte>());
		paquetAllie.getPaquetDeCarte().add(T1);
		paquetAllie.getPaquetDeCarte().add(T2);
		paquetAllie.getPaquetDeCarte().add(T3);
		paquetAllie.getPaquetDeCarte().add(C1);
		paquetAllie.getPaquetDeCarte().add(C2);
		paquetAllie.getPaquetDeCarte().add(C3);
	}

	public int getMancheEnCours() {
		return mancheEnCours;
	}

	public void setMancheEnCours(int mancheEnCours) {
		this.mancheEnCours = mancheEnCours;
	}

}
