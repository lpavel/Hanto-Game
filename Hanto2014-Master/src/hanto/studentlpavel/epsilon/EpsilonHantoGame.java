/******************************************************************************* 
 * This files was developed for CS4233: Object-Oriented Analysis & Design. 
 * The course was taken at Worcester Polytechnic Institute. 
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Eclipse Public License v1.0 
 * which accompanies this distribution, and is available at * http://www.eclipse.org/legal/epl-v10.html 
 * *******************************************************************************/
package hanto.studentlpavel.epsilon;

import common.HantoTestGame.PieceLocationPair;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoGameID;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.HantoPrematureResignationException;
import hanto.common.MoveResult;
import hanto.studentlpavel.BaseHantoCoordinate;
import hanto.studentlpavel.BaseHantoGame;
import hanto.studentlpavel.BaseHantoPiece;

/** EpsilonHantoGame is the class that implements the Epsilon version of Hanto Game
 */
public class EpsilonHantoGame extends BaseHantoGame {

	/**
	 * Constructor for EpsilonHantoGame.
	 * @param firstPlayerColor HantoPlayerColor
	 */
	public EpsilonHantoGame(final HantoPlayerColor firstPlayerColor) {
		super(firstPlayerColor, HantoGameID.EPSILON_HANTO);
	}

	/**
	 * Constructor for EpsilonHantoGame.
	
	 * @param initialPieces PieceLocationPair[] 
	 * @param currentPlayer HantoPlayerColor
	 */
	public EpsilonHantoGame(HantoPlayerColor currentPlayer,
			PieceLocationPair[] initialPieces) {
		super(currentPlayer, HantoGameID.EPSILON_HANTO);
		movesMap.clear();
		
		for(PieceLocationPair pieceLocationPair : initialPieces) {
			final BaseHantoCoordinate to = new BaseHantoCoordinate(pieceLocationPair.location);
			final HantoPlayerColor color = pieceLocationPair.player;
			final BaseHantoPiece piece = new BaseHantoPiece(color, pieceLocationPair.pieceType);
			movesMap.placePieceDuringMove(to, piece);
		}
		updateGameTurn();
	}

	@Override
	public MoveResult makeMove(final HantoPieceType pieceType, final HantoCoordinate from,
			final HantoCoordinate to) throws HantoException {
		if(isResignation(pieceType, from, to)) {
			if(existMoves()) {
				throw new HantoPrematureResignationException();
			}
			isGameOver = true;
			return withdrawal();
		}
		isGameOver();

		final BaseHantoCoordinate epsilonTo = new BaseHantoCoordinate(to);
		final HantoPlayerColor color = movesMap.getNextColor();
		final BaseHantoPiece epsilonPiece = new BaseHantoPiece(color, pieceType);

		if(from == null) {
			applyMove(null, epsilonTo, epsilonPiece);
		}
		else {
			final BaseHantoCoordinate gammaFrom = new BaseHantoCoordinate(from);
			applyMove(gammaFrom, epsilonTo, epsilonPiece);
		}

		return getStatus();
	}

	private static boolean isResignation(final HantoPieceType pieceType,
			final HantoCoordinate from, final HantoCoordinate to) {
		return (pieceType == null) && (from == null) && (to == null);
	}
}
