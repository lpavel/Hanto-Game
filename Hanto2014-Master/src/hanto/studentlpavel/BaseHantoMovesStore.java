/******************************************************************************* 
 * This files was developed for CS4233: Object-Oriented Analysis & Design. 
 * The course was taken at Worcester Polytechnic Institute. 
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Eclipse Public License v1.0 
 * which accompanies this distribution, and is available at * http://www.eclipse.org/legal/epl-v10.html 
 * *******************************************************************************/
package hanto.studentlpavel;

import hanto.common.HantoCoordinate;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;
import static hanto.common.HantoPieceType.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.Queue;


/** This class keeps track of what happened on base Hanto
 */
public abstract class BaseHantoMovesStore {

	protected Map<BaseHantoCoordinate, BaseHantoPiece> movesMap;
	protected HantoPlayerColor firstPlayerColor;
	protected HantoPlayerColor currentPlayerColor;
	private int maxNumberOfMoves;
	private int flyLimit;

	protected int gameTurn;
	private int maxNumberOfButterflies;
	private int maxNumberOfCrabs;
	private int maxNumberOfSparrows;
	private int maxNumberOfHorses;
	
	public HantoPlayerColor getFirstPlayerColor() {
		return firstPlayerColor;
	}

	public HantoPlayerColor getCurrentPlayerColor() {
		return currentPlayerColor;
	}

	/**
	 * Constructor for BaseHantoMovesStore.
	 * @param firstPlayerColor HantoPlayerColor
	 * @param maxNumberOfMoves int
	 * @param flyLimit int
	 * @param numberOfButterflies int
	 * @param numberOfCrabs int
	 * @param numberOfSparrows int
	 * @param numberOfHorses int
	 */
	protected BaseHantoMovesStore(final HantoPlayerColor firstPlayerColor,
			final int maxNumberOfMoves, final int flyLimit,
			int numberOfButterflies, int numberOfCrabs, int numberOfSparrows,
			int numberOfHorses){
		this.firstPlayerColor = firstPlayerColor;
		currentPlayerColor = firstPlayerColor;
		movesMap  = new HashMap<BaseHantoCoordinate, BaseHantoPiece>();
		this.maxNumberOfMoves = maxNumberOfMoves;
		this.flyLimit = flyLimit;
		maxNumberOfButterflies = numberOfButterflies;
		maxNumberOfCrabs       = numberOfCrabs;
		maxNumberOfSparrows    = numberOfSparrows;
		maxNumberOfHorses      = numberOfHorses;
		gameTurn = 0;
	}
	
	public void setGameTurn(final int gameTurn) {
		this.gameTurn = gameTurn;
	}
	
	public void setCurrentPlayer(final HantoPlayerColor currentPlayer) {
		currentPlayerColor = currentPlayer;
	}
	
	/**
	 * Method deleteMove. deletes a move 
	 * @param coord BaseHantoCoordinate
	 */
	public void deleteMove(final BaseHantoCoordinate coord) {
		movesMap.remove(coord);
	}
		
	/**
	 * Method getPieceAt gets the piece at a specific location
	 * @param coordinate baseHantoCoordinate
	
	 * @return BaseHantoPiece */
	public final BaseHantoPiece getPieceAt(final BaseHantoCoordinate coordinate) {
		return movesMap.get(coordinate);
	}
		
	/* gets the next color based on how many moves were made
	 * @return the color of the next move
	 */	
	public HantoPlayerColor getNextColor() {
		return currentPlayerColor;
	}
					
	/**
	 * Method switchPlayer. Updates the color of the player after a move
	 */
	public void switchPlayer() {
		currentPlayerColor = (currentPlayerColor == HantoPlayerColor.BLUE)
				? HantoPlayerColor.RED
				: HantoPlayerColor.BLUE;
	}
	
	/**
	 * Method incrementGameTurn. Updates the turn after a move
	 */
	public void incrementGameTurn() {
		++gameTurn;
	}
	/* checks if it is the first move
	 * @return true if it is the first move
	 */
	public boolean isFirstMove() {
		return gameTurn == 0;
	}
	

