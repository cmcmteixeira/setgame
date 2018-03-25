package setgame

import setgame.game.GameState
import setgame.game.handler.{CardDealingHandler, NoMoreSetsSinglePlayerHandler, SetIdentifiedHandler}

class SinglePlayerGameRun(newSetFoundHandler: SetIdentifiedHandler, cardDealerHandler: CardDealingHandler, noMoreSetsHandler: NoMoreSetsSinglePlayerHandler) {
  def run(initialState: GameState): Unit = {
    val gs01 = initialState.copy(
      table = cardDealerHandler.handle(initialState.table)
    )
    val player = initialState.players.head
    val c1 :: c2 :: c3 :: _ = gs01.table.cardsOnTable

    val gs02 = newSetFoundHandler.handle(gs01,player, (c1,c2,c3)) //player1 thinks he found a set
    val gs03 = gs02.copy(
      table = cardDealerHandler.handle(gs02.table)
    )//new cards will be taken from the deck to cover player 1 removed cards (if it found a valid set)


    val gs04 = noMoreSetsHandler.handle(gs03)// players can't find new sets on the table

    //game finishes
    val playerScore = gs04
      .players
      .map{ p =>
        (p, p.foundSets.length - p.penalties)
      }
      .sortBy{ case (_,score) => score}
      .reverse


    println("Player scores")
    println(playerScore.mkString("\n"))
    println("Table:")
    println(gs04.table.cardsOnTable.mkString("\n"))
  }


}
