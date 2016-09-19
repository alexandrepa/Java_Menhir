package Interface;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.JFrame;

import Cartes.Allie;
import Cartes.Carte;
import Cartes.Chien;
import Cartes.Ingredient;
import Controler.CreateJoueurIAControler;
import Controler.CreateJoueurReelControler;
import Controler.MainPartieControleur;
import Exception.ActionException;
import Exception.JoueurException;
import Joueurs.Joueur;
import Joueurs.JoueurIA;
import Joueurs.JoueurReel;
import Parties.Partie;
import Parties.PartieAvancee;
import Parties.PartieRapide;
/**
 * JFrame principale qui va permettre l'affichage de la partie et des différentes sous-fenetres
 * 
 *
 */
public class VueGraphique extends JFrame implements java.util.Observer {
	private Partie partie;

	private FondPanel mainPanel;
	private CreateJoueurReelControler cjrc;
	private CreateJoueurIAControler cjic;
	private boolean ready;
	private ArrayList<JoueurGraphique> joueursG;
	private ArrayList<CarteGraphique> cartesG;
	private MainPartieControleur mainControl;
	private JLabel saisonGraphique;
	private JLabel actionLabel;
	private ArrayList<CarteGraphique> pile;

/**
 * 
 * @param mainControl Le controleur de la partie
 */
	public VueGraphique(MainPartieControleur mainControl) {
		this.ready=false;
		this.cartesG = null;
		this.mainControl = mainControl; 
		this.pile=new ArrayList<CarteGraphique>();
	}
/**
 * Permet l'affichage de la fenetre de menu
 */
	public void vueMenu() {
		JButton bouttonJouer;
		JButton bouttonRegle;
		JLabel lMenhir;
		this.setTitle("Accueil");
		this.setSize(750, 461);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false); 
		this.mainPanel = new FondPanel("menhir.png");
		setContentPane(this.mainPanel);
		mainPanel.setLayout(null);
		bouttonJouer = new JButton("Jouer");
		bouttonJouer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vueChoixTypePartie();
			}
		});
		mainPanel.add(bouttonJouer);
		bouttonJouer.setBounds(325, 240, 100, 40);
		bouttonRegle = new JButton("Règles du jeu");
		bouttonRegle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vueRegle();
			}
		});
		mainPanel.add(bouttonRegle);
		bouttonRegle.setBounds(20,20,150,40);
		lMenhir = new JLabel("Le Jeu du Menhir");
		Font font = new Font("Arial",Font.BOLD,50);
		lMenhir.setFont(font);

		mainPanel.add(lMenhir);
		lMenhir.setBounds(175,10,800,50);
		this.setVisible(true);
        
	}
	/**
	 * Permet l'affichage de la fenetre de règle
	 */
	public void vueRegle(){
		JButton bouttonRetour;
		this.setTitle("Règles du jeu");
		this.setSize(900, 458);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false); 
		this.mainPanel = new FondPanel("regle.PNG");
		setContentPane(this.mainPanel);
		mainPanel.setLayout(null);
		bouttonRetour = new JButton("Retour");
		bouttonRetour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vueMenu();
			}
		});
		this.mainPanel.add(bouttonRetour);
		bouttonRetour.setBounds(5, 5, 75, 30);
		this.setVisible(true);
	}
