package infection;

import java.util.ArrayList;

public class MiniMax extends Player{
	
	
	public static int cpt; // pour calculer le nombre de noeud explorés
	
	/**
	 *  contructeur qui initialise un joueur minimax 
	 *  @param player : le joueur qui raisonne
	 *  @param depth  : la profondeur de son raisonnement
	*/
	 
	public MiniMax(char player,int depth){
		
		super(player,depth);
		
	}
	
    /**
	 * cette methode permet d'evaluer un etat (min ou max) et elle retourne :
	 *    la valeur minimale pour un joueur min 
	 *    la valeur maximale pour un joueur max
	 *    l'valuation dépend de la profondeur de raisonnement et du joueur qui raisonne 
	 * on appelle cette methode pour la premiere fois dans la methode getBestMove (le max qui fait l'appel)
     * donc la racine represente un etat dont le joueur courant est le joueur min
	 *
	 *                      max                                                       
	 *                    /  |  \          le joueur qui raisonne appelle pour chaque                                      
	 *        d=0  min   *   *   *         coups possible minmax(avec etat min)           
	 *                 / | \    / \        et par la suite on va avoir des appels                                    
	 *        d=1 max *  *  *  *   *       recursifs de minmax() et le nombre                                           
	 *                  / \     \          d'appel dépend de la profondeur de 
	 *                                     raisonnement du max et l'etat (terminal ou non)
     *        ..      . ...     .. .. 	 
	 * 
	 * @param state : l'etat à évaluer
	 * @param depth : la profondeur de raisonnement
	*/
	@Override
	public double minimax(State state,int depth){
		
		double b; 
		MiniMax.cpt++; // on est dans un noeud donc on incremente le compteur
		
		//si l'etat est terminal ou bien la profondeur est atteinte on retourne le score du joueur qui raisonne
		if (depth == 0 || state.isOver()) {
            // on retourne l'evaluation de l'etat pour le joueur qui raisonne 
			return state.getScore(this.player); 
		}
		else{
			if (state.getCurrentPlayer() == this.player){// si le joueur du noeud est le joueur qui raisonne
				b = -2; // on a inialisé avec -2 car :   0 <= le score <= 1 
				// on recupère les coups possibles
				ArrayList<Move> movesPossible = state.getMove(state.getCurrentPlayer());
				for(Move move: movesPossible){ // on parcourt les coups possibles
					State copy = state.copy(); // on crée une copie de l'etat pour tester le coup 
					// on joue le coup sur la copie et on appelle minmax qui permet d'evaluer l'etat suivant
					double m = minimax(copy.play(move),depth-1); 
					if(b < m){ // on maximise le score car on suppose toujours que le joueur max vas jouer le meilleur
						b = m; // coup qui lui donne le meilleur score
					}
				}
				
			}
			else{// si le joueur du noeud est le joueur adversaire
				b = 2; // on a inialisé avec 2 car :   0 <= le score <= 1
				//on recupère les coups possibles
				ArrayList<Move> movesPossible = state.getMove(state.getCurrentPlayer());
				for(Move move: movesPossible){// on parcourt les coups possibles 
					State copy = state.copy(); // on crée une copie de l'etat pour tester le coup 
					// on joue le coup sur la copie et on appelle minmax qui permet d'evaluer l'etat suivant
					double m = minimax(copy.play(move),depth-1);
					if(b > m){// on minimise le score car on suppose toujours que l'adversaire min vas jouer le meilleur
						b = m;// coup qui donne au max le mauvais score
					}
				}
			}
			return b; // on retourne l'evaluation de l'etat pour max 
 		}
		
	}
	
	/**
	 *  cette methode permet de retourner le meilleur coup pour le joueur qui raisonne
	 *  @param state : l'etat pour lequel on doit choisir le meilleur coup (joueur courant = player)   
	 *
	 *        max.getBestMove(): l'appel se fait par le joueur max	(qui raisonne) 
	 *         |                       
	 *        minimax(): pour le premier appel de minimax , la racine represente le joueur min  
	 *
    */
	
	@Override
	public Move getBestMove(State state){
		Move bestMove = null; // pour enregistrer le meilleur coup
		double bestValue = -2; // pour stocker la meilleur valeur d'un etat
		
		// on recupère les coups possibles pour le joueur qui raisonne 
		ArrayList<Move> movesPossible = state.getMove(this.player);
		for(Move move : movesPossible){ // on parcourt tous les coups possibles
			
			
			
			//on crée une copie de l'etat courant 
			State copy = state.copy();
	
			// on teste un coup possible sur la copie 
			State nextState = copy.play(move);
			// on fait une evaluation de l'etat suivant pour le joueur qui raisonne
			double value = minimax(nextState,this.depth);
			// on choisit le coup qui donne plus de chance pour gagner (qui donne un etat avec la meilleur evaluation) 
			if(value > bestValue){
				bestValue = value;
				bestMove = move;
			}
		}
		return bestMove; // on retourne le meilleur coup
	}
	
	// on a redifinit alphabeta car elle est definit comme abstract dans player mais on va pas l'appeler sur un MiniMax
	@Override
	public double alphabeta(State state,double a,double b,int depth){ return 0; };
	
	
	
}