package mmorihiro.larger_circle.controller

import ktx.actors.plus
import mmorihiro.larger_circle.view.BarView


class BarController(originBullets: Int) : Controller {
    override val view = BarView(originBullets).apply {
        viewport.camera.translate(0f, -451f, 0f)
        this + life
        this + turnLabel
        this + star
        this + starLabel
    }
    
}