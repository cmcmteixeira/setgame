package setgame.game.model

import setgame.game.model.enums.Color.Color
import setgame.game.model.enums.Count.Count
import setgame.game.model.enums.Shading.Shading
import setgame.game.model.enums.Shape.Shape

case class Card(shape: Shape, color: Color, count: Count, shading: Shading)
