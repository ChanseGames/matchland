package mmorihiro.matchland.controller.appwarp

import com.shephertz.app42.gaming.multiplayer.client.events.*
import com.shephertz.app42.gaming.multiplayer.client.listener.NotifyListener
import com.typesafe.config.ConfigFactory
import io.github.config4k.extract
import ktx.actors.alpha
import mmorihiro.matchland.model.ConfigModel
import mmorihiro.matchland.model.MessageType
import java.util.*


class NotificationListener(val controller: WarpController) : NotifyListener {
    override fun onUpdatePeersReceived(event: UpdateEvent?) {
        val list = String(event!!.update).split("@")
        if (list[0] == ConfigModel.config.itemType.name) return
        when (MessageType.values().first { it.name == list[1] }) {
            MessageType.CONNECT -> {
                val config = ConfigFactory.parseString(list[2])
                onEnemyConnect(controller.view, config.extract("x"),
                        config.extract("y"), true)
            }
            MessageType.NotConnect -> {
                val config = ConfigFactory.parseString(list[2])
                onEnemyConnect(controller.view, config.extract("x"),
                        config.extract("y"), false)
            }
            MessageType.TouchUp -> onEnemyTouchUp(controller.view)
            MessageType.NotEnough -> {
                controller.view.run {
                    items.forEach {
                        it.filter { it.alpha == 0f }.forEach { it.alpha = 1f }
                    }
                    enemyConnected = listOf()
                    connectEvent?.let {
                        connectEvent = it.copy(enemy = listOf())
                    }
                }
            }
            MessageType.NewItem -> {
                val config = ConfigFactory.parseString(list[2])
                onNewItem(controller.view, config.extract("items"))
            }
        }
    }

    override fun onUserJoinedRoom(data: RoomData?, p1: String?) {
        controller.warpClient.subscribeRoom(data!!.id)
    }

    override fun onUserLeftRoom(data: RoomData?, userName: String?) {
        if (userName == ConfigModel.config.itemType.name) return
        controller.warpClient.run {
            leaveRoom(data!!.id)
            deleteRoom(data.id)
            disconnect()
        }
        controller.view.showWindow(if (isEnemyCleared(controller.view)) "Lose" else "Win")
    }

    override fun onRoomDestroyed(p0: RoomData?) {

    }

    override fun onGameStopped(p0: String?, p1: String?) {
    }

    override fun onPrivateChatReceived(p0: String?, p1: String?) {
    }

    override fun onUserResumed(p0: String?, p1: Boolean, p2: String?) {
    }

    override fun onPrivateUpdateReceived(p0: String?, p1: ByteArray?, p2: Boolean) {
    }

    override fun onRoomCreated(p0: RoomData?) {
    }

    override fun onNextTurnRequest(p0: String?) {
    }

    override fun onGameStarted(p0: String?, p1: String?, p2: String?) {
    }

    override fun onChatReceived(p0: ChatEvent?) {
    }

    override fun onUserChangeRoomProperty(p0: RoomData?, p1: String?, p2: HashMap<String, Any>?, p3: HashMap<String, String>?) {
    }

    override fun onUserLeftLobby(p0: LobbyData?, p1: String?) {
    }

    override fun onMoveCompleted(p0: MoveEvent?) {
    }

    override fun onUserPaused(p0: String?, p1: Boolean, p2: String?) {
    }

    override fun onUserJoinedLobby(p0: LobbyData?, p1: String?) {
    }
}