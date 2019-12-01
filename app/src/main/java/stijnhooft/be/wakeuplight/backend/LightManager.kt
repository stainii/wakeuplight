package stijnhooft.be.wakeuplight.backend

/** Singleton class keeping a bluetooth reference to the light **/
class LightManager private constructor() {

    private object HOLDER {
        val INSTANCE = LightManager()
    }

    companion object {
        val INSTANCE: LightManager by lazy { HOLDER.INSTANCE }
    }

    fun turnOn() {
        send("1")
    }

    fun turnOff() {
        send("0")
    }

    fun toggle() {
        send("2")
    }

    private fun send(key: String) {
        BluetoothManager.INSTANCE.send(key)
    }
}