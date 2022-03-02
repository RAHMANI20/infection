package infection;

import java.util.ArrayList;

public class State{
	
	
	private int columns; // nombre de colonnes  
	private int rows; // nombre de lignes
	private char[][] board; // la grille 
	private ArrayList<State> lastState ; // liste pour stocker les copies des états joués
	private char currentPlayer; // joueur courant  'B' = bleu , 'R' = rouge 
	private double nbPawnRed;// pour stocker le nombre de pion rouge 'le score du joueur rouge'
	private double nbPawnBlue;// pour stocker le nombre de pion bleu 'le score du joueur bleu'
     
	/** 
	 * constructeur qui initialise un etat de jeux 
	 * creation de la liste des etats/initialisation des colonnes et lignes/creation de la grille
	 * initialisation du joueur courant/initialisation des pions '2/2'
	 * @param columns : nombre de colonnes
	 * @param rows : nombre de lignes
	 */
	
	public State(int columns , int rows){
		
		this.columns = columns; 
		this.rows = rows;
		this.board = new char[this.rows][this.columns]; 
		this.lastState = new ArrayList<>();
		this.currentPlayer = 'B';
		this.nbPawnBlue = 2;
		this.nbPawnRed = 2;
		// on initialise la grille avec des X
		for(int i = 0;i < this.rows;i++) {
			for(int j = 0;j < this.columns;j++) {
				
				this.board[i][j] = 'X';
			
			}
		}
		// on place les pions des joueurs
		this.board[0][0] = 'R'; this.board[this.rows-1][this.columns-1] = 'R';
		this.board[this.rows-1][0] = 'B'; this.board[0][this.columns-1] = 'B';
		
		
	}
	
	
	/** cette methode permet d'afficher la grille */
	
	public void printBoard() {
			
			
		for(int i = 0;i < this.rows;i++) {
			for(int j = 0;j < this.columns;j++) {
				
				System.out.print(this.board[i][j]+" ");
				
			}
			System.out.println("");
		}
	}
	
	
	/** cette methode permet de créer une copie de l'etat courant */
	
	public State copy(){
		
		State copy = new State(this.rows,this.columns);
	 
		for(int i = 0;i < this.rows;i++){
			for(int j = 0;j < this.columns;j++){
				copy.board[i][j] = this.board[i][j];
			}
		}
		
		for(State state:this.lastState){// pour eviter la modification de la liste de l'etat courant
			copy.lastState.add(state);
		}
		copy.currentPlayer = this.currentPlayer;
		copy.nbPawnBlue = this.nbPawnBlue;
		copy.nbPawnRed = this.nbPawnRed;
		
		return copy;
	}
	
	
	
	
	
	
	
	/** cette methode permet de créer une copie de l'etat courant et de la stocker dans la liste 
	 *  l'idée est de créer un etat principal qui represente le jeux et avant chaque coup 
	 *  on crée une copie de jeux (etat courant) et on la sauvegarde dans la liste 
	 */
	
	public void saveState(){
		 
		this.lastState.add(this.copy());
	
	}
	
	/** 
	 *  cette methode verifie si l'etat courant existe dans la liste
	 *  autrement dit verifie si le plateau de jeu revient dans un état qui a déjà été joué
	 */
	
	public boolean existState(){
		// je parcours la liste des etats déja joués
		for(State state : this.lastState){
			int i=0; 
			boolean exist = true;
			while( i < this.rows && exist){
				int j=0;
				while( j < this.columns && exist){
					if(state.board[i][j] != this.board[i][j]) exist = false;
					else j++;
				}
				i++;
			}
			if(exist == true) return true; // si l'etat courant existe dans la liste on retourne true
		}
		return false; // on a pas trouvé l'etat courant dans la liste donc on retourne false
	}
    
	/** 
	 *  cette methode permet de retourner la liste des Move possible pour le joueur courant 
	 *  @param player : le joueur pour lequel on retourne les coups possible sous la forme d'une liste
	 */
	
