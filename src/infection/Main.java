package infection;

import java.util.Random;

public class Main{
	
	public static void main (String[] argvs){
		
		// on verifie l'utilisation de la bonne commande
		if (argvs.length != 3  && argvs.length != 0){
			System.out.println("error usage of parameter : ");
			System.out.println("# for Random players : 0 parameter");
			System.out.println("# for Ia players     : <deepBleu> <deepRed> <usingPruning?>");
            return;
		}
		
		int nbRound = 1; // pour calculer le nombre de tour joué
		
		// on crée son jeux
		State game = new State(7,7);
		
		// afficher la situation initiale du jeux
		System.out.println("\nsituation initiale du jeux\n");
		game.printBoard(); 
        System.out.println("\n************************\n");
		
		if(argvs.length == 3){ // dans le cas ou les joueurs sont IA players
			
			// on recupère les arguments
			int deepBlue = Integer.parseInt(argvs[0]);
            int deepRed = Integer.parseInt(argvs[1]);
            boolean pruning = Boolean.parseBoolean(argvs[2]);
			Player j1,j2;
			if(pruning){ // elagage
				
			    j1 = new AlphaBeta('B',deepBlue);
		        j2 = new AlphaBeta('R',deepRed);
				AlphaBeta.cpt = 0;
				
			}
			else{ // sans elagage 
				
				j1 = new MiniMax('B',deepBlue);
		        j2 = new MiniMax('R',deepRed);
				MiniMax.cpt = 0;
			
			}
			
			Move move = null;
		    while (!game.isOver()){ // cette boucle permet de jouer une partie 
			
			    // on verifie d'abord si le joueur courant va passer son tour , alors on passe au prochain tour
			    if(game.isImpty(game.getMove(game.getCurrentPlayer()))) {
				    System.out.println("\n### le joueur : "+game.getCurrentPlayer()+" doit passer son tour\n");
				    if(game.getCurrentPlayer() == 'B') game.setCurrentPlayer('R');
		            else game.setCurrentPlayer('B');;
				    continue;
			    }
				
				// on affcihe le joueur courant
			    System.out.println("\nle coup est pour le joueur :"+game.getCurrentPlayer());
			
			    //jouer un coup
			    if (game.getCurrentPlayer() == 'B') {
				    move = j1.getBestMove(game);
				    game.play(move);
			    }
			    else {
				    move = j2.getBestMove(game);
				    game.play(move);
			    }
			    
				// afficher des informations sur le coup joué
		    
		        System.out.println("le tour :"+nbRound++);
		        System.out.println("le type du coup : "+(move.getType() == 'C' ? "clonage" : "saut"));
		        System.out.println("ligne et colonne de depart : ["+move.getStartX()+","+move.getStartY()+"]");
		        System.out.println("ligne et colonne darrivee  : ["+move.getArrivX()+","+move.getArrivY()+"]");
			    game.printBoard();  
			
			    System.out.println("************************");
			
		    }
			if(pruning)
			    System.out.println("le nombre de noeud explores au cours de la partie par alphabeta :"+AlphaBeta.cpt);
			else
				System.out.println("le nombre de noeud explores au cours de la partie par minimax :"+MiniMax.cpt);
		}
		else{// joueurs aléatoires
		
		    Random rand = new Random(123);// les methode de la classe random permettent de générer des joueur aléatoire
			
			while (!game.isOver()){ // cette boucle permet de jouer une partie  
			
			    // on verifie d'abord si le joueur courant va passer son tour , alors on passe au prochain tour
			    if(game.isImpty(game.getMove(game.getCurrentPlayer()))) {
				    System.out.println("### le joueur : "+game.getCurrentPlayer()+" doit passer son tour");
				    if(game.getCurrentPlayer() == 'B') game.setCurrentPlayer('R');
		            else game.setCurrentPlayer('B');;
				    continue;
			    }
			    // on affcihe le joueur courant
			    System.out.println("\nle coup est pour le joueur : "+game.getCurrentPlayer());
			
			
			    // on genere un coups d'une maniere aléatoire 
			    int coup = rand.nextInt(game.getMove(game.getCurrentPlayer()).size());
		    
			    //jouer le coup choisi d'une maniere aléatoire
			    Move move = game.getMove(game.getCurrentPlayer()).get(coup);
			    game.play(move);
			
			
			    // afficher des informations sur le coup joué + tableau aprés avoir joué le coup
		    
			    System.out.println("le tour :"+nbRound++);
		        System.out.println("le type du coup : "+(move.getType() == 'C' ? "clonage" : "saut"));
		        System.out.println("ligne et colonne de depart : ["+move.getStartX()+","+move.getStartY()+"]");
		        System.out.println("ligne et colonne darrivee  : ["+move.getArrivX()+","+move.getArrivY()+"]");
			    game.printBoard();  
			
			    System.out.println("************************");
			
		    }
		}
	
	
	
		
		// afficher le joueur gagant
		System.out.println("the winner is :"+game.getWinner());
		System.out.println("pion bleu = "+(int)game.getNbPawnBlue()+"\npion rouge = "+(int)game.getNbPawnRed());
		
		
	} 


}