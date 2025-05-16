# University of Washington, Programming Languages, Homework 6, hw6runner.rb

# This is the only file you turn in, so do not modify the other files as
# part of your solution.

class MyPiece < Piece
  # The constant All_My_Pieces should be declared here

  # your enhancements here
  def initialize (point_array, board)
    super(point_array, board)
  end

  def self.next_piece (board)
    MyPiece.new(All_My_Pieces.sample, board)
  end

  def self.cheat_piece (board)
    MyPiece.new([[[0, 0]]], board)
  end

  # class array holding all the pieces and their rotations
  All_My_Pieces = [
                [[[0, 0], [1, 0], [0, 1], [1, 1]]],  # square (only needs one)
                rotations([[0, 0], [-1, 0], [1, 0], [0, -1]]), # T
                [[[0, 0], [-1, 0], [1, 0], [2, 0]], # long (only needs two)
                [[0, 0], [0, -1], [0, 1], [0, 2]]],
                rotations([[0, 0], [0, -1], [0, 1], [1, 1]]), # L
                rotations([[0, 0], [0, -1], [0, 1], [-1, 1]]), # inverted L
                rotations([[0, 0], [-1, 0], [0, -1], [1, -1]]), # S
                rotations([[0, 0], [1, 0], [0, -1], [-1, -1]]), # Z
                rotations([[0, 0], [-1, 0], [1, 0], [0, -1], [-1, -1]]), # b
                rotations([[0, 0], [-1, 0], [1, 0], [0, -1], [1, -1]]), # d
                [[[0, 0], [-2, 0], [-1, 0], [1, 0], [2, 0]],  # 5 piece long (only needs 2)
                 [[0, 0], [0, -2], [0, -1], [0, 1], [0, 2]]],
                rotations([[0, 0], [1, 0], [0, 1]]) # 3 piece L
              ] 


end

class MyBoard < Board
  # your enhancements here
  def initialize (game)
    @grid = Array.new(num_rows) {Array.new(num_columns)}
    @current_block = MyPiece.next_piece(self)
    @score = 0
    @game = game
    @delay = 500
  end

  def next_piece
    if @flag_cheat
      @current_block = MyPiece.cheat_piece(self)
      @score -= 100
      @current_pos = nil
      @flag_cheat = false
    else
      @current_block = MyPiece.next_piece(self)
      @current_pos = nil
    end
  end

  def rotate_180_deg
    if !game_over? and @game.is_running?
      @current_block.move(0, 0, 2)
    end
    draw
  end

  def cheat
    if @score >= 100
      @flag_cheat = true
    else
      @flag_cheat = false
    end
  end

  def store_current
    locations = @current_block.current_rotation
    displacement = @current_block.position
    (0..locations.size - 1).each{|index| 
      current = locations[index];
      @grid[current[1]+displacement[1]][current[0]+displacement[0]] = 
      @current_pos[index]
    }
    remove_filled
    @delay = [@delay - 2, 80].max
  end
end

class MyTetris < Tetris
  # your enhancements here
  def initialize
    super
  end

  def set_board
    @canvas = TetrisCanvas.new
    @board = MyBoard.new(self)
    @canvas.place(@board.block_size * @board.num_rows + 3,
                  @board.block_size * @board.num_columns + 6, 24, 80)
    @board.draw
  end

  def key_bindings  
    super

    @root.bind('u' , proc {@board.rotate_180_deg}) 

    @root.bind('c' , proc {@board.cheat})
  end

  
end

