package mmorihiro.larger_circle.view

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Image
import ktx.actors.plus
import ktx.assets.asset


class MapView : View() {
    val pointer = Image(asset<Texture>("pointer.png")).apply {
        x = 120f
        y = 112f
    }
    val loader = LoadBubbles(32, "bubbles.png")
    val bubbles = Group()
    val tileSize = 34f
    val tiles = Group().apply {
        (0..7).map { xIndex ->
            newCol(xIndex).forEach { tile ->
                tile.x += 52f
                this + tile
            }
        }
    }

    val stars = Group().apply {
        (0..MathUtils.random(3)).map {
            MathUtils.random(6)
        }.toSet().map { xIndex ->
            newStars(xIndex).forEach { tile ->
                tile.x += 52f
                this + tile
            }
        }
    }

    fun newCol(xIndex: Int) =
            (0..6).map { yIndex ->
                Image(asset<Texture>("tile.png")).apply {
                    x = xIndex * tileSize
                    y = yIndex * tileSize + 10f
                }
            }

    fun newStars(xIndex: Int) =
            (0..MathUtils.random(1)).map {
                MathUtils.random(6)
            }.toSet().map { yIndex ->
                Image(asset<Texture>("star.png")).apply {
                    x = xIndex * tileSize + 6
                    y = yIndex * tileSize + 16f
                }
            }
}