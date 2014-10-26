/******************************************************************************* 
 * This files was developed for CS4233: Object-Oriented Analysis & Design. 
 * The course was taken at Worcester Polytechnic Institute. 
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Eclipse Public License v1.0 
 * which accompanies this distribution, and is available at * http://www.eclipse.org/legal/epl-v10.html 
 * *******************************************************************************/
package hanto.studentlpavel.delta;

import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.studentlpavel.BaseHantoMovesStore;
import hanto.studentlpavel.BaseHantoPiece;

/** DeltaMoveRuls contains the specifics of the Delta Version of Hanto
 */
public class DeltaMoveRules extends BaseHantoMovesStore {

	private static int maxNumberOfMovesDelta = 0x7fffffff;
	private static int flyLimit = 0x7fffffff;
	private static int numberOfButterflies = 1;
	private static int numberOfCrabs       = 4;
	private static int numberOfSparrows    = 4;
	private static int numberOfHorses      = 0;

	/**
	 * Constructor for DeltaMoveRules.
	 * @param firstPlayerColor HantoPlayerColor
	 */
	public DeltaMoveRules(final HantoPlayerColor firstPlayerColor) {
		super(firstPlayerColor, maxNumberOfMovesDelta, flyLimit, numberOfButterflies,
				numberOfCrabs, numberOfSparrows, numberOfHorses);
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
			return (getNumberOfPieces(new BaseHantoPiece(HantoPlayerColor.BLUE, HantoPieceType.SPARROW)) < 4);
		}
		else if(piece.getColor() == HantoPlayerColor.RED &&
				piece.getType() == HantoPieceType.BUTTERFLY) {
			return (getNumberOfPieces(new BaseHantoPiece(HantoPlayerColor.RED, HantoPieceType.BUTTERFLY)) < 1);
		}
		else if(piece.getColor() == HantoPlayerColor.RED &&
				piece.getType() == HantoPieceType.SPARROW) {
			return (getNumberOfPieces(new BaseHantoPiece(HantoPlayerColor.RED, HantoPieceType.SPARROW)) < 4);
		}
		else if(piece.getColor() == HantoPlayerColor.BLUE &&
				piece.getType() == HantoPieceType.CRAB) {
			return (getNumberOfPieces(new BaseHantoPiece(HantoPlayerColor.BLUE, HantoPieceType.CRAB)) < 4);
		}
		else {
			return (getNumberOfPieces(new BaseHantoPiece(HantoPlayerColor.RED, HantoPieceType.CRAB)) < 4);
		}
	}

	@Override
	public boolean isAdmissiblePiece(final HantoPieceType pieceType) {
		return (pieceType == HantoPieceType.BUTTERFLY) ||
				(pieceType == HantoPieceType.SPARROW) ||
				(pieceType == HantoPieceType.CRAB);
	}

	@Override
	protected boolean canFly(final HantoPieceType pieceType) {
		return pieceType == HantoPieceType.SPARROW;
	}

	@Override
	protected boolean canWalk(final HantoPieceType pieceType) {
		return (pieceType == HantoPieceType.BUTTERFLY) ||
				(pieceType == HantoPieceType.CRAB);
	}
}
