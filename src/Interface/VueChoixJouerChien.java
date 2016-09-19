package Interface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

import Cartes.Allie;
import Cartes.Carte;
import Cartes.Chien;
import Cartes.Ingredient;
import Joueurs.Joueur;
import Parties.Partie;
import Parties.PartieAvancee;
/**
 * 
 *JDialog permettant de demander au joueur Réel de la partie avancée si il souhaite jouer sa carte Chien en réponse à une attaque de farfadet.
 *
 */
public class VueChoixJouerChien extends JDialog{

	private JButton bOui;
	private JButton bNon;
	private JLabel description;
	private FondPanel mainPanel;
	private Ingredient i;
	private Chien c;
	private Joueur J;
	private Partie p;

	/**
	 * 
	 * @param ingredient Carte Ingredient qui provoque l'action Farfadet
	 * @param chien Carte Allie chien qui peut contrer la carte Ingredient
	 * @param joueur joueur qui est attaqué
	 * @param partie la partie en cours
	 * @param vueG vueGraphique de la partie
	 */
	public VueChoixJouerChien(Ingredient ingredient, Chien chien, Joueur joueur, Partie partie,VueGraphique vueG){
		super(vueG,true);
		this.i = ingredient;
		this.c = chien;
		this.J = joueur;
		this.p = partie;
		this.setTitle("Jouer votre carte chien?");
		this.setSize(600, 250);
		this.setLocationRelativeTo(null);
		this.bOui = new JButton("Oui");
		this.bOui.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				i.setGrainesProtegee(((Chien) c).actionCarteChien(i, p.getSaisonEnCoursChar()));
				J.getMainJoueur().remove(c);
				((PartieAvancee) p).getPaquetAllie().getPaquetDeCarte().add(c);
				fermerfenetre();
			}
		});
		this.bNon = new JButton("Non");
		this.bNon.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				i.setGrainesProtegee(0);
				fermerfenetre();
			}
		});
		this.description = new JLabel("Vous êtes la cible d'une carte farfadet. Souhaitez vous jouer votre carte Chien pour en réduire l'effet?");
		this.mainPanel = new FondPanel("menhir.png");
		setContentPane(this.mainPanel);
		mainPanel.setLayout(null);
		this.mainPanel.add(this.bOui);
		this.mainPanel.add(this.bNon);
		this.mainPanel.add(this.description);
		this.bOui.setBounds(100,100,120,50);
		this.bNon.setBounds(365,100,135,50);
		this.description.setBounds(75,50,450,50);
		
		this.setVisible(true);
	}
	
	public void fermerfenetre(){
		this.dispose();
	}
	


	
}
