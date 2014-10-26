/******************************************************************************* 
 * This files was developed for CS4233: Object-Oriented Analysis & Design. 
 * The course was taken at Worcester Polytechnic Institute. 
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Eclipse Public License v1.0 
 * which accompanies this distribution, and is available at * http://www.eclipse.org/legal/epl-v10.html 
 * *******************************************************************************/
package hanto.studentlpavel;

import java.util.List;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoGame;
import hanto.common.HantoGameID;
import hanto.common.HantoPiece;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;
import hanto.studentlpavel.beta.BetaMoveRules;
import hanto.studentlpavel.delta.DeltaMoveRules;
import hanto.studentlpavel.epsilon.EpsilonMoveRules;
import hanto.studentlpavel.gamma.GammaMoveRules;

/**
 */
public abstract class BaseHantoGame implements HantoGame {

	protected BaseHantoMovesStore movesMap;
	protected HantoGameID gameID;
	protected boolean isGameOver;
	
	/**
	 * Constructor for BaseHantoGame.
	 * @param firstPlayerColor HantoPlayerColor
	 * @param gameID HantoGameID
	 */
	protected BaseHantoGame(final HantoPlayerColor firstPlayerColor, final HantoGameID gameID) {
		this.gameID = gameID;
		isGameOver = false;
		BaseHantoGameFactory(firstPlayerColor);
	}
	
	/**
	 * Method BaseHantoGameFactory. Uses the factory pattern to choose
	 * an implementation of a movesStore that has specific rules
	 * @param firstPlayerColor HantoPlayerColor
	 */
	public void BaseHantoGameFactory(final HantoPlayerColor firstPlayerColor){
		switch(gameID) {
		case BETA_HANTO:
			movesMap = new BetaMoveRules(firstPlayerColor);
			break;
		case GAMMA_HANTO:
			movesMap = new GammaMoveRules(firstPlayerColor);
			break;
		case DELTA_HANTO:
			movesMap = new DeltaMoveRules(firstPlayerColor);
			break;
		case EPSILON_HANTO:
			movesMap = new EpsilonMoveRules(firstPlayerColor);
			break;
		}
	}
	
	/**
	 * Method placePiece. It places a piece where on the table
	 * @param to BaseHantoCoordinate
	 * @param piece BaseHantoPiece
	
	 * @throws HantoException */
	protected void placePiece(final BaseHantoCoordinate to, 
			final BaseHantoPiece piece) throws HantoException {
		if(!movesMap.placePiece(to, piece)) {
			throw new HantoException("Invalid Placement");
		}
		updateGameTurn();
	}
	
	/**
	 * Method movePiece. Moves a piece from a location to a very close one
	 * @param from BaseHantoCoordinate
	 * @param to BaseHantoCoordinate
	 * @param piece BaseHantoPiece
	
	 * @throws HantoException */
	protected void movePiece(final BaseHantoCoordinate from, final BaseHantoCoordinate to, 
			final BaseHantoPiece piece) throws HantoException {
		if(!movesMap.walkPiece(from, to, piece)) {
			throw new HantoException("Invalid Move");
		}
		updateGameTurn();
	}

	/**
	 * Method flyPiece. "Flies" a piece from one location to any one that 
	 * keeps the board contiguous
	 * @param from BaseHantoCoordinate
	 * @param to BaseHantoCoordinate
	 * @param piece BaseHantoPiece
	
	 * @throws HantoException */
	protected void flyPiece(final BaseHantoCoordinate from, final BaseHantoCoordinate to, 
			final BaseHantoPiece piece) throws HantoException {
		if(!movesMap.flyPiece(from, to, piece)) {
			throw new HantoException("Invalid Move");
		}
		updateGameTurn();
	}

	/**
	 * Method jumpPiece. "Jumps" a piece from one location to any one that 
	 * keeps the board contiguous
	 * @param from BaseHantoCoordinate
	 * @param to BaseHantoCoordinate
	 * @param piece BaseHantoPiece
	
	 * @throws HantoException */
	protected void jumpPiece(final BaseHantoCoordinate from, final BaseHantoCoordinate to, 
			final BaseHantoPiece piece) throws HantoException {
		if(!movesMap.jumpPiece(from, to, piece)) {
			throw new HantoException("Invalid Move");
		}
		updateGameTurn();
	}