/**
 * Permet l'affichage de la fenetre permettant de choisir le type de partie
 */
	public void vueChoixTypePartie(){

		JButton bouttonPRapide;
		JButton bouttonPAvancee;
		JLabel imagePRapide = null;
		JLabel imagePAvancee = null;
		this.setTitle("Choix du type de la partie");
		this.setSize(750, 461);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false); 
		this.mainPanel = new FondPanel("menhir.png");
		setContentPane(this.mainPanel);
		mainPanel.setLayout(null);
		bouttonPRapide = new JButton("Partie Rapide");
		bouttonPRapide.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				partie = new PartieRapide();
                Joueur.setNbJoueurs(0);
				
				cjrc = new CreateJoueurReelControler(partie);
				cjic = new CreateJoueurIAControler(partie);
				
				mainControl.setVueG(getVueG());
				
				vueConfiguration();
			}
		});
		bouttonPAvancee = new JButton("Partie Avancée");
		bouttonPAvancee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				partie = new PartieAvancee();
				Joueur.setNbJoueurs(0);
				
				cjrc = new CreateJoueurReelControler(partie);
				cjic = new CreateJoueurIAControler(partie);
				
				mainControl.setVueG(getVueG());
				
				vueConfiguration();
			}
		});
		
		try {
			imagePRapide = new JLabel(new ImageIcon(ImageIO.read(this.getClass().getResource("/parcheminRapide.png"))));
			imagePAvancee = new JLabel(new ImageIcon(ImageIO.read(this.getClass().getResource("/parcheminAvancee.png"))));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		this.mainPanel.add(bouttonPRapide);
		this.mainPanel.add(bouttonPAvancee);
		this.mainPanel.add(imagePRapide);
		this.mainPanel.add(imagePAvancee);
		bouttonPRapide.setBounds(150, 230, 150, 30);
		imagePRapide.setBounds(120, 30, 213, 284);
		imagePAvancee.setBounds(470, 30, 213, 284);
		bouttonPAvancee.setBounds(500, 230, 150, 30);
		this.setVisible(true);
	}
	/**
	 * Permet l'affichage de la fenetre permettant à l'utilisateur de renseigner ses informations
	 */
	public void vueConfiguration() {
		partie.addObserver(this); //on abonne la vue a la partie
		final JButton bouttonValider;
		final JTextField tfNom;
		final JTextField tfAge;

		JLabel lNom;
		JLabel lAge;
		final JLabel lErrorAge;
		JLabel lSexe;
		JRadioButton rbH;
		JRadioButton rbF;
		tfNom = new JTextField();
		tfAge = new JTextField();
		rbH = new JRadioButton("Homme");
		rbH.setActionCommand(rbH.getText());
		rbF = new JRadioButton("Femme");
		rbF.setActionCommand(rbF.getText());
		final ButtonGroup groupeDeBoutons = new ButtonGroup();
		this.setTitle("Configuration de la partie");
		this.setSize(750, 461);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false); 
		this.mainPanel = new FondPanel("menhir.png");
		setContentPane(this.mainPanel);
		this.mainPanel.setLayout(null);
		lNom = new JLabel("Nom:");
		lAge = new JLabel("Age:");
		lErrorAge = new JLabel("Veuillez saisir un entier correct");
		lSexe = new JLabel("Sexe:");
		groupeDeBoutons.add(rbH);
		groupeDeBoutons.add(rbF);
		bouttonValider = new JButton("Valider");
		bouttonValider.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try {
					cjrc.setAge(Integer.parseInt(tfAge.getText()));
				cjrc.setNom(tfNom.getText());
				cjrc.setSexe(groupeDeBoutons.getSelection().getActionCommand().charAt(0));
				cjrc.valider();
				vueConfigIA();
				}catch(NumberFormatException nfe){
					lErrorAge.setForeground(Color.RED);
					lErrorAge.setVisible(true);
					tfAge.setText("");
				}
				
				
			}
		});
		this.mainPanel.add(bouttonValider);
		this.mainPanel.add(tfNom);
		this.mainPanel.add(tfAge);
		this.mainPanel.add(lNom);
		this.mainPanel.add(lAge);
		this.mainPanel.add(lErrorAge);
		this.mainPanel.add(lSexe);
		this.mainPanel.add(rbH);
		this.mainPanel.add(rbF);
		bouttonValider.setBounds(325, 350, 100, 40);
		lNom.setBounds(260, 100, 35, 30);
		tfNom.setBounds(300, 100, 150, 30);
		lAge.setBounds(260, 150, 35, 30);
		lErrorAge.setBounds(455, 150, 200, 30);
		tfAge.setBounds(300, 150, 150, 30);
		lSexe.setBounds(260, 200, 35, 30);
		rbH.setBounds(300, 200, 70, 30);
		rbF.setBounds(370, 200, 70, 30);
		this.setVisible(true);
		lErrorAge.setVisible(false);
	}
	/**
	 * Permet l'affichage de la fenetre permettant de configurer le nombre d'IA et leur difficulté
	 */
	public void vueConfigIA(){
		final JComboBox cbNbJoueurs;
		final ArrayList<JLabel> lJoueurs = new ArrayList<JLabel>();
		final ArrayList<JComboBox> cbJoueursStrategy = new ArrayList<JComboBox>();
		final JButton bouttonValider;
		this.setTitle("Configurer les autres joueurs");
		this.setSize(750, 461);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false); 
		this.mainPanel = new FondPanel("menhir.png");
		setContentPane(this.mainPanel);
		mainPanel.setLayout(null);
		cbNbJoueurs = new JComboBox(new String[] {"Nombre d'IAs","1","2","3","4","5"});
		cbNbJoueurs.addItemListener(new ItemListener() {
	        public void itemStateChanged(ItemEvent arg0) {
	        	for (int i = 0; i < cbJoueursStrategy.size(); i++){
	        		mainPanel.remove(cbJoueursStrategy.get(i));	
	        	}
	        	cbJoueursStrategy.removeAll(cbJoueursStrategy);
	        	for (int k = 0; k < lJoueurs.size(); k++){
	        		mainPanel.remove(lJoueurs.get(k));
	        	}
	        	lJoueurs.removeAll(lJoueurs);
	            switch(cbNbJoueurs.getSelectedItem().toString()){
	            case "Nombre d'IAs":
	            	
	            	mainPanel.repaint();
	            	break;
	            case "1":
	            	
	            	cbJoueursStrategy.add(new JComboBox(new String[] {"Facile", "Normal", "Difficile"}));
	            	lJoueurs.add(new JLabel("Difficulté de l'IA 1"));
	            	lJoueurs.get(0).setForeground(Color.WHITE);
	            	lJoueurs.get(0).setBounds(325, 100, 100, 30);
	            	cbJoueursStrategy.get(0).setBounds(325, 150, 100, 30);
	            	mainPanel.add(cbJoueursStrategy.get(0));
	            	mainPanel.add(lJoueurs.get(0));
	            	validate();
	            	mainPanel.repaint();
	            	break;
	            case "2":
	            	
	            	cbJoueursStrategy.add(new JComboBox(new String[] {"Facile", "Normal", "Difficile"}));
	            	cbJoueursStrategy.add(new JComboBox(new String[] {"Facile", "Normal", "Difficile"}));
	            	lJoueurs.add(new JLabel("Difficulté de l'IA 1"));
	            	lJoueurs.get(0).setForeground(Color.WHITE);
	            	lJoueurs.get(0).setBounds(150, 100, 100, 30);
	            	cbJoueursStrategy.get(0).setBounds(150, 150, 100, 30);
	            	mainPanel.add(cbJoueursStrategy.get(0));
	            	mainPanel.add(lJoueurs.get(0));
	            	lJoueurs.add(new JLabel("Difficulté de l'IA 2"));
	            	lJoueurs.get(1).setForeground(Color.WHITE);
	            	lJoueurs.get(1).setBounds(450, 100, 100, 30);
	            	cbJoueursStrategy.get(1).setBounds(450, 150, 100, 30);
	            	mainPanel.add(cbJoueursStrategy.get(1));
	            	mainPanel.add(lJoueurs.get(1));
	            	validate();
	            	mainPanel.repaint();
	            	break;
	            case "3":
	            	
	            	cbJoueursStrategy.add(new JComboBox(new String[] {"Facile", "Normal", "Difficile"}));
	            	cbJoueursStrategy.add(new JComboBox(new String[] {"Facile", "Normal", "Difficile"}));
	            	cbJoueursStrategy.add(new JComboBox(new String[] {"Facile", "Normal", "Difficile"}));
	            	lJoueurs.add(new JLabel("Difficulté de l'IA 1"));
	            	lJoueurs.get(0).setForeground(Color.WHITE);
	            	lJoueurs.get(0).setBounds(100, 100, 100, 30);
	            	cbJoueursStrategy.get(0).setBounds(100, 150, 100, 30);
	            	mainPanel.add(cbJoueursStrategy.get(0));
	            	mainPanel.add(lJoueurs.get(0));
	            	lJoueurs.add(new JLabel("Difficulté de l'IA 2"));
	            	lJoueurs.get(1).setForeground(Color.WHITE);
	            	lJoueurs.get(1).setBounds(325, 100, 100, 30);
	            	cbJoueursStrategy.get(1).setBounds(325, 150, 100, 30);
	            	mainPanel.add(cbJoueursStrategy.get(1));
	            	mainPanel.add(lJoueurs.get(1));
	            	lJoueurs.add(new JLabel("Difficulté de l'IA 3"));
	            	lJoueurs.get(2).setForeground(Color.WHITE);
	            	lJoueurs.get(2).setBounds(500, 100, 100, 30);
	            	cbJoueursStrategy.get(2).setBounds(500, 150, 100, 30);
	            	mainPanel.add(cbJoueursStrategy.get(2));
	            	mainPanel.add(lJoueurs.get(2));
	            	validate();
	            	mainPanel.repaint();
	            	break;
	            case "4":
	            	
	            	cbJoueursStrategy.add(new JComboBox(new String[] {"Facile", "Normal", "Difficile"}));
	            	cbJoueursStrategy.add(new JComboBox(new String[] {"Facile", "Normal", "Difficile"}));
	            	cbJoueursStrategy.add(new JComboBox(new String[] {"Facile", "Normal", "Difficile"}));
	            	cbJoueursStrategy.add(new JComboBox(new String[] {"Facile", "Normal", "Difficile"}));
	            	lJoueurs.add(new JLabel("Difficulté de l'IA 1"));
	            	lJoueurs.get(0).setForeground(Color.WHITE);
	            	lJoueurs.get(0).setBounds(50, 100, 100, 30);
	            	cbJoueursStrategy.get(0).setBounds(50, 150, 100, 30);
	            	mainPanel.add(cbJoueursStrategy.get(0));
	            	mainPanel.add(lJoueurs.get(0));
	            	lJoueurs.add(new JLabel("Difficulté de l'IA 2"));
	            	lJoueurs.get(1).setForeground(Color.WHITE);
	            	lJoueurs.get(1).setBounds(200, 100, 100, 30);
	            	cbJoueursStrategy.get(1).setBounds(200, 150, 100, 30);
	            	mainPanel.add(cbJoueursStrategy.get(1));
	            	mainPanel.add(lJoueurs.get(1));
	            	lJoueurs.add(new JLabel("Difficulté de l'IA 3"));
	            	lJoueurs.get(2).setForeground(Color.WHITE);
	            	lJoueurs.get(2).setBounds(400, 100, 100, 30);
	            	cbJoueursStrategy.get(2).setBounds(400, 150, 100, 30);
	            	mainPanel.add(cbJoueursStrategy.get(2));
	            	mainPanel.add(lJoueurs.get(2));
	            	lJoueurs.add(new JLabel("Difficulté de l'IA 4"));
	            	lJoueurs.get(3).setForeground(Color.WHITE);
	            	lJoueurs.get(3).setBounds(550, 100, 100, 30);
	            	cbJoueursStrategy.get(3).setBounds(550, 150, 100, 30);
	            	mainPanel.add(cbJoueursStrategy.get(3));
	            	mainPanel.add(lJoueurs.get(3));
	            	validate();
	            	mainPanel.repaint();
	            	break;
	            case "5":
	            	
	            	cbJoueursStrategy.add(new JComboBox(new String[] {"Facile", "Normal", "Difficile"}));
	            	cbJoueursStrategy.add(new JComboBox(new String[] {"Facile", "Normal", "Difficile"}));
	            	cbJoueursStrategy.add(new JComboBox(new String[] {"Facile", "Normal", "Difficile"}));
	            	cbJoueursStrategy.add(new JComboBox(new String[] {"Facile", "Normal", "Difficile"}));
	            	cbJoueursStrategy.add(new JComboBox(new String[] {"Facile", "Normal", "Difficile"}));
	            	lJoueurs.add(new JLabel("Difficulté de l'IA 1"));
	            	lJoueurs.get(0).setForeground(Color.WHITE);
	            	lJoueurs.get(0).setBounds(25, 100, 100, 30);
	            	cbJoueursStrategy.get(0).setBounds(25, 150, 100, 30);
	            	mainPanel.add(cbJoueursStrategy.get(0));
	            	mainPanel.add(lJoueurs.get(0));
	            	lJoueurs.add(new JLabel("Difficulté de l'IA 2"));
	            	lJoueurs.get(1).setForeground(Color.WHITE);
	            	lJoueurs.get(1).setBounds(150, 100, 100, 30);
	            	cbJoueursStrategy.get(1).setBounds(150, 150, 100, 30);
	            	mainPanel.add(cbJoueursStrategy.get(1));
	            	mainPanel.add(lJoueurs.get(1));
	            	lJoueurs.add(new JLabel("Difficulté de l'IA 3"));
	            	lJoueurs.get(2).setForeground(Color.WHITE);
	            	lJoueurs.get(2).setBounds(275, 100, 100, 30);
	            	cbJoueursStrategy.get(2).setBounds(275, 150, 100, 30);
	            	mainPanel.add(cbJoueursStrategy.get(2));
	            	mainPanel.add(lJoueurs.get(2));
	            	lJoueurs.add(new JLabel("Difficulté de l'IA 4"));
	            	lJoueurs.get(3).setForeground(Color.WHITE);
	            	lJoueurs.get(3).setBounds(400, 100, 100, 30);
	            	cbJoueursStrategy.get(3).setBounds(400, 150, 100, 30);
	            	mainPanel.add(cbJoueursStrategy.get(3));
	            	mainPanel.add(lJoueurs.get(3));
	            	lJoueurs.add(new JLabel("Difficulté de l'IA 5"));
	            	lJoueurs.get(4).setForeground(Color.WHITE);
	            	lJoueurs.get(4).setBounds(550, 100, 100, 30);
	            	cbJoueursStrategy.get(4).setBounds(550, 150, 100, 30);
	            	mainPanel.add(cbJoueursStrategy.get(4));
	            	mainPanel.add(lJoueurs.get(4));
	            	validate();
	            	mainPanel.repaint();
	            	break;
	            default:
	            	break;
	            }
	        }
	    });
		
		bouttonValider = new JButton("Valider");
		bouttonValider.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if (((String)cbNbJoueurs.getSelectedItem()).equals("Nombre d'IAs")) {

				} else {
					for (int i = 0; i < cbNbJoueurs.getSelectedIndex(); i++) {
						cjic.setStrategy((String) cbJoueursStrategy.get(i).getSelectedItem());
						cjic.setAge();
						cjic.setSexe();
						cjic.valider();

					}
					if (partie instanceof PartieRapide) {
						mainControl.debuterPartieRapide();
					} else if (partie instanceof PartieAvancee) {
						mainControl.debuterPartieAvancee();
					}
				}
				
			}
		});
		this.mainPanel.add(bouttonValider);
		this.mainPanel.add(cbNbJoueurs);
		bouttonValider.setBounds(325, 350, 100, 40);
		cbNbJoueurs.setBounds(325, 50, 100, 30);
		this.setVisible(true);
	}
	/**
	 * Permet l'affichage de la fenetre principale de la partie, c'est la fenetre de jeu
	 */
	public void vuePrincipale() {

		this.setTitle("Le jeu Menhir");
		this.setSize(800, 600);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false); 
		this.mainPanel = new FondPanel("table.png");
		setContentPane(this.mainPanel);
		mainPanel.setLayout(null);
		this.saisonGraphique = new JLabel();
		this.saisonGraphique.setBounds(700, 10, 100, 50);
		mainPanel.add(this.saisonGraphique);
		this.actionLabel = new JLabel();
		this.actionLabel.setBounds(50, 10, 400, 50);
		mainPanel.add(this.actionLabel);
		
		positionnerJoueurs();
		afficherMainJoueur();

		this.setVisible(true);

	}
