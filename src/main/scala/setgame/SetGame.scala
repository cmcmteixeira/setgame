package setgame

import setgame.game.GameState
import setgame.game.handler.{CardDealingHandler, NoMoreSetsHandler, NoMoreSetsSinglePlayerHandler, SetIdentifiedHandler}
import setgame.game.model.enums.{Color, Count, Shading, Shape}
import setgame.game.model.{Card, Deck, Player, Table}
import setgame.game.rules.SetValidator

object SetGame {

  def main(args: Array[String]): Unit = {
    val allCards = for {
      shape <- Shape.values
      color <- Color.values
      count <- Count.values
      shading <- Shading.values
    } yield Card(shape,color,count,shading)

    val aValidSet = Set( //hack to make the first three cards be a set
      Card(Shape.Diamond,Color.Purple,Count.One,Shading.Solid),
      Card(Shape.Diamond,Color.Purple,Count.Two ,Shading.Outlined),
      Card(Shape.Diamond,Color.Purple,Count.Three,Shading.Striped),
    )
    val cardsWithSet = aValidSet.toList ::: (allCards -- aValidSet).toList

    val setValidator = new SetValidator
    val newSetFoundHandler = new SetIdentifiedHandler(setValidator)
    val cardDealerHandler = new CardDealingHandler(12)
    val noMoreSetsHandler = new NoMoreSetsHandler(3)
    val singlePlayerNoMoreSetsHandler = new NoMoreSetsSinglePlayerHandler(3)

    val cardDeck = Deck(cardsWithSet)
    val players = List(
      Player("player1",0 ,List.empty),
      Player("player2",0 ,List.empty),
      Player("player3",0 ,List.empty)
    )
    val initialState = GameState(
      Table(cardDeck,List.empty),
      players
    )

    val fakeMultiPlayerRun = new MultiPlayerGameRun(
      newSetFoundHandler,
      cardDealerHandler,
      noMoreSetsHandler
    )
    fakeMultiPlayerRun.run(initialState)


    val singlePlayerRun = new SinglePlayerGameRun(
      newSetFoundHandler,
      cardDealerHandler,
      singlePlayerNoMoreSetsHandler
    )
    singlePlayerRun.run(initialState.copy(players = List(initialState.players.head)))
  }
}
