/******************************************************************************* 
 * This files was developed for CS4233: Object-Oriented Analysis & Design. 
 * The course was taken at Worcester Polytechnic Institute. 
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Eclipse Public License v1.0 
 * which accompanies this distribution, and is available at * http://www.eclipse.org/legal/epl-v10.html 
 * *******************************************************************************/

package hanto.studentlpavel.alpha;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoGame;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;
/** AlphaHantoGame represents the implementation of HantoGame
 *  version Alpha
 */
public class AlphaHantoGame implements HantoGame {

	AlphaHantoMovesStore movesMap;
	
	/**
	 * Constructor for AlphaHantoGame.
	 */
	public AlphaHantoGame() {
		movesMap = new AlphaHantoMovesStore();
	}
	
	@Override
	public MoveResult makeMove(final HantoPieceType pieceType, final HantoCoordinate from,
			final HantoCoordinate to) throws HantoException {
		
		final AlphaHantoCoordinate alphaTo = new AlphaHantoCoordinate(to);
		final HantoPlayerColor color = movesMap.getNextColor();
		final AlphaHantoPiece alphaPiece = new AlphaHantoPiece(color, pieceType);
		
		if(movesMap.getNumberMoves() >= 2) {
			throw new HantoException("The Game is over");
		}
		
		if(!alphaPiece.isAdmissible()) {
			throw new HantoException("Invalid piece. Use only Butterfly in this version");
		}
	
		if(from != null) {
			throw new HantoException("Invalid Move. Only placing is allowed");
		}
		
		if(!movesMap.addMove(alphaTo, alphaPiece)) {
			throw new HantoException("Invalid Move");
		}
		return movesMap.checkStatus();
	}

	@Override
	public HantoPiece getPieceAt(final HantoCoordinate where) {
		final AlphaHantoCoordinate alphaCoordinate = new AlphaHantoCoordinate(where);
		
		return movesMap.getPieceAt(alphaCoordinate);
	}

	@Override
	public String getPrintableBoard() {
		System.out.println(movesMap.printMap());
		return movesMap.printMap();
	}
}
