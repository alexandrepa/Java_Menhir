package Controler;

import java.util.ArrayList;

import Joueurs.Joueur;
import Joueurs.JoueurReel;
import Parties.Partie;
/**
 * Classe qui represente un controler pour verifier les informations lors de la création d'un joueur reel
 * Cette classe s'inscrit dans le patron de conception MVC utilisé pour implementer la version graphique du jeu
 * 
 * 
 */
public class CreateJoueurReelControler {
	/**
	 * Vue sur la partie
	 */
	private Partie partie;
	/**
	 * nom du joueur reel
	 */
	private String nom;
	/**
	 * age du joueur reel
	 */
	private int age;
	/**
	 * sexe du joueur reel
	 */
	private char sexe;
/**
 * 
 * @param partie Donne une vue sur la partie au controler
 */
	public CreateJoueurReelControler(Partie partie) {
		this.partie = partie;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public void setSexe(char sexe) {
		this.sexe = sexe;
	}
	/**
	 * Methode qui permet de valider les informations saisies pour la création d'un Joueur reel
	 */
	public void valider(){
		control();
	}
	/**
	 * Verification de la conformité des informations necessaires à la création d'un Joueur reel
	 */
	void control() {
		if ((this.age > 0) && ((this.sexe == 'H') || (this.sexe == 'F')) && (!this.nom.equals(""))){
			ArrayList<Joueur> joueurs = new ArrayList<Joueur>();
			joueurs.add(new JoueurReel(0,this.age,this.sexe,this.nom));
			partie.setJoueurs(joueurs);
		}
	}
}
