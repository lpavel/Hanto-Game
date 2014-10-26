package hanto.studentlpavel.epsilon;

import static hanto.common.HantoPieceType.*;
import static hanto.common.HantoPlayerColor.*;
import static hanto.common.MoveResult.*;
import static org.junit.Assert.*;
import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoGame;
import hanto.common.HantoGameID;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.HantoPrematureResignationException;
import hanto.common.MoveResult;
import hanto.studentlpavel.epsilon.EpsilonHantoMasterTestPersonal.TestHantoCoordinate;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import common.HantoTestGame;
import common.HantoTestGameFactory;
import common.HantoTestGame.PieceLocationPair;

public class EpsilonHantoMasterTestPersonal {

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
		testGame = factory.makeHantoTestGame(HantoGameID.EPSILON_HANTO);
		game = testGame;
	}
	
	@Test
	public void bluePlacesButterflyFirst() throws HantoException
	{
		final MoveResult mr = game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		assertEquals(OK, mr);
		final HantoPiece piece = game.getPieceAt(makeCoordinate(0, 0));
		assertEquals(BLUE, piece.getColor());
		assertEquals(BUTTERFLY, piece.getType());
	}
	
	@Test
	public void redPlacesButterflyFirst() throws HantoException
	{
		testGame = factory.makeHantoTestGame(HantoGameID.EPSILON_HANTO, HantoPlayerColor.RED);
		game = testGame;
		
		final MoveResult mr = game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		assertEquals(OK, mr);
		final HantoPiece piece = game.getPieceAt(makeCoordinate(0, 0));
		assertEquals(RED, piece.getColor());
		assertEquals(BUTTERFLY, piece.getType());
	}
	
	@Test
	public void placeHorse() throws HantoException
	{
		final MoveResult mr = game.makeMove(HORSE, null, makeCoordinate(0, 0));
		assertEquals(OK, mr);
	}	

	@Test(expected=HantoException.class)
	public void placeInvalidPiece() throws HantoException
	{
		game.makeMove(DOVE, null, makeCoordinate(0, 0));
	}
	
	@Test
	public void blueFliesSparrow() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(CRAB, null, makeCoordinate(0, 1));
		game.makeMove(SPARROW, null, makeCoordinate(0, -1));		
		game.makeMove(BUTTERFLY, null, makeCoordinate(1, 1));
		final MoveResult mr = game.makeMove(SPARROW, makeCoordinate(0, -1), makeCoordinate(0, 2));
		assertEquals(OK, mr);
	}

	@Test
	public void blueFliesSparrow5Hexes() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
		game.makeMove(CRAB, null, makeCoordinate(0, -1));		
		game.makeMove(CRAB, null, makeCoordinate(0, 2));
		game.makeMove(SPARROW, null, makeCoordinate(0, -2));		
		game.makeMove(CRAB, null, makeCoordinate(1, 1));
		assertEquals(OK, game.makeMove(SPARROW, makeCoordinate(0, -2), makeCoordinate(-1, 2)));
	}	
	
	@Test(expected=HantoException.class)
	public void blueFliesFartherThan5Hexes() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
		game.makeMove(CRAB, null, makeCoordinate(0, -1));		
		game.makeMove(CRAB, null, makeCoordinate(0, 2));
		game.makeMove(SPARROW, null, makeCoordinate(0, -2));		
		game.makeMove(CRAB, null, makeCoordinate(0, 3));
		game.makeMove(SPARROW, makeCoordinate(0, -2), makeCoordinate(0, 4));		
	}
	
	@Test
	public void blueMovesButterfly() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
		game.makeMove(BUTTERFLY, makeCoordinate(0, 0), makeCoordinate(-1, 1));		
	}
	
	@Test(expected=HantoException.class)
	public void bluePlacesTwoButterflies() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
		game.makeMove(BUTTERFLY, null, makeCoordinate(-1, 1));		
	}
	
	@Test(expected=HantoException.class)
	public void bluePlacesThreeSparrows() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
		game.makeMove(SPARROW, null, makeCoordinate(0, -1));		
		game.makeMove(SPARROW, null, makeCoordinate(0, 2));
		game.makeMove(SPARROW, null, makeCoordinate(0, -2));		
		game.makeMove(SPARROW, null, makeCoordinate(0, 3));
		game.makeMove(SPARROW, null, makeCoordinate(0, -3));		
	}
	
	@Test(expected=HantoException.class)
	public void bluePlaces7Crabs() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
		game.makeMove(CRAB, null, makeCoordinate(0, -1));		
		game.makeMove(CRAB, null, makeCoordinate(0, 2));
		game.makeMove(CRAB, null, makeCoordinate(0, -2));		
		game.makeMove(CRAB, null, makeCoordinate(0, 3));
		game.makeMove(CRAB, null, makeCoordinate(0, -3));		
		game.makeMove(CRAB, null, makeCoordinate(0, 4));
		game.makeMove(CRAB, null, makeCoordinate(0, -4));		
		game.makeMove(CRAB, null, makeCoordinate(0, 5));
		game.makeMove(CRAB, null, makeCoordinate(0, -5));		
		game.makeMove(CRAB, null, makeCoordinate(0, 6));
		game.makeMove(CRAB, null, makeCoordinate(0, -6));		
		game.makeMove(CRAB, null, makeCoordinate(0, 7));
		game.makeMove(CRAB, null, makeCoordinate(0, -7));
	}

	@Test
	public void bluePlaces4Horses() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
		game.makeMove(HORSE, null, makeCoordinate(0, -1));		
		game.makeMove(CRAB, null, makeCoordinate(0, 2));
		game.makeMove(HORSE, null, makeCoordinate(0, -2));		
		game.makeMove(CRAB, null, makeCoordinate(0, 3));
		game.makeMove(HORSE, null, makeCoordinate(0, -3));		
		game.makeMove(CRAB, null, makeCoordinate(0, 4));
		assertEquals(OK, game.makeMove(HORSE, null, makeCoordinate(0, -4)));		
	}

	@Test
	public void redPlaces4Horses() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
		game.makeMove(CRAB, null, makeCoordinate(0, -1));		
		game.makeMove(HORSE, null, makeCoordinate(0, 2));
		game.makeMove(CRAB, null, makeCoordinate(0, -2));		
		game.makeMove(HORSE, null, makeCoordinate(0, 3));
		game.makeMove(CRAB, null, makeCoordinate(0, -3));		
		game.makeMove(HORSE, null, makeCoordinate(0, 4));
		game.makeMove(CRAB, null, makeCoordinate(0, -4));		
		assertEquals(OK, game.makeMove(HORSE, null, makeCoordinate(0, 5)));
	}

	@Test(expected=HantoException.class)
	public void bluePlaces5Horses() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
		game.makeMove(HORSE, null, makeCoordinate(0, -1));		
		game.makeMove(CRAB, null, makeCoordinate(0, 2));
		game.makeMove(HORSE, null, makeCoordinate(0, -2));		
		game.makeMove(CRAB, null, makeCoordinate(0, 3));
		game.makeMove(HORSE, null, makeCoordinate(0, -3));		
		game.makeMove(CRAB, null, makeCoordinate(0, 4));
		game.makeMove(HORSE, null, makeCoordinate(0, -4));		
		game.makeMove(CRAB, null, makeCoordinate(0, 5));
		game.makeMove(HORSE, null, makeCoordinate(0, -5));		
	}

	@Test(expected=HantoException.class)
	public void redPlaces5Horses() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
		game.makeMove(CRAB, null, makeCoordinate(0, -1));		
		game.makeMove(HORSE, null, makeCoordinate(0, 2));
		game.makeMove(CRAB, null, makeCoordinate(0, -2));		
		game.makeMove(HORSE, null, makeCoordinate(0, 3));
		game.makeMove(CRAB, null, makeCoordinate(0, -3));		
		game.makeMove(HORSE, null, makeCoordinate(0, 4));
		game.makeMove(CRAB, null, makeCoordinate(0, -4));		
		game.makeMove(HORSE, null, makeCoordinate(0, 5));
		game.makeMove(CRAB, null, makeCoordinate(0, -5));		
		game.makeMove(HORSE, null, makeCoordinate(0, 6));
	}

	
	@Test
	public void redMovesCrab() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(CRAB, null, makeCoordinate(0, 1));
		game.makeMove(SPARROW, null, makeCoordinate(0, -1));		
		game.makeMove(CRAB, makeCoordinate(0, 1), makeCoordinate(1, 0));
	}
	
	@Test(expected=HantoException.class)
	public void resignAtFirstMove() throws HantoException
	{
		game.makeMove(null, null, null);
	}

	@Test(expected=HantoException.class)
	public void blueButterflyNotOnBoardByFourthTurn() throws HantoException
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
	public void redButterflyNotOnBoardByFourthTurn() throws HantoException
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
	public void resignWhenNoMoreMoveAvailableAndButterflyNotSurrounded() throws HantoException
	{
		final PieceLocationPair[] board = new PieceLocationPair[] {
		    plPair(BLUE, BUTTERFLY, 0, 0), plPair(RED, BUTTERFLY, 0, 1),
		    plPair(RED, CRAB,      -1, 0), plPair(RED, CRAB, 1, -1)
		};
		testGame.initializeBoard(board);
		testGame.setPlayerMoving(BLUE);
		testGame.setTurnNumber(4);
		final MoveResult mr = game.makeMove(null, null, null);
		assertEquals(RED_WINS, mr);
	}

	@Test(expected=HantoPrematureResignationException.class)
	public void resignWhenOnlyAMoveIsAvailable() throws HantoException
	{
		final PieceLocationPair[] board = new PieceLocationPair[] {
		    plPair(BLUE, BUTTERFLY, 0, 0), plPair(RED, BUTTERFLY, 0, 1),
		    plPair(RED, CRAB,      -1, 0), plPair(RED, CRAB, 1, -1),
		    plPair(BLUE, CRAB,     0, -1), plPair(RED, CRAB, 1, -2)
		};
		testGame.initializeBoard(board);
		testGame.setPlayerMoving(BLUE);
		testGame.setTurnNumber(4);
		final MoveResult mr = game.makeMove(null, null, null);
		assertEquals(RED_WINS, mr);
	}

	@Test(expected=HantoPrematureResignationException.class)
	public void resignWhenOnlyAFlyIsAvailable() throws HantoException
	{
		final PieceLocationPair[] board = new PieceLocationPair[] {
		    plPair(BLUE, BUTTERFLY, 0, 0), plPair(RED, BUTTERFLY, 0, 1),
		    plPair(RED, CRAB,      -1, 0), plPair(RED, CRAB, 1, -1),
		    plPair(BLUE, SPARROW,   0, -1), plPair(RED, CRAB, 1, -2)
		};
		testGame.initializeBoard(board);
		testGame.setPlayerMoving(BLUE);
		testGame.setTurnNumber(4);
		final MoveResult mr = game.makeMove(null, null, null);
		assertEquals(RED_WINS, mr);
	}

	@Test(expected=HantoPrematureResignationException.class)
	public void resignWhenOnlyJumpIsAvailable() throws HantoException
	{
		final PieceLocationPair[] board = new PieceLocationPair[] {
		    plPair(BLUE, BUTTERFLY, 0, 0), plPair(RED, BUTTERFLY, 0, 1),
		    plPair(RED, CRAB,      -1, 0), plPair(RED, CRAB, 1, -1),
		    plPair(BLUE, HORSE,    0, -1), plPair(RED, CRAB, 1, -2)
		};
		testGame.initializeBoard(board);
		testGame.setPlayerMoving(BLUE);
		testGame.setTurnNumber(4);
		final MoveResult mr = game.makeMove(null, null, null);
		assertEquals(RED_WINS, mr);
	}

	
	@Test
	public void gameDoesNotEndsAfter10000Moves() throws HantoException
	{
		final PieceLocationPair[] board = new PieceLocationPair[] {
			    plPair(BLUE, BUTTERFLY, 0, 0), plPair(RED, BUTTERFLY, 0, 1),
			    plPair(BLUE, SPARROW, 0, -1), plPair(RED, SPARROW, 0, 2)
			    
		};
		testGame.initializeBoard(board);
		testGame.setPlayerMoving(RED);
		testGame.setTurnNumber(10000);
		assertEquals(OK, game.makeMove(SPARROW, makeCoordinate(0, 2), makeCoordinate(1, 1)));
	}

	@Test
	public void horseCanJump6HexesInAStraightLine() throws HantoException {
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
		game.makeMove(CRAB, null, makeCoordinate(0, -1));		
		game.makeMove(CRAB, null, makeCoordinate(0, 2));
		game.makeMove(HORSE, null, makeCoordinate(0, -2));
		game.makeMove(CRAB, null, makeCoordinate(0, 3));
		assertEquals(OK, game.makeMove(HORSE, makeCoordinate(0, -2), makeCoordinate(0, 4)));
	}
	
	@Test
	public void horseCanJumpInAStraightDiagonalLine() throws HantoException {
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
		game.makeMove(CRAB, null, makeCoordinate(-1, 0));		
		game.makeMove(CRAB, null, makeCoordinate(0, 2));
		game.makeMove(HORSE, null, makeCoordinate(-2, 0));
		game.makeMove(CRAB, null, makeCoordinate(0, 3));
		assertEquals(OK, game.makeMove(HORSE, makeCoordinate(-2, 0), makeCoordinate(1, 0)));
	}
	
	@Test(expected=HantoException.class)
	public void horseCannotJumpOutsideAStraightLine() throws HantoException {
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
		game.makeMove(CRAB, null, makeCoordinate(0, -1));		
		game.makeMove(CRAB, null, makeCoordinate(0, 2));
		game.makeMove(HORSE, null, makeCoordinate(0, -2));
		game.makeMove(CRAB, null, makeCoordinate(0, 3));
		assertEquals(OK, game.makeMove(HORSE, makeCoordinate(0, -2), makeCoordinate(1, 3)));
	}
	
	@Test
	public void resignWhenNoMoreAvailableMoves() throws HantoException{
		final PieceLocationPair[] board = new PieceLocationPair[] {
			    plPair(BLUE, BUTTERFLY, 0 , 1 ), plPair(RED, BUTTERFLY, 0, 0),
			    plPair(BLUE, CRAB,      -1, 1 ), plPair(RED, SPARROW,   1, 0),
			    plPair(BLUE, CRAB,      -1, 0 ), plPair(RED, CRAB,      0, -1),
			    plPair(BLUE, SPARROW,   1 , -1 )			    
		};
		testGame.initializeBoard(board);
		testGame.setPlayerMoving(RED);
		testGame.setTurnNumber(700);
		assertEquals(BLUE_WINS, game.makeMove(null, null, null));		
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