	/**
	 * Method checkStatus. Checks the status of the came
	
	 * @return MoveResult */
	public MoveResult checkStatus() {
		if(isDraw()) {
			return MoveResult.DRAW;
		}
		else if(isRedVictory()) {
			return MoveResult.RED_WINS;
		}
		else if(isBlueVictory()) {
			return MoveResult.BLUE_WINS;
		}
		else {
			return MoveResult.OK;
		}
	}

	/**
	 * Method canFly. checks if a piece can fly
	 * @param pieceType HantoPieceType
	
	 * @return boolean */
	protected boolean canFly(final HantoPieceType pieceType) {
		return false;
	}

	/**
	 * Method canWalk. checks if a piece can walk
	 * @param pieceType HantoPieceType
	
	 * @return boolean */
	protected boolean canWalk(final HantoPieceType pieceType) {
		return false;
	}
	
	/**
	 * Method canJump. checks if a piece can jump
	 * @param pieceType HantoPieceType
	
	 * @return boolean */
	protected boolean canJump(final HantoPieceType pieceType){
		return false;
	}
	/** Method isDraw checks if there is a draw
	 * 
	 * @return boolean
	 */
	protected boolean isDraw() {
		if(gameTurn == maxNumberOfMoves && !isBlueVictory() && !isRedVictory()) {
			return true;
		}

		return isRedVictory() && isBlueVictory();
	}
	
	/** Method isRedVictory checks if red won the game
	 * 
	 * @return boolean
	 */
	protected boolean isRedVictory() {
		BaseHantoCoordinate butterflyCoord = getBlueButterflyCoordinate();
		if(butterflyCoord != null) {
			return piecesAround(butterflyCoord, HantoPlayerColor.RED) == 6;
		}
		return false;
	}

	/** Method isRedVictory checks if blue won the game
	 * 
	 * @return boolean
	 */
	protected boolean isBlueVictory() {
		BaseHantoCoordinate butterflyCoord = getRedButterflyCoordinate();
		if(butterflyCoord != null) {
			return piecesAround(butterflyCoord, HantoPlayerColor.BLUE) == 6;
		}
		return false;
	}
	

	/** Method getRedButterflyCoordinate 
	 * returns the position of the red butterfly
	 * 
	 * @return BaseHantoCoordinate
	 */
	protected BaseHantoCoordinate getRedButterflyCoordinate() {
		for(BaseHantoCoordinate coord: movesMap.keySet()) {
			BaseHantoPiece piece = movesMap.get(coord);
			if(piece.getColor() == HantoPlayerColor.RED && 
					piece.getType() == HantoPieceType.BUTTERFLY) {
				return coord;
			}
		}
		
		return null;
	}

	/** Method getRedButterflyCoordinate 
	 * returns the position of the blue butterfly
	 * 
	 * @return BaseHantoCoordinate
	 */
	protected BaseHantoCoordinate getBlueButterflyCoordinate() {
		for(BaseHantoCoordinate coord: movesMap.keySet()) {
			BaseHantoPiece piece = movesMap.get(coord);
			if(piece.getColor() == HantoPlayerColor.BLUE && 
					piece.getType() == HantoPieceType.BUTTERFLY) {
				return coord;
			}
		}
		
		return null;
	}

	/**
	 * Method piecesAround. gets the number of the pieces around it
	 * @param coord BaseHantoCoordinate
	 * @param color HantoPlayerColor
	
	 * @return int */
	protected int piecesAround(final BaseHantoCoordinate coord, 
			final HantoPlayerColor color) {
		int counter = 0;
		if(movesMap.containsKey(coord.getNW())) {
			BaseHantoPiece piece = movesMap.get(coord.getNW());
			if(piece != null) {
				counter++;
			}
		}
		if(movesMap.containsKey(coord.getNE())) {
			BaseHantoPiece piece = movesMap.get(coord.getNE());
			if(piece != null) {
				counter++;
			}
		}
		if(movesMap.containsKey(coord.getN())) {
			BaseHantoPiece piece = movesMap.get(coord.getN());
			if(piece != null) {
				counter++;
			}
		}
		if(movesMap.containsKey(coord.getS())) {
			BaseHantoPiece piece = movesMap.get(coord.getS());
			if(piece != null) {
				counter++;
			}
		}
		if(movesMap.containsKey(coord.getSE())) {
			BaseHantoPiece piece = movesMap.get(coord.getSE());
			if(piece != null) {
				counter++;
			}
		}
		if(movesMap.containsKey(coord.getSW())) {
			BaseHantoPiece piece = movesMap.get(coord.getSW());
			if(piece != null) {
				counter++;
			}
		}
		return counter;
	}
	