	public ArrayList<Move> getMove(char player){
		
		ArrayList<Move> moveList = new ArrayList<>(); 
		
		for(int i=0;i < this.rows;i++){
			for(int j=0;j < this.columns;j++){
				
				
				if(this.board[i][j] == player){
					// cloner : verifier les cases vides à une distance de 1
					
					//en haut
					if(i-1 >= 0 && this.board[i-1][j] == 'X') {moveList.add(new Move(i,j,i-1,j,'C'));} 
					//en bas
					if(i+1 < this.rows && this.board[i+1][j] == 'X') {moveList.add(new Move(i,j,i+1,j,'C'));}
					//à droite
					if(j+1 < this.columns && this.board[i][j+1] == 'X') {moveList.add(new Move(i,j,i,j+1,'C'));}
					//à gauche
					if(j-1 >= 0 && this.board[i][j-1] == 'X') {moveList.add(new Move(i,j,i,j-1,'C'));}
					// diagonale droite/haut
					if(j+1 < this.columns && i-1 >= 0 && this.board[i-1][j+1] == 'X') {moveList.add(new Move(i,j,i-1,j+1,'C'));}
					// diagonale droite/bas
					if(j+1 < this.columns && i+1 < this.rows && this.board[i+1][j+1] == 'X') {moveList.add(new Move(i,j,i+1,j+1,'C'));}
					// diagonale gauche/haut
					if(j-1 >= 0 && i-1 >= 0 && this.board[i-1][j-1] == 'X') {moveList.add(new Move(i,j,i-1,j-1,'C'));}
					// diagonale gauche/bas
					if(j-1 >= 0 && i+1 < this.rows && this.board[i+1][j-1] == 'X') {moveList.add(new Move(i,j,i+1,j-1,'C'));}
					
					// sauter : verifier les cases vides à une distance de 2
					
					//en haut
					if(i-2 >= 0 && this.board[i-2][j] == 'X') {moveList.add(new Move(i,j,i-2,j,'S'));}
					//en bas
					if(i+2 < this.rows && this.board[i+2][j] == 'X') {moveList.add(new Move(i,j,i+2,j,'S'));}
					//à droite
					if(j+2 < this.columns && this.board[i][j+2] == 'X') {moveList.add(new Move(i,j,i,j+2,'S'));}
					//à gauche
					if(j-2 >= 0 && this.board[i][j-2] == 'X') {moveList.add(new Move(i,j,i,j-2,'S'));}
					//diagonale droite/haut
					if(j+2 < this.columns && i-2 >= 0 && this.board[i-2][j+2] == 'X') {moveList.add(new Move(i,j,i-2,j+2,'S'));}
					//diagonale droite/bas
					if(j+2 < this.columns && i+2 < this.rows && this.board[i+2][j+2] == 'X') {moveList.add(new Move(i,j,i+2,j+2,'S'));}
					//diagonale gauche/haut
					if(j-2 >= 0 && i-2 >= 0 && this.board[i-2][j-2] == 'X') {moveList.add(new Move(i,j,i-2,j-2,'S'));}
					//diagonale gauche/bas
					if(j-2 >= 0 && i+2 < this.rows && this.board[i+2][j-2] == 'X') {moveList.add(new Move(i,j,i+2,j-2,'S'));}
				
					
				}
				
				
				
				
				
			}
		}
		return moveList; // on retourne la liste des coups possibles pour le joueur courant
	}
	

	
	/** 
	 *  cette methode permet d'infecter les pion qui sont adjacents à un pion 
	 *  déplacé par un saut ou un pion nouvellement créé par clonage 
	 *  @param x : ligne de la case qui infecte 
	 *  @param y : colonne de la case qui infecte
	 */

