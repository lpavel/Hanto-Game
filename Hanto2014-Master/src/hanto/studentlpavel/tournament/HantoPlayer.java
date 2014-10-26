/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package hanto.studentlpavel.tournament;

import static hanto.common.HantoPlayerColor.*;

import java.util.List;
import java.util.Random;

import common.HantoTestGame.PieceLocationPair;
import hanto.common.HantoException;
import hanto.common.HantoGameID;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.studentlpavel.BaseHantoCoordinate;
import hanto.studentlpavel.BaseHantoMove;
import hanto.studentlpavel.epsilon.EpsilonHantoGame;
import hanto.tournament.HantoGamePlayer;
import hanto.tournament.HantoMoveRecord;

/** Hanto Player is an AI that plays an Epsilon Hanto
 */
public class HantoPlayer implements HantoGamePlayer {

	boolean moveFirst;
	int gameTurn;
	
	private EpsilonHantoGame game;	
	
	/**
	 * Constructor for HantoPlayer.
	 */
	public HantoPlayer() {
		gameTurn = 0;
	}
	
	@Override
	public void startGame(HantoGameID version, HantoPlayerColor myColor,
			boolean doIMoveFirst) {
		
		switch (myColor) {
		case RED: 
			if(doIMoveFirst) {
				game = new EpsilonHantoGame(RED);
			}
			else {
				game = new EpsilonHantoGame(BLUE);
			}
			break;
		case BLUE:
			if(doIMoveFirst) {
				game = new EpsilonHantoGame(BLUE);
			}
			else {
				game = new EpsilonHantoGame(RED);
			}
			break;
		}
	}

	@Override
	public HantoMoveRecord makeMove(HantoMoveRecord opponentsMove) {
		if(opponentsMove == null) {
			++gameTurn;
			HantoMoveRecord move = new HantoMoveRecord(HantoPieceType.BUTTERFLY, null, new BaseHantoCoordinate(0, 0));
			// make the move and then return it
			try {
				game.makeMove(move.getPiece(), move.getFrom(), move.getTo());
			} catch (HantoException e) {
				e.printStackTrace();
			}
			return move;
		}

		// first add opponent's move 
		try {
			game.makeMove(opponentsMove.getPiece(), opponentsMove.getFrom(), opponentsMove.getTo());
		} catch (HantoException e) {
			e.printStackTrace();
		}
		
		// generate all possible moves
		List<BaseHantoMove> possibleMoves = game.getListOfAllPossibleMoves();
			
		// if first move for the player, than place butterfly
		if(gameTurn < 1) {
			gameTurn++;
			for(BaseHantoMove move : possibleMoves) {
				if(move.getPiece().getType() == HantoPieceType.BUTTERFLY) {
					try {
						game.makeMove(move.getPiece().getType(), move.getFrom(), move.getTo());
					} catch (HantoException e) {
						e.printStackTrace();
					}
					return new HantoMoveRecord(move.getPiece().getType(), move.getFrom(), move.getTo());					
				}
			}
		}		
		++gameTurn;
		// if no move left surrender
		if(possibleMoves.size() == 0) {
			try {
				game.makeMove(null, null, null);
			} catch (HantoException e) {
				e.printStackTrace();
			}
			return new HantoMoveRecord(null, null, null);
		}

		// else choose a random move from the created ones
		int idx = new Random().nextInt(possibleMoves.size());
		BaseHantoMove retMove = (BaseHantoMove) possibleMoves.toArray()[idx];

		// make the move and return it
		try {
			game.makeMove(retMove.getPiece().getType(), retMove.getFrom(), retMove.getTo());
		} catch (HantoException e) {
			e.printStackTrace();
		}
		return new HantoMoveRecord(retMove.getPiece().getType(), retMove.getFrom(), retMove.getTo());
	}

	
	/**
	 * Method initializeBoard. - for testing purposes. Helps set up a board
	 * @param initialPieces PieceLocationPair[]
	 * @param currentPlayer HantoPlayerColor
	 */
	public void initializeBoard(PieceLocationPair[] initialPieces,
			HantoPlayerColor currentPlayer)	{
		gameTurn = 5; // does not matter what value, just not 0 or 1 
		game = new EpsilonHantoGame(currentPlayer, initialPieces);
	}	
}
