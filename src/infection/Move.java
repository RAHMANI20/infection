package infection;

public class Move{
	
	/** pour stocker la case de depart */
	private int startX;
	private int startY;
	/** pour stocker la case d'arrivée */
	private int arrivX;
	private int arrivY;
	/** pour stocker le type du coup */
	private char type; // 'C' = clonage , 'S' = saut
	
	/** 
	 * constructeur qui initialise un coup
	 * @param startX : x de la case de depart
	 * @param startY : y de la case de depart
	 * @param arrivX : x de la case d'arrivée
	 * @param arrivY : y de la case d'arrivée
	 * @param type : le type de coup 'saut/clonage'
	 */
	
	public Move(int startX,int startY,int arrivX,int arrivY,char type){
		this.startX = startX;
		this.startY = startY;
		this.arrivX = arrivX;
		this.arrivY = arrivY;
		this.type = type;
	}
	
	/**
	 * cette methode permet de verifier l'églité entre deux coups
	 * @param move : le coup à comparer 
	 */
	 
	public boolean equalMove(Move move) {
       return (this.startX == move.startX && this.startY == move.startY && this.arrivX == move.arrivX && this.arrivY == move.arrivY && this.type == move.type);
    }
	
	/** getters and setters */
	
	public int getStartX(){
		return this.startX;
	}
	public void setStartX(int startX){
		this.startX = startX;
	}
	
	public int getStartY(){
		return this.startY;
	}
	public void setStartY(int startY){
		this.startY = startY;
	}
	
	public int getArrivX(){
		return this.arrivX;
	}
	public void setArrivX(int arrivX){
		this.arrivX = arrivX;
	}
	
	public int getArrivY(){
		return this.arrivY;
	}
	public void setArrivY(int arrivY){
		this.arrivY = arrivY;
	}
	
	public char getType(){
		return this.type;
	}
	public void setType(char type){
		this.type = type;
	}
	
	
	
}