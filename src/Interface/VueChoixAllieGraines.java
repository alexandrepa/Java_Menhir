package Interface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;

import Cartes.Carte;
import Joueurs.Joueur;
import Parties.Partie;
import Parties.PartieAvancee;
/**
 * JDialog permettant de demander au joueur Réel de la partie avancée si il souhaite piocher une carte ou récuperer 2 graines.
 * 
 *
 */
public class VueChoixAllieGraines extends JDialog{

	private JButton graines;
	private JButton carteAllie;
	private JLabel description;
	private FondPanel mainPanel;
	protected Partie partie;
	protected Joueur joueur;
	protected VueGraphique vueG;
	/**
	 * 
	 * @param p la partie en cours	
	 * @param j le joueur Réel
	 * @param vG la vueGraphique de la partie
	 */
	public VueChoixAllieGraines(Partie p, Joueur j, VueGraphique vG){
		super(vG,true);
		this.partie = p;
		this.joueur = j;
		this.vueG = vG;
		this.setTitle("Choix de départ");
		this.setSize(385, 250);
		this.setLocationRelativeTo(null);
		this.graines = new JButton("Deux graines");
		this.graines.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				partie.getScore().modifierNbGraine(2, joueur);
				fermerfenetre();
				vueG.afficherMainJoueur();
			}
		});
		this.carteAllie = new JButton("Une carte allié");
		this.carteAllie.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Carte cartePiochee = ((PartieAvancee)partie).getPaquetAllie().piocher();
				joueur.getMainJoueur().add(cartePiochee);
				cartePiochee.addObserver(vueG);
				
				fermerfenetre();
				vueG.afficherMainJoueur();
			}
		});
		this.description = new JLabel("Choisissez de commencer avec une carte allié ou deux graines.");
		this.mainPanel = new FondPanel("menhir.png");
		setContentPane(this.mainPanel);
		mainPanel.setLayout(null);
		this.mainPanel.add(this.graines);
		this.mainPanel.add(this.carteAllie);
		this.mainPanel.add(this.description);
		this.graines.setBounds(20,100,120,40);
		this.carteAllie.setBounds(220,100,135,40);
		this.description.setBounds(5,5,375,50);
		
		this.setVisible(true);
	}
	/**
	 * Ferme la JDialog pour que la partie puisse reprendre.
	 */
	public void fermerfenetre(){
		this.dispose();
	}
	


	
}
