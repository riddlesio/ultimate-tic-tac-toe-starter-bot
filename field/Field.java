/*
 *  Copyright 2016 riddles.io (developers@riddles.io)
 *
 *      Licensed under the Apache License, Version 2.0 (the "License");
 *      you may not use this file except in compliance with the License.
 *      You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *      Unless required by applicable law or agreed to in writing, software
 *      distributed under the License is distributed on an "AS IS" BASIS,
 *      WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *      See the License for the specific language governing permissions and
 *      limitations under the License.
 *
 *      For the full copyright and license information, please view the LICENSE
 *      file that was distributed with this source code.
 */

package field;

import java.util.ArrayList;

import move.Move;

/**
 * Field class
 * 
 * Handles everything that has to do with the field, such 
 * as storing the current state and performing calculations
 * on the field.
 * 
 * @author Jim van Eeden - jim@riddles.io, Joost de Meij - joost@riddles.io
 */

public class Field {
    public static final String EMPTY_FIELD = ".";
    public static final String AVAILABLE_FIELD = "-1";
    private final int COLS = 9;
    private final int ROWS = 9;

	private String[][] mBoard;
	private String[][] mMacroboard;
    private int myId;
    private int opponentId;
	
	public Field() {
		mBoard = new String[COLS][ROWS];
		mMacroboard = new String[COLS / 3][ROWS / 3];
		clearBoard();
	}
	
	/**
	 * Initialise field from comma separated String
	 * @param s input String
	 */
	public void parseFromString(String s) {
		s = s.replace(";", ",");
		String[] r = s.split(",");
		int counter = 0;
		for (int y = 0; y < ROWS; y++) {
			for (int x = 0; x < COLS; x++) {
				mBoard[x][y] = r[counter]; 
				counter++;
			}
		}
	}
	
	/**
	 * Initialise macroboard from comma separated String
	 * @param s input String
	 */
	public void parseMacroboardFromString(String s) {
		String[] r = s.split(",");
		int counter = 0;
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 3; x++) {
				mMacroboard[x][y] = r[counter];
				counter++;
			}
		}
	}
	
	public void clearBoard() {
		for (int x = 0; x < COLS; x++) {
			for (int y = 0; y < ROWS; y++) {
				mBoard[x][y] = EMPTY_FIELD;
			}
		}
	}

	public ArrayList<Move> getAvailableMoves() {
	    ArrayList<Move> moves = new ArrayList<Move>();
		
		for (int y = 0; y < ROWS; y++) {
            for (int x = 0; x < COLS; x++) {
                if (isInActiveMicroboard(x, y) && mBoard[x][y].equals(EMPTY_FIELD)) {
                	moves.add(new Move(x, y));
                }
            }
        }

		return moves;
	}
	
	public Boolean isInActiveMicroboard(int x, int y) {
	    return mMacroboard[x/3][y/3].equals(AVAILABLE_FIELD);
	}


	/**
	 * Creates comma separated String with player ids for the microboards.
	 * @return : String with player names for every cell, or 'empty' when cell is empty.
	 */
	@Override
	public String toString() {
		String r = "";
		int counter = 0;
		for (int y = 0; y < ROWS; y++) {
			for (int x = 0; x < COLS; x++) {
				if (counter > 0) {
					r += ",";
				}
				r += mBoard[x][y];
				counter++;
			}
		}
		return r;
	}
	
	/**
	 * Checks whether the field is full
	 * @return Returns true when field is full, otherwise returns false.
	 */
	public boolean isFull() {
		for (int x = 0; x < COLS; x++)
		  for (int y = 0; y < ROWS; y++)
		    if (mBoard[x][y].equals(EMPTY_FIELD))
		      return false; // At least one cell is not filled
		// All cells are filled
		return true;
	}
	
	public int getNrColumns() {
		return COLS;
	}
	
	public int getNrRows() {
		return ROWS;
	}

	public boolean isEmpty() {
		for (int x = 0; x < COLS; x++) {
			  for (int y = 0; y < ROWS; y++) {
				  if (!mBoard[x][y].equals(EMPTY_FIELD)) {
					  return false;
				  }
			  }
		}
		return true;
	}
	
	/**
	 * Returns the player id on given column and row
	 * @param column Column
     * @param row Row
	 * @return int
	 */
	public String getPlayerId(int column, int row) {
		return mBoard[column][row];
	}

    public int getMyId() {
        return this.myId;
    }

    public void setMyId(int myId) {
        this.myId = myId;
    }

    public int getOpponentId() {
        return this.opponentId;
    }

    public void setOpponentId(int opponentId) {
        this.opponentId = opponentId;
    }
}