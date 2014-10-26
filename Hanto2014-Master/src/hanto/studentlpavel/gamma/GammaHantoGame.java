/******************************************************************************* 
 * This files was developed for CS4233: Object-Oriented Analysis & Design. 
 * The course was taken at Worcester Polytechnic Institute. 
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Eclipse Public License v1.0 
 * which accompanies this distribution, and is available at * http://www.eclipse.org/legal/epl-v10.html 
 * *******************************************************************************/
package hanto.studentlpavel.gamma;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoGameID;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;
import hanto.studentlpavel.BaseHantoCoordinate;
import hanto.studentlpavel.BaseHantoGame;
import hanto.studentlpavel.BaseHantoPiece;

/** GammaHantoGame is the class that implements the Gamma Version of Hanto
 */
public class GammaHantoGame extends BaseHantoGame {

	protected final int maxNumberOfMovesGamma = 40;
	
	/**
	 * Constructor for GammaHantoGame.
	 * @param firstPlayerColor HantoPlayerColor
	 */
	public GammaHantoGame(final HantoPlayerColor firstPlayerColor) {
		super(firstPlayerColor, HantoGameID.GAMMA_HANTO);
	}

	@Override
	public MoveResult makeMove(final HantoPieceType pieceType, 
			final HantoCoordinate from, final HantoCoordinate to) 
					throws HantoException {
		
		
		checkGameAlreadyOver(maxNumberOfMovesGamma);

		
		final BaseHantoCoordinate gammaTo = new BaseHantoCoordinate(to);
		final HantoPlayerColor color = movesMap.getNextColor();
		final BaseHantoPiece gammaPiece = new BaseHantoPiece(color, pieceType);

		if(from == null) {
			applyMove(null, gammaTo, gammaPiece);
		}
		else {
			final BaseHantoCoordinate gammaFrom = new BaseHantoCoordinate(from);
			applyMove(gammaFrom, gammaTo, gammaPiece);
		}

		return getStatus();
	}
}
