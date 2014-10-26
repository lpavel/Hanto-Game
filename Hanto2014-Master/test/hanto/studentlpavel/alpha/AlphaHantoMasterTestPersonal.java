/******************************************************************************* 
 * This files was developed for CS4233: Object-Oriented Analysis & Design. 
 * The course was taken at Worcester Polytechnic Institute. 
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Eclipse Public License v1.0 
 * which accompanies this distribution, and is available at * http://www.eclipse.org/legal/epl-v10.html 
 * *******************************************************************************/
package hanto.studentlpavel.alpha;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import hanto.common.*;
import hanto.studentlpavel.HantoGameFactory;
import hanto.studentlpavel.alpha.AlphaHantoGame;

/** Testing class for Alpha Hanto
 */
public class AlphaHantoMasterTestPersonal {
	/**
	 * Internal class for these test cases.
	 * @version Sep 13, 2014
	 */
	
	class TestHantoCoordinate implements HantoCoordinate
	{
		private final int x, y;
		/**
		 * Constructor for TestHantoCoordinate.
		 * @param x int
		 * @param y int
		 */
		TestHantoCoordinate(int x, int y)
		{
			this.x = x;
			this.y = y;
		}
		/*
		 * @see hanto.common.HantoCoordinate#getX()
		 */
		@Override
		public int getX()
		{
			return x;
		}
		/*
		 * @see hanto.common.HantoCoordinate#getY()
		 */
		@Override
		public int getY()
		{
			return y;
		}
	}
	private static HantoGameFactory factory = null;
	private HantoGame game;
	
	/**
	 * Method initializeClass.
	 */
	@BeforeClass
	public static void initializeClass()
	{
		factory = HantoGameFactory.getInstance();
	}

	/**
	 * Method setup.
	 */
	@Before
	public void setup()
	{
		game = factory.makeHantoGame(HantoGameID.ALPHA_HANTO);
	}
	
	/**
	 * Method getAnAlphaHantoGameFromTheFactory.
	 */
	@Test
	public void getAnAlphaHantoGameFromTheFactory()
	{
		assertTrue(game instanceof AlphaHantoGame);
	}
	
	/**
	 * Method blueMakesValidFirstMove.
	 * @throws HantoException
	 */
	@Test
	public void blueMakesValidFirstMove() throws HantoException
	{
		final MoveResult mr = game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0, 0));
		assertEquals(MoveResult.OK, mr);
	}
		
	/**
	 * Method afterFirstMoveBlueButterflyIsAt0_0.
	 * @throws HantoException
	 */
	@Test
	public void afterFirstMoveBlueButterflyIsAt0_0() throws HantoException
	{
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0, 0));
		final HantoPiece p = game.getPieceAt(new TestHantoCoordinate(0, 0));
		assertEquals(HantoPlayerColor.BLUE, p.getColor());
		assertEquals(HantoPieceType.BUTTERFLY, p.getType());
	}
	
	/**
	 * Method bluePlacesNonButterfly.
	 * @throws HantoException
	 */
	@Test(expected=HantoException.class)
	public void bluePlacesNonButterfly() throws HantoException
	{
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0, 0));
	}

	/**
	 * Method redPlacesButterflyNextToBlueButterfly.
	 * @throws HantoException
	 */
	@Test
	public void redPlacesButterflyNextToBlueButterfly() throws HantoException
	{
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0, 0));
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0, 1));
		final HantoPiece p = game.getPieceAt(new TestHantoCoordinate(0, 1));
		assertEquals(HantoPieceType.BUTTERFLY, p.getType());
		assertEquals(HantoPlayerColor.RED, p.getColor());
	}

	/**
	 * Method blueAttemptsToPlaceButterflyAtWrongLocation.
	 * @throws HantoException
	 */
	@Test(expected=HantoException.class)
	public void blueAttemptsToPlaceButterflyAtWrongLocation() throws HantoException
	{
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(-1, 1));
	}
	
	/**
	 * Method redMakesValidSecondMoveAndGameIsDrawn.
	 * @throws HantoException
	 */
	@Test
	public void redMakesValidSecondMoveAndGameIsDrawn() throws HantoException
	{
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0, 0));
		final MoveResult mr = game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(-1, 1));
		assertEquals(MoveResult.DRAW, mr);
	}

	/**
	 * Method redPlacesButterflyNonAdjacentToBlueButterfly.
	 * @throws HantoException
	 */
	@Test(expected=HantoException.class)
	public void redPlacesButterflyNonAdjacentToBlueButterfly() throws HantoException
	{
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0, 0));
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0, 2));
	}
	/**
	 * Method attemptToMoveRatherThanPlace.
	 * @throws HantoException
	 */
	@Test(expected=HantoException.class)
	public void attemptToMoveRatherThanPlace() throws HantoException
	{
		game.makeMove(HantoPieceType.BUTTERFLY, new TestHantoCoordinate(0, 1), new TestHantoCoordinate(0, 0));
	}

	/**
	 * Method checkPrintableBoard.
	 * @throws HantoException
	 */
	@Test
	public void checkPrintableBoard() throws HantoException
	{
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0, 0));
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(-1, 0));
		assertNotNull(game.getPrintableBoard());
	}
	
	/**
	 * Method redMakesValidMoveBlueTriesToPlaceOver.
	 * @throws HantoException
	 */
	@Test(expected=HantoException.class)
	public void redMakesValidMoveBlueTriesToPlaceOver() throws HantoException
	{
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0, 0));
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0, 0));
	}
	
	/**
	 * Method redMakesValidMoveBluePlacesValidlySE.
	 * @throws HantoException
	 */
	@Test
	public void redMakesValidMoveBluePlacesValidlySE() throws HantoException
	{
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0, 0));
		final MoveResult mr = game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(1, -1));
		assertEquals(MoveResult.DRAW, mr);
	}
	
	/**
	 * Method redMakesValidMoveBluePlacesValidlyS.
	 * @throws HantoException
	 */
	@Test
	public void redMakesValidMoveBluePlacesValidlyS() throws HantoException
	{
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0, 0));
		final MoveResult mr = game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0, -1));
		assertEquals(MoveResult.DRAW, mr);
	}
	
	/**
	 * Method redMakesValidMoveBluePlacesValidlyNE.
	 * @throws HantoException
	 */
	@Test
	public void redMakesValidMoveBluePlacesValidlyNE() throws HantoException
	{
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0, 0));
		final MoveResult mr = game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(1, 0));
		assertEquals(MoveResult.DRAW, mr);
	}
	
	/**
	 * Method placeThreePiecesOnTheBoard.
	 * @throws HantoException
	 */
	@Test(expected=HantoException.class)
	public void placeThreePiecesOnTheBoard() throws HantoException
	{
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0, 0));
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0, 1));
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0, -1));
	}
	
	
	
}
