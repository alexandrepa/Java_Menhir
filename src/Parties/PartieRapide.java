package Parties;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;
import javax.swing.Timer;

import Cartes.Carte;
import Cartes.Ingredient;
import Exception.ActionException;
import Exception.CarteException;
import Exception.JoueurException;
import Joueurs.Joueur;
import Joueurs.JoueurIA;
import Joueurs.JoueurReel;
import Paquets.PIngredient;
/**
 * Classe qui représente une partie Rapide.
 *
 */
public class PartieRapide extends Partie {

	private PIngredient paquetIngredient;
	
	public PartieRapide(){
		super();
	}
	/**Méthode qui va permettre de récuperer les différentes informations sur la partie Rapide.
	 * On va créer les différentes joueurs et les ajouter à la liste des joueurs
	 * On va aussi génerer et distribuer les différents paquets
	 * On initialise les scores des différents joueurs.
	 */
	public void debutPartie() {
		
		this.genererPaquet();
		this.paquetIngredient.melanger();
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
			System.out.println("test");
		this.setJoueurActif(getJoueurs().get(getJoueurs().indexOf(youngJoueur)-1));
		}
		
		int[] nbGraines = {0, 0, 0, 0, 0, 0};
		int[] nbMenhirs = {0, 0, 0, 0, 0, 0};
		Score score = new Score(nbGraines, nbMenhirs);
		score.initialiserScore();
		this.setScore(score);
		this.getScore().setJoueurs(this.getJoueurs());

		
		this.setChanged();
		this.notifyObservers("lancer_partie");
	}
	
/**
 * Permet de changer la saison en cours et de passer à la suivante ou de déclarer le vainqueur si c'est l'hiver
 */
	public void chgtSaison() {
		this.getScore().afficherScore();
		
		if(super.saisonEnCours == 'P'){
			this.setSaisonEnCours('E');
		} else if (super.saisonEnCours == 'E'){
			this.setSaisonEnCours('A');
		} else if (super.saisonEnCours == 'A'){
			this.setSaisonEnCours('H');
		} else if (super.saisonEnCours == 'H'){// si la dernière saison était l'hiver cela signifie que la partie est terminée
			Joueur jGagnant = this.getScore().determinerGagnant(this); //on recupère le joueur gagnant
			super.termine=true;//on indique que la partie est terminée
			System.out.println("Bravo, le gagnant est le joueur "+ jGagnant.getId());
			this.setChanged();
			this.notifyObservers(jGagnant);
		}
		else{
			this.setSaisonEnCours('P');
		}
	}
	/**
	 * Permet le commencement et le déroulement d'un nouveau tour de jeu
	 */
	public void chgtTour(){
		super.chgtTour();
		
		if(!this.termine){
			this.setChanged();
			this.notifyObservers("changement_joueur");
			System.out.println("\nC'est à vous de jouer, Joueur : "+super.getJoueurActif().nom());
			if(super.getJoueurActif() instanceof JoueurReel){//on regarde si c'est un joueur IA ou un joueur Reel qui doit jouer
		
			this.setChanged();
			this.notifyObservers("afficher_main_joueur");
			
			
			
			}
			else {
				Timer t = new Timer(1000 * 2, new ActionListener() {
					
				    public void actionPerformed(ActionEvent e) {
				    	try{
							((JoueurIA)getJoueurActif()).executeStrategyRapide(getPartie());
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
	 * Distribue les cartes du paquet Ingredient aux joueurs de la partie
	 */
	public void distribuer(){
		int i = 0;
		while (!paquetIngredient.getPaquetDeCarte().isEmpty() && i < 4){
			Iterator<Joueur> it = super.getJoueurs().iterator();
			while(it.hasNext()){
				it.next().getMainJoueur().add(paquetIngredient.piocher());
			}
			i++;
		}
	}
	/**
	 * Genère tous les différents Ingrédients et les ajoute au paquet de la partie
	 */
	public void genererPaquet(){
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
	}
}
