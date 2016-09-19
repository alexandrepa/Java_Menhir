package Paquets;
import Cartes.Carte;
/**
 * Classe qui r�presente un paquet contenant les cartes Alli�s
 * 
 *
 */

public class PAllie extends Paquet{
	public PAllie(){
		
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
