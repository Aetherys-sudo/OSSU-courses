## Solution template for Guess The Word practice problem (section 7)

require_relative './section-7-provided'

class ExtendedGuessTheWordGame < GuessTheWordGame
  ## YOUR CODE HERE
  def initialize secret_word_class
    super
  end


end

class ExtendedSecretWord < SecretWord
  ## YOUR CODE HERE
  def initialize phrase
    self.word = phrase
    self.pattern = self.word.split(//).map {|x| if x.match(/^[[:alpha:]]$/) then x = '-' else x end}.join("")
    @prev_wrongs = Array.new(3)
  end

  def valid_guess? guess
    guess.match(/^[[:alpha:]]$/) and guess.length == 1
  end

  def guess_letter! letter
    found = (self.word.index letter or self.word.index letter.upcase)

    if found
      start = 0
      regex = Regexp.new('[' + letter.downcase + letter.upcase + ']')
      while ix = self.word.index(regex, start)
        self.pattern[ix] = self.word[ix]
        start = ix + 1
      end
    else
      if ! @prev_wrongs.index(letter.downcase)
        @prev_wrongs.push(letter.downcase)
        found = false
      else
        found = true
      end
    end
    found
  end


end

## Change to `false` to run the original game
if true
  ExtendedGuessTheWordGame.new(ExtendedSecretWord).play
else
  GuessTheWordGame.new(SecretWord).play
end
