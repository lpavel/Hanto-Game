/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.studentlpavel.gamma;

import static org.junit.Assert.*;
import hanto.common.*;

import org.junit.*;

import common.*;
import static hanto.common.HantoPieceType.*;
import static hanto.common.MoveResult.*;
import static hanto.common.HantoPlayerColor.*;
import common.HantoTestGame.PieceLocationPair;

/**
 * Description
 * @version Sep 24, 2014
 */
public class GammaHantoMasterTestPersonal
{
	/**
	 * Internal class for these test cases.
	 * @version Sep 13, 2014
	 */
	class TestHantoCoordinate implements HantoCoordinate
	{
		private final int x, y;
		
		public TestHantoCoordinate(int x, int y)
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
	
	private static HantoTestGameFactory factory;
	private HantoGame game;
	private HantoTestGame testGame;
	
	@BeforeClass
	public static void initializeClass()
	{
		factory = HantoTestGameFactory.getInstance();
	}
	
	@Before
	public void setup()
	{
		// By default, blue moves first.
		testGame = factory.makeHantoTestGame(HantoGameID.GAMMA_HANTO);
		game = testGame;
	}
	
	@Test
	public void bluePlacesButterflyFirst() throws HantoException, HantoPrematureResignationException
	{
		final MoveResult mr = game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		assertEquals(OK, mr);
		final HantoPiece piece = game.getPieceAt(makeCoordinate(0, 0));
		assertEquals(BLUE, piece.getColor());
		assertEquals(BUTTERFLY, piece.getType());
	}
	
	@Test
	public void redPlacesButterflyFirst() throws HantoException, HantoPrematureResignationException
	{
		testGame = factory.makeHantoTestGame(HantoGameID.GAMMA_HANTO, HantoPlayerColor.RED);
		game = testGame;
		
		final MoveResult mr = game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		assertEquals(OK, mr);
		final HantoPiece piece = game.getPieceAt(makeCoordinate(0, 0));
		assertEquals(RED, piece.getColor());
		assertEquals(BUTTERFLY, piece.getType());
	}	
	
	@Test
	public void redPlacesSparrowFirst() throws HantoException, HantoPrematureResignationException
	{
		final MoveResult mr = game.makeMove(SPARROW, null, makeCoordinate(0, 0));
		assertEquals(OK, mr);
	}
	
	@Test
	public void blueMovesSparrow() throws HantoException, HantoPrematureResignationException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
		game.makeMove(SPARROW, null, makeCoordinate(0, -1));
		game.makeMove(SPARROW, null, makeCoordinate(0, 2));
		final MoveResult mr = game.makeMove(SPARROW, makeCoordinate(0, -1), makeCoordinate(-1, 0));
		assertEquals(OK, mr);
		final HantoPiece piece = game.getPieceAt(makeCoordinate(-1, 0));
		assertEquals(BLUE, piece.getColor());
		assertEquals(SPARROW, piece.getType());
	}

