package Controler;

import java.util.ArrayList;
import java.util.Random;

import Joueurs.Joueur;
import Joueurs.JoueurIA;
import Joueurs.JoueurReel;
import Parties.Partie;
/**
 * Classe qui represente un controler pour verifier les informations lors de la création d'un joueurIA
 * Cette classe s'inscrit dans le patron de conception MVC utilisé pour implementer la version graphique du jeu
 * 
 * 
 */
public class CreateJoueurIAControler {
	/**
	 * Instance de la partie, tel que décrit dans le patron MVC
	 */
	private Partie partie;
	/**
	 * Nom du joueur IA
	 */
	private String nom;
	/**
	 * age du joueur IA
	 */
	private int age;
	/**
	 * sexe du joueur IA
	 */
	private char sexe;
	/**
	 * Strategy du joueur IA
	 */
	private String strategy;
/**
 * 
 * @param partie donne une vue sur la partie au controler
 */
	public CreateJoueurIAControler(Partie partie) {
		this.partie = partie;
	}
/**
 * Generation aléatoire parmis le tableau de valeur de la classe JoueurIA
 * 
 *@see JoueurIA.AGE_JOUEUR
 */
	public void setAge() {
		this.age = (JoueurIA.AGE_JOUEUR[new Random().nextInt(JoueurIA.AGE_JOUEUR.length)]);
	}
	/**
	 * Generation aléatoire parmis le tableau de valeur de la classe JoueurIA
	 * 
	 *@see JoueurIA.SEXE_JOUEUR
	 */
	public void setSexe() {
		this.sexe = (JoueurIA.SEXE_JOUEUR[new Random().nextInt(JoueurIA.SEXE_JOUEUR.length)]);
	}
	/**
	 * 
	 * @param strategy Transmet la stratégie saisie par l'utilisateur pour le joueur IA correspondant
	 */
	public void setStrategy(String strategy){
		this.strategy = strategy;
	}
	
	/**
	 * Methode qui permet de valider les informations saisies pour la création d'un Joueur IA
	 */
	public void valider(){
		control();
	}
/**
 * Verification de la conformité des informations necessaires à la création d'un Joueur IA
 */
	public void control() {
		if ((this.age > 0) && ((this.sexe == 'H') || (this.sexe == 'F')) && (this.strategy != "")){
			Joueur j = new JoueurIA(Joueur.getNbJoueurs(),this.age,this.sexe,this.strategy);
			partie.addJoueurs(j);
			
		}
	}
}
