package setgame.game.handler

import org.scalatest.{Matchers, WordSpec}
import setgame.game.model.{Card, Deck, Table}
import setgame.game.model.enums.{Color, Count, Shading, Shape}

class CardDealingHandlerSpec extends WordSpec with Matchers {
  val cards = List(
    Card(Shape.Diamond  , Color.Green,  Count.One, Shading.Outlined),
    Card(Shape.Squiggle , Color.Green,  Count.One, Shading.Outlined),
    Card(Shape.Oval     , Color.Green,  Count.One, Shading.Outlined),
    Card(Shape.Diamond  , Color.Purple, Count.One, Shading.Outlined),
    Card(Shape.Diamond  , Color.Purple, Count.One, Shading.Solid),
  )
  val cardSlotsOnTheTable=3
  val handler = new CardDealingHandler(cardSlotsOnTheTable)

  "The CardDealingHandler" should {

    "add as many cards as specified in the recommendedCardSlots param to an empty table" in {
      val cardsOnTable = List.empty
      val deck = Deck(cards)
      val table = Table(
        deck = deck,
        cardsOnTable = cardsOnTable
      )
      val newTable = handler.handle(table)

      newTable.cardsOnTable.length shouldBe cardSlotsOnTheTable //the dealer has dealt the right number of cards
      newTable.deck.cards.length shouldBe cards.length-cardSlotsOnTheTable //the dealer took the cards out of the deck
    }

    "add enough cards on a table w/ cards to bring the total count up to the recommendedCardSlots" in {
      val (cardsOnTable, cardsOnDeck) = cards.splitAt(2)
      val newTable = handler.handle(Table(
        Deck(cardsOnDeck),
        cardsOnTable
      ))

      newTable.cardsOnTable.length shouldBe cardSlotsOnTheTable
      newTable.deck.cards.length shouldBe 2 //there's only one card left in the deck
    }

    "add all cards left in the deck if the count of available slots in the table is bigger than the number of cards left in the deck" in {
      val (cardsOnTable, cardsOnDeck) = cards.take(2).splitAt(1) //one card in the table & one card in the deck
      val newTable = handler.handle(Table(
        Deck(cardsOnDeck),
        cardsOnTable
      ))

      newTable.cardsOnTable.length shouldBe 2 //all cards have been placed in the table
      newTable.deck.cards.length shouldBe 0 // the deck is empty
    }

    "add no cards if the table already has enough cards" in {
      val (cardsOnTable, cardsOnDeck) = cards.splitAt(3) //one card in the table & one card in the deck
      val newTable = handler.handle(Table(
        Deck(cardsOnDeck),
        cardsOnTable
      ))

      newTable.cardsOnTable.length shouldBe 3 //all cards have been placed in the table
      newTable.deck.cards.length shouldBe 2 // the deck is empty
    }

    "add no cards if the table has a number larger than the recommended number of cards" in {
      val (cardsOnTable, cardsOnDeck) = cards.splitAt(4) //one card in the table & one card in the deck
      val newTable = handler.handle(Table(
        Deck(cardsOnDeck),
        cardsOnTable
      ))

      newTable.cardsOnTable.length shouldBe 4 //all cards have been placed in the table
      newTable.deck.cards.length shouldBe 1 // the deck is empty
    }
  }
}
