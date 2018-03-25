package setgame.game.handler

import setgame.game.GameState
import setgame.game.model.Deck


class NoMoreSetsSinglePlayerHandler(newCardsCount: Int) {
  def handle(gs: GameState) : GameState = {
    val table = gs.table
    val player = gs.players.head //this is a single player handler so it's fine to get just the head
    val (newCardsOnTable, cardsOnDeck) = table.deck.cards.splitAt(newCardsCount)

    val newTable = table.copy(
      deck = Deck(cardsOnDeck),
      cardsOnTable = newCardsOnTable ::: table.cardsOnTable
    )
    gs.copy(
      table = newTable,
      players = List(player.copy(penalties = player.penalties+1))
    )
  }
}

