/******************************************************************************* 
 * This files was developed for CS4233: Object-Oriented Analysis & Design. 
 * The course was taken at Worcester Polytechnic Institute. 
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Eclipse Public License v1.0 
 * which accompanies this distribution, and is available at * http://www.eclipse.org/legal/epl-v10.html 
 * *******************************************************************************/
package hanto.studentlpavel.alpha;

import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;

import java.util.HashMap;
import java.util.Map;

/** This class keeps track of the moves already made
 */
public class AlphaHantoMovesStore {

	Map<AlphaHantoCoordinate, AlphaHantoPiece> movesMap;

	/**
	 * Constructor for AlphaHantoMovesStore.
	 */
	public AlphaHantoMovesStore(){
		movesMap  = new HashMap<AlphaHantoCoordinate, AlphaHantoPiece>();
	}

	/**
	 * Method getPieceAt gets the piece at a specific location
	 * @param coordinate AlphaHantoCoordinate
	 * @return AlphaHantoPiece
	 */
	public final AlphaHantoPiece getPieceAt(final AlphaHantoCoordinate coordinate) {
		return movesMap.get(coordinate);
	}

	/**
	 * Method addMove -adds a move and throws exceptions if the
	 * move is invalid
	 * @param coordinate AlphaHantoCoordinate
	 * @param piece AlphaHantoPiece
	 * @return boolean
	 */
	public boolean addMove(final AlphaHantoCoordinate coordinate, final AlphaHantoPiece piece) {
		if(isValidMove(coordinate)) {
			movesMap.put(coordinate, piece);
			return true;
		}
		else  {
			return false;
		}
	}

	/* gets the next color based on how many moves were made
	 * @return the color of the next move
	 */
	public HantoPlayerColor getNextColor() {
		return (movesMap.size() % 2 == 0)
		? HantoPlayerColor.BLUE
		: HantoPlayerColor.RED;
	}

	/* checks if it is the first move
	 * @return true if it is the first move
	 */
	public boolean isFirstMove() {
		return movesMap.size() == 0;
	}

	/* checks if the move is valid
	 * @return true if it is valid
	 */
	private boolean isValidMove(final AlphaHantoCoordinate coordinate) {
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
	 * Method checkStatus.
	 * @return the outcome of the game
	 */
	public MoveResult checkStatus() {
		return (movesMap.size() == 1)
				? MoveResult.OK
				: MoveResult.DRAW;
	}

	/**
	 * Method printMap - prints the map
	 * @return string that represents the map
	 */
	public String printMap() {
		String mapStr = new String();
		for(AlphaHantoCoordinate coord: movesMap.keySet()) {
			mapStr += "CoordX:" + coord.getX() + " CoordY:" + coord.getY() + " - ";
			AlphaHantoPiece piece = movesMap.get(coord);
			mapStr += "Color:" + piece.getColor().toString() + 
					" Type:" + piece.getType().toString() + "\n";
		}
		return mapStr;
	}

	/*
	 * getNumberMoves looks for the number of moves already made
	 * @return number of moves already made
	 */
	public int getNumberMoves() {
		return movesMap.size();
	}
}
