package setgame.game.handler

import org.scalatest.{Matchers, WordSpec}
import setgame.game.GameState
import setgame.game.model.enums.{Color, Count, Shading, Shape}
import setgame.game.model.{Card, Deck, Player, Table}
import setgame.game.rules.SetValidator

class PlayerSetIdentifiedHandlerSpec extends WordSpec with Matchers {
  "PlayerSetIdentifiedHandlerSpec" should {
    val alwaysValidSetValidator : SetValidator = new SetValidator{
      override def validate(set: (Card,Card,Card)) : Boolean  = true
    }
    val alwaysFalseSetValidator : SetValidator = new SetValidator{
      override def validate(set: (Card,Card,Card)) : Boolean  = false
    }
    val cards = List(
      Card(Shape.Diamond  , Color.Green,  Count.One, Shading.Outlined),
      Card(Shape.Squiggle , Color.Green,  Count.One, Shading.Outlined),
      Card(Shape.Oval     , Color.Green,  Count.One, Shading.Outlined),
      Card(Shape.Diamond  , Color.Red, Count.One, Shading.Outlined),
      Card(Shape.Diamond  , Color.Green, Count.One, Shading.Outlined),
      Card(Shape.Diamond  , Color.Purple, Count.One, Shading.Outlined),
    )
    val gc = GameState(
      table = Table (
        deck = Deck(cards.take(1)),
        cards.slice(1,10000)
      ),
      players = List(
        Player("player1",0,List.empty),
        Player("player2",0,List.empty),
      )
    )
    val c1 :: c2 :: c3 :: _ = cards

    "add a valid found set to a player and remove it from the table" in {
      val handler = new SetIdentifiedHandler(alwaysValidSetValidator)
      val newContext = handler.handle(
        gc,
        gc.players.head,
        (c1,c2,c3)
      )
      newContext.table.cardsOnTable.length shouldBe gc.table.cardsOnTable.length - 3//the set should have been removed
      newContext.players.find(_.name=="player1") shouldBe Some(Player("player1",0,List((c1,c2,c3)))) //player 1 should have the found set
    }
    "keep an invalid set in the table and increment the player penalties" in {
      val handler = new SetIdentifiedHandler(alwaysFalseSetValidator)
      val newContext = handler.handle(
        gc,
        gc.players.head,
        (c1,c2,c3)
      )
      newContext.table.cardsOnTable.length shouldBe gc.table.cardsOnTable.length//the set should NOT have been removed
      newContext.players.find(_.name=="player1") shouldBe Some(Player("player1",1,List.empty)) // player1 should have been penalized
    }

  }



}