/**
 * Permet de positionner les JoueurGraphique tout autour de la table de jeu sur la vue
 */
	public void positionnerJoueurs() {
		ArrayList<JoueurGraphique> joueursGraphique = new ArrayList<JoueurGraphique>();
		this.joueursG=joueursGraphique;
		for (Iterator itJ = this.partie.getJoueurs().iterator(); itJ.hasNext();) {
			
			Joueur joueur = (Joueur) itJ.next();
			System.out.println(this.partie.getJoueurs().size());
			JoueurGraphique joueurG = new JoueurGraphique(joueur);
			mainPanel.add(joueurG.getJoueurLabel());
			mainPanel.add(joueurG.getGraineLabel());
			mainPanel.add(joueurG.getMenhirLabel());
			joueursGraphique.add(joueurG);
			
			
		}
		if (joueursGraphique.size() == 2) {
			joueursGraphique.get(0).getJoueurLabel().setBounds(370, 400, 50, 50);
			joueursGraphique.get(1).getJoueurLabel().setBounds(370, 75, 50, 50);
			joueursGraphique.get(0).getGraineLabel().setBounds(340, 340, 50, 50);
			joueursGraphique.get(1).getGraineLabel().setBounds(400, 145, 50, 50);
			joueursGraphique.get(0).getMenhirLabel().setBounds(400, 340, 50, 50);
			joueursGraphique.get(1).getMenhirLabel().setBounds(340, 145, 50, 50);
		}
		if (joueursGraphique.size() == 3) {
			joueursGraphique.get(0).getJoueurLabel().setBounds(370, 400, 50, 50);
			joueursGraphique.get(1).getJoueurLabel().setBounds(370, 75, 50, 50);
			joueursGraphique.get(2).getJoueurLabel().setBounds(35, 230, 50, 50);
			joueursGraphique.get(0).getGraineLabel().setBounds(340, 340, 50, 50);
			joueursGraphique.get(1).getGraineLabel().setBounds(400, 145, 50, 50);
			joueursGraphique.get(2).getGraineLabel().setBounds(100, 190, 50, 50);
			joueursGraphique.get(0).getMenhirLabel().setBounds(400, 340, 50, 50);
			joueursGraphique.get(1).getMenhirLabel().setBounds(340, 145, 50, 50);
			joueursGraphique.get(2).getMenhirLabel().setBounds(100, 250, 50, 50);
		}
		if (joueursGraphique.size() == 4) {

			joueursGraphique.get(0).getJoueurLabel().setBounds(370, 400, 50, 50);
			joueursGraphique.get(1).getJoueurLabel().setBounds(715, 230, 50, 50);
			joueursGraphique.get(2).getJoueurLabel().setBounds(370, 75, 50, 50);
			joueursGraphique.get(3).getJoueurLabel().setBounds(35, 230, 50, 50);
			joueursGraphique.get(0).getGraineLabel().setBounds(340, 340, 50, 50);
			joueursGraphique.get(1).getGraineLabel().setBounds(650, 190, 50, 50);
			joueursGraphique.get(2).getGraineLabel().setBounds(400, 145, 50, 50);
			joueursGraphique.get(3).getGraineLabel().setBounds(100, 190, 50, 50);
			joueursGraphique.get(0).getMenhirLabel().setBounds(400, 340, 50, 50);
			joueursGraphique.get(1).getMenhirLabel().setBounds(650, 250, 50, 50);
			joueursGraphique.get(2).getMenhirLabel().setBounds(340, 145, 50, 50);
			joueursGraphique.get(3).getMenhirLabel().setBounds(100, 250, 50, 50);
			
		}

		if (joueursGraphique.size() == 5) {
			
			joueursGraphique.get(0).getJoueurLabel().setBounds(370, 400, 50, 50);
			joueursGraphique.get(1).getJoueurLabel().setBounds(715, 250, 50, 50);
			joueursGraphique.get(2).getJoueurLabel().setBounds(550, 80, 50, 50);
			joueursGraphique.get(3).getJoueurLabel().setBounds(190, 80, 50, 50);
			joueursGraphique.get(4).getJoueurLabel().setBounds(35, 250, 50, 50);
			
			joueursGraphique.get(0).getGraineLabel().setBounds(340, 340, 50, 50);
			joueursGraphique.get(1).getGraineLabel().setBounds(650, 210, 50, 50);
			joueursGraphique.get(2).getGraineLabel().setBounds(530, 145, 50, 50);
			joueursGraphique.get(3).getGraineLabel().setBounds(180, 145, 50, 50);
			joueursGraphique.get(4).getGraineLabel().setBounds(100, 210, 50, 50);
			joueursGraphique.get(0).getMenhirLabel().setBounds(400, 340, 50, 50);
			joueursGraphique.get(1).getMenhirLabel().setBounds(650, 270, 50, 50);
			joueursGraphique.get(2).getMenhirLabel().setBounds(590, 145, 50, 50);
			joueursGraphique.get(3).getMenhirLabel().setBounds(240, 145, 50, 50);
			joueursGraphique.get(4).getMenhirLabel().setBounds(100, 270, 50, 50);
		}

		if (joueursGraphique.size() == 6) {

			joueursGraphique.get(0).getJoueurLabel().setBounds(370, 400, 50, 50);
			joueursGraphique.get(1).getJoueurLabel().setBounds(715, 300, 50, 50);
			joueursGraphique.get(2).getJoueurLabel().setBounds(650, 120, 50, 50);
			joueursGraphique.get(3).getJoueurLabel().setBounds(370, 75, 50, 50);
			joueursGraphique.get(4).getJoueurLabel().setBounds(110, 120, 50, 50);
			joueursGraphique.get(5).getJoueurLabel().setBounds(35, 300, 50, 50);
			joueursGraphique.get(0).getGraineLabel().setBounds(340, 340, 50, 50);
			joueursGraphique.get(1).getGraineLabel().setBounds(650, 260, 50, 50);
			joueursGraphique.get(2).getGraineLabel().setBounds(620, 150, 50, 50);
			joueursGraphique.get(3).getGraineLabel().setBounds(400, 145, 50, 50);
			joueursGraphique.get(4).getGraineLabel().setBounds(200, 145, 50, 50);
			joueursGraphique.get(5).getGraineLabel().setBounds(100, 260, 50, 50);
			joueursGraphique.get(0).getMenhirLabel().setBounds(400, 340, 50, 50);
			joueursGraphique.get(1).getMenhirLabel().setBounds(650, 320, 50, 50);
			joueursGraphique.get(2).getMenhirLabel().setBounds(590, 150, 50, 50);
			joueursGraphique.get(3).getMenhirLabel().setBounds(340, 145, 50, 50);
			joueursGraphique.get(4).getMenhirLabel().setBounds(170, 145, 50, 50);
			joueursGraphique.get(5).getMenhirLabel().setBounds(100, 320, 50, 50);
			
			
			
		}

	}
