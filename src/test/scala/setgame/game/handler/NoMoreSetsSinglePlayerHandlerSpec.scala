package setgame.game.handler

import org.scalatest.{Matchers, WordSpec}
import setgame.game.GameState
import setgame.game.model.{Card, Deck, Player, Table}
import setgame.game.model.enums.{Color, Count, Shading, Shape}

class NoMoreSetsSinglePlayerHandlerSpec extends WordSpec with Matchers {
  "NoMoreSets for single player mode" should {
    val cards = List(
      Card(Shape.Diamond  , Color.Green,  Count.One, Shading.Outlined),
      Card(Shape.Squiggle , Color.Green,  Count.One, Shading.Outlined),
      Card(Shape.Oval     , Color.Green,  Count.One, Shading.Outlined),
      Card(Shape.Diamond  , Color.Red, Count.One, Shading.Outlined),
      Card(Shape.Diamond  , Color.Green, Count.One, Shading.Outlined),
      Card(Shape.Diamond  , Color.Purple, Count.One, Shading.Outlined),
    )
    val deck = Deck(cards)
    val newCardsCount = 2
    val handler = new NoMoreSetsSinglePlayerHandler(newCardsCount)
    val player = Player("player1",0,List.empty)

    "decrement the player's score and add the new cards to the table" in {
        val gs = GameState(
          table = Table(deck,List.empty),
          players = List(player)
        )
      val newGs = handler.handle(gs)
      newGs.players shouldBe List(player.copy(penalties = 1))
      newGs.table.cardsOnTable.length shouldBe 2
      newGs.table.deck.cards.length shouldBe 4
    }

  }
}