	/**
	 * Method printMap. string representation of a map
	
	 * @return String */
	public String printMap() {
		String mapStr = new String();
		for(BaseHantoCoordinate coord: movesMap.keySet()) {
			mapStr += "CoordX:" + coord.getX() + " CoordY:" + coord.getY() + " - ";
			BaseHantoPiece piece = movesMap.get(coord);
			mapStr += "Color:" + piece.getColor().toString() + 
					" Type:" + piece.getType().toString() + "\n";
		}
		return mapStr;
	}
	
	public int getNumberMoves() {
		return gameTurn;
	}
	

	/** Method getNumberOfBlueHorses
	
	
	 * @param piece BaseHantoPiece
	 * @return int - the number of pieces */
	protected int getNumberOfPieces(final BaseHantoPiece piece) {
		int counter = 0;
		for(BaseHantoCoordinate coord: movesMap.keySet()) {
			BaseHantoPiece newPiece = movesMap.get(coord);
			if(newPiece.getColor() == piece.getColor() && 
					newPiece.getType() == piece.getType()) {
				++counter;
			}
		}
		return counter;		
	}
		
	/**
	 * Method isAdmissiblePiece. checks if the piece is admissible
	 * @param pieceType HantoPieceType
	
	 * @return boolean */
	public abstract boolean isAdmissiblePiece(HantoPieceType pieceType);
	/**
	 * Method isValidPlacement. check if a piece can be placed on
	 * that location
	
	 * @param piece BaseHantoPiece
	
	 * @return boolean */

	/**
	 * Method checkNumberOfPieces - Check Number of Pieces and if they
	 * don't contradict
	 * @param piece BetaHantoPiece
	 * @return boolean
	 */
	protected abstract boolean checkNumberOfPieces(final BaseHantoPiece piece);
	
	/* checks if the move is valid
	 * @return true if it is valid
	 */
	/**
	 * Method isValidPlacement.
	 * @param coordinate BaseHantoCoordinate
	 * @param piece BaseHantoPiece
	 * @return boolean
	 */
	protected boolean isValidPlacement(final BaseHantoCoordinate coordinate, 
			final BaseHantoPiece piece) {
		if(!checkNumberOfPieces(piece)) {
			return false;
		}
		if(isFirstMove() && coordinate.isOrigin()) {
			return true;
		}
		
		return hasCorrectPlacementNeighbors(coordinate, piece.getColor());
	}
	
	private boolean hasCorrectPlacementNeighbors(final BaseHantoCoordinate coordinate,
			final HantoPlayerColor playerColor) {
		int numberOppositePiecesAround = 
				getNumberOppositePiecesAround(coordinate, playerColor);
		int numberFriendPiecesAround   = 
				getNumberFriendPiecesAround(coordinate, playerColor);
		
		if(movesMap.containsKey(coordinate)) {
			return false;
		}
		else {
			if((getNumberMoves() == 1) && (getNeighbors(coordinate).size() != 0)) {
				return true;
			}
			return (numberOppositePiecesAround == 0) && (numberFriendPiecesAround != 0);
		}
		
	}

	private int getNumberFriendPiecesAround(final BaseHantoCoordinate coord, 
			final HantoPlayerColor color) {
		int numberFriendPiecesAround = 0;
		final List<BaseHantoCoordinate> neighbors = 
				getNeighbors(coord);

		for(BaseHantoCoordinate neighbor : neighbors) {
			if(getPieceAt(neighbor).getColor() == color) {
				++numberFriendPiecesAround;
			}
		}		
		return numberFriendPiecesAround;
	}
	
	private int getNumberOppositePiecesAround(final BaseHantoCoordinate coord, 
			final HantoPlayerColor color) {
		int numberFriendPiecesAround = 0;
		final List<BaseHantoCoordinate> neighbors = 
				getNeighbors(coord);

		for(BaseHantoCoordinate neighbor : neighbors) {
			if(getPieceAt(neighbor).getColor() != color) {
				++numberFriendPiecesAround;
			}
		}		
		return numberFriendPiecesAround;
	}
	
