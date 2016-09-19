package Exception;

/**
 * Exception sur l'action d'un joueur (Geant/Engrais/Farfadet)
 * @author Alexandre
 *
 */
public class ActionException extends Exception {
	public ActionException(String message){
		super("Erreur action : "+message);
	}

}