	/**
	 * Method applyMove. Helper function that takes care of deciding what type
	 * of move should it do
	 * @param from BaseHantoCoordinate
	 * @param to BaseHantoCoordinate
	 * @param piece BaseHantoPiece
	
	 * @throws HantoException */
	protected void applyMove(final BaseHantoCoordinate from, final BaseHantoCoordinate to, 
			final BaseHantoPiece piece) throws HantoException {
		checkAdmissiblePiece(piece);
		if(from == null) {
			placePiece(to, piece);
		}
		else {
			if(movesMap.canWalk(piece.getType())) {
				movePiece(from, to, piece);
			}
			else if(movesMap.canFly(piece.getType())) {
				flyPiece(from, to, piece);
			}
			else if(movesMap.canJump(piece.getType())) {
				jumpPiece(from, to, piece);
			}
			else {
				throw new HantoException("Invalid Move");
			}
		}
	}
	
	/**
	 * Method checkGameAlreadyOver. Checks if the game is already over
	 * @param maxNumberOfMoves int
	
	 * @throws HantoException */
	protected void checkGameAlreadyOver(final int maxNumberOfMoves) throws HantoException {
		if(movesMap.checkStatus() == MoveResult.BLUE_WINS ||
				movesMap.checkStatus() == MoveResult.RED_WINS) {
			throw new HantoException("The Game is already over");
		}
		if(movesMap.getNumberMoves() >= maxNumberOfMoves) {
			throw new HantoException("The Game is over");
		}
	}
	
	/**
	 * Method checkAdmissiblePiece. Checks if the piece can be used
	 * @param piece BaseHantoPiece
	
	 * @throws HantoException */
	protected void checkAdmissiblePiece(final BaseHantoPiece piece) throws HantoException {
		if(!movesMap.isAdmissiblePiece(piece.getType())) {
			throw new HantoException("Invalid piece. Use only what "
					+ "is allowed in this version");
		}
	}
	
	
	/**
	 * Method checkAdmissiblePlacement. Function that helps beta Hanto not to admit
	 * any type of moves
	 * @param from HantoCoordinate
	
	 * @throws HantoException */
	protected void checkAdmissiblePlacement(final HantoCoordinate from) throws HantoException {
		if(!movesMap.isAdmissiblePlacement(from)) {
			throw new HantoException("Invalid Move. "
					+ "Only placing is allowed");
		}
	}

	/** Method getStatus. Returns the status of the game 
	 * @return MoveResult
	 */
	protected MoveResult getStatus() {
		if(movesMap.checkStatus() != MoveResult.OK) {
			isGameOver = true;
		}
		return movesMap.checkStatus();
	}
	
	@Override
	public HantoPiece getPieceAt(final HantoCoordinate where) {
		final BaseHantoCoordinate baseCoordinate = new BaseHantoCoordinate(where);
		return movesMap.getPieceAt(baseCoordinate);
	}

	@Override
	public String getPrintableBoard() {
		System.out.println(movesMap.printMap());
		return movesMap.printMap();
	}

	/**
	 * Method update game turn. Updates the turn
	*/
	protected void updateGameTurn() {
		movesMap.switchPlayer();
		movesMap.incrementGameTurn();
	}
	
	/**
	 * Method isGameOver.
	
	 * @throws HantoException */
	protected void isGameOver() throws HantoException {
		if(isGameOver) {
			throw new HantoException("The Game is already over");
		}
	}

	/**
	 * Method existMoves.
	 * @return boolean
	 */
	protected boolean existMoves() {
		if(movesMap.checkStatus() != MoveResult.OK) {
			return false;
		}
		List<BaseHantoMove> moves = movesMap.getAllPossibleMoves(movesMap.currentPlayerColor);
		return moves.size() > 0;
	}
	
	/**
	 * Method getListOfAllPossibleMoves returns all the possible moves that can be done at a time
	 * @return List<BaseHantoMove>
	 */
	public List<BaseHantoMove> getListOfAllPossibleMoves() {
		return movesMap.getAllPossibleMoves(movesMap.currentPlayerColor);
	}
	
	/**
	 * Method withdrawal.
	
	 * @return MoveResult */
	protected MoveResult withdrawal() {
		switch(movesMap.currentPlayerColor) {
		case BLUE:
			return MoveResult.RED_WINS;
		case RED:
			return MoveResult.BLUE_WINS;
		}
		return null; // should never get here
	}
}
