package Paquets;
import Cartes.Carte;
/**
 * Classe qui repr�sente un paquet de carte Ingr�dient
 * 
 *
 */

public class PIngredient extends Paquet{
	public PIngredient(){
		
	}
	/**
	 * permet de piocher une carte en retirant le premier objet de la collection de carte
	 * 
	 * @return Retourne la carte pioch�e
	 */
	public Carte piocher(){
		return super.getPaquetDeCarte().poll();
	}
}