	public void infect(int x,int y){
		
		if(this.getCurrentPlayer() == 'B') {// le joueur bleu qui infecte
			if(x-1 >= 0 && this.board[x-1][y] == 'R'){//haut
				this.board[x-1][y] = 'B' ;this.nbPawnBlue++;this.nbPawnRed--;
			}
			if(x+1 < this.rows && this.board[x+1][y] == 'R'){//bas
				this.board[x+1][y] = 'B' ;this.nbPawnBlue++;this.nbPawnRed--;
			}
			if(y-1 >= 0 && this.board[x][y-1] == 'R'){//gauche
				this.board[x][y-1] = 'B' ;this.nbPawnBlue++;this.nbPawnRed--;
			}
			if(y+1 < this.columns && this.board[x][y+1] == 'R'){//droite
				this.board[x][y+1] = 'B' ;this.nbPawnBlue++;this.nbPawnRed--;
			}
			if(x-1 >= 0 && y-1 >= 0 && this.board[x-1][y-1] == 'R'){//diagonale haut/gauche
				this.board[x-1][y-1] = 'B' ;this.nbPawnBlue++;this.nbPawnRed--;
			}
			if(x-1 >= 0 && y+1 < this.columns && this.board[x-1][y+1] == 'R'){//diagonale haut/droite
				this.board[x-1][y+1] = 'B' ;this.nbPawnBlue++;this.nbPawnRed--;
			}
			if(x+1 < this.rows && y-1 >= 0 && this.board[x+1][y-1] == 'R'){//diagonale bas/gauche
				this.board[x+1][y-1] = 'B' ;this.nbPawnBlue++;this.nbPawnRed--;
			}
			if(x+1 < this.rows && y+1 < this.columns && this.board[x+1][y+1] == 'R'){//diagonale bas/droite
				this.board[x+1][y+1] = 'B' ;this.nbPawnBlue++;this.nbPawnRed--;
			}
		}
		else{// le joueur rouge qui infecte 
			if(x-1 >= 0 && this.board[x-1][y] == 'B'){//haut
				this.board[x-1][y] = 'R' ;this.nbPawnRed++;this.nbPawnBlue--;
			}
			if(x+1 < this.rows && this.board[x+1][y] == 'B'){//bas
				this.board[x+1][y] = 'R' ;this.nbPawnRed++;this.nbPawnBlue--;
			}
			if(y-1 >= 0 && this.board[x][y-1] == 'B'){//gauche
				this.board[x][y-1] = 'R' ;this.nbPawnRed++;this.nbPawnBlue--;
			}
			if(y+1 < this.columns && this.board[x][y+1] == 'B'){//droite
				this.board[x][y+1] = 'R' ;this.nbPawnRed++;this.nbPawnBlue--;
			}
			if(x-1 >= 0 && y-1 >= 0 && this.board[x-1][y-1] == 'B'){//diagonale haut/gauche
				this.board[x-1][y-1] = 'R' ;this.nbPawnRed++;this.nbPawnBlue--;
			}
			if(x-1 >= 0 && y+1 < this.columns && this.board[x-1][y+1] == 'B'){//diagonale haut/droite
				this.board[x-1][y+1] = 'R' ;this.nbPawnRed++;this.nbPawnBlue--;
			}
			if(x+1 < this.rows && y-1 >= 0 && this.board[x+1][y-1] == 'B'){//diagonale bas/gauche
				this.board[x+1][y-1] = 'R' ;this.nbPawnRed++;this.nbPawnBlue--;
			}
			if(x+1 < this.rows && y+1 < this.columns && this.board[x+1][y+1] == 'B'){//diagonale bas/droite
				this.board[x+1][y+1] = 'R' ;this.nbPawnRed++;this.nbPawnBlue--;
			}
		}
		
	}

	
	
	
	/** 
	 *  cette methode permet de jouer un coup 
	 *  @param move : le coup à jouer
	 */
	
