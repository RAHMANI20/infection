package infection;

public abstract class Player{
	
    protected char player; // pour stocker le joueur qui raisonne
 	protected int  depth;  // pour stocker la profondeur de raisonnement
	
	/**
	 *  contructeur qui initialise un joueur minimax 
	 *  @param player : le joueur qui raisonne
	 *  @param depth  : la profondeur de son raisonnement
	*/
	 
	public Player(char player,int depth){
		
		this.player = player;
		this.depth = depth;
		
	}
	
    
	public abstract double minimax(State state,int depth);
	
	public abstract double alphabeta(State state,double a,double b,int depth);
	
	public abstract Move getBestMove(State state);
	
	



}