	/**
	 * Method walkPiece. implementation of the walk
	 * @param from BaseHantoCoordinate
	 * @param to BaseHantoCoordinate
	 * @param basePiece BaseHantoPiece
	
	 * @return boolean */
	public boolean walkPiece(BaseHantoCoordinate from, BaseHantoCoordinate to,
			BaseHantoPiece basePiece) {
		if(isValidWalk(from, to, basePiece)) {
			BaseHantoPiece pieceFrom = getPieceAt(from);
			deleteMove(from);
			placePieceDuringMove(to, pieceFrom);
			return true;
		}
		return false;
	}

	private boolean isValidWalk(final BaseHantoCoordinate from, final BaseHantoCoordinate to,
			final BaseHantoPiece basePiece) {
		BaseHantoPiece pieceFrom = getPieceAt(from);
		BaseHantoPiece pieceTo   = getPieceAt(to);

		
		if(pieceFrom == null || pieceTo != null) {
			return false;
		}

		if((pieceFrom.getType() != basePiece.getType()) ||
				pieceFrom.getColor() != currentPlayerColor) {
			return false;
		}
		
		if(!areNeighbors(from, to)) {
			return false;
		}
		if(isSqueezed(from, to)) {
			return false;
		}

		deleteMove(from);
		placePieceDuringMove(to, pieceFrom);
		if(!isContiguous(to)) {
			deleteMove(to);
			placePieceDuringMove(from, pieceFrom);
			return false;
		}
		deleteMove(to);
		placePieceDuringMove(from, pieceFrom);		
		return true;
		
	}
	
	/**
	 * Method flyPiece. implementation of the fly
	 * @param from BaseHantoCoordinate
	 * @param to BaseHantoCoordinate
	 * @param basePiece BaseHantoPiece
	
	 * @return boolean */
	public boolean flyPiece(BaseHantoCoordinate from, BaseHantoCoordinate to,
			BaseHantoPiece basePiece) {
		if(isValidFly(from, to, basePiece)) {
			BaseHantoPiece pieceFrom = getPieceAt(from);
			deleteMove(from);
			placePieceDuringMove(to, pieceFrom);
			return true;
		}
		return false;
	}

	private boolean isValidFly(BaseHantoCoordinate from, BaseHantoCoordinate to,
			BaseHantoPiece basePiece) {
		BaseHantoPiece pieceFrom = getPieceAt(from);
		BaseHantoPiece pieceTo   = getPieceAt(to);
		
		if(pieceFrom == null || pieceTo != null) {
			return false;
		}
		
		if(pieceFrom.getType() != basePiece.getType() ||
				pieceFrom.getColor() != currentPlayerColor) {
			return false;
		}
		
		if(getDistance(from, to) >= flyLimit) {
			return false;
		}
		
		deleteMove(from);
		placePieceDuringMove(to, pieceFrom);
		if(!isContiguous(to)) {
			deleteMove(to);
			placePieceDuringMove(from, pieceFrom);
			return false;
		}
		deleteMove(to);
		placePieceDuringMove(from, pieceFrom);

		return true;

	}
	
	private int getDistance(BaseHantoCoordinate from, BaseHantoCoordinate to) {
		int dx = from.getX() - to.getX();
		int dy = from.getY() - to.getY();

		if (sign(dx) == sign(dy)) {
			return Math.abs(dx + dy);
		}
		else {
		    return Math.max(Math.abs(dx), Math.abs(dy));
		}
	}

	private int sign(int x) {
		if(x < 0) {
			return -1;
		}
		else {
			return 1;
		}
	}

	/**
	 * Method jumpPiece. implementation of the jump
	 * @param from BaseHantoCoordinate
	 * @param to BaseHantoCoordinate
	 * @param basePiece BaseHantoPiece
	
	 * @return boolean */
	public boolean jumpPiece(BaseHantoCoordinate from, BaseHantoCoordinate to,
			BaseHantoPiece basePiece) {

		if(isValidJump(from, to, basePiece)) {
			BaseHantoPiece pieceFrom = getPieceAt(from);
			deleteMove(from);
			placePieceDuringMove(to, pieceFrom);
			return true;
		}
		return false;
	}

