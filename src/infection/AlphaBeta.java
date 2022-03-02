package infection;

import java.util.ArrayList;

public class AlphaBeta extends Player{
	

	public static int cpt ; // pour calculer le nombre de noeud explorés
	
	/**
	 *  contructeur qui initialise un joueur alphaBeta 
	 *  @param player : le joueur qui raisonne
	 *  @param depth  : la profondeur de son raisonnement
	*/
	 
	public AlphaBeta(char player,int depth){
		
		super(player,depth);
		
	}
	
	/**
	 * cette methode permet de retourner le maximum entre deux valeur
	 * @param a,b: les deux valeurs à comparer
	*/
	public double max(double a,double b){
		
		if(a > b) return a;
		else return b;
		
	}
	
	/**
	 * cette methode permet de retourner le minimum entre deux valeur
	 * @param a,b: les deux valeurs à comparer
	*/
	public double min(double a,double b){
		
		if(a < b) return a;
		else return b;
		
	}
	
    /**
	 * L'élagage alpha-beta permet d'optimiser l'algorithme minimax sans en modifier le résultat
     *  	 
	 * 
	 * @param state : l'etat à évaluer
	 * @param a     : valeur la plus basse que le joueur Max sait pouvoir obtenir
	 * @param b     : valeur maximale que le joueur Min autorisera Max à obtenir
	 * @param depth : la profondeur de raisonnement
	*/
	@Override
	public double alphabeta(State state,double a,double b,int depth){
		
		AlphaBeta.cpt++; // on est dans un noeud donc on incremente le compteur
		
		//si l'etat est terminal ou bien la profondeur est atteinte on retourne le score du joueur qui raisonne
		if (depth == 0 || state.isOver()) {
			return state.getScore(player); 
		}
		else{
			if (state.getCurrentPlayer() == this.player){// si le joueur du noeud est le joueur qui raisonne
				// on recupère les coups possibles
				ArrayList<Move> movesPossible = state.getMove(state.getCurrentPlayer());
				for(Move move: movesPossible){// on parcourt les coups possibles 
					State copy = state.copy(); // on crée une copie de l'etat pour tester le coup
					a = this.max(a,alphabeta(copy.play(move),a,b,depth-1)); 
					if(a >= b){ // si on atteint ou bien on depasse le score maximale autorisé 
						return a; //on retourne directement la valeur a sans perdre de temps à faire des calcules inutiles
					}
				}
				return a;
			}
			else{// si le joueur du noeud est le joueur adversaire
				// on recupère les coups possibles
				ArrayList<Move> movesPossible = state.getMove(state.getCurrentPlayer());
				for(Move move: movesPossible){// on parcourt les coups possibles 
					State copy = state.copy();// on crée une copie de l'etat pour tester le coup 
					b = this.min(b,alphabeta(copy.play(move),a,b,depth-1));
					if(a >= b){// si on atteint ou bien on depasse le score maximale autorisé
						return b;//on retourne directement la valeur b sans perdre de temps à faire des calcules inutiles
					}
				}
				return b;
			}
			
 		}
		
	}
	
 	/**
	 *  cette methode permet de retourner le meilleur coup pour le joueur qui raisonne
	 *  @param state : l'etat pour lequel on doit choisir le meilleur coup (joueur courant = player)   
	 *
	 *        max.getBestMove(): l'appel se fait par le joueur max	(qui raisonne) 
	 *         |                       
	 *        alphabeta(min,-2,2,depth): pour le premier appel de minimax , la racine represente le joueur min  
	 *  
	 *  on initialise a et b par -2,2 <=> -infini , +infini car (0 <= score <= 1)  
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
			double value = alphabeta(nextState,-2,2,this.depth);
			// on choisit le coup qui donne plus de chance pour gagner (qui donne un etat avec la meilleur evaluation) 
			if(value > bestValue){
				bestValue = value;
				bestMove = move;
			}
		}
		return bestMove; // on retourne le meilleur coup
	}
	
	// on a redifinit minimax car elle est definit comme abstract dans player mais on va pas l'appeler sur un AlphaBeta
	@Override
	public double minimax(State state,int depth){ return 0; };
	

	
	
}