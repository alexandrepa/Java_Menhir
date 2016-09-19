package Parties;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Random;
import java.util.Scanner;

import Cartes.Carte;
import Controler.CreateJoueurIAControler;
import Controler.CreateJoueurReelControler;
import Exception.JoueurException;

import Joueurs.Joueur;
import Joueurs.JoueurIA;
import Joueurs.JoueurReel;
/**
 * Classe qui représente une partie.
 * Une partie est composée d'un score, d'une liste des joueurs, des cartes sur le tapis, du joueur actif, de la saison en cours et d'un booleen indiquant si la partie est terminée.
 * Cette classe implemente la classe Observable pour le patron MVC
 *
 */

public abstract class Partie extends Observable{
	/**
	 * attribut qui permet d'avoir la saison en cours
	 */
	protected char saisonEnCours;
	/**
	 * Attribut qui permet d'avoir le joueur qui a la main
	 */
	private Joueur joueurActif;
	/**
	 * Vue sur le score de la partie
	 */
	private Score score;
	/**
	 * Liste des joueurs de la partie
	 */
	private ArrayList<Joueur> joueurs;
	/**
	 * Liste des cartes jouées lors de la manche, utilisé pour l'affichage graphique des cartes jouées lors du tour en cours
	 */
	private LinkedList<Carte> carteOnBoard;
	/**
	 * Permet de garder le joueur le plus jeune (pour le premier tour) puis le joueur qui commence le tour
	 */
	protected Joueur youngJoueur;
	/**
	 * booleen qui permet de verifier si la partie est terminée ou pas
	 */
	protected boolean termine;
	
	/**
	 * Saison en cours assignée a 'O', pour un traitement préliminaire (distribution des cartes)
	 */
	public Partie(){
		this.termine = false;
		this.saisonEnCours = 'O';
	
		
	}
	/**
	 * Méthode statique qui permet à l'utilisateur dans un premier temps de choisir quel type de partie il souhaite jouer.
	 * @return La méthode retourne la partie instanciée qui est du type choisi par l'utilisateur.
	 */
	public static Partie choixPartie(){
		Scanner sc = new Scanner(System.in);
		String choix;
		do{
			 choix = sc.nextLine();	
		}while(!(choix.equals("Rapide")||choix.equals("Avancee")));
		
		if(choix.equals("Rapide")){
			PartieRapide partieR= new PartieRapide();
			return partieR;
		}
		else if(choix.equals("Avancee")){
			PartieAvancee partieA= new PartieAvancee();
			return partieA;
		}
		else return null;
	
	}
	    /**
	     * Méthode qui va permettre de récuperer les différentes informations sur la partie.
	     * On va créer les différentes joueurs et les ajouter à la liste des joueurs
	     */
	public void debutPartie(){
		joueurs = new ArrayList<Joueur>();//on crée la liste des joueurs
		
		Scanner sc = new Scanner(System.in);
		int nbJoueurs;
		do{
		try{
			
		nbJoueurs = sc.nextInt();
	
		}
		catch (Exception e){
			sc.next();//pour passer à la ligne suivante et ne pas relir l'input data incorrecte
			nbJoueurs=0;
		}
		}
		while(nbJoueurs>6 || nbJoueurs<1);
		for(int i=0;i< nbJoueurs;i++){
			
			int age;
			do{
			try{
		    age = sc.nextInt();
			}
			catch(Exception E){
				sc.next();
				age=0;
			}
			}
			while (age<1);
			sc.nextLine();
			char sexe;
			do{
			
		    sexe = sc.nextLine().toUpperCase().charAt(0);
		    }
			while(sexe!='H' && sexe!='F');
			
			String nom;
			do{
		    nom = sc.nextLine();
			}
			while(nom.equals(""));
		    Joueur joueur = new JoueurReel(i,age,sexe,nom);//on crée le joueur selon les informations reçues
		    this.joueurs.add(joueur);
		    if(i ==0){
		    	this.joueurActif = joueur;
		    }
		}
		int nbJoueursIA=0;
		do{
		if(nbJoueurs!=6){	
	    nbJoueursIA= sc.nextInt();
		}
		}
		while(nbJoueursIA>6-nbJoueurs || nbJoueursIA<1);
		sc.nextLine();
		for(int i = 0;i<nbJoueursIA;i++){
			String level;
			do{
			    level = sc.nextLine();
			}while(!level.equals("Facile")&& !level.equals("Normal") && !level.equals("Difficile"));
			int age = (JoueurIA.AGE_JOUEUR[new Random().nextInt(JoueurIA.AGE_JOUEUR.length)]);
			char sexe = (JoueurIA.SEXE_JOUEUR[new Random().nextInt(JoueurIA.SEXE_JOUEUR.length)]);
			Joueur joueurIA = new JoueurIA(i+nbJoueurs,age,sexe,level);
			this.joueurs.add(joueurIA);
		}
		 
		Iterator<Joueur> it = this.joueurs.iterator();
		while(it.hasNext()){            // on parcourt la liste des joueurs et on affecte a la variables joueurActif le joueur le plus jeune
			Joueur jTemp = it.next();
			if (jTemp.getAge() < this.joueurActif.getAge()){
				this.joueurActif=jTemp;
			}
		}
		Collections.swap(this.joueurs,0,this.joueurActif.getId());//on met à la première place de la liste le plus jeune joueur car c'est lui qui va commencer.
	}
	/**
	 * Methode abstraite de changement de saison
	 */
	public abstract void chgtSaison();
	/**
	 * Methode abstraite qui permet de distribuer le paquets de la partie
	 */
	public abstract void distribuer();
	/**
	 * Permet le commencement d'un nouveau tour de jeu
	 */
	public void chgtTour(){
		
		if(this.joueurs.indexOf(joueurActif)==this.joueurs.indexOf(this.youngJoueur)){
			this.chgtSaison();
			this.setChanged();
			this.notifyObservers("clear_pile");
		}
		
		
		System.out.println("C'est la saison du "+this.getSaisonEnCours());
		if (this.getSaisonEnCoursChar() != 'P'){
			this.getScore().afficherScore();
		}
	}
	
	
	/**
	 * Methode qui permet d'afficher les joueurs. Utilisée dans la version console du jeu
	 * 
	 * @deprecated
	 */
	public void afficherJoueur(){
		Iterator<Joueur> it = this.joueurs.iterator();
		while (it.hasNext()){
			Joueur jTemp = it.next();
			System.out.println(jTemp.getId());
		}
	}
	
