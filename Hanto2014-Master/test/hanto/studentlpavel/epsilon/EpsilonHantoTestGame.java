package hanto.studentlpavel.epsilon;

import common.HantoTestGame;
import hanto.common.HantoGameID;
import hanto.common.HantoPlayerColor;
import hanto.studentlpavel.BaseHantoCoordinate;
import hanto.studentlpavel.BaseHantoPiece;

public class EpsilonHantoTestGame extends EpsilonHantoGame implements HantoTestGame {

	public EpsilonHantoTestGame(HantoPlayerColor firstPlayerColor) {
		super(firstPlayerColor);
	}

	@Override
	public void initializeBoard(PieceLocationPair[] initialPieces) {
		movesMap.clear();
		
		for(PieceLocationPair pieceLocationPair : initialPieces) {
			final BaseHantoCoordinate to = new BaseHantoCoordinate(pieceLocationPair.location);
			final HantoPlayerColor color = pieceLocationPair.player;
			final BaseHantoPiece piece = new BaseHantoPiece(color, pieceLocationPair.pieceType);
			movesMap.placePieceDuringMove(to, piece);
		}
	}

	@Override
	public void setTurnNumber(int turnNumber) {
		int numberOfMoves = (movesMap.getCurrentPlayerColor() == movesMap.getFirstPlayerColor())
				? ((turnNumber-1) * 2)
				: ((turnNumber-1) *2 + 1);
		movesMap.setGameTurn(numberOfMoves);
	}

	@Override
	public void setPlayerMoving(HantoPlayerColor player) {
		movesMap.setCurrentPlayer(player);
	}
}
