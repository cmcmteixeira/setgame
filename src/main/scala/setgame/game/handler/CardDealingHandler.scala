package setgame.game.handler

import setgame.game.model.{Deck, Table}

class CardDealingHandler(recommendedCardSlots: Int) {

  def handle(table: Table) : Table = {
    val availableSlots = Math.max(recommendedCardSlots - table.cardsOnTable.length,0) //the rules tell us there can be more than the recommended card slots in the table
    val (newCardsOnTable, cardsOnDeck) = table.deck.cards.splitAt(availableSlots)
    table.copy(
      deck = Deck(cardsOnDeck),
      cardsOnTable = newCardsOnTable ::: table.cardsOnTable
    )
  }
}