	/**
	 * Methode abstraire pour generer les paquets
	 */
	public abstract void genererPaquet();
	/**
	 * Permet de convertir le caractère de la saison en cours en chaine de caractère correspondant afin de l'afficher dans la console
	 * Exemple : P --> Printemps
	 * @return la chaîne de caractère qui correspond.
	 */
	public String getSaisonEnCours() {
		if (this.saisonEnCours=='P'){
			return "Printemps";	
		}
		else if (this.saisonEnCours=='E'){
			return "Ete";	
		}
		else if (this.saisonEnCours=='A'){
			return "Automne";	
		}
		else if (this.saisonEnCours=='H'){
			return "Hiver";	
		}
		else{
			return "Frozen";
		}
	}

	public char getSaisonEnCoursChar() {
		return this.saisonEnCours;
	}
	public void setSaisonEnCours(char saisonEnCours) {
		this.saisonEnCours = saisonEnCours;
	}

	public Joueur getJoueurActif() {
		return joueurActif;
	}

	public void setJoueurActif(Joueur joueurActif) {
		this.joueurActif = joueurActif;
	}

	public Score getScore() {
		return score;
	}

	public void setScore(Score score) {
		this.score = score;
	}

	public Joueur getYoungJoueur() {
		return youngJoueur;
	}
	public void setYoungJoueur(Joueur youngJoueur) {
		this.youngJoueur = youngJoueur;
	}
	public ArrayList<Joueur> getJoueurs() {
		return joueurs;
	}

	public void setJoueurs(ArrayList<Joueur> joueurs) {
		this.joueurs = joueurs;
	
	}

	public void addJoueurs(Joueur j){
		this.joueurs.add(j);
	
	}
	
	public LinkedList getCarteOnBoard() {
		return carteOnBoard;
	}

	public void setCarteOnBoard(LinkedList carteOnBoard) {
		this.carteOnBoard = carteOnBoard;
	}
	
	public boolean isTermine(){
		return this.termine;
	}
	
    /**
     * Permet de recuperer l'instance d'un joueur en renseignant son id.
     * @param joueurID l'id du joueur que l'on souhaite recupérer.
     * @return Le joueur demandé
     */
	public Joueur getJoueurById(int joueurID) throws JoueurException {
		Iterator itJ = this.joueurs.iterator(); 
		boolean found = false;
		Joueur joueurTrouve = null;
		while(itJ.hasNext() && found==false){//on parcourt la liste des joueurs jusqu'à trouver celui recherché
			joueurTrouve = (Joueur) itJ.next();
			if(joueurTrouve.getId()==joueurID){
				found=true;
			}
		}
		if(found == false){throw new JoueurException("Joueur introuvable");}
		return joueurTrouve;
	}
	/**
	 * Permet de changer le joueur actif
	 */
	public void changeJoueurActif(){
		int index = (this.joueurs.indexOf(joueurActif));
		if(index<this.joueurs.size()-1){
		this.setJoueurActif(joueurs.get(index+1));
		}
		else{
			this.setJoueurActif(joueurs.get(0));
		}
	}
	public Partie getPartie(){
		return this;
	}
	
}
