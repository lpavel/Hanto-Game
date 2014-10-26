/******************************************************************************* 
 * This files was developed for CS4233: Object-Oriented Analysis & Design. 
 * The course was taken at Worcester Polytechnic Institute. 
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Eclipse Public License v1.0 
 * which accompanies this distribution, and is available at * http://www.eclipse.org/legal/epl-v10.html 
 * *******************************************************************************/
package hanto.studentlpavel.beta;

import hanto.common.HantoGameID;
import hanto.common.HantoPlayerColor;
import hanto.studentlpavel.BaseHantoGame;
import hanto.studentlpavel.BaseHantoPiece;
import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoPieceType;
import hanto.common.MoveResult;
import hanto.studentlpavel.BaseHantoCoordinate;


/** This class takes care of the implementation of a Beta Hanto Game
 */
public class BetaHantoGame extends BaseHantoGame {

	private final int maxNumberOfMovesBeta = 12;
	
	/**
	 * Constructor for BetaHantoGame.
	 * @param firstPlayerColor HantoPlayerColor
	 */
	public BetaHantoGame(final HantoPlayerColor firstPlayerColor) {
		super(firstPlayerColor, HantoGameID.BETA_HANTO);
	}
	
	public MoveResult makeMove(final HantoPieceType pieceType, final HantoCoordinate from,
			final HantoCoordinate to) throws HantoException {
		
		checkGameAlreadyOver(maxNumberOfMovesBeta);
		
		final BaseHantoCoordinate betaTo = new BaseHantoCoordinate(to);
		final HantoPlayerColor color = movesMap.getNextColor();
		final BaseHantoPiece betaPiece = new BaseHantoPiece(color, pieceType);
		
		checkAdmissiblePlacement(from);
		applyMove(null, betaTo, betaPiece);

		return getStatus();
	}
}