	private boolean isValidJump(BaseHantoCoordinate from, BaseHantoCoordinate to,
			BaseHantoPiece basePiece) {
		
		BaseHantoPiece pieceFrom = getPieceAt(from);
		BaseHantoPiece pieceTo   = getPieceAt(to);
		if(pieceFrom == null || pieceTo != null) {
			return false;
		}

		if(!isStraightLine(from, to)) {
			return false;
		}
		
		if(isGap(from, to)) {
			return false;
		}

		if(getDistance(from, to) < 2) {
			return false;
		}

		if(pieceFrom.getType() != basePiece.getType() ||
				pieceFrom.getColor() != currentPlayerColor) {
			return false;
		}
		
		deleteMove(from);
		placePieceDuringMove(to, pieceFrom);
		if(!isContiguous(to)) {
			deleteMove(to);
			placePieceDuringMove(from, pieceFrom);
			return false;
		}
		deleteMove(to);
		placePieceDuringMove(from, pieceFrom);

		return true;
	}
	
	private boolean isGap(BaseHantoCoordinate from, BaseHantoCoordinate to) {
		int minX = (from.getX() < to.getX())? from.getX() : to.getX();
		int maxX = (from.getX() > to.getX())? from.getX() : to.getX();
		int minY = (from.getY() < to.getY())? from.getY() : to.getY();
		int maxY = (from.getY() > to.getY())? from.getY() : to.getY();
		
		boolean gapFound = false;
		
		if(from.getX() == to.getX()) {
			for(int y = minY + 1; y < maxY; ++y) {
				if(getPieceAt(new BaseHantoCoordinate(from.getX(), y)) == null) {
					gapFound = true;
				}
			}
		}
		else if(from.getY() == to.getY()) {
			for(int x = minX + 1; x < maxX; ++x) {
				if(getPieceAt(new BaseHantoCoordinate(x, from.getY())) == null) {
					gapFound = true;
				}
			}			
		}
		else {
			for(int x = minX + 1; x < maxX; ++x) {
				if(getPieceAt(new BaseHantoCoordinate(x, maxY - (x - minX))) == null) {
					gapFound = true;
				}			
			}
		}
		return gapFound;
	}

	private boolean isStraightLine(BaseHantoCoordinate from,
			BaseHantoCoordinate to) {
		return (from.getX() == to.getX()) ||
				(from.getY() == to.getY()) ||
				((Math.abs(from.getY() - to.getY()) == Math.abs(from.getX() - to.getX())) &&
					(sign(from.getY() - to.getY()) != sign(from.getX() - to.getX())));
	}

	/**
	 * Method placePiece - adds a move and throws exceptions if the
	 * move is invalid
	 * @param coordinate BetaHantoCoordinate
	 * @param piece BetaHantoPiece
	
	 * @return boolean */
	public boolean placePiece(BaseHantoCoordinate coordinate, BaseHantoPiece piece) {
		if(isValidPlacement(coordinate, piece)) {
			movesMap.put(coordinate, piece);
			return true;
		}
		else  {
			return false;
		}
	}
	
	/**
	 * Method placePieceDuringWalking.
	 * @param coordinate BaseHantoCoordinate
	 * @param piece BaseHantoPiece
	
	 * @return boolean */
	public boolean placePieceDuringMove(BaseHantoCoordinate coordinate, BaseHantoPiece piece) {
		if(movesMap.containsKey(coordinate)) {
			return false;
		}
		else {
			movesMap.put(coordinate, piece);
			return true;
		}
	}
	
	/**
	 * Method isAdmissiblePlacement. 
	 * @param from HantoCoordinate
	
	 * @return boolean */
	protected boolean isAdmissiblePlacement(HantoCoordinate from) {
		return from == null;
	}

	
	/**
	 * Method clear. Clears the board
	 */
	public void clear() {
		movesMap.clear();
	}

