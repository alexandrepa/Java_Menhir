package Cartes;

import java.util.Observable;

import Exception.CarteException;

/**
 * Classe abstraite qui va contenir les diff�rentes informations communes � toutes les cartes du jeu.
 *
 */


public abstract class Carte extends Observable{
	/**
	 * Nom de la carte qui est unique
	 */
	private String nom;
	/**
	 * Description de la carte (Larme de dryade, ...)
	 */
	private String description;
	
	/**
	 * 
	 * @param nom Represente le nom de la carte alli� (C1, T2, ...)
	 * @param description Represente la description de la carte (Larmes de dryade, ...)
	 */
	public Carte(String nom, String description){
		this.nom = nom;
		this.description = description;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}
	
	
    public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	/**
     * M�thode qui va afficher dans la console les informations de la cartes
     * @deprecated Methode utilis�e dans la version console du jeu. L'affichage du contenu de la carte est d�sormais assur� par l'interface graphique
     */
	public abstract void afficherCarte();
	
	
	/**
	 * M�thode qui va permet de convertir un caract�re de saison en entier correspondant.
	 * 
	 * @param saison Caractere de la saison en cours (P/E/A/H).
	 * @throws CarteException
	 * 					
	 * @return l'entier qui est l'index du tableau du score correspond � la saison.
	 */
	public int saisonConversion (char saison) {
		try{
			
		
		if(saison == 'P'){
			return 0;
		}
		else if (saison== 'E'){
			return 1;
		}
		else if (saison== 'A'){
			return 2;
		}
		else if(saison=='H'){
			return 3;
		}
		else {
			
			
			throw new CarteException("Conversion saison impossible, caract�re inconnu");
			
		}
	}
		catch (CarteException e){
			e.printStackTrace();;
			return 0;
		}
		
	}
}

