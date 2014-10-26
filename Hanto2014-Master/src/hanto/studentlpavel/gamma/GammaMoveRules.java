/******************************************************************************* 
 * This files was developed for CS4233: Object-Oriented Analysis & Design. 
 * The course was taken at Worcester Polytechnic Institute. 
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Eclipse Public License v1.0 
 * which accompanies this distribution, and is available at * http://www.eclipse.org/legal/epl-v10.html 
 * *******************************************************************************/
package hanto.studentlpavel.gamma;

import java.util.List;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.studentlpavel.BaseHantoCoordinate;
import hanto.studentlpavel.BaseHantoMovesStore;
import hanto.studentlpavel.BaseHantoPiece;

/** GammaMoveRules is a class that contains the specifics of the Gamma game
 */
public class GammaMoveRules extends BaseHantoMovesStore {

	private static int maxNumberOfMovesGamma = 40;
	private static int flyLimit = 0x7fffffff;
	private static int numberOfButterflies = 1;
	private static int numberOfCrabs       = 0;
	private static int numberOfSparrows    = 5;
	private static int numberOfHorses      = 0;

	/**
	 * Constructor for GammaMoveRules.
	 * @param firstPlayerColor HantoPlayerColor
	 */
	public GammaMoveRules(final HantoPlayerColor firstPlayerColor) {
		super(firstPlayerColor, maxNumberOfMovesGamma, flyLimit, numberOfButterflies,
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
		int numberOppositePiecesAround = 0;
		int numberFriendPiecesAround = 0;
		
		final List<BaseHantoCoordinate> neighbors = 
				getNeighbors(coordinate);

		for(BaseHantoCoordinate neighbor : neighbors) {
			if(getPieceAt(neighbor).getColor() == piece.getColor()) {
				++numberFriendPiecesAround;
			}
			else {
				++numberOppositePiecesAround;
			}
		}
		
		if(movesMap.containsKey(coordinate)) {
			return false;
		}
		else {
			if((getNumberMoves() == 1) && (neighbors.size() != 0)) {
				return true;
			}

			return (numberOppositePiecesAround == 0) && (numberFriendPiecesAround != 0);
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

	@Override
	public boolean isAdmissiblePiece(final HantoPieceType pieceType) {
		return (pieceType == HantoPieceType.BUTTERFLY) ||
				(pieceType == HantoPieceType.SPARROW);
	}

	@Override
	protected boolean canWalk(final HantoPieceType pieceType) {
		return (pieceType  == HantoPieceType.BUTTERFLY) ||
				(pieceType == HantoPieceType.SPARROW);
	}
}
