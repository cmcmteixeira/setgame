package setgame.game.model


case class Player(name: String, penalties: Long, foundSets: List[(Card,Card,Card)])
