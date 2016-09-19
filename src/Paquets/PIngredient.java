package Paquets;
import Cartes.Carte;
/**
 * Classe qui représente un paquet de carte Ingrédient
 * 
 *
 */

public class PIngredient extends Paquet{
	public PIngredient(){
		
	}
	/**
	 * permet de piocher une carte en retirant le premier objet de la collection de carte
	 * 
	 * @return Retourne la carte piochée
	 */
	public Carte piocher(){
		return super.getPaquetDeCarte().poll();
	}
}
