package Paquets;
import Cartes.Carte;
/**
 * Classe qui répresente un paquet contenant les cartes Alliés
 * 
 *
 */

public class PAllie extends Paquet{
	public PAllie(){
		
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
