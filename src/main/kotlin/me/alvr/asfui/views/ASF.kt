package me.alvr.asfui.views

import javafx.scene.Parent
import javafx.scene.control.Button
import javafx.scene.control.ComboBox
import me.alvr.asfui.ASFProcess
import me.alvr.asfui.util.Command
import tornadofx.Fragment
import tornadofx.action
import tornadofx.enableWhen
import tornadofx.selectedItem

class ASF : Fragment() {
    override val root: Parent by fxml("/asf.fxml")

    private val asfHelp: Button by fxid("asf_help")
    private val asfRejoin: Button by fxid("asf_rejoin")
    private val asfUpdate: Button by fxid("asf_update")
    private val asfVersion: Button by fxid("asf_version")
    private val asfApi: Button by fxid("asf_api")

    private val bots: ComboBox<String> by param()
    private var bot = bots.selectedItem

    init {
        bots.valueProperty().addListener { _, _, newBot ->
            bot = newBot
        }

        asfHelp.apply {
            action {
                hostServices.showDocument("https://github.com/JustArchi/ArchiSteamFarm/wiki")
            }
            enableWhen(ASFProcess.started)
        }

        asfRejoin.apply {
            action {
                runAsync {
                    Command.sendCommand(Command.generateCommand(Command.REJOIN_CHAT, bot))
                }
            }
            enableWhen(ASFProcess.started)
        }

        asfUpdate.apply {
            asfUpdate.action {
                runAsync {
                    Command.sendCommand(Command.UPDATE)
                }
            }
            enableWhen(ASFProcess.started)
        }

        asfVersion.apply {
            asfVersion.action {
                runAsync {
                    Command.sendCommand(Command.VERSION)
                }
            }
            enableWhen(ASFProcess.started)
        }

        asfApi.apply {
            asfApi.action {
                runAsync {
                    Command.sendCommand(Command.generateCommand(Command.API, bot))
                }
            }
            enableWhen(ASFProcess.started)
        }
    }
}