package mmorihiro.matchland.controller

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import ktx.actors.onClick
import ktx.assets.Assets
import ktx.assets.asset
import ktx.assets.load
import ktx.assets.unload
import ktx.scene2d.Scene2DSkin
import mmorihiro.matchland.controller.appwarp.WaitingView
import mmorihiro.matchland.controller.appwarp.WarpController
import mmorihiro.matchland.view.HomeView
import mmorihiro.matchland.view.StageView
import mmorihiro.matchland.view.View

class MainListener : ApplicationAdapter() {
    private val topView by lazy { View() }
    private var currentViews: List<View> = listOf()

    override fun create() {
        loadAssets()
        Scene2DSkin.defaultSkin = asset("ui/uiskin.json")
        currentViews = listOf(homeView(), topView)
    }

    private fun homeView(): HomeView =
            HomeController(onPlay = {
                currentViews = listOf(
                        StageView({ backHome() }, topView),
                        topView)
            }, onBattle = {
                val waitingView = WaitingView()
                val warpController =
                        WarpController({ currentViews -= waitingView }, { backHome() }, topView)
                Gdx.input.inputProcessor = waitingView
                waitingView.button.onClick { _, _ ->
                    warpController.warpClient.disconnect()
                    backHome()
                    StageChangeEffect().resumeEffect(topView)
                }
                currentViews = listOf(warpController.view, topView, waitingView)
            }, top = topView).view

    private fun backHome() {
        currentViews = listOf(homeView(), topView)
    }


    private fun loadAssets() {
        Assets.manager = AssetManager()
        load<Skin>("ui/uiskin.json")
        load<Texture>("homeBackground.png")
        load<Texture>("play.png")
        load<Texture>("online.png")
        load<Texture>("items.png")
        load<Texture>("tile.png")
        Assets.manager.finishLoading()
        load<Texture>("background.png")
        load<Texture>("backgroundTop.png")
        load<Texture>("star.png")
        load<Texture>("grayStar.png")
        load<Texture>("bar.png")
        load<Texture>("white.png")
        load<Texture>("pause.png")
    }

    override fun render() {
        Gdx.gl.glClearColor(0 / 255f, 136 / 255f, 170 / 255f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        currentViews.forEach { it.act(Gdx.graphics.deltaTime) }
        currentViews.forEach(Stage::draw)
    }

    override fun resize(width: Int, height: Int) {
        currentViews.forEach { it.vp.update(width, height) }
    }

    override fun dispose() {
        unload("homeBackground.png")
        unload("background.png")
        unload("items.png")
        unload("tile.png")
        unload("star.png")
        unload("grayStar.png")
        unload("bar.png")
        unload("white.png")
        unload("ui/uiskin.json")
    }
}
