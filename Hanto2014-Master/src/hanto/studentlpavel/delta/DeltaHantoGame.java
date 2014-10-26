/******************************************************************************* 
 * This files was developed for CS4233: Object-Oriented Analysis & Design. 
 * The course was taken at Worcester Polytechnic Institute. 
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Eclipse Public License v1.0 
 * which accompanies this distribution, and is available at * http://www.eclipse.org/legal/epl-v10.html 
 * *******************************************************************************/
package hanto.studentlpavel.delta;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoGameID;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;
import hanto.studentlpavel.BaseHantoCoordinate;
import hanto.studentlpavel.BaseHantoGame;
import hanto.studentlpavel.BaseHantoPiece;

/** DeltaHantoGame is the class that implements the Delta version of Hanto Game
 */
public class DeltaHantoGame extends BaseHantoGame {

	/**
	 * Constructor for DeltaHantoGame.
	 * @param firstPlayerColor HantoPlayerColor
	 */
	public DeltaHantoGame(final HantoPlayerColor firstPlayerColor) {
		super(firstPlayerColor, HantoGameID.DELTA_HANTO);
	}

	@Override
	public MoveResult makeMove(final HantoPieceType pieceType, final HantoCoordinate from,
			final HantoCoordinate to) throws HantoException {
		if(isResignation(pieceType, from, to)) {
			isGameOver = true;
			return withdrawal();
		}
		isGameOver();

		final BaseHantoCoordinate deltaTo = new BaseHantoCoordinate(to);
		final HantoPlayerColor color = movesMap.getNextColor();
		final BaseHantoPiece deltaPiece = new BaseHantoPiece(color, pieceType);

		if(from == null) {
			applyMove(null, deltaTo, deltaPiece);
		}
		else {
			final BaseHantoCoordinate gammaFrom = new BaseHantoCoordinate(from);
			applyMove(gammaFrom, deltaTo, deltaPiece);
		}

		return getStatus();
	}

	private static boolean isResignation(final HantoPieceType pieceType,
			final HantoCoordinate from, final HantoCoordinate to) {
		return (pieceType == null) && (from == null) && (to == null);
	}
}
