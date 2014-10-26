/******************************************************************************* 
 * This files was developed for CS4233: Object-Oriented Analysis & Design. 
 * The course was taken at Worcester Polytechnic Institute. 
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Eclipse Public License v1.0 
 * which accompanies this distribution, and is available at * http://www.eclipse.org/legal/epl-v10.html 
 * *******************************************************************************/
package hanto.studentlpavel.beta;

import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.studentlpavel.BaseHantoCoordinate;
import hanto.studentlpavel.BaseHantoMovesStore;
import hanto.studentlpavel.BaseHantoPiece;

/** BetaMoveRules is a class that contains the specifics of the Beta game
 */
public class BetaMoveRules extends BaseHantoMovesStore {

	private static int maxNumberOfMovesBeta = 12;
	private static int flyLimit = 0x7fffffff;
	private static int numberOfButterflies = 1;
	private static int numberOfCrabs       = 0;
	private static int numberOfSparrows    = 5;
	private static int numberOfHorses      = 0;
	/**
	 * Constructor for BetaMoveRules.
	 * @param firstPlayerColor HantoPlayerColor
	 */
	public BetaMoveRules(final HantoPlayerColor firstPlayerColor) {
		super(firstPlayerColor, maxNumberOfMovesBeta, flyLimit, numberOfButterflies,
				numberOfCrabs, numberOfSparrows, numberOfHorses);
	}
	
	/* checks if the move is valid
	 * @return true if it is valid
	 */
	protected boolean isValidPlacement(final BaseHantoCoordinate coordinate, 
			final BaseHantoPiece piece) {
		if(!checkNumberOfPieces(piece)) {
			return false;
		}
		if(isFirstMove() && coordinate.isOrigin()) {
			return true;
		}
		if(movesMap.containsKey(coordinate)) {
			return false;
		}
		else {
			boolean found = false; 
			if(movesMap.containsKey(coordinate.getNW())) {
				found  = true;
			}
			else if(movesMap.containsKey(coordinate.getNE())) {
				found  = true;
			}
			else if(movesMap.containsKey(coordinate.getN())) {
				found  = true;
			}
			else if(movesMap.containsKey(coordinate.getS())) {
				found  = true;
			}
			else if(movesMap.containsKey(coordinate.getSE())) {
				found  = true;
			}
			else if(movesMap.containsKey(coordinate.getSW())) {
				found  = true;
			}
			return found;
		}
	}

	/**
	 * Method checkNumberOfPieces - Check Number of Pieces and if they
	 * don't contradict
	 * @param piece BetaHantoPiece
	 * @return boolean
	 */
	@Override
	protected boolean checkNumberOfPieces(final BaseHantoPiece piece) {
		if(getNumberMoves() >= 6){
			if(piece.getType() != HantoPieceType.BUTTERFLY){
				if(piece.getColor() == HantoPlayerColor.BLUE && 
					getNumberOfPieces(new BaseHantoPiece(HantoPlayerColor.BLUE, HantoPieceType.BUTTERFLY)) == 0) {
					return false;
				}
				else if(piece.getColor() == HantoPlayerColor.RED && 
						getNumberOfPieces(new BaseHantoPiece(HantoPlayerColor.RED, HantoPieceType.BUTTERFLY)) == 0) {
					return false;
				}
			}
		}
		
		if(piece.getColor() == HantoPlayerColor.BLUE &&
				piece.getType() == HantoPieceType.BUTTERFLY) {
			return (getNumberOfPieces(new BaseHantoPiece(HantoPlayerColor.BLUE, HantoPieceType.BUTTERFLY)) < 1);
		}
		else if(piece.getColor() == HantoPlayerColor.BLUE &&
				piece.getType() == HantoPieceType.SPARROW) {
			return (getNumberOfPieces(new BaseHantoPiece(HantoPlayerColor.BLUE, HantoPieceType.SPARROW)) < 5);
		}
		else if(piece.getColor() == HantoPlayerColor.RED &&
				piece.getType() == HantoPieceType.BUTTERFLY) {
			return (getNumberOfPieces(new BaseHantoPiece(HantoPlayerColor.RED, HantoPieceType.BUTTERFLY)) < 1);
		}
		else {
			return (getNumberOfPieces(new BaseHantoPiece(HantoPlayerColor.RED, HantoPieceType.SPARROW)) < 5);
		}
	}

	public boolean isAdmissiblePiece(final HantoPieceType pieceType) {
		return (pieceType == HantoPieceType.BUTTERFLY) ||
				(pieceType == HantoPieceType.SPARROW);
	}
}
