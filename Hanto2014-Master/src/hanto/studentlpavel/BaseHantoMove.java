/******************************************************************************* 
 * This files was developed for CS4233: Object-Oriented Analysis & Design. 
 * The course was taken at Worcester Polytechnic Institute. 
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Eclipse Public License v1.0 
 * which accompanies this distribution, and is available at * http://www.eclipse.org/legal/epl-v10.html 
 * *******************************************************************************/
package hanto.studentlpavel;

/** class BaseHantoMove is a wrapper of a move. It is mostly used in the AI for getting
 *  all the possible moves
 */
public class BaseHantoMove {
	BaseHantoCoordinate from; 
	BaseHantoCoordinate to; 
	BaseHantoPiece piece;
	
	/* gets the coordinate from where the move was made
	 * @return BaseHantoCoordinate
	 */	
	public BaseHantoCoordinate getFrom() {
		return from;
	}

	/* gets the coordinate to where the move was made
	 * @return BaseHantoCoordinate
	 */	
	public BaseHantoCoordinate getTo() {
		return to;
	}

	/* gets the coordinate the piece used in the move
	 * @return BaseHantoCoordinate
	 */	
	public BaseHantoPiece getPiece() {
		return piece;
	}
	
	/**
	 * Constructor for BaseHantoMove.
	 * @param from BaseHantoCoordinate
	 * @param to BaseHantoCoordinate
	 * @param piece BaseHantoPiece
	 */
	public BaseHantoMove(final BaseHantoCoordinate from, final BaseHantoCoordinate to,
			final BaseHantoPiece piece) {
		this.from = from;
		this.to = to;
		this.piece = piece;
	}
}
