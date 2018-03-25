package setgame.game.handler

import setgame.game.model.{Deck, Table}


class NoMoreSetsHandler(newCardsCount: Int){
  def handle(table: Table) : Table = {
    val (newCardsOnTable, cardsOnDeck) = table.deck.cards.splitAt(newCardsCount)
    table.copy(
        deck = Deck(cardsOnDeck),
        cardsOnTable = newCardsOnTable ::: table.cardsOnTable
    )
  }
}