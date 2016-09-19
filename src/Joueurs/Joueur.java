package Joueurs;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;

import Cartes.Allie;
import Cartes.Carte;
import Cartes.Ingredient;
import Exception.ActionException;
import Exception.CarteException;
import Parties.Partie;
import Parties.Score;
/**
 * Classe qui représente un Joueur.
 * Un Joueur est défini par un ID unique, un age, un sexe et par les cartes qu'il a dans la main.
 *  
 * 
 *
 */

public abstract class Joueur extends Observable{
	/**
	 * Membre statique permettant de compter le nombre de joueurs existant.
	 */
	private static int nbJoueurs;
	/**
	 * id du joueur
	 */
	private int id;	
	/**
	 * age du joueur, utilisé pour determiner qui commencera la partie
	 */
	private int age;
	/**
	 * sexe du joueur
	 */
	private char sexe;
	/**
	 * La main du joueur est représentée par une ArrayList contenant des objects de type Carte.
	 */
	private ArrayList<Carte> mainJoueur =  new ArrayList<Carte>();
	
	/**
	 * 
	 * @param id id du joueur
	 * @param age age du joueur
	 * @param sexe sexe du joueur
	 */
	public Joueur(int id, int age, char sexe){
		this.nbJoueurs++;
		this.id=id;
		this.age=age;
		this.sexe=sexe;
	}
	
	/**
	 * Méthode qui permet au joueur de jouer une carte Allié
	 * @param allie Instance de la carte allié jouée
	 * @param saisonEnCours Saison en cours dans la partie, sous forme de caractère
	 * @param score score de la partie en cours
	 * @param jCible cible de la carte allié
	 */
public void jouer(Allie allie, char saisonEnCours, Score score, Joueur jCible){
	allie.actionCarte(jCible, saisonEnCours, score);
	this.mainJoueur.remove(allie);	
	this.setChanged();
	this.notifyObservers(allie);
}
/**
 * Méthode qui permet de jouer une carte Ingrédient et d'effectuer l'action Geant ou Engrais.
 * @param ingredient La carte ingrédient que le joueur souhaite jouer.
 * @param Action L'action que le joueur souhaite utiliser avec la carte (Geant ou Engrais).
 * @param saisonEnCours La saison en cours dans la partie
 * @param score Le score de la partie
 * 
 * @throws ActionException Leve l'exception si l'action est inconnue
 */
public void jouer(Ingredient ingredient, String Action, char saisonEnCours, Score score) throws ActionException{
	if(Action.equals("Geant")){//On regarde quelle action le joueur souhaite effectuer.
		ingredient.recupGraine(this,saisonEnCours, score);
		this.mainJoueur.remove(ingredient);//on retire la carte de la main du joueur
	}
	else if(Action.equals("Engrais")){
		ingredient.fairePousser(this,saisonEnCours,score);
		this.mainJoueur.remove(ingredient);//on retire la carte de la main du joueur
		
	}
	else{
		throw new ActionException("Action : "+Action +" inconnue");
	}
	this.setChanged();
	this.notifyObservers(ingredient);
	
}
/**
 * 
 * @param ingredient Méthode qui permet de jouer une carte Ingrédient et d'effectuer l'action Farfadet.
 * @param joueur Le joueur auquel on souhaite voler les graines
 * @param saisonEnCours La saison en cours dans la partie
 * @param score Le score de la partie
 */
public void jouer(Ingredient ingredient,Joueur joueur, char saisonEnCours, Partie partie){
	ingredient.volerGraine(joueur,this,saisonEnCours,partie);
	this.mainJoueur.remove(ingredient);
	this.setChanged();
	this.notifyObservers(ingredient);
}


/**
 * Permet d'afficher la main du joueur dans la console
 * @deprecated Methode utilisée dans la version console du jeu
 */
public void afficherMain()
{
 for( Iterator itC = this.mainJoueur.iterator();itC.hasNext();){ //on parcourt la main du joueur et a chaque fois on affiche la carte.
	 Carte carte = (Carte) itC.next();
	 System.out.println();
	 carte.afficherCarte();
 }
}
/**
 * 
 * @return Retourne le nom du joueur
 */
public abstract String nom();

public static int getNbJoueurs() {
	return nbJoueurs;
}
public static void setNbJoueurs(int nbJoueurs) {
	Joueur.nbJoueurs = nbJoueurs;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public int getAge() {
	return age;
}
public void setAge(int age) {
	this.age = age;
}
public char getSexe() {
	return sexe;
}
public void setSexe(char sexe) {
	this.sexe = sexe;
}
public ArrayList getMainJoueur() {
	return mainJoueur;
}
public void setMainJoueur(ArrayList mainJoueur) {
	this.mainJoueur = mainJoueur;
}

/**
 * Permet de recupérer une référence sur une carte en donnant son nom.
 * @param carteName l'attribut nom de la carte
 * 
 * @throws CarteException Leve l'exception si la carte n'est pas trouvable dans la main du joueur
 * @return une référence sur la carte correspondante au nom
 */
public Carte getMainCarteByName(String carteName) throws CarteException {
	Iterator itC = this.mainJoueur.iterator(); 
	boolean found = false;
	Carte carteTrouve = null;
	while(itC.hasNext() && found==false){  //on parcourt la main du joueur pour trouver la carte avec le nom qui correspond.
		carteTrouve = (Carte) itC.next();
		if(carteTrouve.getNom().equals(carteName)){
			found=true;
		}
	}
	if(found==false){
		throw new CarteException("Impossible de trouver la carte "+carteName+" dans la main du joueur "+this.getId());
	}
	return carteTrouve;
}


	
}