	/**
	 * Method areNeighbors. checks if 2 coordinates are neighbors
	 * @param from BaseHantoCoordinate
	 * @param to BaseHantoCoordinate
	
	 * @return boolean */
	protected static boolean areNeighbors(final BaseHantoCoordinate from, 
			final BaseHantoCoordinate to) {
		return  from.equals(to.getN())  ||
				from.equals(to.getNE()) ||
				from.equals(to.getNW()) ||
				from.equals(to.getS())  ||
				from.equals(to.getSE()) ||
				from.equals(to.getSW());
	}
	
	/**
	 * Method getNeighbors. Method that returns a list of neighbors
	 * @param loc BaseHantoCoordinate
	
	 * @return List<BaseHantoCoordinate> */
	protected List<BaseHantoCoordinate> getNeighbors(BaseHantoCoordinate loc) {
		final List<BaseHantoCoordinate> coords = new Vector<BaseHantoCoordinate>();
		if(getPieceAt(loc.getN()) != null) {
			coords.add(loc.getN());
		}
		if(getPieceAt(loc.getNE()) != null) {
			coords.add(loc.getNE());
		}
		if(getPieceAt(loc.getNW()) != null) {
			coords.add(loc.getNW());
		}
		if(getPieceAt(loc.getS()) != null) {
			coords.add(loc.getS());
		}
		if(getPieceAt(loc.getSE()) != null) {
			coords.add(loc.getSE());
		}
		if(getPieceAt(loc.getSW()) != null) {
			coords.add(loc.getSW());
		}
		return coords;
	}
	
	/**
	 * Method isSqueezed. Checks if a move squeezes a piece
	 * @param from BaseHantoCoordinate
	 * @param to BaseHantoCoordinate
	
	 * @return boolean */
	protected boolean isSqueezed(BaseHantoCoordinate from, 
			BaseHantoCoordinate to) {
		// piece needs to be squeezed if from and to 
		// have 2 Neighbors 
		final List<BaseHantoCoordinate> fromNeighbors = 
				getNeighbors(from);

		final List<BaseHantoCoordinate> toNeighbors = 
				getNeighbors(to);

		int numberOfNeighbors = 0;
		
		for(BaseHantoCoordinate fromCoord : fromNeighbors) {
			for(BaseHantoCoordinate toCoord : toNeighbors) {
				if(fromCoord.equals(toCoord)) {
					++numberOfNeighbors;
				}
			}
		}
			
		return numberOfNeighbors == 2;
	}
	
	/**
	 * Method isContiguous. Checks if the board is contiguous
	 * @param coord BaseHantoCoordinate
	
	 * @return boolean */
	protected boolean isContiguous(BaseHantoCoordinate coord){
		List<BaseHantoCoordinate> visited = new Vector<BaseHantoCoordinate>();
		Queue<BaseHantoCoordinate> coords = new LinkedList<BaseHantoCoordinate>();
		coords.add(coord);
		visited.add(coord);
		while(coords.size() > 0) {
			BaseHantoCoordinate newCoord = coords.poll();
			List<BaseHantoCoordinate> neighbors = 
					getNeighbors(newCoord);
			for(BaseHantoCoordinate eachCoord : neighbors) {
				if(!visited.contains(eachCoord)) {
					visited.add(eachCoord);
					coords.add(eachCoord);
				}
				
			}
		}
		return visited.size() == movesMap.size();
	}

	/**
	 * Method getAllPossibleMoves. Gives back a list with all possible moves
	 * @param playerColor HantoPlayerColor
	 * @return List<BaseHantoMove>
	 */
	public List<BaseHantoMove> getAllPossibleMoves(
			HantoPlayerColor playerColor) {
		List<BaseHantoMove> moves = new Vector<BaseHantoMove>();
		moves.addAll(0, placementCoordinates(playerColor));
		moves.addAll(0, movesAvailable(playerColor));
		
		return moves;
	}

