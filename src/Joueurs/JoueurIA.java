package Joueurs;
import java.util.Random;

import Cartes.Allie;
import Cartes.Chien;
import Cartes.Ingredient;
import Exception.ActionException;
/**
 * Classe qui représente un joueur non réel.
 */
import Parties.Partie;
import Parties.PartieAvancee;
import Parties.Score;
import Strategy.DifficileStrategy;
import Strategy.FacileStrategy;
import Strategy.JoueurStrategy;
import Strategy.NormalStrategy;


/**
 * 
 * Classe qui represente un joueurIA
 *
 */
public class JoueurIA extends Joueur {
	
	/**
	 * Tableau statique qui permet, a la création, de generer un sexe aléatoire
	 */
	public final static char[] SEXE_JOUEUR = {'H','H','H','H','F','F','F','F'};
	/**
	 * Tableau statique qui permet, a la création, d'associer un age
	 */
	public final static int[] AGE_JOUEUR = {47,27,47,38,20,21,22,30};
	/**
	 * Stratégie du joueur
	 */
 private JoueurStrategy strategie;
 
 /**
  * 
  * @param id id du joueurIA
  * @param age age du joueurIA
  * @param sexe sexe du joueurIA
  * @param difficulte Trois valeurs possibles: Facile Normal ou Difficile. En fonction de la valeur du parametre, une stratégie est mise dans l'attribut strategie
  */
	public JoueurIA(int id, int age, char sexe,String difficulte) {
		super(id, age, sexe);
		if(difficulte.equals("Facile")){
			this.strategie = new FacileStrategy();
			
		}
		else if(difficulte.equals("Normal")){
			this.strategie= new NormalStrategy();
			
		}
		else{
			this.strategie= new DifficileStrategy();
			
		}
	}
	/**
	 * Permet d'executer a la volée selon le design pattern strategy les methodes d'IA relative a une partie rapide.
	 * 
	 * @param partie Instance de la partie en cours
	 * @throws ActionException Leve une exception si l'action est inconnue
	 */
	public void executeStrategyRapide(Partie partie) throws ActionException{
		
		strategie.choixTourRapide(this, super.getMainJoueur(), partie);
		
	}
	/**
	 * Permet d'executer a la volée selon le design pattern strategy les methodes d'IA relative a une partie avancee.
	 * 
	 * @param partie Instance de la partie en cours
	 * @throws ActionException Leve une exception si l'action est inconnue
	 */
	public void executeStrategyAvancee(Partie partie) throws ActionException{
		strategie.choixTourAvancee(this, super.getMainJoueur(), partie);
	}
	/**
	 * Permet d'executer la méthode qui permet a l'IA de decider de piocher une carte allié ou de recuperer deux graines.
	 * Utilisée dans les parties avancées
	 * 
	 * @param partie Instance de la partie en cours
	 */
	public void piocherAllie(Partie partie){
		strategie.choixPiocheAllie(this,super.getMainJoueur(), partie);
	}
	/**
	 * Permet d'executer la méthode qui permet a l'IA de decider si il souhaite jouer sa carte Taupe.
	 * 
	 * @param partie Instance de la partie en cours
	 * @param cTemp Instance de la carte allié jouée
	 */
	public void jouerTaupe(Partie partie, Allie cTemp){
		if (strategie.choixJouerTaupe(this, cTemp, partie) == true){
			partie.getScore().afficherScore();
		}
	}
	
	/**
	 * Permet d'executer la méthode qui permet a l'IA de decider si il souhaite jouer sa carte Taupe.

	 * 
	 * @param ingredient Instance de la carte ingrédient qui a ciblé le joueurIA avec une action farfadet
	 * @param partie Instance de la partie en cours
	 * @param saisonEnCours saison en cours dans la partie, sous forme de charactère
	 * @param allie Instance de la carte chien du joueurIA
	 * @return
	 */
	public int jouerChien(Ingredient ingredient,Partie partie, char saisonEnCours,  Allie allie){
		if (strategie.choixJouerChien((Joueur) this, ingredient, allie, partie) == true){
			if (partie instanceof PartieAvancee){
				((PartieAvancee)partie).getPaquetAllie().getPaquetDeCarte().add(allie);
			}
			return ((Chien)allie).actionCarteChien(ingredient, saisonEnCours);
		}else{
			return 0;
		}
	}

	/**
	 * Permet de recuperer le nom du joueurIA sous la forme "Joueur 3"
	 */
	public String nom() {
		return "Joueur "+this.getId();
	}
	
	


}