	public State play(Move move){
		
		
		// sauvegarder l'etat courant
		
		this.saveState();
		
		//récupérer les informations du coup 'case de depart' , 'case d'arrivée' , 'type du coup'
		
		int startX = move.getStartX();
		int startY = move.getStartY();
		int arrivX = move.getArrivX();
		int arrivY = move.getArrivY();
		char type  = move.getType();
		
		// jouer le coup
		
		if(type == 'S'){// on fait un deplacement(saut) à une distance de 2
		    this.board[arrivX][arrivY] = this.currentPlayer;// sauter
		    this.board[startX][startY] = 'X';// mise à jour de la case de depart
			
		}
		else{// on fait un clonage à une distance de 1
			this.board[arrivX][arrivY] = this.currentPlayer;//cloner
		    // augmenter le score pour le joueur courant +1
			if(this.getCurrentPlayer() == 'B') this.nbPawnBlue++;
			else this.nbPawnRed++;
		}
		
		// infecter les cases adjacentes
		
		this.infect(arrivX,arrivY);
		
		// changer le joueur courant
		
		if(this.currentPlayer == 'B') this.currentPlayer = 'R';
		else this.currentPlayer = 'B';
		
		
		// retourner l'etat courant (le nouveau etat)
		return this;
		
		
	}
	
	
	
	/** 
	 * cette methode permet de vérifier si la listes des coups possible est vide 
	 * @param moveList : liste à verifier
	 */
	
	public boolean isImpty(ArrayList<Move> moveList){
		if (moveList.size() == 0) return true;
		else return false;
	}
	
	
	
	
	/** cettte methode permet de verifier si la partie est fini */
	
	public boolean isOver(){
		//un des joueurs n’a plus de pion de sa couleur sur le plateau
		if (this.nbPawnBlue == 0 || this.nbPawnRed == 0){ 
			return true;
		}	
		//le plateau de jeu revient dans un état qui a déjà été joué
		if (this.existState()){
			return true;
		}	
		// les deux joueurs doivent passer leur tour
		if(this.isImpty(this.getMove('B')) && this.isImpty(this.getMove('R'))){ 
		    return true;
		}	
		return false; // la partie n'est pas fini
		
		
	}
	
	/** 
	 *   cette methode permet de retourner l’évaluation de l’état par le joueur passé en argument
	 *   la proportion de pions que possède le joueur par rapport à son adversaire
	 *   @param player : le joueur pour lequel on retourne le score
	 */

	public double getScore(char player){
		

		if(player == 'B'){
			return this.nbPawnBlue/(this.nbPawnBlue+this.nbPawnRed);
		}
		else{
			return this.nbPawnRed/(this.nbPawnBlue+this.nbPawnRed);
		}
		
	}
	
	/**
	 *  cette methode permet de retourner le joueur gagant
	 */
	
	public String getWinner(){
		if(this.getScore('B')>this.getScore('R')) return "joueur bleu";
		if(this.getScore('R')>this.getScore('B')) return "joueur rouge";
		return "match nul";
	}
	
	
	/** getters and setters */
	
	public char[][] getBoard(){
		return this.board;
	}
	public void setBoard(char[][] board){
		this.board = board;
	}
	
	public int getColumns(){
		return this.columns;
	}
	public void setColumns(int columns){
		this.columns = columns;
	}
	
	public int getRows(){
		return this.rows;
	}
	public void setRows(int rows){
		this.rows = rows;
	}
	
	public char getCurrentPlayer(){
		return this.currentPlayer;
	}
	public void setCurrentPlayer(char currentPlayer){
		this.currentPlayer = currentPlayer;
	}
	
	public ArrayList<State> getLastState(){
		return this.lastState;
	}
	public void setLastState(ArrayList<State> lastState){
		this.lastState = lastState;
	}
	
	public double getNbPawnBlue(){
		return this.nbPawnBlue;
	}
	public void setNbPawnBlue(double nbPawnBlue){
		this.nbPawnBlue = nbPawnBlue;
	}
	
	public double getNbPawnRed(){
		return this.nbPawnRed;
	}
	public void setNbPawnRed(double nbPawnRed){
		this.nbPawnRed = nbPawnRed;
	}





















}