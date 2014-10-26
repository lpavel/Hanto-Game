/******************************************************************************* 
 * This files was developed for CS4233: Object-Oriented Analysis & Design. 
 * The course was taken at Worcester Polytechnic Institute. 
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Eclipse Public License v1.0 
 * which accompanies this distribution, and is available at * http://www.eclipse.org/legal/epl-v10.html 
 * *******************************************************************************/
package hanto.studentlpavel.epsilon;


import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.studentlpavel.BaseHantoMovesStore;
import hanto.studentlpavel.BaseHantoPiece;

/** EpsilonMoveRules contains the specifics of the Epsilon Version of Hanto
 */
public class EpsilonMoveRules extends BaseHantoMovesStore {

	private static int maxNumberOfMovesEpsilon = 0x7fffffff;
	private static int flyLimit = 5;
	private static int numberOfButterflies = 1;
	private static int numberOfCrabs       = 6;
	private static int numberOfSparrows    = 2;
	private static int numberOfHorses      = 4;
	
	/**
	 * Constructor for EpsilonMoveRules.
	 * @param firstPlayerColor HantoPlayerColor
	 */
	public EpsilonMoveRules(final HantoPlayerColor firstPlayerColor) {
		super(firstPlayerColor, maxNumberOfMovesEpsilon, flyLimit, numberOfButterflies,
				numberOfCrabs, numberOfSparrows, numberOfHorses);
	}

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
			return (getNumberOfPieces(new BaseHantoPiece(HantoPlayerColor.BLUE, HantoPieceType.SPARROW)) < 2);
		}
		else if(piece.getColor() == HantoPlayerColor.BLUE &&
				piece.getType() == HantoPieceType.HORSE) {
			return (getNumberOfPieces(new BaseHantoPiece(HantoPlayerColor.BLUE, HantoPieceType.HORSE)) < 4);
		}
		else if(piece.getColor() == HantoPlayerColor.BLUE &&
				piece.getType() == HantoPieceType.CRAB) {
			return (getNumberOfPieces(new BaseHantoPiece(HantoPlayerColor.BLUE, HantoPieceType.CRAB)) < 6);
		}
		else if(piece.getColor() == HantoPlayerColor.RED &&
				piece.getType() == HantoPieceType.BUTTERFLY) {
			return (getNumberOfPieces(new BaseHantoPiece(HantoPlayerColor.RED, HantoPieceType.BUTTERFLY)) < 1);
		}
		else if(piece.getColor() == HantoPlayerColor.RED &&
				piece.getType() == HantoPieceType.SPARROW) {
			return (getNumberOfPieces(new BaseHantoPiece(HantoPlayerColor.RED, HantoPieceType.SPARROW)) < 2);
		}
		else if(piece.getColor() == HantoPlayerColor.RED &&
				piece.getType() == HantoPieceType.HORSE) {
			return (getNumberOfPieces(new BaseHantoPiece(HantoPlayerColor.RED, HantoPieceType.HORSE)) < 4);
		}
		else {
			return (getNumberOfPieces(new BaseHantoPiece(HantoPlayerColor.RED, HantoPieceType.CRAB)) < 6);
		}
	}

	
	@Override
	public boolean isAdmissiblePiece(final HantoPieceType pieceType) {
		return (pieceType == HantoPieceType.BUTTERFLY) ||
				(pieceType == HantoPieceType.SPARROW) ||
				(pieceType == HantoPieceType.CRAB) ||
				(pieceType == HantoPieceType.HORSE);
	}
	
	@Override
	protected boolean canFly(final HantoPieceType pieceType) {
		return pieceType == HantoPieceType.SPARROW;
	}

	@Override
	protected boolean canJump(final HantoPieceType pieceType) {
		return pieceType == HantoPieceType.HORSE;
	}

	@Override
	protected boolean canWalk(final HantoPieceType pieceType) {
		return (pieceType == HantoPieceType.BUTTERFLY) ||
				(pieceType == HantoPieceType.CRAB);
	}
}
