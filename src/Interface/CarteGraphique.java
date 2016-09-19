package Interface;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import Cartes.Allie;
import Cartes.Carte;
import Cartes.Ingredient;
import Controler.MainPartieControleur;
import Joueurs.Joueur;

/**
 * Représensation graphique d'une carte sur la vue
 *
 */
public class CarteGraphique extends JLabel implements MouseListener {
	private ImageIcon imageG;
	private ImageIcon imageP;
	private String nom;
	private int position;
	private boolean eventEnabled;
	private MainPartieControleur mainControl;
	static private JButton engrais;
	static private JButton geant;
	static private JButton farfadet;
	/**
	 * 
	 * @param linkImageG lien de l'image de grande taille 160*160
	 * @param linkImageP lien de l'image de petite taille 90*90
	 * @param nom nom de la carte
	 * @param position position dans la main
	 * @param mainControl controleur de la partie
	 * @throws IOException
	 * @throws ClassNotFoundException 
	 */
public CarteGraphique(String linkImageG,String linkImageP, String nom, int position,MainPartieControleur mainControl) throws IOException, ClassNotFoundException{
	super(new ImageIcon(ImageIO.read(mainControl.getClass().getResource("/"+linkImageP))));
	
	imageG=null;
	imageP=null;
	try {
		imageP = new ImageIcon(ImageIO.read(mainControl.getClass().getResource("/"+linkImageP)));
		imageG = new ImageIcon(ImageIO.read(mainControl.getClass().getResource("/"+linkImageG)));
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	this.nom=nom;
	this.eventEnabled=false;
	this.position=position;
	addMouseListener(this);
	if(position>-1){
	this.setBounds(120+position*100, 455, 90, 90);
	}
this.mainControl=mainControl;

	
}

public ImageIcon getImageG() {
	return imageG;
}

public void setImageG(ImageIcon imageG) {
	this.imageG = imageG;
}

public ImageIcon getImageP() {
	return imageP;
}

public void setImageP(ImageIcon imageP) {
	this.imageP = imageP;
}

public String getNom() {
	return nom;
}

public void setNom(String nom) {
	this.nom = nom;
}

public int getPosition() {
	return position;
}

public void setPosition(int position) {
	this.position = position;
}

public MainPartieControleur getMainControl() {
	return mainControl;
}

public void setMainControl(MainPartieControleur mainControl) {
	this.mainControl = mainControl;
}

/**
 * Permet de generer les cartesGraphiques du joueur réel.
 * 
 * @param joueur joueur auquel il faut generer les cartesGraphiques (Le joueur Réel)
 * @param mainControl //le mainControleur de la partie
 * @return
 */
public static ArrayList<CarteGraphique> genererCarteGraphique (Joueur joueur,MainPartieControleur mainControl){
	engrais = new JButton("Engrais");
	geant = new JButton("Geant");
	farfadet = new JButton("Farfadet");
	ArrayList<CarteGraphique> cartes = new ArrayList<CarteGraphique>();
	int index=0;
	for(Iterator<Carte> itC = joueur.getMainJoueur().iterator();itC.hasNext();){
		Carte carte =itC.next();
	try {
		if (carte instanceof Ingredient){
			CarteGraphique carteG = attribuerImageToCarteIngredient(carte.getNom(), index, mainControl);
			
			cartes.add(carteG);
		}else if(carte instanceof Allie){
			CarteGraphique carteG = attribuerImageToCarteAllie(carte.getNom(), index, mainControl);
			
			cartes.add(carteG);
		}
		
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	index++;
	}
	
	
	return cartes;
	
}

/**
 * Permet de gérer les différentes intéractions avec le click souris (action jouer carte)
 */
@Override
public void mouseClicked(MouseEvent e) {
	// TODO Auto-generated method stub
	
	if(position>-1){
		if((eventEnabled) && (getCarte().getNom().charAt(0) != 'C') && (getCarte().getNom().charAt(0) != 'T')){
		
			removeJoueurListener();
		engrais.setBounds(100, 400, 100, 50);
		geant.setBounds(200, 400, 100, 50);
		mainControl.getVueG().getMainPanel().add(engrais);
		mainControl.getVueG().getMainPanel().add(geant);
		farfadet.setBounds(300, 400, 100, 50);
		mainControl.getVueG().getMainPanel().add(farfadet);
		engrais.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println(getCarte().getNom());
				mainControl.jouerCarte(getCarte(),"Engrais",null);
				mainControl.getVueG().getMainPanel().remove(engrais);
				mainControl.getVueG().getMainPanel().remove(geant);
				mainControl.getVueG().getMainPanel().remove(farfadet);
				removeJoueurListener();
			}
			
		});
	
	
	
		
		geant.addActionListener(new ActionListener(){

			
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				mainControl.jouerCarte(getCarte(),"Geant",null);
				mainControl.getVueG().getMainPanel().remove(engrais);
				mainControl.getVueG().getMainPanel().remove(geant);
				mainControl.getVueG().getMainPanel().remove(farfadet);
				removeJoueurListener();
			}
			
		});
		
		
		farfadet.addActionListener(new ActionListener(){

			
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				mainControl.getVueG().getActionLabel().setText("Veuillez cliquer sur le nom du joueur à qui vous souhaitez voler des graines");
				for(Iterator itJ = mainControl.getVueG().getJoueursG().iterator();itJ.hasNext();){
					final JoueurGraphique joueurG = (JoueurGraphique) itJ.next();
					joueurG.getJoueurLabel().addMouseListener(new MouseAdapter(){
						public void mouseReleased(MouseEvent e) {
							System.out.println("------------------------------------------");
								mainControl.jouerCarte(getCarte(),"farfadet",joueurG.getJoueur());
							removeJoueurListener();
							
						}
					});
				}
				
				mainControl.getVueG().getMainPanel().remove(engrais);
				mainControl.getVueG().getMainPanel().remove(geant);
				mainControl.getVueG().getMainPanel().remove(farfadet);
			}
			
		});
		
		
		}else if ((eventEnabled) && (getCarte().getNom().charAt(0) == 'T')){
			mainControl.getVueG().getMainPanel().remove(engrais);
			mainControl.getVueG().getMainPanel().remove(geant);
			mainControl.getVueG().getMainPanel().remove(farfadet);
			
			mainControl.getVueG().getActionLabel().setText("Veuillez cliquer sur le nom du joueur à qui vous souhaitez detruire des menhirs");
			mainControl.getVueG().getMainPanel().repaint();
			for(Iterator itJ = mainControl.getVueG().getJoueursG().iterator();itJ.hasNext();){
				final JoueurGraphique joueurG = (JoueurGraphique) itJ.next();
				joueurG.getJoueurLabel().addMouseListener(new MouseAdapter(){
					public void mouseClicked(MouseEvent e) {
						mainControl.jouerCarte(getCarte(),"taupe",joueurG.getJoueur());
						for(Iterator itJ = mainControl.getVueG().getJoueursG().iterator();itJ.hasNext();){
						JoueurGraphique joueurListener = (JoueurGraphique) itJ.next();
							MouseListener[] listeners = joueurListener.getJoueurLabel().getMouseListeners();
						for(int i = 0;i<listeners.length;i++){
							joueurListener.getJoueurLabel().removeMouseListener(listeners[i]);
						}
						}
					}
				});
			}
		}else if ((eventEnabled) && (getCarte().getNom().charAt(0) == 'C')){
			mainControl.getVueG().getMainPanel().remove(engrais);
			mainControl.getVueG().getMainPanel().remove(geant);
			mainControl.getVueG().getMainPanel().remove(farfadet);
			mainControl.getVueG().getMainPanel().repaint();
			mainControl.getVueG().getActionLabel().setText("Vous ne pouvez pas jouer cette carte, elle n'aura aucun effet");
		}
		mainControl.getVueG().getMainPanel().repaint();
	}
	
}
@Override
public void mousePressed(MouseEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void mouseReleased(MouseEvent e) {
	// TODO Auto-generated method stub
	
}

/**
 * Permet de gérer les différentes intéractions avec le passage de la souris sur la carte (zomm sur la carte )
 */
@Override
public void mouseEntered(MouseEvent e) {
	// TODO Auto-generated method stub
	if(position>-1){
	if(eventEnabled){
	this.setIcon(imageG);
	this.setBounds((120)+position*100,385,160, 160);
	this.repaint();
	}
	}
	else{
		this.setIcon(imageG);
		this.setBounds(100*(-1*position), 175, 190, 190);
		this.repaint();	
	}
}
/**
 * Permet de gérer les différentes intéractions avec le exit souris (dezoom de la carte)
 */
@Override
public void mouseExited(MouseEvent e) {
	// TODO Auto-generated method stub
	
	if(position>-1){
	if(eventEnabled){	
	 this.setIcon(imageP);
	this.setBounds(120+position*100, 455, 90, 90);
	}
	}
	else{
		this.setIcon(imageP);
		this.setBounds(100*(-1*position), 175, 190, 190);
		this.repaint();	
	}
}
/**
 * Permet de supprimer tous les listeners présent sur chaque joueur
 */
public void removeJoueurListener(){
	for(Iterator itJ = mainControl.getVueG().getJoueursG().iterator();itJ.hasNext();){
		JoueurGraphique joueurListener = (JoueurGraphique) itJ.next();
			MouseListener[] listeners = joueurListener.getJoueurLabel().getMouseListeners();
		for(int i = 0;i<listeners.length;i++){
			joueurListener.getJoueurLabel().removeMouseListener(listeners[i]);
		}
		}
}
/**
 * Retourne la CarteGraphique associé à un Ingrédient
 * 
 * @param cNom nom de la carte
 * @param index index de la carte (placement sur la vue)
 * @param mainControl controleur de la partie
 * @return
 * @throws IOException
 */
public static CarteGraphique attribuerImageToCarteIngredient(String cNom, int index, MainPartieControleur mainControl) throws Exception{
	if(index>=0){
	if (cNom.equals("I1")){
		CarteGraphique carteG = new CarteGraphique("cartesIngredients/I1G.PNG","cartesIngredients/I1.png",cNom,index,mainControl);
		return carteG;
	}else if(cNom.equals("I2")){
		CarteGraphique carteG = new CarteGraphique("cartesIngredients/I2G.PNG","cartesIngredients/I2.png",cNom,index,mainControl);
		return carteG;
	}else if(cNom.equals("I3")){
		CarteGraphique carteG = new CarteGraphique("cartesIngredients/I3G.PNG","cartesIngredients/I3.png",cNom,index,mainControl);
		return carteG;
	}else if(cNom.equals("I4")){
		CarteGraphique carteG = new CarteGraphique("cartesIngredients/I4G.PNG","cartesIngredients/I4.png",cNom,index,mainControl);
		return carteG;
	}else if(cNom.equals("I5")){
		CarteGraphique carteG = new CarteGraphique("cartesIngredients/I5G.PNG","cartesIngredients/I5.png",cNom,index,mainControl);
		return carteG;
	}else if(cNom.equals("I6")){
		CarteGraphique carteG = new CarteGraphique("cartesIngredients/I6G.PNG","cartesIngredients/I6.png",cNom,index,mainControl);
		return carteG;
	}else if(cNom.equals("I7")){
		CarteGraphique carteG = new CarteGraphique("cartesIngredients/I7G.PNG","cartesIngredients/I7.png",cNom,index,mainControl);
		return carteG;
	}else if(cNom.equals("I8")){
		CarteGraphique carteG = new CarteGraphique("cartesIngredients/I8G.PNG","cartesIngredients/I8.png",cNom,index,mainControl);
		return carteG;
	}else if(cNom.equals("I9")){
		CarteGraphique carteG = new CarteGraphique("cartesIngredients/I9G.PNG","cartesIngredients/I9.png",cNom,index,mainControl);
		return carteG;
	}else if(cNom.equals("I10")){
		CarteGraphique carteG = new CarteGraphique("cartesIngredients/I10G.PNG","cartesIngredients/I10.png",cNom,index,mainControl);
		return carteG;
	}else if(cNom.equals("I11")){
		CarteGraphique carteG = new CarteGraphique("cartesIngredients/I11G.PNG","cartesIngredients/I11.png",cNom,index,mainControl);
		return carteG;
	}else if(cNom.equals("I12")){
		CarteGraphique carteG = new CarteGraphique("cartesIngredients/I12G.PNG","cartesIngredients/I12.png",cNom,index,mainControl);
		return carteG;
	}else if(cNom.equals("I13")){
		CarteGraphique carteG = new CarteGraphique("cartesIngredients/I13G.PNG","cartesIngredients/I13.png",cNom,index,mainControl);
		return carteG;
	}else if(cNom.equals("I14")){
		CarteGraphique carteG = new CarteGraphique("cartesIngredients/I14G.PNG","cartesIngredients/I14.png",cNom,index,mainControl);
		return carteG;
	}else if(cNom.equals("I15")){
		CarteGraphique carteG = new CarteGraphique("cartesIngredients/I15G.PNG","cartesIngredients/I15.png",cNom,index,mainControl);
		return carteG;
	}else if(cNom.equals("I16")){
		CarteGraphique carteG = new CarteGraphique("cartesIngredients/I16G.PNG","cartesIngredients/I16.png",cNom,index,mainControl);
		return carteG;
	}else if(cNom.equals("I17")){
		CarteGraphique carteG = new CarteGraphique("cartesIngredients/I17G.png","cartesIngredients/I17.PNG",cNom,index,mainControl);
		return carteG;
	}else if(cNom.equals("I18")){
		CarteGraphique carteG = new CarteGraphique("cartesIngredients/I18G.PNG","cartesIngredients/I18.png",cNom,index,mainControl);
		return carteG;
	}else if(cNom.equals("I19")){
		CarteGraphique carteG = new CarteGraphique("cartesIngredients/I19G.PNG","cartesIngredients/I19.png",cNom,index,mainControl);
		return carteG;
	}else if(cNom.equals("I20")){
		CarteGraphique carteG = new CarteGraphique("cartesIngredients/I20G.PNG","cartesIngredients/I20.png",cNom,index,mainControl);
		return carteG;
	}else if(cNom.equals("I21")){
		CarteGraphique carteG = new CarteGraphique("cartesIngredients/I21G.PNG","cartesIngredients/I21.png",cNom,index,mainControl);
		return carteG;
	}else if(cNom.equals("I22")){
		CarteGraphique carteG = new CarteGraphique("cartesIngredients/I22G.PNG","cartesIngredients/I22.png",cNom,index,mainControl);
		return carteG;
	}else if(cNom.equals("I23")){
		CarteGraphique carteG = new CarteGraphique("cartesIngredients/I23G.PNG","cartesIngredients/I23.png",cNom,index,mainControl);
		return carteG;
	}else if(cNom.equals("I24")){
		CarteGraphique carteG = new CarteGraphique("cartesIngredients/I24G.PNG","cartesIngredients/I24.png",cNom,index,mainControl);
		return carteG;
	}else {
		return null;
	}
	}
	else{
		if (cNom.equals("I1")){
			CarteGraphique carteG = new CarteGraphique("cartesIngredients/I1.png","cartesIngredients/dos.PNG",cNom,index,mainControl);
			return carteG;
		}else if(cNom.equals("I2")){
			CarteGraphique carteG = new CarteGraphique("cartesIngredients/I2.png","cartesIngredients/dos.PNG",cNom,index,mainControl);
			return carteG;
		}else if(cNom.equals("I3")){
			CarteGraphique carteG = new CarteGraphique("cartesIngredients/I3.png","cartesIngredients/dos.PNG",cNom,index,mainControl);
			return carteG;
		}else if(cNom.equals("I4")){
			CarteGraphique carteG = new CarteGraphique("cartesIngredients/I4.png","cartesIngredients/dos.PNG",cNom,index,mainControl);
			return carteG;
		}else if(cNom.equals("I5")){
			CarteGraphique carteG = new CarteGraphique("cartesIngredients/I5.png","cartesIngredients/dos.PNG",cNom,index,mainControl);
			return carteG;
		}else if(cNom.equals("I6")){
			CarteGraphique carteG = new CarteGraphique("cartesIngredients/I6.png","cartesIngredients/dos.PNG",cNom,index,mainControl);
			return carteG;
		}else if(cNom.equals("I7")){
			CarteGraphique carteG = new CarteGraphique("cartesIngredients/I7.png","cartesIngredients/dos.PNG",cNom,index,mainControl);
			return carteG;
		}else if(cNom.equals("I8")){
			CarteGraphique carteG = new CarteGraphique("cartesIngredients/I8.png","cartesIngredients/dos.PNG",cNom,index,mainControl);
			return carteG;
		}else if(cNom.equals("I9")){
			CarteGraphique carteG = new CarteGraphique("cartesIngredients/I9.png","cartesIngredients/dos.PNG",cNom,index,mainControl);
			return carteG;
		}else if(cNom.equals("I10")){
			CarteGraphique carteG = new CarteGraphique("cartesIngredients/I10.png","cartesIngredients/dos.PNG",cNom,index,mainControl);
			return carteG;
		}else if(cNom.equals("I11")){
			CarteGraphique carteG = new CarteGraphique("cartesIngredients/I11.png","cartesIngredients/dos.PNG",cNom,index,mainControl);
			return carteG;
		}else if(cNom.equals("I12")){
			CarteGraphique carteG = new CarteGraphique("cartesIngredients/I12.png","cartesIngredients/dos.PNG",cNom,index,mainControl);
			return carteG;
		}else if(cNom.equals("I13")){
			CarteGraphique carteG = new CarteGraphique("cartesIngredients/I13.png","cartesIngredients/dos.PNG",cNom,index,mainControl);
			return carteG;
		}else if(cNom.equals("I14")){
			CarteGraphique carteG = new CarteGraphique("cartesIngredients/I14.png","cartesIngredients/dos.PNG",cNom,index,mainControl);
			return carteG;
		}else if(cNom.equals("I15")){
			CarteGraphique carteG = new CarteGraphique("cartesIngredients/I15.png","cartesIngredients/dos.PNG",cNom,index,mainControl);
			return carteG;
		}else if(cNom.equals("I16")){
			CarteGraphique carteG = new CarteGraphique("cartesIngredients/I16.png","cartesIngredients/dos.PNG",cNom,index,mainControl);
			return carteG;
		}else if(cNom.equals("I17")){
			CarteGraphique carteG = new CarteGraphique("cartesIngredients/I17.PNG","cartesIngredients/dos.PNG",cNom,index,mainControl);
			return carteG;
		}else if(cNom.equals("I18")){
			CarteGraphique carteG = new CarteGraphique("cartesIngredients/I18.png","cartesIngredients/dos.PNG",cNom,index,mainControl);
			return carteG;
		}else if(cNom.equals("I19")){
			CarteGraphique carteG = new CarteGraphique("cartesIngredients/I19.png","cartesIngredients/dos.PNG",cNom,index,mainControl);
			return carteG;
		}else if(cNom.equals("I20")){
			CarteGraphique carteG = new CarteGraphique("cartesIngredients/I20.png","cartesIngredients/dos.PNG",cNom,index,mainControl);
			return carteG;
		}else if(cNom.equals("I21")){
			CarteGraphique carteG = new CarteGraphique("cartesIngredients/I21.png","cartesIngredients/dos.PNG",cNom,index,mainControl);
			return carteG;
		}else if(cNom.equals("I22")){
			CarteGraphique carteG = new CarteGraphique("cartesIngredients/I22.png","cartesIngredients/dos.PNG",cNom,index,mainControl);
			return carteG;
		}else if(cNom.equals("I23")){
			CarteGraphique carteG = new CarteGraphique("cartesIngredients/I23.png","cartesIngredients/dos.PNG",cNom,index,mainControl);
			return carteG;
		}else if(cNom.equals("I24")){
			CarteGraphique carteG = new CarteGraphique("cartesIngredients/I24.png","cartesIngredients/dos.PNG",cNom,index,mainControl);
			return carteG;
		}else {
			return null;
		}
	}
}


/**
 * Retourne la CarteGraphique associé à un Allie
 * 
 * @param cNom nom de la carte
 * @param index index de la carte (placement sur la vue)
 * @param mainControl controleur de la partie
 * @return
 * @throws IOException
 */
public static CarteGraphique attribuerImageToCarteAllie(String cNom, int index, MainPartieControleur mainControl) throws Exception{
	if(index>=0){
	if (cNom.equals("T1")){
		CarteGraphique carteG = new CarteGraphique("cartesAllies/T1G.PNG","cartesAllies/T1.png",cNom,index,mainControl);
		return carteG;
	}else if(cNom.equals("T2")){
		CarteGraphique carteG = new CarteGraphique("cartesAllies/T2G.PNG","cartesAllies/T2.png",cNom,index,mainControl);
		return carteG;
	}else if(cNom.equals("T3")){
		CarteGraphique carteG = new CarteGraphique("cartesAllies/T3G.PNG","cartesAllies/T3.png",cNom,index,mainControl);
		return carteG;
	}else if(cNom.equals("C1")){
		CarteGraphique carteG = new CarteGraphique("cartesAllies/C1G.PNG","cartesAllies/C1.png",cNom,index,mainControl);
		return carteG;
	}else if(cNom.equals("C2")){
		CarteGraphique carteG = new CarteGraphique("cartesAllies/C2G.PNG","cartesAllies/C2.png",cNom,index,mainControl);
		return carteG;
	}else if(cNom.equals("C3")){
		CarteGraphique carteG = new CarteGraphique("cartesAllies/C3G.PNG","cartesAllies/C3.png",cNom,index,mainControl);
		return carteG;
	}else{
		return null;
	}
	}
	else{
		if (cNom.equals("T1")){
			CarteGraphique carteG = new CarteGraphique("cartesAllies/T1.png","cartesAllies/dosT.PNG",cNom,index,mainControl);
			return carteG;
		}else if(cNom.equals("T2")){
			CarteGraphique carteG = new CarteGraphique("cartesAllies/T2.png","cartesAllies/dosT.PNG",cNom,index,mainControl);
			return carteG;
		}else if(cNom.equals("T3")){
			CarteGraphique carteG = new CarteGraphique("cartesAllies/T3.png","cartesAllies/dosT.PNG",cNom,index,mainControl);
			return carteG;
		}else if(cNom.equals("C1")){
			CarteGraphique carteG = new CarteGraphique("cartesAllies/C1.png","cartesAllies/dosC.PNG",cNom,index,mainControl);
			return carteG;
		}else if(cNom.equals("C2")){
			CarteGraphique carteG = new CarteGraphique("cartesAllies/C2.png","cartesAllies/dosC.PNG",cNom,index,mainControl);
			return carteG;
		}else if(cNom.equals("C3")){
			CarteGraphique carteG = new CarteGraphique("cartesAllies/C3.png","cartesAllies/dosC.PNG",cNom,index,mainControl);
			return carteG;
		}else{
			return null;
		}	
	}
}

public boolean isEventEnabled() {
	return eventEnabled;
}

public void setEventEnabled(boolean eventEnabled) {
	this.eventEnabled = eventEnabled;
}
public CarteGraphique getCarte(){
	return this;
}


}
