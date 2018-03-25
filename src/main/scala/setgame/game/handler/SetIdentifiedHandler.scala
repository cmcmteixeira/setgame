package setgame.game.handler

import setgame.game.GameState
import setgame.game.model.{Card, Player}
import setgame.game.rules.SetValidator


class SetIdentifiedHandler(setValidator: SetValidator) {

  def handle(gameContext: GameState, player: Player, set: (Card,Card,Card)): GameState = {
    val isValidSet = setValidator.validate(set)

    val newPlayer = if (isValidSet) {
      player.copy(foundSets = set :: player.foundSets)
    } else {
      player.copy(penalties = player.penalties+1)
    }

    val (c1,c2,c3) = set
    val newCardsOnTable = if(isValidSet) {
      gameContext.table.cardsOnTable.filterNot(List(c1, c2, c3).contains(_))
    } else {
      gameContext.table.cardsOnTable
    }

    val newGameContext = gameContext.copy(
      players = newPlayer :: gameContext.players.filterNot(_ == player),
      table = gameContext.table.copy(cardsOnTable = newCardsOnTable)
    )
    newGameContext
  }

}
