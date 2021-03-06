package mmorihiro.matchland.controller.appwarp

import com.shephertz.app42.gaming.multiplayer.client.WarpClient
import com.shephertz.app42.gaming.multiplayer.client.command.WarpResponseResultCode
import com.shephertz.app42.gaming.multiplayer.client.events.*
import com.shephertz.app42.gaming.multiplayer.client.listener.ZoneRequestListener


class ZoneListener(private val warpClient: WarpClient) : ZoneRequestListener {
    override fun onCreateRoomDone(re: RoomEvent?) {
        if (re!!.result == WarpResponseResultCode.SUCCESS) warpClient.joinRoom(re.data.id)
        else error("")
    }

    override fun onGetUserStatusDone(p0: LiveUserInfoEvent?) {
    }

    override fun onGetLiveUserInfoDone(p0: LiveUserInfoEvent?) {
    }

    override fun onGetAllRoomsCountDone(p0: AllRoomsEvent?) {
    }

    override fun onDeleteRoomDone(p0: RoomEvent?) {
    }

    override fun onGetAllRoomsDone(p0: AllRoomsEvent?) {
    }

    override fun onGetOnlineUsersCountDone(p0: AllUsersEvent?) {
    }

    override fun onGetMatchedRoomsDone(p0: MatchedRoomsEvent?) {
    }

    override fun onGetOnlineUsersDone(p0: AllUsersEvent?) {
    }

    override fun onSetCustomUserDataDone(p0: LiveUserInfoEvent?) {
    }

}