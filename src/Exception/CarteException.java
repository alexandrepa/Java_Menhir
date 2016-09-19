package Exception;

/**
 * Exception sur Carte
 * @author Alexandre
 *
 */
public class CarteException extends Exception {
	public CarteException(String message){
		super("Erreur carte : "+message);
	}

}