	private List<BaseHantoMove> movesAvailable(
			HantoPlayerColor playerColor) {
		List<BaseHantoMove> availableMoves = new Vector<BaseHantoMove>();
		List<BaseHantoCoordinate> placeCoords = getAvailableCoordinates(playerColor);

		List<BaseHantoCoordinate> coordinates = new Vector<BaseHantoCoordinate>();
		for(BaseHantoCoordinate from: movesMap.keySet()) {
			coordinates.add(from);
		}
		for(BaseHantoCoordinate from: coordinates){
			BaseHantoPiece piece = movesMap.get(from);
			if(piece.getColor() != currentPlayerColor) {
				continue;
			}
			for(BaseHantoCoordinate to : placeCoords) {
				if(canWalk(piece.getType())) {
					if(isValidWalk(from, to, piece)) {
						availableMoves.add(new BaseHantoMove(from, to, piece));
				    }
				}				
				else if(canFly(piece.getType())) {
					if(isValidFly(from, to, piece)) {
						availableMoves.add(new BaseHantoMove(from, to, piece));
					}
				}
				else if(canJump(piece.getType())) {
					if(isValidJump(from, to, piece)) {
						availableMoves.add(new BaseHantoMove(from, to, piece));
					}
				}
			}
		}
		return availableMoves;
	}

	private List<BaseHantoMove> placementCoordinates(
			HantoPlayerColor playerColor) {
		List<BaseHantoMove> placementMoves = new Vector<BaseHantoMove>();
		List<BaseHantoCoordinate> placeCoords = getPlaceableCoordinates(playerColor);
		
		if(getNumberOfPieces(new BaseHantoPiece(currentPlayerColor, BUTTERFLY)) < maxNumberOfButterflies) {
			for(BaseHantoCoordinate coord : placeCoords) {
				placementMoves.add(new BaseHantoMove(null, coord, new BaseHantoPiece(currentPlayerColor, BUTTERFLY)));
			}
		}
		if(getNumberOfPieces(new BaseHantoPiece(currentPlayerColor, CRAB)) < maxNumberOfCrabs) {
			for(BaseHantoCoordinate coord : placeCoords) {
				placementMoves.add(new BaseHantoMove(null, coord, new BaseHantoPiece(currentPlayerColor, CRAB)));
			}
		}
		if(getNumberOfPieces(new BaseHantoPiece(currentPlayerColor, SPARROW)) < maxNumberOfSparrows) {
			for(BaseHantoCoordinate coord : placeCoords) {
				placementMoves.add(new BaseHantoMove(null, coord, new BaseHantoPiece(currentPlayerColor, SPARROW)));
			}
		}
		if(getNumberOfPieces(new BaseHantoPiece(currentPlayerColor, HORSE)) < maxNumberOfHorses) {
			for(BaseHantoCoordinate coord : placeCoords) {
				placementMoves.add(new BaseHantoMove(null, coord, new BaseHantoPiece(currentPlayerColor, HORSE)));
			}
		}

		return placementMoves;
	}

	private List<BaseHantoCoordinate> getAvailableCoordinates(
			HantoPlayerColor playerColor) {

		List<BaseHantoCoordinate> coords = new Vector<BaseHantoCoordinate>();
		
		List<BaseHantoCoordinate> visited = new Vector<BaseHantoCoordinate>();
		
		for(BaseHantoCoordinate coord: movesMap.keySet()) {
			visited.add(coord);
			List<BaseHantoCoordinate> neighbors = coord.getAdjacent();
			for(BaseHantoCoordinate eachCoord : neighbors) {
				if(movesMap.containsKey(eachCoord)) {
					continue;
				}
				if(!visited.contains(eachCoord)) {
					visited.add(eachCoord);
					coords.add(eachCoord);
				}
			}
		}	
		return coords;
	}
	
	private List<BaseHantoCoordinate> getPlaceableCoordinates(
			HantoPlayerColor playerColor) {
		List<BaseHantoCoordinate> coords = new Vector<BaseHantoCoordinate>();
		if(isFirstMove()) {
			coords.add(new BaseHantoCoordinate(0, 0));
			return coords;
		}
		
		List<BaseHantoCoordinate> visited = new Vector<BaseHantoCoordinate>();
		
		for(BaseHantoCoordinate coord: movesMap.keySet()) {
			visited.add(coord);
			List<BaseHantoCoordinate> neighbors = coord.getAdjacent();
			
			for(BaseHantoCoordinate eachCoord : neighbors) {
				if(movesMap.containsKey(eachCoord)) {
					continue;
				}
				if(!visited.contains(eachCoord)) {
					if(hasCorrectPlacementNeighbors(eachCoord, playerColor)) {
						visited.add(eachCoord);
						coords.add(eachCoord);
					}		
				}
			}
		}	
		return coords;
	}	
}
