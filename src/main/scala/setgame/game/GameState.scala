package setgame.game

import setgame.game.model.{Player, Table}

case class GameState(table: Table, players: List[Player])
