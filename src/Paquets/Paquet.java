package Paquets;
import Cartes.Carte;
import java.util.*;
/**
 * Classe qui r�presente un paquet de cartes
 * 
 *
 */
public abstract class Paquet {
	/**
	 * Collection contenant les cartes
	 * LinkedList est utilis� car il est necessaire, lorsque l'on pioche et l'on distribue, de prendre uniquement la carte situ�e sur le tas
	 */
	private LinkedList<Carte> paquetDeCarte;
	/**
	 * Methode abstraite qui permet de piocher une carte dans la collection
	 * 
	 * @return Retourne la carte pioch�e
	 */
	public abstract Carte piocher();
	/**
	 * Methode qui permet de melanger la collection
	 */
	public void melanger(){
		Collections.shuffle(paquetDeCarte);
	}
	public LinkedList<Carte> getPaquetDeCarte() {
		return paquetDeCarte;
	}
	public void setPaquetDeCarte(LinkedList<Carte> paquetDeCarte) {
		this.paquetDeCarte = paquetDeCarte;
	}
	
}
