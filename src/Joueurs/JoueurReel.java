package Joueurs;
/**
 * Classe qui représente un joueur Réel
 * 
 *
 */

public class JoueurReel extends Joueur {
	/**
	 * Represente le nom du joueur
	 */
	private String nom;
	
	/**
	 * 
	 * @param id Id du joueur reel
	 * @param age age du joueur reel
	 * @param sexe sexe du joueur reel
	 * @param nom nom du joueur reel
	 */
	public JoueurReel(int id, int age, char sexe, String nom) {
		super(id, age, sexe);
		this.nom=nom;
		
	}
	/**
	 * Permet de recuperer le nom du joueurIA sous la forme "Joueur 3"
	 */
	public String nom() {
		return this.nom;
	}

}
