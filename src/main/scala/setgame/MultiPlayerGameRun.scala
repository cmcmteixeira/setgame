package setgame

import setgame.game.GameState
import setgame.game.handler.{CardDealingHandler, NoMoreSetsHandler, SetIdentifiedHandler}

class MultiPlayerGameRun(
                              newSetFoundHandler: SetIdentifiedHandler,
                              cardDealerHandler: CardDealingHandler,
                              noMoreSetsHandler: NoMoreSetsHandler
                            ) {

  def run(initialState: GameState) = {
    val gs01 = initialState.copy(
      table = cardDealerHandler.handle(initialState.table)
    )
    val player = initialState.players.head
    val c1 :: c2 :: c3 :: _ = gs01.table.cardsOnTable

    val gs02 = newSetFoundHandler.handle(gs01,player, (c1,c2,c3)) //player1 thinks he found a set
    val gs03 = gs02.copy(
      table = cardDealerHandler.handle(gs02.table)
    )//new cards will be taken from the deck to cover player 1 removed cards (if it found a valid set)

    val gs04 = gs03.copy(
      table = noMoreSetsHandler.handle(gs03.table)
    )// players can't find new sets on the table

    //game finishes
    val scoreSortedPlayers = gs04
      .players
      .map{ p =>
        (p, p.foundSets.length - p.penalties)
      }
      .sortBy{ case (_,score) => score}
      .reverse
    val (_, maxScore) = scoreSortedPlayers.head

    val winners = scoreSortedPlayers.collect{
      case winner@(_,score) if score == maxScore => winner
    }

    println("Player scores")
    println(scoreSortedPlayers.mkString("\n"))
    println("Winners:")
    println(winners)
    println("Table:")
    println(gs04.table.cardsOnTable.mkString("\n"))
  }
}
