package setgame.game.handler

import org.scalatest.{Matchers, WordSpec}
import setgame.game.model.{Card, Deck, Table}
import setgame.game.model.enums.{Color, Count, Shading, Shape}

class NoMoreSetsHandlerSpec extends WordSpec with Matchers {
  val cards = List(
    Card(Shape.Diamond  , Color.Green,  Count.One, Shading.Outlined),
    Card(Shape.Squiggle , Color.Green,  Count.One, Shading.Outlined),
    Card(Shape.Oval     , Color.Green,  Count.One, Shading.Outlined),
    Card(Shape.Diamond  , Color.Red, Count.One, Shading.Outlined),
    Card(Shape.Diamond  , Color.Green, Count.One, Shading.Outlined),
    Card(Shape.Diamond  , Color.Purple, Count.One, Shading.Outlined),
  )
  val newCardsCount = 3
  val handler = new NoMoreSetsHandler(newCardsCount)
  "The NoMoreSetsCardDealingHandlerSpec" should {

    "add as many cards to the table as specified by newCardsCount" in {
      val (cardsOnTable, cardsOnDeck) = cards.splitAt(2)
      val newTable = handler.handle(Table(
        Deck(cardsOnDeck),
        cardsOnTable
      ))

      newTable.cardsOnTable.length shouldBe (cardsOnTable.length + newCardsCount)
      newTable.deck.cards.length shouldBe 1 //there's only one card left in the deck
    }

    "add all cards left in the deck if the number of cards in the deck is smaller than newCardsCount" in {
      val (cardsOnTable, cardsOnDeck) = cards.splitAt(5) //five cards in the table & one card in the deck
      val newTable = handler.handle(Table(
        Deck(cardsOnDeck),
        cardsOnTable
      ))

      newTable.cardsOnTable.length shouldBe 6 //all cards have been placed in the table
      newTable.deck.cards.length shouldBe 0 // the deck is empty
    }
  }
}
