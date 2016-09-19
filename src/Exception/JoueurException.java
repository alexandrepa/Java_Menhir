package Exception;


/**
 * Exception sur un Joueur
 * @author Alexandre
 *
 */
public class JoueurException extends Exception {
public JoueurException(String message){
	super("Erreur joueur :"+message);
}
}
