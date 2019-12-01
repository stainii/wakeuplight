package stijnhooft.be.wakeuplight.backend

/** Singleton class keeping a bluetooth reference to the light **/
class LightHelper private constructor() {

    private object HOLDER {
        val INSTANCE = LightHelper()
    }

    companion object {
        val instance: LightHelper by lazy { HOLDER.INSTANCE }
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
        BluetoothHelper.instance.send(key)
    }
}