	@Test
	public void redWins() throws HantoException, HantoPrematureResignationException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
		game.makeMove(SPARROW, null, makeCoordinate(-1, 0));
		game.makeMove(BUTTERFLY, makeCoordinate(0, 1), makeCoordinate(-1, 1));
		game.makeMove(SPARROW, null, makeCoordinate(0, -1));
		game.makeMove(SPARROW, null, makeCoordinate(-1, 2));
		game.makeMove(SPARROW, null, makeCoordinate(1, 0));
		game.makeMove(SPARROW, makeCoordinate(-1, 2), makeCoordinate(0, 1));
		final MoveResult mr = game.makeMove(SPARROW, null, makeCoordinate(1, -1));
		assertEquals(RED_WINS, mr);
	}

	@Test
	public void blueWins() throws HantoException, HantoPrematureResignationException
	{
		testGame = factory.makeHantoTestGame(HantoGameID.GAMMA_HANTO, HantoPlayerColor.RED);
		game = testGame;

		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
		game.makeMove(SPARROW, null, makeCoordinate(-1, 0));
		game.makeMove(BUTTERFLY, makeCoordinate(0, 1), makeCoordinate(-1, 1));
		game.makeMove(SPARROW, null, makeCoordinate(0, -1));
		game.makeMove(SPARROW, null, makeCoordinate(-1, 2));
		game.makeMove(SPARROW, null, makeCoordinate(1, 0));
		game.makeMove(SPARROW, makeCoordinate(-1, 2), makeCoordinate(0, 1));
		final MoveResult mr = game.makeMove(SPARROW, null, makeCoordinate(1, -1));
		assertEquals(BLUE_WINS, mr);
	}

	@Test(expected=HantoException.class)
	public void attemptToMoveWrongColor() throws HantoException, HantoPrematureResignationException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
		game.makeMove(BUTTERFLY, makeCoordinate(0, 1), makeCoordinate(1, 0));
	}	
	
	@Test(expected=HantoException.class)
	public void attemptToPlaceInWrongPosition() throws HantoException, HantoPrematureResignationException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
		game.makeMove(SPARROW, null, makeCoordinate(0, 0));
	}

	@Test(expected=HantoException.class)
	public void attemptToPlaceNextToOpposite() throws HantoException, HantoPrematureResignationException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
		game.makeMove(SPARROW, null, makeCoordinate(1, 0));
	}
	
	@Test(expected=HantoException.class)
	public void attemptToPlaceInvalidPiece() throws HantoException, HantoPrematureResignationException
	{
		game.makeMove(CRAB, null, makeCoordinate(0, 0));
	}
	

	@Test
	public void blueMovesSparrowUsingTestGame() throws HantoException, HantoPrematureResignationException
	{
		final PieceLocationPair[] board = new PieceLocationPair[] {
		    plPair(BLUE, BUTTERFLY, 0, 0), plPair(RED, BUTTERFLY, 0, 1),
		    plPair(BLUE, SPARROW, 0, -1), plPair(RED, SPARROW, 0, 2)
		    
		};
		testGame.initializeBoard(board);
		testGame.setPlayerMoving(BLUE);
		testGame.setTurnNumber(3);
		final MoveResult mr = game.makeMove(SPARROW, makeCoordinate(0, -1), makeCoordinate(-1, 0));
		assertEquals(OK, mr);
		final HantoPiece piece = game.getPieceAt(makeCoordinate(-1, 0));
		assertEquals(BLUE, piece.getColor());
		assertEquals(SPARROW, piece.getType());
	}

	@Test(expected=HantoException.class)
	public void blueButterflyNotOnBoardByFourthTurn() throws HantoException, HantoPrematureResignationException
	{
		final PieceLocationPair[] board = new PieceLocationPair[] {
		    plPair(BLUE, SPARROW, 0, 0), plPair(RED, SPARROW, 0, 1),
		    plPair(BLUE, SPARROW, 0, -1), plPair(RED, SPARROW, 0, 2),
		    plPair(BLUE, SPARROW, -1, -1), plPair(RED, SPARROW, 1, 1)
		    
		};
		testGame.initializeBoard(board);
		testGame.setPlayerMoving(BLUE);
		testGame.setTurnNumber(4);
		final MoveResult mr = game.makeMove(SPARROW, null, makeCoordinate(0, -2));
		assertEquals(OK, mr);
		final HantoPiece piece = game.getPieceAt(makeCoordinate(-1, 0));
		assertEquals(BLUE, piece.getColor());
		assertEquals(SPARROW, piece.getType());
	}

	@Test(expected=HantoException.class)
	public void redButterflyNotOnBoardByFourthTurn() throws HantoException, HantoPrematureResignationException
	{
		final PieceLocationPair[] board = new PieceLocationPair[] {
		    plPair(BLUE, SPARROW, 0, 0), plPair(RED, SPARROW, 0, 1),
		    plPair(BLUE, SPARROW, 0, -1), plPair(RED, SPARROW, 0, 2),
		    plPair(BLUE, SPARROW, -1, -1), plPair(RED, SPARROW, 1, 1)
		    
		};
		testGame.initializeBoard(board);
		testGame.setPlayerMoving(BLUE);
		testGame.setTurnNumber(4);
		final MoveResult mr = game.makeMove(BUTTERFLY, null, makeCoordinate(0, -2));
		assertEquals(OK, mr);
		final MoveResult mr2 = game.makeMove(SPARROW, null, makeCoordinate(-1, 2));		
		final HantoPiece piece = game.getPieceAt(makeCoordinate(-1, 0));
		assertEquals(BLUE, piece.getColor());
		assertEquals(SPARROW, piece.getType());
	}

	@Test
	public void gameEndsInDrawAfter20Moves() throws HantoException, HantoPrematureResignationException
	{
		final PieceLocationPair[] board = new PieceLocationPair[] {
			    plPair(BLUE, BUTTERFLY, 0, 0), plPair(RED, BUTTERFLY, 0, 1),
			    plPair(BLUE, SPARROW, 0, -1), plPair(RED, SPARROW, 0, 2)
			    
		};
		testGame.initializeBoard(board);
		testGame.setPlayerMoving(RED);
		testGame.setTurnNumber(20);
		assertEquals(DRAW, game.makeMove(SPARROW, makeCoordinate(0, 2), makeCoordinate(1, 1)));
	}

	@Test(expected=HantoException.class)
	public void gameIsOverAfter50Moves() throws HantoException, HantoPrematureResignationException
	{
		final PieceLocationPair[] board = new PieceLocationPair[] {
			    plPair(BLUE, BUTTERFLY, 0, 0), plPair(RED, BUTTERFLY, 0, 1),
			    plPair(BLUE, SPARROW, 0, -1), plPair(RED, SPARROW, 0, 2)
			    
		};
		testGame.initializeBoard(board);
		testGame.setPlayerMoving(RED);
		testGame.setTurnNumber(50);
		game.makeMove(SPARROW, makeCoordinate(0, 2), makeCoordinate(1, 1));
	}

	@Test
	public void moveButterfly() throws HantoException, HantoPrematureResignationException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
		assertEquals(OK, game.makeMove(BUTTERFLY, makeCoordinate(0, 0), makeCoordinate(1, 0)));
		final HantoPiece piece = game.getPieceAt(makeCoordinate(1, 0));
		assertEquals(BLUE, piece.getColor());
		assertEquals(BUTTERFLY, piece.getType());
		assertNull(game.getPieceAt(makeCoordinate(0, 0)));
	}
	
	@Test(expected=HantoException.class)
	public void moveToDisconnectConfiguration() throws HantoException, HantoPrematureResignationException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
		game.makeMove(BUTTERFLY, makeCoordinate(0, 0), makeCoordinate(0, -1));
	}

	@Test(expected=HantoException.class)
	public void attemptToMoveAPieceFromAnEmptyHex() throws HantoException, HantoPrematureResignationException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
		game.makeMove(BUTTERFLY, makeCoordinate(1, 0), makeCoordinate(0, -1));
	}
	
	@Test(expected=HantoException.class)
	public void attemptToMoveWrongPiece() throws HantoException, HantoPrematureResignationException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
		game.makeMove(SPARROW, makeCoordinate(0, 0), makeCoordinate(1, 0));
	}
	
	@Test(expected=HantoException.class)
	public void attemptToMoveMoreFartherThanOneSquare() throws HantoException, HantoPrematureResignationException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
		game.makeMove(SPARROW, null, makeCoordinate(-1, 0));
		game.makeMove(SPARROW, null, makeCoordinate(0, 2));
		game.makeMove(SPARROW, makeCoordinate(-1, 0), makeCoordinate(1, -1));
	}

	@Test(expected=HantoException.class)
	public void tryToMoveSqueezingThroughTwoPieces() throws HantoException, HantoPrematureResignationException
	{
		final PieceLocationPair[] board = new PieceLocationPair[] {
		    plPair(BLUE, BUTTERFLY, 0, 0), plPair(RED, BUTTERFLY, 0, 1),
		    plPair(BLUE, SPARROW, 0, -1), plPair(RED, SPARROW, 0, 2),
		    plPair(BLUE, SPARROW, 1, -1), plPair(RED, SPARROW, 1, 1),
		    plPair(BLUE, SPARROW, 1, -2), plPair(RED, SPARROW, -1, 2),
		    plPair(BLUE, SPARROW, -1, -1), plPair(RED, SPARROW, 0, 3)
		    
		};
		testGame.initializeBoard(board);
		testGame.setPlayerMoving(BLUE);
		testGame.setTurnNumber(6);
		final MoveResult mr = game.makeMove(SPARROW, makeCoordinate(0, -1), makeCoordinate(0, -2));
	}

	@Test(expected=HantoException.class)
	public void bluePlacesSixSparrows() throws HantoException, HantoPrematureResignationException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
		game.makeMove(SPARROW, null, makeCoordinate(0, -1));		
		game.makeMove(SPARROW, null, makeCoordinate(0, 2));
		game.makeMove(SPARROW, null, makeCoordinate(0, -2));		
		game.makeMove(SPARROW, null, makeCoordinate(0, 3));
		game.makeMove(SPARROW, null, makeCoordinate(0, -3));		
		game.makeMove(SPARROW, null, makeCoordinate(0, 4));
		game.makeMove(SPARROW, null, makeCoordinate(0, -4));		
		game.makeMove(SPARROW, null, makeCoordinate(0, 5));
		game.makeMove(SPARROW, null, makeCoordinate(0, -5));		
		game.makeMove(SPARROW, null, makeCoordinate(0, 6));
		game.makeMove(SPARROW, null, makeCoordinate(0, -6));		
	}
	
	@Test(expected=HantoException.class)
	public void redPlacesSixSparrows() throws HantoException, HantoPrematureResignationException
	{
		testGame = factory.makeHantoTestGame(HantoGameID.GAMMA_HANTO, HantoPlayerColor.RED);
		game = testGame;

		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
		game.makeMove(SPARROW, null, makeCoordinate(0, -1));		
		game.makeMove(SPARROW, null, makeCoordinate(0, 2));
		game.makeMove(SPARROW, null, makeCoordinate(0, -2));		
		game.makeMove(SPARROW, null, makeCoordinate(0, 3));
		game.makeMove(SPARROW, null, makeCoordinate(0, -3));		
		game.makeMove(SPARROW, null, makeCoordinate(0, 4));
		game.makeMove(SPARROW, null, makeCoordinate(0, -4));		
		game.makeMove(SPARROW, null, makeCoordinate(0, 5));
		game.makeMove(SPARROW, null, makeCoordinate(0, -5));		
		game.makeMove(SPARROW, null, makeCoordinate(0, 6));
		game.makeMove(SPARROW, null, makeCoordinate(0, -6));		
	}

	
	// Helper methods
	private HantoCoordinate makeCoordinate(int x, int y)
	{
		return new TestHantoCoordinate(x, y);
	}
	
	/**
	 * Factory method to create a piece location pair.
	 * @param player the player color
	 * @param pieceType the piece type
	 * @param x starting location
	 * @param y end location
	 * @return
	 */
	private PieceLocationPair plPair(HantoPlayerColor player, HantoPieceType pieceType, 
			int x, int y)
	{
		return new PieceLocationPair(player, pieceType, new TestHantoCoordinate(x, y));
	}
}
