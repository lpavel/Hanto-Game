/******************************************************************************* 
 * This files was developed for CS4233: Object-Oriented Analysis & Design. 
 * The course was taken at Worcester Polytechnic Institute. 
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Eclipse Public License v1.0 
 * which accompanies this distribution, and is available at * http://www.eclipse.org/legal/epl-v10.html 
 * *******************************************************************************/
package hanto.studentlpavel;

import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;

/**
 */
public class BaseHantoPiece implements HantoPiece {

	HantoPlayerColor playerColor;
	HantoPieceType pieceType;
	
	/**
	 * Constructor for BetaHantoPiece.
	 * @param hantoColor HantoPlayerColor
	 * @param hantoType HantoPieceType
	 */
	public BaseHantoPiece(final HantoPlayerColor hantoColor, final HantoPieceType hantoType) {
		playerColor = hantoColor;
		pieceType  = hantoType;
	}
		
	@Override
	public HantoPlayerColor getColor() {
		return playerColor;
	}

	@Override
	public HantoPieceType getType() {
		return pieceType;
	}

}
