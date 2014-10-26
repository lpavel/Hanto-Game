/******************************************************************************* 
 * This files was developed for CS4233: Object-Oriented Analysis & Design. 
 * The course was taken at Worcester Polytechnic Institute. 
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Eclipse Public License v1.0 
 * which accompanies this distribution, and is available at * http://www.eclipse.org/legal/epl-v10.html 
 * *******************************************************************************/
package hanto.studentlpavel.beta;
import static org.junit.Assert.*;
import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoGame;
import hanto.common.HantoGameID;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;
import hanto.studentlpavel.HantoGameFactory;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/** Testing class for Beta Hanto
 */
public class BetaHantoMasterTestPersonal {
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
		TestHantoCoordinate(final int x, final int y)
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
		game = factory.makeHantoGame(HantoGameID.BETA_HANTO);
	}
	
	/**
	 * Method getABetaHantoGameFromTheFactory.
	 */
	@Test
	public void getABetaHantoGameFromTheFactory()
	{
		assertTrue(game instanceof BetaHantoGame);
	}
	
	/**
	 * Method blueMakesValidFirstMoveWithButterfly.
	 * @throws HantoException
	 */
	@Test
	public void blueMakesValidFirstMoveWithButterfly() throws HantoException
	{
		final MoveResult mr = game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0, 0));
		assertEquals(MoveResult.OK, mr);
	}
	
	/**
	 * Method blueMakesValidFirstMoveWithSparrow.
	 * @throws HantoException
	 */
	@Test
	public void blueMakesValidFirstMoveWithSparrow() throws HantoException
	{
		final MoveResult mr = game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0, 0));
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
	 * Method afterFirstMoveBlueSparrowIsAt0_0.
	 * @throws HantoException
	 */
	@Test
	public void afterFirstMoveBlueSparrowIsAt0_0() throws HantoException
	{
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0, 0));
		final HantoPiece p = game.getPieceAt(new TestHantoCoordinate(0, 0));
		assertEquals(HantoPlayerColor.BLUE, p.getColor());
		assertEquals(HantoPieceType.SPARROW, p.getType());
	}
	
	/**
	 * Method bluePlacesNonButterflyOrSparrow.
	 * @throws HantoException
	 */
	@Test(expected=HantoException.class)
	public void bluePlacesNonButterflyOrSparrow() throws HantoException
	{
		game.makeMove(HantoPieceType.CRAB, null, new TestHantoCoordinate(0, 0));
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
	 * Method placeThreeButterfliesOnTheBoard.
	 * @throws HantoException
	 */
	@Test(expected=HantoException.class)
	public void placeThreeButterfliesOnTheBoard() throws HantoException
	{
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0, 0));
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0, 1));
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(-1, 0));
	}

	/**
	 * Method placeFourSparrowsOnTheBoard.
	 * @throws HantoException
	 */
	@Test
	public void placeFourSparrowsOnTheBoard() throws HantoException
	{
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0, 0));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0, 1));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(-1, 0));
		final MoveResult mr = game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(1, 0));
		assertEquals(MoveResult.OK, mr);
	}

	/**
	 * Method redPlacesSparrowNextToBlueButterfly.
	 * @throws HantoException
	 */
	@Test
	public void redPlacesSparrowNextToBlueButterfly() throws HantoException
	{
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0, 0));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0, 1));
		final HantoPiece p = game.getPieceAt(new TestHantoCoordinate(0, 1));
		assertEquals(HantoPieceType.SPARROW, p.getType());
		assertEquals(HantoPlayerColor.RED, p.getColor());
	}

	/**
	 * Method blueButterflyNotOnBoardByFourthMove.
	 * @throws HantoException
	 */
	@Test(expected=HantoException.class)
	public void blueButterflyNotOnBoardByFourthMove() throws HantoException
	{
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0, 0));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0, 1));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(-1, 0));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(-1, 1));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(-1, -1));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(1, 1));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(-1, 1));
	}

	/**
	 * Method redButterflyNotOnBoardByFourthMove.
	 * @throws HantoException
	 */
	@Test(expected=HantoException.class)
	public void redButterflyNotOnBoardByFourthMove() throws HantoException
	{
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0, 0));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0, 1));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(-1, 0));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(-1, 1));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(-1, -1));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(1, 1));
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(-1, 1));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0, 2));
	}

	/**
	 * Method butterflyOnBoardAtFourthMove.
	 * @throws HantoException
	 */
	@Test
	public void butterflyOnBoardAtFourthMove() throws HantoException
	{
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0, 0));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0, 1));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(-1, 0));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(-1, 1));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(-1, -1));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(1, 1));
		final MoveResult mr1 = game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(-2, 2));
		final MoveResult mr2 = game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(1, 0));
		assertEquals(MoveResult.OK, mr1);
		assertEquals(MoveResult.OK, mr2);
	}

	/**
	 * Method makeTwelveValidMovesEndWithDraw.
	 * @throws HantoException
	 */
	@Test
	public void makeTwelveValidMovesEndWithDraw() throws HantoException
	{
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0, 0));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0, 1));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(-1, 0));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(-1, 1));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(1, -1));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(1, 1));
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(2, -2));
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(1, -2));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0, -2));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(2, -1));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(2, 0));
		final MoveResult mr1 = game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(3, 0));
		assertEquals(MoveResult.DRAW, mr1);
	}

	/**
	 * Method makeThirtheenMoves.
	 * @throws HantoException
	 */
	@Test(expected=HantoException.class)
	public void makeThirtheenMoves() throws HantoException
	{
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0, 0));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0, 1));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(-1, 0));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(-1, 1));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(1, -1));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(1, 1));
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(2, -2));
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(1, -2));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0, -2));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(2, -1));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(2, 0));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(3, 0));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(4, 0));
	}
	
	/**
	 * Method blueStartsAtInvalidPlace.
	 * @throws HantoException
	 */
	@Test(expected=HantoException.class)
	public void blueStartsAtInvalidPlace() throws HantoException
	{
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(-1, 1));
	}
	
	/**
	 * Method redMakesValidSecondMoveIsOK.
	 * @throws HantoException
	 */
	@Test
	public void redMakesValidSecondMoveIsOK() throws HantoException
	{
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0, 0));
		final MoveResult mr = game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(-1, 1));
		assertEquals(MoveResult.OK, mr);
	}

	/**
	 * Method redPlacesNonAdjacent.
	 * @throws HantoException
	 */
	@Test(expected=HantoException.class)
	public void redPlacesNonAdjacent() throws HantoException
	{
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0, 0));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0, 2));
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
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0, 0));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0, 0));
	}
	
	/**
	 * Method redMakesValidMoveBluePlacesValidlySE.
	 * @throws HantoException
	 */
	@Test
	public void redMakesValidMoveBluePlacesValidlySE() throws HantoException
	{
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0, 0));
		final MoveResult mr = game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(1, -1));
		assertEquals(MoveResult.OK, mr);
	}
	
	/**
	 * Method redMakesValidMoveBluePlacesValidlyS.
	 * @throws HantoException
	 */
	@Test
	public void redMakesValidMoveBluePlacesValidlyS() throws HantoException
	{
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0, 0));
		final MoveResult mr = game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0, -1));
		assertEquals(MoveResult.OK, mr);
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
		assertEquals(MoveResult.OK, mr);
	}

	/**
	 * Method redWins.
	 * @throws HantoException
	 */
	@Test
	public void redWins() throws HantoException
	{
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0, 0));
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0, 1));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0, 2));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(-1, 1));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0, 3));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(-1, 0));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0, 4));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0, -1));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0, 5));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(1, -1));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0, 6));
		final MoveResult mr1 = game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(1, 0));
		assertEquals(MoveResult.RED_WINS, mr1);
	}

	/**
	 * Method blueWins.
	 * @throws HantoException
	 */
	@Test
	public void blueWins() throws HantoException
	{
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0, 0));
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0, 1));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(-1, 1));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0, -1));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(-1, 2));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0, -2));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0, 2));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0, -3));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(1, 1));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0, -4));
		final MoveResult mr1 = game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(1, 0));
		assertEquals(MoveResult.BLUE_WINS, mr1);
	}

	/**
	 * Method blueWinsAndTryOneMoveAfter.
	 * @throws HantoException
	 */
	@Test(expected=HantoException.class)
	public void blueWinsAndTryOneMoveAfter() throws HantoException
	{
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0, 0));
		game.makeMove(HantoPieceType.BUTTERFLY, null, new TestHantoCoordinate(0, 1));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(-1, 1));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0, -1));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(-1, 2));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0, -2));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0, 2));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0, -3));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(1, 1));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0, -4));
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(1, 0));
		final MoveResult mr1 = game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(1, 0));
		assertEquals(MoveResult.BLUE_WINS, mr1);
		game.makeMove(HantoPieceType.SPARROW, null, new TestHantoCoordinate(0, -5));
	}
	
}