/**
 * Permet d'afficher la main du Joueur Reel sur la vue
 */
	public void afficherMainJoueur() {
		
		if(cartesG!=null){
		for(Iterator itC=cartesG.iterator();itC.hasNext();){
			CarteGraphique carte =(CarteGraphique) itC.next();
			mainPanel.remove(carte);
		}
		}
		try {
			cartesG = CarteGraphique.genererCarteGraphique(partie.getJoueurById(0),this.mainControl);
		} catch (JoueurException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (Iterator<CarteGraphique> itC = cartesG.iterator(); itC.hasNext();) {
			CarteGraphique carteG = itC.next();
			mainPanel.add(carteG);
		}
		mainPanel.repaint();

	}


	public boolean isReady() {
		return ready;
	}

	public void setReady(boolean ready) {
		this.ready = ready;
	}


	public JLabel getSaisonGraphique() {
		return saisonGraphique;
	}

	public void setSaisonGraphique(JLabel saisonGraphique) {
		this.saisonGraphique = saisonGraphique;
	}

	public JLabel getActionLabel() {
		return actionLabel;
	}

	public void setActionLabel(JLabel actionLabel) {
		this.actionLabel = actionLabel;
	}

	public ArrayList<JoueurGraphique> getJoueursG() {
		return joueursG;
	}

	public void setJoueursG(ArrayList<JoueurGraphique> joueursG) {
		this.joueursG = joueursG;
	}

	public ArrayList<CarteGraphique> getCartesG() {
		return cartesG;
	}

	public void setCartesG(ArrayList<CarteGraphique> cartesG) {
		this.cartesG = cartesG;
	}

	public Partie getPartie() {
		return partie;
	}

	public void setPartie(Partie partie) {
		this.partie = partie;
	}

	public FondPanel getMainPanel() {
		return mainPanel;
	}

	public void setMainPanel(FondPanel mainPanel) {
		this.mainPanel = mainPanel;
	}

	public MainPartieControleur getMainControl() {
		return mainControl;
	}

	public void setMainControl(MainPartieControleur mainControl) {
		this.mainControl = mainControl;
	}

	public CreateJoueurReelControler getCjrc() {
		return cjrc;
	}

	public void setCjrc(CreateJoueurReelControler cjrc) {
		this.cjrc = cjrc;
	}

	public CreateJoueurIAControler getCjic() {
		return cjic;
	}

	public void setCjic(CreateJoueurIAControler cjic) {
		this.cjic = cjic;
	}
	public VueGraphique getVueG(){
		return this;
	}
/**
 * Permet de gérer les différents événements envoyés par le modèle
 */
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
		if(arg.equals("lancer_partie")){
			this.vuePrincipale();
		}
		if(arg.equals("changement_joueur")){
			for(Iterator itC = this.cartesG.iterator();itC.hasNext();){
		    	CarteGraphique carteG = (CarteGraphique) itC.next();
		    	carteG.setEventEnabled(false);
		    }
		    JoueurGraphique.miseAJourJoueursGraphique(partie, joueursG);
		    this.saisonGraphique.setText(this.partie.getSaisonEnCours());
		}
		if(arg.equals("afficher_main_joueur")){
		    this.afficherMainJoueur();
		    for(Iterator itC = this.cartesG.iterator();itC.hasNext();){
		    	CarteGraphique carteG = (CarteGraphique) itC.next();
		    	carteG.setEventEnabled(true);
		    }
		}
		if(arg.equals("choix_allie_graines")){
			Iterator<Joueur> it = this.partie.getJoueurs().iterator();
			while (it.hasNext()) {
				Joueur jTemp = it.next();
				if (jTemp instanceof JoueurReel) {
						VueChoixAllieGraines vcag = new VueChoixAllieGraines(this.partie, jTemp, this);
				}
			}
			
			
		}
		if(arg.equals("clear_pile")){
			for(Iterator itP = pile.iterator();itP.hasNext();){
				CarteGraphique carteG = (CarteGraphique) itP.next();
				mainPanel.remove(carteG);
			}
			pile.clear();
		}
		if(arg.equals("cartes_generate")){
			for(Iterator itC = ((PartieAvancee) partie).getPaquetIngredient().getPaquetDeCarte().iterator();itC.hasNext();){
				Ingredient carte = (Ingredient) itC.next();
				carte.addObserver(this);
				System.out.println("ajouté :"+carte.getNom());
			}
			for(Iterator itC = ((PartieAvancee) partie).getPaquetAllie().getPaquetDeCarte().iterator();itC.hasNext();){
				Allie carte = (Allie) itC.next();
				carte.addObserver(this);
			}
		}
		if(arg instanceof Joueur){
			JoueurGraphique.miseAJourJoueursGraphique(partie, joueursG);
			JOptionPane jop1 = new JOptionPane();
			jop1.showMessageDialog(null, "Bravo à"+((Joueur) arg).nom(),"Gagnant :" , JOptionPane.INFORMATION_MESSAGE);
			this.vueMenu();
		}
		if(arg instanceof Carte){
			if(pile.size()>4){
				for(Iterator itP = pile.iterator();itP.hasNext();){
					CarteGraphique carteG = (CarteGraphique) itP.next();
					mainPanel.remove(carteG);
				}
				pile.clear();
				
			}
			try {
				CarteGraphique carteG;
								if(arg instanceof Ingredient){
									carteG =CarteGraphique.attribuerImageToCarteIngredient(((Carte) arg).getNom(), -1*(pile.size()+1), this.mainControl);
									
									pile.add(carteG);	
									}
									else {
										carteG = CarteGraphique.attribuerImageToCarteAllie(((Carte) arg).getNom(), -1*(pile.size()+1), this.mainControl);
										pile.add(carteG);
									}
									
								mainPanel.add(carteG);
									carteG.setBounds(100*(pile.indexOf(carteG)+1), 175, 190, 190);
									
							mainPanel.repaint();
			
			} 
			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}if (arg instanceof ArrayList<?>) {
			System.out.println("je suis bien un entier");
			if (o instanceof Ingredient) {
			
				VueChoixJouerChien vcjc = new VueChoixJouerChien(((Ingredient)((ArrayList) arg).get(0)), ((Chien)((ArrayList) arg).get(1)), ((Joueur)((ArrayList) arg).get(2)), this.partie,this);			
			}
		}
		else if(o instanceof Carte){
			actionLabel.setText((String)arg);
			mainPanel.repaint();
			System.out.println("\nnom carte :"+((Carte) o).getNom());
			JoueurGraphique.miseAJourJoueursGraphique(partie, joueursG);
			
		}
		
		
		
		
		
	}
}
