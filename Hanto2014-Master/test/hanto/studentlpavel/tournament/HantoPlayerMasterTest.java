package hanto.studentlpavel.tournament;

import static hanto.common.HantoPieceType.*;
import static hanto.common.HantoPlayerColor.*;
import static hanto.common.MoveResult.*;
import static hanto.common.HantoGameID.*;
import static org.junit.Assert.*;
import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoGameID;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;
import hanto.studentlpavel.BaseHantoMove;
import hanto.studentlpavel.tournament.HantoPlayerMasterTest.TestHantoCoordinate;
import hanto.tournament.HantoMoveRecord;

import org.junit.Test;

import common.HantoTestGame.PieceLocationPair;

public class HantoPlayerMasterTest {
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

	
	@Test
	public void startAsBlueInitialButterflyAtOrigin() throws HantoException
	{
		HantoPlayer player = new HantoPlayer();
		player.startGame(EPSILON_HANTO, BLUE, true);
		HantoMoveRecord mr = player.makeMove(null);
		assertEquals(mr.getPiece(), BUTTERFLY);
		assertEquals(mr.getFrom(), null);
		assertEquals(mr.getTo().getX(), 0);
		assertEquals(mr.getTo().getY(), 0);
	}
	
	@Test
	public void startAsRedInitialButterflyAtOrigin() throws HantoException
	{
		HantoPlayer player = new HantoPlayer();
		player.startGame(EPSILON_HANTO, RED, true);
		HantoMoveRecord mr = player.makeMove(null);
		assertEquals(mr.getPiece(), BUTTERFLY);
		assertEquals(mr.getFrom(), null);
		assertEquals(mr.getTo().getX(), 0);
		assertEquals(mr.getTo().getY(), 0);
	}

	@Test
	public void moveSecondAsRed() throws HantoException
	{
		HantoPlayer player = new HantoPlayer();
		player.startGame(EPSILON_HANTO, BLUE, false);
		HantoMoveRecord mr = player.makeMove(new HantoMoveRecord(BUTTERFLY, null, makeCoordinate(0, 0)));
		assertEquals(mr.getPiece(), BUTTERFLY);
		assertEquals(mr.getFrom(), null);
	}

	@Test
	public void moveSecondAsBlueTwoMoves() throws HantoException
	{
		HantoPlayer player = new HantoPlayer();
		player.startGame(EPSILON_HANTO, RED, false);
		HantoMoveRecord mr = player.makeMove(new HantoMoveRecord(BUTTERFLY, null, makeCoordinate(0, 0)));
		if(mr.getTo().getX() == 0 && mr.getTo().getY() == 1)
			mr = player.makeMove(new HantoMoveRecord(SPARROW, null, makeCoordinate(-1, 0)));
		else
			mr = player.makeMove(mr = player.makeMove(new HantoMoveRecord(SPARROW, null, makeCoordinate(0, 0))));
		
		assertNotNull(mr);
	}

	@Test
	public void badMovePrintsStackAndReturns() throws HantoException
	{
		HantoPlayer player = new HantoPlayer();
		player.startGame(EPSILON_HANTO, RED, false);
		HantoMoveRecord mr = player.makeMove(new HantoMoveRecord(BUTTERFLY, null, makeCoordinate(0, 0)));
		mr = player.makeMove(new HantoMoveRecord(BUTTERFLY, null, makeCoordinate(4, 0)));
	}
	
	@Test
	public void resignWhenNoMoreMoveAvailable() throws HantoException
	{
		final PieceLocationPair[] board = new PieceLocationPair[] {
			    plPair(BLUE, BUTTERFLY, 0 , 1 ), plPair(RED, BUTTERFLY, 0, 0),
			    plPair(BLUE, CRAB,      1, -1 ),
			    plPair(BLUE, CRAB,      -1, 0 )
		};

		HantoPlayer player = new HantoPlayer();
		player.initializeBoard(board, RED);

		HantoMoveRecord mr = player.makeMove(new HantoMoveRecord(SPARROW, null, makeCoordinate(-1, -1)));
		
		assertTrue(mr.getFrom() == null);
		assertTrue(mr.getTo() == null);
		assertTrue(mr.getPiece() == null);
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
