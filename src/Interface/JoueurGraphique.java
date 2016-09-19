package Interface;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import Joueurs.Joueur;
import Parties.Partie;
/**
 * Représentation graphique d'un Joueur sur la vue
 * @author Alexandre
 *
 */


public class JoueurGraphique {
	private JLabel joueurLabel;
	private JLabel menhirLabel;
	/**
	 * Permet de mettre à jour tous les joueurs de l'ArrayList joueursG sur la vue
	 * @param partie la partie en cours
	 * @param joueursG l'ArrayList contenant les joueurGraphique de la partie
	 */
	public static void miseAJourJoueursGraphique(Partie partie, ArrayList<JoueurGraphique> joueursG){
		for(Iterator<JoueurGraphique> itJ = joueursG.iterator(); itJ.hasNext();){
			JoueurGraphique joueurG = itJ.next();
			joueurG.joueurLabel.setText("<html>" + joueurG.joueur.nom() + "<br>Carte: " + joueurG.joueur.getMainJoueur().size() + "</html>");	
			
			joueurG.setScoreGraine(partie.getScore().getNbGraineJoueur(joueurG.joueur));
			joueurG.setScoreMenhir(partie.getScore().getNbMenhirJoueur(joueurG.joueur));
			if(joueurG.joueur==partie.getJoueurActif()){
				joueurG.setJoueurActif(true);
			}
			else{
				joueurG.setJoueurActif(false);
			}
		}
	}
	/**
	 * 
	 * @param joueur le joueur qui doit être représenté en JoueurGraphique
	 */
	public JoueurGraphique(Joueur joueur){
		this.joueurLabel = new JLabel("<html>" + joueur.nom() + "<br>Carte: " + joueur.getMainJoueur().size() + "</html>");
		this.menhirLabel = new JLabel("<html><div style=\"background-image: url('"+this.getClass().getResource("/icone_menhir.png").toString()+"');background-repeat: no-repeat;font-size: 30px\">0 </div></html>");
		this.graineLabel = new JLabel("<html><div style=\"background-image: url('"+this.getClass().getResource("/icone_graine.png").toString()+"');background-repeat: no-repeat;font-size: 30px\">2 </div></html>");
		this.joueur=joueur;
	}
	/**
	 * Change la couleur d'affichage du joueur afin d'indiquer que c'est le joueurActif
	 * @param bool
	 */
	public void setJoueurActif(boolean bool){
		if(bool){
			this.joueurLabel.setForeground(Color.RED);
		}
		else{
			this.joueurLabel.setForeground(Color.BLACK);
		}
	}
	
	public void setScoreGraine(int score){
		this.graineLabel.setText("<html><div style=\"background-image: url('"+this.getClass().getResource("/icone_menhir.png").toString()+"');background-repeat: no-repeat;font-size: 30px\">"+score+"</div></html>");
	}
	public void setScoreMenhir(int score){
		this.menhirLabel.setText("<html><div style=\"background-image: url('"+this.getClass().getResource("/icone_graine.png").toString()+"');background-repeat: no-repeat;font-size: 30px\">"+score+"</div></html>");
	}
	
	public JLabel getJoueurLabel() {
		return joueurLabel;
	}

	public void setJoueurLabel(JLabel joueurLabel) {
		this.joueurLabel = joueurLabel;
	}

	public JLabel getMenhirLabel() {
		return menhirLabel;
	}

	public void setMenhirLabel(JLabel menhirLabel) {
		this.menhirLabel = menhirLabel;
	}

	public JLabel getGraineLabel() {
		return graineLabel;
	}

	public void setGraineLabel(JLabel graineLabel) {
		this.graineLabel = graineLabel;
	}

	public Joueur getJoueur() {
		return joueur;
	}

	public void setJoueur(Joueur joueur) {
		this.joueur = joueur;
	}

	private JLabel graineLabel;
	private Joueur joueur;
	
	
	
	
	

}
